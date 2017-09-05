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
import java.sql.SQLException;

@WebServlet(urlPatterns = { "/doLogin" })
public class DoLoginServlet extends HttpServlet {
    public DoLoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean remember= "Y".equals(rememberMeStr);


        UserAccount user = null;
        boolean hasError = false;
        String errorString = null;

        if (email == null || password == null || email.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required email and password!";
        } else {
            Connection conn = SessionUtils.getStoredConnection(request);
            try {

                user = DatabaseUtils.findUser(conn, email, password);

                if (user == null) {
                    hasError = true;
                    errorString = "Email or password invalid";
                }
            } catch (Exception e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }

        // If error, forward to /WEB-INF/views/login.jsp
        if (hasError) {
            user = new UserAccount();
            user.setEmail(email);
            user.setPassword(password);
            // Store information in request attribute, before forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);
            // Forward to /WEB-INF/views/login.jsp
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
            dispatcher.forward(request, response);
        }
        // If no error Store user information in Session And redirect to home page.
        else {
            HttpSession session = request.getSession();
            SessionUtils.storeLoggedUser(session, user);

            // If user checked "Remember me".
            if(remember)  {
                SessionUtils.storeUserCookie(response,user);
            }
            // Else delete cookie.
            else  {
                SessionUtils.deleteUserCookie(response);
            }
            // Redirect to home page.
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}