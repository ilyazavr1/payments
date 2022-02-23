<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>


<html>
<head>

    <title>User</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>

<p><c:out value="${requestScope.user.firstName}"></c:out></p>


<c:forEach var="card" items="${requestScope.cards}">
    <p><c:out value="${card.number}"></c:out></p>
</c:forEach>

</body>
</html>
