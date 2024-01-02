package com.brickfarm.etc.psj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaginationInfo {
		private int totalRowCount; // 총 row 수 | DB에서 넘어옴
		private int rowCountPerPage = 5; // 페이지 당 row 수 | Query의 LIMIT 에 넘길 값
		private int totalPageCount; // 총 페이지 수 | (int)(Math.ceil(totalRowCount / (double)rowCountPerPage))
		private int curPageNo; // 유저가 클릭한 현재 페이지 번호 | 파라메터로 받아옴
		private int startRowIndex; // 보여주기 시작할 row의 index 번호 | Query의 LIMIT 에 넘길 값 | ((curPageNo - 1) * rowCountPerPage)
		private int pageCountPerBlock = 5; // 한 페이지 블록 내에 표시할 페이지 개수
		private int totalPageBlockCount; // 총 페이지 블록 수 | (int)(Math.ceil(totalPageCount / (double)pageCountPerBlock))
		private int curBlockNo;	// 유저가 클릭한 현재 페이지의 블록 번호 | (int)(Math.ceil(curPageNo / (double)pageCountPerBlock))
		private int startPageIndex;	// 페이지 블록의 시작 페이지 번호 | startRowIndex와 같은 계산식 | ((curBlockNo - 1) * pageCountPerBlock) + 1
		private int endPageIndex;	// 페이지 블록의 끝 페이지 번호 | curBlockNo * pageCountPerBlock
									// | 예외) 마지막 블록일 경우 pageCountPerBlock 보다 페이지 수가 작을 수 있다.
									// curBlockNo(현재 페이지의 블록번호) == totalPageBlockCount(마지막 페이지의 블록번호)
									// 						? totalPageCount(마지막 페이지 번호) : curBlockNo * pageCountPerBlock
		
		public PaginationInfo(int totalRowCount, int curPageNo, int rowCountPerPage, int pageCountPerBlock) {
			this.setTotalRowCount(totalRowCount);
			this.setCurPageNo(curPageNo);
			this.setRowCountPerPage(rowCountPerPage);
			this.setPageCountPerBlock(pageCountPerBlock);
			
			this.paginationProcess();
		}
		
		private void setTotalPageCount() {
			this.totalPageCount = (int)(Math.ceil(this.totalRowCount / (double)this.rowCountPerPage));
		}

		private void setStartRowIndex() {
			this.startRowIndex = ((this.curPageNo - 1) * this.rowCountPerPage);
		}

		private void setTotalPageBlockCount() {
			this.totalPageBlockCount = (int)(Math.ceil(this.totalPageCount / (double)this.pageCountPerBlock));
		}

		private void setCurBlockNo() {
			this.curBlockNo = (int)(Math.ceil(this.curPageNo / (double)this.pageCountPerBlock));
		}

		private void setStartPageIndex() {  
			this.startPageIndex = ((this.curBlockNo - 1) * this.pageCountPerBlock) + 1;
		}

		private void setEndPageIndex() {
			this.endPageIndex = this.curBlockNo == this.totalPageBlockCount ? this.totalPageCount : this.totalPageBlockCount == 0 ? 1 : this.curBlockNo * this.pageCountPerBlock;
		}
		
		public void paginationProcess() {
			this.setTotalPageCount();
			this.setStartRowIndex();
			this.setTotalPageBlockCount();
			this.setCurBlockNo();
			this.setStartPageIndex();
			this.setEndPageIndex();
		}
}
