package rss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rss.user.RssFeed;

public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {
}
