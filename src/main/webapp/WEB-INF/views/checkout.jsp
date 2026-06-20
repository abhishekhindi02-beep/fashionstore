<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.fashionstore.model.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/checkout.css">
</head>
<body>

<%@ include file="partials/navbar.jsp" %>

<%
    List<CartItem> items = (List<CartItem>) request.getAttribute("cartItems");
    Map<Integer, Product> productMap = (Map<Integer, Product>) request.getAttribute("productMap");
    Double grandTotal = (Double) request.getAttribute("grandTotal");
%>

<div class="checkout-container">

    <div class="checkout-left">
        <h2>Checkout</h2>

        <%
            if (items != null && !items.isEmpty()) {
                for (CartItem item : items) {
                    Product p = productMap.get(item.getProductId());
                    double total = item.getQuantity() * item.getUnitPrice();
        %>

        <div class="checkout-card">
            <div>
                <h3><%= (p != null) ? p.getProductName() : "Product" %></h3>
                <p>Size: <%=item.getSizeLabel()%></p>
                <p>Qty: <%=item.getQuantity()%></p>
            </div>
            <div>
                <strong>₹<%=total%></strong>
            </div>
        </div>

        <%
                }
            }
        %>
    </div>

    <div class="checkout-right">
        <h3>Order Summary</h3>

        <div class="summary-row">
            <span>Total</span>
            <span>₹<%=grandTotal%></span>
        </div>

        <form action="<%=request.getContextPath()%>/place-order" method="post">
            <label>Delivery Address</label>
            <textarea name="deliveryAddress" required></textarea>

            <label>Payment Method</label>
            <select name="paymentMethod" required>
                <option value="COD">Cash on Delivery</option>
                <option value="UPI">UPI</option>
            </select>

            <button type="submit" class="checkout-btn">Place Order</button>
        </form>
    </div>

</div>

<%@ include file="partials/footer.jsp" %>

</body>
</html>