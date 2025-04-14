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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/listPublications")
public class ListPublicationsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer profId = (Integer) request.getSession().getAttribute("profId");
        if (profId == null) {
            response.sendRedirect("login.jsp?error=Please login first");
            return;
        }

        String search = request.getParameter("search");
        String typeFilter = request.getParameter("type");
        String dateFilter = request.getParameter("date");

        StringBuilder query = new StringBuilder(
            "SELECT p.*, pr.name AS professor_name " +
            "FROM publications p " +
            "JOIN professors pr ON p.prof_id = pr.id " +
            "WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            query.append(" AND p.title LIKE ?");
            params.add("%" + search + "%");
        }
        if (typeFilter != null && !typeFilter.isEmpty()) {
            query.append(" AND p.type = ?");
            params.add(typeFilter);
        }
        if (dateFilter != null && !dateFilter.isEmpty()) {
            query.append(" AND p.publication_date = ?");
            params.add(dateFilter);
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();

            List<Publication> publications = new ArrayList<>();
            while (rs.next()) {
                Publication pub = new Publication();
                pub.setId(rs.getInt("id"));
                pub.setTitle(rs.getString("title"));
                pub.setType(rs.getString("type"));
                pub.setDate(rs.getDate("publication_date"));
                pub.setPublisher(rs.getString("publisher_name"));
                pub.setDoi(rs.getString("doi"));
                pub.setDocumentPath(rs.getString("document_path"));
                pub.setProfId(rs.getInt("prof_id"));
                pub.setProfessorName(rs.getString("professor_name"));
                pub.setAbstract(rs.getString("abstract")); // New field
                publications.add(pub);
            }
            request.setAttribute("publications", publications);
            request.setAttribute("currentProfId", profId);
            request.getRequestDispatcher("listPublications.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("dashboard.jsp?error=Database error: " + e.getMessage());
        }
    }
}