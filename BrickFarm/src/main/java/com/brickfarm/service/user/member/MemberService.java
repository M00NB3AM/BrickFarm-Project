package com.brickfarm.service.user.member;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.brickfarm.dto.user.psj.UserShoppingCartDTO;
import com.brickfarm.dto.user.ysh.UserMemberDTO;
import com.brickfarm.dto.user.ysh.UserMemberLoginDTO;
import com.brickfarm.vo.user.lkm.UserLikeProductVO;
import com.brickfarm.vo.user.psj.ShoppingCartVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;

public interface MemberService {

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[염세환]==========================================================================================================================================
		String checkUserId(String member_id) throws Exception; // 회원 아이디 중복체크
		
		
		boolean registerMember(UserMemberVO memberVO)throws Exception; // 일반 회원가입
	// ==================================================================================================================================================

		UserMemberVO loginMember(UserMemberLoginDTO memberVO) throws Exception; // 일반 로그인


		UserMemberVO findUserId(String member_name, String phone_number)throws Exception ; // 회원 아이디 찾기


		UserMemberDTO finduserPwd(String member_name, String member_id, String phone_number)throws Exception; // 회원 비밀번호 찾기


		boolean updateUserPwd(String password, String member_id)throws Exception; // 비밀번호 변경


		boolean login(String member_id, String password)throws Exception; // 회원 로그인 아이디 비밀번호 대조.


		int snsloginRegister(Map<String, String> map, String social_check)throws Exception; //snslogin 회원가입

		
		UserMemberVO snsCheck(String social_check)throws Exception; // 소셜로그인 클릭시 데이터가 있는지 없는지 확인.


		boolean snsLoginMemberCheck(String member_id)throws Exception; //소셜로그인시 아이디 중복확인

		//소셜 로그인 멤버의 추가정보 insert
		UserMemberVO addSnsLoginInfo(int member_no, String member_id, String member_name, String phone_number, String zip_code,
				String address, String gender, String birth_date)throws Exception;

		//회원정보를 수정하는 메서드
		boolean ModifiMemberInfo(UserMemberVO memberVO)throws Exception;

		//회원정보를 수정할때 회원 전화번호 가져오기
		String getMemberPhoneNumber(int member_no)throws Exception;

		//회원no로 장바구니list가져오기
		List<ShoppingCartVO> getShoppingList(int member_no)throws Exception;

		//회원 로그인시 회원의 로그정보 입력
		boolean insertLoginLog(int member_no)throws Exception;

		//비밀번호 변경시 기존의 비밀번호와 같은지 확인
		String getbeforePwd(String member_id)throws Exception;

		//일반유저 회원가입시 중복확인
		UserMemberDTO checkedmemberforregister(String member_name, String phone_number, String email)throws Exception;

		//sns 로그인 시에 전화번호 중복검사
		String findMemberPhoneInfo(String phone_number)throws Exception;

		//sns 회원가입 진행 전에 데이터가 있다면 틩겨내기
		boolean findMemberEmailBySocial(String email)throws Exception;

		//회원 삭제 전에 회원의 password 조회하기
		boolean checkDeleteMemberPwd(String password, int member_no)throws Exception;
		
		//회원 삭제
		boolean deleteMember(String password, int member_no, String deletereason)throws Exception;		

		//sns로그인 시에 loginlog 남기기
		void snsLoginLog(int member_no)throws Exception;

		//sns 회원 탈퇴시 이메일 check
		boolean checkDeleteSocialMemberEmail(String email, int member_no)throws Exception;

		//로그인시에 유저의 장바구니 가져오기
		List<UserShoppingCartDTO> getHeaderShoppingList(int member_no)throws Exception;

		//로그인시에 유저의 찜목록 가져오기
		int getHeaderWishList(int member_no)throws Exception ;


	
	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
