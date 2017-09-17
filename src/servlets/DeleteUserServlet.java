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

    private void _deleteUser(Integer userID, HttpServletRequest request){
        Connection conn = SessionUtils.getStoredConnection(request);
        ArrayList<Post> posts = new ArrayList<>(DatabaseUtils.queryPosts(conn, userID));
        //delete user's posts
        if(posts != null)
            for(Post post : posts) {
                ArrayList<Image> images = new ArrayList<>(DatabaseUtils.queryImages(conn, post.getID()));
                //delete post's images
                if (images != null)
                    for(Image img : images) {
                        DatabaseUtils.deleteImage(conn, img);
                    }
                DatabaseUtils.deletePost(conn, post);
            }
        //delete user in DB
        DatabaseUtils.deleteUser(conn,DatabaseUtils.findUser(conn,userID));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to /WEB-INF/views/homeView.jsp

        HttpSession session = request.getSession();
        UserAccount loggedUser = SessionUtils.getLoggedUser(session);
        Integer deleteUserID = Integer.parseInt(request.getParameter("userID"));

        // not logged in
        if (loggedUser == null) {
            // Redirect to login
            response.sendRedirect(request.getContextPath() + "/login");
        }
        //check if user to delete is the logged user
        else if(deleteUserID == loggedUser.getID()) {
            _deleteUser(deleteUserID, request);
            // delete cookie
            SessionUtils.deleteUserCookie(response);
            //delete session
            session.invalidate();
            //redirect
            response.sendRedirect(request.getContextPath() + "/login");
        }
        //if the the user to delete is not logged user check for admin privileges
        else {
            if(loggedUser.getAdmin() == 1) {
                _deleteUser(deleteUserID, request);
                response.sendRedirect(request.getContextPath() + "/home");
            }
            else
                response.sendRedirect(request.getContextPath() + "/home");
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}