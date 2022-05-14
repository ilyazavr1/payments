<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@include file="/jspf/bootstrap.jspf" %>

<html>
<head>
    <title>Sign up</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>

<div style="width:400px; margin-top:10%; margin-left: 40%" class="card bg-light">
    <article class="card-body mx-auto" style="max-width: 400px;">
        <h4 class="card-title mt-3 text-center"><fmt:message key="createAccount"/></h4>


        <form action="${Path.REGISTRATION_PATH}" method="post">
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="firstName" class="form-control" placeholder="<fmt:message key="firstName"/>" type="text">

            </div>
            <c:if test="${requestScope.firstNameNotValid != null}">
                <p style="color: red"><fmt:message key="firstNameNotValid"> </fmt:message></p>
            </c:if>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="lastName" class="form-control" placeholder="<fmt:message key="lastName"/>" type="text">
            </div>
            <c:if test="${requestScope.lastNameNotValid != null}">
                <p style="color: red"><fmt:message key="lastNameNotValid"> </fmt:message></p>
            </c:if>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="surname" class="form-control" placeholder="<fmt:message key="surname"/>" type="text">
            </div>
            <c:if test="${requestScope.surnameNotValid != null}">
                <p style="color: red"><fmt:message key="exception.surnameNotValid"> </fmt:message></p>
            </c:if>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                </div>
                <input name="email" class="form-control" placeholder="<fmt:message key="email"/>" type="email">
            </div>
            <c:if test="${requestScope.emailNotValid != null}">
                <p style="color: red"><fmt:message key="emailNotValid"> </fmt:message></p>
            </c:if>
            <c:if test="${requestScope.mailExists != null}">
                <p style="color: red"><fmt:message key="mailExists"> </fmt:message></p>
            </c:if>

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                </div>
                <input name="password" class="form-control" placeholder="<fmt:message key="password"/>" type="password">
            </div>
            <c:if test="${requestScope.passwordNotValid != null}">
                <p style="color: red"><fmt:message key="passwordNotValid"> </fmt:message></p>
            </c:if>

            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block"> <fmt:message key="createAccount"/></button>
            </div>

            <p class="text-center"><fmt:message key="haveAccount"/> <a href="${Path.LOGIN_PATH}"><fmt:message key="signIn"/></a></p>
        </form>
    </article>
</div>




</body>
</html>
