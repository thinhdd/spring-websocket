package org.springframework.samples.portfolio.dto;

/**
 * Created by thinhdd on 10/18/2016.
 */
public class MessageDTO {
    String message;
    String title;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
