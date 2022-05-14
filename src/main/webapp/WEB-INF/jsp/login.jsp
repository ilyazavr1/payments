<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@include file="/jspf/bootstrap.jspf" %>

<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">

<html>
<head>
    <title>Login</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>

<div class="container">
    <div class="row">
        <div style="width:400px; margin-top:30%; margin-left: 30%" class="card">
            <article class="card-body">
                <a href="${Path.REGISTRATION_PATH}" class="float-right btn btn-outline-primary"><fmt:message
                        key="signUp"/></a>
                <h4 class="card-title mb-4 mt-1"><fmt:message key="signIn"/></h4>

                <form action="${Path.LOGIN_PATH}" method="post">
                    <div class="form-group">
                        <c:if test="${requestScope.userIsBlocked != null}">
                            <p style="color: red"><fmt:message key="userIsBlocked"/></p>
                        </c:if>
                        <c:if test="${requestScope.authenticationFailed != null}">
                            <p style="color: red"><fmt:message key="authenticationFailed"/></p>
                        </c:if>

                        <label><fmt:message key="email"/></label>
                        <input name="email" class="form-control" placeholder="<fmt:message key="email"/> " type="text">
                        <c:if test="${requestScope.emailNotValid != null}">
                            <p style="color: red"><fmt:message key="emailNotValid"/></p>
                        </c:if>
                        <c:if test="${requestScope.wrongEmail != null}">
                            <p style="color: red"><fmt:message key="wrongEmail"/></p>
                        </c:if>
                    </div>
                    <div class="form-group">

                        <label><fmt:message key="password"/></label>
                        <input name="password" class="form-control" placeholder="<fmt:message key="password"/>"
                               type="password">
                        <c:if test="${requestScope.passwordNotValid != null}">
                            <p style="color: red"><fmt:message key="passwordNotValid"> </fmt:message></p>
                        </c:if>
                        <c:if test="${requestScope.wrongPassword != null}">
                            <p style="color: red"><fmt:message key="wrongPassword"> </fmt:message></p>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-block"><fmt:message key="login"/></button>
                    </div>
                </form>
            </article>
        </div>
    </div>
</div>




</body>
</html>
