package com.caogen.core.domain;

import java.util.List;

/**
 * 与具体ORM实现无关的分页查询结果封装.
 */
public class Page<T> {

    protected List<T> result = null;
    /**
     * 总数
     */
    protected long total = -1;
    /**
     * 页码
     */
    protected int page = 1;

    /**
     * 每页记录数
     */
    protected int rows = 2;

    /**
     * 偏移量
     */
    protected long offset = 0;

    /**
     * 排序字段
     */
    protected String orderBy = null;

    /**
     * 排序顺序
     */
    protected String orderDir = null;

    /**
     * 搜索条件
     */
    protected String sSearch = null;

    /**
     * 获得页内的记录列表.
     *
     * @return 记录集
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 设置页内的记录列表.
     *
     * @param result
     */
    public void setResult(final List<T> result) {
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {

        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows > 100){
            rows = 100;
        }
        if(rows < 1){
            rows = 10;
        }
        this.rows = rows;
    }

    public long getOffset() {

        int totalPages = (int) Math.ceil((double)total / (double)rows);
        if (totalPages == 0){
            totalPages = 1;
        }

        if(page > totalPages){
            page = totalPages;
        }

        if(page < 1){
            page = 1;
        }

        return (page - 1) * rows;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDir() {
        return orderDir;
    }

    public void setOrderDir(String orderDir) {
        this.orderDir = orderDir;
    }

    public String getsSearch() {
        return sSearch;
    }

    public void setsSearch(String sSearch) {
        this.sSearch = sSearch;
    }

}
