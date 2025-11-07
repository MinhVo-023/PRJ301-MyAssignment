<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Request" %>
<html>
<head>
    <title>Duyệt đơn nghỉ phép</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">Duyệt đơn nghỉ phép</h2>

    <%
        // Lấy thông tin đơn từ request attribute (nếu có)
        Request r = (Request) request.getAttribute("request");
        if (r == null) {
            out.println("<div class='alert alert-danger'>Không tìm thấy đơn nghỉ phép.</div>");
        } else {
    %>

    <form action="MainController" method="post" class="card shadow-sm p-4">
        <input type="hidden" name="action" value="approveRequest"/>
        <input type="hidden" name="id" value="<%= r.getId() %>"/>

        <div class="mb-3">
            <label class="form-label"><strong>Tiêu đề:</strong></label>
            <input type="text" class="form-control" value="<%= r.getTitle() %>" readonly>
        </div>

        <div class="row mb-3">
            <div class="col">
                <label class="form-label"><strong>Từ ngày:</strong></label>
                <input type="text" class="form-control" value="<%= r.getFromDate() %>" readonly>
            </div>
            <div class="col">
                <label class="form-label"><strong>Đến ngày:</strong></label>
                <input type="text" class="form-control" value="<%= r.getToDate() %>" readonly>
            </div>
        </div>

        <div class="mb-3">
            <label class="form-label"><strong>Lý do:</strong></label>
            <textarea class="form-control" readonly><%= r.getReason() %></textarea>
        </div>

        <div class="mb-3">
            <label class="form-label"><strong>Trạng thái hiện tại:</strong></label>
            <input type="text" class="form-control" value="<%= r.getStatus() %>" readonly>
        </div>

        <div class="mt-4">
            <button type="submit" name="decision" value="approve" class="btn btn-success">✅ Duyệt</button>
            <button type="submit" name="decision" value="reject" class="btn btn-danger">❌ Từ chối</button>
            <a href="MainController?action=listRequest" class="btn btn-secondary">⬅ Quay lại</a>
        </div>
    </form>

    <% } %>
</div>
</body>
</html>
