package com.brickfarm.etc.sjy;

import lombok.ToString;

@ToString
public class PagingInfo {
	private int totalPostCnt; // 전체 게시판 글 개수
	private int viewPostCntPerPage = 10; // 1 페이지 당 보여줄 글의 갯수
	private int totalPageCnt; // 총 페이지 수
	private int startRowIndex; // 보여주기 시작할 글의 row index 번호
	private int pageNo; // 유저가 클릭한 현재 페이지 번호

	private int PageCntPerBlock = 5; // 한 개 블럭에 보여줄 페이지 번호의 개수
	private int totalPagingBlockCnt; // 전체 페이징 블럭의 갯수
	private int pageBlockOfCurrentPage; // 현재 페이지가 속한 페이징 블럭 번호
	private int startNumOfCurrentPagingBlock; // 현재 페이징 블럭에서의 출력 시작 페이지 번호
	private int endNumOfCurrentPagingBlock; // 현재 페이징 블럭에서의 출력 끝 페이지 번호

	public int getTotalPostCnt() {
		return this.totalPostCnt;
	}

	public void setTotalPostCnt(int totalPostCnt) {
		this.totalPostCnt = totalPostCnt;
	}

	public int getViewPostCntPerPage() {
		return viewPostCntPerPage;
	}

	public void setViewPostCntPerPage(int viewPostCntPerPage) {
		this.viewPostCntPerPage = viewPostCntPerPage;
	}

	public int getTotalPageCnt() {
		return totalPageCnt;
	}

	public void setTotalPageCnt() {
		// 총 페이지 수 : 전체 글 수 / 한 페이지 당 보여줄 글의 개수 -> 나누어 떨어지지 않으면 + 1
		if (this.totalPostCnt % this.viewPostCntPerPage == 0) {
			this.totalPageCnt = this.totalPostCnt / this.viewPostCntPerPage;
		} else {
			this.totalPageCnt = (this.totalPostCnt / this.viewPostCntPerPage) + 1;
		}

	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getStartRowIndex() {
		return startRowIndex;
	}

	public void setStartRowIndex() {
		// (pageNo - 1) * N
		this.startRowIndex = (this.pageNo - 1) * this.viewPostCntPerPage;
	}

	public int getPageCntPerBlock() {
		return PageCntPerBlock;
	}

	public void setPageCntPerBlock(int pageCntPerBlock) {
		PageCntPerBlock = pageCntPerBlock;
	}

	public int getTotalPagingBlockCnt() {
		return totalPagingBlockCnt;
	}

	public void setTotalPagingBlockCnt() {
		// 전체 페이징 블럭 갯수 = 전체 페이지 수 / pageCntPerBlock -> 나누어 떨어지지 않으면 + 1
		if (this.totalPageCnt > this.PageCntPerBlock) {
			if (this.totalPageCnt % this.PageCntPerBlock == 0) {
				this.totalPagingBlockCnt = this.totalPageCnt / this.PageCntPerBlock;
			} else {
				this.totalPagingBlockCnt = this.totalPageCnt / this.PageCntPerBlock + 1;
			}
		} else {
			if (this.PageCntPerBlock % this.totalPageCnt == 0) {
				this.totalPagingBlockCnt = this.PageCntPerBlock / this.totalPageCnt;
			} else {
				this.totalPagingBlockCnt = this.PageCntPerBlock / this.totalPageCnt + 1;
			}
		}

	}

	public int getPageBlockOfCurrentPage() {
		return pageBlockOfCurrentPage;
	}

	public void setPageBlockOfCurrentPage() {
		// 현재 페이지 번호 / pageCntPerBlock -> 나누어 떨어지지 않으면 올림
		this.pageBlockOfCurrentPage = (int) Math.ceil((double) this.pageNo / this.PageCntPerBlock);

	}

	public int getStartNumOfCurrentPagingBlock() {
		return startNumOfCurrentPagingBlock;
	}

	public void setStartNumOfCurrentPagingBlock() {
		// (현재 페이징 블럭번호 - 1) * PageCntPerBlock + 1
		this.startNumOfCurrentPagingBlock = (this.pageBlockOfCurrentPage - 1) * this.PageCntPerBlock + 1;
	}

	public int getEndNumOfCurrentPagingBlock() {
		return endNumOfCurrentPagingBlock;
	}

	public void setEndNumOfCurrentPagingBlock() {
		// 현재 페이징 블럭번호 * PageCntPerBlock
		this.endNumOfCurrentPagingBlock = this.pageBlockOfCurrentPage * this.PageCntPerBlock;
		if (this.endNumOfCurrentPagingBlock > this.totalPageCnt) {
			this.endNumOfCurrentPagingBlock = this.totalPageCnt;
		}
	}
}
