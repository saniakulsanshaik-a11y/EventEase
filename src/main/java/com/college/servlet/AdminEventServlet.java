package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;

@WebServlet("/adminEvent")
public class AdminEventServlet extends HttpServlet {

    private boolean notAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("adminName") == null) {
            resp.sendRedirect("adminLogin.jsp");
            return true;
        }
        return false;
    }

    // open form: blank (add) or filled (update, ?id=)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (notAdmin(req, resp)) return;
        String id = req.getParameter("id");
        if (id != null && !id.isEmpty()) {
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM er_events WHERE event_id=?")) {
                ps.setInt(1, Integer.parseInt(id));
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Map<String, Object> m = new HashMap<>();
                        m.put("id", rs.getInt("event_id"));
                        m.put("name", rs.getString("name"));
                        m.put("category", rs.getString("category"));
                        m.put("description", rs.getString("description"));
                        m.put("date", rs.getDate("event_date").toString());
                        m.put("time", rs.getString("event_time"));
                        m.put("venue", rs.getString("venue"));
                        m.put("total", rs.getInt("total_seats"));
                        req.setAttribute("ev", m);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        req.getRequestDispatcher("adminEventForm.jsp").forward(req, resp);
    }

    // save: insert (no id) or update (id present)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (notAdmin(req, resp)) return;
        String id = req.getParameter("id");
        Map<String, Object> m = new HashMap<>();
        m.put("id", id);
        m.put("name", req.getParameter("name").trim());
        m.put("category", req.getParameter("category"));
        m.put("description", req.getParameter("description").trim());
        m.put("date", req.getParameter("date"));
        m.put("time", req.getParameter("time"));
        m.put("venue", req.getParameter("venue").trim());
        m.put("total", req.getParameter("total"));

        try (Connection con = DBConnection.getConnection()) {
            int total = Integer.parseInt(req.getParameter("total"));
            java.sql.Date date = java.sql.Date.valueOf(req.getParameter("date"));
            if (total <= 0) throw new NumberFormatException();

            if (id == null || id.isEmpty()) {
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO er_events (name, category, description, event_date, event_time, "
                        + "venue, total_seats, available_seats) VALUES (?,?,?,?,?,?,?,?)")) {
                    ps.setString(1, (String) m.get("name"));
                    ps.setString(2, (String) m.get("category"));
                    ps.setString(3, (String) m.get("description"));
                    ps.setDate(4, date);
                    ps.setString(5, (String) m.get("time"));
                    ps.setString(6, (String) m.get("venue"));
                    ps.setInt(7, total);
                    ps.setInt(8, total);
                    ps.executeUpdate();
                }
            } else {
                int eventId = Integer.parseInt(id);
                int booked;
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT COUNT(*) FROM er_registrations WHERE event_id=? AND status='REGISTERED'")) {
                    ps.setInt(1, eventId);
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        booked = rs.getInt(1);
                    }
                }
                if (total < booked) {
                    req.setAttribute("ev", m);
                    req.setAttribute("error", "Total seats cannot be less than already registered (" + booked + ").");
                    req.getRequestDispatcher("adminEventForm.jsp").forward(req, resp);
                    return;
                }
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE er_events SET name=?, category=?, description=?, event_date=?, event_time=?, "
                        + "venue=?, total_seats=?, available_seats=? WHERE event_id=?")) {
                    ps.setString(1, (String) m.get("name"));
                    ps.setString(2, (String) m.get("category"));
                    ps.setString(3, (String) m.get("description"));
                    ps.setDate(4, date);
                    ps.setString(5, (String) m.get("time"));
                    ps.setString(6, (String) m.get("venue"));
                    ps.setInt(7, total);
                    ps.setInt(8, total - booked);
                    ps.setInt(9, eventId);
                    ps.executeUpdate();
                }
            }
            resp.sendRedirect("adminDashboard?msg=saved");
        } catch (IllegalArgumentException e) {
            req.setAttribute("ev", m);
            req.setAttribute("error", "Please enter valid date and seats (more than 0).");
            req.getRequestDispatcher("adminEventForm.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("ev", m);
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("adminEventForm.jsp").forward(req, resp);
        }
    }
}