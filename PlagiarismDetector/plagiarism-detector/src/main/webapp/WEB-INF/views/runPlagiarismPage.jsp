<%@page import="com.plagiarism.detector.model.Result"%>
<%@page import="java.util.ArrayList"%>
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
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet" />
<link
	href="https://rawgit.com/wenzhixin/bootstrap-table/master/src/bootstrap-table.css"
	rel="stylesheet" />


<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<link href="/resources/css/style.css" rel="stylesheet" />
<script type="text/javascript" src="/resources/js/app.js"></script>
<script type="text/javascript"
	src="https://rawgit.com/wenzhixin/bootstrap-table/master/src/bootstrap-table.js"></script>



</head>

<body>
	<script>
		window.onload = function() {
			var x = document.getElementById("loader");
			var y = document.getElementById("content");
			x.style.display = "none";
			y.style.display = "block";
		};

		function myFunction(nav) {
			var x = document.getElementById("loader");
			var y = document.getElementById("content");
			y.style.display = "none";
			x.style.display = "block";
			window.location.href = nav;
		}
	</script>


	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<form:form id="form2" name="resultgrid" modelAttribute="result">
		<div class="container">
			<h1>
				<font color="green"><i>Choose Strategy</i></font>
			</h1>
			<div class="row">
				<div class="col-lg-2">
					<a class="btn btn-primary btn-lg"
						onclick="myFunction('/CodeMoveOver')">Code Move Over</a>
				</div>
				
				

				<div class="col-lg-2">
					<a class="btn btn-primary btn-lg"
						onclick="myFunction('/VariableCheck')">Variable Check</a>
				</div>
				

				<div class="col-lg-2">
					<a class="btn btn-primary btn-lg"
						onclick="myFunction('/ModularityCheck')">Modularity Check</a>
				</div>
				
				<div class="col-lg-3">
					<a class="btn btn-primary btn-lg"
						onclick="myFunction('/CommentsCheck')">Comments Similarity</a>
				</div>

				<div class="col-lg-2">
					<a class="btn btn-primary btn-lg"
						onclick="myFunction('/WeightedAvg')">Weighted Average</a>
				</div>
				

			</div>
			<div id="loader" class="well">
				<i class="fa fa-spinner fa-spin  fa-3x"></i>
				<p align="center">
					<font size="6">Please wait while we fetch your results!</font>
				</p>
			</div>
			<div id="content" class="well">
			
			<c:if test="${empty displayResult}">	
				<p align="center">
					<font size="6">Please select one of the above strategies!</font>
				</p>		
			</c:if>
		
			<c:if test="${not empty displayResult}">
				
			
				<form:form></form:form>

				<table class="table table-striped table-bordered"
					data-toggle="table">
					<thead>
						<tr>
							<th scope="col">SUBMISSION 1</th>

							<th scope="col">SUBMISSION 2</th>
							<th data-sortable="true" scope="col">RESULTS</th>
							<th scope="col">DOWNLOAD</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${result}" var="res">
							<tr>
								<td><c:out value="${res.file1}" /></td>
								<td><c:out value="${res.file2}" /></td>
								<td><c:if test="${res.outcome == 'PLAGIARIZED'}">
										<font color="red"> <c:out value="${res.outcome}" /></font>
									</c:if> <c:if test="${res.outcome == 'NOTPLAGIARIZED'}">
										<font color="green"> <c:out value="${res.outcome}" /></font>
									</c:if></td>
								<td><form:form name="submitlogin" action="/download"
										method="POST" modelAttribute="result1">

										<form:input type="hidden" path="file1" name="file1"
											value="${res.file1}" />
										<form:input type="hidden" name="file2" value="${res.file2}"
											path="file2" />
										<input type="submit" value="Get Report" />
									</form:form></td>
							</tr>

						</c:forEach>
					</tbody>
				</table>
			</c:if>
			
			</div>

			<a class="btn btn-success btn-block" href="/login"
				style="margin-left: 0%;">Logout</a>
		</div>

	</form:form>

</body>
</html>