<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pahanaedu.util.Constants" %>
<html>
<head>
    <title>Manage Items - Pahana Edu Bookshop</title>
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
        input[type="text"], input[type="number"] {
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
        }
        .nav-links a:hover {
            background-color: #45a049;
        }
        .item-details {
            margin-top: 20px;
        }
        .item-details p {
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
    <c:if test="${sessionScope.user.role != 'admin'}">
        <%
            request.setAttribute("error", "Access denied: Admin privileges required");
            request.getRequestDispatcher(Constants.DASHBOARD_JSP).forward(request, response);
            return;
        %>
    </c:if>
    <div class="container">
        <h2>Manage Items</h2>
        <div class="form-container">
            <h3>${editMode ? 'Edit Item' : 'Add New Item'}</h3>
            <form action="item" method="post">
                <input type="hidden" name="action" value="${editMode ? 'update' : 'add'}">
                <input type="hidden" name="id" value="${editMode ? item.id : ''}">
                <div class="form-group">
                    <label for="itemId">Item ID:</label>
                    <input type="text" id="itemId" name="itemId" value="${editMode ? item.itemId : ''}" required>
                </div>
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" value="${editMode ? item.name : ''}" required>
                </div>
                <div class="form-group">
                    <label for="price">Price:</label>
                    <input type="number" id="price" name="price" min="0" step="0.01" value="${editMode ? item.price : ''}" required>
                </div>
                <div class="form-group">
                    <label for="stock">Stock:</label>
                    <input type="number" id="stock" name="stock" min="0" step="1" value="${editMode ? item.stock : ''}" required>
                </div>
                <input type="submit" value="${editMode ? 'Update Item' : 'Add Item'}">
            </form>
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>
            <c:if test="${not empty message}">
                <p class="message">${message}</p>
            </c:if>
        </div>
        <c:choose>
            <c:when test="${not empty item && not editMode}">
                <!-- Display single item details -->
                <div class="item-details">
                    <h3>Item Details</h3>
                    <p><strong>Item ID:</strong> <c:out value="${item.itemId}"/></p>
                    <p><strong>Name:</strong> <c:out value="${item.name}"/></p>
                    <p><strong>Price:</strong> <c:out value="${item.price}"/></p>
                    <p><strong>Stock:</strong> <c:out value="${item.stock}"/></p>
                    <div class="action-links">
                        <a href="item?action=edit&id=${item.id}">Edit</a>
                        <a href="item?action=delete&id=${item.id}" onclick="return confirm('Are you sure you want to delete this item?')">Delete</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Display list of items -->
                <h3>All Items</h3>
                <c:if test="${empty items}">
                    <p>No items found.</p>
                </c:if>
                <c:if test="${not empty items}">
                    <table>
                        <tr>
                            <th>Item ID</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Actions</th>
                        </tr>
                        <c:forEach var="item" items="${items}">
                            <tr>
                                <td><c:out value="${item.itemId}"/></td>
                                <td><c:out value="${item.name}"/></td>
                                <td><c:out value="${item.price}"/></td>
                                <td><c:out value="${item.stock}"/></td>
                                <td class="action-links">
                                    <a href="item?action=view&id=${item.id}">View</a>
                                    <a href="item?action=edit&id=${item.id}">Edit</a>
                                    <a href="item?action=delete&id=${item.id}" onclick="return confirm('Are you sure you want to delete this item?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </c:otherwise>
        </c:choose>
        <div class="nav-links">
            <a href="dashboard.jsp">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>