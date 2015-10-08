package com.rick.nc.index.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rick.nc.webservice.weather.CityVO;
import com.rick.nc.webservice.weather.WeatherWS;


@Controller
@RequestMapping("/index")
public class IndexController {
	
	@Resource
	private WeatherWS ww;
 
	@RequestMapping("/{page}")
	public String gotoIndex(@PathVariable int page) {
		return "/index";
	}
	
	@RequestMapping
	public String gotoIndex(Model model) {	
		CityVO vo = ww.getCity("苏州");
		model.addAttribute("weather", vo);
		return "/index";
	}
}
