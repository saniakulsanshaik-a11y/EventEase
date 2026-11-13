package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;

@WebServlet("/adminDeleteEvent")
public class AdminDeleteEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("adminName") == null) {
            resp.sendRedirect("adminLogin.jsp");
            return;
        }
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM er_events WHERE event_id=?")) {
            ps.setInt(1, Integer.parseInt(req.getParameter("id")));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("adminDashboard?msg=deleted");
    }
}