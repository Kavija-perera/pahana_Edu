<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pahanaedu.util.Constants" %>
<html>
<head>
    <title>Dashboard - Pahana Edu Bookshop</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f0f2f5;
        }
        .container {
            max-width: 800px;
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
        .welcome {
            text-align: center;
            margin-bottom: 20px;
            color: #555;
        }
        .nav-links {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 15px;
        }
        .nav-links a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .nav-links a:hover {
            background-color: #45a049;
        }
        .error {
            color: red;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <c:if test="${empty sessionScope.user}">
            <%
                response.sendRedirect("login");
                return;
            %>
        </c:if>
        <h2>Pahana Edu Bookshop Dashboard</h2>
        <div class="welcome">
            <p>Welcome, <c:out value="${sessionScope.user.username}"/>! (Role: <c:out value="${sessionScope.user.role}"/>)</p>
        </div>
        <div class="nav-links">
            <a href="customer?action=list">Manage Customers</a>
            <c:if test="${sessionScope.user.role == 'admin'}">
                <a href="item?action=list">Manage Items</a>
            </c:if>
            <a href="billing">Create Bill</a>
            <a href="help">Help & Support</a>
            <a href="logout">Logout</a>
        </div>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
    </div>
</body>
</html>