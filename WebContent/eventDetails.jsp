<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Event Details</title>
<link rel="stylesheet" href="CSS/style.css">
</head>
<body>
<nav>
  <a href="studentHome.jsp">Home</a>
  <a href="events">Events</a>
  <a href="myRegistrations">My Registrations</a>
  <a href="logout">Logout</a>
</nav>
<div class="box wide">
  <h2>${ev.name}</h2>
  <p><b>Category:</b> ${ev.category}</p>
  <p><b>Description:</b> ${ev.description}</p>
  <p><b>Date:</b> ${ev.date} &nbsp; <b>Time:</b> ${ev.time}</p>
  <p><b>Venue:</b> ${ev.venue}</p>
  <p><b>Seats available:</b> ${ev.seats} / ${ev.total}</p>

  <c:choose>
    <c:when test="${ev.reg}"><p class="ok">You are registered for this event.</p></c:when>
    <c:when test="${ev.seats <= 0}"><p class="err">Event is full.</p></c:when>
    <c:otherwise>
      <form action="registerEvent" method="post">
        <input type="hidden" name="eventId" value="${ev.id}">
        <button type="submit">Register</button>
      </form>
    </c:otherwise>
  </c:choose>
  <p><a href="events">&larr; Back to events</a></p>
</div>
</body>
</html>