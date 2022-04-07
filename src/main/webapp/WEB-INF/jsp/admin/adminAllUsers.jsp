<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>

<html>
<head>
    <title>All users</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>


<div class="container">


    <div class="row col-md-6 col-md-offset-2 custyle">
        <table style="margin-left: 100px" class="table table-striped custab">
            <thead>
            <%--  <a href="#" class="btn btn-primary btn-xs pull-right"><b>+</b> Add new categories</a>--%>

            <tr>
                <th>ID</th>
                <th><fmt:message key="firstName"/></th>
                <th><fmt:message key="lastName"/></th>
                <th><fmt:message key="surname"/></th>
                <th><fmt:message key="email"/></th>
                <th><fmt:message key="status"/></th>
                <th class="text-center"><fmt:message key="cardAction"/></th>
                <th><fmt:message key="cards"/></th>
            </tr>
            </thead>
            <c:if test="${requestScope.usersList != null}">

                <c:forEach var="user" items="${requestScope.usersList }">



                    <c:if test="${user.rolesId ==2}">
                        <tr style="margin-right: 100px">
                            <td>${user.id}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td>${user.surname}</td>
                            <td>${user.email}</td>


                                <%--   <td> <a href="#" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Block</a></td>--%>
                            <c:choose>
                                <c:when test="${user.blocked == false}">
                                    <td> <span class="badge badge-success"><cardFormat:formatActiveBlockedBoolean
                                            status="${user.blocked}"> </cardFormat:formatActiveBlockedBoolean></span>
                                    </td>
                                    <form action="${Path.ADMIN_BLOCK_USER_PATH}" method="post">
                                        <td><input type="submit" class="btn btn-danger btn-xs" value="<fmt:message key="block"/>"></td>
                                        <input type="hidden" name="userId" value="${user.id}">
                                    </form>
                                </c:when>
                                <c:otherwise>

                                    <td> <span class="badge badge-danger"><cardFormat:formatActiveBlockedBoolean status="${user.blocked}"> </cardFormat:formatActiveBlockedBoolean></span>
                                    </td>

                                    <form action="${Path.ADMIN_UNBLOCK_USER_PATH}" method="post">
                                        <td><input type="submit" class="btn btn-success" value="<fmt:message key="unblock"/>"></td>
                                        <input type="hidden" name="userId" value="${user.id}">
                                    </form>
                                </c:otherwise>
                            </c:choose>
                            <form action="${Path.ADMIN_USER_CARDS_PATH}" method="post">
                                <input type="hidden" name="userId" value="${user.id}">
                                <td><input type="submit" class="btn btn-success" value="<fmt:message key="cards"/>"></td>
                            </form>

                        </tr>
                    </c:if>
                </c:forEach>
            </c:if>

        </table>
    </div>
</div>
</body>
</html>
