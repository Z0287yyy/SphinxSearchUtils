/**
 * License: Apache 2.0
 * @author Chris
 * z0287yyy@foxmail.com
 */

package chris.sphinxsearch.SphinxQL.entity;


public class PageBean {

	private static final int DEFAULT_PAGE_SIZE = 20;

	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

	private int start = 0; // 当前页第一条数据在List中的位置,从0开始

	private int currentPage = 1; // 当前页

	private int totalPage = 0; // 总计有多少页

	private int totalCount = 0; // 总记录数
	
	
	public SortDirection order = SortDirection.asc;
	
////////////////
//	构造函数

	public PageBean() {
	}

	public PageBean(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public PageBean(int pageSize, int currentPage) {
		super();
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}

	public static PageBean newInstance() {
		return new PageBean();
	}
	
	public static PageBean newInstance(int currentPage) {
		return new PageBean(currentPage);
	}
	
	public static PageBean newInstance(int pageSize, int currentPage) {
		return new PageBean(pageSize, currentPage);
	}

/////////////////

	public void setPage(int currentPage) {
		if (currentPage > 0) {
			start = (currentPage - 1) * pageSize;
			this.currentPage = currentPage;
		}
	}

	public int getPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public PageBean setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	// 此位置根据计算得到
	protected void setStart() {
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		totalPage = (int) Math.ceil((totalCount + pageSize - 1) / pageSize);
		start = (currentPage - 1) * pageSize;
	}

	// 总页面数根据总数计算得到
	protected void setTotalPage() {

	}

	public int getTotalPage() {
		return totalPage;
	}

///////////////
	// 获取上一页页数
	public int getLastPage() {
		if (hasLastPage()) {
			return currentPage - 1;
		}
		return currentPage;
	}

	public int getNextPage() {
		if (hasNextPage()) {
			return currentPage + 1;
		}
		return currentPage;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return currentPage < totalPage;
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasLastPage() {
		return currentPage > 1;
	}

}
