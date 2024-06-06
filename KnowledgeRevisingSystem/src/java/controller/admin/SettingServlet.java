/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dao.DAO;
import dao.DAOSetting;
import entity.Setting;
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
public class SettingServlet extends HttpServlet {

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
        DAOSetting daoSetting = new DAOSetting();
        HttpSession session = request.getSession();
        String user_name = (String)session.getAttribute("name");
        
        
        if (action != null && !action.isEmpty()) {
            if (action.equals("listAllSetting")) {
                String search = request.getParameter("search");
                String type = request.getParameter("type");
                String status = request.getParameter("status");
                String order = request.getParameter("order");

                if (order != null && Character.isDigit(order.charAt(0))) {
                    order = " and `order` = " + request.getParameter("order");
                    request.setAttribute("order", request.getParameter("order"));
                } else if (order == null) {
                    order = " ORDER BY `order` ";
                    request.setAttribute("order", "ASC");
                } else {
                    order = " ORDER BY `order` " + order;
                    request.setAttribute("order", request.getParameter("order"));
                }
                if (search == null || search.isEmpty()) {
                    search = "";
                }
                if (status == null || status.isEmpty()) {
                    status = "All";
                }
                if (type == null || type.isEmpty()) {
                    type = "All";
                }
                int currentPage = 0;
                if (request.getParameter("currentPage") != null && !request.getParameter("currentPage").isEmpty()) {
                    currentPage = Integer.parseInt(request.getParameter("currentPage")) - 1;
                }

                String sql = null;
                String sqlForPageNumber = null;
                if (status.equals("All") && type.equals("All")) {
                    sql = "select * from Setting where name like '%" + search + "%'" + order + " LIMIT 3 OFFSET " + currentPage * 3;
                    sqlForPageNumber = "select * from Setting where name like '%" + search + "%'" + order;
                } else if (status.equals("All")) {
                    sql = "select * from Setting where name like '%" + search + "%' and type = '" + type + "'" + order + " LIMIT 3 OFFSET " + currentPage * 3;
                    sqlForPageNumber = "select * from Setting where name like '%" + search + "%' and type = '" + type + "'" + order;
                } else if (type.equals("All")) {
                    sql = "select * from Setting where name like '%" + search + "%' and status = " + status + order + " LIMIT 3 OFFSET " + currentPage * 3;
                    sqlForPageNumber = "select * from Setting where name like '%" + search + "%' and status = " + status + order;
                } else {
                    sql = "select * from Setting where name like '%" + search + "%' and status = " + status + " and type ='" + type + "'" + order + " LIMIT 3 OFFSET " + currentPage * 3;
                    sqlForPageNumber = "select * from Setting where name like '%" + search + "%' and status = " + status + " and type ='" + type + "'" + order;
                }
                request.setAttribute("search", search);

                request.setAttribute("type", type);
                request.setAttribute("status", status);
                request.setAttribute("orderList", daoSetting.getOneColumn("select distinct `order` from Setting"));
                request.setAttribute("typeList", daoSetting.getOneColumn("select distinct type from Setting"));
                request.setAttribute("settingList", daoSetting.getSettingDetails(sql));
                request.setAttribute("currentPage", currentPage + 1);
                request.setAttribute("pageNumber", daoSetting.getPageNumber(daoSetting.getSettingDetails(sqlForPageNumber).size()));
                request.setAttribute("size", daoSetting.getSettingDetails(sqlForPageNumber).size());
                request.getRequestDispatcher("admin/settingList.jsp").forward(request, response);
                return;

            }

            if (action.equals("edit")) {
                int setting_id = Integer.parseInt(request.getParameter("setting_id"));
                Setting settingDetails = daoSetting.getSettingDetails("select * from Setting where setting_id=" + setting_id).get(0);
                request.setAttribute("settingDetails", settingDetails);
                request.getRequestDispatcher("admin/settingDetails.jsp").forward(request, response);
                return;
            }

            if (action.equals("update")) {
                int setting_id = Integer.parseInt(request.getParameter("setting_id"));
                String type = request.getParameter("type");
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                boolean status = Boolean.valueOf(request.getParameter("status"));
                int order = Integer.parseInt(request.getParameter("order"));
                String editStatus = request.getParameter("editStatus");
                if (editStatus != null) {
                    Setting settingDetails = new Setting(setting_id, name, type, description, status, order);
                    daoSetting.update(settingDetails);
                    response.sendRedirect("SettingServlet?action=listAllSetting");
                    return;
                }
                if (daoSetting.checkExisted(type, name, status)) {
                    Setting settingDetails = daoSetting.getSettingDetails("select * from Setting where setting_id =" + setting_id).firstElement();
                    request.setAttribute("settingDetails", settingDetails);
                    request.setAttribute("warn", "Update not sccessful because Setting existed");
                    request.getRequestDispatcher("admin/settingDetails.jsp").forward(request, response);
                    return;
                } else if (daoSetting.checkExisted(type, name, status) == false) {
                    Setting settingDetails = new Setting(setting_id, name, type, description, status, order);
                    daoSetting.update(settingDetails);
                    request.setAttribute("settingDetails", settingDetails);
                    request.setAttribute("warn", "Update setting successful");
                    request.getRequestDispatcher("admin/settingDetails.jsp").forward(request, response);
                    return;
                }

            }
            if (action.equals("changeStatus")) {

            }
            if (action.equals("insert")) {
                String type = request.getParameter("type");
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                int order = Integer.parseInt(request.getParameter("order"));
                boolean status = Boolean.valueOf(request.getParameter("status"));
                if (daoSetting.checkExisted(type, name, status)) {
                    request.setAttribute("warn", "Setting existed");
                    request.getRequestDispatcher("admin/addSetting.jsp").forward(request, response);
                    return;
                } else if (daoSetting.checkExisted(type, name, status) == false) {
                    daoSetting.insert(name, type, description, status, order);
                    request.setAttribute("warn", "Add setting successful");
                    request.getRequestDispatcher("admin/addSetting.jsp").forward(request, response);
                    return;
                }
            }
        } else {
            response.sendRedirect("SettingServlet?action=listAllSetting");
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
