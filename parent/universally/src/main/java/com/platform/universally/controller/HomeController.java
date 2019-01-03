package com.platform.universally.controller;

import com.platform.universally.manager.response.JsonResult;
import com.platform.universally.manager.response.ResCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

	@RequestMapping(value="/index")
	public JsonResult index(){
		return JsonResult.getJsonResult(ResCode.SUCCESS, true);
	}
	
}
