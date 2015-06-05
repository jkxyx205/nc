package com.rick.base.plugin.ztree.model;

public class TreeNode {
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public boolean getIsParent() {
		return parent;
	}

	public void setParent(boolean parent) {
		this.parent = parent;
	}

 
	public String getIcon() {
		return icon;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public boolean isParent() {
		return parent;
	}


	private String tid;
	
	private String id;
	
	private String name;
	
	private String pId;
	
	private boolean checked;
	
	private boolean open;
	
	private boolean parent;
	
	private String icon;
	
	private String iconSkin;
}
