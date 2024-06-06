package entity;

/**
 * 
 * @author VKHOANG
 */
public class Post {
    private int post_id;
    private String title, summary, thumbnail_url, content;
    private boolean status;

    public Post() {
    }

    public Post(int post_id, String title, String summary, String thumbnail_url, String content, boolean status) {
        this.post_id = post_id;
        this.title = title;
        this.summary = summary;
        this.thumbnail_url = thumbnail_url;
        this.content = content;
        this.status = status;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumnail_url(String thumnail_url) {
        this.thumbnail_url = thumnail_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
