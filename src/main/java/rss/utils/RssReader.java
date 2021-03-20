package rss.utils;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import rss.user.RssFeed;
import rss.user.RssItem;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RssReader {

    public RssFeed createRssFeed(String url) {
        SyndFeed feedData = getFeedData(url);
        RssFeed rssFeed = new RssFeed();
        rssFeed.setTitle(feedData.getTitle());
        rssFeed.setUrl(url);
        if(feedData.getImage() != null) {
            rssFeed.setImageUrl(feedData.getImage().getUrl());
        }
        rssFeed.setRssItems(getFeedItems(rssFeed));
        return rssFeed;
    }

    public List<RssItem> getFeedItems(RssFeed rssFeed) {
        SyndFeed feedData = getFeedData(rssFeed.getUrl());
        ArrayList<RssItem> items = new ArrayList<>();
        for (SyndEntry entry : feedData.getEntries()) {
            RssItem item = buildRssItem(entry,rssFeed);
            items.add(item);
        }
        return items;
    }

    private RssItem buildRssItem(SyndEntry entry,RssFeed rssFeed) {
        RssItem item = new RssItem();
        item.setTitle(entry.getTitle());
        item.setDescription(extractDescription(entry));
        item.setDate(new Timestamp(entry.getPublishedDate().getTime()));
        item.setUrl(entry.getLink());
        item.setRssFeed(rssFeed);

        String rx = "https?:/(?:/[^/]+)+\\.(?:jpg|gif|png|jpeg)";
        Pattern pattern = Pattern.compile(rx);
        Matcher matcher = pattern.matcher(entry.toString());
        if(matcher.find()) {
            item.setImageUrl(matcher.group());
        }
        return item;
    }

    private String extractDescription(SyndEntry entry){
        if(entry.getDescription() == null){
            return "";
        }
        Document doc = Jsoup.parse(entry.getDescription().getValue());
        String d = doc.getAllElements().text();
        if(d.length() > 999) {
            d = d.substring(0, 998);
        }
        return d;
    }

    private SyndFeed getFeedData(String url) {
        try {
            SyndFeedInput input = new SyndFeedInput();
            return input.build(new XmlReader(new URL(url)));
        } catch (Exception e) {
            throw new RssNotFoundException("Couldn't find rss feed with given url: " + url, e);
        }
    }
}