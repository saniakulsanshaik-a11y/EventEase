<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Admin Dashboard</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<nav>
  <a href="adminDashboard">Dashboard</a>
  <a href="adminEvent">Add Event</a>
  <a href="adminLogout">Logout</a>
</nav>
<h2 style="text-align:center">Welcome, ${adminName}</h2>
<div class="cards">
  <div class="card"><h1>${totalEvents}</h1>Total Events</div>
  <div class="card"><h1>${totalStudents}</h1>Registered Students</div>
  <div class="card"><h1>${totalRegs}</h1>Total Registrations</div>
</div>
<p class="msg ok">
  ${param.msg == 'saved' ? 'Event saved successfully.' : ''}
  ${param.msg == 'deleted' ? 'Event deleted.' : ''}
</p>
<p class="msg err">${error}</p>
<table>
  <tr><th>Event</th><th>Category</th><th>Date</th><th>Time</th><th>Venue</th>
      <th>Seats (free/total)</th><th>Registered</th><th>Actions</th></tr>
  <c:forEach var="e" items="${events}">
    <tr>
      <td>${e.name}</td>
      <td>${e.category}</td>
      <td>${e.date}</td>
      <td>${e.time}</td>
      <td>${e.venue}</td>
      <td>${e.seats} / ${e.total}</td>
      <td>${e.regs}</td>
      <td>
        <a href="adminRegistrations?id=${e.id}">Registrations</a> |
        <a href="adminEvent?id=${e.id}">Edit</a>
        <form action="adminDeleteEvent" method="post" style="display:inline"
              onsubmit="return confirm('Delete this event and its registrations?')">
          <input type="hidden" name="id" value="${e.id}">
          <button type="submit" class="red" style="width:auto;padding:4px 10px">Delete</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  <c:if test="${empty events}"><tr><td colspan="8">No events yet.</td></tr></c:if>
</table>
</body>
</html>