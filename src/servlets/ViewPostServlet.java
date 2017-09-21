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

@WebServlet(urlPatterns = { "/viewPost" })
public class ViewPostServlet extends HttpServlet {

    public ViewPostServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to /WEB-INF/views/homeView.jsp

        HttpSession session = request.getSession();
        UserAccount loggedUser = SessionUtils.getLoggedUser(session);
        Integer postID = Integer.parseInt(request.getParameter("postID"));
        Post post = new Post();

        Connection conn = SessionUtils.getStoredConnection(request);
        post = DatabaseUtils.findPost(conn, postID);
        request.setAttribute("post", post);
        ArrayList<Image> images = new ArrayList<>(DatabaseUtils.queryImages(conn, postID));
        String imgPath = images.get(0).getPath();
        String phone = DatabaseUtils.findUser(conn,post.getUserID()).getPhone();
        request.setAttribute("image", imgPath);
        request.setAttribute("phone", phone);

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/viewPostView.jsp");
        dispatcher.forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
