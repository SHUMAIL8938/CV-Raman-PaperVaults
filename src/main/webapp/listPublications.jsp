<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, portal.Publication" %>
<html>
<head>
    <title>Publications - CV Raman PaperVaults</title>
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
               <a class="nav-link" href="dashboard">Dashboard</a>
                <a class="nav-link" href="addPublication.jsp">Add Publication</a>
                <a class="nav-link" href="logout">Logout</a>
            </div>
        </div>
    </nav>
    <div class="container">
        <h1>All Publications</h1>
        <form action="listPublications" method="get" class="row g-3 mt-3">
            <div class="col-md-4">
                <input type="text" class="form-control" name="search" placeholder="Search Title">
            </div>
            <div class="col-md-3">
                <select class="form-select" name="type">
                    <option value="">All Types</option>
                    <option value="JOURNAL">Journal</option>
                    <option value="CONFERENCE">Conference</option>
                    <option value="BOOK_CHAPTER">Book Chapter</option>
                    <option value="PROJECT">Project</option>
                </select>
            </div>
            <div class="col-md-3">
                <input type="date" class="form-control" name="date">
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-custom w-100">Filter</button>
            </div>
        </form>
        <div class="card shadow mt-4">
            <div class="card-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Professor</th>
                            <th>Title</th>
                            <th>Type</th>
                            <th>Date</th>
                            <th>Publisher</th>
                            <th>DOI</th>
                            <th>Abstract</th>
                            <th>Document</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Publication> publications = (List<Publication>) request.getAttribute("publications");
                            Integer currentProfId = (Integer) request.getAttribute("currentProfId");
                            if (publications != null) {
                                for (Publication pub : publications) {
                        %>
                            <tr>
                                <td><%= pub.getProfessorName() %></td>
                                <td><%= pub.getTitle() %></td>
                                <td><%= pub.getType() %></td>
                                <td><%= pub.getDate() %></td>
                                <td><%= pub.getPublisher() != null ? pub.getPublisher() : "" %></td>
                                <td><%= pub.getDoi() != null ? pub.getDoi() : "" %></td>
                                <td class="abstract-text"><%= pub.getAbstract() != null ? pub.getAbstract() : "" %></td>
                                <td><%= pub.getDocumentPath() != null ? "<a href='" + pub.getDocumentPath() + "' class='btn btn-sm btn-outline-primary'>Download</a>" : "" %></td>
                                <td>
                                    <%
                                        if (pub.getProfId() == currentProfId) {
                                    %>
                                        <a href="editPublication?id=<%= pub.getId() %>" class="btn btn-sm btn-warning">Edit</a>
                                        <a href="deletePublication?id=<%= pub.getId() %>" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure?')">Delete</a>
                                    <% } else { %>
                                        <span class="text-muted">View Only</span>
                                    <% } %>
                                </td>
                            </tr>
                        <%      }
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
        <% if (request.getParameter("success") != null) { %>
            <div class="alert alert-success mt-3"><%= request.getParameter("success") %></div>
        <% } %>
        <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger mt-3"><%= request.getParameter("error") %></div>
        <% } %>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>