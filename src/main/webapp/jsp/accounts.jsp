<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="acc" uri="/WEB-INF/customAccountNumberFromat.tld" %>

<html>
<head>
    <title>Accounts</title>
</head>
<body>

<h1>Accounts</h1>

<p>Add card </p>

<c:if test="${requestScope.accounts == null}">
    <h1>Yot do not have any accounts</h1>
</c:if>
<c:out value="${requestScope.accounts.size()}"> size</c:out>

<c:if test="${requestScope.accounts != null}">
    <c:forEach var="account" items="${requestScope.accounts }">

        <c:url var="accountID"  value="${Path.ACCOUNT_TOP_UP_PATH}">
            <c:param name="id" value="${account.id}"/>
        </c:url>

        <p>Account id: <c:out value="${account.id}"> </c:out></p>
        <p>Account number: <acc:formatAccountNumber number="${account.number}"/></p>
        <p>Account money: <c:out value="${account.money}"> </c:out></p>

        <a href="${accountID}"> TOP UP</a>
    </c:forEach>
</c:if>


<form action="${Path.ACCOUNTS_PATH}" method="post">

    <input type="submit" name="createAccount" value="Add account">
</form>


</body>
</html>
