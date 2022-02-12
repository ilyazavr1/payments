<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>


<html>
<head>
    <title>Accounts</title>
</head>
<body>

<h1>Accounts</h1>

<p>Add card </p>

<c:if test="${sessionScope.accounts == null}">
    <h1>Yot do not have any accounts</h1>
</c:if>

<c:if test="${sessionScope.accounts != null}">
    <c:forEach var="account" items="${sessionScope.accounts }">

        <p>Account id:     <c:out value="${account.id}"></c:out></p>
        <p>Account number: <c:out value="${account.number}"></c:out></p>
        <p>Account money:  <c:out value="${account.money}"></c:out></p>

    </c:forEach>
</c:if>


<form action="${Path.ACCOUNTS_PATH}" method="post">

<input type="submit" name="createAccount" value="Add account">
</form>


</body>
</html>
