<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>Create Request</title></head>
<body>
<h2>Đơn xin nghỉ phép</h2>

<%-- 
    Kiểm tra xem user có trong session không. 
    Nếu có, hiển thị thông tin.
    `sessionScope.user` là đối tượng User bạn đã lưu lúc login.
--%>
<c:if test="${not empty sessionScope.user}">
    <div style="font-weight: bold; margin-bottom: 15px;">
        User: ${sessionScope.user.fullname} ,&nbsp;&nbsp;
        Role: ${sessionScope.user.roleName} ,&nbsp;&nbsp;
        Dep: ${sessionScope.user.departmentName}
    </div>
</c:if>

<form action="MainController?action=createRequest" method="post">
    Từ ngày: <input type="date" name="fromDate" required/><br/>
    Tới ngày: <input type="date" name="toDate" required/><br/>
    Lý do: <textarea name="reason" rows="5" cols="40" required></textarea><br/>
    <button type="submit">Gửi</button>
</form>
</body>
</html>
