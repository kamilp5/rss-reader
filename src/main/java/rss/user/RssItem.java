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
    @Column(length = 999)
    private String description;
    private String imageUrl;
    private String url;
    private Timestamp date;
    @ManyToOne
    private RssFeed rssFeed;
}
