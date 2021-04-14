package rss.user;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<UserFeed> rssFeeds = new ArrayList<>();

    @ManyToMany
    private List<RssItem> seenRssItems;

    @ManyToMany
    private List<RssItem> savedRssItems;

    public void addFeed(RssFeed rssFeed){
        UserFeed userFeed = new UserFeed(this,rssFeed);
        getRssFeeds().add(userFeed);
    }

    public void removeFeed(Long id){
        getRssFeeds().removeIf(f -> f.getRssFeed().getId().equals(id));
    }

    public void addSeenRssItems(List<RssItem> items){
        getSeenRssItems().addAll(items);
    }
}
