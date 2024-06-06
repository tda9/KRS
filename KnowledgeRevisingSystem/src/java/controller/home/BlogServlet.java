package controller.home;

import dao.DAOPost;
import entity.Post;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Vector;

/**
 *
 * @author VKHOANG
 */
public class BlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Get DAO layer
        DAOPost daopost = new DAOPost();
        //Get the blogId for viewing details, or the query for searching keywords
        String blogId = request.getParameter("blogId");
        String query = request.getParameter("query");

        //Check if id of the blog is specified to view blog details
        if (blogId != null && !blogId.isBlank()) {
            //Get blog detail and redirect to Blog Details page
            Post post = daopost.getBlogDetails(blogId);
            request.setAttribute("blogDetail", post);
            request.getRequestDispatcher("blogDetails.jsp").forward(request, response);
        } else if (query != null && !query.isBlank()) {
            //Query and display blogs that contains keyword
            Vector<Post> vector = daopost.getBlogsByKeyword(query);
            request.setAttribute("query", query);
            request.setAttribute("blogList", vector);
            request.getRequestDispatcher("blogList.jsp").forward(request, response);
        } else {
            //Get all blogs and return to Blog List page
            Vector<Post> vector = daopost.getAllBlogs();
            //Pass to the view
            request.setAttribute("blogList", vector);
            request.getRequestDispatcher("blogList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
