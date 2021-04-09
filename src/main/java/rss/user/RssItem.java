package rss.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
public class RssItem {
    public static final int VALID_DAYS_OLD = 15;

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

    @ManyToMany(mappedBy = "seenRssItems")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<User> userSeen;

    @Transient
    private boolean alreadySeen;
}
