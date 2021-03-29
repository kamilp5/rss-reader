package rss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rss.user.RssItem;

import java.util.List;
import java.util.Optional;

public interface RssItemRepository extends JpaRepository<RssItem, Long> {
    Optional<RssItem> getByUrl(String url);

    List<RssItem> getAllByRssFeedId(Long id);
}
