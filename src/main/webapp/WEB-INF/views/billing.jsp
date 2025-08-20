<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pahanaedu.util.Constants" %>
<html>
<head>
    <title>Create Bill - Pahana Edu Bookshop</title>
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
        .form-container {
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }
        select, input[type="number"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .bill-details {
            margin-top: 20px;
        }
        .bill-details p {
            margin: 5px 0;
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
        <h2>Create Bill</h2>
        <div class="form-container">
            <form action="billing" method="post">
                <div class="form-group">
                    <label for="customerId">Customer:</label>
                    <select id="customerId" name="customerId" required>
                        <option value="" disabled selected>Select a customer</option>
                        <c:forEach var="customer" items="${customers}">
                            <option value="${customer.id}"><c:out value="${customer.name} (${customer.accountNumber})"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="itemId">Item:</label>
                    <select id="itemId" name="itemId" required>
                        <option value="" disabled selected>Select an item</option>
                        <c:forEach var="item" items="${items}">
                            <option value="${item.id}"><c:out value="${item.name} (${item.itemId}, Stock: ${item.stock})"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="unitsConsumed">Units Consumed:</label>
                    <input type="number" id="unitsConsumed" name="unitsConsumed" min="1" step="1" required>
                </div>
                <input type="submit" value="Calculate Bill">
            </form>
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>
            <c:if test="${not empty message}">
                <p class="message">${message}</p>
            </c:if>
        </div>
        <c:if test="${not empty bill}">
            <div class="bill-details">
                <h3>Bill Details</h3>
                <p><strong>Customer:</strong> <c:out value="${bill.customer.name} (${bill.customer.accountNumber})"/></p>
                <p><strong>Item:</strong> <c:out value="${bill.item.name} (${bill.item.itemId})"/></p>
                <p><strong>Units Consumed:</strong> <c:out value="${bill.unitsConsumed}"/></p>
                <p><strong>Bill Amount:</strong> <c:out value="${bill.billAmount}"/></p>
            </div>
        </c:if>
        <div class="nav-links">
            <a href="dashboard.jsp">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>