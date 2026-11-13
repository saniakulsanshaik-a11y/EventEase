<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Events</title>
<link rel="stylesheet" href="CSS/style.css">
</head>
<body>
<nav>
  <a href="studentHome.jsp">Home</a>
  <a href="events">Events</a>
  <a href="myRegistrations">My Registrations</a>
  <a href="logout">Logout</a>
</nav>
<h2 style="text-align:center">Upcoming Events</h2>
<p class="msg err">
  ${param.msg == 'full' ? 'Sorry, no seats available.' : ''}
  ${param.msg == 'already' ? 'You are already registered for this event.' : ''}
  ${param.msg == 'error' ? 'Something went wrong. Try again.' : ''}
  ${error}
</p>
<table>
  <tr><th>Event</th><th>Category</th><th>Date</th><th>Time</th><th>Venue</th><th>Seats</th><th>Action</th></tr>
  <c:forEach var="e" items="${events}">
    <tr>
      <td>${e.name}</td>
      <td>${e.category}</td>
      <td>${e.date}</td>
      <td>${e.time}</td>
      <td>${e.venue}</td>
      <td>${e.seats}</td>
      <td>
        <a href="eventDetails?id=${e.id}">Details</a>
        <c:if test="${e.reg}"> | <span class="ok">Registered</span></c:if>
      </td>
    </tr>
  </c:forEach>
  <c:if test="${empty events}"><tr><td colspan="7">No events available.</td></tr></c:if>
</table>
</body>
</html>