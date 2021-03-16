package com.company.common;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieAPI {

	public Map getBoxOffice() {
		String url ="http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20210311";
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, Map.class);
	}
}
