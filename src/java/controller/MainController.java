/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Minh
 */

import dao.DepartmentDAO;
import dao.RequestDAO;
import dao.RoleDAO;
import dao.UserDAO;
import model.Request;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {
    
    
    private final UserDAO userDAO = new UserDAO();
    private final RequestDAO requestDAO = new RequestDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final RoleDAO roleDAO = new RoleDAO();
    
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response)
            throws ServletException, IOException {
//      System.out.println("!!!!!!!!!! [DEBUG] Access request to MainController !!!!!!!!!!!");
        String action = request.getParameter("action");
        if (action == null) action = "home";

        try {
            switch (action) {
                case "login":
                    login(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
                case "createRequest":
                    if ("POST".equalsIgnoreCase(request.getMethod())) createRequestPost(request, response);
                    else request.getRequestDispatcher("createRequest.jsp").forward(request, response);
                    break;
                case "listRequest":
                    listRequest(request, response);
                    break;
                case "approveRequest":
                    approveRequest(request, response);
                    break;
                case "agenda":
                    showAgenda(request, response);
                    break;
                case "home":
                default:
                    request.getRequestDispatcher("home.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        System.out.println("--- DEBUG: In login function---");
//        System.out.println("--- DEBUG: Username form: [" + req.getParameter("username") + "]");
//        System.out.println("--- DEBUG: Password form: [" + req.getParameter("password") + "]");
        String u = req.getParameter("username");
        String p = req.getParameter("password");
        User user = userDAO.authenticate(u, p);
        if (user != null) {
            // 1. Lấy tên phòng ban
            String deptName = departmentDAO.getDepartmentNameById(user.getDepartmentId());
            user.setDepartmentName(deptName);

            // 2. Lấy Role
            String roleName = roleDAO.getRoleNameByUserId(user.getId());
            user.setRoleName(roleName);
            
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("MainController?action=home");
        } else {
            req.setAttribute("error", "Invalid credentials");
            try { req.getRequestDispatcher("login.jsp").forward(req, resp); } catch (Exception e) {}
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) session.invalidate();
        resp.sendRedirect("login.jsp");
    }

    private void createRequestPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String from = req.getParameter("fromDate");
        String to = req.getParameter("toDate");
        String reason = req.getParameter("reason");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Request r = new Request();
        r.setTitle(reason);
        r.setFromDate(df.parse(from));
        r.setToDate(df.parse(to));
        r.setReason(reason);
        r.setStatus("InProgress");

        User u = (User) req.getSession().getAttribute("user");
        r.setCreatedBy(u.getId());

        requestDAO.insertRequest(r);
        resp.sendRedirect("MainController?action=listRequest");
    }

    private void listRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User u = (User) req.getSession().getAttribute("user");
        List<Request> list = requestDAO.getRequestsByUser(u.getId());
        req.setAttribute("requests", list);
        req.getRequestDispatcher("listRequest.jsp").forward(req, resp);
    }

    private void approveRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String idStr = req.getParameter("id");
        String action = req.getParameter("decision"); // approve / reject
        if (idStr != null && action != null) {
            int id = Integer.parseInt(idStr);
            User u = (User) req.getSession().getAttribute("user");
            String status = action.equalsIgnoreCase("approve") ? "Approved" : "Rejected";
            requestDAO.updateStatus(id, status, u.getId());
        }
        resp.sendRedirect("MainController?action=listRequest");
    }
    private void showAgenda(jakarta.servlet.http.HttpServletRequest req,
                            jakarta.servlet.http.HttpServletResponse resp)
                            throws Exception{
        jakarta.servlet.http.HttpSession session = req.getSession();
        model.User currentUser = (model.User) session.getAttribute("user");

        dao.DepartmentDAO depDAO = new dao.DepartmentDAO();
        dao.RequestDAO reqDAO = new dao.RequestDAO();
        dao.UserDAO userDAO = new dao.UserDAO();

        // Lấy danh sách nhân viên trong cùng phòng
        java.util.List<model.User> members = userDAO.getUsersByDepartment(currentUser.getDepartmentId());

        // Giả sử Minh muốn xem từ ngày 2025-01-01 đến 2025-01-09
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date from = df.parse(req.getParameter("fromDate") != null ? req.getParameter("fromDate") : "2025-01-01");
        java.util.Date to = df.parse(req.getParameter("toDate") != null ? req.getParameter("toDate") : "2025-01-09");

        // Lấy danh sách đơn nghỉ của cả phòng trong khoảng đó
        java.util.Map<Integer, java.util.List<model.Request>> leaveMap =
                reqDAO.getLeavesByDepartmentAndDateRange(currentUser.getDepartmentId(), from, to);

        req.setAttribute("members", members);
        req.setAttribute("leaveMap", leaveMap);
        req.setAttribute("fromDate", df.format(from));
        req.setAttribute("toDate", df.format(to));

        req.getRequestDispatcher("agenda.jsp").forward(req, resp);
    }

}

