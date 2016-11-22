package com.jebao.jebaodb.entity.extEntity;

/**
 * Created by Administrator on 2016/10/21.
 */
public class PageWhere {
    /**
     *
     * @param pageIndex 页号-开始页为0页
     * @param pageSize 页大小-显示的行数
     **/
    public PageWhere(int pageIndex,int pageSize)
    {
        pageInit(pageIndex, pageSize);
    }

    /**
     * @param pageIndex 页号-开始页为0页
     * @param pageSize 页大小-显示的行数
     * @param orderBy orderBy 的值
     */
    public PageWhere(int pageIndex,int pageSize,String orderBy)
    {
        pageInit(pageIndex, pageSize);
        this.setOrderBy(orderBy);
    }
    private void pageInit(int pageIndex, int pageSize) {
        pageIndex=pageIndex<0?0:pageIndex;
        pageSize=pageSize<0?0:pageSize;
        this.setBegin(pageIndex*pageSize);
        this.setPageSize(pageSize);
    }

    private int begin;
    private int pageSize;
    private String orderBy;

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }


    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getPageSize() {
        if (pageSize==0){
            pageSize=10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
