// Global App Logic (Header, Footer, Cart badge, user sessions)
document.addEventListener("DOMContentLoaded", () => {
    renderHeaderAndFooter();
    setupMobileMenu();
    updateCartCount();
    setupUserMenu();
});

// Render Global Header and Footer
function renderHeaderAndFooter() {
    const headerEl = document.getElementById("global-header");
    const footerEl = document.getElementById("global-footer");

    // Get current path to highlight active page
    const path = window.location.pathname;
    const pageName = path.substring(path.lastIndexOf("/") + 1);

    const currentUser = JSON.parse(localStorage.getItem("currentUser"));

    if (headerEl) {
        headerEl.innerHTML = `
            <div class="navbar">
                <a href="index.html" class="logo">
                    Fashion<span>Store</span>
                </a>

                <nav class="nav-links" id="nav-links">
                    <a href="index.html" class="${pageName === "index.html" || pageName === "" ? "active" : ""}">Home</a>
                    <a href="products.html" class="${pageName === "products.html" ? "active" : ""}">Products</a>
                    <a href="cart.html" class="${pageName === "cart.html" ? "active" : ""}">Cart</a>
                    ${currentUser ? `<a href="orders.html" class="${pageName === "orders.html" ? "active" : ""}">My Orders</a>` : ""}
                </nav>

                <div class="nav-actions">
                    <a href="cart.html" class="cart-icon-btn" aria-label="Shopping Cart">
                        <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12"/></svg>
                        <span class="cart-badge" id="global-cart-badge">0</span>
                    </a>

                    <div id="auth-action-area">
                        ${currentUser ? `
                            <div class="user-menu" id="user-menu-btn">
                                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                                <span>${currentUser.fullName.split(" ")[0]}</span>
                                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="m6 9 6 6 6-6"/></svg>
                                <div class="user-dropdown" id="user-dropdown-menu">
                                    <a href="orders.html">My Orders</a>
                                    <button id="btn-logout-action">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
                                        Logout
                                    </button>
                                </div>
                            </div>
                        ` : `
                            <a href="login.html" class="btn-login">Login</a>
                        `}
                    </div>

                    <button class="menu-btn" id="menu-btn" aria-label="Toggle Menu">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="4" x2="20" y1="12" y2="12"/><line x1="4" x2="20" y1="6" y2="6"/><line x1="4" x2="20" y1="18" y2="18"/></svg>
                    </button>
                </div>
            </div>
        `;
    }

    if (footerEl) {
        footerEl.innerHTML = `
            <div class="footer-content">
                <div class="footer-section">
                    <h3 style="color:var(--primary); font-weight:800; font-size:20px;">Fashion<span style="color:var(--white);">Store</span></h3>
                    <p style="margin-top:10px;">Your ultimate destination for curated, premium fashion trends. We promise style, quality, and expression in every stitch.</p>
                </div>
                <div class="footer-section">
                    <h3>Quick Links</h3>
                    <ul>
                        <li><a href="index.html">Home</a></li>
                        <li><a href="products.html">All Products</a></li>
                        <li><a href="cart.html">View Cart</a></li>
                    </ul>
                </div>
                <div class="footer-section">
                    <h3>Customer Support</h3>
                    <ul>
                        <li><a href="#">Contact Us</a></li>
                        <li><a href="#">Shipping Policy</a></li>
                        <li><a href="#">Returns & Exchanges</a></li>
                        <li><a href="#">FAQ</a></li>
                    </ul>
                </div>
            </div>
            <div class="footer-bottom">
                <p>&copy; 2026 FashionStore. All Rights Reserved.</p>
                <div class="footer-bottom-links">
                    <a href="#">Privacy Policy</a>
                    <a href="#">Terms of Service</a>
                </div>
            </div>
        `;
    }
}

// Mobile navigation menu toggle
function setupMobileMenu() {
    const menuBtn = document.getElementById("menu-btn");
    const navLinks = document.getElementById("nav-links");

    if (menuBtn && navLinks) {
        menuBtn.addEventListener("click", () => {
            navLinks.classList.toggle("active");
        });
    }
}

// User session dropdown menu
function setupUserMenu() {
    const userMenuBtn = document.getElementById("user-menu-btn");
    const userDropdown = document.getElementById("user-dropdown-menu");

    if (userMenuBtn && userDropdown) {
        userMenuBtn.addEventListener("click", (e) => {
            e.stopPropagation();
            const isCurrentlyOpen = userDropdown.style.display === "flex";
            userDropdown.style.display = isCurrentlyOpen ? "none" : "flex";
        });

        // Close dropdown when clicking outside
        document.addEventListener("click", () => {
            userDropdown.style.display = "none";
        });
    }

    const logoutBtn = document.getElementById("btn-logout-action");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", (e) => {
            e.preventDefault();
            localStorage.removeItem("currentUser");
            window.location.reload();
        });
    }
}

// Update the global cart badge number
function updateCartCount() {
    const currentUser = JSON.parse(localStorage.getItem("currentUser"));
    const badge = document.getElementById("global-cart-badge");
    
    if (!badge) return;

    if (!currentUser) {
        badge.textContent = "0";
        badge.style.display = "none";
        return;
    }

    const cartKey = `cart_${currentUser.userId}`;
    const cartItems = JSON.parse(localStorage.getItem(cartKey)) || [];
    
    // Count total quantities of items in cart
    const count = cartItems.reduce((acc, item) => acc + item.quantity, 0);
    
    badge.textContent = count;
    badge.style.display = count > 0 ? "flex" : "none";
}

// Redirect helpers
function checkAuth() {
    const currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if (!currentUser) {
        window.location.href = "login.html";
        return false;
    }
    return currentUser;
}
