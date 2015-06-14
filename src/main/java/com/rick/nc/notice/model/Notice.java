package com.rick.nc.notice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="nc_notice")
public class Notice {
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getNoticeStatus() {
		return noticeStatus;
	}


	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	
	

	public Date getPublishTime() {
		return publishTime;
	}


	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}


	

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}




	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Integer id;
	
	private String title;
	
	private String userId;
	
	private String content;
	
	private Date createTime;
	
	private Date updateTime;
	
	private Date publishTime;
	
	@Column(name="notice_status")
	private String noticeStatus;
	
	
	public enum Status {
		DRAFT,PUBLISHED
	}

}
