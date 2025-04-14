package portal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@WebServlet("/editPublication")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class EditPublicationServlet extends HttpServlet {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String UPLOAD_DIR = "uploads";

    static {
        DATE_FORMAT.setLenient(false);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer profId = (Integer) request.getSession().getAttribute("profId");
        if (profId == null) {
            response.sendRedirect("login.jsp?error=Please login first");
            return;
        }

        int pubId = Integer.parseInt(request.getParameter("id"));
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM publications WHERE id = ? AND prof_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, pubId);
            stmt.setInt(2, profId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Publication pub = new Publication();
                pub.setId(rs.getInt("id"));
                pub.setTitle(rs.getString("title"));
                pub.setType(rs.getString("type"));
                pub.setDate(rs.getDate("publication_date"));
                pub.setPublisher(rs.getString("publisher_name"));
                pub.setDoi(rs.getString("doi"));
                pub.setDocumentPath(rs.getString("document_path"));
                pub.setProfId(rs.getInt("prof_id"));
                pub.setAbstract(rs.getString("abstract")); // New field
                request.setAttribute("publication", pub);
                request.getRequestDispatcher("editPublication.jsp").forward(request, response);
            } else {
                response.sendRedirect("listPublications.jsp?error=You can only edit your own publications");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("listPublications.jsp?error=Database error: " + e.getMessage());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                response.sendRedirect("listPublications.jsp?error=You can only edit your own publications");
                return;
            }

            String type = request.getParameter("type");
            String title = request.getParameter("title");
            String dateStr = request.getParameter("date");
            String publisher = request.getParameter("publisher");
            String doi = request.getParameter("doi");
            String abstractText = request.getParameter("abstract"); // New field
            Part filePart = request.getPart("document");

            java.sql.Date sqlDate;
            try {
                java.util.Date utilDate = DATE_FORMAT.parse(dateStr);
                sqlDate = new java.sql.Date(utilDate.getTime());
            } catch (java.text.ParseException e) {
                response.sendRedirect("editPublication.jsp?id=" + pubId + "&error=Invalid date. Use YYYY-MM-DD");
                return;
            }

            String documentPath = null;
            if (filePart != null && filePart.getSize() > 0) {
                String appPath = request.getServletContext().getRealPath("");
                String uploadPath = appPath + File.separator + UPLOAD_DIR;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String fileName = extractFileName(filePart);
                documentPath = UPLOAD_DIR + File.separator + profId + "_" + System.currentTimeMillis() + "_" + fileName;
                filePart.write(appPath + File.separator + documentPath);
            }

            String query = "UPDATE publications SET type = ?, title = ?, publication_date = ?, publisher_name = ?, doi = ?, document_path = IFNULL(?, document_path), abstract = ? WHERE id = ? AND prof_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, type);
            stmt.setString(2, title);
            stmt.setDate(3, sqlDate);
            stmt.setString(4, publisher);
            stmt.setString(5, doi);
            stmt.setString(6, documentPath);
            stmt.setString(7, abstractText); // New field
            stmt.setInt(8, pubId);
            stmt.setInt(9, profId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                response.sendRedirect("listPublications.jsp?success=Publication updated");
            } else {
                response.sendRedirect("listPublications.jsp?error=Update failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("listPublications.jsp?error=Database error: " + e.getMessage());
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String cd : contentDisp.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}