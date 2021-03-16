package com.company.temp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.common.MovieAPI;

@Controller
public class MovieController {
	
	@Autowired MovieAPI movieAPI;
	
	@RequestMapping("/boxOffice")
	@ResponseBody
	public List<String> boxOffice() {
		List<String> list = new ArrayList<>();
		Map map = movieAPI.getBoxOffice();
		Map boxOfficeResult = (Map)map.get("boxOfficeResult");
		List<Map> dailyBoxOfficeList = 
				     (List<Map>)boxOfficeResult.get("dailyBoxOfficeList");
		for(Map movie : dailyBoxOfficeList ) {
			list.add((String)movie.get("movieNm") 
					 + ":" 
					 + (String)movie.get("movieCd"));
		}
		return list;	
	}
	@RequestMapping("/movieActor")
	@ResponseBody
	public List<String> movieActor() {
		List<String> list = new ArrayList<>();
		//Map map = movieAPI.getmovieInfo();	
		return list;	
	}
}
