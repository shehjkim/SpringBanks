package com.company.temp;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	// 페이지 이동
	@GetMapping("/getBiz")
	public String getBizForm() {
		return "bank/getBiz";
	}

	// 크롤링결과 조회
	@PostMapping("/getBiz")
	public String getBiz(@RequestParam String bizno, Model model) throws IOException {// VO, Map, String
		// 크롤링 회사명 찾아서
		String url = "https://bizno.net/?query=" + "";
		Document doc = Jsoup.connect(url).get();
		Elements element = doc.select("________");
		String bizName = element.text();
		System.out.println(bizName);
		// 모델에 저장하여 뷰페이지로 전달
		model.addAttribute("bizname", bizName);
		return "bank/getBiz";
	}

	@RequestMapping(value = "/respAPI")
	@ResponseBody
	public Map respAPI() {
		RestTemplate rest = new RestTemplate();
		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20210310";
		ResponseEntity<Map> result = rest.getForEntity(url, Map.class);
	   // JsonObject box = result.get("boxOfficeResult").getAsJsonObject();
	   // JsonArray list = box.getAsJsonArray("dailyBoxOfficeList");
	    System.out.println(result.getBody()); 
	        
//		// 영화이름만 출력
//		ArrayList<String> list = new ArrayList<String>();
//		List<DailyBoxOffice> DailyBoxOffice = boxOffice.getBoxOfficeResult().getDailyBoxOfficeList();
//		for (int i = 0; i < DailyBoxOffice.size(); i++) {
//			list.add(DailyBoxOffice.get(i).getMovieNm());
//		}
		return result.getBody();
	}
	
	public void test() {
		// request url
		String url = "https://jsonplaceholder.typicode.com/posts/{id}";

		// create an instance of RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();

		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// example of custom header
		headers.set("X-Request-Source", "Desktop");

		// build the request
		HttpEntity request = new HttpEntity(headers);

		// make an HTTP GET request with headers
		ResponseEntity<String> response = restTemplate.exchange(
		        url,
		        HttpMethod.GET,
		        request,
		        String.class,
		        1
		);

		// check response
		if (response.getStatusCode() == HttpStatus.OK) {
		    System.out.println("Request Successful.");
		    System.out.println(response.getBody());
		} else {
		    System.out.println("Request Failed");
		    System.out.println(response.getStatusCode());
		}
	}
}
