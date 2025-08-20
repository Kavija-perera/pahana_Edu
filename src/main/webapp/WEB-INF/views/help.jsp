<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pahanaedu.util.Constants" %>
<html>
<head>
    <title>Help & Support - Pahana Edu Bookshop</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background-color: #f0f2f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .container {
            max-width: 600px;
            margin: 20px;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        h2, h3 {
            text-align: center;
            color: #333;
        }
        p, li {
            color: #555;
            line-height: 1.6;
        }
        ul {
            list-style-type: disc;
            padding-left: 20px;
        }
        .error {
            color: red;
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
        }
        .nav-links a:hover {
            background-color: #45a049;
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
        <h2>Help & Support</h2>
        <h3>Welcome to Pahana Edu Bookshop</h3>
        <p>This page provides guidance on using the system. If you need further assistance, please contact our support team.</p>
        <h3>How to Use the System</h3>
        <ul>
            <li><strong>Login</strong>: Use your username and password to access the system. Contact your administrator if you don't have credentials.</li>
            <li><strong>Dashboard</strong>: After logging in, the dashboard provides access to all features based on your role (Admin or Staff).</li>
            <li><strong>Manage Customers</strong>: Add, edit, or view customer details (available to all users). Use the "Manage Customers" link to start.</li>
            <li><strong>Manage Items</strong>: Admins can add, edit, or delete items in the inventory. Use the "Manage Items" link on the dashboard (admin-only).</li>
            <li><strong>Create Bills</strong>: Select a customer and item, enter units consumed, and calculate the bill. Use the "Create Bill" link.</li>
            <li><strong>Logout</strong>: Always log out after completing your tasks to secure your session.</li>
        </ul>
        <h3>Frequently Asked Questions</h3>
        <ul>
            <li><strong>How do I reset my password?</strong> Contact the system administrator to reset your password.</li>
            <li><strong>Why can't I access certain features?</strong> Some features (e.g., item management) are restricted to admin users. Check your role on the dashboard.</li>
            <li><strong>What if I encounter an error?</strong> Note the error message and contact support with details.</li>
        </ul>
        <h3>Contact Support</h3>
        <p>For additional help, email our support team at <a href="mailto:support@pahanaedu.com">support@pahanaedu.com</a>.</p>
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
        <div class="nav-links">
            <a href="dashboard.jsp">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>