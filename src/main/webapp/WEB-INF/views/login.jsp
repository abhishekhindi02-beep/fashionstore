<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
</head>
<body>

<%@ include file="partials/navbar.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/auth.css">

<div class="auth-container">
    <div class="auth-card">

        <h2>Login</h2>

        <form action="<%=request.getContextPath()%>/login" method="post">

            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required>
            </div>

            <button class="auth-btn">Login</button>

            <div class="auth-link">
                <p>Don't have an account?
                    <a href="<%=request.getContextPath()%>/register">Register</a>
                </p>
            </div>

        </form>

    </div>
</div>

<%@ include file="partials/footer.jsp" %>

</body>
</html>