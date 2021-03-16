<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>잔액조회</h3>
<table border="1">
<tr><td>은행</td><td>${balance.bank_name}</td></tr>
<tr><td>계좌명</td><td>${balance.product_name}</td></tr>
<tr><td>잔액</td><td align="right"><fmt:formatNumber value="${balance.balance_amt}"/>원 </td></tr>
<tr><td>사용가능금액</td><td align="right"><fmt:formatNumber value="${balance.available_amt}"/>원</td></tr>
</table>
<hr>
${balance}
</body>
</html>