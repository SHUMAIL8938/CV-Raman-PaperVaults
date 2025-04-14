<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="portal.Publication" %>
<html>
<head>
    <title>Edit Publication</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">CV Raman PaperVaults</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="dashboard">Dashboard</a>
                <a class="nav-link" href="addPublication.jsp">Add Publication</a>
                <a class="nav-link" href="listPublications.jsp">View/Edit Publications</a>
                <a class="nav-link" href="logout">Logout</a>
            </div>
        </div>
    </nav>
    <div class="container">
        <h1>Edit Publication</h1>
        <div class="card shadow mt-4">
            <div class="card-body">
                <%
                    Publication pub = (Publication) request.getAttribute("publication");
                    if (pub != null) {
                %>
                <form action="editPublication" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="<%= pub.getId() %>">
                    <div class="mb-3">
                        <label for="type" class="form-label">Type</label>
                        <select class="form-select" id="type" name="type">
                            <option value="JOURNAL" <%= "JOURNAL".equals(pub.getType()) ? "selected" : "" %>>Journal</option>
                            <option value="CONFERENCE" <%= "CONFERENCE".equals(pub.getType()) ? "selected" : "" %>>Conference</option>
                            <option value="BOOK_CHAPTER" <%= "BOOK_CHAPTER".equals(pub.getType()) ? "selected" : "" %>>Book Chapter</option>
                            <option value="PROJECT" <%= "PROJECT".equals(pub.getType()) ? "selected" : "" %>>Project</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="title" class="form-label">Title</label>
                        <input type="text" class="form-control" id="title" name="title" value="<%= pub.getTitle() %>" required>
                    </div>
                    <div class="mb-3">
                        <label for="date" class="form-label">Date</label>
                        <input type="date" class="form-control" id="date" name="date" value="<%= pub.getDate() %>" required>
                    </div>
                    <div class="mb-3">
                        <label for="publisher" class="form-label">Publisher</label>
                        <input type="text" class="form-control" id="publisher" name="publisher" value="<%= pub.getPublisher() != null ? pub.getPublisher() : "" %>">
                    </div>
                    <div class="mb-3">
                        <label for="doi" class="form-label">DOI</label>
                        <input type="text" class="form-control" id="doi" name="doi" value="<%= pub.getDoi() != null ? pub.getDoi() : "" %>">
                    </div>
                    <div class="mb-3">
                        <label for="abstract" class="form-label">Abstract</label>
                        <textarea class="form-control" id="abstract" name="abstract" rows="4"><%= pub.getAbstract() != null ? pub.getAbstract() : "" %></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="document" class="form-label">Document (Current: <%= pub.getDocumentPath() != null ? "<a href='" + pub.getDocumentPath() + "'>Download</a>" : "None" %>)</label>
                        <input type="file" class="form-control" id="document" name="document">
                    </div>
                    <button type="submit" class="btn btn-custom">Update Publication</button>
                </form>
                <% } %>
                <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger mt-3"><%= request.getParameter("error") %></div>
                <% } %>
            </div>
        </div>
        <a href="listPublications.jsp" class="btn btn-outline-secondary mt-3">Back to List</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>