package rss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rss.repository.RssFeedRepository;
import rss.repository.RssItemRepository;
import rss.user.RssFeed;
import rss.user.User;
import rss.utils.RssReader;

import java.util.Optional;

@Service
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

    public RssFeed saveRssFeed(RssFeed rssFeed){
        return rssFeedRepository.save(rssFeed);
    }

    public RssFeed subscribeRss(String url){
        Optional<RssFeed> rssFeed = rssFeedRepository.findByUrl(url);
        if(rssFeed.isEmpty()) {
            RssFeed newRssFeed = rssReader.getRssFeed(url);
        User user = userService.getLoggedUser();
        user.addFeed(newRssFeed);
        rssFeedRepository.save(newRssFeed);
        userService.saveUser(user);
            return newRssFeed;
        }else return  rssFeed.get();

    }
}
