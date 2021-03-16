package com.company.board.service;

import java.util.HashMap;
import java.util.Map;

public class JsonUtilTest {
	public static void main(String[] args) {
		JsonUtil json = new JsonUtil();
		
		Map<String, Object> map = new HashMap<>();
		map.put("name", "김현정");
		map.put("age", "30");
		map.put("dept", "개발");
		
		String result = json.toJson(map);
		System.out.println(result); // {"name" : "홍길동", "age": 30, "dept" : "개발" }
	}
	
//	BoardVO vo = new BoardVO();
//	vo.setContent("test");
//	vo.setTitle("title");
//	vo.setFilename("file");
//	
}
