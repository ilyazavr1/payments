<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>

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
    <p>User surname: <c:out value="${sessionScope.user.surname}"></c:out></p>
    <p>User email: <c:out value="${sessionScope.user.email}"></c:out></p>
    <p>User password: <c:out value="${sessionScope.user.password}"></c:out></p>
    <p>User blocked: <c:out value="${sessionScope.user.blocked}"></c:out></p>
    <p>User rolesId: <c:out value="${sessionScope.user.rolesId}"></c:out></p>
    <p>User rolesId: <c:out value="${sessionScope.user.rolesId}"></c:out></p>
</c:if>

<c:if test="${sessionScope.user == null}">
<p>User is null</p>
</c:if>

<a href="${Path.ACCOUNTS_PATH}">My cards</a><br/>
<a href="${Path.PAYMENT_PATH}">My payments</a><br/>

</body>
</html>
