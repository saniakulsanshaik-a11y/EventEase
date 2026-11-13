<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>My Registrations</title>
<link rel="stylesheet" href="CSS/style.css">
</head>
<body>
<nav>
  <a href="studentHome.jsp">Home</a>
  <a href="events">Events</a>
  <a href="myRegistrations">My Registrations</a>
  <a href="logout">Logout</a>
</nav>
<h2 style="text-align:center">My Registrations</h2>
<p class="msg ok">
  ${param.msg == 'success' ? 'Registration successful!' : ''}
  ${param.msg == 'cancelled' ? 'Registration cancelled.' : ''}
</p>
<p class="msg err">${error}</p>
<table>
  <tr><th>Event</th><th>Date</th><th>Time</th><th>Venue</th><th>Status</th><th>Action</th></tr>
  <c:forEach var="r" items="${regs}">
    <tr>
      <td>${r.name}</td>
      <td>${r.date}</td>
      <td>${r.time}</td>
      <td>${r.venue}</td>
      <td>${r.status}</td>
      <td>
        <c:if test="${r.status == 'REGISTERED'}">
          <form action="cancelRegistration" method="post" style="margin:0"
                onsubmit="return confirm('Cancel this registration?')">
            <input type="hidden" name="regId" value="${r.regId}">
            <button type="submit" class="red">Cancel</button>
          </form>
        </c:if>
      </td>
    </tr>
  </c:forEach>
  <c:if test="${empty regs}"><tr><td colspan="6">No registrations yet.</td></tr></c:if>
</table>
</body>
</html>