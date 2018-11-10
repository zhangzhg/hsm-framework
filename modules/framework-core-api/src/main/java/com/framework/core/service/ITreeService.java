package com.framework.core.service;


import com.framework.common.domain.BaseModel;
import com.framework.common.domain.TreeModel;

import java.util.Iterator;

/**
 * 后端树接口
 *
 * @param <T>
 */
public interface ITreeService<T extends BaseModel> {

	/**
	 * 初始化树
	 *
	 * @return
	 */
	public TreeModel<T> initTree();

	/**
	 * 添加节点
	 *
	 * @param parentNode
	 * @param childNode
	 */
	public void addNode(TreeModel<T> parentNode, TreeModel<T> childNode);

    TreeModel removeTreeNode(TreeModel root, Iterator children);
}
