package com.company.temp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.common.BankAPI;
import com.company.temp.service.BankVO;

@Controller
public class BankController {

	@Autowired
	BankAPI bankAPI;
	
	@RequestMapping("/getAuthorizeAccount")
	public String auth() throws Exception {

		return "redirect:" + bankAPI.getAuthorizeAccountUrl();
	}
	
	@RequestMapping("/callback")
	public String callback(@RequestParam Map<String, Object> map
			              , HttpSession session) {
		
		System.out.println(map.get("code"));
		String code = (String)map.get("code");
		
		//access_token 받기
		Map<String, Object> tokenMap = bankAPI.getAccessToken(code);
		System.out.println("access_token=" + tokenMap.get("access_token"));
		
		//session
		session.setAttribute("access_token", tokenMap.get("access_token"));
		return "home";
	}
	
	String access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwMDM0NDk2Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjMyMDUyMjQsImp0aSI6IjdjNWM4MzUzLTU2YTMtNDE5ZS1hNWU4LTQwNzU0OWNlOGM3OSJ9.qIvMiSZ-emMb_hOS2sWAiupP6ovjb2Hq8g9WaDdaFaY";

	@RequestMapping("/getAccountList")
	public String getAccountList(HttpServletRequest request, Model model) {
		String use_num = "1100034496";
		Map<String, Object> map = bankAPI.getAccoutList(access_token, use_num);
		System.out.println("getAccountList=" + map);
		model.addAttribute("list", map);
		return "bank/getAccountList";
	}
	
	@RequestMapping("/getBalance")
	public String getBalance(BankVO vo, Model model) {
		vo.setAccessToken(access_token);
		Map<String, Object> map = bankAPI.getBalance(vo);
		System.out.println("잔액=" + map);
		model.addAttribute("balance", map);
		return "bank/getBalance";
	}
	
	@ResponseBody
	@RequestMapping("/ajaxGetBalance")
	public Map ajaxGetBalance(BankVO vo) {
		vo.setAccessToken(access_token);
		Map<String, Object> map = bankAPI.getBalance(vo);
		return map;		
	}
	
	@RequestMapping("/getOrgAuthorize")
	public String getOrgAuthorize() {
		Map<String, Object> map = bankAPI.getOrgAccessTokenRestTemplate(); //.getOrgAccessToken();
		System.out.println("access_token:" + map.get("access_token"));
		return "home";
	}

}
