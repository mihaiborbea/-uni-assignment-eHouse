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

@WebServlet(urlPatterns = { "/doRegister" })
public class DoRegisterServlet extends HttpServlet {
    public DoRegisterServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confpassword = request.getParameter("confpassword");
        String phone = request.getParameter("phone");
        int adminsts = 0;

        UserAccount user = null;
        boolean hasError = false;
        String errorString = null;

        if (email == null || password == null || email.length() == 0 || password.length() == 0
                || fname == null || fname.length() == 0 || lname == null || lname.length() == 0
                || phone == null || phone.length() == 0 || confpassword == null || confpassword.length() == 0) {
            hasError = true;
            errorString = "All fields are mandatory";
        } else if(!password.equals(confpassword)){
            hasError = true;
            errorString = "Passwords do not match!";

        } else {
            Connection conn = SessionUtils.getStoredConnection(request);
            user = DatabaseUtils.findUser(conn, email);
            if (user != null) {
                hasError = true;
                errorString = "Email already in use!";
            }
        }

        // If error, forward to /WEB-INF/views/regiser.jsp
        if (hasError) {
            user = new UserAccount();
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setPhone(phone);
            // Store information in request attribute, before forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);
            // Forward to /WEB-INF/views/register.jsp
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerView.jsp");
            dispatcher.forward(request, response);
        }
        // If no error create user and redirect to home.
        else {
            Connection conn = SessionUtils.getStoredConnection(request);
            user = new UserAccount();
            user.setEmail(email);
            user.setPassword(password);
            user.setFirstName(fname);
            user.setLastName(lname);
            user.setPhone(phone);
            user.setAdmin(adminsts);
            DatabaseUtils.createUser(conn,user);
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
