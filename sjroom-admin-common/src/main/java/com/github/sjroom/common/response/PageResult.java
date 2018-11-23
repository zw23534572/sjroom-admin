package com.github.sjroom.common.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author luobinwen
 *
 * @param <T>
 */
@Data
public class PageResult<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 每页大小
	 */
	private int pageSize;

	/**
	 * 当前页。第一页是1
	 */
	private int pageNo;

	/**
	 * 总记录数
	 */
	private int totalItem;

	/**
	 * 总页数
	 */
	private int totalPage;

	private List<T> data;

	public PageResult() {
	}

	public PageResult(int pageNo, int pageSize, int totalItem) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalItem = totalItem;
		if (pageSize == 0) {
			totalPage = 1;
		} else {
			totalPage = totalItem / pageSize + (totalItem % pageSize > 0 ? 1 : 0);
		}
	}

	public PageResult(int totalItem, List<T> data) {
		this.totalItem = totalItem;
		this.data = data;
	}
}
