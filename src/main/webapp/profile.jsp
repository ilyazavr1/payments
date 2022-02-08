<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Profile</title>
</head>
<body>
<h1>Profile</h1>

<c:if test="${sessionScope.user != null}">

    <p>User id: <c:out value="${sessionScope.user.id}"></c:out></p>
    <p>User name: <c:out value="${sessionScope.user.firstName}"></c:out></p>
    <p>User last name: <c:out value="${sessionScope.user.lastName}"></c:out></p>
</c:if>

<c:if test="${sessionScope.user == null}">
<p>User is null</p>
</c:if>

</body>
</html>
