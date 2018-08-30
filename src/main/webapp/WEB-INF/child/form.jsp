<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: wdr434
  Date: 27.08.18
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/css/bootstrap.min.css" integrity="sha384-Smlep5jCw/wG7hdkwQ/Z5nLIefveQRIY9nfy6xoR1uRYBtpZgI6339F5dgvm/e9B" crossorigin="anonymous">

    <title>Child Form</title>
</head>
<body>
<c:import url="../fragments/header.jsp"/>

    <form:form modelAttribute="child" method="post">
        <form:errors path="*"/><br/>
        <form:input path="firstName" placeholder="First Name"/>
        <form:input path="secondName" placeholder="Second Name"/>
        <form:input path="pesel" placeholder="PESEL"/>
        <form:select path="sex">
            <form:option value="-" label="--Select sex--"/>
            <form:options items="${sexMap}"/>
        </form:select>
        <input type="submit" value="Add Child">
    </form:form>


<c:if test="${not empty children}">

        <h4>
            Do you want create family?
            <a href="/family/add" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Yes</a>
        </h4>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">First Name</th>
                <th scope="col">Second Name</th>
                <th scope="col">PESEL</th>
                <th scope="col">sex</th>
            </tr>
            </thead>
            <tbody>
    <c:forEach items="${children}" var="child">
            <tr>
                <td>${child.firstName}</td>
                <td>${child.secondName}</td>
                <td>${child.pesel}</td>
                <td>${child.sex}</td>
            </tr>
    </c:forEach>
            </tbody>
        </table>
</c:if>


<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js" integrity="sha384-o+RDsa0aLu++PJvFqy8fFScvbHFLtbvScb8AjopnFD+iEQ7wo/CG0xlczd+2O/em" crossorigin="anonymous"></script>
</body>
</html>
