<!-- 로그인 전 홈페이지 -->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Itemrior</title>
    <link rel="stylesheet"  type="text/css" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
</head>

<body>
<%@ include file="header.jsp" %>
	<div>
    <hr>
    <main>
        <div class="product-card">
            <a href=""><img src="img/product.jpg" alt="Product 1" class="product-image"></img></a>
            <p>Product 1</p>
        </div>
        <div class="product-card">
            <a href=""><img src="img/product.jpg" alt="Product 2" class="product-image"></a>
            <p>Product 2<p>
        </div>
        <div class="product-card">
            <a href=""><img src="img/product.jpg" alt="Product 3" class="product-image"></a>
            <p>Product 3<p>
        </div>
        <div class="product-card">
            <a href=""><img src="img/product.jpg" alt="Product 4" class="product-image"></a>
            <p>Product 4<p>
        </div>
        <div class="product-card">
            <a href=""><img src="img/product.jpg" alt="Product 5" class="product-image"></a>
            <p>Product 5<p>
        </div>
        <div class="product-card">
            <a href=""><img src="img/product.jpg" alt="Product 6" class="product-image"></a>
            <p>Product 6<p>
        </div>
    </main>
    </div>
    <footer>
        <p>&copy; Itemrior 잇템리어 </p>
    </footer>
    
</body>
</html>