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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer profId = (Integer) request.getSession().getAttribute("profId");
        if (profId == null) {
            response.sendRedirect("login.jsp?error=Please login first");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Fetch publication counts
            String countQuery = "SELECT type, COUNT(*) as count FROM publications WHERE prof_id = ? GROUP BY type";
            PreparedStatement countStmt = conn.prepareStatement(countQuery);
            countStmt.setInt(1, profId);
            ResultSet countRs = countStmt.executeQuery();

            Map<String, Integer> counts = new HashMap<>();
            counts.put("JOURNAL", 0);
            counts.put("CONFERENCE", 0);
            counts.put("BOOK_CHAPTER", 0);
            counts.put("PROJECT", 0);
            while (countRs.next()) {
                counts.put(countRs.getString("type"), countRs.getInt("count"));
            }

            // Fetch publication list
            String listQuery = "SELECT * FROM publications WHERE prof_id = ?";
            PreparedStatement listStmt = conn.prepareStatement(listQuery);
            listStmt.setInt(1, profId);
            ResultSet listRs = listStmt.executeQuery();

            List<Publication> publications = new ArrayList<>();
            while (listRs.next()) {
                Publication pub = new Publication();
                pub.setId(listRs.getInt("id"));
                pub.setTitle(listRs.getString("title"));
                pub.setType(listRs.getString("type"));
                pub.setDate(listRs.getDate("publication_date"));
                pub.setPublisher(listRs.getString("publisher_name"));
                pub.setDoi(listRs.getString("doi"));
                pub.setDocumentPath(listRs.getString("document_path"));
                pub.setAbstract(listRs.getString("abstract"));
                publications.add(pub);
            }

            request.setAttribute("counts", counts);
            request.setAttribute("myPublications", publications);
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database error: " + e.getMessage());
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        }
    }
}