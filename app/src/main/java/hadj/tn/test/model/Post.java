package hadj.tn.test.model;

public class Post {
    private int pubId;
    private User appUserId;
    private String contenu;
    private String comments;
    private String image;
    private String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Post(int pubId, User userId, String contenu, String comments, String image) {
        this.pubId = pubId;
        this.appUserId = userId;
        this.contenu = contenu;
        this.comments = comments;
        this.image = image;
    }

    public Post(User appUserId, String contenu) {
        this.appUserId = appUserId;
        this.contenu = contenu;
    }

    public Post() {
    }

    public int getPubId() {
        return pubId;
    }

    public void setPubId(int pubId) {
        this.pubId = pubId;
    }

    public User getUserId() {
        return appUserId;
    }

    public void setUserId(User userId) {
        this.appUserId = userId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
