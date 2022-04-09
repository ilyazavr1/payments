<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@include file="/jspf/navBar.jspf" %>


<html>
<head>
    <title><fmt:message key="cardCreate"/></title>
</head>
<body>

<form action="${Path.CARD_CREATE_PATH}" method="post">

    <div class="container">
        <div class="row">

            <div style="width:400px; margin-top:30%; margin-left: 30%" class="card">
                <article class="card-body">

                    <h4 class="card-title mb-4 mt-1"><fmt:message key="cardCreate"/></h4>

                        <div class="form-group">
                            <label><fmt:message key="nameInput"/></label>

                            <input class="form-control" type="text" value="card" name="cardName">
                            <c:if test="${requestScope.invalidCardName != null}">
                                <p style="color: red"><fmt:message key="invalidCardName"/></p>
                            </c:if>
                            <c:if test="${requestScope.failedToCreateCard != null}">
                                <p style="color: red"><fmt:message key="failedToCreateCard"/></p>
                            </c:if>
                        </div>

                        <div class="form-group mx-sm-3 mb-2">
                            <button style="margin-left: 150px" type="submit" class="btn btn-primary"><fmt:message key="create"/> </button>
                        </div>


                </article>
            </div>


        </div>

    </div>

</form>


</body>
</html>
