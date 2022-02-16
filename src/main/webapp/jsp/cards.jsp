<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="cardFormat" uri="/WEB-INF/customCardNumberFromat.tld" %>

<html>
<head>
    <title>Cards</title>
</head>
<body>

<h1>Cards</h1>

<p>Add card </p>

<c:if test="${requestScope.cards == null}">
    <h1>Yot do not have any cards</h1>
</c:if>
<c:out value="${requestScope.cards.size()}"> size</c:out>

<c:if test="${requestScope.cards != null}">
    <c:forEach var="card" items="${requestScope.cards }">

        <c:url var="cardID"  value="${Path.CARD_TOP_UP_PATH}">
            <c:param name="id" value="${card.id}"/>
        </c:url>

        <p>Card id: <c:out value="${card.id}"> </c:out></p>
        <p>Card number: <cardFormat:formatCardNumber number="${card.number}"/></p>
        <p>Card money: <c:out value="${card.money}"> </c:out></p>
        <p>Card blocked: <c:out value="${card.blocked}"> </c:out></p>

        <a href="${cardID}"> TOP UP</a>
    </c:forEach>
</c:if>


<form action="${Path.CARD_PATH}" method="post">

    <input type="submit" name="createCart" value="Add card">
</form>


</body>
</html>
