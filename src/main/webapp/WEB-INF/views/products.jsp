<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>

<!DOCTYPE html>
<html>
<head>
    <title>All Products</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/products.css">
</head>
<body>

<%@ include file="partials/navbar.jsp" %>

<div class="container">

    <h2>All Products</h2>
    

    <div class="product-grid">

<%
    List<Product> list = (List<Product>) request.getAttribute("productList");

    if (list != null && !list.isEmpty()) {
        for (Product p : list) {
%>

        <div class="card">
            <img src="<%=request.getContextPath()%>/<%=p.getImageUrl()%>" class="product-img">
            <h3><%=p.getProductName()%></h3>
            <p>₹<%=p.getPrice()%></p>
            <a href="product-details?id=<%=p.getProductId()%>" class="btn">View</a>
        </div>

<%
        }
    }
%>

    </div>
</div>

<%@ include file="partials/footer.jsp" %>

</body>
</html>