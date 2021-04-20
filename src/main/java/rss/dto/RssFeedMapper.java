package rss.dto;

import org.springframework.stereotype.Component;
import rss.Model.UserFeed;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RssFeedMapper {

    public RssFeedDto toDto(UserFeed userFeed) {
        return RssFeedDto.builder()
                .id(userFeed.getId().getRssFeedId())
                .title(userFeed.getRssFeed().getTitle())
                .url(userFeed.getRssFeed().getUrl())
                .imageUrl(userFeed.getRssFeed().getImageUrl())
                .lastOpenedDate(userFeed.getLastOpenedDate())
                .build();
    }

    public List<RssFeedDto> toDtoList(List<UserFeed> userFeeds) {
        return userFeeds.stream().map(this::toDto).collect(Collectors.toList());
    }

}
