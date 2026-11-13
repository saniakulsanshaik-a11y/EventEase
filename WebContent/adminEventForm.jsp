<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Event Form</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
<nav>
  <a href="adminDashboard">Dashboard</a>
  <a href="adminEvent">Add Event</a>
  <a href="adminLogout">Logout</a>
</nav>
<div class="box wide">
  <h2>${empty ev.id ? 'Add Event' : 'Update Event'}</h2>
  <p class="err">${error}</p>
  <form action="adminEvent" method="post">
    <input type="hidden" name="id" value="${ev.id}">
    Event Name<input type="text" name="name" value="${ev.name}" required>
    Category
    <select name="category">
      <option value="TECHNICAL" ${ev.category == 'TECHNICAL' ? 'selected' : ''}>TECHNICAL</option>
      <option value="CULTURAL" ${ev.category == 'CULTURAL' ? 'selected' : ''}>CULTURAL</option>
      <option value="WORKSHOP" ${ev.category == 'WORKSHOP' ? 'selected' : ''}>WORKSHOP</option>
    </select>
    Description<textarea name="description" rows="3">${ev.description}</textarea>
    Date<input type="date" name="date" value="${ev.date}" required>
    Time<input type="time" name="time" value="${ev.time}" required>
    Venue<input type="text" name="venue" value="${ev.venue}" required>
    Total Seats<input type="number" name="total" min="1" value="${ev.total}" required>
    <button type="submit">Save</button>
    <a href="adminDashboard">Cancel</a>
  </form>
</div>
</body>
</html>