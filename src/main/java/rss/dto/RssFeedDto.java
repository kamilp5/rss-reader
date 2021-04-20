package rss.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class RssFeedDto {

    private Long id;
    private String title;
    private String url;
    private String imageUrl;
    private Timestamp lastOpenedDate;
    private boolean hasNewItems;
}
