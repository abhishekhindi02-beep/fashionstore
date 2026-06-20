// Authentication Logic
document.addEventListener("DOMContentLoaded", () => {
    setupRegisterForm();
    setupLoginForm();
});

// Setup registration form handler
function setupRegisterForm() {
    const registerForm = document.getElementById("register-form");
    if (!registerForm) return;

    registerForm.addEventListener("submit", (e) => {
        e.preventDefault();

        const fullName = document.getElementById("fullName").value.trim();
        const email = document.getElementById("email").value.trim().toLowerCase();
        const phone = document.getElementById("phone").value.trim();
        const password = document.getElementById("password").value;
        const gender = document.getElementById("gender").value;
        const address = document.getElementById("address").value.trim();
        const errorEl = document.getElementById("auth-error");

        if (errorEl) errorEl.textContent = "";

        const users = JSON.parse(localStorage.getItem("users")) || [];

        // Check if email already exists
        if (users.some(u => u.email === email)) {
            if (errorEl) errorEl.textContent = "Email is already registered!";
            return;
        }

        // Create new user id
        const newUserId = users.length > 0 ? users[users.length - 1].userId + 1 : 1;
        
        const newUser = {
            userId: newUserId,
            fullName,
            email,
            phone,
            password, // In a real app this is hashed, but mock local storage uses plain text
            gender,
            address
        };

        // Add user and save
        users.push(newUser);
        localStorage.setItem("users", JSON.stringify(users));

        // Create a blank cart for user
        localStorage.setItem(`cart_${newUserId}`, JSON.stringify([]));

        // Auto log in user
        localStorage.setItem("currentUser", JSON.stringify({
            userId: newUserId,
            fullName: newUser.fullName,
            email: newUser.email
        }));

        // Redirect to homepage
        window.location.href = "index.html";
    });
}

// Setup login form handler
function setupLoginForm() {
    const loginForm = document.getElementById("login-form");
    if (!loginForm) return;

    loginForm.addEventListener("submit", (e) => {
        e.preventDefault();

        const email = document.getElementById("email").value.trim().toLowerCase();
        const password = document.getElementById("password").value;
        const errorEl = document.getElementById("auth-error");

        if (errorEl) errorEl.textContent = "";

        const users = JSON.parse(localStorage.getItem("users")) || [];

        // Find user
        const user = users.find(u => u.email === email && u.password === password);

        if (!user) {
            if (errorEl) errorEl.textContent = "Invalid email or password!";
            return;
        }

        // Login user
        localStorage.setItem("currentUser", JSON.stringify({
            userId: user.userId,
            fullName: user.fullName,
            email: user.email
        }));

        // Redirect to homepage
        window.location.href = "index.html";
    });
}
