package com.company.board.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	
	public String toJson(Map<String, Object> map) {
		StringBuilder result = new StringBuilder();
		result.append("{");
		boolean start = true;
		Iterator<String> keys = map.keySet().iterator();		//key 값을 일렬로 줄세움
		while(keys.hasNext()) {
			String key = keys.next();		//keys.next() -- 현재 가르키는 값
			String value = (String)map.get(key);
			
			if( ! start ) {			//, 붙이기 
				result.append(",");
			}else {
				start = false;
			}			
			result.append("\""+key+"\":");
			result.append("\"" + value + "\"");
		}  
		
		
		
		result.append("}");
		//to do
		return result.toString();
	}

	public String toJson(List<Map<String, Object>> map) {
		String result  = "";
		return result;
	}
	
	public String toObjectJson(Object vo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String result = "";
		Field[] fields = vo.getClass().getDeclaredFields();
		for(Field field : fields) {
			String fieldName = field.getName();
			String method = "get" + field.getName()
							.substring(0,1).toUpperCase()
							+field.getName().substring(1);
		Method m = vo.getClass().getDeclaredMethod(method, null);
				String value = (String) m.invoke(vo,null);
		System.out.println(fieldName + ":" + value);
		}
		return result;
		
	}
	
	public String toObjectJson(List<Object> vo) {
		String result = "";
		return result;
		
	}
}
