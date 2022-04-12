<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>

<html>
<head>
    <%@include file="/jspf/bootstrap.jspf" %>
    <title>Block card</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>

<div class="container">


    <div class="row">

        <div style="width:400px; margin-top:30%; margin-left: 30%" class="card">
            <article class="card-body">

                <h4 class="card-title mb-4 mt-1">Confirm</h4>
                <form action="${Path.CARD_BLOCK_PATH}" method="post">
                    <div class="form-group">
                        <label>Your password</label>
                        <input name="password" class="form-control" placeholder="<fmt:message key="password"/> "
                               type="password">

                    </div>
                    <c:if test="${requestScope.passwordNotValid != null}">
                        <p style="color: red"><fmt:message key="${requestScope.passwordNotValid}"/> </p>
                    </c:if>
                    <c:if test="${requestScope.wrongPassword != null}">
                        <p style="color: red"><fmt:message key="${requestScope.wrongPassword}"/></p>
                    </c:if>
                    <div class="form-group mx-sm-3 mb-2">
                        <a style="background-color: lightcoral" href="${Path.CARDS_PATH}"
                           class="btn btn-outline-secondary"><fmt:message key="cancel"/></a>
                        <%-- <button type="submit" class="btn btn-danger"> Cancel</button>--%>
                        <button style="margin-left: 150px" type="submit" class="btn btn-primary"> <fmt:message key="confirm"/></button>
                    </div>


                    <input type="hidden" name="cardId" value="${requestScope.cardId}">


                </form>
            </article>
        </div> <!-- card.// -->


    </div> <!-- row.// -->

</div>

</body>
</html>
