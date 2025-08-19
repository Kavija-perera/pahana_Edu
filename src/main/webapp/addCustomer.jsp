<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%--%>
<%--    if (session.getAttribute("loggedIn") == null || !(Boolean) session.getAttribute("loggedIn")) {--%>
<%--        response.sendRedirect("login.jsp");--%>
<%--    }--%>
<%--%>--%>
<html>
<head>
    <title>Add Customer</title>
</head>
<body>
<h2>Add New Customer</h2>
<% if (request.getAttribute("message") != null) { %>
<p style="color:green;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>
<form action="../addCustomer" method="post">
    Account Number: <input type="text" name="accountNumber"><br>
    Name: <input type="text" name="name"><br>
    Address: <input type="text" name="address"><br>
    Phone: <input type="text" name="phone"><br>
    Units Consumed: <input type="number" name="units"><br>
    <input type="submit" value="Add">
</form>
<a href="./dashbord.jsp">Back to Dashboard</a>
</body>
</html>
