package rss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rss.Model.RssFeed;

import java.util.Optional;

public interface RssFeedRepository extends JpaRepository<RssFeed, Long> {
    Optional<RssFeed> findByUrl(String url);
}
