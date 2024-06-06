package entity;

/**
 * 
 * @author VKHOANG
 */
public class Post_Category {
    private int post_id, setting_id;
    private String category_name;

    public Post_Category() {
    }

    public Post_Category(int post_id, int setting_id, String category_name) {
        this.post_id = post_id;
        this.setting_id = setting_id;
        this.category_name = category_name;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getSetting_id() {
        return setting_id;
    }

    public void setSetting_id(int setting_id) {
        this.setting_id = setting_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    
    
}
