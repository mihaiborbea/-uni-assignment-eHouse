package utils;

import beans.Image;
import beans.Post;
import beans.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseUtils {

    public static UserAccount findUser(Connection conn, String email, String password) {

        String sql = "Select id,first_name,last_name,email,phone,password,admin from users where email =? and password =?;";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, email);
            pstm.setString(2, password);

            try(ResultSet rs = pstm.executeQuery()){
                if (rs.next()) {
                    UserAccount user = new UserAccount();
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setID(rs.getInt("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setPhone(rs.getString("phone"));
                    user.setAdmin(rs.getInt("admin"));
                    return user;
                }
            }
        }
        catch(SQLException e){
            System.out.println(e.toString());
        }
        return null;
    }

    public static UserAccount findUser(Connection conn, String email) {

        String sql = "Select id,first_name,last_name,email,phone,password,admin from users where email = ?;";

        try (PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, email);

            try(ResultSet rs = pstm.executeQuery()){
                if (rs.next()) {
                    UserAccount user = new UserAccount();
                    user.setEmail(email);
                    user.setPassword(rs.getString("password"));
                    user.setID(rs.getInt("id"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setPhone(rs.getString("phone"));
                    user.setAdmin(rs.getInt("admin"));
                    return user;
                }
            }
        }
        catch(SQLException e){
            System.out.println(e.toString());
        }
        return null;
    }

    public static void createUser (Connection conn, UserAccount user) {
       String sql = "Insert into users(first_name,last_name,email,phone,password,admin) values (?,?,?,?,?,?)";

       try(PreparedStatement pstm = conn.prepareStatement(sql)) {

           pstm.setString(1, user.getFirstName());
           pstm.setString(2, user.getLastName());
           pstm.setString(3, user.getEmail());
           pstm.setString(4, user.getPhone());
           pstm.setString(5, user.getPassword());
           pstm.setInt(6, user.getAdmin());
           pstm.executeUpdate();
       }
       catch (SQLException e) {
           System.out.println(e.toString());
       }
   }

    public static void updateUser (Connection conn, UserAccount user) {
        String sql = "UPDATE users SET first_name = ?,last_name = ?,phone = ?,password = ?,admin = ? where email = ? ;d";

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {

            int useradm = 0;
            pstm.setString(1, user.getFirstName());
            pstm.setString(2, user.getLastName());
            pstm.setString(3, user.getPhone());
            pstm.setString(4, user.getPassword());
            pstm.setFloat(5, useradm);
            pstm.setString(6, user.getEmail());
            pstm.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public static void deleteUser (Connection conn, UserAccount user) {
        String sql = "Delete from users where email = ?";

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, user.getEmail());
            pstm.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.toString());
        }
    }

    public static void addPost (Connection conn, Post post, int userID) {
        String sql = "Insert into post(user_id,date,views,title,address,price,city,country) values (?,?,?,?,?,?,?,?)";

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            pstm.setInt(1, userID);
            pstm.setString(2, sdf.format(post.getDate()));
            pstm.setInt(3, post.getViews());
            pstm.setString(4, post.getTitle());
            pstm.setString(5, post.getAddress());
            pstm.setInt(6, post.getPrice());
            pstm.setString(7, post.getCity());
            pstm.setString(8, post.getCountry());
            pstm.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public static void updatePost (Connection conn, Post post) {
        String sql = "Update post SET" +
                     "title = ?," +
                     "address = ?," +
                     "price = ?," +
                     "city = ?," +
                     "country = ?" +
                     "where id = ?";

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, post.getTitle());
            pstm.setString(2, post.getAddress());
            pstm.setInt(3, post.getPrice());
            pstm.setString(4, post.getCity());
            pstm.setString(5, post.getCountry());
            pstm.setInt(6, post.getID());
            pstm.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public static Post findPost (Connection conn, int postID) {
        String sql = "Select p.id, p.user_id, p.date, p.views, p.title, p.address, p.price, p.city, p.country" +
                     " from posts p" +
                     " where id=?";

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, postID);
            try(ResultSet rs = pstm.executeQuery()){
                if (rs.next()) {
                    Post post = new Post();
                    post.setID(postID);
                    post.setUserID(rs.getInt("user_id"));
                    post.setDate(rs.getTimestamp("date"));
                    post.setViews(rs.getInt("views"));
                    post.setTitle(rs.getString("title"));
                    post.setAddress(rs.getString("address"));
                    post.setPrice(rs.getInt("price"));
                    post.setCity(rs.getString("city"));
                    post.setCountry(rs.getString("country"));
                    return post;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static void deletePost (Connection conn, Post post) {
        String sql = "Delete from post where id = ?";

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, post.getID());
            pstm.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e.toString());
        }
    }

    public static List<Post> queryPosts(Connection conn){
        String sql = "Select p.id, p.user_id, p.date, p.views, p.title, p.address, p.price, p.city, p.country" +
                     "from posts p";
        List<Post> list = new ArrayList<Post>();

        try(PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int userid = rs.getInt("user_id");
                Date date = rs.getTimestamp("date");
                int views = rs.getInt("views");
                String title = rs.getString("title");
                String address = rs.getString("address");
                int price = rs.getInt("price");
                String city = rs.getString("city");
                String country = rs.getString("country");

                Post post = new Post();
                post.setID(id);
                post.setUserID(userid);
                post.setDate(date);
                post.setViews(views);
                post.setTitle(title);
                post.setAddress(address);
                post.setPrice(price);
                post.setCity(city);
                post.setCountry(country);
                list.add(post);
            }
        }
        catch(SQLException e){
            System.out.println(e.toString());
        }
        return list;
    }

    public static void addImage (Connection conn, Image image, int postID) {
        String sql = "Insert into image(path,post_id) values (?,?)";

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, image.getPath());
            pstm.setInt(2, postID);
            pstm.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public static void deleteImage (Connection conn, Image image) {
        String sql = "Delete from image where id = ?";

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, image.getID());
            pstm.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public static List<Image> queryImage(Connection conn){
        String sql = "Select i.id, i.path, i.post_id" +
                     "from images i";
        List<Image> list = new ArrayList<Image>();

        try(PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String path = rs.getString("path");
                int postid = rs.getInt("post_id");

                Image image = new Image();
                image.setID(id);
                image.setPath(path);
                image.setPostID(postid);
                list.add(image);
            }
        }
        catch(SQLException e) {
            System.out.println(e.toString());
        }
        return list;
    }
}
