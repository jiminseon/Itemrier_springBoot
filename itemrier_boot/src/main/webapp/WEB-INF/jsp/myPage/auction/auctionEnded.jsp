<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>경매 판매 완료</title>
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
	
	.container {
		margin-top:50px;
	}
	button {
		
		background-color: #e6e6fa;
		width : 100px;
		height: 30px;
		text-align: center;
		border-radius: 20px;
	}
	
</style>
<body>
		<jsp:include page="../myPage.jsp" />
	<div class="myPageUsedGoods">
		<div class="itemList">
			<div class="title">
				<br>경매 판매 완료<br>
			</div>
			<div class=container>
				<div class="row" align="center">
					 <c:forEach var="entry" items="${auctionList.entrySet()}">
				        <c:set var="auction" value="${entry.key}" />
				        <c:set var="details" value="${entry.value}" />
						<div class="col-md-4">
							<img src="https://placehold.co/180" alt="상품 이미지"/>
							<h3>상품 이름: ${auction.itemName}</h3>
							<p>가격: ${auction.price}원</p>
							<p>거래자: ${details}원</p>
							<p>날짜:${auction.startTime}
							</p>
							<button type="button">구매자 관리</button>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>