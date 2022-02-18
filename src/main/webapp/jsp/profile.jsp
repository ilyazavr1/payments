<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@include file="/jspf/navBar.jspf" %>

<html>
<head>

    <title>Profile</title>
</head>
<body>



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

<a href="${Path.CARDS_PATH}?page=2&records=6">My cards</a><br/>
<a href="${Path.PAYMENT_PATH}">My payments</a><br/>


</body>
</html>
