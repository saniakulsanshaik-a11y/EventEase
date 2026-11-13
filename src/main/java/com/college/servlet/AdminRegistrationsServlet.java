package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;

@WebServlet("/adminRegistrations")
public class AdminRegistrationsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("adminName") == null) {
            resp.sendRedirect("adminLogin.jsp");
            return;
        }
        int eventId = Integer.parseInt(req.getParameter("id"));
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("SELECT name FROM er_events WHERE event_id=?")) {
                ps.setInt(1, eventId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) req.setAttribute("eventName", rs.getString(1));
                }
            }
            String sql = "SELECT s.name, s.email, s.phone, r.status, r.reg_date "
                    + "FROM er_registrations r JOIN students s ON r.student_id = s.student_id "
                    + "WHERE r.event_id=? ORDER BY r.reg_date";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, eventId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("name", rs.getString("name"));
                        m.put("email", rs.getString("email"));
                        m.put("phone", rs.getString("phone"));
                        m.put("status", rs.getString("status"));
                        m.put("date", rs.getTimestamp("reg_date"));
                        list.add(m);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
        }
        req.setAttribute("regs", list);
        req.getRequestDispatcher("adminRegistrations.jsp").forward(req, resp);
    }
}