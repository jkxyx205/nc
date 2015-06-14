package com.rick.nc.notice;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rick.nc.notice.model.Notice;
import com.rick.nc.notice.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	private static final int ROWS = 15;
	
	@Resource
	private NoticeService noticeService;
	
	@RequestMapping("/gotoIndexPage")
	public String gotoIndexPage() {
		return "/notice/index";
	}
	
	@RequestMapping("/gotoAdd/{id}")
	public String gotoEdit(@PathVariable Integer id,Model model) {
		if (id != null) {
			Notice notice = noticeService.getNoticeById(id);
			model.addAttribute("notice", notice);
		} else {
			model.addAttribute("notice", null);
		}
		
		return "/notice/add";
	}
	
	@RequestMapping("/gotoAdd")
	public String gotoAdd() {
		return "/notice/add";
	}
	
	
	@RequestMapping("/saveNotice")
	public String saveNotice(Notice notice,String noticeStatus) {
		Date now = new Date();
		
		Integer id = notice.getId();
		if (id == null) {
			notice.setUserId("admin");
			notice.setNoticeStatus(Notice.Status.PUBLISHED.name());
			notice.setCreateTime(new Date());
			notice.setUpdateTime(now);
			if(Notice.Status.PUBLISHED.name().equals(noticeStatus))
				notice.setPublishTime(now);
			notice.setNoticeStatus(noticeStatus);
			noticeService.addNotice(notice);
		} else {
			Notice old = noticeService.getNoticeById(id);
			old.setUpdateTime(now);;
			old.setContent(notice.getContent());
			old.setTitle(notice.getTitle());
			old.setNoticeStatus(noticeStatus);
			if(Notice.Status.PUBLISHED.name().equals(noticeStatus))
				old.setPublishTime(now);
			noticeService.updateNotice(old);
		}
		
		return "redirect:gotoIndexPage";
	}
	
	@RequestMapping("/gotoDetail/{id}")
	public String gotoDetail(@PathVariable Integer id,Model model) {
		Notice notice = noticeService.getNoticeById(id);
		model.addAttribute("notice", notice);
		return "/notice/detail";
	}
	
	@RequestMapping("/list/{currentPage}")
	public String gotoList(String title,@PathVariable int currentPage,Model model) throws Exception {
		model.addAttribute("jqgridJsonBO", noticeService.getPageList(title, currentPage, ROWS));
		model.addAttribute("start", (currentPage-1) * ROWS + 1);
		model.addAttribute("title", title);
		return "/notice/list";
	}
}
