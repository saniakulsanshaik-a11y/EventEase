<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Event Registrations</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<nav>
  <a href="adminDashboard">Dashboard</a>
  <a href="adminEvent">Add Event</a>
  <a href="adminLogout">Logout</a>
</nav>
<h2 style="text-align:center">Registrations: ${eventName}</h2>
<p class="msg err">${error}</p>
<table>
  <tr><th>Student</th><th>Email</th><th>Phone</th><th>Status</th><th>Registered On</th></tr>
  <c:forEach var="r" items="${regs}">
    <tr>
      <td>${r.name}</td>
      <td>${r.email}</td>
      <td>${r.phone}</td>
      <td>${r.status}</td>
      <td>${r.date}</td>
    </tr>
  </c:forEach>
  <c:if test="${empty regs}"><tr><td colspan="5">No registrations yet.</td></tr></c:if>
</table>
<p style="text-align:center"><a href="adminDashboard">&larr; Back to dashboard</a></p>
</body>
</html>