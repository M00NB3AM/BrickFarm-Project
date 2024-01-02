package com.brickfarm.dao.mypage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.user.psj.UserWithdrawalApplyDTO;
import com.brickfarm.dto.user.psj.UserOrderListDTO;
import com.brickfarm.dto.user.psj.UserOrderWithdrawalDTO;
import com.brickfarm.dto.user.psj.UserOrderWithdrawalListDTO;
import com.brickfarm.etc.psj.PaginationInfo;
import com.brickfarm.etc.psj.SearchCriteria;
import com.brickfarm.vo.user.ysh.UserMemberVO;

@Repository
public class MyPageDAOImpl implements MyPageDAO {
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.MyPageMapper";
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================	
	// ====================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	@Override
	public UserMemberVO selectMemberInfo(int tmpmember_no) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loginMemberno", tmpmember_no);
		UserMemberVO loginMemberInfo = ses.selectOne(ns + ".selectMyPage", param);
		int member_no = loginMemberInfo.getMember_no();
		UserMemberVO loginMemberCountInfo = ses.selectOne(ns + ".selectLoginMemberCntInfo", member_no);
		loginMemberInfo.setCoupon_count(loginMemberCountInfo.getCoupon_count());
		loginMemberInfo.setOrder_count(loginMemberCountInfo.getOrder_count());
		loginMemberInfo.setWish_list_count(loginMemberCountInfo.getWish_list_count());
		loginMemberInfo.setCancel_count(loginMemberCountInfo.getCancel_count());
		
		return loginMemberInfo;
	}
	@Override
	public List<UserOrderListDTO> selectOrderList(PaginationInfo pi, int member_no, SearchCriteria sc) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("member_no", member_no);
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		params.put("searchState", sc.getSearchState());
		params.put("searchPeriod", sc.getSearchPeriod());
		return ses.selectList(ns + ".selectOrderList", params);
	}

	@Override
	public List<UserOrderWithdrawalListDTO> selectOrderWithDrawalList(int member_no, PaginationInfo pi, SearchCriteria sc) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		System.out.println(sc.getSearchState());
		params.put("member_no", member_no);
		params.put("startRowIndex", pi.getStartRowIndex());
		params.put("rowCountPerPage", pi.getRowCountPerPage());
		params.put("searchState", sc.getSearchState());
		return ses.selectList(ns + ".selectOrderWithdrawalList", params);
	}

	@Override
	public UserOrderWithdrawalDTO selectUserOrderWithdrawal(int member_no, int detailed_order_no) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("member_no", member_no);
		param.put("detailed_order_no", detailed_order_no);
		return ses.selectOne(ns + ".selectOrderWithdrawal", param);
	}

	@Override
	public int updateMemberPoint(Map<String, Object> confirmOrderInfo) throws Exception {
		
		return ses.update(ns + ".updateMemberPoint", confirmOrderInfo);
	}

	@Override
	public UserWithdrawalApplyDTO selectUserWithDrawalApplyDTO(int detailed_order_no) throws Exception {
		return ses.selectOne(ns + ".selectUserWithdrawalApplyDTO" , detailed_order_no);
	}

	@Override
	public int selectAllOrderListCount(int member_no ,SearchCriteria sc) throws Exception {
		int result = -1;
		Map<String, Object> params = new HashMap<String, Object>();
		if(sc.getSearchPeriod().equals("") && sc.getSearchState().equals("")) {
			result = ses.selectOne(ns + ".selectAllOrderListCount", member_no);
			System.out.println("전체 수 :" +  result);
		} else {
			String searchState = sc.getSearchState();
			String searchPeriod = sc.getSearchPeriod();
			params.put("member_no", member_no);
			params.put("searchState", searchState);
			params.put("searchPeriod", searchPeriod);
			result = ses.selectOne(ns + ".selectAllOrderListCountByCondition", params);
			System.out.println("전체 수 :" +  result);
		}
		return result;
	}

	@Override
	public int selectAllCancelAndExchangeListCount(int member_no, SearchCriteria sc) throws Exception {
		int result = -1;
		Map<String, Object> params = new HashMap<String, Object>();
		if(sc.getSearchState().equals("")) {
			result = ses.selectOne(ns + ".selectAllCancelAndExchangeListCount", member_no);
			
		} else {
			String searchState = sc.getSearchState();
			params.put("member_no", member_no);
			params.put("searchState", searchState);
			result = ses.selectOne(ns + ".selectAllCancelAndExchangeListCountByCondition", params);
		}
		return result;
	}
	@Override
	public int selectAllCouponListCount(int member_no, SearchCriteria sc) throws Exception {
		int result = -1;
		Map<String, Object> params = new HashMap<String, Object>();
		if(sc.getSearchPeriod().equals("") && sc.getSearchState().equals("")) {
			result = ses.selectOne(ns + ".selectCouponListCount", member_no);
			
		} else {
			String searchState = sc.getSearchState();
			String searchOrder = sc.getSearchOrder();
			params.put("member_no", member_no);
			params.put("searchState", searchState);
			params.put("searchOrder", searchOrder);
			result = ses.selectOne(ns + ".selectCouponListCountByCondition", params);
			System.out.println("sss : " + result);
			System.out.println(params.toString());
		}
		return result;
	}

	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
