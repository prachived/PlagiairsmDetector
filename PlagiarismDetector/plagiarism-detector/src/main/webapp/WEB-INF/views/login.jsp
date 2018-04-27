<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>Assignment Home Page</title>

<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet" />
<link href="/resources/css/style.css" rel="stylesheet" />
<script type="text/javascript" src="/resources/js/app.js"></script>


</head>

<body>

	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<form:form name="submitlogin" action="/login" method="POST"
		modelAttribute="user">
		<div class="container">
			<h1>
				<font color="blue"><i>Plagiarism Detection Application</i></font>
			</h1>
			<c:if test="${not empty error}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${error}" />
				</div>
			</c:if>

			<h1>Login</h1>

			<br>
			<form:input path="name" required="required" type="text"
				class="form-control" placeholder="username" />
			<form:input path="password" required="required" type="password"
				name="password" class="form-control" placeholder="password" />

			<a class="btn btn-primary btn-block" onclick="submitlogin.submit();">Login</a>

		</div>

	</form:form>
</body>
</html>