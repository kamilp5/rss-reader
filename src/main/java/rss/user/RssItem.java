package rss.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private RssFeed rssFeed;

    @Transient
    private boolean alreadySeen;
}
