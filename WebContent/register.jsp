<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<title>Register</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<nav>
  <a href="index.jsp">Home</a>
  <a href="login.jsp">Student Login</a>
  <a href="register.jsp">Register</a>
  <a href="adminLogin.jsp">Admin Login</a>
</nav>
<div class="box">
  <h2>Student Registration</h2>
  <p class="err">${error}</p>
  <form action="register" method="post">
    Name<input type="text" name="name" required>
    Email<input type="email" name="email" required>
    Phone<input type="text" name="phone" pattern="[0-9]{10}" title="10 digits" required>
    Password<input type="password" name="password" minlength="4" required>
    <button type="submit">Register</button>
  </form>
  <p style="text-align:center">Already registered? <a href="login.jsp">Login</a></p>
</div>
</body>
</html>