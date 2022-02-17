<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="cardFormat" uri="/WEB-INF/customCardNumberFromat.tld" %>

<html>
<head>
    <title>My payments</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>


<c:if test="${requestScope.payments != null}">

    <c:forEach items="${requestScope.payments}" var="payment">

        <p>id      <c:out value="${payment.id}"> </c:out></p>
        <p>money   <c:out value="${payment.money}"> </c:out></p>
        <p>idFrom  <c:out value="${payment.cardSenderId}"> </c:out></p>
        <p>idTo    <c:out value="${payment.cardDestinationId}"> </c:out></p>

    </c:forEach>

</c:if>

</body>
</html>
