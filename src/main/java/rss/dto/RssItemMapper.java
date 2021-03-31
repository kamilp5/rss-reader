package rss.dto;

import org.springframework.stereotype.Component;
import rss.user.RssItem;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RssItemMapper {

    public RssItemDto toDto(RssItem rssItem) {
        RssItemDto dto = new RssItemDto();
        dto.setId(rssItem.getId());
        dto.setTitle(rssItem.getTitle());
        dto.setDate(rssItem.getDate());
        dto.setDescription(rssItem.getDescription());
        dto.setImageUrl(rssItem.getImageUrl());
        dto.setUrl(rssItem.getUrl());
        dto.setAlreadySeen(rssItem.isAlreadySeen());
        return dto;
    }

    public List<RssItemDto> toDtoList(List<RssItem> items) {
        return items.stream().map(this::toDto).collect(Collectors.toList());
    }
}
