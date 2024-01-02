package com.brickfarm.dao.carryingout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.sjy.AdminCarryingOutDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.etc.sjy.CarryingOutSearchCondition;
import com.brickfarm.etc.sjy.PagingInfo;

@Repository
public class CarryingOutDAOImpl implements CarryingOutDAO {

	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.CarryingOutMapper";

	// ==[송지영]==========================================================================================================================================
	@Override
	public List<AdminCarryingOutDTO> selectAllCarryingOut(PagingInfo pi) throws Exception {
		return ses.selectList(ns + ".selectAllCarryingOut", pi);
	}

	@Override
	public List<AdminCarryingOutDTO> selectCarryingOutByNo(int carrying_out_no) throws Exception {
		return ses.selectList(ns + ".selectCarryingOutByNo", carrying_out_no);
	}

	@Override
	public List<AdminCarryingOutDTO> selectAllCarryingOutByCondition(CarryingOutSearchCondition searchCondition, PagingInfo pi)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search_type",searchCondition.getSearch_type());
		map.put("search_word",searchCondition.getSearch_word());
		map.put("max_date",searchCondition.getMax_date());
		map.put("min_date",searchCondition.getMin_date());
		map.put("all",searchCondition.getAll());
		map.put("complete",searchCondition.getComplete());
		map.put("wait",searchCondition.getWait());
		map.put("startRowIndex", pi.getStartRowIndex());
		map.put("viewPostCntPerPage", pi.getViewPostCntPerPage());
		return ses.selectList(ns + ".selectAllCarryingOutByCondition", map);
	}

	@Override
	public int updateCarryingOutComplete(AdminCarryingOutDTO carryingOut) throws Exception {
		return ses.update(ns + ".updateCarryingOutComplete", carryingOut);
	}

	@Override
	public int updateCarryingOutModify(AdminCarryingOutDTO carryingOut) throws Exception {
		return ses.update(ns + ".updateCarryingOutModify", carryingOut);
	}

	@Override
	public int updateCarryingOutConfirm() throws Exception {
		return ses.update(ns + ".updateCarryingOutConfirm");
	}

	@Override
	public int selectCarriedOutCount() throws Exception {
		return ses.selectOne(ns + ".selectCarriedOutCount");
	}

	@Override
	public int selectTotalPostCnt() throws Exception {
		return ses.selectOne(ns + ".selectTotalPostCnt");
	}

	@Override
	public int selectSearchedTotalPostCnt(CarryingOutSearchCondition searchCondition) {
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
	/**
	 * @methodName : selectHasCarryingOut
	 * @author : syt
	 * @param product_code
	 * @return
	 * @returnValue : @param product_code
	 * @returnValue : @return
	 * @date : 2023. 11. 22. 오전 11:19:43
	 * @description : 반출테이블에 정보가 이미 있는지 없는지 확인
	 */
	@Override
	public int selectHasCarryingOut(String product_code) throws Exception {
		return ses.selectOne(ns + ".selectHasCarryingOut", product_code);
	}

	/**
	 * @methodName : updateCarryingOutData
	 * @author : syt
	 * @param carryinOutDataList
	 * @return
	 * @returnValue : @param carryinOutDataList
	 * @returnValue : @return
	 * @date : 2023. 11. 22. 오전 11:27:06
	 * @description : 이미 정보가 있어 수량만 업데이트
	 */
	@Override
	public int updateCarryingOutData(UserPaymentListDTO carryinOutData) throws Exception {
		return ses.update(ns + ".updateCarryingOutData", carryinOutData);
	}

	/**
	 * @methodName : insertCarryingOutData
	 * @author : syt
	 * @param carryinOutDataList
	 * @return
	 * @returnValue : @param carryinOutDataList
	 * @returnValue : @return
	 * @date : 2023. 11. 22. 오전 11:27:08
	 * @description : 정보가 없어 새로 인설트
	 */
	@Override
	public int insertCarryingOutData(UserPaymentListDTO carryinOutData) throws Exception {
		return ses.insert(ns + ".insertCarryingOutData", carryinOutData);
	}
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}