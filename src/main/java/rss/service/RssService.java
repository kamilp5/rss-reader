package rss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rss.repository.RssFeedRepository;
import rss.repository.RssItemRepository;
import rss.user.RssFeed;
import rss.user.RssItem;
import rss.user.User;
import rss.utils.RssReader;

import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class RssService {


    private RssFeedRepository rssFeedRepository;
    private RssItemRepository rssItemRepository;
    private UserService userService;
    private RssReader rssReader;


    @Autowired
    public RssService(RssFeedRepository rssFeedRepository, RssItemRepository rssItemRepository, UserService userService, RssReader rssReader) {
        this.rssFeedRepository = rssFeedRepository;
        this.rssItemRepository = rssItemRepository;
        this.userService = userService;
        this.rssReader = rssReader;
    }

    public RssFeed saveRssFeed(RssFeed rssFeed) {
        return rssFeedRepository.save(rssFeed);
    }

    public RssFeed subscribeRss(String url) {
        Optional<RssFeed> optionalRssFeed = rssFeedRepository.findByUrl(url);
        User user = userService.getLoggedUser();
        RssFeed rssFeed ;

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
        user.getRssFeeds().removeIf(f -> f.getId().equals(id));
        userService.saveUser(user);
    }

    public List<RssFeed> getRssFeeds() {
        User user = userService.getLoggedUser();
        return user.getRssFeeds();
    }

    public List<RssItem> getRssFeedItems(Long id) {
        return rssFeedRepository.getOne(id).getRssItems();
    }

    @Scheduled(fixedRate = 1800000)
    public void fetchRssItems(){
        List<RssFeed> rssFeeds = rssFeedRepository.findAll();
        for(RssFeed rssFeed: rssFeeds) {
            List<RssItem> rssItems = rssReader.getFeedItems(rssFeed);
            rssItems.forEach(i -> i.setRssFeed(rssFeed));
            rssItemRepository.saveAll(rssItems);
        }
    }
}
