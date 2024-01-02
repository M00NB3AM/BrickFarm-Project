
package com.brickfarm.etc.kmh;

public class PaginationInfo {
	

	// -------------------------------------------------------
	// 1페이지 당 출력할 데이터를 끊어 내기 위해 필요한 멤버들 
	private int totalCount; // 전체 게시판 글의 수
	private int rowOfNums = 10; // 한 페이지 당 들어가는 갯수
	private int totalPage; // 총 페이지 수
	private int startRowIndex; // 보여주기 시작할 글의 row index 번호
	private int pageNo; // 유저가 클릭한 현재 페이지 번호
	
	// ---------------------------------------------------------
	// 페이지네이션을 위한 멤버들
	private int pagePerGroup = 5; // 한 개 블럭에 보여줄 페이지 번호의 수
	private int totalGroup; // 전체 체이징 블럭의 수
	private int pageBlockOfCurrentPage; // 현재 페이지가 속한 페이징 블럭 번호
	private int startNumOfCurPageGroup; // 현재 페이징 블럭에서 출력 시작 페이지 번호
	private int endNumOfCurPageGroup; // 현재 체이지 블럭에서 출력되는 마지막 페이지 번호
	



	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public int getTotalCount() {
		return this.totalCount;
	}

	public int getRowOfNums() {
		return rowOfNums;
	}

	public void setRowOfNums(int rowOfNums) {
		this.rowOfNums = rowOfNums;
	}
	
	
	public int getTotalPage() {
		return this.totalPage;
	}


	public void setTotalPage(int totalCount, int rowOfNums) {
		if(totalCount % rowOfNums == 0) {
			this.totalPage = totalCount / rowOfNums;
		} else {
			this.totalPage = (totalCount / rowOfNums) + 1;
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public void setStartRowIndex() {
		
		this.startRowIndex = (this.pageNo -1) * this.rowOfNums;
	}
	public int getStartRowIndex() {
		return this.startRowIndex;
	}
	
	public int getPagePerGroupk() {
		return pagePerGroup;
	}

	public void setPagePerGroup(int pageCntPerBlock) {
		this.pagePerGroup = pageCntPerBlock;
	}
	public void setTotalGroup () {
		
		if(this.totalPage % this.pagePerGroup == 0) {
			this.totalGroup = this.totalPage/this.pagePerGroup;
		} else {
			this.totalGroup = (this.totalPage/this.pagePerGroup) +1;
		}
		
	}
	
	public int getTotalGroup() {
		return this.totalGroup;
	}
	
	public void setPageBlockOfCurrentPage() {
	
		// else 문만 작성해도 같은 의미로 사용 가능
		if(this.pageNo % this.pagePerGroup == 0) {
			this.pageBlockOfCurrentPage = this.pageNo / this.pagePerGroup;
		} else {
			this.pageBlockOfCurrentPage = (int)(Math.ceil(this.pageNo/(double)this.pagePerGroup));
		}
	}

	public int getPageBlockOfCurrentPage() {
		return pageBlockOfCurrentPage;
	}
	
	public void setStartNumOfCurPageGroup(){
		this.startNumOfCurPageGroup = ((this.pageBlockOfCurrentPage -1) * this.pagePerGroup) +1;
	}

	public int getStartNumOfCurPageGroup() {
		return startNumOfCurPageGroup;
	}
	
	public void setEndNumOfCurPageGroup() {
		this.endNumOfCurPageGroup = this.pageBlockOfCurrentPage * this.pagePerGroup;
		if(this.endNumOfCurPageGroup > this.totalPage) {
			this.endNumOfCurPageGroup = this.totalPage;
		}
	}

	public int getEndNumOfCurPageGroup() {
		return endNumOfCurPageGroup;
	}

	@Override
	public String toString() {
		return "PagingInfo [totalCount=" + totalCount + ", rowOfNums=" + rowOfNums + ", totalPage=" + totalPage
				+ ", startRowIndex=" + startRowIndex + ", pageNo=" + pageNo + ", pagePerGroup=" + pagePerGroup
				+ ", totalGroup=" + totalGroup + ", pageBlockOfCurrentPage=" + pageBlockOfCurrentPage
				+ ", startNumOfCurPageGroup=" + startNumOfCurPageGroup + ", endNumOfCurPageGroup="
				+ endNumOfCurPageGroup + "]";
	}

}
