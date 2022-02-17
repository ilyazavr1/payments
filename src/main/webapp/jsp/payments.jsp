<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="cardFormat" uri="/WEB-INF/customCardNumberFromat.tld" %>

<html>
<head>
    <title>Payments</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>

<form action="${Path.PAYMENT_PATH}" method="post">
    <select name="cardSenderId">
        <c:forEach items="${requestScope.cards}" var="card">
            <option value="${card.id}"><cardFormat:formatCardNumber number="${card.number}"/></option>
        </c:forEach>
    </select>
    <br>



    <select name="cardDestinationId">
        <c:forEach items="${requestScope.cards}" var="card">
            <option  value="${card.id}"><cardFormat:formatCardNumber number="${card.number}"/></option>
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
