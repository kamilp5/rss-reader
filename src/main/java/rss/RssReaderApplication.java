package rss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import rss.utils.RssReader;

@SpringBootApplication
public class RssReaderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RssReaderApplication.class,args);
        RssReader rssReader = context.getBean(RssReader.class);
        rssReader.addFeed("https://rss.nytimes.com/services/xml/rss/nyt/World.xml");
//        SpringApplication.run(RssReaderApplication.class, args);
    }

}
