<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>


<html>
<head>
    <title>My payments</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>

<%--
<c:if test="${requestScope.payments != null}">

    <c:forEach items="${requestScope.payments}" var="payment">

        <p>id <c:out value="${payment.id}"> </c:out></p>
        <p>money <c:out value="${payment.money}"> </c:out></p>
        <p>status <c:out value="${payment.status}"> </c:out></p>
        <p>time <c:out value="${payment.creationTimestamp}"> </c:out></p>
        <p>cardTo <c:out value="${payment.senderCardNumber}"> </c:out></p>
        <p>cardFrom <c:out value="${payment.destinationCardNumber}"> </c:out></p>

    </c:forEach>

</c:if>--%>

<%--<select>
    <option value="1">1</option>
    <option  value="2">2</option>
    <a href="${Path.PROFILE_PATH}"><option  value="3">3</option></a>
</select>--%>
<form action="${Path.PAYMENTS_PATH}" method="get">
    <div style="width: 10%" class="form-group">
        <label for="sel1">SORT BY NUMBER:</label>
        <select name="sorting" class="form-control" id="sel1">
           
            <%--<option value="asc">From smaller</option>
            <option value="desc">From greater</option>--%>
       <c:choose>
           <c:when test="${requestScope.sorting.equals('asc')}">
               <option selected value="asc">From smaller</option>
               <option value="desc">From greater</option>
           </c:when>
           <c:otherwise>
               <option  value="asc">From smaller</option>
               <option selected value="desc">From greater</option>
           </c:otherwise>
       </c:choose>
       
        </select>
    </div>
<input type="submit" value="go">
</form>
<div class="container">


    <div class="row col-md-6 col-md-offset-2 custyle">
        <table class="table table-striped custab">
            <thead>
            <%--  <a href="#" class="btn btn-primary btn-xs pull-right"><b>+</b> Add new categories</a>--%>

            <tr>
                <th>Number</th>
                <th style="padding-right: 150px">Card from</th>
                <th style="padding-right: 150px">Card to</th>
                <th>Money</th>
                <th>Time</th>
                <th>Status</th>
                <th class="text-center">Action</th>
            </tr>
            </thead>
            <c:if test="${requestScope.payments != null}">
             <%--   <c:forEach items="${requestScope.payments}" var="payment">

                    <p>id </p>
                    <p>money <c:out value="${payment.money}"> </c:out></p>
                    <p>status <c:out value="${payment.status}"> </c:out></p>
                    <p>time <c:out value="${payment.creationTimestamp}"> </c:out></p>
                    <p>cardTo <c:out value="${payment.senderCardNumber}"> </c:out></p>
                    <p>cardFrom <c:out value="${payment.destinationCardNumber}"> </c:out></p>

                </c:forEach>--%>
                <c:forEach var="payment" items="${requestScope.payments }">
                    <tr>

                        <td>${payment.id}</td>
                        <td > <cardFormat:formatCardNumber number="${payment.senderCardNumber}"> </cardFormat:formatCardNumber></td>
                        <td style="width: 2000px"> <cardFormat:formatCardNumber number="${payment.destinationCardNumber}"> </cardFormat:formatCardNumber> </td>
                        <td>${payment.money}</td>
                        <td>${payment.creationTimestamp}</td>
                        <td>${payment.status}</td>

                            <%--   <td> <a href="#" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-remove"></span> Block</a></td>--%>
                        <%--<c:choose>
                            <c:when test="${user.blocked == false}">
                                <form action="${Path.ADMIN_BLOCK_USER_PATH}" method="post">
                                    <td><input type="submit" class="btn btn-danger btn-xs" value="Block"></td>
                                    <input type="hidden" name="userId" value="${user.id}">
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="${Path.ADMIN_BLOCK_USER_PATH}" method="post">
                                    <td><input disabled type="submit" class="btn btn-danger btn-xs" value="Blocked">
                                    </td>
                                    <input type="hidden" name="userId" value="${user.id}">
                                </form>
                            </c:otherwise>
                        </c:choose>--%>

                    </tr>
                </c:forEach>
            </c:if>

        </table>
    </div>
</div>
</body>
</html>
