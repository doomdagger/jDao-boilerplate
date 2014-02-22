package com.doomdagger.dao.support;

/**
 * Page Number 从0开始计算，跟数组一个标准
 * 
 * @author Li He
 */
public class Pageable {
	private int pageSize;
	private int offset;
	private int pageNumber;
	private int pageLength;
	private int itemSum;
	
	private Pageable() {
	}

	/**
	 * 创建一个pageable对象
	 * @param pageSize 单页的条目个数，即单页容量
	 * @param pageNumber 页码，即第几页，从0开始计算，0为第一页
	 */
	private Pageable(int pageSize, int pageNumber) {
		this();
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.offset = this.pageSize * this.pageNumber;
	}

	/**
	 * 创建一个pageable对象
	 * @param pageSize 单页的条目个数，即单页容量
	 * @param pageNumber 页码，即第几页，从0开始计算，0为第一页
	 * @return 返回一个Pageable对象
	 */
	public static Pageable inPage(int pageNumber, int pageSize) {
		return new Pageable(pageSize, pageNumber);
	}

	/**
	 * 获取页码
	 * @return 返回的页码，从0开始计算，0为第一页
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * 返回页面容量
	 * @return 一页含有多少条目数
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 返回需要跳过的条目数，此值由系统计算生成，根据传入的pageSize与pageNumber
	 * @return 跳过的条目数
	 */
	public int getOffset() {
		return offset;
	}


	/**
	 * 页数，经过dao层调用后可能会被赋值，代表总页数
	 * @return 返回总页数
	 */
	public int getPageLength() {
		return pageLength;
	}

	public void setPageLength(int pageLength) {
		this.pageLength = pageLength;
	}

	/**
	 * 总条目数，经过dao层调用后可能会被赋值
	 * @return 返回总条目数
	 */
	public int getItemSum() {
		return itemSum;
	}

	public void setItemSum(int itemSum) {
		this.itemSum = itemSum;
	}
	
	
}