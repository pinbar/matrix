<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="errorMessages" class="error">${error}</div>
<form:form id="timesheet" class="form-horizontal" method="post"
    modelAttribute="timesheet"
    action="${pageContext.request.contextPath}/timesheet/save">
    <table id="timesheetTable" class="table table-bordered table-striped cf rt">
        <thead>
            <tr>
                <th>Cost Center</th>
                <th>Mon</th>
                <th>Tue</th>
                <th>Wed</th>
                <th>Thu</th>
                <th>Fri</th>
                <th>Sat</th>
                <th>Sun</th>
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

                <c:forEach items="${timesheet.tsCostCenters}"
                    varStatus="status" var="tsCostCenters">
                    <tr data-costcode= "${timesheet.tsCostCenters[status.index].costCode}">
                        <td><c:choose>
                                <c:when test="${not empty costCenters}">
                                    <form:select
                                        path="tsCostCenters[${status.index}].costCode"
                                        class="input-sm">
                                        <form:option value=""
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
                                <form:errors class="error"
                                    path="tsCostCenters[${status.index}].costCode"></form:errors>
                            </div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].monday.hours"
                                type="text" class="form-control timesheetHours" /> <form:hidden
                                path="tsCostCenters[${status.index}].monday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].monday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].monday.costCode" />
                             <div>
                                <form:errors class="error"
                                    path="tsCostCenters[${status.index}].monday.hours"></form:errors>
                            </div> </td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].tuesday.hours"
                                type="text" class="form-control timesheetHours" /> <form:hidden
                                path="tsCostCenters[${status.index}].tuesday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].tuesday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].tuesday.costCode" />
                            <div>
                                <form:errors class="error"
                                    path="tsCostCenters[${status.index}].tuesday.hours"></form:errors>
                            </div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].wednesday.hours"
                                type="text" class="form-control timesheetHours" /> <form:hidden
                                path="tsCostCenters[${status.index}].wednesday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].wednesday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].wednesday.costCode" />
                            <div>
                                <form:errors class="error"
                                    path="tsCostCenters[${status.index}].wednesday.hours"></form:errors>
                            </div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].thursday.hours"
                                type="text" class="form-control timesheetHours" /> <form:hidden
                                path="tsCostCenters[${status.index}].thursday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].thursday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].thursday.costCode" />
                            <div>
                                <form:errors class="error"
                                    path="tsCostCenters[${status.index}].thursday.hours"></form:errors>
                            </div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].friday.hours"
                                type="text" class="form-control timesheetHours" /> <form:hidden
                                path="tsCostCenters[${status.index}].friday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].friday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].friday.costCode" />
                            <div>
                                <form:errors class="error"
                                    path="tsCostCenters[${status.index}].friday.hours"></form:errors>
                            </div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].saturday.hours"
                                type="text" class="form-control timesheetHours" /> <form:hidden
                                path="tsCostCenters[${status.index}].saturday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].saturday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].saturday.costCode" />
                            <div>
                                <form:errors class="error"
                                    path="tsCostCenters[${status.index}].saturday.hours"></form:errors>
                            </div></td>
                        <td><form:input
                                path="tsCostCenters[${status.index}].sunday.hours"
                                type="text" class="form-control timesheetHours" /> <form:hidden
                                path="tsCostCenters[${status.index}].sunday.date" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].sunday.id" />
                            <form:hidden
                                path="tsCostCenters[${status.index}].sunday.costCode" />
                            <div>
                                <form:errors class="error"
                                    path="tsCostCenters[${status.index}].sunday.hours"></form:errors>
                            </div></td>
                        <td><a class="delCostCodeRow"
                            href="${pageContext.request.contextPath}/timesheet/deleteCostCodeRow?timesheetId=${timesheet.id}&costCode=${timesheet.tsCostCenters[status.index].costCode}&weekEnding=${timesheet.weekEnding}"><span
                                class="glyphicon glyphicon-trash"></span>
                        </a></td>
                    </tr>
                </c:forEach>
            </c:if>
        </tbody>

    </table>
    <div class="control-group">
        <a id="addRow"
            href="${pageContext.request.contextPath}/timesheet/addCostCodeRow?timesheetId=${timesheet.id}&weekEnding=${timesheet.weekEnding}">
            <input id="addCostCodeBtn" type="button"
            class="btn btn-info" value="Add Row">
        </a> <input type="submit" id="saveTimesheet" class="btn btn-success"
            value="Save"> <input type="reset" id="cancel"
            name="cancel" class="btn btn-warning" value="Cancel">
        <input type="button" id="submitTimesheet" class="btn btn-danger"
            value="Submit Timesheet">
        <div>
            <br> <input type="button" value="Show/Hide Attachments"
                class="btn btn-default" id="toggleAttachments" />
        </div>
    </div>
</form:form>
<div id="attachments" class="control-group collapse">
    <form:form id="fileupload" class="form-horizontal" method="post"
        modelAttribute="timesheet"
        action="${pageContext.request.contextPath}/attachment/timesheet"
        enctype="multipart/form-data">
        <input type="hidden" name="timesheetId" id="timesheetId"
            value="${timesheet.id}" />
        <div class="fileupload-buttonbar">
            <div class="spacer"></div>
            <div class="col-12 col-sm-10 col-lg-10">
                <!-- The fileinput-button span is used to style the file input field as button -->
                <span class="btn btn-success fileinput-button"> <i
                    class="icon-plus icon-white"></i> <span>Add
                        files...</span> <input type="file" name="file" multiple>
                </span>
                <!-- Auto upload enabled -->
                <!-- <button type="submit"
                                    class="btn btn-primary start">
                                    <i class="icon-upload icon-white"></i>
                                    <span>Start upload</span>
                                </button> -->
                <button type="reset" class="btn btn-warning cancel">
                    <i class="icon-ban-circle icon-white"></i> <span>Cancel
                        upload</span>
                </button>
                <button type="button" class="btn btn-danger delete">
                    <i class="icon-trash icon-white"></i> <span>Delete</span>
                </button>
                <input type="checkbox" class="toggle">
                <!-- The loading indicator is shown during file processing -->
                <span class="fileupload-loading"></span>
            </div>
            <!-- The global progress information -->
            <div class="col-5 fileupload-progress fade">
                <!-- The global progress bar -->
                <div
                    class="progress progress-success progress-striped active"
                    role="progressbar" aria-valuemin="0"
                    aria-valuemax="100">
                    <div class="bar" style="width: 0%;"></div>
                </div>
                <!-- The extended global progress information -->
                <!-- <div class="progress-extended"></div>  -->
            </div>
        </div>
        <!-- The table listing the files available for upload/download -->
        <table id="filePresentation" role="presentation"
            class="table table-striped">
            <tbody class="files" data-toggle="modal-gallery"
                data-target="#modal-gallery"></tbody>
        </table>

    </form:form>
</div>
<!-- The template to display files available for upload -->
<script
    src="${pageContext.request.contextPath}/resources/jQuery-File-Upload/js/vendor/jquery.ui.widget.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jQuery-File-Upload/js/jquery.iframe-transport.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jQuery-File-Upload/js/jquery.fileupload.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jQuery-File-Upload/js/load-image.min.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jQuery-File-Upload/js/jquery.fileupload-process.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jQuery-File-Upload/js/jquery.fileupload-resize.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jQuery-File-Upload/js/jquery.fileupload-validate.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/jQuery-File-Upload/js/jquery.fileupload-ui.js"></script>
<script
    src="${pageContext.request.contextPath}/resources/js/timesheet.js"></script>