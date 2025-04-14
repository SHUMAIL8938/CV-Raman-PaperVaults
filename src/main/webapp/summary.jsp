<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>Publication Summary</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="css/styles.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">C.V.Raman PaperVaults</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="dashboard.jsp">Dashboard</a>
                <a class="nav-link" href="addPublication.jsp">Add Publication</a>
                <a class="nav-link" href="listPublications.jsp">View/Edit Publications</a>
                <a class="nav-link" href="logout">Logout</a>
            </div>
        </div>
    </nav>
    <div class="container">
        <h1>Publication Summary</h1>
        <div class="card shadow mt-4">
            <div class="card-body">
                <%
                    Map<String, Integer> summary = (Map<String, Integer>) request.getAttribute("summary");
                    if (summary != null && !summary.isEmpty()) {
                %>
                    <ul class="list-group list-group-flush">
                        <% for (Map.Entry<String, Integer> entry : summary.entrySet()) { %>
                            <li class="list-group-item"><%= entry.getKey() %>: <%= entry.getValue() %></li>
                        <% } %>
                    </ul>
                <% } else { %>
                    <p class="text-muted">No publications found.</p>
                <% } %>
            </div>
        </div>
        <a href="addPublication.jsp" class="btn btn-outline-secondary mt-3">Back</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>