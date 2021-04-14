package rss.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;
import rss.dto.RssFeedDto;
import rss.dto.RssItemDto;
import rss.service.RssService;
import rss.user.RssFeed;

import java.util.List;

@RestController
public class RssFeedController {

    private RssService rssService;

    @Autowired
    public RssFeedController(RssService rssService) {
        this.rssService = rssService;
    }

    @PostMapping("/api/rssFeeds")
    public RssFeed subscribeRssFeed(@RequestBody String url) {
        return rssService.subscribeFeed(url);
    }

    @GetMapping("/api/rssFeeds")
    public List<RssFeedDto> getRssFeeds() {
        return rssService.getRssFeeds();
    }

    @GetMapping("/api/rssFeeds/{id}")
    public Page<RssItemDto> getRssFeedItems(@PathVariable Long id,
                                            @SortDefault(sort = "date" ,direction = Sort.Direction.DESC)Pageable pageable) {
        return rssService.getRssFeedItems(id, pageable);
    }

    @DeleteMapping("/api/rssFeeds/{id}")
    public void unsubscribeRssFeed(@PathVariable Long id) {
        rssService.unsubscribeRssFeed(id);
    }

    @PutMapping("/api/rssFeeds/{id}")
    public void updateLastOpenedDate(@PathVariable Long id) {
        rssService.updateLastOpenedDate(id);
    }

    @PostMapping("/api/rssFeeds/saved")
    public void addRssItemToSaved(@RequestBody Long id){
        rssService.addRssItemToSaved(id);
    }

    @GetMapping("/api/rssFeeds/saved")
    public Page<RssItemDto> getUserSavedRssItems(Pageable pageable){
        return rssService.getSavedRssItems(pageable);
    }

    @DeleteMapping("/api/rssFeeds/saved/{id}")
    public void deleteRssItemFromSaved(@PathVariable Long id){
        rssService.deleteRssItemFromSaved(id);
    }
}
