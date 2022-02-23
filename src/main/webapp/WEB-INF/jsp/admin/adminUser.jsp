<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="ufn" tagdir="/WEB-INF/tags" %>


<html>
<head>

    <title>User</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>

<div style="padding-left: 50px" class="panel-body">
   <div style="padding-left: 600px">
    <table>
        <thead>
        <tr>
            <th style="padding-right: 150px">Full name:</th>
            <th style="padding-right: 100px">Email</th>
            <th>Status</th>
        </tr>

        </thead>
        <tr>
            <td><ufn:fullName firstName="${requestScope.user.firstName}" lastName="${requestScope.user.lastName}"
                              surname="${requestScope.user.surname}"> </ufn:fullName></td>
            <td><c:out value="${requestScope.user.email}"> </c:out></td>
            <td><span class="badge badge-success"><cardFormat:formatActiveBlockedBoolean
                    status="${requestScope.user.blocked}"> </cardFormat:formatActiveBlockedBoolean></span></td>
        </tr>
        <%--  <dl>
              <dt>Full name:</dt>
              <dd><ufn:fullName firstName="${requestScope.user.firstName}" lastName="${requestScope.user.lastName}"
                                surname="${requestScope.user.surname}"> </ufn:fullName></dd>
              <dt>Email</dt>
              <dd><c:out value="${requestScope.user.email}"> </c:out></dd>
              <dt>Status</dt>
              <dd><span class="badge badge-success"><cardFormat:formatActiveBlockedBoolean
                      status="${requestScope.user.blocked}"> </cardFormat:formatActiveBlockedBoolean></span></dd>

          </dl>--%>
    </table>
   </div>

    <div class="container">


        <div class="row col-md-6 col-md-offset-2 custyle">
            <table class="table table-striped custab">
                <thead>
                <%--  <a href="#" class="btn btn-primary btn-xs pull-right"><b>+</b> Add new categories</a>--%>

                <tr>
                    <th>ID</th>
                    <th>Card name</th>
                    <th  style="padding-right: 150px">Card number</th>
                    <th style="padding-right: 50px">Balance</th>
                    <th>Status</th>

                    <th class="text-center">Action</th>
                </tr>
                </thead>
                <c:if test="${requestScope.cards != null}">

                    <c:forEach var="card" items="${requestScope.cards }">

                        <c:url var="cardBlockID" value="${Path.CARD_BLOCK_PATH}">
                            <c:param name="id" value="${card.id}"/>
                        </c:url>

                        <tr style="margin-right: 100px">
                            <td>${card.id}</td>
                            <td>${card.name}</td>
                            <td><cardFormat:formatCardNumber number="${card.number}"/></td>
                            <td>${card.money} uah</td>


                                <%--   <td> <a href="#" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Block</a></td>--%>
                            <c:choose>
                                <c:when test="${card.blocked == false}">
                                    <td> <span class="badge badge-success"><cardFormat:formatActiveBlockedBoolean
                                            status="${card.blocked}"> </cardFormat:formatActiveBlockedBoolean></span>
                                    </td>
                                   <td> <a style="background-color: lightcoral" href="${cardBlockID}"
                                       class="btn btn-outline-secondary">BLOCK</a></td>
                                </c:when>
                                <c:otherwise>
                                    <td> <span class="badge badge-danger"><cardFormat:formatActiveBlockedBoolean
                                            status="${card.blocked}"> </cardFormat:formatActiveBlockedBoolean></span>
                                    </td>
                                    <form action="${Path.ADMIN_UNBLOCK_CARD_PATH}" method="post">
                                        <td><input type="submit" class="btn btn-success" value="Unblock"></td>
                                        <input type="hidden" name="cardId" value="${card.id}">
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </tr>

                    </c:forEach>
                </c:if>

            </table>
        </div>
    </div>
</div>
</body>
</html>
