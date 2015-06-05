package com.rick.nc.index.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/index")
public class IndexController {
	
	@RequestMapping("/{page}")
	public String gotoIndex(@PathVariable int page) {
		System.out.println(page + ".....................");
		return "/index";
	}
	
	@RequestMapping
	public String gotoIndex() {
		System.out.println(1 + ".....................");
		return "/index";
	}
	
	
}
