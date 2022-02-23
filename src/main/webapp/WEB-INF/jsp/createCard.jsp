<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@include file="/jspf/navBar.jspf" %>


<html>
<head>
    <title>Create card</title>
</head>
<body>

<form action="${Path.CARD_CREATE_PATH}" method="post">



    <div class="container">


        <div class="row">

            <div style="width:400px; margin-top:30%; margin-left: 30%" class="card">
                <article class="card-body">

                    <h4 class="card-title mb-4 mt-1">Create card</h4>
                    <form action="   " method="post">
                        <div class="form-group">
                            <label>Input name</label>

                            <input class="form-control" type="text" value="card" name="cardName">
                            <c:if test="${requestScope.emptyCardName != null}">
                                <p style="color: red"><fmt:message key="emptyCardName"> </fmt:message> </p>
                            </c:if>
                        </div>

                        <div class="form-group mx-sm-3 mb-2">
                            <button style="margin-left: 150px" type="submit" class="btn btn-primary"> Confirm</button>
                        </div>

                      <%--  <input type="hidden" name="cardId" value="${requestScope.cardId}">
                        <input type="hidden" name="adminPathToRedirect" value="${requestScope.adminPathToRedirect}">--%>

                    </form>
                </article>
            </div>


        </div>

    </div>

</form>


</body>
</html>
