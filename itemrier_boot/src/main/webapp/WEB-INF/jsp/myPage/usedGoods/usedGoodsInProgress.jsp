<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>판매, 예약 중인 중고 물품</title>
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<style>
	.myPageUsedGoods{
		display: flex;
		height: 1000px;
	}
	
	.itemList{
		float: rigth;
		margin-top: 10px;
		margin-left: 30px;
		flex-grow:1;
	}
	
	.title{
		background-color: #fff0f5;
		font-size: 25px;
		font-weight: bold;
		height: 10%;
		border-radius: 20px;
		padding-left: 30px; 
	}

	
	.status{
		background-color: #e6e6fa;
		width : 80px;
		height: 30px;
		text-align: center;
		border-radius: 20px;
	}
	
	img{
		margin: 20px;
	}
</style>
<body>
<jsp:include page="../myPage.jsp" />
	<div class="myPageUsedGoods">
		<div class="itemList">
			<div class="title">
				<br>판매, 예약 중인 중고 물품<br>
			</div>
			<div class=container>
				<div class="row" align="center">
					<c:forEach var="userGoods" items="${userGoodsList}">
					<div class="col-md-4">
						<img src="https://placehold.co/180" alt="상품 이미지"/>
						<h3>상품 이름: ${userGoods.itemName}</h3>
						<p>가격: ${userGoods.price}원</p>
						<p>날짜:${userGoods.registerDate}</p>
						<div class="status">${userGoods.status}</div>
					</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>