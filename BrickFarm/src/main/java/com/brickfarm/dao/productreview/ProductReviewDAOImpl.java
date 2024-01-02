package com.brickfarm.dao.productreview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.user.psj.UserProductReviewDTO;
import com.brickfarm.dto.user.psj.UserProductReviewInfoDTO;
import com.brickfarm.etc.lkm.PagingInfo;
import com.brickfarm.vo.user.lkm.UserProductReviewVO;

@Repository
public class ProductReviewDAOImpl implements ProductReviewDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.ProductReviewMapper";
	
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	
	@Override
	public List<UserProductReviewVO> selectProductReviewList(String productCode, String sortReview, PagingInfo pagingInfo) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("product_code", productCode);
		params.put("sortReview", sortReview);
		params.put("startRowIndex", pagingInfo.getStartRowIndex());
		params.put("viewPostCountPerPage", pagingInfo.getViewPostCountPerPage());
		
		List<UserProductReviewVO> productReviewList = ses.selectList(ns + ".selectProductReviewList", params);
		
		return productReviewList;
	}
	
	// ==================================================================================================================================================
	
	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[박상진]==========================================================================================================================================
	@Override
	public UserProductReviewInfoDTO selectProductReviewInfo(int detailedOrderNo) throws Exception {
		return ses.selectOne(ns + ".selectReviewProductInfo", detailedOrderNo);
	}
	// ==================================================================================================================================================

	@Override
	public int insertProductReview(UserProductReviewDTO productReviewDTO) throws Exception {
		int result = 0;
		if(ses.insert(ns + ".insertProductReview", productReviewDTO) == 1) {
			result = productReviewDTO.getProduct_review_no();
		}
		return result;
	}

	@Override
	public UserProductReviewDTO selectProductReview(int detailed_order_no) throws Exception {
		
		return ses.selectOne(ns+".selectProductReview", detailed_order_no);
	}

	@Override
	public int updateProductReview(UserProductReviewDTO productReviewDTO) {
		return ses.update(ns +".updateProductReview", productReviewDTO);
	}

	@Override
	public int selectReviewCount(int memberNo) throws Exception {
		return ses.selectOne(ns + ".selectReviewCount", memberNo);
	}
	
	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
