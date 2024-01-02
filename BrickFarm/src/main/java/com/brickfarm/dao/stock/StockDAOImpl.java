package com.brickfarm.dao.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.admin.sjy.AdminReceivingDTO;
import com.brickfarm.dto.user.syt.UserDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.vo.admin.sjy.AdminProductVO;
import com.brickfarm.vo.admin.sjy.AdminStockVO;

@Repository
public class StockDAOImpl implements StockDAO {

	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.StockMapper";

	// ==[송지영]==========================================================================================================================================
	@Override
	public int insertProductStock(AdminStockVO stock) throws Exception {
		return ses.insert(ns + ".insertProductStock", stock);
	}

	@Override
	public void updateProductStockModify(AdminStockVO stock) throws Exception {
		ses.update(ns + ".updateProductStockModify", stock);

	}

	@Override
	public int deleteStock(String product_code) throws Exception {
		return ses.delete(ns + ".deleteStock", product_code);
	}

	@Override
	public List<AdminProductVO> selectStockCheck() throws Exception {
		return ses.selectList(ns + ".selectStockCheck");
	}

	@Override
	public int selectSoldOutCount() throws Exception {
		return ses.selectOne(ns + ".selectSoldOutCount");
	}

	@Override
	public boolean updateStock(String product_code) {
		boolean result = false;
		if (ses.update(ns + ".updateStock", product_code) > 0) {
			result = true;
		}
		return result;
	}
	
	@Override
	public int updateStockManually(AdminReceivingDTO receiving) {
		return ses.update(ns + ".updateStockManually", receiving);
	}
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	
	@Override
	public int selectProductStockQuantity(String productCode) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("product_code", productCode);
		
		List<Object> stockQuantity = new ArrayList<Object>();
		stockQuantity = ses.selectList(ns + ".selectProductStockQuantity", params);
		
		int result = 0;
		
		if (stockQuantity.size() != 1) {
			for (int i = 0; i < stockQuantity.size(); i++) {
				result += (int) stockQuantity.get(i);
			}
		} else {
			result = ses.selectOne(ns + ".selectProductStockQuantity", params);
		}

		return result;
	}
	
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	@Override
	public int updateStockOnStockQuantity(List<UserDetailedOrderDTO> detailedOrderList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("detailedOrderList", detailedOrderList);
		return ses.update(ns + ".updateStockOnStockQuantity", param);
	}
	// ==================================================================================================================================================

	@Override
	public int isVerifyStock(List<UserPaymentListDTO> paymentList) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("paymentList", paymentList);
		return ses.selectOne(ns + ".isVerifyStock", param);
	}
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
