package rss.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rss.dto.RssFeedDto;
import rss.dto.RssFeedMapper;
import rss.dto.RssItemMapper;
import rss.repository.RssFeedRepository;
import rss.repository.RssItemRepository;
import rss.user.RssFeed;
import rss.user.User;
import rss.user.UserFeed;
import rss.utils.RssReader;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RssServiceTest {

    @Mock
    private RssFeedRepository rssFeedRepository;
    @Mock
    private RssItemRepository rssItemRepository;
    @Mock
    private UserService userService;
    @Mock
    private RssReader rssReader;
    @Mock
    private RssFeedMapper rssFeedMapper;
    @Mock
    private RssItemMapper rssItemMapper;

    @InjectMocks
    private RssService rssService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    void saveRssFeed() {
        RssFeed feed = new RssFeed();
        feed.setTitle("title");

        when(rssFeedRepository.save(any(RssFeed.class))).thenReturn(feed);
        RssFeed result = rssService.saveRssFeed(feed);

        verify(rssFeedRepository, times(1)).save(any());
    }

    @Test
    void subscribeFeed_newFeed() {
        User user = new User();
        String feedUrl = "https://jvm-bloggers.com/pl/rss";
        user.setEmail("email@email.com");
        RssFeed feed = new RssFeed();
        feed.setTitle("new feed");
        feed.setUrl(feedUrl);

        when(userService.getLoggedUser()).thenReturn(user);
        when(rssFeedRepository.findByUrl(any())).thenReturn(Optional.empty());
        when(rssFeedRepository.save(any(RssFeed.class))).thenReturn(feed);
        when(userService.saveUser(any())).thenReturn(user);
        when(rssReader.createRssFeed(feedUrl)).thenReturn(feed);

        RssFeed result = rssService.subscribeFeed(feedUrl);

        verify(userService).saveUser(userCaptor.capture());
        verify(rssReader).createRssFeed(feedUrl);
        verify(rssFeedRepository).save(any());

        assertTrue(userCaptor.getValue().getRssFeeds().stream().anyMatch(f -> f.getRssFeed().equals(feed)));
        assertEquals(feed, result);
    }

    @Test
    void subscribeFeed_existingFeed() {
        User user = new User();
        String feedUrl = "https://jvm-bloggers.com/pl/rss";
        user.setEmail("email@email.com");
        RssFeed feed = new RssFeed();
        feed.setTitle("new feed");
        feed.setUrl(feedUrl);

        when(userService.getLoggedUser()).thenReturn(user);
        when(rssFeedRepository.findByUrl(any())).thenReturn(Optional.of(feed));
        when(userService.saveUser(any())).thenReturn(user);

        RssFeed result = rssService.subscribeFeed(feedUrl);

        verify(userService).saveUser(userCaptor.capture());
        verify(rssReader, never()).createRssFeed(feedUrl);
        verify(rssFeedRepository, never()).save(any());


        assertTrue(userCaptor.getValue().getRssFeeds().stream().anyMatch(f -> f.getRssFeed().equals(feed)));
        assertEquals(feed, result);
    }

    @Test
    void unsubscribeRssFeed() {
        User user = new User();
        user.setEmail("email@email.com");
        RssFeed feed = new RssFeed();
        feed.setTitle("title");
        feed.setId(1L);
        feed.setUrl("https://jvm-bloggers.com/pl/rss");
        user.addFeed(feed);
        RssFeed feed2 = new RssFeed();
        feed2.setTitle("title");
        feed2.setId(2L);
        feed2.setUrl("https://rssjakis.com/pl/rss");
        user.addFeed(feed2);

        when(userService.getLoggedUser()).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        rssService.unsubscribeRssFeed(1L);

        verify(userService).saveUser(userCaptor.capture());
        assertTrue(userCaptor.getValue().getRssFeeds().stream().anyMatch(f -> f.getRssFeed().getId().equals(feed2.getId())));
        assertEquals(1, userCaptor.getValue().getRssFeeds().size());
    }

    @Test
    void unsubscribeRssFeed_feedNotSubscribed() {
        User user = new User();
        user.setEmail("email@email.com");
        RssFeed feed = new RssFeed();
        feed.setTitle("title");
        feed.setId(1L);
        feed.setUrl("https://jvm-bloggers.com/pl/rss");
        user.addFeed(feed);
        RssFeed feed2 = new RssFeed();
        feed2.setTitle("title");
        feed2.setId(2L);
        feed2.setUrl("https://rssjakis.com/pl/rss");
        user.addFeed(feed2);

        when(userService.getLoggedUser()).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(user);

        rssService.unsubscribeRssFeed(3L);

        verify(userService).saveUser(userCaptor.capture());
        assertTrue(userCaptor.getValue().getRssFeeds().stream().anyMatch(f -> f.getRssFeed().getId().equals(feed2.getId())));
        assertTrue(userCaptor.getValue().getRssFeeds().stream().anyMatch(f -> f.getRssFeed().getId().equals(feed.getId())));
        assertEquals(2, userCaptor.getValue().getRssFeeds().size());
    }

    @Test
    void getRssFeeds() {
        User user = new User();
        user.setEmail("email@email.com");
        RssFeed feed = new RssFeed();
        feed.setTitle("title");
        feed.setId(1L);
        feed.setUrl("https://jvm-bloggers.com/pl/rss");
        user.addFeed(feed);
        RssFeed feed2 = new RssFeed();
        feed2.setTitle("title");
        feed2.setId(2L);
        feed2.setUrl("https://rssjakis.com/pl/rss");
        user.addFeed(feed2);
        RssFeedDto feedDto1 = new RssFeedDto(1L,"title","url","");

        when(userService.getLoggedUser()).thenReturn(user);


    }

    @Test
    void getRssFeedItems() {
    }

    @Test
    void updateLastOpenedDate() {
    }

    @Test
    void addRssItemToSaved() {
    }

    @Test
    void getSavedRssItems() {
    }

    @Test
    void deleteRssItemFromSaved() {
    }
}