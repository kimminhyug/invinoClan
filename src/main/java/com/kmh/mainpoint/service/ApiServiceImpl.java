package com.kmh.mainpoint.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.kmh.mainpoint.dao.ApiDAO;
import com.kmh.test.userInfoAPI;


@Service("apiService")
public class ApiServiceImpl implements ApiService{
	
	
	@Resource(name="apiDAO")
	private ApiDAO apiDAO;

	int apiIOCount = 0;
	
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		return apiDAO.selectBoardList(map);
	}


	@Override
	public List<Map<String, Object>> selectUserInfo(Map<String, Object> map) throws Exception {
		return apiDAO.selectUserInfo(map);
	}
	@Override
	public List<Map<String, Object>> selectUserInfoIgnoreAfk(Map<String, Object> map) throws Exception {
		return apiDAO.selectIgnoreUserInfo(map);
	}


	@Override
	public List<Map<String, Object>> attendanceCheckUser(List<Map<String, Object>> list) throws JSONException, IOException, ParseException, InterruptedException {
		String ApiKey = apiDAO.selectApiKey();
		apiIOCount = apiIOCount + 1;
		if(apiIOCount >= 20) {
			apiIOCount = 0;
			Thread.sleep(120000);
		}
//		JSONObject level = userInfoAPI.getUserInfo(umodel.getUserId().toString(),"TEST");
		String ab2 = getMonday("2019","12",getWeek(-1));

		Date date = new Date();
		
		
		ab2 = ab2+" 00:01:01";
		
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss",Locale.US);
		String monday = getMonday("2019","12",getWeek(-1));
		String sunday = getSunday("2019","12",getWeek(-1));
		
		monday = monday + " 00:00:01";
		sunday = sunday + " 23:59:59";
		Date nowTime1 = dateFormat.parse(monday);
		Date nowTime2 = dateFormat.parse(sunday);
		long unixMonday = nowTime1.getTime();
		long unixSunday = nowTime2.getTime();
		


		String play = "";
		
		
		
		

		for(int i=0;i<=list.size()-1;i++) { //클럽원 한명한명체크
			
			String user = (list.get(i).get("name")).toString();
			String uaccountID = (list.get(i).get("accountId")).toString();
			
			String accountId = null;
			System.out.println(apiIOCount);
			JSONObject info = userInfoAPI.getUserInfo(user,ApiKey); 
			apiIOCount = apiIOCount + 1;
			if(apiIOCount >= 20) {
				apiIOCount = 0;
				Thread.sleep(120000);
				System.out.println("휴식중");
			}
			accountId = (info.get("accountId")).toString();
			JSONObject matchsInfo = userInfoAPI.getUserMatchInfo(accountId,ApiKey,unixMonday,unixSunday); //
			apiIOCount = apiIOCount + 1;
			if(apiIOCount >= 20) {
				apiIOCount = 0;
				Thread.sleep(120000);
				System.out.println("휴식중");
			}
			
			
			//matchid 가저오기

			int matchTotalCount = matchsInfo.getJSONArray("matches").length();
			int currentMatchIndex = -1; //총매치데이터에서 매치정보 하나씩 뺼때 그 인자
			do
			{
				play = "";
				if(currentMatchIndex == matchTotalCount-1) { //매치정보 조회가 끝날시 종료
					
					break;
				}
				currentMatchIndex = currentMatchIndex + 1;
				JSONObject matchDetail = userInfoAPI.getUserMatchDetail((matchsInfo.getJSONArray("matches").getJSONObject(currentMatchIndex).get("gameId")).toString(),ApiKey);
				apiIOCount = apiIOCount + 1;
				if(apiIOCount >= 20) {
					apiIOCount = 0;
					Thread.sleep(120000);
					System.out.println("휴식중");
				}
				
				
				JSONArray matchDetailPlayer = matchDetail.getJSONArray("participantIdentities");
				String gameid = matchDetail.get("gameId").toString();


		
				
				for(int j=0;j<=matchDetailPlayer.length()-1;j++) { //해당 매치 유저 아이디 하나씩 뽑기
//					System.out.println(matchDetailPlayer);
//					System.out.println(matchDetailPlayer.length());
					System.out.println((JSONObject) matchDetailPlayer.get(0));
					JSONObject playerInfos = (JSONObject) matchDetailPlayer.get(j);
					JSONObject playerInfo = playerInfos.getJSONObject("player");
					String teamAccountId = playerInfo.get("accountId").toString();
					

					for(int k=0;k<=list.size()-1;k++) {//클럽원 계정아이디 하나하나 뽑아가며 해당매치인원과 비교

//						System.out.println(k);
//						
//						System.out.println(user+" 주인공 "+playerInfo.get("summonerName").toString()+"  "+ playerInfo.get("accountId").toString());
//						System.out.println(list.get(k).get("name").toString() +" "+list.get(k).get("accountId").toString() );
//						
						
						
						if(teamAccountId.equals(list.get(k).get("accountId").toString())) {
							if(uaccountID.equals(teamAccountId)) {
								
							} else {
								play = "play";
								play = teamAccountId + "   " + list.get(k).get("accountId").toString();
								break;	
							}
							
						}
						
					}
					
				}
				
			}   while(play == "");



		

			
//			System.out.print(play+"  ");
//			System.out.println(user);
			valueUpdate(play,user,"checkPlay");
			
			
		}
		return null;
	}
	//유저 accountId insert로직
	@Override
	public List<Map<String, Object>> GetUserAccountId(List<Map<String, Object>> list) throws JSONException, IOException, ParseException {
		String ApiKey = apiDAO.selectApiKey();

		for(int i=0;i<=list.size()-1;i++) { //유저 숫자만큼 반복 
			String user = (list.get(i).get("name")).toString();	
			String accountId = null;
			
			JSONObject info = userInfoAPI.getUserInfo(user,ApiKey);
			accountId = (info.get("accountId")).toString();
			valueUpdate(accountId,user,"accountId");
			
		}
		return null;
	}
	
	public void valueUpdate(String value, String userName,String col){
		HashMap<String, String> map = new HashMap<>();
		
		map.put("value", value);
		map.put("userName", userName);
		if(col.equals("accountId")) {
			apiDAO.updateAccountId(map);
		}
		else if(col.equals("checkPlay")) {
			apiDAO.updatecheckPlay(map);
		}
 	}
	
	public static String getWeek(int idx){
	    TimeZone newYorkTime = TimeZone.getTimeZone("America/New_York");
 		Calendar c = Calendar.getInstance(newYorkTime);
 		
 		String week = String.valueOf(c.get(Calendar.WEEK_OF_MONTH)+idx);
 		return week;
 	}


	public static String getMonday(String yyyy,String mm, String wk){
 		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy.MM.dd",Locale.US);
 		TimeZone newYorkTime = TimeZone.getTimeZone("America/New_York");
 		Calendar c = Calendar.getInstance(newYorkTime);
 		int y=Integer.parseInt(yyyy);
 		int m=Integer.parseInt(mm)-1;
 		int w=Integer.parseInt(wk);
 	
 		c.set(Calendar.YEAR,y);
 		c.set(Calendar.MONTH,m);
 		c.set(Calendar.WEEK_OF_MONTH,w);
 		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

 		return formatter.format(c.getTime());
 	}



	public static String getSunday(String yyyy,String mm, String wk){
 		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy.MM.dd",Locale.US);
 		TimeZone newYorkTime = TimeZone.getTimeZone("America/New_York");
 		Calendar c = Calendar.getInstance(newYorkTime);
 		int y=Integer.parseInt(yyyy);
 		int m=Integer.parseInt(mm)-1;
 		int w=Integer.parseInt(wk);
 		
 		c.set(Calendar.YEAR,y);
 		c.set(Calendar.MONTH,m);
 		c.set(Calendar.WEEK_OF_MONTH,w);
 		c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
 		c.add(c.DATE,7);
 		return formatter.format(c.getTime());
 	}









}
