package com.brickfarm.etc.lkm;

/**
 * @packageName : com.brickfarm.etc.lkm 
 * @fileName :  PagingInfo.java
 * @author : lkm
 * @date : 2023. 10. 11.
 * @description : 페이징 처리를 위한 클래스 
 */
public class PagingInfo {
	private int totalPostCount; // 전체 게시글 갯수
	private int viewPostCountPerPage; // 한 페이지의 보여줄 게시글의 갯수
	private int totalPageCount; // (totalPostCount / viewPostCountPerPage) = n + 1;
	private int startRowIndex; // 보여주기 시작할 글의 row index 번호
	private int pageNo; // 유저가 클릭한 페이지의 번호
	
	private int pageCountPerBlock = 5; // 한개 페이징 블럭에서 보여줄 페이지 번호의 갯수
	private int totalPagingBlockCount; // 전체 페이징 블럭의 갯수
	private int pageBlockOfCurrentPage; // 현재 페이지가 속한 페이징 블록 번호
	private int startNumOfCurrentPagingBlock; // 현재 페이징 블럭에서 출력 시작 페이지 번호
	private int endNumOfCurrentPagingBlock; // 현재 페이징 블럭에서 출력 끝 페이지 번호
	
	public int getEndNumOfCurrentPagingBlock() {
		return endNumOfCurrentPagingBlock;
	}


	public void setEndNumOfCurrentPagingBlock() {
		// 현재 페이징 블럭의 끝 페이지 번호 = 현재 페이징 블럭번호* pageCountPerBlock
		this.endNumOfCurrentPagingBlock = this.pageBlockOfCurrentPage * this.pageCountPerBlock;
		
		if(this.endNumOfCurrentPagingBlock > this.totalPageCount) {
			this.endNumOfCurrentPagingBlock = this.totalPageCount;
		}
	}
	
	
	public int getStartNumOfCurrentPagingBlock() {
		return startNumOfCurrentPagingBlock;
	}

	public void setStartNumOfCurrentPagingBlock() {
		// 현재 페이징 블럭 시작 페이지 번호 = ((현재 페이징 블럭번호 - 1) * pageCountPerBlcok) + 1
		this.startNumOfCurrentPagingBlock = ((this.pageBlockOfCurrentPage - 1) * this.pageCountPerBlock) + 1;
	}
	
	public int getPageBlockOfCurrentPage() {
		return pageBlockOfCurrentPage;
	}


	public void setPageBlockOfCurrentPage() {
		// 현재 페이지 번호 / pageCountPerBlock -> 나눈 나머지가 0으로 떨어지지 않으면 올림
		if((this.pageNo % this.pageCountPerBlock) == 0) {
			this.pageBlockOfCurrentPage = this.pageNo / this.pageCountPerBlock;
		} else {
			this.pageBlockOfCurrentPage = (int)(Math.ceil(this.pageNo / (double)this.pageCountPerBlock));
		}
	}
	
	
	public void setTotalPagingBlockCount() {
		if (this.totalPageCount % this.pageCountPerBlock == 0) {
			this.totalPagingBlockCount = this.totalPageCount / this.pageCountPerBlock;
		} else { 
			this.totalPagingBlockCount = (this.totalPageCount / this.pageCountPerBlock) + 1;
		}
	}
	
	public int getPageCountPerBlock() {
		return pageCountPerBlock;
	}

	public void setPageCountPerBlock(int pageCountPerBlock) {
		this.pageCountPerBlock = pageCountPerBlock;
	}

	public void setTotalPostCount(int totalPostCount) {
		this.totalPostCount = totalPostCount;
	}
	
	public int getTotalPostCount() {
		return this.totalPostCount;
	}

	public int getViewPostCountPerPage() {
		return viewPostCountPerPage;
	}
	
	public void setViewPostCountPerPage(int viewPostCountPerPage) {
		this.viewPostCountPerPage = viewPostCountPerPage;
	}
	
	public void setTotalPageCount(int totalPostCount, int viewPostCountPerPage) {
		if ((totalPostCount % viewPostCountPerPage) == 0) {
			this.totalPageCount = totalPostCount / viewPostCountPerPage;
		} else {
			this.totalPageCount = (totalPostCount / viewPostCountPerPage) + 1;
		}
		
	}

	public int getTotalPageCount() {
		return this.totalPageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getStartRowIndex() {
		return this.startRowIndex;
	}

	public void setStartRowIndex() {
		// (현재 페이지 번호 -1) * 1페이지당 보여줄 글의 갯수
		this.startRowIndex = (this.pageNo - 1) * this.viewPostCountPerPage;
	}


	@Override
	public String toString() {
		return "PagingInfo [totalPostCount=" + totalPostCount + ", viewPostCountPerPage=" + viewPostCountPerPage
				+ ", totalPageCount=" + totalPageCount + ", startRowIndex=" + startRowIndex + ", pageNo=" + pageNo
				+ ", pageCountPerBlock=" + pageCountPerBlock + ", totalPagingBlockCount=" + totalPagingBlockCount
				+ ", pageBlockOfCurrentPage=" + pageBlockOfCurrentPage + ", startNumOfCurrentPagingBlock="
				+ startNumOfCurrentPagingBlock + ", endNumOfCurrentPagingBlock=" + endNumOfCurrentPagingBlock + "]";
	}
}
