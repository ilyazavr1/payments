<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>

<html>
<head>
    <%@include file="/jspf/navBar.jspf" %>
    <title>Cards unblock requests</title>
</head>
<body>

<div class="container">


    <div class="row col-md-6 col-md-offset-2 custyle">
        <table class="table table-striped custab">
            <thead>
            <%--  <a href="#" class="btn btn-primary btn-xs pull-right"><b>+</b> Add new categories</a>--%>

            <tr>
                <th>Id</th>
                <th>User id</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Surname</th>
                <th>Card number</th>
                <th>Money</th>
                <th>Blocked</th>
                <th class="text-center">Action</th>
            </tr>
            </thead>
            <c:if test="${requestScope.listCards != null}">

                <c:forEach var="card" items="${requestScope.listCards }">
                    <tr style="margin-right: 100px">
                        <td>${card.id}</td>
                        <td>${card.userId}</td>
                        <td>${card.firstName}</td>
                        <td>${card.lastName}</td>
                        <td>${card.surname}</td>
                        <td>${card.cardId}</td>
                        <td>${card.cardNumber}</td>
                        <td>${card.money}</td>


                            <%--   <td> <a href="#" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Block</a></td>--%>
                        <td> <span class="badge badge-danger"><cardFormat:formatActiveBlockedBoolean
                                status="${card.blocked}"> </cardFormat:formatActiveBlockedBoolean></span></td>
                        <form action="${Path.ADMIN_UNBLOCK_CARD_PATH}" method="post">
                            <td><input type="submit" class="btn btn-success" value="Unblock"></td>
                            <input type="hidden" name="cardId" value="${card.cardId}">
                        </form>

                    </tr>

                </c:forEach>
            </c:if>

        </table>
    </div>
</div>


</body>
</html>