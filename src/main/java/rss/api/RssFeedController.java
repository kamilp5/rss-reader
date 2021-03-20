package rss.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rss.service.RssService;
import rss.user.RssFeed;

@RestController
public class RssFeedController {

    private RssService rssService;

    @Autowired
    public RssFeedController(RssService rssService) {
        this.rssService = rssService;
    }

    @PostMapping("/api/rssFeed")
    public ResponseEntity<RssFeed> subscribeRssFeed(@RequestBody String url){
        return new ResponseEntity<>(rssService.subscribeRss(url), HttpStatus.OK);
    }
}
