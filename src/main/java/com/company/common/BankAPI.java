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
	String client_id = "DPV8GH7pNOp0GCbyM2APvlzdIsWMeT2G619H7ZDc";
	String client_secret="goAag7uEhDx16xXmdgJ1OePNxste9N8z7KPPcqIi";
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
			con.setRequestMethod("POST"); // HEADER + BODY(파라미터값)
			con.setDoOutput(true);

			//header
			con.setRequestProperty("Content-Type"," application/x-www-form-urlencoded; charset=UTF-8");
			
			OutputStream os = con.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(param.toString()); // 파라미터와 &= 합체.
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
            
            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
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
			con.setRequestMethod("POST"); // HEADER + BODY(파라미터값)
			con.setDoOutput(true);

			OutputStream os = con.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
			writer.write(param.toString()); // 파라미터와 &= 합체.
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
            
            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성  "{}"
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
		//32바이트 난수
		
		return "";
	}
	
	//9자리의 난수
	public String getRand() {   
		long time = System.currentTimeMillis();
		String str = Long.toString(time);
		return str.substring(str.length()-9);
	}
	
	public HashMap<String, Object> getBalance(BankVO vo){
		// 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
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
	        
	        // 요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + vo.getAccessToken());
	       
	        // 출력되는 값이 200이면 정상작동
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);
	        
	        //string -> 맵에 담기 
	        Gson gson = new Gson();
            map = gson.fromJson(result, HashMap.class);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	    
	    return map;			
	}
			
	public HashMap<String, Object> getAccoutList(String access_token, String use_num) {
	    // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
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
	        
	        // 요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
	       
	        // 출력되는 값이 200이면 정상작동
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);
	        
	        //string -> 맵에 담기 
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

		// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
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
