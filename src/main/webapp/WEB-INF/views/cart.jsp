<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.fashionstore.model.*" %>

<!DOCTYPE html>
<html>
<head>
    <title>Cart</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/cart.css">
</head>
<body>

<%@ include file="partials/navbar.jsp" %>

<div class="cart-container">

    <!-- LEFT: CART ITEMS -->
    <div class="cart-items">

        <h2>Your Cart</h2>

        <%
            List<CartItem> items = (List<CartItem>) request.getAttribute("cartItems");
            Map<Integer, Product> productMap = (Map<Integer, Product>) request.getAttribute("productMap");

            double grandTotal = 0;

            if (items != null && !items.isEmpty()) {
                for (CartItem item : items) {

                    Product p = productMap.get(item.getProductId());

                    double total = item.getQuantity() * item.getUnitPrice();
                    grandTotal += total;
        %>

        <div class="cart-card">
            <div class="cart-img">
        <img src="<%=request.getContextPath()%>/<%=p.getImageUrl()%>" width="80">
    </div>
            <!-- LEFT INFO -->
            <div class="cart-info">
                <h3><%= (p != null) ? p.getProductName() : "Product" %></h3>
                <p>Size: <%=item.getSizeLabel()%></p>

                <!-- 🔥 QUANTITY CONTROL -->
                <div class="qty-box">

                    <form action="<%=request.getContextPath()%>/cart" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="decrease">
                        <input type="hidden" name="cartItemId" value="<%=item.getCartItemId()%>">
                        <button>-</button>
                    </form>

                    <span><%=item.getQuantity()%></span>

                    <form action="<%=request.getContextPath()%>/cart" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="increase">
                        <input type="hidden" name="cartItemId" value="<%=item.getCartItemId()%>">
                        <button>+</button>
                    </form>

                </div>

                <!-- 🔥 REMOVE BUTTON -->
                <form action="<%=request.getContextPath()%>/cart" method="post">
                    <input type="hidden" name="action" value="remove">
                    <input type="hidden" name="cartItemId" value="<%=item.getCartItemId()%>">

                    <button class="remove-btn">Remove</button>
                </form>
            </div>

            <!-- RIGHT PRICE -->
            <div class="cart-price">
                <p>₹<%=item.getUnitPrice()%></p>
                <strong>₹<%=total%></strong>
            </div>

        </div>

        <%
                }
            } else {
        %>
            <p>Your cart is empty</p>
        <%
            }
        %>

    </div>

    <!-- RIGHT: SUMMARY -->
    <div class="cart-summary">

        <h3>Order Summary</h3>

        <div class="summary-row">
            <span>Total</span>
            <span>₹<%=grandTotal%></span>
        </div>

        <a href="<%=request.getContextPath()%>/checkout" class="checkout-btn" style="display:block; text-align:center; text-decoration:none;">
    Proceed to Checkout
</a>

        <a href="<%=request.getContextPath()%>/products" class="continue-btn">
            Continue Shopping
        </a>

    </div>

</div>

<%@ include file="partials/footer.jsp" %>

</body>
</html>