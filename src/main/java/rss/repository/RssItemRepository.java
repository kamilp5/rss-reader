package rss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rss.user.RssItem;

public interface RssItemRepository extends JpaRepository<RssItem, Long> {
}
