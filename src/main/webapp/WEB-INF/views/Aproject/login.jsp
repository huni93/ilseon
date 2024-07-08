<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body {
            background-color: #e0f0ff;
            font-family: Arial, sans-serif;
        }
        .login-container {
            width: 300px;
            margin: 100px auto;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #ffffff;
            text-align: center;
        }
        .login-container h2 {
            margin-bottom: 20px;
        }
        .login-container label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .login-container input[type="text"],
        .login-container input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #cccccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .login-container input[type="submit"] {
            padding: 10px 20px;
            background-color: #4CAF50;
            border: none;
            border-radius: 5px;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }
        .login-container input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<%-- 메시지 출력 --%>
    <c:if test="${not empty msg}">
        <script>
            let msg = "${msg}";
            if (msg !== "") {
                alert(msg);
            }
        </script>
    </c:if>
    <div class="login-container">
        <h2>Login</h2>
        <form action="login" method="post">
            <label for="memberId">아이디 :</label>
            <input type="text" id="memberId" name="memberId" placeholder="아이디를 입력하세요">
            <label for="memberPw">비밀번호 :</label>
            <input type="password" id="memberPw" name="memberPw" placeholder="비밀번호를 입력하세요">
            <input type="submit" value="로그인">
        </form>
        <c:if test="${not empty error}">
            <p style="color:red;">${error}</p>
        </c:if>
    </div>
    
    
</body>
</html>
