package dao;

import entity.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOPost extends DBConnect {

    public Vector<Post> getAllBlogs() {
        Vector<Post> vector = new Vector<>();
        String sql = "SELECT * FROM Post";
        try{
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                vector.add(new Post(rs.getInt("post_id"), rs.getString("title"), rs.getString("summary"), rs.getString("thumbnail_url"), rs.getString("content"), rs.getBoolean("status")));
            }
            return vector;
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Post getBlogDetails(String postId) {
        String sql = "SELECT * FROM Post where post_id = ?";
        try{
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, postId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new Post(rs.getInt("post_id"), rs.getString("title"), rs.getString("summary"), rs.getString("thumbnail_url"), rs.getString("content"), rs.getBoolean("status"));
            }
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Vector<Post> getBlogsByKeyword(String query){
        Vector<Post> vector = new Vector<>();
        String sql = "SELECT * FROM Post WHERE title LIKE ?";
        try {
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, "%" + query + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                vector.add(new Post(rs.getInt("post_id"), rs.getString("title"), rs.getString("summary"), rs.getString("thumbnail_url"), rs.getString("content"), rs.getBoolean("status")));
            }
            return vector;
        } catch (Exception ex) {
            Logger.getLogger(DAOPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
