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
        잇템리어입니다.
    </main>
    </div>
    <footer>
        <p>&copy; Itemrior 잇템리어 </p>
    </footer>
    
</body>
</html>