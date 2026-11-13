package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;

@WebServlet("/registerEvent")
public class RegisterEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("studentId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        int studentId = (Integer) session.getAttribute("studentId");
        int eventId = Integer.parseInt(req.getParameter("eventId"));

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                String existing = null;
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT status FROM er_registrations WHERE student_id=? AND event_id=?")) {
                    ps.setInt(1, studentId);
                    ps.setInt(2, eventId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) existing = rs.getString(1);
                    }
                }
                if ("REGISTERED".equals(existing)) {
                    con.rollback();
                    resp.sendRedirect("events?msg=already");
                    return;
                }
                int seat;
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE er_events SET available_seats = available_seats - 1 "
                        + "WHERE event_id=? AND available_seats > 0")) {
                    ps.setInt(1, eventId);
                    seat = ps.executeUpdate();
                }
                if (seat == 0) {
                    con.rollback();
                    resp.sendRedirect("events?msg=full");
                    return;
                }
                if (existing == null) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO er_registrations (student_id, event_id, status) VALUES (?,?,'REGISTERED')")) {
                        ps.setInt(1, studentId);
                        ps.setInt(2, eventId);
                        ps.executeUpdate();
                    }
                } else {
                    try (PreparedStatement ps = con.prepareStatement(
                            "UPDATE er_registrations SET status='REGISTERED', reg_date=SYSTIMESTAMP "
                            + "WHERE student_id=? AND event_id=?")) {
                        ps.setInt(1, studentId);
                        ps.setInt(2, eventId);
                        ps.executeUpdate();
                    }
                }
                con.commit();
                resp.sendRedirect("myRegistrations?msg=success");
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect("events?msg=error");
        }
    }
}