<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Meal list</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <caption>Meal list</caption>
    <a href="meals?action=create">Add Meal</a>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <c:forEach items="${mealList}" var="meal">
            <tr style="color:${meal.excess ? 'red' : 'green'}">
                <td>${meal.id}</td>
                <td><p>${f:formatLocalDateTime(meal.dateTime, 'dd.MM.yyyy hh:mm')}</p></td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>


    </table>
</section>
</body>
</html>