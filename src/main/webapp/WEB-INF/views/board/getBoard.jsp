<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>getBoard</title>

</head>
<body>
	<table border="1">
		<tr>
			<th>제목</th>
			<td>${board.title }
		</tr>
		<tr>
			<th>작성자</th>
			<td>${board.writer }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${board.content}</td>
		</tr>
		<tr>
			<th>첨부파일 단건</th>
			<td><a href="fileDown?seq=${board.seq }">${board.filename }</a></td>
		</tr>
		<tr>
			<th>첨부파일 다건</th>
			<c:forTokens items="${board.filename }" delims="," var="file">
			<td><a href="fileNameDown?filename=${file }">${file }</a></td>
			</c:forTokens>
		</tr>
</body>
</html>