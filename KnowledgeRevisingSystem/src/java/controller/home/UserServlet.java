/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dao.DAOUser;
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
public class UserServlet extends HttpServlet {

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
        HttpSession session = request.getSession(true);

        //initialize DAO
        DAOUser daoUser = new DAOUser();

        //Get user name from session
        String name = (String) session.getAttribute("name");
        User user = new User();

        //if logged ( session has name )
        if (name != null) {
            Vector<User> vectorUser = daoUser.getAll("select * from user where user_name = '" + name + "' or email = '" + name + "'");
            user = vectorUser.get(0);
        }

        //execute list all users including null state action
        String action = request.getParameter("action");
        if (action == null) {
            action = "listAll";
        }
        System.out.println(action);
        if (action.equals("login")) {
            name = request.getParameter("name");
            String pass = request.getParameter("password");     
           
            if (daoUser.login(name, pass)) {
                session.setAttribute("name", name);
                response.sendRedirect("UserServlet?action=displayFollowByRole");
                return;

            } else {
                request.setAttribute("error", "Username or password is incorrected!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else if (action.equals("forgotpassword")) {
            String email = request.getParameter("email");
            Vector<User> result = daoUser.getAll("select * from user where email = '" + email + "'");

            if (result.isEmpty()) {
                request.setAttribute("error", "Invalid email");
                request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            }
        } else if (action.equals("register")) {
            String fullname = request.getParameter("fullname");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
            int role = Integer.parseInt(request.getParameter("role"));

            if (!daoUser.validateRegisterAccount(username, email, phone, request)) {
                request.setAttribute("fullname", fullname);
                request.setAttribute("username", username);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("gender", gender);
                request.setAttribute("password", password);
                request.setAttribute("passwordConfirm", password);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                User registerAccount = new User(role, phone, email, gender, fullname, username, password, 1, "", "");
                daoUser.insert(registerAccount);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } else if (action.equals("listUserProfile")) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("userProfile.jsp").forward(request, response);

        } else if (action.equals("updateProfile")) {
            //Update profile page
            String submit = request.getParameter("submit");
            //Submit != null
            if (submit != null) {
                String fullName = request.getParameter("fullName");
                boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String note = request.getParameter("note");
                user.setFullName(fullName);
                user.setGender(gender);
                user.setEmail(email);
                user.setPhone(phone);
                user.setNote(note);
                daoUser.update(user);
                request.getRequestDispatcher("UserServlet?action=listUserProfile").forward(request, response);

            }
        } else if (action.equals("logout")) {
            //action logout
            session.removeAttribute("name");
            response.sendRedirect("homepage.jsp");
        } else if (action.equals("listAll")) {
            response.sendRedirect("homepage.jsp");

        }
        System.out.println(action);
        if (action.equals("displayFollowByRole")) {
            request.setAttribute("user", user);
            //role admin, manager
            switch (user.getSettingId()) {
                case 1:
                case 2:
                    response.sendRedirect("/krs/admin/dashboard.jsp");
                    return;
                //role teacher
                case 3: {
                    RequestDispatcher dis = request.getRequestDispatcher("TeacherServlet?action=listCourse");
                    dis.forward(request, response);
                    return;

                }
                //role student
                case 4: {
                    RequestDispatcher dis = request.getRequestDispatcher("StudentServlet?action=listCourse");
                    dis.forward(request, response);
                    break;
                }
                
            }
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
