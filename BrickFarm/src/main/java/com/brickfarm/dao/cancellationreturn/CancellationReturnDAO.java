package com.brickfarm.dao.cancellationreturn;

import java.sql.Timestamp;
import java.util.List;

import com.brickfarm.dto.admin.syt.AdminCancelDataDTO;
import com.brickfarm.dto.admin.syt.AdminCancelReturnDTO;
import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.admin.syt.AdminSearchDTO;
import com.brickfarm.dto.user.psj.UserCancelAndReturnOrderDTO;
import com.brickfarm.dto.user.psj.UserWithdrawalConfirmDTO;
import com.brickfarm.etc.syt.Pagination;
import com.brickfarm.vo.admin.syt.AdminCancellationReturnVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerDataVO;

public interface CancellationReturnDAO {

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// 취소, 반품 신청 시 데이터 추가
	int insertCancelAndReturnConfirm(UserWithdrawalConfirmDTO userWithDrawalConfirmDTO) throws Exception;
	
	// 로그인한 멤버의 주문번호 별 취소, 반품 주문 상세 정보 조회(취소, 반품)
	List<UserCancelAndReturnOrderDTO> selectMemberCancelOrderInfo(int memberNo, String merchantUid) throws Exception;
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// 결제에 필요한 데이터 수집 공용
	AdminCancelDataDTO getCancelData(int pkNumber) throws Exception;

	// 페이지네이션을 위한 총데이터 갯수 공용
	int selectTotalDataCount(AdminSearchDTO searchDto, String what) throws Exception;

	// 상태 변경 확인
	int updateCancelReturnByStateCheck(List<AdminCancelReturnDTO> cancelReturnList, String what) throws Exception;

	// 상태 변경 완료
	int updateCancelReturnByStateComplete(List<AdminCancelReturnDTO> cancelReturnList, String what) throws Exception;

	// 리스트 불러오기 공용
	List<AdminCancellationReturnVO> selectCancelReturnList(AdminSearchDTO searchDto, Pagination pagination, String what)
			throws Exception;

	// 취소반품 테이블 삭제(신청취소시 로직)
	void deleteCancelReturnByDetailedOrderNo(List<AdminDetailedOrderDTO> detailedOrderList) throws Exception;
	// 취소반품 테이블 추가(신청시 로직)
	int insertCancelReturnList(List<AdminDetailedOrderDTO> detailedOrderList, String what) throws Exception;
	// 입금기한 지난것들 취소신청
	int insertSchedulerCancel(List<AdminSchedulerDataVO> schedulerDataList, Timestamp now) throws Exception;
	// 취소 신청 되어있던애 지우기(스케줄러로 다시 인설트를 위해)
	int deleteSchedulerCancel(List<AdminSchedulerDataVO> schedulerDataList) throws Exception;
	// ==================================================================================================================================================


	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
