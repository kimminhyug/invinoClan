package com.kmh.lolapis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kmh.models.RequestUserModel;

import com.kmh.pools.dbPool;
import com.kmh.test.userInfoAPI;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	 



	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping(value = "/dbTest", method = RequestMethod.POST)
	public String dbTest(Locale locale, Model model) throws Exception {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		int i = dbPool.test();
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("dbcheck", formattedDate );
		
		return "home";
	}
	@RequestMapping(value = "/input", method = {RequestMethod.GET, RequestMethod.POST})
	public String userInput(Locale locale, Model model) throws Exception {

		return "input";
	}

	
	@RequestMapping("/lolInfo") 
	public String lolInfo(RequestUserModel umodel,Model model) throws JSONException, IOException{ 
		System.out.println("Á¦¸ñ:"+umodel.getUserId());
		//String level = userInfoAPI.getUserInfo(umodel.getUserId().toString(),"TEST");
		JSONObject level = userInfoAPI.getUserInfo(umodel.getUserId().toString(),"TEST");
		//model.addAttribute("userId", umodel.getUserId());
		model.addAttribute("userId", level);
		return "result";
		}

	
	
}
