<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<jsp:include page="fragments/header.jsp"/>
<body>


<div class="container">

    <c:choose>
        <c:when test="${groupForm['new']}">
            <h1>Add Group</h1>
        </c:when>
        <c:otherwise>
            <h1>Update Group</h1>
        </c:otherwise>
    </c:choose>
    <br />


    <form:form class="form-horizontal" method="post"
               modelAttribute="groupForm" action="${pageContext.request.contextPath}/saveGroup">

        <form:hidden path="id" />

        <spring:bind path="name">
            <div class="form-group">
                <label class="col-sm-2 control-label">Name</label>
                <div class="col-sm-10">
                    <form:input path="name" type="text" class="form-control"
                                id="name" placeholder="Name" />
                </div>
            </div>
        </spring:bind>

        <spring:bind path="link">
            <div class="form-group">
                <label class="col-sm-2 control-label">Link</label>
                <div class="col-sm-10">
                    <form:input path="link" class="form-control"
                                id="link" placeholder="Price" />
                    <form:errors path="link" class="control-label" />
                </div>
            </div>
        </spring:bind>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${groupForm['new']}">
                        <button type="submit" class="btn-lg btn-primary pull-right">Add
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn-lg btn-primary pull-right">Update
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>

</div>

</body>
</html>
