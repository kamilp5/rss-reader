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
    Optional <RssItem> getNewestItemByRssFeedId(Long rssFeedId);

    @Query(value = "SELECT * FROM rss_item i " +
            "LEFT JOIN users_saved_rss_items s ON s.saved_rss_items_id = i.id " +
            "WHERE s.user_id = ?1",
            nativeQuery = true)
    Page<RssItem> getUserSavedRssItems(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM rss_item i " +
            "LEFT JOIN users_saved_rss_items s ON s.saved_rss_items_id = i.id " +
            "WHERE s.user_id = ?1 AND s.saved_rss_items_id = ?2",
            nativeQuery = true)
    Optional<RssItem> isRssItemInUserSaved(Long userId, Long rssItemId);


    @Query(value = "SELECT * FROM rss_item i RIGHT JOIN users_saved_rss_items s ON s.saved_rss_items_id = i.id;",
            nativeQuery = true)
    List<RssItem> getAllSavedItems();

    List<RssItem> getAllByDateBefore(Timestamp timestamp);
}
