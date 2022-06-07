<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Meal list</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal list</h2>
<table>
    <table border="1">
        <caption>Meal list</caption>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        <%List<MealTo> mealList = (List) request.getAttribute("mealList"); %>

        <c:forEach items="${mealList}" var="meal">
            <tr style="color:${meal.excess ? 'red' : 'green'}">
                <td>${meal.date} ${meal.time}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>Update</td>
                <td>Delete</td>
            </tr>
        </c:forEach>

    </table>
</table>
</body>
</html>