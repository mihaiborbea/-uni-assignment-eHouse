package servlets;

import beans.Image;
import beans.Post;
import beans.UserAccount;
import utils.DatabaseUtils;
import utils.SessionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = { "/deleteUser" })
public class DeleteUserServlet extends HttpServlet {

    public DeleteUserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserAccount loggedUser = SessionUtils.getLoggedUser(session);
        // not logged in
        if (loggedUser == null) {
            // Redirect to home
            response.sendRedirect(request.getContextPath() + "/login");
        }
        else {
            //delete cookie
            SessionUtils.deleteUserCookie(response);
            Connection conn = SessionUtils.getStoredConnection(request);
            String deleteUserEmail = (String) request.getParameter("user");
            int userID = DatabaseUtils.findUser(conn,deleteUserEmail).getID();
            List<Post> posts = new ArrayList<Post>();
            posts = DatabaseUtils.queryPosts(conn, userID);
            //delete user's posts
            if(posts != null)
                for(Post post : posts) {
                    List<Image> images = new ArrayList<Image>();
                    images = DatabaseUtils.queryImages(conn, post.getID());
                    //delete user's post images
                    if (images != null)
                        for(Image img : images) {
                            DatabaseUtils.deleteImage(conn, img);
                        }
                    DatabaseUtils.deletePost(conn, post);
                }
            //delete user in DB
            DatabaseUtils.deleteUser(conn,DatabaseUtils.findUser(conn,deleteUserEmail));
            //delete session
            session.invalidate();
            //redirect to home
            response.sendRedirect(request.getContextPath() + "/home");
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}