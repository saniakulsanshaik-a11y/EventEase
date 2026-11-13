<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
<title>Admin Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<div class="box">
  <h2>Admin Login</h2>
  <p class="err">${error}</p>
  <form action="adminLogin" method="post">
    Username<input type="text" name="username" required>
    Password<input type="password" name="password" required>
    <button type="submit">Login</button>
  </form>
  <p><a href="index.jsp">&larr; Home</a></p>
</div>
</body>
</html>