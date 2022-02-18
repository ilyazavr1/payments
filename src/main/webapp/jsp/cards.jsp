<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>
<%@taglib prefix="cardFormat" uri="/WEB-INF/customCardNumberFromat.tld" %>
<%--<%@taglib prefix="cardStatus" uri="/WEB-INF/customCardBooleanFromat.tld" %>--%>

<html>
<head>
    <%--    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>--%>
    <%@include file="/jspf/bootstrap.jspf" %>
    <title>Cards</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>


<c:if test="${requestScope.cards == null}">
    <h1>Yot do not have any cards</h1>
</c:if>
<c:out value="${requestScope.cards.size()}"> size</c:out>

<div class="container">

    <div class="row">

        <c:if test="${requestScope.cards != null}">
            <c:forEach var="card" items="${requestScope.cards }">

                <c:url var="cardID" value="${Path.CARD_TOP_UP_PATH}">
                    <c:param name="id" value="${card.id}"/>
                </c:url>
                <c:if test="${card.blocked == false}">
                    <div class="col-sm-4 py-2">
                        <div class="card h-100 border-primary">
                            <div class="card-body">
                                <h3 class="card-title"><cardFormat:formatCardNumber number="${card.number}"/></h3>
                                <h3 class="card-title">${card.name}</h3>
                                <p class="card-text"><c:out value="${card.money}"> </c:out></p>
                                <p class="card-text"><cardFormat:formatCardBoolean
                                        status="${card.blocked}"> </cardFormat:formatCardBoolean></p>
                                <a style="background-color: lightgreen" href="${cardID}"
                                   class="btn btn-outline-secondary">TOP UP</a>
                                <a style="margin-left: 150px; background-color: lightcoral" href="${cardID}"
                                   class="btn btn-outline-secondary">BLOCK</a>
                            </div>
                        </div>
                    </div>
                </c:if>
                <c:if test="${card.blocked == true}">
                    <div class="col-sm-4 py-2">
                        <div style="background-color: red" class="card h-100 border-primary">
                            <div class="card-body">
                                <h3 class="card-title"><cardFormat:formatCardNumber number="${card.number}"/></h3>
                                <p class="card-text"><c:out value="${card.money}"> </c:out></p>
                                <p class="card-text"><cardFormat:formatCardBoolean
                                        status="${card.blocked}"> </cardFormat:formatCardBoolean></p>
                                <a style="background-color: lightgreen" onclick="alert('Card is blocked')"
                                   class="btn btn-outline-secondary">TOP UP</a>
                                <a style="margin-left: 150px; background-color: lightcoral" href="${cardID}"
                                   class="btn btn-outline-secondary">BLOCK</a>
                            </div>
                        </div>
                    </div>
                </c:if>

            </c:forEach>
        </c:if>
    </div>

</div>

<%--<nav aria-label="Page navigation example">
    <ul class="pagination">
        <li class="page-item">
            <a class="page-link" href="#" aria-label="Previous">
                <span aria-hidden="true"><</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>
        <c:set var="rec" value="${requestScope.recordsOnPage}" scope="request"> </c:set>

        <c:choose>
            <c:when test="${requestScope.page == 1}">
                <li class="page-item active"><a class="page-link" href="${Path.CARDS_PATH}?page=1&records=${rec}">1</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="${Path.CARDS_PATH}?page=1&records=${rec}">1</a></li>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${requestScope.page == 2}">
                <li class="page-item active"><a class="page-link" href="${Path.CARDS_PATH}?page=2&records=${rec}">2</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="${Path.CARDS_PATH}?page=2&records=${rec}">2</a></li>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${requestScope.page == 3}">
                <li class="page-item active"><a class="page-link" href="${Path.CARDS_PATH}?page=3&records=${rec}">3</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="${Path.CARDS_PATH}?page=3&records=${rec}">3</a></li>
            </c:otherwise>
        </c:choose>
        <li class="page-item">
            <a class="page-link" href="#" aria-label="Next">
                <span aria-hidden="true">></span>
                <span class="sr-only">Next</span>
            </a>
        </li>
    </ul>


</nav>--%>
<nav aria-label="Page navigation example">

    <ul class="pagination">
        <li class="page-item">
            <a class="page-link" href="#" aria-label="Previous">
                <span aria-hidden="true"><</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>

        <c:set var="rec" value="${requestScope.records}" scope="request"> </c:set>
        <c:set var="lenght" value="${requestScope.loopPagination}" scope="request"> </c:set>

        <c:forEach begin="1" end="${lenght}" varStatus="loop">
            <c:set var="num" value="${loop.index}" scope="page"> </c:set>
            <c:choose>
                <c:when test="${requestScope.page == num}">
                    <li class="page-item active"><a class="page-link"
                                                    href="${Path.CARDS_PATH}?page=${num}&records=${rec}">${num}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item"><a class="page-link"
                                             href="${Path.CARDS_PATH}?page=${num}&records=${rec}">${num}</a></li>
                </c:otherwise>
            </c:choose>


        </c:forEach>

        <%--   <c:choose>
               <c:when test="${requestScope.page == 1}">
                   <li class="page-item active"><a class="page-link" href="${Path.CARDS_PATH}?page=1&records=${rec}">1</a>
                   </li>
               </c:when>
               <c:otherwise>
                   <li class="page-item"><a class="page-link" href="${Path.CARDS_PATH}?page=1&records=${rec}">1</a></li>
               </c:otherwise>
           </c:choose>

           <c:choose>
               <c:when test="${requestScope.page == 2}">
                   <li class="page-item active"><a class="page-link" href="${Path.CARDS_PATH}?page=2&records=${rec}">2</a>
                   </li>
               </c:when>
               <c:otherwise>
                   <li class="page-item"><a class="page-link" href="${Path.CARDS_PATH}?page=2&records=${rec}">2</a></li>
               </c:otherwise>
           </c:choose>

           <c:choose>
               <c:when test="${requestScope.page == 3}">
                   <li class="page-item active"><a class="page-link" href="${Path.CARDS_PATH}?page=3&records=${rec}">3</a>
                   </li>
               </c:when>
               <c:otherwise>
                   <li class="page-item"><a class="page-link" href="${Path.CARDS_PATH}?page=3&records=${rec}">3</a></li>
               </c:otherwise>
           </c:choose>--%>
        <li class="page-item">
            <a class="page-link" href="#" aria-label="Next">
                <span aria-hidden="true">></span>
                <span class="sr-only">Next</span>
            </a>
        </li>
    </ul>


</nav>
<form action="${Path.CARDS_PATH}" method="get">
    <input type="hidden" name="page" value="${requestScope.page}">
    <select name="records"> <!--Supplement an id here instead of using 'name'-->

        <c:choose>
        <c:when test="${requestScope.records == 3}">
        <option selected value="3">3</option>
        </c:when>
        <c:otherwise>
        <option value="3">3</option>
        </c:otherwise>
        </c:choose>

        <c:choose>
        <c:when test="${requestScope.records == 6}">
        <option selected value="6">6</option>
        </c:when>
        <c:otherwise>
        <option value="6">6</option>
        </c:otherwise>
        </c:choose>
        <c:choose>
        <c:when test="${requestScope.records == 9}">
        <option selected value="9">9</option>
        </c:when>
        <c:otherwise>
        <option value="9">9</option>
        </c:otherwise>
        </c:choose>
        <input type="submit" value="go">

</form>
<%--<form action="${Path.CARDS_PATH}" method="get">
    <select name="recordsOnPage"> <!--Supplement an id here instead of using 'name'-->
        <c:if test="${requestScope.recordsOnPage == 3}">
            <option selected value="3">3</option>
        </c:if>
        <option value="3">3</option>
        <c:if test="${requestScope.recordsOnPage == 6}">
            <option selected value="6">6</option>
        </c:if>
        <option value="6">6</option>
        <c:if test="${requestScope.recordsOnPage == 9}">
            <option selected value="9">9</option>
        </c:if>
        <option value="9">9</option>
    </select>
    <input type="submit" value="go">
</form>--%>

<%--<%@include file="/jspf/pagination.jspf" %>--%>

</body>
</html>
