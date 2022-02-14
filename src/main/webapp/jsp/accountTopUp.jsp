<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="acc" uri="/WEB-INF/customAccountNumberFromat.tld" %>

<html>
<head>
    <title>Acc top up</title>
</head>
<body>
<h1>Top ur account</h1>


<c:if test="${requestScope.account != null}">
    <p>Number: <acc:formatAccountNumber number="${requestScope.account.number}"/></p>
    <p>Money <c:out value="${requestScope.account.money}">.</c:out></p>

</c:if>


<form action="${Path.ACCOUNT_TOP_UP_PATH}" method="post">

    <label for="money">Input money</label>
    <input type="number" name="money" id="money" min="1" max="10000">
    <br>

    <input type="hidden" id="account" name="id" value="${requestScope.account.id}"/>


    <input type="submit" name="submitBtn" id="submitBtn" value="Top up">


</form>
</body>
</html>
