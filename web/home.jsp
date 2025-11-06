<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<html>
<head>
    <title>Trang chủ - Leave Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <%
        User u = (User) session.getAttribute("user");
        if (u == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>

    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
            <h3>Xin chào, <%= u.getFullname() %>!</h3>
        </div>
        <div class="card-body">
            <p><strong>Tài khoản:</strong> <%= u.getUsername() %></p>
            <p><strong>Phòng ban:</strong> <%= u.getDepartmentId() %></p>

            <hr>

            <h5>Chức năng</h5>
            <ul>
                <li><a href="MainController?action=createRequest">Tạo đơn nghỉ phép</a></li>
                <li><a href="MainController?action=listRequest">Xem các đơn của tôi</a></li>
                <li><a href="MainController?action=agenda">Xem agenda phòng ban</a></li>
            </ul>

            <hr>
            <a class="btn btn-outline-danger" href="MainController?action=logout">Đăng xuất</a>
        </div>
    </div>
</div>

</body>
</html>
