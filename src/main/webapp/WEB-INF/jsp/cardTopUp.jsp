<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>


<html>
<head>
    <%@include file="/jspf/bootstrap.jspf" %>
    <title>Card top up</title>
</head>
<body>

<%@include file="/jspf/navBar.jspf" %>


<%--
<form action="${Path.CARD_TOP_UP_PATH}" method="post">

    <label for="money">Input money</label>
    <input type="number" name="money" id="money" min="1" max="10000">
    <br>

    &lt;%&ndash;<input type="hidden" id="card" name="id" value="${requestScope.card.id}"/>&ndash;%&gt;


    <input type="submit" name="submitBtn" id="submitBtn" value="Top up">


</form>--%>
<div class="container">


    <div class="row">

        <div style="width:400px; margin-top:30%; margin-left: 30%" class="card">
            <article class="card-body">

                <h4 class="card-title mb-4 mt-1">Top up</h4>
                <h4 class="card-title mb-4 mt-1">Number: <cardFormat:formatCardNumber
                        number="${requestScope.card.number}"/></h4>
                <p><c:out value="${requestScope.card.money} uah"> </c:out></p>

                <form action="${Path.CARD_TOP_UP_PATH}" method="post">
                    <div class="form-group">
                        <label>Enter the amount</label>
                        <input name="money" class="form-control" type="number" >
                        <c:if test="${requestScope.invalidMoneyAmount != null}">
                           <p style="color: red"> <fmt:message key="${requestScope.invalidMoneyAmount}"></fmt:message></p>
                        </c:if>
                    </div>
                    <input type="hidden" id="card" name="id" value="${requestScope.card.id}"/>

                    <div class="form-group mx-sm-3 mb-2">
                        <button type="submit" class="btn btn-danger"> Cancel</button>
                        <button style="margin-left: 150px" type="submit" class="btn btn-primary"> Confirm</button>
                    </div>

                </form>
            </article>
        </div>


    </div>

</div>
</body>
</html>
