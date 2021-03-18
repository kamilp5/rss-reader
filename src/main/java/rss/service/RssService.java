package rss.service;

import org.springframework.beans.factory.annotation.Autowired;
import rss.repository.RssFeedRepository;
import rss.repository.RssItemRepository;
import rss.user.RssFeed;

public class RssService {
    private RssFeedRepository rssFeedRepository;

    private RssItemRepository rssItemRepository;
    private UserService userService;


    @Autowired
    public RssService(RssFeedRepository rssFeedRepository, RssItemRepository rssItemRepository, UserService userService) {
        this.rssFeedRepository = rssFeedRepository;
        this.rssItemRepository = rssItemRepository;
        this.userService = userService;
    }

    public RssFeed saveRssFeed(RssFeed rssFeed){
        return rssFeedRepository.save(rssFeed);
    }

//    public RssFeed subscribeRss(String url){
//
//    }
//
//    public RssFeed subscribeRss(Long id){
//
//    }
}
