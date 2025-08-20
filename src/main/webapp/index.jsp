<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.pahanaedu.util.Constants" %>
<html>
<head>
    <title>Welcome - Pahana Edu Bookshop</title>
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
            text-align: center;
            color: #333;
        }
        h2 {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Pahana Edu Bookshop</h2>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <%
                    response.sendRedirect("dashboard.jsp");
                %>
            </c:when>
            <c:otherwise>
                <%
                    response.sendRedirect("login");
                %>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>