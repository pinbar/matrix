<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Dashboard</title>
<jsp:include page="commonIncludes.jsp" />
<script
    src="${pageContext.request.contextPath}/resources/js/timesheet.js"></script>
</head>

<body>
    <jsp:include page="header.jsp" />
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span2">
                <jsp:include page="timesheetSideBar.jsp" />
            </div>

            <div class="span10">
                <form:form id="timeSheet" class="form-horizontal"
                    method="post" modelAttribute="timesheet"
                    action="${pageContext.request.contextPath}/timesheet/save">
                    <table class="table table-bordered table-condensed">
                        <thead>
                            <tr>
                                <th><u>Cost Center</u></th>
                                <th><u>Monday</u></th>
                                <th><u>Tuesday</u></th>
                                <th><u>Wednesday</u></th>
                                <th><u>Thursday</u></th>
                                <th><u>Friday</u></th>
                                <th><u>Saturday</u></th>
                                <th><u>Sunday</u></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${timesheet != null}">
                                <p>
                                    Timesheet for week ending: <b>${timesheet.weekEnding}</b>
                                </p>
                                <form:hidden path="weekEnding" />
                                <form:hidden path="id" />

                                <c:forEach
                                    items="${timesheet.tsCostCenters}"
                                    varStatus="status">
                                    <tr>
                                        <td><c:choose>
                                                <c:when
                                                    test="${not empty costCenters}">
                                                    <form:select
                                                        path="tsCostCenters[${status.index}].costCode"
                                                        class="input-medium">
                                                        <form:options
                                                            items="${costCenters}"
                                                            itemValue="costCode"
                                                            itemLabel="name" />
                                                    </form:select>
                                                </c:when>
                                                <c:otherwise>
                                                  HEY SET UP YOUR TABLES CORRECTLY      
                                              </c:otherwise>
                                            </c:choose></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].monday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].monday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].monday.id" /></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].tuesday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].tuesday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].tuesday.id" /></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].wednesday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].wednesday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].wednesday.id" /></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].thursday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].thursday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].thursday.id" /></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].friday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].friday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].friday.id" /></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].saturday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].saturday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].saturday.id" /></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].sunday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].sunday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].sunday.id" /></td>
                                        <td><a
                                            href="${pageContext.request.contextPath}/timesheet/deleteCostCodeRow?timesheetId=${timesheet.id}&costCode=${timesheet.tsCostCenters[status.index].costCode}"><span
                                                class="icon-trash"></span></a></td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>

                    </table>
                    <div class="control-group">
                        <input type="submit" class="btn btn-success"
                            value="Save"> <input type="reset"
                            id="cancel" name="cancel"
                            class="btn btn-warning" value="Cancel">
                    </div>
                    <div class="control-group">
                        <a
                            href="${pageContext.request.contextPath}/timesheet/addCostCodeRow?timesheetId=${timesheet.id}"><input
                            id="addCostCodeBtn" type="button"
                            class="btn btn-primary" value="Add Row"></a>
                        <input type="button" class="btn btn-danger"
                            value="Submit Timesheet">
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
</html>