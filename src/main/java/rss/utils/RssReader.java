package rss.utils;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Component;
import rss.user.RssFeed;

import java.net.URL;

@Component
public class RssReader {

    public void addFeed(String url){
        SyndFeed result;
        try {
            SyndFeedInput input = new SyndFeedInput();
            result = input.build(new XmlReader(new URL(url)));
        }catch (Exception e){
            e.printStackTrace();
            return ;
        }
        RssFeed rssFeed = new RssFeed();
        rssFeed.setTitle(result.getTitle());
        rssFeed.setUrl(url);
        rssFeed.setImageUrl(result.getImage().getUrl());
        System.out.println(rssFeed);
//        System.out.println(result);
    }
}