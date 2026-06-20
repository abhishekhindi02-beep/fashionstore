<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/style.css">
</head>
<body>

<%@ include file="partials/navbar.jsp" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/auth.css">

<div class="auth-container">
    <div class="auth-card">

        <h2>Register</h2>

        <form action="<%=request.getContextPath()%>/register" method="post">

            <div class="form-group">
                <label>Full Name</label>
                <input type="text" name="fullName" required>
            </div>

            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required>
            </div>

            <div class="form-group">
                <label>Phone</label>
                <input type="text" name="phone" required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required>
            </div>

            <div class="form-group">
                <label>Gender</label>
                <select name="gender">
                    <option>Male</option>
                    <option>Female</option>
                </select>
            </div>

            <div class="form-group">
                <label>Address</label>
                <textarea name="address"></textarea>
            </div>

            <button class="auth-btn">Register</button>

        </form>

    </div>
</div>
<%@ include file="partials/footer.jsp" %>

</body>
</html>