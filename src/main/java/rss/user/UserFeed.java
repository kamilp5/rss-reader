package rss.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class UserFeed {

    public static final Timestamp DEFAULT_OPENED_SEEN = Timestamp.valueOf(LocalDateTime.now().minusYears(1));

    @EmbeddedId
    private UserFeedId id;
    private Timestamp lastOpenedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("rssFeedId")
    private RssFeed rssFeed;

    public UserFeed(User user, RssFeed rssFeed) {
        this.lastOpenedDate = DEFAULT_OPENED_SEEN;
        this.user = user;
        this.rssFeed = rssFeed;
        this.id = new UserFeedId(user.getId(),rssFeed.getId());
    }
}

