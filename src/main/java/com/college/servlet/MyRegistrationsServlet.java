package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;

@WebServlet("/myRegistrations")
public class MyRegistrationsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("studentId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        int studentId = (Integer) session.getAttribute("studentId");
        String sql = "SELECT r.reg_id, e.name, e.event_date, e.event_time, e.venue, r.status "
                + "FROM er_registrations r JOIN er_events e ON r.event_id = e.event_id "
                + "WHERE r.student_id=? ORDER BY r.reg_date DESC";
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("regId", rs.getInt("reg_id"));
                    m.put("name", rs.getString("name"));
                    m.put("date", rs.getDate("event_date"));
                    m.put("time", rs.getString("event_time"));
                    m.put("venue", rs.getString("venue"));
                    m.put("status", rs.getString("status"));
                    list.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
        }
        req.setAttribute("regs", list);
        req.getRequestDispatcher("myRegistrations.jsp").forward(req, resp);
    }
}