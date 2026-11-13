package com.college.servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.college.util.DBConnection;
import com.college.util.PasswordUtil;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name").trim();
        String email = req.getParameter("email").trim().toLowerCase();
        String phone = req.getParameter("phone").trim();
        String password = PasswordUtil.hash(req.getParameter("password"));

        String sql = "INSERT INTO students (name, email, phone, password) VALUES (?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, password);
            ps.executeUpdate();
            resp.sendRedirect("login.jsp?msg=registered");
        } catch (SQLIntegrityConstraintViolationException e) {
            req.setAttribute("error", "This email is already registered.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}