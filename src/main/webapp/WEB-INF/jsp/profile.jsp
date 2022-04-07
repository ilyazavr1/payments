<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>


<html>
<head>
    <%@include file="/jspf/bootstrap.jspf" %>
    <title>Profile</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>


<%--
<c:if test="${sessionScope.user != null}">

    <p>User id: <c:out value="${sessionScope.user.id}"></c:out></p>
    <p>User name: <c:out value="${sessionScope.user.firstName}"></c:out></p>
    <p>User last name: <c:out value="${sessionScope.user.lastName}"></c:out></p>
    <p>User surname: <c:out value="${sessionScope.user.surname}"></c:out></p>
    <p>User email: <c:out value="${sessionScope.user.email}"></c:out></p>
    <p>User password: <c:out value="${sessionScope.user.password}"></c:out></p>
    <p>User blocked: <c:out value="${sessionScope.user.blocked}"></c:out></p>
    <p>User rolesId: <c:out value="${sessionScope.user.rolesId}"></c:out></p>
    <p>User rolesId: <c:out value="${sessionScope.user.rolesId}"></c:out></p>
</c:if>
--%>


<c:if test="${sessionScope.user == null}">

    <p>User is null</p>
</c:if>

<div class="container">
    <div class="row">
        <div class="col-md-5  toppad  pull-right col-md-offset-3 ">
            <A href="edit.html">Edit Profile</A>

            <A href="edit.html">Logout</A>
            <br>

        </div>
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad">


            <div class="panel panel-info">

                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3 col-lg-3 " align="center"><img alt="User Pic"
                                                                            src="http://babyinfoforyou.com/wp-content/uploads/2014/10/avatar-300x300.png"
                                                                            class="img-circle img-responsive"></div>

                        <c:if test="${sessionScope.user != null}">
                        <div class=" col-md-9 col-lg-9 ">
                            <table class="table table-user-information">
                                <tbody>
                                <tr>
                                    <td>First name:</td>
                                    <td><c:out value="${sessionScope.user.firstName}"></c:out></td>
                                </tr>
                                <tr>
                                    <td>Last name:</td>
                                    <td><c:out value="${sessionScope.user.lastName}"></c:out></td>
                                </tr>
                                <tr>
                                    <td>Surname</td>
                                    <td><c:out value="${sessionScope.user.surname}"></c:out></td>
                                </tr>

                                <tr>
                                <tr>
                                    <td>Email</td>
                                    <td><c:out value="${sessionScope.user.email}"></c:out></td>
                                </tr>

                                </tr>

                                </tbody>
                            </table>

                        </div>
                    </div>

                    </c:if>

                </div>


            </div>
        </div>
    </div>
</div>

</body>

</html>
