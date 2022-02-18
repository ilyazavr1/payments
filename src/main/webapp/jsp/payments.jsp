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

        <p>id         <c:out value="${payment.id}"> </c:out></p>
        <p>money      <c:out value="${payment.money}"> </c:out></p>
        <p>status     <c:out value="${payment.status}"> </c:out></p>
        <p>time       <c:out value="${payment.creationTimestamp}"> </c:out></p>
        <p>cardTo     <c:out value="${payment.senderCardNumber}"> </c:out></p>
        <p>cardFrom   <c:out value="${payment.destinationCardNumber}"> </c:out></p>

    </c:forEach>

</c:if>

</body>
</html>
