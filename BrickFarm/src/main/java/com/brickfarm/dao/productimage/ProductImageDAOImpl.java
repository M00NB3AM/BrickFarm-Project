package com.brickfarm.dao.productimage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.vo.admin.sjy.AdminProductImageVO;
import com.brickfarm.vo.user.lkm.UserProductImageVO;

@Repository
public class ProductImageDAOImpl implements ProductImageDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.ProductImageMapper";
	// ==[송지영]==========================================================================================================================================

	@Override
	public List<AdminProductImageVO> selectProductImageByCode(int product_code) throws Exception {
		return ses.selectList(ns + ".selectProductImageByCode", product_code);
	}

	@Override
	public void insertProductImg(String path, String product_code) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("product_file_path", path);
		param.put("product_code", product_code);
		
		ses.insert(ns + ".insertProductImg", param);
	}
	
	@Override
	public int deleteAllProductImg(String product_code) throws Exception {
		return ses.delete(ns + ".deleteAllProductImg", product_code);
		
	}
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	
	@Override
	public List<UserProductImageVO> selectProductImages(String productCode) throws Exception {
		Map<String, Object> productDetailImages = new HashMap<String, Object>();
		productDetailImages.put("product_code", productCode);
		
		return ses.selectList(ns + ".selectProductDetailImages", productDetailImages);
	}
	
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
