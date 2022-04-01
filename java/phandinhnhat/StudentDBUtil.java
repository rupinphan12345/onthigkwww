package phandinhnhat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {
	private DataSource dataSource;

	public StudentDBUtil(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	private void close(Connection myConnection, Statement myStatement, ResultSet myResultSet) {
		try {
			if(myConnection != null)
				myConnection.close();
			if(myStatement != null)
				myStatement.close();
			if(myResultSet != null)
				myResultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Student> getStudents() throws SQLException {
		List<Student> studentList = new ArrayList<Student>();
		Connection myConnection = null;
		Statement myStatement = null;
		ResultSet myResultSet = null;
		
		try {
			myConnection = dataSource.getConnection();
			String query = "SELECT * FROM STUDENT";
			myStatement = myConnection.createStatement();
			myResultSet = myStatement.executeQuery(query);
			while(myResultSet.next()) {
				int id = myResultSet.getInt("id");
				String firstName = myResultSet.getString("firstName");
				String lastName = myResultSet.getString("lastName");
				String email = myResultSet.getString("email");
				Student student = new Student(id, firstName, lastName, email);
				studentList.add(student);
			}
			return studentList;
		} finally {
			close(myConnection, myStatement, myResultSet);
		}
	}
	
	public void addStudent(Student student) {
		Connection myConnection = null;
		PreparedStatement myPreparedStatement = null;
		try {
			myConnection = dataSource.getConnection();
			String query = "INSERT INTO STUDENT (firstName, LastName, email) VALUES (?, ?, ?)";
			myPreparedStatement = myConnection.prepareStatement(query);
			myPreparedStatement.setString(1, student.getFirstName());
			myPreparedStatement.setString(2, student.getLastName());
			myPreparedStatement.setString(3, student.getEmail());
			
			myPreparedStatement.execute();
			
		} catch (Exception e) {
			close(myConnection, myPreparedStatement, null);
		}
	}
	
	public void deleteStudent(String theStudentID) throws SQLException {
		Connection myConnection = null;
		PreparedStatement myStatement = null;
		try {
			int studentID = Integer.parseInt(theStudentID);
			myConnection = dataSource.getConnection();
			String query = "DELETE FROM STUDENT WHERE ID = ?";
			myStatement = myConnection.prepareStatement(query);
			myStatement.setInt(1, studentID);
			myStatement.execute();
		} finally {
			close(myConnection, myStatement, null);
		}
	}
	
	public Student getStudent(String theSutdentId) throws Exception {
		Student student = null;
		Connection myConnection = null;
		PreparedStatement myPreparedStatement = null;
		ResultSet myResultSet = null;
		try {
			int studentId = Integer.parseInt(theSutdentId);
			myConnection = dataSource.getConnection();
			String sql = "SELECT * FROM STUDENT WHERE ID = ?";
			myPreparedStatement = myConnection.prepareStatement(sql);
			myPreparedStatement.setInt(1, studentId);
			myResultSet = myPreparedStatement.executeQuery();
			if(myResultSet.next()) {
				String firstName = myResultSet.getString("firstName");
				String lastName = myResultSet.getString("lastName");
				String email = myResultSet.getString("email");
				student = new Student(studentId,firstName, lastName, email);
			}else {
				throw new Exception("Could not find student id: " + studentId);
			}
			return student;
		} finally {
			// TODO: handle exception
			close(myConnection, myPreparedStatement, myResultSet);
		}
	}
	
	public void updateStudent(Student student) throws SQLException {
		Connection myConnection = null;
		PreparedStatement myPreparedStatement = null;
		try {
			myConnection = dataSource.getConnection();
			String query = "UPDATE STUDENT SET FirstName = ?, LastName = ?, Email=? WHERE ID = ?";
			myPreparedStatement = myConnection.prepareStatement(query);
			myPreparedStatement.setString(1, student.getFirstName());
			myPreparedStatement.setString(2, student.getLastName());
			myPreparedStatement.setString(3, student.getEmail());
			myPreparedStatement.setInt(4, student.getId());
			myPreparedStatement.execute();
			
		} finally {
			close(myConnection, myPreparedStatement, null);
		}
	}

}
