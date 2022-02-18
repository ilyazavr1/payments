<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@include file="/jspf/bootstrap.jspf" %>
<%--<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--%>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">

<html>
<head>
    <title>Login</title>
</head>
<body>

<div class="container">

    <hr>

    <div class="row">

        <div style="width:400px; margin-top:30%; margin-left: 30%" class="card">
            <article class="card-body">
                <a href="${Path.REGISTRATION_PATH}" class="float-right btn btn-outline-primary">Sign up</a>
                <h4 class="card-title mb-4 mt-1">Sign in</h4>
                <form action="${Path.LOGIN_PATH}" method="post">
                    <div class="form-group">
                        <c:if test="${requestScope.userIsBlocked != null}">
                           <p><fmt:message key="userIsBlocked"> </fmt:message></p>
                        </c:if>
                        <label>Your email</label>
                        <input name="email" class="form-control" placeholder="<fmt:message key="email"/> " type="text">
                        <c:if test="${requestScope.emailNotValid != null}">
                            <p style="color: red"><fmt:message key="emailNotValid"> </fmt:message></p>
                        </c:if>
                        <c:if test="${requestScope.wrongEmail != null}">
                            <p style="color: red"><fmt:message key="wrongEmail"> </fmt:message></p>
                        </c:if>
                    </div> <!-- form-group// -->
                    <div class="form-group">
                        <%--   <a class="float-right" href="#">Forgot?</a>--%>
                        <label>Your password</label>
                        <input name="password" class="form-control" placeholder="<fmt:message key="password"/>"
                               type="password">
                            <c:if test="${requestScope.passwordNotValid != null}">
                                <p style="color: red"><fmt:message key="passwordNotValid"></fmt:message></p>
                            </c:if>
                            <c:if test="${requestScope.wrongPassword != null}">
                                <p style="color: red"><fmt:message key="wrongPassword"> </fmt:message></p>
                            </c:if>
                    </div> <!-- form-group// -->
                    <%-- <div class="form-group">
                         <div class="checkbox">
                             <label> <input type="checkbox"> Save password </label>
                         </div> <!-- checkbox .// -->
                     </div> <!-- form-group// -->--%>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-block"> Login</button>
                    </div> <!-- form-group// -->
                </form>
            </article>
        </div> <!-- card.// -->


    </div> <!-- row.// -->

</div>


<p>Qwerty12345</p>

</body>
</html>
<%--
<form action="${Path.LOGIN_PATH}" method="post">

    <label for="email">Input email</label>
    <input type="email" name="email" id="email">
    <br>

    <label for="password">Input password</label>
    <input type="password" name="password" id="password">
    <br>

    <input type="submit" name="login" value="Login">

</form>
--%>
