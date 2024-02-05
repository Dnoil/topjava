<%@ page import="ru.javawebinar.topjava.model.MealTo, java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<body>
    <table border="1">
        <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${meals}" var="meal">
                <tr style="color:${meal.excess ? 'red' : 'green'}">
                        <td><fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"/>
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}"/></td>
                        <td>${meal.description} </td>
                        <td>${meal.calories}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>