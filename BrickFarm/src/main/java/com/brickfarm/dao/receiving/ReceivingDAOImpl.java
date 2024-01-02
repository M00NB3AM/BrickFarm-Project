package com.brickfarm.dao.receiving;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.sjy.AdminPlaceOrderDTO;
import com.brickfarm.dto.admin.sjy.AdminReceivingDTO;
import com.brickfarm.etc.sjy.PagingInfo;
import com.brickfarm.etc.sjy.ReceivingSearchCondition;

@Repository
public class ReceivingDAOImpl implements ReceivingDAO {

	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.ReceivingMapper";

	// ==[송지영]==========================================================================================================================================
	@Override
	public List<AdminReceivingDTO> selectReceiving(PagingInfo pi) throws Exception {
		return ses.selectList(ns + ".selectReceiving", pi);
	}

	@Override
	public List<AdminReceivingDTO> selectReceivingByNo(int receiving_no) throws Exception {
		return ses.selectList(ns + ".selectReceivingByNo", receiving_no);
	}

	@Override
	public List<AdminReceivingDTO> selectAllReceivingByCondition(ReceivingSearchCondition searchCondition,
			PagingInfo pi) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search_word", searchCondition.getSearch_word());
		map.put("search_type", searchCondition.getSearch_type());
		map.put("min_date", searchCondition.getMin_date());
		map.put("max_date", searchCondition.getMax_date());
		map.put("all", searchCondition.getAll());
		map.put("wait", searchCondition.getWait());
		map.put("complete", searchCondition.getComplete());
		map.put("startRowIndex", pi.getStartRowIndex());
		map.put("viewPostCntPerPage", pi.getViewPostCntPerPage());
		return ses.selectList(ns + ".selectAllReceivingByCondition", map);
	}

	@Override
	public int updateReceivingComplete(AdminReceivingDTO receiving) throws Exception {
		return ses.update(ns + ".updateReceivingComplete", receiving);
	}

	@Override
	public int updateReceivingModify(AdminReceivingDTO receiving) throws Exception {
		return ses.update(ns + ".updateReceivingModify", receiving);
	}

	@Override
	public int deleteReceiving(String product_code) throws Exception {
		return ses.delete(ns + ".deleteReceiving", product_code);
	}

	@Override
	public int insertReceiving(List<AdminPlaceOrderDTO> list) throws Exception {
		return ses.insert(ns + ".insertReceiving", list);
	}

	@Override
	public int updateReceivingConfirm() throws Exception {
		return ses.update(ns + ".updateReceivingConfirm");
	}

	@Override
	public int selectTotalPostCnt() throws Exception {
		return ses.selectOne(ns + ".selectTotalPostCnt");
	}

	@Override
	public int selectSearchedTotalPostCnt(ReceivingSearchCondition searchCondition) {
		return ses.selectOne(ns + ".selectSearchedTotalPostCnt", searchCondition);
	}
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
