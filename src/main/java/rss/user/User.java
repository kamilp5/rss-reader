package rss.user;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
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

    public void addFeed(RssFeed rssFeed){
        UserFeed userFeed = new UserFeed(this,rssFeed);
        getRssFeeds().add(userFeed);
    }
}
