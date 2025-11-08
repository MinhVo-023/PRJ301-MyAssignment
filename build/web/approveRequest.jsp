<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- BẮT BUỘC: Thêm thư viện JSTL (dùng "chuẩn" http, không dùng jakarta.tags) --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Duyệt đơn nghỉ phép</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="mb-4">Duyệt đơn nghỉ phép</h2>

    <%-- 
        Dùng <c:choose> để kiểm tra 
        ${requestScope.requestDetail} chính là cái attribute "requestDetail" 
        mà MainController đã setAttribute 
    --%>
    <c:choose>
        <%-- KHI TÌM THẤY ĐƠN --%>
        <c:when test="${not empty requestScope.requestDetail}">
            <form action="MainController" method="post" class="card shadow-sm p-4">
                
                <%-- SỬA Ở ĐÂY: Action phải là "processApproval" --%>
                <input type="hidden" name="action" value="processApproval"/>
                
                <%-- Dùng EL ${...} để lấy ID. Sạch sẽ hơn nhiều. --%>
                <input type="hidden" name="id" value="${requestScope.requestDetail.id}"/>

                <div class="mb-3">
                    <label class="form-label"><strong>Tiêu đề:</strong></label>
                    <input type="text" class="form-control" value="${requestScope.requestDetail.title}" readonly>
                </div>

                <div class="row mb-3">
                    <div class="col">
                        <label class="form-label"><strong>Từ ngày:</strong></label>
                        <input type="text" class="form-control" value="${requestScope.requestDetail.fromDate}" readonly>
                    </div>
                    <div class="col">
                        <label class="form-label"><strong>Đến ngày:</strong></label>
                        <input type="text" class="form-control" value="${requestScope.requestDetail.toDate}" readonly>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label"><strong>Lý do:</strong></label>
                    <textarea class="form-control" readonly>${requestScope.requestDetail.reason}</textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label"><strong>Trạng thái hiện tại:</strong></label>
                    <input type="text" class="form-control" value="${requestScope.requestDetail.status}" readonly>
                </div>

                <div class="mt-4">
                    <button type="submit" name="decision" value="approve" class="btn btn-success">✅ Duyệt</button>
                    <button type="submit" name="decision" value="reject" class="btn btn-danger">❌ Từ chối</button>
                </div>
            </form>
        </c:when>
        
        <%-- KHI KHÔNG TÌM THẤY ĐƠN (do 'requestDetail' bị null) --%>
        <c:otherwise>
            <div class='alert alert-danger'>Không tìm thấy đơn nghỉ phép.</div>
            <a href="MainController?action=listRequest" class="btn btn-secondary">⬅ Quay lại</a>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>