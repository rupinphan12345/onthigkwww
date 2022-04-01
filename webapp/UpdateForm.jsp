<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="container">
		<form action="StudentControllerServerlet" method="get">
			<input type="hidden" name="command" value="UPDATE">
			<input type="hidden" name="studentId" value="${THE_STUDENT.id}">
			<table>
				<tr>
					<td><label>First Name:</label></td>
					<td><input type="text" name="firstName" value="${THE_STUDENT.firstName}"></td>
				</tr>
				<tr>
					<td><label>Last Name:</label></td>
					<td><input type="text" name="lastName" value="${THE_STUDENT.lastName}"></td>
				</tr>
				<tr>
					<td><label>Email:</label></td>
					<td><input type="text" name="email" value="${THE_STUDENT.email}"></td>
				</tr>
				<tr>
					<td><label></label></td>
					<td><input type="submit" value="Update" class="save"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>