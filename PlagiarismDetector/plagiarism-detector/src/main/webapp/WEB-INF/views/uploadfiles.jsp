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
<link href="/resources/css/style.css" rel="stylesheet" />
<script type="text/javascript" src="/resources/js/app.js"></script>


</head>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<body>

	<div class="container">

		<form:form name="uploadFiles" action="/uploadfiles" method="POST"
			enctype="multipart/form-data" modelAttribute="user">

			<h1 name="name" class="text-success">Hello ${name} !!</h1>
			<h4 name="msg" class="text-success">Please upload Python source
				code files</h4>

			<form:input path="name" type="hidden" value="${name}" />
			<br />
			<div class="well">

				<c:if test="${not empty uploadSuccessful}">
					<div class="alert alert-success" role="alert">
						<c:out value="${uploadSuccessful}" />
					</div>
				</c:if>
				<c:if test="${not empty error}">
					<div class="alert alert-danger" role="alert">
						<c:out value="${error}" />
					</div>
				</c:if>
				<label class="text-danger">Upload two separate Submission</label>
				<div class="container row">
					<div class="col-lg-3" style="margin-top: 1.5%;">
						<label class="text-success">Select Folder for Student 1: </label>
					</div>
					<div class="col-lg-8">
						<input type="file" name="filesStudent1" id="file"
							class="custom-file-input" webkitdirectory="" directory=""
							multiple=""> <span class="custom-file-control"></span>
					</div>
				</div>
				<div class="container row">
					<div class="col-lg-3" style="margin-top: 1.5%;">
						<label class="text-success">Select Folder for Student 2: </label>
					</div>
					<div class="col-lg-8">
						<input type="file" name="filesStudent2" id="file"
							class="custom-file-input" webkitdirectory="" directory=""
							multiple=""> <span class="custom-file-control"></span>
					</div>
				</div>
				<a class="btn btn-primary btn-lg" onclick="uploadFiles.submit();">Upload
					Files</a>
		</form:form>
		<c:if test="${not empty uploadSuccessful}">

			<form:form name="runPlg" action="/runPlg" method="POST"
				enctype="multipart/form-data" modelAttribute="user">
				<form:input path="name" type="hidden" name="name"
					class="form-control" value="${name}" />
				<a class="btn btn-primary btn-lg" onclick="runPlg.submit();">Run
					Plagiarism</a>
			</form:form>
		</c:if>
	</div>

	<div class="well">
		<form:form name="uploadFilesInBatch" action="/uploadfilesInBatch"
			method="POST" enctype="multipart/form-data" modelAttribute="user">

			<form:input path="name" type="hidden" value="${name}" />

			<c:if test="${not empty uploadSuccessfulBatch}">
				<div class="alert alert-success" role="alert">
					<c:out value="${uploadSuccessfulBatch}" />
				</div>
			</c:if>
			
			<c:if test="${not empty error1}">
					<div class="alert alert-danger" role="alert">
						<c:out value="${error1}" />
					</div>
			</c:if>

			<label class="text-danger">Upload Batch Submission Folder</label>

			<div class="container row">
				<div class="col-lg-4" style="margin-top: 1.5%;">
					<label class="text-success">Select Folder for Student
						Submissions: </label>
						<div class="well">
							<p>Please put all
						student submissions in a folder "uploads" and upload that folder </p>
			
						</div>
					</div>
				<div class="col-lg-8">
					<input type="file" name="filesStudent" id="file"
						class="custom-file-input" webkitdirectory="" directory=""
						multiple=""> <span class="custom-file-control"></span>
				</div>
			</div>
			<a class="btn btn-primary btn-lg"
				onclick="uploadFilesInBatch.submit();">Upload Files</a>
		</form:form>
		<c:if test="${not empty uploadSuccessfulBatch}">

			<form:form name="runPlg" action="/runPlg" method="POST"
				enctype="multipart/form-data" modelAttribute="user">
				<form:input path="name" type="hidden" name="name"
					class="form-control" value="${name}" />
				<a class="btn btn-primary btn-lg" onclick="runPlg.submit();">Run
					Plagiarism</a>
			</form:form>
		</c:if>
	</div>

	<a class="btn btn-success btn-block" style="mragin: 0%" href="/login">Logout</a>

	</div>


</body>
</html>