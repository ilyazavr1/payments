<%@include file="/jspf/tagLibs.jspf" %>
<%@include file="/jspf/page.jspf" %>


<html>
<head>
    <title>My payments</title>
</head>
<body>
<%@include file="/jspf/navBar.jspf" %>

<form>


</form>


<form action="${Path.PAYMENTS_PATH}" method="get">
    <input type="hidden" value="${requestScope.page}" name="page">
    <input type="hidden" value="${requestScope.records}" name="records">
    <div style="width: 30%" class="row">
        <div class="col">
            <label for="typeSort">Sort by:</label>
            <select name="sortingType" class="form-control" id="typeSort">
                <c:choose>
                    <c:when test="${requestScope.sortingType.equals('creation_timestamp')}">
                        <option selected value="creation_timestamp">Date</option>
                        <option value="payment.id">Number</option>
                    </c:when>
                    <c:otherwise>
                        <option value="creation_timestamp">Date</option>
                        <option selected value="payment.id">Number</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </div>
        <div class="col">
            <label for="orderSort">Sorting order:</label>
            <select name="sortingOrder" class="form-control" id="orderSort">
                <c:choose>
                    <c:when test="${requestScope.sortingOrder.equals('asc')}">
                        <option selected value="asc">Ascending</option>
                        <option value="desc">Descending</option>
                    </c:when>
                    <c:otherwise>
                        <option value="asc">Ascending</option>
                        <option selected value="desc">Descending</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </div>
        <div class="col">
            <input type="submit" value="Sort">
        </div>
    </div>
</form>
<div class="container">


    <div class="row col-md-6 col-md-offset-2 custyle">
        <table class="table table-striped custab">
            <thead>


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
                <c:forEach var="payment" items="${requestScope.payments }">
                    <tr>

                        <td>${payment.id}</td>
                        <td><cardFormat:formatCardNumber
                                number="${payment.senderCardNumber}"> </cardFormat:formatCardNumber></td>
                        <td style="width: 2000px"><cardFormat:formatCardNumber
                                number="${payment.destinationCardNumber}"> </cardFormat:formatCardNumber></td>
                        <td>${payment.money}</td>
                        <td>${payment.creationTimestamp}</td>
                        <td>${payment.status}</td>

                    </tr>
                </c:forEach>
            </c:if>

        </table>
    </div>
</div>
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
        <c:set var="sortType" value="${requestScope.sortingType}" scope="request"> </c:set>
        <c:set var="sortOrder" value="${requestScope.sortingOrder}" scope="request"> </c:set>


        <c:forEach begin="1" end="5" varStatus="loop">
            <c:set var="num" value="${loop.index}" scope="page"> </c:set>
            <c:choose>
                <c:when test="${requestScope.page == num}">
                    <li class="page-item active">
                        <a class="page-link"
                           href="${Path.PAYMENTS_PATH}?page=${num}&records=${rec}&sortingType=${sortType}&sortingOrder=${sortOrder}">${num}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="${Path.PAYMENTS_PATH}?page=${num}&records=${rec}&sortingType=${sortType}&sortingOrder=${sortOrder}">${num}</a></li>
                </c:otherwise>
            </c:choose>


        </c:forEach>


        <li class="page-item">
            <a class="page-link" href="#" aria-label="Next">
                <span aria-hidden="true">></span>
                <span class="sr-only">Next</span>
            </a>
        </li>
    </ul>


</nav>
<form action="${Path.PAYMENTS_PATH}" method="get">
    <input type="hidden" name="page" value="${requestScope.page}">

    <select name="records">

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
        <input type="hidden" name="sortingType" value="${requestScope.sortingType}">
        <input type="hidden" name="sortingOrder" value="${requestScope.sortingOrder}">
        <input type="submit" value="go">

</form>

</body>
</html>
