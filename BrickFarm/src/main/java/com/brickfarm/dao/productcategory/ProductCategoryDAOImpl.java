package com.brickfarm.dao.productcategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.etc.lkm.PagingInfo;
import com.brickfarm.vo.admin.sjy.AdminProductCategoryVO;
import com.brickfarm.vo.user.lkm.UserProductVO;

@Repository
public class ProductCategoryDAOImpl implements ProductCategoryDAO {
	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.ProductCategoryMapper";

	// ==[송지영]==========================================================================================================================================
	@Override
	public List<AdminProductCategoryVO> selectAllCategory() throws Exception {
		return ses.selectList(ns + ".selectAllCategory");
	}
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	
	@Override
	public List<UserProductVO> selectProductByCaterory(String category, PagingInfo pagingInfo, String sortMethod, String searchProduct) throws Exception {
		
		Map<String, Object> productResult = new HashMap<String, Object>();
		productResult.put("category", category);
		productResult.put("startRowIndex", pagingInfo.getStartRowIndex());
		productResult.put("viewPostCountPerPage", pagingInfo.getViewPostCountPerPage());
		productResult.put("sortMethod", sortMethod);
		
		return ses.selectList(ns + ".selectProductByCaterory", productResult);
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
