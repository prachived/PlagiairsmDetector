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

	<form:form name="registerUser" action="/register" method="POST"
		modelAttribute="user">
		<div class="container">
			<c:if test="${not empty regFail}">
				<div class="alert alert-danger" role="alert">
					<c:out value="${regFail}" />
				</div>
			</c:if>
			<c:if test="${not empty success}">
				<div class="alert alert-success" role="alert">
					<c:out value="${success}" />
				</div>
			</c:if>
			<h1>Register</h1>
			<form:input type="text" required="required" path="name"
				class="form-control" placeholder="Username" />
			<form:input type="text" required="required" class="form-control"
				path="fname"
				placeholder="First Name" />
			<form:input type="text" required="required" path="lname"
				class="form-control" placeholder="Last Name" />
			<form:input type="email" required="required" class="form-control"
				path="email" placeholder="Email" onChange="return ValidateEmail()" />
			<form:input type="password" required="required" path="password"
				class="form-control" placeholder="Password" />
			<a class="btn btn-primary
			 btn-block" onclick="registerUser.submit();">Register</a>
	</form:form>
	<form:form name="deleteUser" action="/delete" method="POST"
		modelAttribute="user">
		<c:if test="${not empty deleteSuccess}">
			<div class="alert alert-success" role="alert">
				<c:out value="${deleteSuccess}" />
			</div>
		</c:if>

		<c:if test="${not empty deleteUnSuccess}">
			<div class="alert alert-danger" role="alert">
				<c:out value="${deleteUnSuccess}" />
			</div>
		</c:if>

		<h1>Delete User</h1>

		<form:input type="text" path="name" class="form-control"
			placeholder="Username" />
		<a class="btn btn-danger  btn-block" onclick="deleteUser.submit();">Delete</a>
		<a class="btn btn-success btn-block" href="/login">Logout</a>
	</form:form>
	</div>

</body>
</html>