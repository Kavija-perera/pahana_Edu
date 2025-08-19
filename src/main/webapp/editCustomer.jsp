<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.pahanaedu.model.Customer" %>
<%
    if (session.getAttribute("loggedIn") == null || !(Boolean) session.getAttribute("loggedIn")) {
        response.sendRedirect("login.jsp");
    }
    Customer customer = (Customer) request.getAttribute("customer");
%>
<html>
<head>
    <title>Edit Customer</title>
</head>
<body>
<h2>Edit Customer</h2>
<% if (request.getAttribute("message") != null) { %>
<p style="color:green;"><%= request.getAttribute("message") %></p>
<% } %>
<% if (request.getAttribute("error") != null) { %>
<p style="color:red;"><%= request.getAttribute("error") %></p>
<% } %>
<form action="../editCustomer" method="get">
    Account Number: <input type="text" name="accountNumber" value="<%= customer != null ? customer.getAccountNumber() : "" %>">
    <input type="submit" value="Load">
</form>
<% if (customer != null) { %>
<form action="../editCustomer" method="post">
    <input type="hidden" name="accountNumber" value="<%= customer.getAccountNumber() %>">
    Name: <input type="text" name="name" value="<%= customer.getName() %>"><br>
    Address: <input type="text" name="address" value="<%= customer.getAddress() %>"><br>
    Phone: <input type="text" name="phone" value="<%= customer.getPhone() %>"><br>
    Units Consumed: <input type="number" name="units" value="<%= customer.getUnitsConsumed() %>"><br>
    <input type="submit" value="Update">
</form>
<% } %>
<a href="dashboard.jsp">Back to Dashboard</a>
</body>
</html>