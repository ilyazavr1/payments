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
        <table class="table table-striped custab">
            <thead>
            <%--  <a href="#" class="btn btn-primary btn-xs pull-right"><b>+</b> Add new categories</a>--%>

            <tr>
                <th>ID</th>
                <th>First name</th>
                <th>Last name</th>
                <th>Surname</th>
                <th>Email</th>
                <th>Status</th>
                <th class="text-center">Action</th>
            </tr>
            </thead>
            <c:if test="${requestScope.usersList != null}">

                <c:forEach var="user" items="${requestScope.usersList }">

                    <c:url var="userId" value="${Path.ADMIN_USER_CARDS_PATH}">
                        <c:param name="userId" value="${user.id}"/>
                    </c:url>

                    <c:if test="${user.rolesId ==2}">
                        <tr style="margin-right: 100px">
                            <td>${user.id}</td>
                            <td><a href="${userId}">${user.firstName}</a></td>
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
                                        <td><input type="submit" class="btn btn-danger btn-xs" value="Block"></td>
                                        <input type="hidden" name="userId" value="${user.id}">
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <td> <span class="badge badge-danger"><cardFormat:formatActiveBlockedBoolean
                                            status="${user.blocked}"> </cardFormat:formatActiveBlockedBoolean></span>
                                    </td>
                                    <form action="${Path.ADMIN_UNBLOCK_USER_PATH}" method="post">
                                        <td><input type="submit" class="btn btn-success" value="Unblock"></td>
                                        <input type="hidden" name="userId" value="${user.id}">
                                    </form>
                                </c:otherwise>
                            </c:choose>

                        </tr>
                    </c:if>
                </c:forEach>
            </c:if>

        </table>
    </div>
</div>
</body>
</html>
