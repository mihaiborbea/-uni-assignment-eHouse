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

@WebServlet(urlPatterns = { "/home"})
public class HomeServlet extends HttpServlet {

    public HomeServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to /WEB-INF/views/homeView.jsp
        Connection conn = SessionUtils.getStoredConnection(request);
        ArrayList<Post> myPosts = new ArrayList<Post>(DatabaseUtils.queryPosts(conn));
        request.setAttribute("myPosts", myPosts);
        ArrayList<Image> imgs = new ArrayList<>();
        for (Post post : myPosts) {
            imgs.addAll(DatabaseUtils.queryImages(conn, post.getID()));
        }
        request.setAttribute("images", imgs);
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}