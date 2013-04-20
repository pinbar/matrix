<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${clients != null}">
    <h4>
        <u>Clients</u>
    </h4>
    <table class="table table-striped table-bordered table-condensed">
        <thead>
            <tr class="well">
                <th><u>Id</u></th>
                <th><u>Name</u></th>
                <th><u>Employees</u></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="client" items="${clients}">
                <tr>
                    <td>${client.id}</td>
                    <td>${client.name}</td>
                    <td>${client.employees}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>