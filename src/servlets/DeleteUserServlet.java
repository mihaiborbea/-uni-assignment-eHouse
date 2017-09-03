package servlets;

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
            //delete session
            session.invalidate();
            //delete user in DB
            Connection conn = SessionUtils.getStoredConnection(request);
            String deleteUserEmail = (String) request.getParameter("user");
            DatabaseUtils.deleteUser(conn,DatabaseUtils.findUser(conn,deleteUserEmail));
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