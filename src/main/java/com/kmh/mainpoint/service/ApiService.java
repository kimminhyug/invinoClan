package com.kmh.mainpoint.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

public interface ApiService {

	List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception;

	List<Map<String, Object>> selectUserInfo(Map<String, Object> map) throws Exception;

	List<Map<String, Object>> attendanceCheckUser(List<Map<String, Object>> list) throws Exception;

	List<Map<String, Object>> GetUserAccountId(List<Map<String, Object>> list)
			throws JSONException, IOException, ParseException;

	List<Map<String, Object>> selectUserInfoIgnoreAfk(Map<String, Object> map) throws Exception;
	
	

	
	
}
