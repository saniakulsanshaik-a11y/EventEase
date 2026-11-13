package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;

@WebServlet("/eventDetails")
public class EventDetailsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("studentId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        int studentId = (Integer) session.getAttribute("studentId");
        int eventId = Integer.parseInt(req.getParameter("id"));
        String sql = "SELECT e.*, (SELECT COUNT(*) FROM er_registrations r WHERE r.event_id=e.event_id "
                + "AND r.student_id=? AND r.status='REGISTERED') AS reg FROM er_events e WHERE e.event_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", rs.getInt("event_id"));
                    m.put("name", rs.getString("name"));
                    m.put("category", rs.getString("category"));
                    m.put("description", rs.getString("description"));
                    m.put("date", rs.getDate("event_date"));
                    m.put("time", rs.getString("event_time"));
                    m.put("venue", rs.getString("venue"));
                    m.put("total", rs.getInt("total_seats"));
                    m.put("seats", rs.getInt("available_seats"));
                    m.put("reg", rs.getInt("reg") > 0);
                    req.setAttribute("ev", m);
                    req.getRequestDispatcher("eventDetails.jsp").forward(req, resp);
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("events");
    }
}