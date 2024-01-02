package com.brickfarm.controller.user.product.lkm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.brickfarm.controller.HomeController;
import com.brickfarm.etc.lkm.PagingInfo;
import com.brickfarm.service.user.product.ProductService;
import com.brickfarm.vo.user.lkm.UserProductImageVO;
import com.brickfarm.vo.user.lkm.UserLikeProductVO;
import com.brickfarm.vo.user.lkm.UserProductVO;
import com.brickfarm.vo.user.lkm.UserProductReviewVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;

/**
 * @packageName : com.brickfarm.controller.user.product.lkm
 * @fileName : UserProductController.java
 * @author : lkm
 * @date : 2023. 10. 25.
 * @description :
 */
@Controller
@RequestMapping("user/product/*")
public class UserProductController {
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Inject
	private ProductService productService;

	/**
	 * @methodName : productListAll
	 * @author : lkm
	 * @throws Exception
	 * @returnValue :
	 * @date : 2023. 10. 10. 오후 12:35:34
	 * @description : index(MainPage)에서 상품 목록을 누르면 전체 상품을 조회하고 조회된 상품들이 보여지도록 응답
	 */
	@RequestMapping("productListAll")
	public String productListAll(Model model, HttpServletRequest request, HttpSession session,
			@RequestParam(value = "category", defaultValue = "all") String category, 
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "showCount", defaultValue = "12") int showCount,
			@RequestParam(value = "sortMethod", defaultValue = "salesOrderSort") String sortMethod,
			@RequestParam(value = "searchProduct", defaultValue = "") String searchProduct,
			@RequestParam(value = "priceMin", defaultValue = "0") String priceMin,
			@RequestParam(value = "priceMax", defaultValue = "0") String priceMax) {
		
		int min = Integer.parseInt(priceMin);
		int max = Integer.parseInt(priceMax);
		
		// 전체 상품 select
		if ("all".equals(category) && min == 0 && max == 0) {
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductAll(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				model.addAttribute("searchProduct", searchProduct);
				model.addAttribute("min", min);
				model.addAttribute("max", max);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		
			// 베스트 상품 select
		} else if ("best".equals(category)) {
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getBestProduct(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("productList", productList);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			} 

			
			// 자동차 전체 select
		} else if ("cars".equals(category)) {
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}


			// 자동차 - 스포츠카 전체 select
		} else if ("sportsCar".equals(category)) {
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			// 자동차 - 레이싱카 전체 select
		} else if ("racingCar".equals(category)) {
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			// 자동차 - 승용차(세단) 전체 select
		} else if ("sedan".equals(category)) { 
		
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}

			// 자동차 - 대형차 전체 select
		} else if ("largeCar".equals(category)) { 
		
			Map<String, Object> productResult;
			
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			// 자동차 - 오프로드&SUV 전체 select
		} else if ("suv".equals(category)) {
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			// 자동차 - 오토바이 전체 select
		} else if ("motorcycle".equals(category)) {
			
			Map<String, Object> productResult;
			
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			// 배 / 보트 전체 select
		} else if ("ships".equals(category)) {
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {

				e.printStackTrace();
			}
			
			// 배 / 보트 - 배 전체 select
		} else if ("ship".equals(category)) {
			
			Map<String, Object> productResult;
			
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {

				e.printStackTrace();
			}
			
			// 배 / 보트 - 보트 전체 select
		} else if ("boat".equals(category)) {
			
			Map<String, Object> productResult;
			
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			// 비행기 / 우주선 전체 select
		} else if ("flyingObject".equals(category)) { 
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// 비행기 / 우주선 - 비행기 전체 select
		} else if ("airplane".equals(category)) {
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// 비행기 / 우주선 - 헬리콥터 전체 select
		} else if ("Helicopter".equals(category)) {
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {

				e.printStackTrace();
			}
			
			// 비행기 / 우주선 - 우주선 전체 select
		} else if ("spacecraft".equals(category)) {
			
			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByCaterory(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if (min != 0 && max != 0) {

			Map<String, Object> productResult;
			try {
				productResult = productService.getProductByPrice(category, pageNo, showCount, sortMethod, searchProduct, min, max);
				List<UserProductVO> productList = (List<UserProductVO>) productResult.get("productList");
				PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
				
				model.addAttribute("pagingInfo", pagingInfo);
				model.addAttribute("category", category);
				model.addAttribute("productList", productList);
				model.addAttribute("showCount", showCount);
				model.addAttribute("sortMethod", sortMethod);
				model.addAttribute("min", min);
				model.addAttribute("max", max);
				
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		
		session = request.getSession();
		UserMemberVO memberInfo = new UserMemberVO(); 
		memberInfo = (UserMemberVO) session.getAttribute("loginMemberInfo");
		
		if (memberInfo != null) {
			int memberNo = memberInfo.getMember_no();			
			List<UserLikeProductVO> likeProductList;
			try {
				likeProductList = productService.getLikeProductList(memberNo);
				model.addAttribute("likeProductList", likeProductList);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "/user/product/productListAll";

	}
	
	@ResponseBody
	@RequestMapping(value = "productListOfRating", method = RequestMethod.POST)
	public List<UserProductVO> productListOfRating(Model model,
			@RequestParam(value = "category", defaultValue = "all") String category, 
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "showCount", defaultValue = "12") int showCount,
			@RequestParam(value = "sortMethod", defaultValue = "salesOrderSort") String sortMethod,
			@RequestParam(value = "searchProduct", defaultValue = "") String searchProduct,
			@RequestParam(value = "priceMin", defaultValue = "0") int min,
			@RequestParam(value = "priceMax", defaultValue = "0") int max,
			@RequestParam(value = "selectedRatingValue", defaultValue = "0") int selectedRatingValue) {
		
		Map<String, Object> productResult;
		List<UserProductVO> productList = new ArrayList<UserProductVO>();
		
		try {
			productResult = productService.getRatingProductList(category, pageNo, showCount, sortMethod,
					searchProduct, min, max, selectedRatingValue);
			productList = (List<UserProductVO>) productResult.get("productList");
			PagingInfo pagingInfo = (PagingInfo) productResult.get("pagingInfo");
			
			model.addAttribute("pagingInfo", pagingInfo);
			model.addAttribute("ratingProductList", productList);
			model.addAttribute("showCount", showCount);
			model.addAttribute("sortMethod", sortMethod);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return productList;
	}
	
	/**
	 * @methodName : productDetail
	 * @author : lkm
	 * @returnValue :
	 * @date : 2023. 10. 17. 오후 5:13:04
	 * @description :
	 */
	@RequestMapping("productDetail")
	public void productDetail(Model model, HttpServletRequest request, HttpSession session, 
			@RequestParam(value = "productCode") String productCode,
			@RequestParam(value = "sortReview", defaultValue = "highRating") String sortReview,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {

		// 제품 정보 select
		Map<String, Object> productDetailResult;
		try {
			productDetailResult = productService.getProductDetail(productCode);
			
			for (String key : productDetailResult.keySet()) {
			    Object value = productDetailResult.get(key);
			}
			
			model.addAttribute("product", productDetailResult.get("product"));
			
			
			// 제품 이미지 select
			List<UserProductImageVO> productImages = productService.getProductDetailImages(productCode);
			model.addAttribute("productImages", productImages);
			
			// 제품 보유 재고 select
			int stockQuantity = productService.getProductStockQuantity(productCode);
			model.addAttribute("stockQuantity", stockQuantity);
			
			// 함께 본 상품 select
			UserProductVO product = (UserProductVO) productDetailResult.get("product");
			int productCategoryNo = product.getProduct_category_no();
			List<UserProductVO> alsoViewProductList = productService.getAlsoViewProduct(productCode, productCategoryNo);
			model.addAttribute("alsoViewProductList", alsoViewProductList);
			
			// 상품 좋아요 표기
			session = request.getSession();
			UserMemberVO memberInfo = new UserMemberVO(); 
			memberInfo = (UserMemberVO) session.getAttribute("loginMemberInfo");
			
			if (memberInfo != null) {
				int memberNo = memberInfo.getMember_no();			
				List<UserLikeProductVO> likeProductList = productService.getLikeProductList(memberNo);
				
				model.addAttribute("likeProductList", likeProductList);
			
			}
			
			// 상품 review select
			Map<String, Object> productReviewResult = new HashMap<String, Object>();
			List<UserProductReviewVO> productReviewList = new ArrayList<UserProductReviewVO>();
			
			productReviewResult = productService.getProductReviewList(productCode, sortReview, pageNo);
			productReviewList = (List<UserProductReviewVO>) productReviewResult.get("productReviewList");
			PagingInfo pagingInfo = (PagingInfo) productReviewResult.get("pagingInfo");
			
			float totalStarCount = 0;
			
			System.out.println(productReviewList);
			System.out.println(productReviewList.size());
			
			if (productReviewList.get(0) != null) {
				for (int i = 0; i < productReviewList.size(); i++) {
					totalStarCount += productReviewList.get(i).getStar_count();
				
				}
					totalStarCount = totalStarCount / productReviewList.size();		
					model.addAttribute("totalReview", productReviewList.size());
			} else {
				model.addAttribute("totalReview", productReviewList.get(0));
				System.out.println(productReviewList.get(0));
			}
			
			
			
			model.addAttribute("pagingInfo", pagingInfo);
			model.addAttribute("productReviewList", productReviewList);
			model.addAttribute("totalStarCount", totalStarCount);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "changeReview", method = RequestMethod.POST)
	public List<UserProductReviewVO> changeReview(Model model,
			@RequestParam(value = "productCode") String productCode,
			@RequestParam(value = "sortReview", defaultValue = "highRating") String sortReview,
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {

		Map<String, Object> productReviewResult = new HashMap<String, Object>();
		List<UserProductReviewVO> productReviewList = new ArrayList<UserProductReviewVO>();
		try {
			productReviewResult = productService.getProductReviewList(productCode, sortReview, pageNo);
			productReviewList = (List<UserProductReviewVO>) productReviewResult.get("productReviewList");
			PagingInfo pagingInfo = (PagingInfo) productReviewResult.get("pagingInfo");
			
			model.addAttribute("pagingInfo", pagingInfo);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return productReviewList;
	}


}
