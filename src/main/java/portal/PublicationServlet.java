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
import java.sql.SQLException;
import java.text.SimpleDateFormat;

@WebServlet("/publication")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class PublicationServlet extends HttpServlet {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final String UPLOAD_DIR = "uploads";

    static {
        DATE_FORMAT.setLenient(false);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer profId = (Integer) request.getSession().getAttribute("profId");
        if (profId == null) {
            response.sendRedirect("login.jsp?error=Please login first");
            return;
        }

        String action = request.getParameter("action");
        if ("add".equals(action)) {
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
                response.sendRedirect("addPublication.jsp?error=Invalid date. Use YYYY-MM-DD");
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

            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO publications (prof_id, type, title, publication_date, publisher_name, doi, document_path, abstract) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, profId);
                stmt.setString(2, type);
                stmt.setString(3, title);
                stmt.setDate(4, sqlDate);
                stmt.setString(5, publisher);
                stmt.setString(6, doi);
                stmt.setString(7, documentPath);
                stmt.setString(8, abstractText); // New field
                stmt.executeUpdate();
                response.sendRedirect("listPublications.jsp?success=Publication added");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("addPublication.jsp?error=Database error: " + e.getMessage());
            }
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