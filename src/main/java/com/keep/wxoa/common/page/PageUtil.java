package com.keep.wxoa.common.page;

import com.github.pagehelper.PageInfo;

/**
 * @Author: xinyueyan
 * @Date: 12/14/2020 4:29 PM
 */
public class PageUtil {
    /**
     * 将分页信息封装到统一的接口
     * @param
     * @param
     * @return
     */
    public static PageResult getPageResult(PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }
}
