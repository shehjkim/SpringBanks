<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html><html><head><title>getAccountList.jsp</title></head>
<body>
<table border="1">
<c:forEach items="${list.res_list}" var="bank">
	<tr><td>${bank.bank_name}</td>
	<td>${bank.account_alias}</td>
	<td>${bank.account_num_masked}</td>
	<td><a href="#" onclick="getBalance('${bank.fintech_use_num}')">
	${bank.fintech_use_num}</a></td>
	<td><a href="#" onclick="getBalance2('${bank.fintech_use_num}')">
	${bank.fintech_use_num}</a></td>
	</tr>
</c:forEach>
</table>
<div id="result">
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
function getBalance(fin){
	$.ajax({
		url : "getBalance",
		data : {fintechUseNum : fin},  //fintechUseNum="+fin
		success : function(response) {	$("#result").html(response);	}
	});
}
function getBalance2(fin){
	$.ajax({
		url : "ajaxGetBalance",
		data : {fintechUseNum : fin},  //fintechUseNum="+fin
		dataType : "json",
		success : function(response) {
			$("#result").empty();
			$("#result").append("bankname:" + response.bank_name +"<br>")
			.append("product_name:" + response.product_name +"<br>")
			.append("balance_amt:" + response.balance_amt +"<br>")
			.append("available_amt:" + response.available_amt);
		}
	});
}
</script>
</body>
</html>