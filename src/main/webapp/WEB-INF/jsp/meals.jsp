<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<section>
    <h3><a href="/topjava"><spring:message code="app.home"/></a></h3>
    <hr/>
    <h2><spring:message code="meal.title"/></h2>
    <form method="get" action="meals/filter">
        <input type="hidden" name="action" value="meals/filter">
        <dl>
            <dt><spring:message code="app.from_date_incl"/>:</dt>
            <dd><input type="date" name="startDate"></dd>
        </dl>
        <dl>
            <dt><spring:message code="app.to_date_incl"/>:</dt>
            <dd><input type="date" name="endDate"></dd>
        </dl>
        <dl>
            <dt><spring:message code="app.from_time_incl"/>:</dt>
            <dd><input type="time" name="startTime"></dd>
        </dl>
        <dl>
            <dt><spring:message code="app.to_time_excl"/>:</dt>
            <dd><input type="time" name="endTime"></dd>
        </dl>
        <button type="submit"><spring:message code="common.filter"/></button>
    </form>
    <hr/>
    <a href="meals/create"><spring:message code="app.add_meal"/></a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="app.date_time"/></th>
            <th><spring:message code="app.description"/></th>
            <th><spring:message code="app.calories"/></th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${requestScope.meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr data-meal-excess="${meal.excess}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals/update/${meal.id}"><spring:message code="common.update"/></a></td>
                <td><a href="meals/delete/${meal.id}"><spring:message code="common.delete"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>