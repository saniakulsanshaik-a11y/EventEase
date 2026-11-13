<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<title>College Event Registration</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<nav>
  <a href="index.jsp">Home</a>
  <a href="login.jsp">Student Login</a>
  <a href="register.jsp">Register</a>
  <a href="adminLogin.jsp">Admin Login</a>
</nav>

<div class="hero">
  <h1>🎓 EventEase</h1>
  <p>Discover technical, cultural and workshop events, and register in just a few clicks.</p>
  <a class="btn btn-light" href="login.jsp">Student Login</a>
  <a class="btn btn-outline" href="register.jsp">Create Account</a>
</div>

<div class="features">
  <div class="feature">
    <div class="icon">&#128197;</div>
    <h3>Browse Events</h3>
    <p>See dates, venues and available seats for upcoming events.</p>
  </div>
  <div class="feature">
    <div class="icon">&#9989;</div>
    <h3>Register Online</h3>
    <p>Reserve your seat instantly. No paperwork, no queues.</p>
  </div>
  <div class="feature">
    <div class="icon">&#128221;</div>
    <h3>Manage Registrations</h3>
    <p>Track your registrations and cancel whenever you need.</p>
  </div>
</div>

<div class="footer">
  &copy; 2026 Event Registration System &nbsp;|&nbsp; <a href="adminLogin.jsp">Admin</a>
</div>
</body>
</html>