<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<jsp:include page="fragments/header.jsp"/>
<body>

<div class="container">
    <a href="<%=request.getContextPath()%>/addGroup">Add Group</a>

    <c:forEach var="u" items="${user}">
        <p>${u.name} ${u.lastname}</p>
    </c:forEach>
</div>

<div class="container">

    <h1>All Groups</h1>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Photo</th>
            <th>Name</th>
            <th>Is member</th>
            <th>Posts for today</th>
        </tr>
        </thead>

        <c:forEach var="g" items="${groups}">
            <tr>
                <td><img src="${g.photo}"></td>
                <td><a href="${g.link}"/>${g.name}</td>
                <td>${g.subscribed}</td>
                <td>${g.postsSize}</td>
            </tr>
        </c:forEach>
    </table>

</div>

</body>
</html>
