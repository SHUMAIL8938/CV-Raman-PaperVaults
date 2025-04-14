<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Publication</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">CV Raman PaperVaults</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="dashboard">Dashboard</a>
                <a class="nav-link" href="listPublications.jsp">View/Edit Publications</a>
                <a class="nav-link" href="logout">Logout</a>
            </div>
        </div>
    </nav>
    <div class="container">
        <h1>Add a Publication</h1>
        <div class="card shadow mt-4">
            <div class="card-body">
                <form action="publication" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="add">
                    <div class="mb-3">
                        <label for="type" class="form-label">Type</label>
                        <select class="form-select" id="type" name="type">
                            <option value="JOURNAL">Journal</option>
                            <option value="CONFERENCE">Conference</option>
                            <option value="BOOK_CHAPTER">Book Chapter</option>
                            <option value="PROJECT">Project</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="title" class="form-label">Title</label>
                        <input type="text" class="form-control" id="title" name="title" required>
                    </div>
                    <div class="mb-3">
                        <label for="date" class="form-label">Date</label>
                        <input type="date" class="form-control" id="date" name="date" required>
                    </div>
                    <div class="mb-3">
                        <label for="publisher" class="form-label">Publisher</label>
                        <input type="text" class="form-control" id="publisher" name="publisher">
                    </div>
                    <div class="mb-3">
                        <label for="doi" class="form-label">DOI</label>
                        <input type="text" class="form-control" id="doi" name="doi">
                    </div>
                    <div class="mb-3">
                        <label for="abstract" class="form-label">Abstract</label>
                        <textarea class="form-control" id="abstract" name="abstract" rows="4" placeholder="Enter abstract here"></textarea>
                    </div>
                    <div class="mb-3">
                        <label for="document" class="form-label">Document</label>
                        <input type="file" class="form-control" id="document" name="document">
                    </div>
                    <button type="submit" class="btn btn-custom">Add Publication</button>
                </form>
                <% if (request.getParameter("success") != null) { %>
                    <div class="alert alert-success mt-3"><%= request.getParameter("success") %></div>
                <% } %>
                <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger mt-3"><%= request.getParameter("error") %></div>
                <% } %>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>