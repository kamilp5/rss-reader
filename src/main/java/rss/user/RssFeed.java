package rss.user;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class RssFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;
    private String imageUrl;

    @OneToMany(mappedBy = "rssFeed", cascade = CascadeType.ALL)
    private List<RssItem> rssItems;
}
