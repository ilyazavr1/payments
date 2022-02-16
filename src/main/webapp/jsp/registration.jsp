<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@include file="/jspf/bootstrap.jspf" %>
<%--<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--%>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">



<head>
    <title>Sign up</title>
</head>
<body>
<h1>Sign up</h1>

<div style="width:400px; margin-top:10%; margin-left: 40%" class="card bg-light">
    <article class="card-body mx-auto" style="max-width: 400px;">
        <h4 class="card-title mt-3 text-center"><fmt:message key="creatAccount"/></h4>
        <p class="text-center">Get started with your free account</p>

        <form action="${Path.REGISTRATION_PATH}" method="post">
            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input  name="firstName" class="form-control" placeholder="<fmt:message key="firstName"/>" type="text">
            </div> <!-- form-group// -->

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="lastName" class="form-control" placeholder="<fmt:message key="lastName"/>" type="text">
            </div> <!-- form-group// -->

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-user"></i> </span>
                </div>
                <input name="surname" class="form-control" placeholder="<fmt:message key="surname"/>" type="text">
            </div> <!-- form-group// -->

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-envelope"></i> </span>
                </div>
                <input name="email" class="form-control" placeholder="<fmt:message key="email"/>" type="email">
            </div> <!-- form-group// -->

            <div class="form-group input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text"> <i class="fa fa-lock"></i> </span>
                </div>
                <input name="password" class="form-control" placeholder="<fmt:message key="password"/>" type="password">
            </div> <!-- form-group// -->

            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block"> Create Account  </button>
            </div> <!-- form-group// -->

            <p class="text-center">Have an account? <a href="${Path.LOGIN_PATH}">Sign In</a> </p>
        </form>
    </article>
</div> <!-- card.// -->

</div>

</body>
<%--</html>
<form action="${Path.REGISTRATION_PATH}" method="post">

    <label for="firstName">Input first name</label>
    <input type="text" name="firstName" id="firstName">
    <br>

    <label for="lastName">Input last name</label>
    <input type="text" name="lastName" id="lastName">
    <br>

    <label for="surname">Input surname</label>
    <input type="text" name="surname" id="surname">
    <br>

    <label for="email">Input email</label>
    <input type="email" name="email" id="email">
    <br>

    <label for="password">Input password</label>
    <input type="password" name="password" id="password">
    <br>

    <input type="submit" name="register" value="Register">
</form>--%>
