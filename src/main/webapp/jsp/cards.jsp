<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="cardFormat" uri="/WEB-INF/customCardNumberFromat.tld" %>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<html>
<head>
    <title>Cards</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>


<c:if test="${requestScope.cards == null}">
    <h1>Yot do not have any cards</h1>
</c:if>
<c:out value="${requestScope.cards.size()}"> size</c:out>

<%--
<c:if test="${requestScope.cards != null}">
    <c:forEach var="card" items="${requestScope.cards }">

        <c:url var="cardID"  value="${Path.CARD_TOP_UP_PATH}">
            <c:param name="id" value="${card.id}"/>
        </c:url>

        <p>Card id: <c:out value="${card.id}"> </c:out></p>
        <p>Card number: <cardFormat:formatCardNumber number="${card.number}"/></p>
        <p>Card money: <c:out value="${card.money}"> </c:out></p>
        <p>Card blocked: <c:out value="${card.blocked}"> </c:out></p>

        <a href="${cardID}"> TOP UP</a>
    </c:forEach>
</c:if>
--%>

<div class="container">

    <div class="row">
        <%-- <div class="col-sm-4 py-2">
             <div class="card card-body h-100">
                 Card. I'm just a simple card-block.
             </div>
         </div>

         <div class="col-sm-4 py-2">
             <div class="card h-100 text-white bg-danger">
                 <div class="card-body">
                     <h3 class="card-title">Danger</h3>
                     <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                     <a href="#" class="btn btn-outline-light">Outline</a>
                 </div>
             </div>
         </div>

         <div class="col-sm-4 py-2">
             <div class="card h-100 card-body">
                 Card. I'm just a simple card-block, but I have a little more text!
             </div>
         </div>

         <div class="col-sm-4 py-2">
             <div class="card h-100 border-primary">
                 <div class="card-body">
                     <h3 class="card-title">Primary</h3>
                     <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                     <a href="#" class="btn btn-outline-secondary">Outline</a>
                 </div>
             </div>
         </div>

         <div class="col-sm-4 py-2">
             <div class="card h-100 card-body">
                 Card. I'm just a simple card-block.
             </div>
         </div>

         <div class="col-sm-4 py-2">
             <div class="card text-white bg-primary">
                 <div class="card-body">
                     <h3 class="card-title">Hello</h3>
                     <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                     <a href="#" class="btn btn-outline-light">Outline</a>
                 </div>
             </div>
         </div>--%>
        <c:if test="${requestScope.cards != null}">
            <c:forEach var="card" items="${requestScope.cards }">

                <c:url var="cardID" value="${Path.CARD_TOP_UP_PATH}">
                    <c:param name="id" value="${card.id}"/>
                </c:url>

                <%--   <p>Card id: <c:out value="${card.id}"> </c:out></p>


                   <p>Card blocked: <c:out value="${card.blocked}"> </c:out></p>

                   <a href="${cardID}"> TOP UP</a>--%>

                <div class="col-sm-4 py-2">
                    <div class="card h-100 border-primary">
                        <div class="card-body">
                            <h3 class="card-title"><cardFormat:formatCardNumber number="${card.number}"/></h3>
                            <p class="card-text"><c:out value="${card.money}"> </c:out></p>
                            <a href="${cardID}" class="btn btn-outline-secondary">TOP UP</a>
                        </div>
                    </div>
                </div>

            </c:forEach>
        </c:if>
    </div>

</div>

<form action="${Path.CARD_PATH}" method="post">

    <input type="submit" name="createCart" value="Add card">
</form>


</body>
</html>
