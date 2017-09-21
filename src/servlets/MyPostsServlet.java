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
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

@WebServlet(urlPatterns = { "/myPosts"})
public class MyPostsServlet extends HttpServlet {

    public MyPostsServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to /WEB-INF/views/loginView.jsp

        // Check User is logged in
        HttpSession session = request.getSession();
        UserAccount loggedUser = SessionUtils.getLoggedUser(session);
        if (loggedUser == null) {
            // Redirect to home
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("user", loggedUser);
            Connection conn = SessionUtils.getStoredConnection(request);
            ArrayList<Post> myPosts = new ArrayList<Post>(DatabaseUtils.queryPosts(conn, loggedUser.getID()));
            request.setAttribute("myPosts", myPosts);
            ArrayList<Image> imgs = new ArrayList<>();
            for(Post post : myPosts) {
                imgs.addAll(DatabaseUtils.queryImages(conn, post.getID()));
            }
            request.setAttribute("images", imgs);
            //Redirect to register
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/myPostsView.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
