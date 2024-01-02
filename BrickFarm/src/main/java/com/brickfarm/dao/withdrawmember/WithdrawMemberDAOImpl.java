package com.brickfarm.dao.withdrawmember;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.kmh.AdminNoActiveMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminWithdrawMemberDTO;
import com.brickfarm.etc.kmh.PaginationInfo;
import com.brickfarm.vo.admin.kmh.AdminMemberOrderVO;
import com.brickfarm.vo.admin.kmh.AdminWithdrawMemberVO;

@Repository
public class WithdrawMemberDAOImpl implements WithdrawMemberDAO {

	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.WithdrawMemberMapper";
	
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
		

		@Override
		public List<AdminWithdrawMemberVO> selectWithdrawMember(AdminNoActiveMemberDTO tmpMember) throws Exception {
			
			return ses.selectList(ns + ".findWithdrawMember", tmpMember);
		}

		@Override
		public int insertWithdrawMember(AdminWithdrawMemberDTO tmpdelMember) throws Exception {
			return ses.insert(ns + ".insertWithdrawMember", tmpdelMember);
		}

		@Override
		public List<AdminMemberOrderVO> selectMemberOrderList(int member_no, PaginationInfo pi) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("member_no", member_no);
			params.put("startRowIndex", pi.getStartRowIndex());
			params.put("rowOfNums", pi.getRowOfNums());
			
			return ses.selectList(ns + ".findMemberOrderList", params);
		}
		
		
		
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[염세환]==========================================================================================================================================
		
		//회원탈퇴
		@Override
		public AdminWithdrawMemberDTO insertDeleteMember(AdminWithdrawMemberDTO withdrawMemberVO) {
			ses.insert(ns + ".deleteMemberInsert", withdrawMemberVO);
			withdrawMemberVO.getWithdraw_member_no();
			
			return withdrawMemberVO;
		}
		
	// ==================================================================================================================================================
	
	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
