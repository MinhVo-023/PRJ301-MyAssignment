<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User, model.Request, java.util.*" %>
<html>
<head>
    <title>Agenda</title>
</head>
<body>
<h2>Tình hình nghỉ phép phòng ban</h2>

<form action="MainController" method="get">
    <input type="hidden" name="action" value="agenda"/>
    Từ ngày: <input type="date" name="fromDate" value="${fromDate}"/>
    Đến ngày: <input type="date" name="toDate" value="${toDate}"/>
    <button type="submit">Xem</button>
</form>

<table border="1" cellpadding="6">
    <tr>
        <th>Nhân viên</th>
        <th>Tình trạng</th>
    </tr>

    <%
        List<User> members = (List<User>) request.getAttribute("members");
        Map<Integer, List<Request>> leaveMap = (Map<Integer, List<Request>>) request.getAttribute("leaveMap");
        if (members != null) {
            for (User u : members) {
                List<Request> leaves = leaveMap != null ? leaveMap.get(u.getId()) : null;
    %>
    <tr>
        <td><%= u.getFullname() %></td>
        <td>
            <%
                if (leaves == null || leaves.isEmpty()) {
                    out.print("Đi làm bình thường");
                } else {
                    for (Request r : leaves) {
                        out.print("Nghỉ từ " + r.getFromDate() + " đến " + r.getToDate() + " (" + r.getReason() + ")<br/>");
                    }
                }
            %>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
