<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.ProductSize" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product Details</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/product-details.css">
</head>
<body>

<%@ include file="partials/navbar.jsp" %>

<div class="container">

<%
    Product product = (Product) request.getAttribute("product");
    List<ProductSize> sizeList = (List<ProductSize>) request.getAttribute("sizeList");
%>

    <div class="details-container">

        <!-- IMAGE -->
        <div class="image-section">
           <img src="<%=request.getContextPath()%>/<%=product.getImageUrl()%>" class="detail-img">
        </div>

        <!-- DETAILS -->
        <div class="info-section">
            <h2><%=product.getProductName()%></h2>

            <p class="price">₹<%=product.getPrice()%></p>

            <p class="desc"><%=product.getDescription()%></p>

            <!-- SIZES -->
            <div class="sizes">
                
             <form action="<%=request.getContextPath()%>/cart" method="post">

    <input type="hidden" name="productId" value="<%=product.getProductId()%>">

    <div class="sizes">
        <h4>Select Size:</h4>

        <%
            if (sizeList != null && !sizeList.isEmpty()) {
                for (ProductSize s : sizeList) {
        %>
            <label style="margin-right: 10px;">
                <input type="radio" name="sizeLabel" value="<%=s.getSizeLabel()%>" required>
                <%=s.getSizeLabel()%>
            </label>
        <%
                }
            }
        %>
    </div>

    <button type="submit" class="add-btn">Add to Cart</button>

</form>
        </div>

    </div>

</div>

<%@ include file="partials/footer.jsp" %>

</body>
</html>