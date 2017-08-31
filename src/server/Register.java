package server;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;

public class Register extends HttpServlet {

    @Resource(name = "jdbc/MySQLDb")
    private DataSource dbRes;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String fname = request.getParameter("fname");
        String lname = request.getParameter("lname");
        String email = request.getParameter("email");
        String password = request.getParameter("pass");
        String phone = request.getParameter("phone");

        try{

            java.sql.Connection myConn = utils.ConnectionUtils.getConnection();
            PreparedStatement ps = myConn.prepareStatement
                    ("insert into users(first_name, last_name, email, phone, password, admin) values(?,?,?,?,?,?)");

            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, password);
            ps.setString(6, "0");
            int i = ps.executeUpdate();

            if(i>0)
            {
                out.println("You are sucessfully registered");
            }

        }
        catch(Exception se)
        {
            se.printStackTrace();
        }

    }
}
