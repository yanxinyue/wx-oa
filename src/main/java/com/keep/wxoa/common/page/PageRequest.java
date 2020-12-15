package com.keep.wxoa.common.page;

/**
 * 封装分页请求类
 * @Author: xinyueyan
 * @Date: 12/14/2020 4:20 PM
 */
public class PageRequest {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public PageRequest() {
    }
}
