package com.brickfarm.etc.syt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @packageName : com.brickfarm.etc.syt 
 * @fileName :  Pagination.java
 * @author : syt
 * @date : 2023. 10. 2.
 * @description : 페이지네이션에 사용하는 값
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Pagination {
	private int totalDataCount; // 총 데이터 개수*
	private int showDataCountPerPage;  // 1페이지당 데이터 수*
	private int totalPageCount;  // 총 페이지 개수
	private int startRowIndex;   // 보여주기 시작할 글의 row index 번호
	private int pageNo;          // 선택한 페이지 번호*
	
	private int totalPageGroupsCount;    // 총 페이지 그룹 개수
	private int showPageGroupsCount;     // 한번에 보여질 페이지 개수 * 
	private int pageGroupsOfCurrentPage; // 현재 페이지가 속한 페이징 그룹 번호
	private int startNumOfPageGroups;    // 페이지 그룹의 첫페이지 번호
	private int endNumOfPageGroups;      // 페이지 그룹의 마지막페이지 번호
	
	public Pagination(int totalDataCount, int showDataCountPerPage, int pageNo, int showPageGroupsCount) {
		super();
		this.totalDataCount = totalDataCount;
		this.showDataCountPerPage = showDataCountPerPage;
		this.totalPageCount = (int)Math.ceil((double)totalDataCount / (double)showDataCountPerPage);;
		this.startRowIndex = (pageNo - 1) * showDataCountPerPage;
		this.pageNo = pageNo;
		
		this.totalPageGroupsCount = (int)Math.ceil((double)totalPageCount /(double)showPageGroupsCount);
		this.showPageGroupsCount = showPageGroupsCount;
		this.pageGroupsOfCurrentPage = (int)Math.ceil((double)pageNo/(double)showPageGroupsCount);
		this.startNumOfPageGroups = (int)Math.ceil(((double)(pageGroupsOfCurrentPage - 1) * (double)showPageGroupsCount) +1);
		this.endNumOfPageGroups = pageGroupsOfCurrentPage * showPageGroupsCount;
		if(this.endNumOfPageGroups > this.totalPageCount) {
			this.endNumOfPageGroups = this.totalPageCount;
		}
	}
}
