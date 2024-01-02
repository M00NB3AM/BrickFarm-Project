package com.brickfarm.service.user.member;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.brickfarm.dao.loginlog.LoginLogDAO;
import com.brickfarm.dao.member.MemberDAO;
import com.brickfarm.dao.ordersheet.OrderSheetDAO;
import com.brickfarm.dao.productwishlist.ProductWishListDAO;
import com.brickfarm.dao.shoppingcart.ShoppingCartDAO;
import com.brickfarm.dao.withdrawmember.WithdrawMemberDAO;
import com.brickfarm.dto.admin.kmh.AdminWithdrawMemberDTO;
import com.brickfarm.dto.user.psj.UserShoppingCartDTO;
import com.brickfarm.dto.user.ysh.UserMemberDTO;
import com.brickfarm.dto.user.ysh.UserMemberLoginDTO;
import com.brickfarm.vo.admin.kmh.AdminWithdrawMemberVO;
import com.brickfarm.vo.user.lkm.UserLikeProductVO;
import com.brickfarm.vo.user.psj.ShoppingCartVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Inject
	private MemberDAO mDao;
	
	@Inject
	private WithdrawMemberDAO wmDao;
	
	@Inject
	private OrderSheetDAO oDao;
	
	@Inject
	private ProductWishListDAO wishDao;
	
	@Inject
	private ShoppingCartDAO shoppingDao;
	
	@Inject
	private LoginLogDAO lDao;

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// 회원가입시 id 중복체크
	@Override
	public String checkUserId(String member_id) throws Exception{
		String result = mDao.CheckMemberId(member_id);

		return result;

	}

	// 일반 회원가입
	@Override
	public boolean registerMember(UserMemberVO memberVO) throws Exception{
		boolean result = false;
		
//		String tmpPhone = memberVO.getPhone_number();
//		String tmpPhone1 = tmpPhone.substring(0, 3); // 010
//		String tmpPhone2 = tmpPhone.substring(3,7); // 가운대 4자리
//		String tmpPhone3 = tmpPhone.substring(7);// 뒷자리 4자리
//		String phone_number = tmpPhone1 + "-" + tmpPhone2 + "-" + tmpPhone3;
//		memberVO.setPhone_number(phone_number);
		memberVO.setMember_grade_name("일반");
		memberVO.setInactive_status('N');
		System.out.println(memberVO);
		if (mDao.registerMember(memberVO)) {
			UserMemberVO tmpMemberVO = mDao.selectMemberAddr(memberVO);
			System.out.println("76번째줄 : " + tmpMemberVO);
			mDao.insertMemberAddress(tmpMemberVO);
			result = true;
		}
		return result;
	}

	// 일반 유저 로그인
	@Override
	public UserMemberVO loginMember(UserMemberLoginDTO memberDTO) throws Exception{
		UserMemberVO member = mDao.selectUserInfo(memberDTO);
		member.setPassword("");
		return member;
	}

	//회원의 아이디 찾기.
	@Override
	public UserMemberVO findUserId(String member_name, String tmpPhone_number) throws Exception{
		String tmpPhone1 = tmpPhone_number.substring(0, 3); // 010
		String tmpPhone2 = tmpPhone_number.substring(3,7); // 가운대 4자리
		String tmpPhone3 = tmpPhone_number.substring(7);// 뒷자리 4자리
		String phone_number = tmpPhone1 + "-" + tmpPhone2 + "-" + tmpPhone3;
		System.out.println("serviceimpl phone : " + phone_number);
		
		UserMemberVO memberVO = mDao.findUserId(member_name, phone_number);
		System.out.println("serviceimpl : "+memberVO);
		
		return memberVO;
	}

	//회원의 비밀번호 찾기.
	@Override
	public UserMemberDTO finduserPwd(String member_name, String member_id, String tmpPhone_number) throws Exception{
		boolean result = false;
		String tmpPhone1 = tmpPhone_number.substring(0, 3); // 010
		String tmpPhone2 = tmpPhone_number.substring(3,7); // 가운대 4자리
		String tmpPhone3 = tmpPhone_number.substring(7);// 뒷자리 4자리
		String phone_number = tmpPhone1 + "-" + tmpPhone2 + "-" + tmpPhone3;
		System.out.println("serviceimpl phone : " + phone_number);
		UserMemberDTO memberInfo = new UserMemberDTO();
		if(mDao.finduserPwd(member_name, member_id, phone_number) != null) {
			 memberInfo = mDao.finduserPwd(member_name, member_id, phone_number);		
			 System.out.println("serviceimpl phone"+memberInfo);
			return memberInfo; // 값이 있음
		}else {
			return memberInfo; // null
		}
	}
	//비밀번호 변경
	@Override
	public boolean updateUserPwd(String password, String member_id) throws Exception{
		boolean result = false;
		
		if(mDao.updateMemberPwd(password, member_id) == 1) {
			return result = true;
		}
		return result;
		
	}
	
	//로그인 아이디 비밀번호 대조.
	@Override
	public boolean login(String member_id, String password)throws Exception {
		boolean result = false;
		if(mDao.login(member_id, password)) {
			System.out.println("MEMBERSERVICEIMPL");
			result = true;
		}
		return result;
		
	}
	//snslogin db데이터 입력
	@Override
	public int snsloginRegister(Map<String, String> map, String social_check) throws Exception{	
		String tmpmember_no = "";
		if(mDao.snsloginregister(map) == true) {
			tmpmember_no =  mDao.selectSnsLoginInfo(social_check);
		}
		int member_no =  Integer.parseInt(tmpmember_no);
		return member_no;
	}
	//snslogin시 데이터 있는지 확인
	@Override
	public UserMemberVO snsCheck(String social_check) throws Exception{
		UserMemberVO memberVO = mDao.snsCheck(social_check);   
		return  memberVO;
	}
	//snslogin 아이디 중복확인
	@Override
	public boolean snsLoginMemberCheck(String member_id)throws Exception {
		boolean result = false;

		if(mDao.snsloginMemberidCheck(member_id)) {
			result = true;
		}
		return result;
	}

	//sns 로그인시 추가로 받은 데이터
	@Override
	public UserMemberVO addSnsLoginInfo(int member_no, String member_id, String member_name, String phone_number,
			String zip_code, String address, String gender, String birth_date)throws Exception {
			Map<String, String> snsloginInfo = new HashMap<String, String>();
			snsloginInfo.put("member_id", member_id);
			snsloginInfo.put("member_name", member_name);
//			String tmpPhone1 = tmpPhone_number.substring(0, 3); // 010
//			String tmpPhone2 = tmpPhone_number.substring(3,7); // 가운대 4자리
//			String tmpPhone3 = tmpPhone_number.substring(7);// 뒷자리 4자리
//			String phone_number = tmpPhone1 + "-" + tmpPhone2 + "-" + tmpPhone3;
			snsloginInfo.put("phone_number", phone_number);
			snsloginInfo.put("zip_code", zip_code);
			snsloginInfo.put("address", address);
			snsloginInfo.put("member_no", String.valueOf(member_no));
			snsloginInfo.put("gender", gender);
			snsloginInfo.put("birth_date", birth_date);
		UserMemberVO memberVO = new UserMemberVO();
		memberVO = mDao.addSnsLoginInfo(snsloginInfo, member_no);
		if(memberVO != null) {
			System.out.println("194번줄 : " + snsloginInfo);
			mDao.insertSnsMemberAddr(snsloginInfo);
		}
		return memberVO;
	}
	//회원정보 수정
	@Override
	public boolean ModifiMemberInfo(UserMemberVO memberVO) throws Exception{
		boolean result = false;
		if(mDao.ModifyMemberInfo(memberVO)) {
				result = true;
			}else {
				result = false;
			}
		return result;
	}

	//회원정보 수정시 전화번호가져오기
	@Override
	public String getMemberPhoneNumber(int member_no) throws Exception{
		
		return mDao.selectMemberPhoneNumber(member_no);
	}

	//회원 no로 회원의 장바구니 list 가져오기
	@Override
	public List<ShoppingCartVO> getShoppingList(int member_no) throws Exception{

		return mDao.getShoppingList(member_no);
	}

	//로그인시 로그 기록 남겨주기
	@Override
	public boolean insertLoginLog(int member_no) throws Exception{
		boolean result = false;
		if(mDao.insertLoginLog(member_no)) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}

	//비밀번호 변경시 기존의 비밀번호 가져오기
	@Override
	public String getbeforePwd(String member_id) throws Exception{
		
		return mDao.selectBeforePwd(member_id);
	}
	// 회원가입시 이메일, 전화번호 중복확인
	@Override
	public UserMemberDTO checkedmemberforregister(String member_name, String tmpPhone_number, String email) throws Exception{
//		String tmpPhone1 = tmpPhone_number.substring(0, 3); // 010
//		String tmpPhone2 = tmpPhone_number.substring(3,7); // 가운대 4자리
//		String tmpPhone3 = tmpPhone_number.substring(7);// 뒷자리 4자리
//		String phone_number = tmpPhone1 + "-" + tmpPhone2 + "-" + tmpPhone3;
		
		
		return mDao.checkedmemberforregister(member_name, tmpPhone_number, email);
	}

	//sns 추가정보 입력시 전화번호 중복검사
	@Override
	public String findMemberPhoneInfo(String phone_number) throws Exception {
		
		
		return mDao.findMemberPhoneInfo(phone_number);
	}
	// sns로그인시 중복되는 이메일이 있는지 확인
	@Override
	public boolean findMemberEmailBySocial(String email) throws Exception {
		
		return mDao.findMemberEmailBySocial(email);
	}
	//회원 탈퇴시 비밀번호 비교
	@Override
	public boolean checkDeleteMemberPwd(String password, int member_no) throws Exception {
		boolean result = false;
		UserMemberDTO memberDTO = new UserMemberDTO(member_no, password);
		memberDTO.setMember_no(member_no);
		memberDTO.setPassword(password);
		System.out.println(memberDTO);
		
		if(mDao.checkDeleteMemberPwd(memberDTO)) {
			result = true;
		}else {
			result = false;
		}
		return result;
		
		
	}
	//회원 탈퇴 처리
	@Override
	public boolean deleteMember(String password, int member_no, String reason_memo) throws Exception{

		boolean result =false;
		//1.회원 테이블에서 삭제 전에 회원의 정보를 삭제테이블에 insert 후 돌아와서 삭제하기.
		AdminWithdrawMemberDTO withMemberDTO = new AdminWithdrawMemberDTO();
		withMemberDTO.setMember_no(member_no);
		withMemberDTO.setReason_memo(reason_memo);
		withMemberDTO.setWithdraw_reason("자진탈퇴");
		
		System.out.println("회원 탈퇴처리 : " + withMemberDTO);

		//탈퇴테이블에 insert
		AdminWithdrawMemberDTO withdrawMember = wmDao.insertDeleteMember(withMemberDTO); // 탈퇴 테이블에 insert 완료.
		//orderSheet에 넣어주기
		oDao.insertWithdrawMember(withdrawMember); //완료 되었다면
			if(mDao.deleteMemberInfo(member_no)) {	//탈퇴한 회원 member 테이블에서 delete
				result = true;
			}else {
				result = false;
			}	
		return result;
	}

	//sns 회원가입 주소록에 넣기
//	@Override
//	public void insertSnsMemberAddress(UserMemberVO tmpMemberVO) throws Exception {
//		System.out.println("314번째줄");
//		mDao.insertMemberAddress(tmpMemberVO);
//		
//	}

	
	// sns 로그인 시에 로그인 로그 남겨주기
	@Override
	public void snsLoginLog(int member_no) throws Exception{
		
		lDao.insertSnsLoginLog(member_no);
	}
	// sns 회원 탈퇴시 이메일 비교
	@Override
	public boolean checkDeleteSocialMemberEmail(String email, int member_no) throws Exception{
		boolean result = false;	
		
		Map<String, String> socialMembberInfo = new HashMap<String, String>();
		socialMembberInfo.put("member_no",String.valueOf(member_no));
		socialMembberInfo.put("email",email);
		
		
		if(mDao.checkDeleteSocialMemberEmail(socialMembberInfo)){
			result = true;
		}else {
			result = false;
		}
		
		
		return result;
	}
	//로그인시에 유저의 장바구니 가져오기.
	@Override
	public List<UserShoppingCartDTO> getHeaderShoppingList(int member_no) throws Exception {
		
		List<UserShoppingCartDTO> shoppingCartList = shoppingDao.selectShoppingCartList(member_no);
		System.out.println("MemberService의" + shoppingCartList);
		
		
		return shoppingCartList;
	}
	// 로그인시에 유저의 찜목록 가져오기
	@Override
	public int getHeaderWishList(int member_no) throws Exception {
		
		List<UserLikeProductVO> list = wishDao.getHeaderWishList(member_no);
		int count = list.size();
		
		return count;
	}
	
	

	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
