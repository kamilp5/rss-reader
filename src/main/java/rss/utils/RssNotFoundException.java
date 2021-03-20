package rss.utils;

public class RssNotFoundException extends RuntimeException{

    public RssNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
