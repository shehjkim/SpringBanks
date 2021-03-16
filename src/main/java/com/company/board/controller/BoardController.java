package com.company.board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.company.board.service.BoardVO;
import com.company.board.service.impl.BoardMapper;
import com.company.common.FileRenamePolicy;
import com.lowagie.text.List;

@Controller
public class BoardController {

	@Autowired BoardMapper  boardMapper;
	
	@GetMapping("/insertBoard")
	public String insertBoardForm() {
		return "board/insertBoard";
	}

//단건 업로드
//	@PostMapping("/insertBoard")
//	public String insertBoard(BoardVO vo) throws IllegalStateException, IOException {
//		System.out.println(vo);
//		//첨부파일처리
//		MultipartFile file = vo.getUploadFile();	//업로드 파일이 임시폴더에 있는 상태
//		if(file != null && ! file.isEmpty() && file.getSize() > 0 ) { //파일이 null, getEmpty 아니고 사이즈가 0보다 크면
//			String filename = file.getOriginalFilename();
//			//파일명 중복체크 -> rename
//			File rename = FileRenamePolicy.rename(new File("c:/upload",filename));	//new File()안에 있는 파일이있는지 검사
//			//
//			vo.setFilename(rename.getName()); //vo 업로드된 파일명 담아서 DB에 저장
//			//임시폴더에서 업로드 폴더로 파일 이동
//			file.transferTo(rename);
//		}
//		//등록서비스 호출
//		boardMapper.insertBoard(vo);
//			return "redirect:/getBoard?seq=" + vo.getSeq();
//	}
	
//다건 업로드
	@PostMapping("/insertBoard")
	public String insertBoard(BoardVO vo) throws IllegalStateException, IOException {
		System.out.println(vo);
		//첨부파일처리
		MultipartFile[] files = vo.getUploadFile();	
		String filenames =  "";
		boolean start = true;
		for(MultipartFile file : files) {
		if(file != null && ! file.isEmpty() && file.getSize() > 0 ) { //파일이 null, getEmpty 아니고 사이즈가 0보다 크면
			String filename = file.getOriginalFilename();	//원본파일명
			//파일명 중복체크 -> rename
			File rename = FileRenamePolicy.rename(new File("c:/upload",filename));	//new File()안에 있는 파일이있는지 검사
			
			if( ! start ) {
				filenames +=  ",";
			}else {
				start = false;
			}
			filenames += rename.getName();			
			//임시폴더에서 업로드 폴더로 파일 이동
			file.transferTo(rename);
		}
		}
		vo.setFilename(filenames); //vo 업로드된 파일명 담아서 DB에 저장
		//등록서비스 호출
		boardMapper.insertBoard(vo);
			return "redirect:/getBoard?seq=" + vo.getSeq();
	
}
//단건조회
	@GetMapping("/getBoard")
	public String getBoard(BoardVO vo,Model model) {
		model.addAttribute("board", boardMapper.getBoard(vo));
		return "board/getBoard";
	}

//단건조회 파일다운로드
	@GetMapping("/fileDown")
	public void fileDown(BoardVO vo,HttpServletResponse response) throws IOException {
		vo = boardMapper.getBoard(vo);
		File file = new File("c:/upload" ,vo.getFilename());
		if(file.exists()) {
			 response.setContentType("application/octet-stream;charset=UTF-8");
			 response.setHeader("Content-Disposition", "attachment; filename=\"" 
					 			+ URLEncoder.encode(vo.getFilename(), "utf-8") + "\"");
			 
			 BufferedInputStream in = null;
			 BufferedOutputStream out = null;
			 try {
			
			 in = new BufferedInputStream(new FileInputStream(file));
			 out = new BufferedOutputStream(response.getOutputStream());
			 FileCopyUtils.copy(in, out);
			 out.flush();
			 } catch (IOException ex) {
			 } finally {
			 in.close();
			 response.getOutputStream().flush();
			 response.getOutputStream().close();
			 }
			 
			 
		}else {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter()
					.append("<script>")
					.append("alert('file not found(파일없음)');")
					.append("history.go(-1)")
					.append("</script>");
					
		}
	}

//다건조회 파일다운로드
		@GetMapping("/fileNameDown")
		public void fileNameDown(BoardVO vo,HttpServletResponse response) throws IOException {
			File file = new File("c:/upload" ,vo.getFilename());
			if(file.exists()) {
				 response.setContentType("application/octet-stream;charset=UTF-8");
				 response.setHeader("Content-Disposition", "attachment; filename=\"" 
						 			+ URLEncoder.encode(vo.getFilename(), "utf-8") + "\"");
				 
				 BufferedInputStream in = null;
				 BufferedOutputStream out = null;
				 try {
				
				 in = new BufferedInputStream(new FileInputStream(file));
				 out = new BufferedOutputStream(response.getOutputStream());
				 FileCopyUtils.copy(in, out);
				 out.flush();
				 } catch (IOException ex) {
				 } finally {
				 in.close();
				 response.getOutputStream().flush();
				 response.getOutputStream().close();
				 }
				 
				 
			}else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter()
						.append("<script>")
						.append("alert('file not found(파일없음)');")
						.append("history.go(-1)")
						.append("</script>");
						
			}
		}
}
