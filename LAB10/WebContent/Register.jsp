<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Student details</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/Register.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>User Management</h1>
				<form id="formRegister" name="formRegister">
					User Name: <input id="name" name="name" type="text"
						class="form-control form-control-sm"> <br>
					User Email:<input id="email" name="email" type="text"
						class="form-control form-control-sm"> <br> 
					User Password: <input id="password" name="password" type="text"
						class="form-control form-control-sm"> <br>
					User repassword: <input id="repassword" name="repassword" type="text"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidRegisterIDSave" name="hidRegisterIDSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divRegisterGrid">
					<%
						Register registerObj = new Register();
					out.print(registerObj.readRegister());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
