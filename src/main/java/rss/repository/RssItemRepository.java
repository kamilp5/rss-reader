package rss.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rss.user.RssItem;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface RssItemRepository extends JpaRepository<RssItem, Long> {
    Optional<RssItem> getByUrl(String url);

    Page<RssItem> getAllByRssFeedId(Long id, Pageable pageable);

    boolean existsByDateAfterAndRssFeedId(Timestamp date, Long id);


    @Query(value = "SELECT * FROM rss_item r WHERE " +
            " r.date = (SELECT MAX(s.date) FROM rss_item s WHERE s.rss_feed_id = ?1) " +
            "AND r.rss_feed_id = ?1", nativeQuery = true)
    RssItem getNewestItemByRssFeedId(Long rssFeedId);
}
