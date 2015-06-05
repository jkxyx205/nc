package com.rick.nc.notice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@RequestMapping("/gotoIndexPage")
	public String gotoIndexPage() {
		return "/notice/index";
	}

}
