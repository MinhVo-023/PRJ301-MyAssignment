<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Create Request</title></head>
<body>
<h2>Tạo đơn nghỉ phép</h2>
<form action="MainController?action=createRequest" method="post">
    Title: <input type="text" name="title"/><br/>
    From: <input type="date" name="fromDate"/><br/>
    To: <input type="date" name="toDate"/><br/>
    Reason: <textarea name="reason"></textarea><br/>
    <button type="submit">Gửi</button>
</form>
</body>
</html>
