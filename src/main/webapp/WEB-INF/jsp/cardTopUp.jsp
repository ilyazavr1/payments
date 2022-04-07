<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>


<html>
<head>
    <%@include file="/jspf/bootstrap.jspf" %>
    <title>Card top up</title>
</head>
<body>

<%@include file="/jspf/navBar.jspf" %>


<div class="container">


    <div class="row">

        <div style="width:450px; margin-top:30%; margin-left: 30%" class="card">
            <article class="card-body">

                <h4 class="card-title mb-4 mt-1"><fmt:message key="topUp"/> </h4>
                <h4 class="card-title mb-4 mt-1"><fmt:message key="number"/>: <cardFormat:formatCardNumber
                        number="${requestScope.card.number}"/></h4>
                <p><c:out value="${requestScope.card.money} uah"> </c:out></p>

                <form action="${Path.CARD_CONFIRM_TOP_UP_PATH}" method="post">
                    <div class="form-group">
                        <label><fmt:message key="amountInput"/></label>
                        <input name="money" class="form-control" type="number" >
                        <c:if test="${requestScope.invalidMoneyAmount != null}">
                           <p style="color: red"> <fmt:message key="${requestScope.invalidMoneyAmount}"/></p>
                        </c:if>
                    </div>
                    <input type="hidden" id="card" name="id" value="${requestScope.card.id}"/>

                    <div class="form-group mx-sm-3 mb-2">
                        <a  href="${Path.CARDS_PATH}"
                           class="btn btn-danger"><fmt:message key="cancel"/></a>

                        <button style="margin-left: 150px" type="submit" class="btn btn-primary"> <fmt:message key="confirm"/></button>
                    </div>

                </form>
            </article>
        </div>


    </div>

</div>
</body>
</html>
