package com.company.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.company.temp.service.BankVO;

public class TestReflection {

	public static void main(String[] args) {
		BankVO vo = new BankVO();
		vo.setAccessToken("aaa");
		vo.setFintechUseNum("bbb");
		Map<String, Object> map = methodToMap(vo);
		System.out.println(map);
	}

	public static Map<String, Object> methodToMap(Object obj) {
		// Field[] fields = obj.getClass().getDeclaredFields();
		Method[] method = obj.getClass().getDeclaredMethods();
		Map<String, Object> result = new HashMap<String, Object>();

		for (int i = 0; i < method.length; ++i) {
			try {
				// System.out.printf("name : %s, value : %s \n",fields[i].getName(),
				// fields[i].get(this) );
				if(method[i].getName().startsWith("set") );
//					result.put(method[i].getName().substring(3), method[i].canAccess(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}

	public static Map<String, Object> fieldToMap(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		Map<String, Object> result = new HashMap<String, Object>();

		for (int i = 0; i < fields.length; ++i) {
			try {
				// System.out.printf("name : %s, value : %s \n",fields[i].getName(),
				// fields[i].get(this) );
				result.put(fields[i].getName(), fields[i].get(obj));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			}
		}
		return result;
	}

}
