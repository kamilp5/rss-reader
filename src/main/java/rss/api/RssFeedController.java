package rss.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rss.service.RssService;
import rss.user.RssFeed;
import rss.user.RssItem;

import java.util.List;

@RestController
public class RssFeedController {

    private RssService rssService;

    @Autowired
    public RssFeedController(RssService rssService) {
        this.rssService = rssService;
    }

    @PostMapping("/api/rssFeeds")
    public RssFeed subscribeRssFeed(@RequestBody String url){
        return rssService.subscribeRss(url);
    }

    @GetMapping("/api/rssFeeds")
    public List<RssFeed> getRssFeeds(){
        return rssService.getRssFeeds();
    }
    @GetMapping("/api/rssFeeds/{id}")
    public List<RssItem> getRssFeedItems(@PathVariable Long id){
        return rssService.getRssFeedItems(id);
    }

    @PutMapping("/api/rssFeeds/{id}")
    public void unsubscribeRssFeed(@PathVariable Long id){
        rssService.unsubscribeRssFeed(id);
    }

}
