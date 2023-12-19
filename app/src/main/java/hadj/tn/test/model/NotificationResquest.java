package hadj.tn.test.model;

public class NotificationResquest {
    private String title;
    private String message;
    private String token;

    public NotificationResquest(String title, String message, String token) {
        this.title = title;
        this.message = message;
        this.token = token;
    }

    public NotificationResquest(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
