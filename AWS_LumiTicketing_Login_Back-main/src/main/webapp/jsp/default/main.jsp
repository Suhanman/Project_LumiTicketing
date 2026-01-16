<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
    .welcome-section {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 10px;
        padding: 40px;
    }

    .logo-container {
        flex-shrink: 0;
        margin-right: 20px; /* 🔹 간격 줄임 */
    }

    .logo-container img {
        width: 400px !important;
        height: auto;
        display: block;
    }

    .text-container {
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .main-title {
        font-size: 42px;
        font-weight: bold;
        color: #333;
        margin-bottom: 10px;
    }

    .subtitle {
        font-size: 20px;
        color: #666;
    }
</style>

<div class="welcome-section">
    <div class="logo-container">
        <img src="${pageContext.request.contextPath}/icon.png" alt="루미티케팅 로고">
    </div>
    <div class="text-container">
        <div class="main-title">루미티케팅</div>
        <div class="subtitle">환영합니다, 티켓 대리예매 사이트 루미티케팅입니다.</div>
    </div>
</div>



