<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<html>
<head>
<title>Dashboard</title>
<jsp:include page="commonIncludes.jsp" />
<script src="${pageContext.request.contextPath}/resources/jquery/js/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery/js/jquery.iframe-transport.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery/js/jquery.fileupload.js"></script>
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
                                <th>Cost Center</th>
                                <th>Monday</th>
                                <th>Tuesday</th>
                                <th>Wednesday</th>
                                <th>Thursday</th>
                                <th>Friday</th>
                                <th>Saturday</th>
                                <th>Sunday</th>
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
                                                        <form:option
                                                            value=""
                                                            label="Please select.."
                                                            disabled="true"></form:option>
                                                        <form:options
                                                            items="${costCenters}"
                                                            itemValue="costCode"
                                                            itemLabel="name" />
                                                    </form:select>
                                                </c:when>
                                                <c:otherwise>
                                                  HEY! SET UP COST CENTERS CORRECTLY      
                                              </c:otherwise>
                                            </c:choose>
                                            <div>
                                                <form:errors
                                                    class="errorMsg"
                                                    path="tsCostCenters[${status.index}].costCode"></form:errors>
                                            </div></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].monday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].monday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].monday.id" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].monday.costCode" />
                                            <div>
                                                <form:errors
                                                    class="errorMsg"
                                                    path="tsCostCenters[${status.index}].monday.hours"></form:errors>
                                            </div></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].tuesday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].tuesday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].tuesday.id" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].tuesday.costCode" />
                                            <div>
                                                <form:errors
                                                    class="errorMsg"
                                                    path="tsCostCenters[${status.index}].tuesday.hours"></form:errors>
                                            </div></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].wednesday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].wednesday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].wednesday.id" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].wednesday.costCode" />
                                            <div>
                                                <form:errors
                                                    class="errorMsg"
                                                    path="tsCostCenters[${status.index}].wednesday.hours"></form:errors>
                                            </div></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].thursday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].thursday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].thursday.id" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].thursday.costCode" />
                                            <div>
                                                <form:errors
                                                    class="errorMsg"
                                                    path="tsCostCenters[${status.index}].thursday.hours"></form:errors>
                                            </div></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].friday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].friday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].friday.id" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].friday.costCode" />
                                            <div>
                                                <form:errors
                                                    class="errorMsg"
                                                    path="tsCostCenters[${status.index}].friday.hours"></form:errors>
                                            </div></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].saturday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].saturday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].saturday.id" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].saturday.costCode" />
                                            <div>
                                                <form:errors
                                                    class="errorMsg"
                                                    path="tsCostCenters[${status.index}].saturday.hours"></form:errors>
                                            </div></td>
                                        <td><form:input
                                                path="tsCostCenters[${status.index}].sunday.hours"
                                                type="text"
                                                class="input-small" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].sunday.date" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].sunday.id" />
                                            <form:hidden
                                                path="tsCostCenters[${status.index}].sunday.costCode" />
                                            <div>
                                                <form:errors
                                                    class="errorMsg"
                                                    path="tsCostCenters[${status.index}].sunday.hours"></form:errors>
                                            </div></td>
                                        <td><a
                                            href="${pageContext.request.contextPath}/timesheet/deleteCostCodeRow?timesheetId=${timesheet.id}&costCode=${timesheet.tsCostCenters[status.index].costCode}"><span
                                                class="icon-trash"></span></a></td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                        </tbody>

                    </table>
                    <div class="control-group">
                        <a
                            href="${pageContext.request.contextPath}/timesheet/addCostCodeRow?timesheetId=${timesheet.id}"><input
                            id="addCostCodeBtn" type="button"
                            class="btn btn-primary" value="Add Row"></a>
                        <%-- <a
                            href="${pageContext.request.contextPath}/timesheet/addCostCodeRow?timesheetId=${timesheet.id}"><input
                            id="addCostCodeBtn" type="button"
                            class="btn btn-info"
                            value="Show Attachments"></a> --%>
                        <a href="#attachmentsDialog" role="button"
                            class="btn btn-primary" data-toggle="modal">Show
                            Attachments</a>
                    </div>
                    <div class="control-group">
                        <input type="submit" class="btn btn-success"
                            value="Save"> <input type="reset"
                            id="cancel" name="cancel"
                            class="btn btn-warning" value="Cancel">

                        <input type="button" class="btn btn-danger"
                            value="Submit Timesheet">
                    </div>
                </form:form>
                <%--  <div class="control-group">
                        Attachment:
                        <c:if test="1=2">
                            <a target="blank"
                                href="${pageContext.request.contextPath}/attachment/timesheet?attachmentId=${timesheet.id}"><input
                                id="downloadAttachment" type="button"
                                class="btn btn-info" value="Download"></a>
                        </c:if>
                    </div>
                    --%>
                <!-- Modal dialog -->
                <div id="attachmentsDialog" class="modal hide fade"
                    tabindex="-1" role="dialog"
                    aria-labelledby="attachmentsDialog"
                    aria-hidden="true">
                    <div class="modal-header">
                        <button type="button" class="close"
                            data-dismiss="modal" aria-hidden="true">x</button>
                        <h3 id="attachmentsDialog">Attachments</h3>
                    </div>
                    <div class="modal-body">
                        <table id="attachmentListTable"
                            class="table table-striped table-bordered table-condensed">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>

                    </div>
                    <div class="modal-footer">
                        <button class="btn" data-dismiss="modal"
                            aria-hidden="true">Close</button>

                        <div class="control-group">
                            <form:form id="attachment"
                                class="form-horizontal" method="post"
                                modelAttribute="timesheet"
                                action="${pageContext.request.contextPath}/attachment/timesheet"
                                enctype="multipart/form-data">
                                <input type="hidden" name="timesheetId" value="${timesheet.id}" />
                            Add/Replace attachment:
                                    <input id="fileupload" type="file" name="file">
                                <input type="button" id="upload"
                                    class="btn btn-success"
                                    value="Upload">
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>