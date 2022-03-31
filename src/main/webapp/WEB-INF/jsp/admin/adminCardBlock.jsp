<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="ufn" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <%@include file="/jspf/bootstrap.jspf" %>
    <title>Block user card</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>
<div class="container">


    <div class="row">

        <div style="width:400px; margin-top:30%; margin-left: 30%" class="card">
            <article class="card-body">

                <h4 class="card-title mb-4 mt-1">Confirm</h4>
                <form action="${Path.ADMIN_CARD_BLOCK_PATH}" method="post">
                    <div class="form-group">
                        <label>Your password</label>
                        <input name="password" class="form-control" placeholder="<fmt:message key="password"/> "
                               type="password">

                    </div>
                    <c:if test="${requestScope.passwordNotValid != null}">
                        <p style="color: red"><fmt:message key="${requestScope.passwordNotValid}"> </fmt:message></p>
                    </c:if>
                    <c:if test="${requestScope.wrongPassword != null}">
                        <p style="color: red"><fmt:message key="${requestScope.wrongPassword}"> </fmt:message></p>
                    </c:if>
                    <div class="form-group mx-sm-3 mb-2">
                        <a style="background-color: lightcoral" href="${Path.PROFILE_PATH}"
                           class="btn btn-outline-secondary">Cancel</a>
                        <%-- <button type="submit" class="btn btn-danger"> Cancel</button>--%>
                        <button style="margin-left: 150px" type="submit" class="btn btn-primary"> Confirm</button>
                    </div>


                    <input type="hidden" name="cardId" value="${requestScope.cardId}">


                </form>
            </article>
        </div> <!-- card.// -->


    </div> <!-- row.// -->

</div>



</body>
</html>
