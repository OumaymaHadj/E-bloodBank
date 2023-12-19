package hadj.tn.test.model;

public class FCMToken {

    private int token_id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;
    private String token;

    public FCMToken(int token_id, String username, String token) {
        this.token_id = token_id;
        this.username = username;
        this.token = token;
    }

    public FCMToken() {
    }

    public int getToken_id() {
        return token_id;
    }

    public void setToken_id(int token_id) {
        this.token_id = token_id;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
