package com.brickfarm.dao.placeorder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.sjy.AdminPlaceOrderDTO;
import com.brickfarm.dto.admin.sjy.AdminReceivingDTO;
import com.brickfarm.etc.sjy.PagingInfo;
import com.brickfarm.etc.sjy.PlaceOrderCondition;
import com.brickfarm.vo.admin.sjy.AdminProductVO;

@Repository
public class PlaceOrderDAOImpl implements PlaceOrderDAO {
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.PlaceOrderMapper";

	// ==[송지영]==========================================================================================================================================
	@Override
	public List<AdminPlaceOrderDTO> selectPlaceOrder(PagingInfo pi) throws Exception {
		return ses.selectList(ns + ".selectPlaceOrder", pi);
	}
	
	@Override
	public List<AdminPlaceOrderDTO> selectPlaceOrderByNo(int place_order_no) throws Exception {
		return ses.selectList(ns + ".selectPlaceOrderByNo", place_order_no);
	}
	
	@Override
	public List<AdminPlaceOrderDTO> selectPlaceOrderByCondition(PlaceOrderCondition searchCondition, PagingInfo pi) throws Exception {
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
		return ses.selectList(ns + ".selectPlaceOrderByCondition", map);
	}
	
	@Override
	public int insertPlaceOrder(List<AdminPlaceOrderDTO> products) throws Exception {
		return ses.insert(ns + ".insertPlaceOrder", products);
	}
	
	@Override
	public int updatePlaceOrder(AdminPlaceOrderDTO product) throws Exception {
		return ses.update(ns + ".updatePlaceOrder", product);
	}
	
	@Override
	public int deleteOrder(String product_code) throws Exception {
		return ses.delete(ns + ".deleteOrder", product_code);
	}
	
	@Override
	public int deleteSelectedOrder(String place_order_no) throws Exception {
		return ses.delete(ns + ".deleteSelectedOrder", place_order_no);
	}
	
	@Override
	public int deleteOrderByCode(int place_order_no, String product_code) {
		Map<String, Object> deleteOrder = new HashMap<String, Object>();
		deleteOrder.put("place_order_no", place_order_no);
		deleteOrder.put("product_code", product_code);
		return ses.delete(ns + ".deleteOrderByCode", deleteOrder);
	}
	
	@Override
	public int updateOrderConfirm() throws Exception {
		return ses.update(ns + ".updateOrderConfirm");
	}
	
	@Override
	public List<AdminPlaceOrderDTO> selectPastThreeDaysOrder() throws Exception {
		return ses.selectList(ns + ".selectPastThreeDaysOrder");
	}

	@Override
	public int insertPlaceOrderAuto(List<AdminProductVO> productList) throws Exception {
		return ses.insert(ns + ".insertPlaceOrderAuto", productList);
	}

	@Override
	public int selectIsNotPlaced(AdminProductVO product) throws Exception {
		return ses.selectOne(ns + ".selectIsNotPlaced", product);
	}

	@Override
	public int selectTotalPostCnt() throws Exception {
		return ses.selectOne(ns + ".selectTotalPostCnt");
	}

	@Override
	public int selectSearchedTotalPostCnt(PlaceOrderCondition searchCondition) {
		return ses.selectOne(ns + ".selectSearchedTotalPostCnt", searchCondition);
	}

	@Override
	public int selectMaxPlaceOrderNo() throws Exception {
		return ses.selectOne(ns + ".selectMaxPlaceOrderNo");
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
