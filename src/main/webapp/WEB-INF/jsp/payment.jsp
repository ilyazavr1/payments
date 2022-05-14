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


    <div class="card card-outline-secondary">
        <div class="card-body">
            <h3 class="text-center"><fmt:message key="makePayment"/> </h3>
            <hr>

            <form class="form" role="form" action="${Path.PAYMENT_PATH}" method="post">
                <div class="form-group">
                    <label for="cardSender"><fmt:message key="myCards"/></label>
                    <select name="cardSenderId" id="cardSender" class="form-control" name="cardSenderId">
                        <c:forEach items="${requestScope.cards}" var="card">
                            <c:set var="cardSender" value="${card.blocked}"> </c:set>
                            <option value="${card.id}"><cardFormat:formatCardNumber number="${card.number}"/> [<fmt:message key="name"/>: ${card.name}  <fmt:message key="balance"/>: ${card.money}]</option>
                        </c:forEach>
                    </select>
                    <c:if test="${requestScope.cardSenderIsBlocked != null}">
                        <p style="color: red"><fmt:message key="${requestScope.cardSenderIsBlocked}"> </fmt:message></p>
                    </c:if>
                    <c:if test="${requestScope.outOfMoney != null}">
                        <p style="color: red"><fmt:message key="${requestScope.outOfMoney}"> </fmt:message></p>
                    </c:if>
                </div>
                <div class="form-group">
                    <label><fmt:message key="recipientCard"/></label>
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
                    <label class="col-md-12"><fmt:message key="amount"/></label>
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

                <c:choose>
                    <c:when test="${requestScope.cards.isEmpty()}">
                        <div class="form-group row">
                            <div class="col-md-6">
                                <button disabled name="prepare" type="submit" class="btn btn-warning btn-lg btn-block"><fmt:message key="prepare"/></button>
                            </div>
                            <div class="col-md-6">
                                <button disabled name="send" type="submit" class="btn btn-success btn-lg btn-block"><fmt:message key="send"/></button>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="form-group row">
                            <div class="col-md-6">
                                <button name="prepare" type="submit" class="btn btn-warning btn-lg btn-block"><fmt:message key="prepare"/></button>
                            </div>
                            <div class="col-md-6">
                                <button name="send" type="submit" class="btn btn-success btn-lg btn-block"><fmt:message key="send"/></button>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>


            </form>
        </div>
    </div>
</div>


</body>
</html>
