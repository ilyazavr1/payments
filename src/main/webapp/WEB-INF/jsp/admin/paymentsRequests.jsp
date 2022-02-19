<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>

<html>
<head>
    <title>Payments requests</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>


<%--<c:forEach var="payment" items="${requestScope.payments}">
    <p><c:out value="${payment.id}"> </c:out></p>
    <p><c:out value="${payment.status}"> </c:out></p>
    <p><c:out value="${payment.creationTimestamp}"> </c:out></p>
    <p><c:out value="${payment.senderCardNumber}"> </c:out></p>
    <p><c:out value="${payment.senderFullName}"> </c:out></p>
    <p><c:out value="${payment.destinationCardNumber}"> </c:out></p>
    <p><c:out value="${payment.destinationFullName}"> </c:out></p>
    <p><c:out value="${payment.money}"> </c:out></p>

</c:forEach>--%>
<div class="row col-md-6 col-md-offset-2 custyle">
    <table class="table table-striped custab">
        <thead>
        <%--  <a href="#" class="btn btn-primary btn-xs pull-right"><b>+</b> Add new categories</a>--%>

        <tr>
            <th>Payment ID</th>
            <th style="padding-right: 100px">Sender</th>
            <th style="padding-right: 200px">Sender card</th>
            <th>Money</th>
            <th style="padding-right: 100px">Recipient</th>
            <th style="padding-right: 200px">Recipient card</th>
            <th style="padding-right: 100px">Time</th>
            <th>Status</th>

            <th class="text-center">Action</th>
        </tr>
        </thead>
        <c:if test="${requestScope.payments != null}">

            <c:forEach var="payment" items="${requestScope.payments }">

                <tr style="margin-right: 100px">
                    <td>${payment.id}</td>
                    <td>${payment.senderFullName}</td>
                    <td><cardFormat:formatCardNumber number="${payment.senderCardNumber}"></cardFormat:formatCardNumber></td>
                    <td style="background-color: #cdf7ff">${payment.money}</td>
                    <td>${payment.destinationFullName}</td>
                    <td>  <cardFormat:formatCardNumber number="${payment.destinationCardNumber}"></cardFormat:formatCardNumber></td>
                    <%--<td>${payment.creationTimestamp}</td>--%>
                    <td><cardFormat:dateTimeFormat dateTime="${payment.creationTimestamp}"></cardFormat:dateTimeFormat></td>
                        <%-- <td>${payment.status}</td>--%>

                    <c:if test="${payment.status.equals('PREPARED')}">
                        <td><span class="badge badge-primary">PREPARED</span></td>
                    </c:if>
                    <c:if test="${!payment.status.equals('PREPARED')}">
                        <td><span class="btn btn-success">SENT</span></td>
                    </c:if>
                    <c:if test="${payment.status.equals('PREPARED')}">
                        <form action="${Path.ADMIN_CONFIRM_PAYMENT_PATH}" method="post">
                            <td><input type="submit" class="btn btn-success" value="Confirm"></td>
                            <input type="hidden" name="paymentId" value="${payment.id}">
                        </form>
                    </c:if>

                        <%--   <td> <a href="#" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Block</a></td>--%>
                    <%--<c:choose>
                    </c:choose>--%>

                </tr>

            </c:forEach>
        </c:if>

    </table>
</div>
</body>
</html>
