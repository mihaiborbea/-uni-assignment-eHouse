package utils;

import beans.Post;
import beans.UserAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseUtils {

    public static UserAccount findUser(Connection conn, String email, String password) {

        String sql = "Select u.id," +
                "u.first_name," +
                "u.last_name, " +
                "u.email," +
                "u.phone," +
                " u.password " +
                "from users u" +
                " where u.email = ? and u.password = ?";

        try (PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {


            pstm.setString(1, email);
            pstm.setString(2, password);
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
        catch(SQLException e){
            System.out.println(e.toString());
        }
        return null;
    }

    public static void addUser (Connection conn, UserAccount user) {
       String sql = "Insert into users(first_name,last_name,email,phone,password,admin) values (?,?,?,?,?,?)";

       try(PreparedStatement pstm = conn.prepareStatement(sql)) {

           int useradm = 0;
           pstm.setString(1, user.getFirstName());
           pstm.setString(2, user.getLastName());
           pstm.setString(3, user.getEmail());
           pstm.setString(4, user.getPhone());
           pstm.setString(5, user.getPassword());
           pstm.setInt(6, useradm);
           pstm.executeUpdate();
       }
       catch (SQLException e) {
           System.out.println(e.toString());
       }
   }

    public static void updateUser (Connection conn, UserAccount user) {
        String sql = "UPDATE users SET" +
                     "first_name = ?," +
                     "last_name = ?," +
                     "phone = ?," +
                     "password = ?," +
                     "admin = ?" +
                     "where email = ?";

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

    public static void deleteUser (Connection conn, String email) {
        String sql = "Delete from users where email = ?";

        try(PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, email);
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

}
