package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;

@WebServlet("/events")
public class EventsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("studentId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        int studentId = (Integer) session.getAttribute("studentId");
        String sql = "SELECT e.event_id, e.name, e.category, e.event_date, e.event_time, e.venue, "
                + "e.available_seats, (SELECT COUNT(*) FROM er_registrations r WHERE r.event_id=e.event_id "
                + "AND r.student_id=? AND r.status='REGISTERED') AS reg "
                + "FROM er_events e ORDER BY e.event_date";
        List<Map<String, Object>> events = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", rs.getInt("event_id"));
                    m.put("name", rs.getString("name"));
                    m.put("category", rs.getString("category"));
                    m.put("date", rs.getDate("event_date"));
                    m.put("time", rs.getString("event_time"));
                    m.put("venue", rs.getString("venue"));
                    m.put("seats", rs.getInt("available_seats"));
                    m.put("reg", rs.getInt("reg") > 0);
                    events.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
        }
        req.setAttribute("events", events);
        req.getRequestDispatcher("events.jsp").forward(req, resp);
    }
}