// Checkout Logic
document.addEventListener("DOMContentLoaded", () => {
    loadCheckoutDetails();
    setupCheckoutForm();
});

// Load summary items on checkout
function loadCheckoutDetails() {
    const listContainer = document.getElementById("checkout-items-list");
    const summaryContainer = document.getElementById("checkout-summary-rows");
    const addressInput = document.getElementById("deliveryAddress");

    if (!listContainer || !summaryContainer) return;

    // Check auth
    const currentUser = checkAuth();
    if (!currentUser) return; // app.js checkAuth will redirect if not logged in

    // Set default user address from registration
    const users = JSON.parse(localStorage.getItem("users")) || [];
    const fullUser = users.find(u => u.userId === currentUser.userId);
    if (fullUser && addressInput) {
        addressInput.value = fullUser.address || "";
    }

    const cartKey = `cart_${currentUser.userId}`;
    const cartItems = JSON.parse(localStorage.getItem(cartKey)) || [];

    if (cartItems.length === 0) {
        alert("Your cart is empty. Redirecting to catalog.");
        window.location.href = "products.html";
        return;
    }

    let grandTotal = 0;

    // Render list
    listContainer.innerHTML = cartItems.map(item => {
        const product = getProductById(item.productId);
        if (!product) return "";

        const itemSubtotal = item.quantity * item.unitPrice;
        grandTotal += itemSubtotal;

        return `
            <div class="checkout-item-row">
                <div class="checkout-item-image">
                    <img src="${product.imageUrl}" alt="${product.productName}">
                </div>
                <div class="checkout-item-info">
                    <h4>${product.productName}</h4>
                    <p>Size: ${item.sizeLabel} | Qty: ${item.quantity}</p>
                </div>
                <div class="checkout-item-price">
                    ₹${itemSubtotal.toLocaleString("en-IN")}
                </div>
            </div>
        `;
    }).join("");

    // Calculate totals
    const shipping = grandTotal > 2000 ? 0 : 150;
    const tax = Math.round(grandTotal * 0.05); // 5% GST
    const finalTotal = grandTotal + shipping + tax;

    // Save final total in dataset for order submission
    document.getElementById("checkout-form-submit").dataset.total = finalTotal;

    // Render Summary numbers
    summaryContainer.innerHTML = `
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
        
        <div class="summary-row total" style="margin-bottom:0; padding-bottom:0; border-bottom:none;">
            <span>Total</span>
            <span>₹${finalTotal.toLocaleString("en-IN")}</span>
        </div>
    `;
}

// Setup order placement handler
function setupCheckoutForm() {
    const form = document.getElementById("checkout-form-submit");
    if (!form) return;

    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const currentUser = JSON.parse(localStorage.getItem("currentUser"));
        if (!currentUser) return;

        const address = document.getElementById("deliveryAddress").value.trim();
        const paymentMethod = document.getElementById("paymentMethod").value;
        const totalAmount = parseFloat(form.dataset.total);

        const cartKey = `cart_${currentUser.userId}`;
        const cartItems = JSON.parse(localStorage.getItem(cartKey)) || [];

        if (cartItems.length === 0) {
            alert("Your cart is empty!");
            window.location.href = "products.html";
            return;
        }

        const orders = JSON.parse(localStorage.getItem("orders")) || [];
        const newOrderId = orders.length > 0 ? orders[orders.length - 1].orderId + 1 : 10001;

        // Build detailed items list for order history
        const orderItemsList = cartItems.map(item => {
            const product = getProductById(item.productId);
            return {
                productId: item.productId,
                productName: product ? product.productName : "Product",
                quantity: item.quantity,
                unitPrice: item.unitPrice,
                subtotal: item.quantity * item.unitPrice,
                sizeLabel: item.sizeLabel
            };
        });

        const newOrder = {
            orderId: newOrderId,
            userId: currentUser.userId,
            totalAmount,
            paymentMethod,
            orderStatus: "PLACED",
            deliveryAddress: address,
            orderDate: new Date().toLocaleDateString("en-IN", {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
            }),
            items: orderItemsList
        };

        // Save order
        orders.push(newOrder);
        localStorage.setItem("orders", JSON.stringify(orders));

        // Deduct inventory stock (optional simulation)
        const products = getProducts();
        cartItems.forEach(cartItem => {
            const prod = products.find(p => p.productId === cartItem.productId);
            if (prod) {
                const sizeObj = prod.sizes.find(s => s.sizeLabel === cartItem.sizeLabel);
                if (sizeObj) {
                    sizeObj.stockQuantity = Math.max(0, sizeObj.stockQuantity - cartItem.quantity);
                }
            }
        });
        localStorage.setItem("products", JSON.stringify(products));

        // Clear cart
        localStorage.setItem(cartKey, JSON.stringify([]));
        updateCartCount();

        // Redirect to success page
        window.location.href = "order-success.html";
    });
}
