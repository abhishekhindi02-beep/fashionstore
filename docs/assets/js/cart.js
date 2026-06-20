// Cart Page Operations
document.addEventListener("DOMContentLoaded", () => {
    renderCart();
});

// Render all items in cart
function renderCart() {
    const listContainer = document.getElementById("cart-items-list-container");
    const summaryContainer = document.getElementById("cart-summary-container");
    
    if (!listContainer || !summaryContainer) return;

    // Check auth
    const currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if (!currentUser) {
        listContainer.innerHTML = `
            <div class="cart-empty-state">
                <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/></svg>
                <h2>Sign In Required</h2>
                <p>Please login to your account to view your shopping cart.</p>
                <a href="login.html" class="btn">Sign In Now</a>
            </div>
        `;
        summaryContainer.style.display = "none";
        return;
    }

    const cartKey = `cart_${currentUser.userId}`;
    const cartItems = JSON.parse(localStorage.getItem(cartKey)) || [];

    if (cartItems.length === 0) {
        listContainer.innerHTML = `
            <div class="cart-empty-state">
                <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"><circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/><path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/></svg>
                <h2>Your Cart is Empty</h2>
                <p>Add some stylish items to your shopping cart to get started.</p>
                <a href="products.html" class="btn">Start Shopping</a>
            </div>
        `;
        summaryContainer.style.display = "none";
        return;
    }

    // Show summary card
    summaryContainer.style.display = "block";

    let grandTotal = 0;
    
    // Render list
    listContainer.innerHTML = cartItems.map(item => {
        const product = getProductById(item.productId);
        if (!product) return "";

        const itemSubtotal = item.quantity * item.unitPrice;
        grandTotal += itemSubtotal;

        return `
            <div class="cart-item-card fade-in">
                <div class="cart-item-image">
                    <img src="${product.imageUrl}" alt="${product.productName}">
                </div>
                <div class="cart-item-details">
                    <h3 class="cart-item-name">${product.productName}</h3>
                    <span class="cart-item-meta">Size: ${item.sizeLabel}</span>
                    
                    <div class="cart-item-actions">
                        <!-- Quantity Controller -->
                        <div class="cart-qty-selector">
                            <button class="cart-qty-btn" onclick="updateQty(${item.cartItemId}, -1)">-</button>
                            <span class="cart-qty-value">${item.quantity}</span>
                            <button class="cart-qty-btn" onclick="updateQty(${item.cartItemId}, 1)">+</button>
                        </div>

                        <!-- Remove Button -->
                        <button class="cart-remove-btn" onclick="removeCartItem(${item.cartItemId})">
                            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/></svg>
                            Remove
                        </button>
                    </div>
                </div>
                
                <div class="cart-item-price-column">
                    <span class="cart-item-unit-price">₹${item.unitPrice.toLocaleString("en-IN")}</span>
                    <strong class="cart-item-total-price">₹${itemSubtotal.toLocaleString("en-IN")}</strong>
                </div>
            </div>
        `;
    }).join("");

    // Calculate details
    const shipping = grandTotal > 2000 ? 0 : 150;
    const tax = Math.round(grandTotal * 0.05); // 5% GST
    const finalTotal = grandTotal + shipping + tax;

    // Render Order Summary
    summaryContainer.innerHTML = `
        <div class="summary-card">
            <h3>Order Summary</h3>
            <div class="summary-row">
                <span>Subtotal</span>
                <span>₹${grandTotal.toLocaleString("en-IN")}</span>
            </div>
            <div class="summary-row">
                <span>Estimated Tax (5%)</span>
                <span>₹${tax.toLocaleString("en-IN")}</span>
            </div>
            <div class="summary-row">
                <span>Shipping</span>
                <span>${shipping === 0 ? '<span style="color:var(--success); font-weight:600;">FREE</span>' : `₹${shipping}`}</span>
            </div>
            
            <div class="summary-row total">
                <span>Total</span>
                <span>₹${finalTotal.toLocaleString("en-IN")}</span>
            </div>

            <button class="btn summary-btn" onclick="goToCheckout()">
                Proceed to Checkout
            </button>

            <a href="products.html" class="continue-shopping-btn">
                Continue Shopping
            </a>
        </div>
    `;
}

// Update quantity logic
window.updateQty = function(cartItemId, delta) {
    const currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if (!currentUser) return;

    const cartKey = `cart_${currentUser.userId}`;
    let cartItems = JSON.parse(localStorage.getItem(cartKey)) || [];

    const item = cartItems.find(i => i.cartItemId === cartItemId);
    if (!item) return;

    const newQty = item.quantity + delta;
    if (newQty < 1) return; // cannot go below 1
    if (newQty > 10) {
        alert("Maximum quantity limit is 10 items per product size.");
        return;
    }

    item.quantity = newQty;
    localStorage.setItem(cartKey, JSON.stringify(cartItems));

    renderCart();
    updateCartCount();
};

// Remove cart item logic
window.removeCartItem = function(cartItemId) {
    const currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if (!currentUser) return;

    const cartKey = `cart_${currentUser.userId}`;
    let cartItems = JSON.parse(localStorage.getItem(cartKey)) || [];

    // Filter out item
    cartItems = cartItems.filter(i => i.cartItemId !== cartItemId);
    localStorage.setItem(cartKey, JSON.stringify(cartItems));

    renderCart();
    updateCartCount();
};

// Navigate to checkout
window.goToCheckout = function() {
    window.location.href = "checkout.html";
};
