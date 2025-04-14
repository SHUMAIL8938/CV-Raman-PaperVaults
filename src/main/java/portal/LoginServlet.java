package portal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    try (Connection conn = DatabaseConnection.getConnection()) {
	        String query = "SELECT id, name FROM professors WHERE username = ? AND password = ?";
	        PreparedStatement stmt = conn.prepareStatement(query);
	        stmt.setString(1, username);
	        stmt.setString(2, password);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            HttpSession session = request.getSession();
	            int profId = rs.getInt("id");
	            session.setAttribute("profId", profId);
	            session.setAttribute("profName", rs.getString("name"));
	            System.out.println("Login: Setting profId = " + profId); // Debug
	            response.sendRedirect("dashboard"); // Ensure this is "dashboard", not "dashboard.jsp"
	        } else {
	            response.sendRedirect("login.jsp?error=Invalid username or password");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.sendRedirect("login.jsp?error=Database error: " + e.getMessage());
	    }
	}
    }
