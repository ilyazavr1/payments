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
        <table style="margin-left: 200px">
            <thead>
            <tr>
                <th style="padding-right: 150px"><fmt:message key="fullName"/>:</th>
                <th style="padding-right: 100px"><fmt:message key="email"/></th>
                <th><fmt:message key="status"/></th>
            </tr>

            </thead>
            <tr>
                <td><ufn:fullName firstName="${requestScope.user.firstName}" lastName="${requestScope.user.lastName}"
                                  surname="${requestScope.user.surname}"> </ufn:fullName></td>
                <td><c:out value="${requestScope.user.email}"> </c:out></td>
                <td><span class="badge badge-success"><cardFormat:formatActiveBlockedBoolean
                        status="${requestScope.user.blocked}"> </cardFormat:formatActiveBlockedBoolean></span></td>
            </tr>

        </table>
    </div>

    <div class="container">


        <div class="row col-md-6 col-md-offset-2 custyle">
            <table style="margin-left: 150px" class="table table-striped custab">
                <thead>

                <tr>
                    <th>ID</th>
                    <th><fmt:message key="cardName"/></th>
                    <th style="padding-right: 150px"><fmt:message key="number"/></th>
                    <th style="padding-right: 100px"><fmt:message key="balance"/></th>
                    <th><fmt:message key="status"/></th>

                    <th class="text-center"><fmt:message key="cardAction"/></th>
                </tr>
                </thead>
                <c:if test="${requestScope.cards != null}">

                    <c:forEach var="card" items="${requestScope.cards }">
                        <c:url var="cardBlockID" value="${Path.ADMIN_CARD_BLOCK_PATH}">
                            <c:param name="id" value="${card.id}"/>
                        </c:url>


                        <tr style="margin-right: 100px">
                            <td>${card.id}</td>
                            <td>${card.name}</td>
                            <td><cardFormat:formatCardNumber number="${card.number}"/></td>
                            <td>${card.money} uah</td>


                            <c:choose>
                                <c:when test="${card.blocked == false}">
                                    <td> <span class="badge badge-success"><cardFormat:formatActiveBlockedBoolean
                                            status="${card.blocked}"> </cardFormat:formatActiveBlockedBoolean></span>
                                    </td>

                                    <form action="${Path.ADMIN_CARD_BLOCK_PATH}" method="post">
                                        <td><input style="background-color: lightcoral" type="submit"
                                                   class="btn btn-outline-secondary"
                                                   value="<fmt:message key="block"/>"></td>
                                        <input type="hidden" name="cardId" value="${card.id}">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <td> <span class="badge badge-danger"><cardFormat:formatActiveBlockedBoolean
                                            status="${card.blocked}"> </cardFormat:formatActiveBlockedBoolean></span>
                                    </td>
                                    <form action="${Path.ADMIN_UNBLOCK_CARD_PATH}" method="post">
                                        <td><input type="submit" class="btn btn-success"
                                                   value="<fmt:message key="unblock"/>"></td>
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
