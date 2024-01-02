package com.brickfarm.dao.member;

import java.util.List;
import java.util.Map;

import com.brickfarm.dto.admin.kmh.AdminMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminMessageDTO;
import com.brickfarm.dto.admin.syt.AdminCancelDataDTO;
import com.brickfarm.dto.user.ysh.UserMemberDTO;
import com.brickfarm.dto.user.ysh.UserMemberLoginDTO;
import com.brickfarm.vo.admin.kmh.AdminChartVO;
import com.brickfarm.vo.admin.kmh.AdminMemberDashboardVO;
import com.brickfarm.vo.admin.kmh.AdminMemberVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerPaymentDataVO;
import com.brickfarm.vo.user.psj.ShoppingCartVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;

public interface MemberDAO {

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
		// 대시보드에 들어갈 통계
		AdminMemberDashboardVO selectMemberDashboardCount() throws Exception;
	
		// 최근 가입한 5인 정보 조회
		List<AdminMemberVO> selectRecentRegistMember() throws Exception;
	
		// 회원 정보 조회
		List<AdminMemberVO> selectMember(AdminMemberDTO tmpMember) throws Exception;

		// 회원 탈퇴
		int deleteMember(String member_id) throws Exception;

		// 휴먼회원 전환
		int updateInactiveStatus(List<Integer> member_no) throws Exception;
		
		// 회원 활성화 전환
		boolean updateInactiveStatus(String member_id) throws Exception;
		
		// 회원 등급 구하기
		List<AdminMemberVO> selectMemberGrade(String member_grade_name) throws Exception;

		// 생일인 사람 전화번호 구하기
		List<String> selectBirthDayMember(String mmDD)  throws Exception;
		
		// 6개월 내 구매 금액 기준 등급 업데이트
		int updateMemberGrade(List<AdminMemberVO> memberList, String gradeName) throws Exception;
	
		// 문자 보낼 고객의 전화번호 구하기
		List<String> selectMemberPhoneNumber(AdminMessageDTO tmpMessage) throws Exception;
		
		// 가입부터 주문까지 걸리는 시간 구하기
		AdminChartVO selectTimeToOrder() throws Exception;
		
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
		
		//회원가입시 아이디 중복검사
		String CheckMemberId(String member_id)throws Exception;

		//일반 유저 회원가입
		boolean registerMember(UserMemberVO memberVO)throws Exception;
		
		//일반 유저 로그인
		UserMemberVO selectUserInfo(UserMemberLoginDTO memberDTO)throws Exception;
		
		//유저의 아이디 찾기.
		UserMemberVO findUserId(String member_name, String phone_number)throws Exception;
		
		//유저의 비밀번호 찾기.
		UserMemberDTO finduserPwd(String member_name, String member_id, String phone_number)throws Exception;
		
		//비밀번호 변경
		int updateMemberPwd(String password, String member_id)throws Exception;
		
		//로그인 아이디 비밀번호 대조
		boolean login(String member_id, String password)throws Exception;
		
		//소셜 로그인 정보 db넣기
		boolean snsloginregister(Map<String, String> map)throws Exception;
		
		//소셜로그인 db 넣은 후 member_no값 가져오기.
		String selectSnsLoginInfo(String social_check)throws Exception;
		
		//소셜로그인 데이터가 있는지 확인
		UserMemberVO snsCheck(String social_check)throws Exception;
		
		//소셜로그인 회원의 추가데이터
		UserMemberVO addSnsLoginInfo(Map<String, String> loginInfo, int member_no)throws Exception;
		
		//sns로그인 id 중복체크
		boolean snsloginMemberidCheck(String member_id)throws Exception;
		
		//회원정보를 수정
		boolean ModifyMemberInfo(UserMemberVO memberVO)throws Exception;
		
		//회원가입후 회원번호랑 주소데이터 가져오기기
		UserMemberVO selectMemberAddr(UserMemberVO memberVO)throws Exception;
		
		//회원가입후 유저의 주소를 주소북에 insert
		void insertMemberAddress(UserMemberVO memberVO)throws Exception;
		
		//snsLogin 유저의 주소 주소북에 insert
		void insertSnsMemberAddr(Map<String, String> snsloginInfo)throws Exception;
		
		//회원정보 수정시 기존의 전화번호 가져오기
		String selectMemberPhoneNumber(int member_no)throws Exception;
		
		//no를 이용하여 장바구니 리스트 가져오기
		List<ShoppingCartVO> getShoppingList(int member_no)throws Exception;
		
		//로그인시 로그 기록 남겨주기
		boolean insertLoginLog(int member_no)throws Exception;
		
		//비밀번호 변경시 기존의 비밀번호 가져오기
		String selectBeforePwd(String member_id)throws Exception;
		
		//회원가입시 회원의 전화번호, 이메일 중복확인, 소셜확인 
		UserMemberDTO checkedmemberforregister(String member_name, String phone_number, String email)throws Exception;
		
		//sns 추가사항 입력시 전화번호 중복검사
		String findMemberPhoneInfo(String phone_number)throws Exception;
		
		//sns 로그인시에 이메일 정보가 있는지 확인
		boolean findMemberEmailBySocial(String email)throws Exception;
		
		//회원 탈퇴시에 비밀번호 대조
		boolean checkDeleteMemberPwd(UserMemberDTO memberDTO)throws Exception;
		
		// 회원 탈퇴
		boolean deleteMemberInfo(int member_no)throws Exception;
		
		//소셜로그인 유저 회원 탈퇴시 이메일 체크
		boolean checkDeleteSocialMemberEmail(Map<String, String> socialMembberInfo)throws Exception;

	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
		int updateMemberAccrualAmountByConfirmOrder(Map<String, Object> confirmOrderInfo) throws Exception;
		
		int updateMemberAccrualAmountByWriteReview(int memberNo) throws Exception;
		
		int updateMemberAccrualAmountByWritePhotoReview(int memberNo) throws Exception;
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
		// 회원 포인트 사용후 변경
		int updataMemberOnAccrualAmount(int member_no, int point_pay_money) throws Exception;
		// 포인트 적립후 변경
		int updateMemberOnAccumulate(String compareMerchantUid) throws Exception;
		// 보유 포인트 유효성검사
		int selectMemberVerifyPoint(int member_no) throws Exception;
		// 세션 정보로 멤버데이터 호출
		UserMemberVO getMember(int member_no) throws Exception;
		// 포인트 검증
		int verifyPoint(int member_no, int point_pay_money) throws Exception;
		// 쿠폰 검증
		int verifyCoupon(int member_no, int coupon_held_no) throws Exception;
		// 취소시 포인트 반환된값 업데이트
		int updateCancelPoint(AdminCancelDataDTO adminCancelData) throws Exception;
		// [스케줄러] 입금만료 취소시 포인트 업데이트
		int updateSchedulerPoint(AdminSchedulerPaymentDataVO schedulerPaymentData) throws Exception;
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
