package rss.exception;

public class RssNotFoundException extends RuntimeException{
    public RssNotFoundException(Long id) {
        super("Rss Feed with id: " + id + " not found");
    }

    public RssNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
