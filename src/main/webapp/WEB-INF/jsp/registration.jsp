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
        <h4 class="card-title mt-3 text-center"><fmt:message key="creatAccount"/></h4>
        <p class="text-center">Get started with your free account</p>

        <form action="${Path.REGISTRATION_PATH}" method="post">
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="firstName" class="form-control" placeholder="<fmt:message key="firstName"/>" type="text">

            </div> <!-- form-group// -->
            <c:if test="${requestScope.firstNameNotValid != null}">
                <p style="color: red"><fmt:message key="firstNameNotValid"> </fmt:message></p>
            </c:if>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="lastName" class="form-control" placeholder="<fmt:message key="lastName"/>" type="text">

            </div> <!-- form-group// -->
            <c:if test="${requestScope.lastNameNotValid != null}">
                <p style="color: red"><fmt:message key="lastNameNotValid"> </fmt:message></p>
            </c:if>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="surname" class="form-control" placeholder="<fmt:message key="surname"/>" type="text">

            </div> <!-- form-group// -->
            <c:if test="${requestScope.surnameNotValid != null}">
                <p style="color: red"><fmt:message key="exception.surnameNotValid"> </fmt:message></p>
            </c:if>
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                </div>
                <input name="email" class="form-control" placeholder="<fmt:message key="email"/>" type="email">
            </div> <!-- form-group// -->
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
            </div> <!-- form-group// -->
            <c:if test="${requestScope.passwordNotValid != null}">
                <p style="color: red"><fmt:message key="passwordNotValid"> </fmt:message></p>
            </c:if>

            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block"> Create Account</button>
            </div> <!-- form-group// -->

            <p class="text-center">Have an account? <a href="${Path.LOGIN_PATH}">Sign In</a></p>
        </form>
    </article>
</div> <!-- card.// -->




</body>
</html>
