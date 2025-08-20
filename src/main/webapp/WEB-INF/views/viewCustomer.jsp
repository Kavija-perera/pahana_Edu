<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pahanaedu.util.Constants" %>
<html>
<head>
    <title>View Customers - Pahana Edu Bookshop</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f0f2f5;
        }
        .container {
            max-width: 1000px;
            margin: 20px auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ccc;
            text-align: left;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .action-links a {
            margin-right: 10px;
            color: #007bff;
            text-decoration: none;
        }
        .action-links a:hover {
            text-decoration: underline;
        }
        .error {
            color: red;
            font-size: 14px;
            text-align: center;
            margin-top: 10px;
        }
        .message {
            color: green;
            font-size: 14px;
            text-align: center;
            margin-top: 10px;
        }
        .nav-links {
            text-align: center;
            margin-top: 20px;
        }
        .nav-links a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 10px;
        }
        .nav-links a:hover {
            background-color: #45a049;
        }
        .customer-details {
            margin-top: 20px;
        }
        .customer-details p {
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <c:if test="${empty sessionScope.user}">
        <%
            response.sendRedirect("login");
            return;
        %>
    </c:if>
    <div class="container">
        <h2>Customer Management</h2>
        <c:choose>
            <c:when test="${not empty customer}">
                <!-- Display single customer details -->
                <div class="customer-details">
                    <h3>Customer Details</h3>
                    <p><strong>Account Number:</strong> <c:out value="${customer.accountNumber}"/></p>
                    <p><strong>Name:</strong> <c:out value="${customer.name}"/></p>
                    <p><strong>Address:</strong> <c:out value="${customer.address}"/></p>
                    <p><strong>Telephone Number:</strong> <c:out value="${customer.telephoneNumber}"/></p>
                    <p><strong>Units Consumed:</strong> <c:out value="${customer.unitsConsumed}"/></p>
                    <div class="action-links">
                        <a href="customer?action=edit&id=${customer.id}">Edit</a>
                        <a href="customer?action=delete&id=${customer.id}" onclick="return confirm('Are you sure you want to delete this customer?')">Delete</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Display list of customers -->
                <h3>All Customers</h3>
                <c:if test="${empty customers}">
                    <p>No customers found.</p>
                </c:if>
                <c:if test="${not empty customers}">
                    <table>
                        <tr>
                            <th>Account Number</th>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Telephone Number</th>
                            <th>Units Consumed</th>
                            <th>Actions</th>
                        </tr>
                        <c:forEach var="customer" items="${customers}">
                            <tr>
                                <td><c:out value="${customer.accountNumber}"/></td>
                                <td><c:out value="${customer.name}"/></td>
                                <td><c:out value="${customer.address}"/></td>
                                <td><c:out value="${customer.telephoneNumber}"/></td>
                                <td><c:out value="${customer.unitsConsumed}"/></td>
                                <td class="action-links">
                                    <a href="customer?action=view&id=${customer.id}">View</a>
                                    <a href="customer?action=edit&id=${customer.id}">Edit</a>
                                    <a href="customer?action=delete&id=${customer.id}" onclick="return confirm('Are you sure you want to delete this customer?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <c:if test="${not empty message}">
            <p class="message">${message}</p>
        </c:if>
        <div class="nav-links">
            <a href="customer?action=add">Add New Customer</a>
            <a href="dashboard.jsp">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>