package rss.dto;

import org.springframework.stereotype.Component;
import rss.user.UserFeed;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RssFeedMapper {

    public RssFeedDto toDto(UserFeed userFeed) {
        RssFeedDto dto = new RssFeedDto();
        dto.setId(userFeed.getId().getRssFeedId());
        dto.setTitle(userFeed.getRssFeed().getTitle());
        dto.setUrl(userFeed.getRssFeed().getUrl());
        dto.setImageUrl(userFeed.getRssFeed().getImageUrl());
        dto.setLastOpenedDate(userFeed.getLastOpenedDate());
        return dto;
    }

    public List<RssFeedDto> toDtoList(List<UserFeed> userFeeds) {
        return userFeeds.stream().map(this::toDto).collect(Collectors.toList());
    }

}
