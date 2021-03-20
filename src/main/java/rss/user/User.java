package rss.user;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<RssFeed> rssFeeds;

    public void addFeed(RssFeed rssFeed){
        getRssFeeds().add(rssFeed);
    }
}
