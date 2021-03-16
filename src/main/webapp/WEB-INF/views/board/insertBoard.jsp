<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>자료실</h3>
	<form action="insertBoard" method="post" encType="multipart/form-data">
		작성자:   <input type="text" name="writer"><br /> 
		제목:    <input type="text" name="title"><br /> 
		내용:    <textarea name="content"></textarea><br /> 
		<!-- 첨부파일: <input type="file" name="uploadFile" /><br />  단건업로드-->
		첨부파일: <input type="file" name="uploadFile" multiple="multiple"/><br />
		
		<!-- file 타입이 있으면 메서드는 post, encType은 multipart가 들어가야함!!! -->
		<input type="submit" value="저장">
	</form>
</body>
</html>