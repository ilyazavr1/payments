<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="acc" uri="/WEB-INF/customAccountNumberFromat.tld" %>

<html>
<head>
    <title>Payments</title>
</head>
<body>

<p>hello</p>

<form action="${Path.PAYMENT_PATH}" method="post">
    <select name="accountSenderId">
        <c:forEach items="${requestScope.accounts}" var="account">
            <option value="${account.id}"><acc:formatAccountNumber number="${account.number}"/></option>
        </c:forEach>
    </select>
    <br>



    <select name="accountDestinationId">
        <c:forEach items="${requestScope.accounts}" var="account">
            <option disabled value="${account.id}"><acc:formatAccountNumber number="${account.number}"/></option>
        </c:forEach>
    </select>
    <br>


    <label for="money">Input money</label>
    <input type="number" name="money" id="money" min="1" max="10000">
    <br>


    <input type="submit" value="go">
</form>

</body>
</html>
