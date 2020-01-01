package com.kmh.test;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QueryMap_Main {
	
   public static void main(String[] args) throws Exception {

        URL aURL = new URL("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/%EC%97%AC%ED%96%89%EB%96%A0%EB%82%98%EB%8A%94%EB%B0%94%EB%93%9C%EC%B0%A1?api_key=RGAPI-561213d8-6059-458b-9a77-6d191db66181");

        
        Map<String, String> map=getQueryMap(aURL.getQuery());
        
        if (map!=null) {
	        Set<String> keys=map.keySet();
	        int idx=0;
	        for (String key: keys) {
	        	System.out.println("Parameter[" + idx + "].Name=" + key);
	        	System.out.println("Parameter[" + idx + "].Value=" + map.get(key));
	        	idx++;
	        }
        }
        else {
        	System.out.println("Cannot Find Query");
        }
    }
    
    
    /** URL에서 파라미터를 파싱한다 **/
    public static Map<String, String> getQueryMap(String query)
    {    	
    	if (query==null) return null;
    	
    	int pos1=query.indexOf("?");
    	if (pos1>=0) {
    		query=query.substring(pos1+1);
    	}
    	
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
}