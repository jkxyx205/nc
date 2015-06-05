package com.rick.base.plugin.ztree.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rick.base.plugin.ztree.model.TreeNode;

public interface ZtreeService {
	/**
	 * 获取子节点
	 * @param id
	 * @return
	 */
	public List<TreeNode> getSubTreeNode(String id,HttpServletRequest request);
	
	/**
	 * 获取所有节点
	 * @return
	 */
	public List<TreeNode> getTreeNode(HttpServletRequest request);
	
}
