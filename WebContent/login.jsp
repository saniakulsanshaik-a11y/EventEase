<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<title>Login</title>
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
  <h2>Student Login</h2>
  <p class="ok">${param.msg == 'registered' ? 'Registration successful! Please login.' : ''}</p>
  <p class="err">${error}</p>
  <form action="login" method="post">
    Email<input type="email" name="email" required>
    Password<input type="password" name="password" required>
    <button type="submit">Login</button>
  </form>
  <p style="text-align:center">New user? <a href="register.jsp">Register</a></p>
</div>
</body>
</html>