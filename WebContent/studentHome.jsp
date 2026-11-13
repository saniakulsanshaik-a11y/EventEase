<%@ page contentType="text/html;charset=UTF-8" %>
<%
  if (session.getAttribute("studentId") == null) {
      response.sendRedirect("login.jsp");
      return;
  }
%>
<html>
<head>
<title>Student Home</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<nav>
  <a href="studentHome.jsp">Home</a>
  <a href="events">Events</a>
  <a href="myRegistrations">My Registrations</a>
  <a href="logout">Logout</a>
</nav>
<div class="box wide" style="text-align:center">
  <h2>Welcome, ${studentName}!</h2>
  <p>Browse upcoming events and manage your registrations.</p>
  <a class="btn" href="events">View Events</a>
  <a class="btn" href="myRegistrations">My Registrations</a>
</div>
</body>
</html>