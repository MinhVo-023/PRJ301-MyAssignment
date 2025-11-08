<%@ page import="model.Request" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>My Requests</title></head>
<body>
<h2>My request</h2>
<a href="MainController?action=createRequest">Create new</a> |
<a href="MainController?action=logout">Logout</a>
<table border="1">
    <tr><th>Title</th><th>From</th><th>To</th><th>Status</th><th>Processed By</th><th>Action</th></tr>
    <%
        java.util.List<Request> list = (java.util.List<Request>) request.getAttribute("requests");
        if (list != null) {
            for (Request r : list) {
    %>
    <tr>
        <td><%= r.getTitle() %></td>
        <td><%= r.getFromDate() %></td>
        <td><%= r.getToDate() %></td>
        <td><%= r.getStatus() %></td>
        <td><%= r.getProcessedBy() %></td>
        <td>
            <% if ("InProgress".equalsIgnoreCase(r.getStatus())) { %>
                <a href="MainController?action=viewRequest&id=<%= r.getId() %>">View Request</a>
            <% } %>
        </td>
    </tr>
    <%      }
        }
    %>
</table>
</body>
</html>
