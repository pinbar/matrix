<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="sidebar-nav">
    <div>
        <ul class="nav nav-list">
            <c:forEach var="ts" items="${timesheetList}">
                <c:choose>
                    <c:when test="${ts.status=='REJECTED'}">
                        <li><span><a
                                href="${pageContext.request.contextPath}/timesheet/weekendings/${ts.weekEnding}">${ts.weekEnding}</a>
                                <span class="badge badge-important">!</span>
                        </span></li>
                    </c:when>
                    <c:when test="${ts.status=='PENDING'}">
                        <li><span><a
                                href="${pageContext.request.contextPath}/timesheet/weekendings/${ts.weekEnding}">${ts.weekEnding}</a>
                                <span class="badge badge-warning">?</span>
                        </span><span><a
                                href="${pageContext.request.contextPath}/timesheet/delete/${ts.id}"><span
                                    class="icon-trash"></span> </a> </span></li>
                    </c:when>
                    <c:otherwise>
                        <li><span><a
                                href="${pageContext.request.contextPath}/timesheet/weekendings/${ts.weekEnding}">${ts.weekEnding}</a>
                                <span class="badge badge-success">&#10003;</span>
                        </span></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </div>
    <div class="input-append">
        <input id="dp" type="text" class="input-small"
            value="01-01-2013" data-date-format="mm-dd-yyyy"><input
            id="tsCreateBtn" type="button" class="btn btn-info"
            value="create">
    </div>
</div>
