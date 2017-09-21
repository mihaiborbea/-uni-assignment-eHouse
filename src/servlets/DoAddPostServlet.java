package servlets;

import beans.Image;
import beans.UserAccount;
import beans.Post;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import utils.DatabaseUtils;
import utils.SessionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@WebServlet(urlPatterns = { "/doAddPost"})
@MultipartConfig
public class DoAddPostServlet extends HttpServlet {

    public DoAddPostServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String title = request.getParameter("title");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String sprice = request.getParameter("price");
        Part filePart = request.getPart("image");
        Integer price;
        if(sprice == null || sprice.equals("")) { price = 0; }
        else { price = Integer.parseInt(sprice); }
        int views = 0;

        String errorString = null;
        boolean hasErrorr = false;
        Post post = new Post();

        if (title == null || address == null || title.length() == 0 || address.length() == 0 || city == null ||
                city.length() == 0 || country == null || country.length() == 0 || price == 0) {
            hasErrorr = true;
            errorString = "All fields are mandatory!";
        }


        HttpSession session = request.getSession();
        UserAccount loggedUser = SessionUtils.getLoggedUser(session);
        if (hasErrorr) {
            post.setUserID(loggedUser.getID());
            post.setTitle(title);
            post.setAddress(address);
            post.setCity(city);
            post.setCountry(country);
            post.setPrice(price);
            // Store information in request attribute, before forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("post", post);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/addPostView.jsp");
            dispatcher.forward(request, response);
        }
        // If no error create post and redirect to home.
        else {
            Connection conn = SessionUtils.getStoredConnection(request);
            post.setUserID(loggedUser.getID());
            post.setTitle(title);
            post.setAddress(address);
            post.setCity(city);
            post.setCountry(country);
            post.setPrice(price);
            post.setViews(views);
            Date date = new Date();
            post.setDate(date);
            DatabaseUtils.addPost(conn,post,post.getUserID());
            int postID = DatabaseUtils.findPost(conn, loggedUser.getID(), post.getTitle()).getID();

            //image upload
            File path = new File("/Users/borbe/IdeaProjects/eHouse_Dev/web/uploads");

            String file = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fileName = FilenameUtils.removeExtension(file) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(file);
            File savedFile = new File(path, fileName);
            String absolutePath = savedFile.getAbsolutePath();
            filePart.write(absolutePath);

            Image img = new Image();
            img.setPostID(postID);
            img.setPath(fileName);
            DatabaseUtils.addImage(conn, img);

            response.sendRedirect(request.getContextPath() + "/myPosts");
        }

//        //process only if its multipart content
//        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//        if (isMultipart) {
//            FileItemFactory factory = new DiskFileItemFactory();
//            ServletFileUpload upload = new ServletFileUpload(factory);
//
//            try {
//                // Parse the request
//                List items = upload.parseRequest(request);
//                Iterator iterator = items.iterator();
//                while (iterator.hasNext()) {
//                    FileItem item = (FileItem) iterator.next();
//                    if (!item.isFormField()) {
//                        String fileName = item.getName();
//                        String root = getServletContext().getRealPath("/");
//                        File path = new File(root + "/uploads");
//                        if (!path.exists()) {
//                            boolean status = path.mkdirs();
//                        }
//
//                        File uploadedFile = new File(path + "/" + fileName);
//                        System.out.println(uploadedFile.getAbsolutePath());
//                        item.write(uploadedFile);
//                    }
//                }
//            } catch (FileUploadException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }
}
