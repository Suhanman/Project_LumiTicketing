<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>세션 테스트</title>
</head>
<body>
    <h1>세션 값 확인</h1>
    <p>아이디: <%= session.getAttribute("id") %></p>
    <p>이름: <%= session.getAttribute("userName") %></p>
    <p>전화번호: <%= session.getAttribute("mobile") %></p>
    <p>등급: <%= session.getAttribute("membership") %></p>
</body>
</html>
