/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.DAOSetting;
import dao.DAOSubject;
import dao.DAOSubject;
import entity.Subject;
import entity.Subject;
import entity.Subject;
import entity.Subject;
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
 * @author DELL
 */
public class TrainingServlet extends HttpServlet {

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
        String action = request.getParameter("action");
        DAOSubject daoSubject = new DAOSubject();
        DAOSetting daoSetting = new DAOSetting();
        HttpSession session = request.getSession();
        String user_name = (String)session.getAttribute("name");
       
            if (action != null && !action.isEmpty()) {
                if (action.equals("listAllSubject")) {
                    String search = request.getParameter("search");
                    String status = request.getParameter("status");
                    if (search == null || search.isEmpty()) {
                        search = "";
                    }
                    if (status == null || status.isEmpty()) {
                        status = "All";
                    }

                    int currentPage = 0;
                    if (request.getParameter("currentPage") != null && !request.getParameter("currentPage").isEmpty()) {
                        currentPage = Integer.parseInt(request.getParameter("currentPage")) - 1;
                    }

                    String sql = null;
                    String sqlForPageNumber = null;
                    if (status.equals("All")) {
                        sql = "select * from Subject where subject_name like '%" + search + "%' LIMIT 3 OFFSET " + currentPage * 3;
                        sqlForPageNumber = "select * from Subject where subject_name like '%" + search + "%'";
                    } else {
                        sql = "select * from Subject where subject_name like '%" + search + "%' and status = " + status + " LIMIT 3 OFFSET " + currentPage * 3;
                        sqlForPageNumber = "select * from Subject where subject_name like '%" + search + "%' and status = " + status;
                    }
                    request.setAttribute("search", search);
                    request.setAttribute("status", status);
                    request.setAttribute("subjectList", daoSubject.getSubjectDetails(sql));
                    request.setAttribute("currentPage", currentPage + 1);
                    request.setAttribute("pageNumber", daoSubject.getPageNumber(daoSubject.getSubjectDetails(sqlForPageNumber).size()));
                    request.setAttribute("size", daoSubject.getSubjectDetails(sqlForPageNumber).size());
                    request.getRequestDispatcher("admin/subjectList.jsp").forward(request, response);
                    return;
                }
                if (action.equals("edit")) {
                    int subject_id = Integer.parseInt(request.getParameter("subject_id"));
                    Subject subjectDetails = daoSubject.getSubjectDetails("select * from Subject where subject_id=" + subject_id).get(0);
                    request.setAttribute("subjectDetails", subjectDetails);
                    request.getRequestDispatcher("admin/subjectDetails.jsp").forward(request, response);
                    return;
                }
                if (action.equals("update")) {
                    int subject_id = Integer.parseInt(request.getParameter("subject_id"));
                    String subject_name = request.getParameter("subject_name");
                    String subject_code = request.getParameter("subject_code");
                    String description = request.getParameter("description");
                    boolean status = Boolean.valueOf(request.getParameter("status"));
                    String editStatus = request.getParameter("editStatus");
                    if (editStatus != null) {
                        Subject settingDetails = new Subject(subject_id, subject_name, subject_code, description, status);
                        daoSubject.update(settingDetails);
                        response.sendRedirect("TrainingServlet?action=listAllSubject");
                        return;
                    }
                    if (daoSubject.checkExisted(subject_name, subject_code, status)) {
                        Subject subjectDetails = daoSubject.getSubjectDetails("select * from Subject where subject_id =" + subject_id).firstElement();
                        request.setAttribute("subjectDetails", subjectDetails);
                        request.setAttribute("warn", "Subject existed");
                        request.getRequestDispatcher("admin/subjectDetails.jsp").forward(request, response);
                    } else {
                        Subject subjectDetails = new Subject(subject_id, subject_name, subject_code, description, status);
                        request.setAttribute("subjectDetails", subjectDetails);
                        daoSubject.update(subjectDetails);
                        request.setAttribute("warn", "Update subject successful");
                        request.getRequestDispatcher("admin/subjectDetails.jsp").forward(request, response);
                        return;
                    }
                }
                if (action.equals("insert")) {

                    String subject_name = request.getParameter("subject_name");
                    String subject_code = request.getParameter("subject_code");
                    String description = request.getParameter("description");
                    boolean status = Boolean.valueOf(request.getParameter("status"));
                    if (daoSubject.checkExisted(subject_name, subject_code, status)) {
                        request.setAttribute("warn", "Subject existed");
                        request.getRequestDispatcher("admin/addSubject.jsp").forward(request, response);
                    } else {
                        daoSubject.insert(subject_name, subject_code, description, status);
                        request.setAttribute("warn", "Add subject successful");
                        request.getRequestDispatcher("admin/addSubject.jsp").forward(request, response);
                        return;
                    }
                }
            } else {
                response.sendRedirect("TrainingServlet?action=listAllSubject");
                return;
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
