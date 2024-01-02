package com.brickfarm.dao.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

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


@Repository
public class MemberDAOImpl implements MemberDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.MemberMapper";
	
	private static String addrbook = "com.brickfarm.mappers.MemberAddressBookMapper";
	
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	
	
	/**
	 * @methodName : selectMemberDashboardCount
	 * @author : kmh
	 * @return
	 * @returnValue : @return
	 * @date : 2023. 10. 7. 오후 4:58:36
	 * @description : 대시보드의 넣을 값 조회
	 */
	@Override
	public AdminMemberDashboardVO selectMemberDashboardCount() {
		return ses.selectOne(ns + ".countDashboard");
	}
	
	/**
	 * @methodName : selectRecentRegistMember
	 * @author : kmh
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 7. 오후 5:37:43
	 * @description : 최근 가입한 회원 조회
	 */
	@Override
	public List<AdminMemberVO> selectRecentRegistMember() throws Exception {
		return ses.selectList(ns + ".findRecentRegistMember");
	}
	
	/**
	 * @methodName : selectMember
	 * @author : kmh
	 * @param tmpMember
	 * @return
	 * @returnValue : @param tmpMember
	 * @returnValue : @return
	 * @date : 2023. 10. 7. 오후 4:23:21
	 * @description : 일반 회원 조회
	 */
	@Override
	public List<AdminMemberVO> selectMember(AdminMemberDTO tmpMember) {
		return ses.selectList(ns + ".findmember", tmpMember);
	}
	
	
	/**
	 * @methodName : deleteMember
	 * @author : kmh
	 * @param member_id
	 * @return
	 * @throws Exception
	 * @returnValue : @param member_id
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 16. 오후 7:36:30
	 * @description : 회원 탈퇴 트랜잭션(멤버테이블 회원 지우기)
	 */
	@Override
	public int deleteMember(String member_id) throws Exception {
		
		return ses.delete(ns + ".deleteMember", member_id);
	}
	

	/**
	 * @methodName : updateInactiveStatus
	 * @author : kmh
	 * @param member_no, status
	 * @return
	 * @returnValue : @param member_no
	 * @returnValue : @return
	 * @date : 2023. 11. 5. 오후 6:58:12
	 * @description : 멤버테이블 inactive_status 변환 Y or N (일괄처리)
	 */
	@Override
	public int updateInactiveStatus(List<Integer> member_no) throws Exception{
		return ses.update(ns + ".updateInactiveStatus", member_no);
	}
	
	/**
	 * @methodName : updateInactiveStatus
	 * @author : kmh
	 * @param member_id, status
	 * @param status
	 * @return
	 * @returnValue : @param member_id
	 * @returnValue : @param status
	 * @returnValue : @return
	 * @date : 2023. 11. 7. 오후 6:05:12
	 * @description : 멤버테이블 inactive_status 변환 Y or N (한 아이디 대상)
	 */
	@Override
	public boolean updateInactiveStatus(String member_id) throws Exception{
		boolean result = false;

		if(ses.update(ns + ".updateActiveStatus", member_id) == 1) {
			result = true;
		}
		return result; 
	}
	
	
	/**
	 * @methodName : selectMemberGrade
	 * @author : kmh
	 * @param string
	 * @return
	 * @throws Exception
	 * @returnValue : @param string
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 9. 오후 4:10:08
	 * @description : 6개월 내 구매 금액 기준 회원 찾기
	 */
	@Override
	public List<AdminMemberVO> selectMemberGrade(String member_grade_name) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("member_grade_name", member_grade_name);
		
		return ses.selectList(ns + ".findMemberNoPerSixMonths", params);
	}
	

	/**
	 * @methodName : updateMemberGrade
	 * @author : kmh
	 * @param gradeName
	 * @return
	 * @throws Exception
	 * @returnValue : @param gradeName
	 * @returnValue : @param string
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 9. 오후 4:14:51
	 * @description : 6개월 내 구매 금액 기준 회원 등급 업데이트
	 */
	@Override
	public int updateMemberGrade(List<AdminMemberVO> memberList, String gradeName) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberList", memberList);
		params.put("gradeName", gradeName);
		return ses.update(ns + ".updateMemberGradePerSixMonths", params);
	}

	

	/**
	 * @methodName : selectBirthDayMember
	 * @author : kmh
	 * @param mmDD
	 * @return
	 * @throws Exception
	 * @returnValue : @param mmDD
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 10. 오후 3:43:30
	 * @description : 생일인 사람 전화번호 구하기 
	 */
	@Override
	public List<String> selectBirthDayMember(String mmDD) throws Exception {
		return ses.selectList(ns + ".findBirthdayMember", mmDD);
	}
	
	/**
	 * @methodName : selectMemberPhoneNumber
	 * @author : kmh
	 * @param tmpMessage
	 * @return
	 * @throws Exception
	 * @returnValue : @param tmpMessage
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 20. 오후 4:06:24
	 * @description : 회원 전화번호 구하기
	 */
	@Override
	public List<String> selectMemberPhoneNumber(AdminMessageDTO tmpMessage) throws Exception {
		return ses.selectList(ns + ".selectPhoneNumberFromSelectedMembers", tmpMessage);
	}
	
	/**
	 * @methodName : selectTimeToOrder
	 * @author : kmh
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 28. 오후 6:36:11
	 * @description : 가입부터 주문까지 걸리는 시간 구하기
	 */
	@Override
	public AdminChartVO selectTimeToOrder() throws Exception {
		return ses.selectOne(ns + ".findTakeTimeToOrder");
	}

	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	@Override
	public String CheckMemberId(String member_id)throws Exception {
		
		
		return ses.selectOne(ns +  ".checkUserId", member_id);
	}
	
	//일반유저 회원가입
	@Override
	public boolean registerMember(UserMemberVO memberVO)throws Exception {
		boolean result = false;
		if(ses.insert(ns + ".registerMember", memberVO) == 1) {
			result = true;
		}
		return result;
	}
	
	//일반 유저 로그인
	@Override
	public UserMemberVO selectUserInfo(UserMemberLoginDTO memberDTO) throws Exception{
			
		return ses.selectOne(ns + ".selectUserInfo",memberDTO);
		
	}
	
	//유저 아이디 찾기
	@Override
	public UserMemberVO findUserId(String member_name, String phone_number) throws Exception{
		UserMemberVO userMemberVO = new UserMemberVO(member_name, phone_number);
		return ses.selectOne(ns + ".findMemberId" ,userMemberVO);
	}
	
	//비밀번호 찾기
	@Override
	public UserMemberDTO finduserPwd(String member_name, String member_id, String phone_number) throws Exception{
		
		UserMemberDTO tmpmemberDTO = new UserMemberDTO(member_name, member_id, phone_number);
		UserMemberDTO memberDTO = null;
		if(ses.selectOne(ns + ".findMemberPwd", tmpmemberDTO) != null) {
			UserMemberVO memberVO = ses.selectOne(ns + ".findMemberPwd", tmpmemberDTO);
			String tmpmember_name = memberVO.getMember_name();
			
			String tmp_member_id = memberVO.getMember_id();
			
			String tmp_phone_number = memberVO.getPhone_number();
			
			String tmpsocial_check = memberVO.getSocial_check();
			
			memberDTO = new UserMemberDTO();
			memberDTO.setMember_name(tmpmember_name);
			memberDTO.setMember_id(tmp_member_id);
			memberDTO.setPhone_number(tmp_phone_number);
			memberDTO.setSocial_check(tmpsocial_check);
			return memberDTO; // 값이 있다면.	
		}else { // 값이 없다면.
			return memberDTO; //null
		}
	}
	//비밀번호 변경
	@Override
	public int updateMemberPwd(String password, String member_id) throws Exception{
		UserMemberLoginDTO memberDTO = new UserMemberLoginDTO(member_id, password);
		return ses.update(ns + ".updateMemberPwd", memberDTO);
		
	}
	//로그인 아이디 비밀번호 대조
	@Override
	public boolean login(String member_id, String password) throws Exception{
		boolean result = false;
		UserMemberLoginDTO memberDTO = new UserMemberLoginDTO(member_id, password);
		
		if(ses.selectOne(ns + ".login", memberDTO) != null) {
			result = true;
		}
		return result;
	}
	//sns 로그인한 정보 db에 넣어주기.
	@Override
	public boolean snsloginregister(Map<String, String> map) throws Exception{
			boolean result = false;
			if(ses.insert(ns + ".snsloginregister" , map) == 1) {
				result = true;
			}
			
		return result;
	}
	//snsDB 넣은 후 no값 가져오기
	@Override
	public String selectSnsLoginInfo(String social_check) throws Exception{
		
		return ses.selectOne(ns + ".selectsnsno" ,social_check);
	}
	//소셜로그인 데이터가 있는지 확인
	@Override
	public UserMemberVO snsCheck(String social_check) {
			
		return ses.selectOne(ns + ".snscheckinfo", social_check);
	}
	//sns로그인 아이디 중복확인
	@Override
	public boolean snsloginMemberidCheck(String member_id)throws Exception { 
		boolean result = false;
		
		if(ses.selectOne(ns + ".snsloginmembercheck", member_id) != null) {
			result = true;
		}else {
			result = false;
		}
		
			return result;
	}
	//sns로그인 회원의 추가정보
	@Override
	public UserMemberVO addSnsLoginInfo(Map<String, String> loginInfo, int member_no) {
		UserMemberVO snsloginVO = new UserMemberVO();
		
			if(ses.update(ns + ".addSnslogininfo", loginInfo) == 1) {
				snsloginVO = ses.selectOne(ns + ".selectsnsloginforsession", loginInfo);
			}
		return snsloginVO;
		
	}
	
	//회원 정보 수정
	@Override
	public boolean ModifyMemberInfo(UserMemberVO memberVO)throws Exception {
		boolean result = false;
		String list = "";
		ses.selectList(list);
		
		
		
		if(ses.update(ns + ".modifymemberinfo", memberVO) == 1) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}
	//회원가입후 회원번호와 주소 가져오기
	@Override
	public UserMemberVO selectMemberAddr(UserMemberVO memberVO) throws Exception{
		UserMemberVO tmpMemberVO = ses.selectOne(ns + ".selectmemberaddr", memberVO);
		
		return tmpMemberVO;
	}

	
	//일반 로그인 후 회원가입 후 유저의 주소를 주소북에 insert
	@Override
	public void insertMemberAddress(UserMemberVO memberVO)throws Exception {
		ses.insert(addrbook + ".insertaddrbook", memberVO);
	}
	
	//Sns일반유저의 주소를 주소북에 insert
	@Override
	public void insertSnsMemberAddr(Map<String, String> snsloginInfo) throws Exception{
		
		ses.insert(addrbook + ".insertsnsaddrbook", snsloginInfo);
		
	}
	
	//회원정보 수정시 회원의 전화번호 가져오기
	@Override
	public String selectMemberPhoneNumber(int member_no) throws Exception{
		
		return ses.selectOne(ns + ".selectmemberphonenumber", member_no);
	}
	
	// member_no를 이용하여 장바구니 리스트 가져오기.
	@Override
	public List<ShoppingCartVO> getShoppingList(int member_no) throws Exception{

		return ses.selectList(ns + ".getshoppinglist", member_no);
	}
	
	//로그인시에 로그기록 남겨주기
	@Override
	public boolean insertLoginLog(int member_no) throws Exception{
			boolean result = false;
		if(ses.insert(ns + ".insertloginlog", member_no)==1) {
			result = true;
		}else {
			result = false;
		}
		return result;
		
	}
	//비밀번호 변경시 기존의 비밀번호 가져오기
	@Override
	public String selectBeforePwd(String member_id) throws Exception{

		return ses.selectOne(ns + ".selectbeforepwd", member_id);
	}
	
	//회원 전화번호, 이메일 중복확인 및 소셜로그인 확인
	@Override
	public UserMemberDTO checkedmemberforregister(String member_name, String phone_number, String email)throws Exception {
		UserMemberDTO memberDTO = new UserMemberDTO();
		
		String name = "";
		
		String phone ="";

		String result1 = ses.selectOne(ns + ".findphonenumber", phone_number);
		String result2 = ses.selectOne(ns + ".findemail", email);
		String result3 = ses.selectOne(ns + ".findsocial", phone_number);
		
		if(ses.selectOne(ns + ".findphonenumber", phone_number) == null) {
			phone = null;
			memberDTO.setPhone_number(phone);
		}else if(ses.selectOne(ns + ".findphonenumber", phone_number) != null) {
			phone = ses.selectOne(ns + ".findphonenumber", phone_number);
			memberDTO.setPhone_number(phone);
		}
		
		String mail ="";
		if(ses.selectOne(ns + ".findemail", email) == null) {
			mail = null;
			memberDTO.setEmail(mail);
		}else if(ses.selectOne(ns + ".findemail", email) != null) {
			mail = ses.selectOne(ns + ".findemail", email);
			memberDTO.setEmail(mail);
		}
		
		String social = "";
		if(ses.selectOne(ns + ".findsocial", phone_number) == null) {
			social = null;
			memberDTO.setSocial_check(social);
		}else if(ses.selectOne(ns + ".findsocial", phone_number) != null) {
			social = ses.selectOne(ns + ".findsocial", phone_number);
			memberDTO.setSocial_check(social);
		}
		
		return memberDTO;
	}
	
	//전화번호 중복검사
	@Override
	public String findMemberPhoneInfo(String phone_number) throws Exception{
		
		return ses.selectOne(ns + ".findmemberphoneinfo", phone_number);
	}
	
	// sns로그인시에 기존의 이메일이 있는지 확인
	@Override
	public boolean findMemberEmailBySocial(String email)throws Exception{
		boolean result = false;
		
		if(ses.selectOne(ns + ".findmemberemail",email) != null) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}
	
	//회원탈퇴시 비밀번호 비교
	@Override
	public boolean checkDeleteMemberPwd(UserMemberDTO memberDTO) throws Exception{
		boolean result = false;
		String pwd = ses.selectOne(ns + ".checkdeletememberpwd", memberDTO);
		System.out.println(pwd);
		
		if(ses.selectOne(ns + ".checkdeletememberpwd", memberDTO) != null) {
			
			result = true; // 비밀번호가 나온다면
		}else {
			result = false; // 비밀번호가 없다면
		}
		return result;
		
	}
	
	// 회원 탈퇴 
	@Override
	public boolean deleteMemberInfo(int member_no) throws Exception{
		boolean result = false;
		
		if(ses.delete(ns + ".deletememberinfo", member_no) == 1) {//삭제 됐다면..
			result = true;
		}else {
			result = false;
		}
		return result;
	}
	
	//소셜로그인 회원 탈퇴시 email 비교
	@Override
	public boolean checkDeleteSocialMemberEmail(Map<String, String> socialMembberInfo)throws Exception {
		boolean result = false;
		if(ses.selectOne(ns + ".checkdeletesocilmemberemail", socialMembberInfo) != null) {
			
			result = true; // 이메일이 나온다면
		}else {
			result = false; // 이메일이 없다면
		}
		
		return result;
	}

	
	
	
	
	

	// ==================================================================================================================================================
	// ==[박상진]==========================================================================================================================================
		@Override
		public int updateMemberAccrualAmountByConfirmOrder(Map<String, Object> confirmOrderInfo) throws Exception {
			int member_no = (int)confirmOrderInfo.get("loginMemberNo");
			return ses.update(ns+".updateMemberAccrualAmountByConfirmOrder",member_no );
		}
		

		@Override
		public int updateMemberAccrualAmountByWriteReview(int memberNo) throws Exception {
			return ses.update(ns+".updateMemberAccrualAmountByWriteReview", memberNo);
		}

		@Override
		public int updateMemberAccrualAmountByWritePhotoReview(int memberNo) throws Exception {
			return ses.update(ns+".updateMemberAccrualAmountByWritePhotoReview", memberNo);
		}
	
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================

	@Override
	public int updataMemberOnAccrualAmount(int member_no, int point_pay_money) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("member_no", member_no);
		param.put("point_pay_money", point_pay_money);
		
		return ses.update(ns+".updataMemberOnAccrualAmount", param);
	}
	
	@Override
	public int updateMemberOnAccumulate(String compareMerchantUid) throws Exception {
		return ses.update(ns+".updateMemberOnAccumulate", compareMerchantUid);
	}
	
	/**
	 * @methodName : selectMemberVerifyPoint
	 * @author : syt
	 * @param i
	 * @return
	 * @returnValue : @param i
	 * @returnValue : @return
	 * @date : 2023. 11. 7. 오후 9:04:44
	 * @description : 포인트 사용 유효성검사
	 */
	@Override
	public int selectMemberVerifyPoint(int member_no) throws Exception {
		return ses.selectOne(ns + ".selectMemberVerifyPoint", member_no);
	}
	
	/**
	 * @methodName : getMember
	 * @author : syt
	 * @param member_no
	 * @return
	 * @returnValue : @param member_no
	 * @returnValue : @return
	 * @date : 2023. 11. 8. 오후 7:20:50
	 * @description : 세션 정보로 멤버데이터 호출
	 */
	@Override
	public UserMemberVO getMember(int member_no) throws Exception {
		return ses.selectOne(ns + ".getMember", member_no);
	}
	
	/**
	 * @methodName : verifyPointCoupon
	 * @author : syt
	 * @param member_id
	 * @param point_pay_money
	 * @param coupon_held_no
	 * @return
	 * @returnValue : @param member_id
	 * @returnValue : @param point_pay_money
	 * @returnValue : @param coupon_held_no
	 * @returnValue : @return
	 * @date : 2023. 11. 8. 오후 8:06:14
	 * @description : 포인트 검증
	 */
	@Override
	public int verifyPoint(int member_no, int point_pay_money) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("member_no", member_no);
		param.put("point_pay_money", point_pay_money);
		return ses.selectOne(ns + ".verifyPoint", param); 
	}
	
	/**
	 * @methodName : verifyCoupon
	 * @author : syt
	 * @param member_no
	 * @param coupon_held_no
	 * @return
	 * @returnValue : @param member_no
	 * @returnValue : @param coupon_held_no
	 * @returnValue : @return
	 * @date : 2023. 11. 8. 오후 8:06:14
	 * @description : 쿠폰 검증
	 */
	@Override
	public int verifyCoupon(int member_no, int coupon_held_no) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("member_no", member_no);
		param.put("coupon_held_no", coupon_held_no);
		return ses.selectOne(ns + ".verifyCoupon", param); 
	}

	/**
	 * @methodName : updateCancelPoint
	 * @author : syt
	 * @param adminCancelData
	 * @return
	 * @returnValue : @param adminCancelData
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 12:39:22
	 * @description : 취소시 포인트 반환된값 업데이트
	 */
	@Override
	public int updateCancelPoint(AdminCancelDataDTO adminCancelData) throws Exception {
		return ses.update(ns+".updateCancelPoint", adminCancelData);
	}

	/**
	 * @methodName : updateSchedulerPoint
	 * @author : syt
	 * @param adminSchedulerDataVO
	 * @return
	 * @returnValue : @param adminSchedulerDataVO
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 3:27:34
	 * @description : [스케줄러] 입금만료 취소시 포인트 업데이트
	 */
	@Override
	public int updateSchedulerPoint(AdminSchedulerPaymentDataVO schedulerPaymentData) throws Exception {
		return ses.update(ns+".updateSchedulerPoint", schedulerPaymentData);
	}

	
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
