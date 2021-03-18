package rss.user;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class RssItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String url;
    private Timestamp date;
    @ManyToOne
    @JoinColumn(name = "rssFeed_id")
    private RssFeed rssFeed;
}
