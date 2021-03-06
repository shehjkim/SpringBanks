package com.company.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.company.temp.service.BankVO;
import com.google.gson.Gson;


@Component
public class BankAPI {

	String host = "https://testapi.openbanking.or.kr";
	String client_id = "a24b461b27b243abb7444d0084be4664";
	String client_secret="ae9521614c8d40df83c116c7a5755899";
	String redirect_uri = "http://localhost/bank/callback";
	String use_org_code = "T990034430";
	String org_access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJUOTkwMDM0NDMwIiwic2NvcGUiOlsib29iIl0sImlzcyI6Imh0dHBzOi8vd3d3Lm9wZW5iYW5raW5nLm9yLmtyIiwiZXhwIjoxNjIzMzA2NTY5LCJqdGkiOiIwZjUyODk5NC01Y2MzLTQxMzItYjcwMC1hZWNhZjM1NmE5MjQifQ.x12c9TwQQqLCsoWDCTYhJKF-W1kqchNHBm6NakfER6Q";

	public Map<String, Object> getOrgAccessTokenRestTemplate() {
		String reqURL = host + "/oauth/2.0/token";   
		
        MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
        param.add("client_id", client_id);
        param.add("client_secret", client_secret);
        param.add("scope", "oob");
        param.add("grant_type", "client_credentials");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        HttpEntity<MultiValueMap<String, String>> request = 
        		new HttpEntity<MultiValueMap<String, String>>(param, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        Map map = restTemplate.postForObject( reqURL, request, Map.class);
        return map;
	}
	
	public Map<String, Object> getOrgAccessToken() {
        String reqURL = host + "/oauth/2.0/token";   
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            StringBuilder param = new StringBuilder();
            param.append("&client_id=").append(client_id)
	             .append("&client_secret=").append(client_secret)
	             .append("&redirect_uri=").append(redirect_uri)
	             .append("&grant_type=authorization_code");
            
            URL url = new URL(reqURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // HEADER + BODY(???????????????)
			con.setDoOutput(true);

			//header
			con.setRequestProperty("Content-Type"," application/x-www-form-urlencoded; charset=UTF-8");
			
			OutputStream os = con.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(param.toString()); // ??????????????? &= ??????.
			writer.flush();
			writer.close();
			os.close();

			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				System.out.println("" + sb.toString());
			} else {
				System.out.println("token error" + con.getResponseMessage());
			}
            
            // Gson ?????????????????? ????????? ???????????? JSON?????? ?????? ??????
            Gson gson = new Gson();
            map = gson.fromJson(sb.toString(), Map.class);
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return map;
    }
	
	public Map<String, Object> getAccessToken(String authorize_code) {
        String reqURL = host + "/oauth/2.0/token";   
        Map<String, Object> map = new HashMap<String,Object>();
        try {
            StringBuilder param = new StringBuilder();
            param.append("code=").append(authorize_code)
	             .append("&client_id=").append(client_id)
	             .append("&client_secret=").append(client_secret)
	             .append("&redirect_uri=").append(redirect_uri)
	             .append("&grant_type=authorization_code");
            
            URL url = new URL(reqURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // HEADER + BODY(???????????????)
			con.setDoOutput(true);

			OutputStream os = con.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(param.toString()); // ??????????????? &= ??????.
			writer.flush();
			writer.close();
			os.close();

			StringBuilder sb = new StringBuilder();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append("\n");
				}
				br.close();
				System.out.println("" + sb.toString());
			} else {
				System.out.println("token error" + con.getResponseMessage());
			}
            
            // Gson ?????????????????? ????????? ???????????? JSON?????? ?????? ??????  "{}"
            Gson gson = new Gson();
            map = gson.fromJson(sb.toString(), Map.class);
            
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        return map;
    }

	public String getDate() {
		String str = "";
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		str = format.format(date);
		return str;
	}
	
	public String getRand32() {
		//32????????? ??????
		
		return "";
	}
	
	//9????????? ??????
	public String getRand() {   
		long time = System.currentTimeMillis();
		String str = Long.toString(time);
		return str.substring(str.length()-9);
	}
	
	public HashMap<String, Object> getBalance(BankVO vo){
		// ???????????? ????????????????????? ?????? ????????? ?????? ??? ????????? HashMap???????????? ??????
	    HashMap<String, Object> map = new HashMap<>();
	    String reqURL = host + "/v2.0/account/balance/fin_num"; 
	    StringBuilder qstr = new StringBuilder();
		qstr.append("bank_tran_id=").append(use_org_code +"U" + getRand() )
			.append("&fintech_use_num=").append(vo.getFintechUseNum())
			.append("&tran_dtime=").append( getDate() );
		System.out.println(qstr);
	    try {
	        URL url = new URL(reqURL + "?" + qstr);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        
	        // ????????? ????????? Header??? ????????? ??????
	        conn.setRequestProperty("Authorization", "Bearer " + vo.getAccessToken());
	       
	        // ???????????? ?????? 200?????? ????????????
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);
	        
	        //string -> ?????? ?????? 
	        Gson gson = new Gson();
            map = gson.fromJson(result, HashMap.class);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	    
	    return map;			
	}
			
	public HashMap<String, Object> getAccoutList(String access_token, String use_num) {
	    // ???????????? ????????????????????? ?????? ????????? ?????? ??? ????????? HashMap???????????? ??????
	    HashMap<String, Object> map = new HashMap<>();
	    String reqURL = host + "/v2.0/account/list"; 
	    StringBuilder qstr = new StringBuilder();
		qstr.append("user_seq_no=").append(use_num)
			.append("&include_cancel_yn=").append("Y")
			.append("&sort_order=").append("D");		
	    try {
	        URL url = new URL(reqURL + "?" + qstr);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        
	        // ????????? ????????? Header??? ????????? ??????
	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
	       
	        // ???????????? ?????? 200?????? ????????????
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);
	        
	        //string -> ?????? ?????? 
	        Gson gson = new Gson();
            map = gson.fromJson(result, HashMap.class);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	    
	    return map;		
	}
	
	public String rand32() {	
		return "12345678901234567890123456789012";
	}
	
	public String getAuthorizeAccountUrl() {
		String reqURL = host + "/oauth/2.0/authorize_account";

		// POST ????????? ????????? ???????????? ???????????? ???????????? ?????? ??????
		StringBuilder param = new StringBuilder();
		param.append("response_type=code")
			.append("&client_id=").append(client_id)
			.append("&redirect_uri=").append(redirect_uri)
			.append("&scope=login inquiry transfer")
			.append("&state=").append(rand32())
			.append("&auth_type=").append("0");

		return reqURL + "?"+ param;
	}

}
