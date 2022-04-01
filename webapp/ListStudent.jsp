<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div id="container">
		<div style="height: 300px">
			<input type="button" value="Add Student" onclick="window.location.href='AddStudentForm.jsp'; return false" class="add-student-button" >
			<table>
				<tr>
					<th> First Name</th>
					<th> Last Name</th>
					<th> Email Name</th>
					<th> Action Name</th>
				</tr>
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
					<c:url var="tempLink" value="StudentControllerServerlet">
						<c:param name="command" value="LOAD"></c:param>
						<c:param name="studentId" value="${tempStudent.id}"></c:param>
					</c:url>
					
					<c:url var="deleteLink" value="StudentControllerServerlet">
						<c:param name="command" value="DELETE"></c:param>
						<c:param name="studentId" value="${tempStudent.id}"></c:param>
					</c:url>
					
					<tr>
						<td> ${tempStudent.firstName}</td>
						<td> ${tempStudent.lastName}</td>
						<td> ${tempStudent.email}</td>
						<td>
							<a href="${tempLink}">Update</a>
							<a href="${deleteLink }" onclick="if(!(confirm('Are you sure you want to delete this student?'))) return false">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div>
			<span>Phan Dinh Nhat - 19511041</span>
		</div>
	</div>
</body>
</html>