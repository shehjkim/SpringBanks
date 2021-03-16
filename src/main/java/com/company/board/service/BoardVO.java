package com.company.board.service;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardVO {

	private String seq;
	private String title;
	private String writer;
	private String content;
	private String filename;
//	private MultipartFile uploadFile;	//단건 업로드
	private MultipartFile[] uploadFile;	
	
	
//	예) uploadFile=MultipartFile[field="uploadFile"	
//					, filename=mybatis-context.xml --파일이름
//					, contentType=text/xml			--파일형식
//					, size=1194]					--파일크기
}
