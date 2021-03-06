package rss.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class RssItemDto {

    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String url;
    private Timestamp date;
    private boolean alreadySeen;
}
