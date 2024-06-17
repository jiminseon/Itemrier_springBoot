<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<!-- jQuery -->
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<!-- iamport.payment.js -->
<script type="text/javascript"
	src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script>
	var IMP = window.IMP;
	IMP.init("imp21655237");

	function requestPay() {
		IMP.request_pay({
			pg : "html5_inicis",
			pay_method : "card",
			merchant_uid : "2406145137", // 주문번호
			name : $('#buyerName').val(),
			amount : 100, // 숫자 타입
			buyer_email : $('#email').val(),
			buyer_name : $('#buyerName').val(),
			buyer_tel : $('#phoneNum').val(),
			buyer_addr : $('#addStreet').val(),
			buyer_postcode : $('#zipCode').val()
		}, function(rsp) {
			if (rsp.success) {
				//[1] 서버단에서 결제정보 조회를 위해 jQuery ajax로 imp_uid 전달하기
				alert("결제 완료");
				var result = {
						orderId : rsp.merchant_uid,
						buyerId : 451,
						buyerInfo :{
							buyerName : $('#buyerName').val(),
							phoneNum : rsp.buyer_tel	
						},
						deliveryInfo :{
							zipCode : rsp.buyer_postcode,
							addStreet : $('#addStreet').val(),
							addDetail : $('#addDetail').val(),
							deliveryLocation : $('#deliveryLocation').val(),
							deliveryRequest : $('#deliveryRequest').val()
						},
						email : rsp.buyer_email,
						price : rsp.paid_amount,
						quantity : 1,
						pg : "html5_inicis",
						pay_method : "card",
						orderDate : new Date().toISOString(),
						itemId : 51
				}
				
				$.ajax({
					url : 'insertOrder', //cross-domain error가 발생하지 않도록 주의해주세요
					type : 'POST',
					contentType : "application/json; charset=utf-8",
					dataType : "json",
					data : JSON.stringify(result),
					success: function(res){
						consol.log(res);
						location.href=res;
					},
					error: function(err){
						console.log(err);
					}
				});
			} else {
				var msg = '결제에 실패하였습니다.';
				msg += '에러내용 : ' + rsp.error_msg;
			}
		});
	}
</script>
<meta charset="UTF-8">
<title>결제</title>
</head>
<style>
.title {
	background-color: #fff0f5;
	font-size: 25px;
	font-weight: bold;
	height: 100px;
	border-radius: 20px;
	padding-left: 30px;
	text-align: center;
}

.info {
	display: flex;
	justify-content: center;
}

.write {
	height: 500px;
	margin: 50px;
	padding: 50px;
}

.orderForm {
	font-size: 20px;
	width: 700px;
	height: 700px;
}

textarea {
	height: 100px;
	width: 500px;
	border: 1px solid gray;
	resize: none;
}

.longInfo {
	height: 150px;
	width: 400px;
}

.smallText {
	width: 250px;
	height: 30px;
}

.item {
	widtd: 600px;
	margin: 50px;
	background-color: #f8f8ff;
	padding: 50px;
	border-radius: 10px;
}

.itemTable {
	border: 1px solid gray;
	margin: 10px;
	border-radius: 30px;
	height: 250px;
	width: 450px;
}

img {
	margin: 20px;
}

button {
	width: 150px;
	height: 60px;
	border-radius: 10px;
	background-color: #add8e6;
	font-size: 18px;
	margin-top: 10px;
}

td {
	width: 250px;
}
</style>
<body>
	<div class="title">
		<br>경매 주문<br>
	</div>
	<div class="info">
		<div class="write">
			<table class="orderForm">
				<tr>
					<td>주문자</td>
					<td>&nbsp;<input type="text" class="smallText" id="buyerName"></td>
				</tr>
				<tr>
					<td>우편번호</td>
					<td>&nbsp;<input type="text" class="smallText" id="zipCode"></td>
				</tr>
				<tr>
					<td rowspan="2">주소<br></td>
					<td>&nbsp;<input type="text" class="smallText"
						placeholder="건물명" id="addStreet"></td>
				</tr>
				<tr>
					<td>&nbsp;<input type="text" class="smallText"
						placeholder="상세 주소" id="addDetail"></td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td>&nbsp;<input type="text" class="smallText" id="phoneNum"></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td>&nbsp;<input type="text" class="smallText" id="email"></td>
				</tr>
				<tr>
					<td>배송 위치</td>
					<td>&nbsp;<input type="text" class="smallText" id="deliveryLocation"></td>
				</tr>
				<tr class="longInfo">
					<td>배송 요청 사항</td>
					<td><textarea id="longinput" id="deliveryRequest"></textarea></td>
				</tr>
			</table>
		</div>
		<div class="item">
			<table class="itemTable">
				<tr>
					<td rowspan="3"><img src="https://placehold.co/180" /></td>
					<td>상품 이름</td>
				</tr>
				<tr>
					<td>$가격</td>
				</tr>
				<tr>
					<td>판매자</td>
				</tr>
			</table>
		</div>
	</div>
	<button onclick="requestPay()">결제하기</button>
</body>
</html>