package rss.dto;

import org.springframework.stereotype.Component;
import rss.Model.RssItem;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RssItemMapper {

    public RssItemDto toDto(RssItem rssItem) {
        return RssItemDto.builder()
                .id(rssItem.getId())
                .title(rssItem.getTitle())
                .date(rssItem.getDate())
                .description(rssItem.getDescription())
                .imageUrl(rssItem.getImageUrl())
                .url(rssItem.getUrl())
                .alreadySeen(rssItem.isAlreadySeen())
                .build();
    }

    public List<RssItemDto> toDtoList(List<RssItem> items) {
        return items.stream().map(this::toDto).collect(Collectors.toList());
    }
}
