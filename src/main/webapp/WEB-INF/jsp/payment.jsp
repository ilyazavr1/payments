<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>


<html>
<head>
    <%@include file="/jspf/bootstrap.jspf" %>
    <title>Payments</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>


<div class="col-md-6 offset-md-3">
    <span class="anchor" id="formPayment"></span>
    <hr class="my-5">

    <!-- form card cc payment -->
    <div class="card card-outline-secondary">
        <div class="card-body">
            <h3 class="text-center">Credit Card Payment</h3>
            <hr>

            <form class="form" role="form" action="${Path.PAYMENT_PATH}" method="post">
                <div class="form-group">
                    <label for="cardSender">My cards</label>
                   <%-- <input type="text" class="form-control" id="cc_name" pattern="\w+ \w+.*" title="First and last name" required="required">--%>
                    <select name="cardSenderId" id="cardSender" class="form-control" name="cardSenderId">
                        <c:forEach items="${requestScope.cards}" var="card">
                            <c:set var="cardSender" value="${card.blocked}"> </c:set>
                            <option value="${card.id}"><cardFormat:formatCardNumber number="${card.number}"/> [name: ${card.name}  balance: ${card.money}]</option>
                        </c:forEach>
                    </select>
                    <c:if test="${requestScope.cardSenderIsBlocked != null}">
                        <p style="color: red"><fmt:message key="${requestScope.cardSenderIsBlocked}"> </fmt:message></p>
                    </c:if>
                    <c:if test="${requestScope.outOfMoney != null}">
                        <p style="color: red"><fmt:message key="${requestScope.outOfMoney}"></fmt:message></p>
                    </c:if>
                </div>
                <div class="form-group">
                    <label>Destination card number</label>
                    <input name="cardDestinationNumber" type="text" class="form-control" >
                </div>
                <c:if test="${requestScope.cardsAreSame != null}">
                    <p style="color: red"><fmt:message key="${requestScope.cardsAreSame}"> </fmt:message></p>
                </c:if>
                <c:if test="${requestScope.cardDestinationIsBlocked != null}">
                    <p style="color: red"><fmt:message key="${requestScope.cardDestinationIsBlocked}"> </fmt:message></p>
                </c:if>
                <c:if test="${requestScope.invalidCard != null}">
                    <p style="color: red"><fmt:message key="${requestScope.invalidCard}"></fmt:message></p>
                </c:if>
                <c:if test="${requestScope.invalidCardNumber != null}">
                    <p style="color: red"><fmt:message key="${requestScope.invalidCardNumber}"></fmt:message></p>
                </c:if>
                <div class="row">
                    <label class="col-md-12">Amount</label>
                </div>
                <div class="form-inline">
                    <div class="input-group">
                        <div class="input-group-prepend"><span class="input-group-text">uah</span></div>
                        <input name="money" type="text" class="form-control text-right" id="exampleInputAmount"  placeholder="0">

                    </div>
                    <c:if test="${requestScope.invalidMoneyAmount != null}">
                        <p style="color: red"><fmt:message key="${requestScope.invalidMoneyAmount}"></fmt:message></p>
                    </c:if>
                </div>
                <hr>
                <div class="form-group row">
                    <div class="col-md-6">
                        <button type="reset" class="btn btn-default btn-lg btn-block">Cancel</button>
                    </div>
                    <div class="col-md-6">
                        <button name="prepare" type="submit" class="btn btn-warning btn-lg btn-block">Prepare</button>
                    </div>
                    <div class="col-md-6">
                        <button name="send" type="submit" class="btn btn-success btn-lg btn-block">Send</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>


</body>
</html>
