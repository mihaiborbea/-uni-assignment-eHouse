package filters;

import utils.ConnectionUtils;
import utils.SessionUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Map;

@WebFilter(filterName = "jdbcFilter", urlPatterns = { "/*" })
public class JDBCFilter implements Filter {

    public JDBCFilter() {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }


    // Check the target of the request is a servlet?
    private boolean needJDBC(HttpServletRequest request) {
        System.out.println("JDBC Filter");
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();

        String urlPattern = servletPath;

        if (pathInfo != null) {
            urlPattern = servletPath + "/*";
        }

        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
                .getServletRegistrations();


        // Collection of all servlet in your webapp.
        Collection<? extends ServletRegistration> values = servletRegistrations.values();
        for (ServletRegistration sr : values) {
            Collection<String> mappings = sr.getMappings();
            if (mappings.contains(urlPattern)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        // Only open connections for the special requests need connection. (For example, the path to the servlet, JSP, ..)
        if (this.needJDBC(req)) {

            System.out.println("Open Connection for: " + req.getServletPath());

            Connection conn = null;
            try {
                conn = ConnectionUtils.getConnection();
                conn.setAutoCommit(false);
                SessionUtils.storeConnection(request, conn);
                chain.doFilter(request, response);
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                ConnectionUtils.rollbackQuietly(conn);
//                throw new ServletException();
            } finally {
                ConnectionUtils.closeQuietly(conn);
            }
        }
        // With commons requests (images, css, html,etc) dont open the connection.
        else {
            chain.doFilter(request, response);
        }

    }

}
