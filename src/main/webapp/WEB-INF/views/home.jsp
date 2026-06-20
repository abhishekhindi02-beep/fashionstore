<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Fashion Store</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/home.css">
</head>
<body>

<!-- NAVBAR -->
<%@ include file="partials/navbar.jsp" %>

<!-- HERO -->
<div class="hero">
    <h1>Discover Your Style</h1>
    <p>Trendy fashion for every occasion</p>
</div>

<!-- PRODUCTS -->
<div class="container">
    <h2>Featured Products</h2>

    <div class="product-grid">
        <%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>

<%
    List<Product> list = (List<Product>) request.getAttribute("productList");

    if (list != null && !list.isEmpty()) {
        for (Product p : list) {
%>

    <div class="card">
        <img src="<%=request.getContextPath()%>/<%=p.getImageUrl()%>">
        <h3><%=p.getProductName()%></h3>
        <p>₹<%=p.getPrice()%></p>
        <a href="#" class="btn">View</a>
    </div>

<%
        }
    } else {
%>
    <p>No products available</p>
<%
    }
%>
        
    </div>
</div>

<!-- FOOTER -->
<%@ include file="partials/footer.jsp" %>

</body>
</html>