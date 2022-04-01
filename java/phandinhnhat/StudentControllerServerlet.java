package phandinhnhat;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/StudentControllerServerlet")
public class StudentControllerServerlet extends HttpServlet{

	private StudentDBUtil studentDBUtil;
	@Resource(name = "jdbc/list_student")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
			studentDBUtil = new StudentDBUtil(dataSource);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServletException();
		}
	}
	
	private void listStudent (HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Student> studentList = studentDBUtil.getStudents();
		request.setAttribute("STUDENT_LIST", studentList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/ListStudent.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		Student student = new Student(firstName, lastName, email);
		studentDBUtil.addStudent(student);
		listStudent(request, response);
	}
	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String theStudentId = request.getParameter("studentId");
		studentDBUtil.deleteStudent(theStudentId);
		listStudent(request, response);
	}
	
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String studentID = request.getParameter("studentId");
		Student student = studentDBUtil.getStudent(studentID);
		request.setAttribute("THE_STUDENT", student);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/UpdateForm.jsp");
		dispatcher.forward(request, response);
	}
	
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		Student student = new Student(id, firstName, lastName, email);
		studentDBUtil.updateStudent(student);
		listStudent(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String theCommand = request.getParameter("command");
			if(theCommand == null)
				theCommand = "LIST";
			
			switch (theCommand) {
			case "LIST": {
				listStudent(request, response);
				break;
			}
			case "ADD":
				addStudent(request, response);
				break;
			case "DELETE":
				deleteStudent(request, response);
				break;
			case "LOAD":
				loadStudent(request, response);
				break;
			case "UPDATE":
				updateStudent(request, response);
				break;
			default:
				listStudent(request, response);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
	
}
