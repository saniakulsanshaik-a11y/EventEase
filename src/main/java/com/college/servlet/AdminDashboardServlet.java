package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;

@WebServlet("/adminDashboard")
public class AdminDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("adminName") == null) {
            resp.sendRedirect("adminLogin.jsp");
            return;
        }
        List<Map<String, Object>> events = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            try (Statement st = con.createStatement()) {
                try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM er_events")) {
                    rs.next(); req.setAttribute("totalEvents", rs.getInt(1));
                }
                try (ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM students")) {
                    rs.next(); req.setAttribute("totalStudents", rs.getInt(1));
                }
                try (ResultSet rs = st.executeQuery(
                        "SELECT COUNT(*) FROM er_registrations WHERE status='REGISTERED'")) {
                    rs.next(); req.setAttribute("totalRegs", rs.getInt(1));
                }
            }
            String sql = "SELECT e.event_id, e.name, e.category, e.event_date, e.event_time, e.venue, "
                    + "e.total_seats, e.available_seats, "
                    + "(SELECT COUNT(*) FROM er_registrations r WHERE r.event_id=e.event_id "
                    + "AND r.status='REGISTERED') AS regs FROM er_events e ORDER BY e.event_date";
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", rs.getInt("event_id"));
                    m.put("name", rs.getString("name"));
                    m.put("category", rs.getString("category"));
                    m.put("date", rs.getDate("event_date"));
                    m.put("time", rs.getString("event_time"));
                    m.put("venue", rs.getString("venue"));
                    m.put("total", rs.getInt("total_seats"));
                    m.put("seats", rs.getInt("available_seats"));
                    m.put("regs", rs.getInt("regs"));
                    events.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
        }
        req.setAttribute("events", events);
        req.getRequestDispatcher("adminDashboard.jsp").forward(req, resp);
    }
}