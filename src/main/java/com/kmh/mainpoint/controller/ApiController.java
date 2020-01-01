package com.kmh.mainpoint.controller;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kmh.mainpoint.service.ApiService;

@Controller
public class ApiController {

	
		
		
		@Resource(name="apiService")
		private ApiService apiService;
		
		@RequestMapping(value="/invino/openSampleBoardList.do")
	    public ModelAndView openSampleBoardList(Map<String,Object> commandMap) throws Exception{
	    	ModelAndView mv = new ModelAndView("/invino/ShowUser");
	    	System.out.println("AAA");
	    	List<Map<String,Object>> list = apiService.selectBoardList(commandMap);
	    	mv.addObject("list", list);
	    	
	    	return mv;
	    }

		@RequestMapping(value="/invino/attendanceCheck.do")
	    public ModelAndView attendanceCheck(Map<String,Object> commandMap) throws Exception{
	    	ModelAndView mv = new ModelAndView("/invino/attendanceCheck");
	    	System.out.println("bbb");
	    	
	    	List<Map<String,Object>> list = apiService.selectUserInfo(commandMap);
	    	List<Map<String,Object>> Ignorelist = apiService.selectUserInfoIgnoreAfk(commandMap);
	    	
	    	List<Map<String,Object>> list3 = apiService.GetUserAccountId(Ignorelist);
	    	List<Map<String,Object>> list2 = apiService.attendanceCheckUser(Ignorelist);
	    	
	    	
	    	mv.addObject("list", list);
	    	
	    	return mv;
	    }
		@RequestMapping(value="/invino/NoattendanceCheck.do")
	    public ModelAndView NoattendanceCheck(Map<String,Object> commandMap) throws Exception{
	    	ModelAndView mv = new ModelAndView("/invino/attendanceCheck");
	    	System.out.println("bbb");
	    	
	    	List<Map<String,Object>> list = apiService.selectUserInfo(commandMap);

	    	mv.addObject("list", list);
	    	
	    	return mv;
	    }

}
