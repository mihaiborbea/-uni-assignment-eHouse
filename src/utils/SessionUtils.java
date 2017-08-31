package utils;

import beans.UserAccount;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;

public class SessionUtils {

    public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

    private static final String ATT_NAME_USER_EMAIL = "ATTRIBUTE_FOR_STORED_USER_EMAIL_IN_COOKIE";


    // Store Connection in request attribute.
    // (Information stored only exist during requests)
    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME_CONNECTION, conn);
    }

    // Get the Connection object has been stored in one attribute of the request.
    public static Connection getStoredConnection(ServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
        return conn;
    }

    // Store user info in Session.
    public static void storeLoggedUser(HttpSession session, UserAccount loggedUser) {

        // On the JSP can access ${loggedUser}
        session.setAttribute("loggedUser", loggedUser);
    }

    // Get the user information stored in the session.
    public static UserAccount getLoggedUser(HttpSession session) {
        UserAccount loggedUser = (UserAccount) session.getAttribute("loggedUser");
        return loggedUser;
    }

    // Store info in Cookie
    public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
        System.out.println("Store user cookie");
        Cookie cookieUserEmail = new Cookie(ATT_NAME_USER_EMAIL, user.getEmail());

        // 1 day (Convert to seconds)
        cookieUserEmail.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUserEmail);
    }

    public static String getUserCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_EMAIL.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // Delete cookie.
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserEmail = new Cookie(ATT_NAME_USER_EMAIL, null);

        // 0 seconds (Expires immediately)
        cookieUserEmail.setMaxAge(0);
        response.addCookie(cookieUserEmail);
    }

}
