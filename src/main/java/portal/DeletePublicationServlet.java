package portal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/deletePublication")
public class DeletePublicationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer profId = (Integer) request.getSession().getAttribute("profId");
        if (profId == null) {
            response.sendRedirect("login.jsp?error=Please login first");
            return;
        }

        int pubId = Integer.parseInt(request.getParameter("id"));
        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkQuery = "SELECT prof_id FROM publications WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, pubId);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next() || rs.getInt("prof_id") != profId) {
                response.sendRedirect("listPublications.jsp?error=You can only delete your own publications");
                return;
            }

            String query = "DELETE FROM publications WHERE id = ? AND prof_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, pubId);
            stmt.setInt(2, profId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("listPublications.jsp?success=Publication deleted");
            } else {
                response.sendRedirect("listPublications.jsp?error=Deletion failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("listPublications.jsp?error=Database error: " + e.getMessage());
        }
    }
}