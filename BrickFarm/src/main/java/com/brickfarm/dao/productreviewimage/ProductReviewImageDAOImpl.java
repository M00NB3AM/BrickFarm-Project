package com.brickfarm.dao.productreviewimage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.vo.user.psj.UserProductReviewImageVO;

@Repository
public class ProductReviewImageDAOImpl implements ProductReviewImageDAO {
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.ProductReviewImageMapper";
	
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[박상진]==========================================================================================================================================
	@Override
	public int insertProductReviewImgs(int product_review_no, List<UserProductReviewImageVO> fileList) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Map<String, Object>> paramFileList = new ArrayList<Map<String,Object>>();
		for(UserProductReviewImageVO file : fileList) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("product_review_no",product_review_no);
			item.put("original_file_name",file.getOriginal_file_name());
			item.put("new_file_name",file.getNew_file_name());
			item.put("thumb_file_name",file.getThumb_file_name());
			item.put("file_size",file.getFile_size());
			paramFileList.add(item);
		}
		params.put("fileList", paramFileList);
		System.out.println(params.toString());
		return ses.insert(ns + ".insertProductReviewImgs", params);
	}
	
	
	@Override
	public List<UserProductReviewImageVO> selectProductReviewImg(int product_review_no) {
		
		return ses.selectList(ns + ".selectProductReviewImg", product_review_no);
	}
	// ==================================================================================================================================================

	@Override
	public int deleteProductReviewImg(int product_review_image_no) {
		return ses.delete(ns + ".deleteProductReviewImg", product_review_image_no);
	}


	@Override
	public int insertProductReviewImg(UserProductReviewImageVO newFile) {
		
		return ses.insert(ns+".insertProductReviewImg", newFile);
	}


	@Override
	public int deleteAll(int product_review_no) {
		
		return ses.delete(ns+".deleteAll", product_review_no);
	}


	
	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================
	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
