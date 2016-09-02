package com.caogen.core.domain;


import com.caogen.core.util.StringUtils;

import java.io.Serializable;

/**
 * 分页参数封装类.
 * 
 */
public class PageRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 页码
	 */
	protected int pageNo = 1;
	
	/**
	 * 每页记录数
	 */
	protected int pageSize = 10;
	
	/**
	 * 排序字段
	 */
	protected String orderBy = null;
	
	/**
	 * 排序顺序
	 */
	protected String orderDir = null;
	
	/**
	 * 数据库字段前缀
	 */
	protected String prefix=null;
	
	/**
	 * 搜索条件
	 */
	protected String sSearch=null;
	
	/**
	 * 开始记录数
	 */
	protected int start=0;
	
	/**
	 * Datatables使用的标识
	 */
	protected String sEcho=null;


	protected boolean countTotal = true;

	public PageRequest() {
	}

	public PageRequest(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	/**
	 * 获得当前页的页号, 序号从1开始, 默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号, 序号从1开始, 低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * 获得每页的记录数量, 默认为10.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量, 低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}
	
	/**
	 * 将属性名称按照驼峰式命名法转成数据库字段名称
	 * 例如 userName 转化为User_Name 
	 * @param name 属性名称
	 * @return 数据库字段名称
	 */
	private  String addUnderscores(String name) {
		StringBuffer buf = new StringBuffer(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if ('_' != buf.charAt(i - 1)
					&& Character.isUpperCase(buf.charAt(i))
					&& !Character.isUpperCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		if(StringUtils.isNotEmpty(this.prefix))
		{
			buf.insert(0,this.prefix);
		}
		return buf.toString().toLowerCase();
	}

	/**
	 * 获得排序字段, 无默认值. 多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段, 多个排序字段时用','分隔.
	 */
	public void setOrderBy( String orderBy) {
		if(StringUtils.isNotEmpty(orderBy))
			this.orderBy = addUnderscores(orderBy);
		else
			this.orderBy=orderBy;
	}

	/**
	 * 获得排序方向, 无默认值.
	 */
	public String getOrderDir() {
		return orderDir;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param orderDir 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrderDir( String orderDir) {
		this.orderDir=orderDir;
	}

	/**
	 * 获得排序参数.
	 */
	/*public List<Sort> getSort() {
		String[] orderBys = StringUtils.split(orderBy, ',');
		String[] orderDirs = StringUtils.split(orderDir, ',');
		Assert.isTrue(orderBys.length == orderDirs.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

		List<Sort> orders = Lists.newArrayList();
		for (int i = 0; i < orderBys.length; i++) {
			orders.add(new Sort(orderBys[i], orderDirs[i]));
		}

		return orders;
	}
*/
	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(orderDir));
	}

	/**
	 * 是否默认计算总记录数.
	 */
	public boolean isCountTotal() {
		return countTotal;
	}

	/**
	 * 设置是否默认计算总记录数.
	 */
	public void setCountTotal(boolean countTotal) {
		this.countTotal = countTotal;
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置, 序号从0开始.
	 */
	public int getOffset() {
		return ((pageNo - 1) * pageSize);
	}
	
	public String getsSearch() {
		return sSearch;
	}

	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
	
	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
