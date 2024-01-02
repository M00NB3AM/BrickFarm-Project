package com.brickfarm.dao.loginlog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.kmh.AdminMemberDTO;
import com.brickfarm.etc.kmh.PaginationInfo;
import com.brickfarm.vo.admin.kmh.AdminLoginLogVO;

@Repository
public class LoginLogDAOImpl implements LoginLogDAO {
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.LoginLogMapper";

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================

	@Override
	public List<AdminLoginLogVO> selectLoginLog(AdminMemberDTO loginMember) {
		return ses.selectList(ns + ".findLoginLog", loginMember);
	}

	@Override
	public List<Integer> selectInactiveMemerInSixMonth() throws Exception {
		return ses.selectList(ns + ".findInactiveMemberInSixMonths");
	}

	@Override
	public int selectLoginLogCount(int member_no) throws Exception {

		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("member_no", member_no);
		return ses.selectOne(ns + ".findWithdrawMemberLoginLogCount", param);
	}

	@Override
	public List<AdminLoginLogVO> selectWithdrawMemberLoginLog(int member_no, PaginationInfo pi) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("member_no", member_no);
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowOfNums", pi.getRowOfNums());
		return ses.selectList(ns + ".selectWithdrawMemberLoginLog", params);
	}

	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================

	// 소셜 로그인 로그기록 남기기
	@Override
	public void insertSnsLoginLog(int member_no) {

		ses.insert(ns + ".insertsnsloginlog", member_no);
	}

	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
