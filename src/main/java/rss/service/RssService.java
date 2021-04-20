package rss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rss.dto.RssFeedDto;
import rss.dto.RssFeedMapper;
import rss.dto.RssItemDto;
import rss.dto.RssItemMapper;
import rss.repository.RssFeedRepository;
import rss.repository.RssItemRepository;
import rss.Model.RssFeed;
import rss.Model.RssItem;
import rss.Model.User;
import rss.Model.UserFeed;
import rss.exception.RssNotFoundException;
import rss.utils.RssReader;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class RssService {


    private RssFeedRepository rssFeedRepository;
    private RssItemRepository rssItemRepository;
    private UserService userService;
    private RssReader rssReader;
    private RssFeedMapper rssFeedMapper;
    private RssItemMapper rssItemMapper;

    @Autowired
    public RssService(RssFeedRepository rssFeedRepository, RssItemRepository rssItemRepository, UserService userService, RssReader rssReader, RssFeedMapper rssFeedMapper, RssItemMapper rssItemMapper) {
        this.rssFeedRepository = rssFeedRepository;
        this.rssItemRepository = rssItemRepository;
        this.userService = userService;
        this.rssReader = rssReader;
        this.rssFeedMapper = rssFeedMapper;
        this.rssItemMapper = rssItemMapper;
    }

    public RssFeed saveRssFeed(RssFeed rssFeed) {
        return rssFeedRepository.save(rssFeed);
    }

    public RssFeed subscribeFeed(String url) {
        Optional<RssFeed> optionalRssFeed = rssFeedRepository.findByUrl(url);
        User user = userService.getLoggedUser();
        RssFeed rssFeed;

        if (optionalRssFeed.isPresent()) {
            rssFeed = optionalRssFeed.get();
        } else {
            rssFeed = rssReader.createRssFeed(url);
            rssFeedRepository.save(rssFeed);
        }
        user.addFeed(rssFeed);
        userService.saveUser(user);
        return rssFeed;
    }

    public void unsubscribeRssFeed(Long id) {
        User user = userService.getLoggedUser();
        user.removeFeed(id);
        userService.saveUser(user);
    }

    public List<RssFeedDto> getRssFeeds() {
        User user = userService.getLoggedUser();
        List<UserFeed> userFeeds = user.getRssFeeds();
        List<RssFeedDto> rssFeedDtos = rssFeedMapper.toDtoList(userFeeds);
        for (RssFeedDto dto : rssFeedDtos) {
            dto.setHasNewItems(rssItemRepository.existsByDateAfterAndRssFeedId(dto.getLastOpenedDate(), dto.getId()));
        }

        return rssFeedDtos;
    }

    public Page<RssItemDto> getRssFeedItems(Long feedId, Pageable pageable) {
        User user = userService.getLoggedUser();
        Page<RssItem> items = rssItemRepository.getAllByRssFeedId(feedId, pageable);
        List<RssItem> seenItems = user.getSeenRssItems();
        items = setAlreadySeen(items, seenItems);

        user.addSeenRssItems(items.stream().filter(i -> !(seenItems.contains(i))).collect(Collectors.toList()));
        userService.saveUser(user);

        return items.map(rssItemMapper::toDto);
    }

    private Page<RssItem> setAlreadySeen(Page<RssItem> items, List<RssItem> seenItems) {
        for (RssItem item : items) {
            item.setAlreadySeen(seenItems.contains(item));
        }
        return items;
    }

    public void updateLastOpenedDate(Long id) {
        User user = userService.getLoggedUser();
        Optional<RssItem> newestItem = rssItemRepository.getNewestItemByRssFeedId(id);
        if (newestItem.isPresent()) {
            UserFeed userFeed = user.getRssFeeds().stream()
                    .filter(f -> f.getRssFeed().getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new RssNotFoundException(id));
            userFeed.setLastOpenedDate(newestItem.get().getDate());
        }
        userService.saveUser(user);
    }

    public void addRssItemToSaved(Long id) {
        User user = userService.getLoggedUser();
        if (rssItemRepository.isRssItemInUserSaved(user.getId(), id).isPresent()) {
            return;
        }
        RssItem item = rssItemRepository.getOne(id);
        user.addItemToSaved(item);
        userService.saveUser(user);
    }

    public Page<RssItemDto> getSavedRssItems(Pageable pageable) {
        User user = userService.getLoggedUser();
        Page<RssItem> items = rssItemRepository.getUserSavedRssItems(user.getId(), pageable);
        return items.map(rssItemMapper::toDto);
    }

    public void deleteRssItemFromSaved(Long id) {
        User user = userService.getLoggedUser();
        user.removeItemFromSaved(id);
        userService.saveUser(user);
    }

    private void fetchRssItems() {
        List<RssFeed> rssFeeds = rssFeedRepository.findAll();
        for (RssFeed rssFeed : rssFeeds) {
            List<RssItem> rssItems = rssReader.getFeedItems(rssFeed);
            rssItems = rssItems.stream()
                    .filter(i -> rssItemRepository.getByUrl(i.getUrl()).isEmpty())
                    .collect(Collectors.toList());
            rssItemRepository.saveAll(rssItems);
        }
    }

    private void deleteRssItemsOlderThan(int days) {
        Timestamp date = Timestamp.valueOf(LocalDateTime.now().minusDays(days));
        List<RssItem> savedItems = rssItemRepository.getAllSavedItems();
        List<RssItem> allItems = rssItemRepository.getAllByDateBefore(date);
        allItems = allItems.stream().filter(i -> !savedItems.contains(i)).collect(Collectors.toList());

        rssItemRepository.deleteAll(allItems);
    }

    @Scheduled(fixedRate = 1800000)
    private void schedule(){
        fetchRssItems();
        deleteRssItemsOlderThan(RssItem.VALID_DAYS_OLD);
    }
}
