<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="cardFormat" uri="/WEB-INF/customCardNumberFromat.tld" %>

<html>
<head>
    <title>Card top up</title>
</head>
<body>

<%@include file="/jspf/navBar.jspf" %>

<c:if test="${requestScope.card != null}">
    <p>Number: <cardFormat:formatCardNumber number="${requestScope.card.number}"/></p>
    <p>Money <c:out value="${requestScope.card.money}">.</c:out></p>

</c:if>


<form action="${Path.CARD_TOP_UP_PATH}" method="post">

    <label for="money">Input money</label>
    <input type="number" name="money" id="money" min="1" max="10000">
    <br>

    <input type="hidden" id="card" name="id" value="${requestScope.card.id}"/>


    <input type="submit" name="submitBtn" id="submitBtn" value="Top up">


</form>
</body>
</html>
