package com.kmh.pools;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbPool {

	
	public static int test() throws Exception{
		  Class.forName("com.mysql.jdbc.Driver");
		  final String URL = "jdbc:mysql://localhost:3306/kmh1?characterEncoding=utf8&amp;useSSL=false&amp;autoReconnection=true";
		  final String USER = "root"; //DB 사용자명 
		  final String PW = "1234";   //DB 사용자 비밀번호
		  int index = 0; //0 실패 1성공
		  try(Connection con = (Connection) DriverManager.getConnection(URL, USER, PW)){
		   System.out.println("성공");
		   System.out.println(con);
		   index = 1;
		  }catch (Exception e) {
		   System.out.println("에러발생");
		   index = 0;
		   e.printStackTrace();
		  }
		  return index;
	}

}
