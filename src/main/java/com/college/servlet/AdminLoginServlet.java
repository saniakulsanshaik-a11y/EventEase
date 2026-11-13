package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;
import com.college.util.PasswordUtil;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String user = req.getParameter("username").trim();
        String pass = PasswordUtil.hash(req.getParameter("password"));
        String sql = "SELECT username FROM er_admins WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user);
            ps.setString(2, pass);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    req.getSession().setAttribute("adminName", rs.getString(1));
                    resp.sendRedirect("adminDashboard");
                    return;
                }
            }
            req.setAttribute("error", "Invalid admin credentials.");
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
        }
        req.getRequestDispatcher("adminLogin.jsp").forward(req, resp);
    }
}