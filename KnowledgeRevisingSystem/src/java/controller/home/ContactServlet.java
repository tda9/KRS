/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dao.DAOContact;
import entity.Contact;
import entity.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;

/**
 *
 * @author ADMIN
 */
public class ContactServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DAOContact daoContact = new DAOContact();

        String action = request.getParameter("action");
        HttpSession session = request.getSession(true);

        //Get name from session
        Object name = session.getAttribute("name");

        //If not guest
        if (name != null) {
            //user submit form
            if (action == null) {
                action = "listInfo";
            }
        } else {
            if (action == null) {
                action = "guestContact";
            }
        }

        //Replace info of user to Placeholder of form
        if (action.equals("listInfo")) {
            //Get user by user name or email
            Vector<User> vectorUser = daoContact.getUserDetail("select * from user where user_name = '" + name + "' or email = '" + name + "'");
            User user = vectorUser.get(0);
            request.setAttribute("user", user);
            RequestDispatcher dis = request.getRequestDispatcher("contactAdmin.jsp");
            dis.forward(request, response);
        } else if (action.equals("createContactMessage")) {
            //submit form contact admin
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String subject = request.getParameter("subject");
            String phone = request.getParameter("phone");
            String message = request.getParameter("message");
            int settingId = 1;
            boolean status = true;           
            //contact id auto incremental in db
            int n = daoContact.createContact(new Contact(0, fullName, email, phone, message, settingId, status, subject));
            response.sendRedirect("ContactServlet");
        } else if (action.equals("guestContact")) {
            RequestDispatcher dis = request.getRequestDispatcher("contactAdmin.jsp");
            dis.forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
