package servlets;

import beans.UserAccount;
import utils.SessionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = { "/addPost"})
public class AddPostServlet extends HttpServlet {

    public AddPostServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward to /WEB-INF/views/registerView.jsp
        // Check User has logged on
        HttpSession session = request.getSession();
        UserAccount loggedUser = SessionUtils.getLoggedUser(session);
        // logged in
        if (loggedUser == null) {
            // Redirect to home
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/addPostView.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
