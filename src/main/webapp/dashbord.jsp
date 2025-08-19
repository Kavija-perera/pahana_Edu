<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%--%>
<%--    if (session.getAttribute("loggedIn") == null || !(Boolean) session.getAttribute("loggedIn")) {--%>
<%--        response.sendRedirect("dashbord.jsp");--%>
<%--    }--%>
<%--%>--%>
<html>
<head>
    <title>Dashboard - Pahana Edu System</title>

</head>
<body>
<h2>Welcome to Pahana Edu Bookshop System</h2>
<ul>
    <li><a href="addCustomer.jsp">Add New Customer</a></li>
    <li><a href="editCustomer.jsp">Edit Customer</a></li>
    <li><a href="addItem.jsp">Add Item</a></li>
    <li><a href="editItem.jsp">Update Item</a></li>
    <li><a href="deleteItem.jsp">Delete Item</a></li>
    <li><a href="../displayItems">Display Items</a></li>
    <li><a href="../displayAccounts">Display Customer Accounts</a></li>
    <li><a href="calculateBill.jsp">Calculate and Print Bill</a></li>
    <li><a href="help.jsp">Help</a></li>
    <li><a href="./index.jsp">Logout (Exit)</a></li>
</ul>
</body>
</html>
