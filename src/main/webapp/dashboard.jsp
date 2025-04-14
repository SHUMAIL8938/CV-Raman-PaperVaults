<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map, java.util.List, portal.Publication" %>
<html>
<head>
    <title>Dashboard - CV Raman PaperVaults</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="css/styles.css" rel="stylesheet">
    <style>
        .abstract-text {
            max-width: 300px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .abstract-text:hover {
            white-space: normal;
            overflow: visible;
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">CV Raman PaperVaults</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link" href="addPublication.jsp">Add Publication</a>
            <a class="nav-link" href="listPublications.jsp">View/Edit Publications</a>
            <a class="nav-link" href="dashboard">Dashboard</a> <!-- Updated -->
            <a class="nav-link" href="logout">Logout</a>
        </div>
    </div>
</nav>
    <div class="container">
        <h1>Welcome, <%= session.getAttribute("profName") %></h1>
        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-body">
                        <h2 class="card-title">Your Publications</h2>
                        <%
                            Map<String, Integer> counts = (Map<String, Integer>) request.getAttribute("counts");
                            if (counts != null) {
                        %>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">Journal: <%= counts.get("JOURNAL") %></li>
                                <li class="list-group-item">Conference: <%= counts.get("CONFERENCE") %></li>
                                <li class="list-group-item">Book Chapters: <%= counts.get("BOOK_CHAPTER") %></li>
                                <li class="list-group-item">Projects: <%= counts.get("PROJECT") %></li>
                            </ul>
                        <% } else { %>
                            <p class="text-muted">No publication data available.</p>
                        <% } %>
                        <% if (request.getAttribute("error") != null) { %>
                            <div class="alert alert-danger mt-3"><%= request.getAttribute("error") %></div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
        <!-- My Publications Section -->
        <div class="row mt-4">
            <div class="col-md-12">
                <div class="card shadow">
                    <div class="card-body">
                        <h2 class="card-title">My Publications</h2>
                        <%
                            List<Publication> myPublications = (List<Publication>) request.getAttribute("myPublications");
                            if (myPublications != null && !myPublications.isEmpty()) {
                        %>
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Title</th>
                                        <th>Type</th>
                                        <th>Date</th>
                                        <th>Publisher</th>
                                        <th>DOI</th>
                                        <th>Abstract</th>
                                        <th>Document</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% for (Publication pub : myPublications) { %>
                                        <tr>
                                            <td><%= pub.getTitle() %></td>
                                            <td><%= pub.getType() %></td>
                                            <td><%= pub.getDate() %></td>
                                            <td><%= pub.getPublisher() != null ? pub.getPublisher() : "" %></td>
                                            <td><%= pub.getDoi() != null ? pub.getDoi() : "" %></td>
                                            <td class="abstract-text"><%= pub.getAbstract() != null ? pub.getAbstract() : "" %></td>
                                            <td><%= pub.getDocumentPath() != null ? "<a href='" + pub.getDocumentPath() + "' class='btn btn-sm btn-outline-primary'>Download</a>" : "" %></td>
                                        </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        <% } else { %>
                            <p class="text-muted">You have no publications yet.</p>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>