package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;

@WebServlet("/cancelRegistration")
public class CancelRegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("studentId") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        int studentId = (Integer) session.getAttribute("studentId");
        int regId = Integer.parseInt(req.getParameter("regId"));

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                int changed;
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE er_registrations SET status='CANCELLED' "
                        + "WHERE reg_id=? AND student_id=? AND status='REGISTERED'")) {
                    ps.setInt(1, regId);
                    ps.setInt(2, studentId);
                    changed = ps.executeUpdate();
                }
                if (changed == 1) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "UPDATE er_events SET available_seats = available_seats + 1 "
                            + "WHERE event_id = (SELECT event_id FROM er_registrations WHERE reg_id=?)")) {
                        ps.setInt(1, regId);
                        ps.executeUpdate();
                    }
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("myRegistrations?msg=cancelled");
    }
}