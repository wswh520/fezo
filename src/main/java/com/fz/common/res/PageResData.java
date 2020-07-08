package com.fz.common.res;

import java.util.List;

public class PageResData<T> {
	
	private int limit = 15;
	private String anchor;
	private List<T> datas;
	
	//用于逐页获取
	private boolean hasMore;
	
	//用于分页获取
	private int currentPage = 1;//当前页号
	private int totalPages = 0;//查询结果总页面
	private int totalRecords = 0;//查询结果记录数
	
	public PageResData(){}
	public PageResData(int limit){
		this.limit = limit;
	}
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public List<T> getDatas() {
		return datas;
	}
	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	public boolean getHasMore() {
		return hasMore;
	}
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
		totalPages = totalRecords==0?0:(totalRecords-1)/limit+1;
		currentPage = repairPn(totalPages,currentPage);
	}
	public int getOffset(){
		return (this.getCurrentPage()-1)*this.getLimit();
	}
	
	private int repairPn(int totalPages,int currentPage){
		if(totalPages==0){
			currentPage = 0;
		}else if(currentPage<=0){
			currentPage = 1;
		}else if(currentPage>totalPages){
			currentPage = totalPages;
		}
		return currentPage;
	}
}
