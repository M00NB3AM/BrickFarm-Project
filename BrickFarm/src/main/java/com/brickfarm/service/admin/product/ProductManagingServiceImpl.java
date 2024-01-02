package com.brickfarm.service.admin.product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brickfarm.dao.carryingout.CarryingOutDAO;
import com.brickfarm.dao.detailedorder.DetailedOrderDAO;
import com.brickfarm.dao.event.EventDAO;
import com.brickfarm.dao.eventlog.EventLogDAO;
import com.brickfarm.dao.placeorder.PlaceOrderDAO;
import com.brickfarm.dao.product.ProductDAO;
import com.brickfarm.dao.productcategory.ProductCategoryDAO;
import com.brickfarm.dao.productimage.ProductImageDAO;
import com.brickfarm.dao.receiving.ReceivingDAO;
import com.brickfarm.dao.schedulerlog.SchedulerLogDAO;
import com.brickfarm.dao.stock.StockDAO;
import com.brickfarm.dto.admin.sjy.AdminCarryingOutDTO;
import com.brickfarm.dto.admin.sjy.AdminPlaceOrderDTO;
import com.brickfarm.dto.admin.sjy.AdminReceivingDTO;
import com.brickfarm.etc.sjy.AdminProductRank;
import com.brickfarm.etc.sjy.CarryingOutSearchCondition;
import com.brickfarm.etc.sjy.EventSearchCondition;
import com.brickfarm.etc.sjy.PagingInfo;
import com.brickfarm.etc.sjy.PlaceOrderCondition;
import com.brickfarm.etc.sjy.ProductSearchCondition;
import com.brickfarm.etc.sjy.ReceivingSearchCondition;
import com.brickfarm.vo.admin.sjy.AdminEventVO;
import com.brickfarm.vo.admin.sjy.AdminProductCategoryVO;
import com.brickfarm.vo.admin.sjy.AdminProductVO;
import com.brickfarm.vo.admin.sjy.AdminStockVO;
import com.brickfarm.vo.admin.sjy.AdminProductImageVO;

@Service
public class ProductManagingServiceImpl implements ProductManagingService {
	// ==[송지영]==========================================================================================================================================
	@Inject
	private ProductDAO pDAO;

	@Inject
	private ProductImageDAO piDAO;

	@Inject
	private ReceivingDAO rDAO;

	@Inject
	private PlaceOrderDAO poDAO;

	@Inject
	private CarryingOutDAO coDAO;

	@Inject
	private ProductCategoryDAO pcDAO;

	@Inject
	private StockDAO sDAO;

	@Inject
	private EventDAO eDAO;

	@Inject
	private EventLogDAO elDAO;

	@Inject
	private DetailedOrderDAO doDAO;

	@Inject
	private SchedulerLogDAO slDAO;

	/**
	 * @methodName : getAllProducts
	 * @author : sjy
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:54:31
	 * @description : 상품 전체 목록 가져오기
	 */
	@Override
	public Map<String, Object> getAllProducts() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminProductVO> list = pDAO.selectAllProducts();

		result.put("productList", list);

		return result;
	}

	/**
	 * @methodName : getAllProducts
	 * @author : sjy
	 * @param curPageNo
	 * @return
	 * @throws Exception
	 * @returnValue : @param curPageNo
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 17. 오후 4:09:10
	 * @description : 상품 리스트 페이징
	 */
	@Override
	public Map<String, Object> getAllProducts(int curPageNo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		PagingInfo pi = createPagingInfo(curPageNo, "product");
		List<AdminEventVO> list = pDAO.selectAllProductsPaging(pi);

		result.put("productList", list);
		result.put("pagingInfo", pi);

		return result;
	}

	/**
	 * @methodName : getAllEvent
	 * @author : sjy
	 * @param curPageNo
	 * @return
	 * @throws Exception
	 * @returnValue : @param curPageNo
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 17. 오후 3:04:54
	 * @description : 이벤트 전체 목록 페이징
	 */
	@Override
	public Map<String, Object> getAllEvent(int curPageNo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		PagingInfo pi = createPagingInfo(curPageNo, "event");
		List<AdminEventVO> list = eDAO.selectAllEventPaging(pi);

		result.put("eventList", list);
		result.put("pagingInfo", pi);

		return result;
	}

	/**
	 * @methodName : getAllEvent
	 * @author : sjy
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 12:18:42
	 * @description : 이벤트 전체 목록 가져오기
	 */
	@Override
	public Map<String, Object> getAllEvent() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminEventVO> list = eDAO.selectAllEvent();

		result.put("eventList", list);

		return result;
	}

	/**
	 * @methodName : getEventDetail
	 * @author : sjy
	 * @param event_no
	 * @return
	 * @throws Exception
	 * @returnValue : @param event_no
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 3:20:48
	 * @description : 이벤트 번호로 이벤트 상세정보 가져오기
	 */
	@Override
	public Map<String, Object> getEventDetail(int event_no) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminEventVO> list = eDAO.selectEventDetail(event_no);

		result.put("event", list);

		return result;
	}

	/**
	 * @methodName : getProductsByEvtNo
	 * @author : sjy
	 * @param event_no
	 * @return
	 * @throws Exception
	 * @returnValue : @param event_no
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 13. 오후 9:39:53
	 * @description : 이벤트 해당 상품 가져오기
	 */
	@Override
	public Map<String, Object> getProductsByEvtNo(int event_no) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminProductVO> list = pDAO.selectProductsByEvtNo(event_no);

		result.put("productList", list);

		return result;
	}

	/**
	 * @methodName : getAllImgByCode
	 * @author : sjy
	 * @param product_code
	 * @return
	 * @throws Exception
	 * @returnValue : @param product_code
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:54:54
	 * @description : 상품 코드로 이미지 가져오기
	 */
	@Override
	public Map<String, Object> getAllImgByCode(int product_code) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminProductImageVO> list = piDAO.selectProductImageByCode(product_code);

		result.put("imgList", list);

		return result;
	}

	/**
	 * @methodName : getAllReceiving
	 * @author : sjy
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:55:20
	 * @description : 입고 목록 전부 가져오기
	 */
	@Override
	public Map<String, Object> getAllReceiving(int curPageNo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		PagingInfo pi = createPagingInfo(curPageNo, "receiving");
		List<AdminReceivingDTO> list = rDAO.selectReceiving(pi);

		result.put("receivingList", list);
		result.put("pagingInfo", pi);

		return result;
	}

	/**
	 * @methodName : getAllPlaceOrder
	 * @author : sjy
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:55:34
	 * @description : 발주 목록 전부 가져오기
	 */
	@Override
	public Map<String, Object> getAllPlaceOrder(int curPageNo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		PagingInfo pi = createPagingInfo(curPageNo, "placeOrder");
		List<AdminPlaceOrderDTO> list = poDAO.selectPlaceOrder(pi);

		result.put("placeOrderList", list);
		result.put("pagingInfo", pi);

		return result;
	}

	/**
	 * @methodName : getAllCarryingOut
	 * @author : sjy
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:55:49
	 * @description : 반출 목록 전부 가져오기
	 */
	@Override
	public Map<String, Object> getAllCarryingOut(int curPageNo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		PagingInfo pi = createPagingInfo(curPageNo, "carryingOut");
		List<AdminCarryingOutDTO> list = coDAO.selectAllCarryingOut(pi);

		result.put("carryingOutList", list);
		result.put("pagingInfo", pi);

		return result;
	}

	/**
	 * @methodName : getAllCategory
	 * @author : sjy
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:56:01
	 * @description : 카테고리 목록 전부 가져오기
	 */
	@Override
	public Map<String, Object> getAllCategory() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminProductCategoryVO> list = pcDAO.selectAllCategory();

		result.put("category", list);

		return result;
	}

	/**
	 * @methodName : getProductDetail
	 * @author : sjy
	 * @param product_code
	 * @return
	 * @throws Exception
	 * @returnValue : @param product_code
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:56:09
	 * @description : 상품 코드로 상품 상세 정보 가져오기
	 */
	@Override
	public Map<String, Object> getProductDetail(int product_code) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminProductVO> list = pDAO.selectProductDetails(product_code);

		result.put("product", list);

		return result;
	}

	/**
	 * @methodName : getPlaceOrderByNo
	 * @author : sjy
	 * @param place_order_no
	 * @return
	 * @throws Exception
	 * @returnValue : @param place_order_no
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:56:22
	 * @description : 발주 번호로 발주 목록 가져오기
	 */
	@Override
	public Map<String, Object> getPlaceOrderByNo(int place_order_no) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminPlaceOrderDTO> list = poDAO.selectPlaceOrderByNo(place_order_no);

		result.put("placeOrderList", list);

		return result;
	}

	/**
	 * @methodName : getReceivingByNo
	 * @author : sjy
	 * @param receiving_no
	 * @return
	 * @throws Exception
	 * @returnValue : @param receiving_no
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:56:50
	 * @description : 입고 번호로 입고 목록 가져오기
	 */
	@Override
	public Map<String, Object> getReceivingByNo(int receiving_no) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminReceivingDTO> list = rDAO.selectReceivingByNo(receiving_no);

		result.put("receivingList", list);

		return result;
	}

	/**
	 * @methodName : getCarryingOutByNo
	 * @author : sjy
	 * @param carrying_out_no
	 * @return
	 * @throws Exception
	 * @returnValue : @param carrying_out_no
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:57:06
	 * @description : 반출 번호로 반출 목록 가져오기
	 */
	@Override
	public Map<String, Object> getCarryingOutByNo(int carrying_out_no) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminCarryingOutDTO> list = coDAO.selectCarryingOutByNo(carrying_out_no);

		result.put("carryingOutList", list);

		return result;
	}

	/**
	 * @methodName : getAllProducts
	 * @author : sjy
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 * @returnValue : @param searchCondition
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:57:23
	 * @description : 검색 조건에 맞는 상품 목록 가져오기
	 */
	@Override
	public Map<String, Object> getAllProducts(ProductSearchCondition searchCondition) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminProductVO> list = pDAO.selectAllProductsByCondition(searchCondition);

		result.put("productList", list);

		return result;
	}

	/**
	 * @methodName : getAllProducts
	 * @author : sjy
	 * @param searchCondition
	 * @param curPageNo
	 * @return
	 * @throws Exception
	 * @returnValue : @param searchCondition
	 * @returnValue : @param curPageNo
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 17. 오후 4:40:55
	 * @description : 검색 조건에 맞는 상품 목록 페이징
	 */
	@Override
	public Map<String, Object> getAllProducts(ProductSearchCondition searchCondition, int curPageNo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		PagingInfo pi = createSearchedProductPagingInfo(curPageNo, searchCondition);
		System.out.println(pi);

		List<AdminProductVO> list = pDAO.getSearchedProductsPaging(searchCondition, pi);

		result.put("productList", list);
		result.put("pagingInfo", pi);

		return result;
	}

	private PagingInfo createSearchedProductPagingInfo(int curPageNo, ProductSearchCondition searchCondition)
			throws Exception {
		PagingInfo pi = new PagingInfo();

		pi.setPageNo(curPageNo);

		pi.setTotalPostCnt(pDAO.selectSearchedTotalPostCnt(searchCondition));

		if (pi.getTotalPostCnt() > 0) {
			// 총 페이지 수 구하기
			pi.setTotalPageCnt();

			// 보여주기 시작할 글의 row index 번호
			pi.setStartRowIndex();

			// 몇 개의 페이징 블럭이 나오는 지
			pi.setTotalPagingBlockCnt();

			// 현재 페이지가 속한 페이징 블럭 번호 구하기
			pi.setPageBlockOfCurrentPage();

			// 현재 블럭의 시작 페이지 번호 구하기
			pi.setStartNumOfCurrentPagingBlock();

			// 현재 블럭의 끝 페이지 번호 구하기
			pi.setEndNumOfCurrentPagingBlock();
		}

		return pi;
	}

	/**
	 * @methodName : getAllReceiving
	 * @author : sjy
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 * @returnValue : @param searchCondition
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:57:43
	 * @description : 검색 조건에 맞는 입고 목록 가져오기
	 */
	@Override
	public Map<String, Object> getAllReceiving(ReceivingSearchCondition searchCondition, int curPageNo)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		PagingInfo pi = createSearchReceivingPagingInfo(curPageNo, searchCondition);
		System.out.println(pi.toString());

		List<AdminReceivingDTO> list = rDAO.selectAllReceivingByCondition(searchCondition, pi);

		result.put("receivingList", list);
		result.put("pagingInfo", pi);

		return result;
	}

	private PagingInfo createSearchReceivingPagingInfo(int curPageNo, ReceivingSearchCondition searchCondition) {
		PagingInfo pi = new PagingInfo();

		pi.setPageNo(curPageNo);

		pi.setTotalPostCnt(rDAO.selectSearchedTotalPostCnt(searchCondition));

		if (pi.getTotalPostCnt() > 0) {
			// 총 페이지 수 구하기
			pi.setTotalPageCnt();

			// 보여주기 시작할 글의 row index 번호
			pi.setStartRowIndex();

			// 몇 개의 페이징 블럭이 나오는 지
			pi.setTotalPagingBlockCnt();

			// 현재 페이지가 속한 페이징 블럭 번호 구하기
			pi.setPageBlockOfCurrentPage();

			// 현재 블럭의 시작 페이지 번호 구하기
			pi.setStartNumOfCurrentPagingBlock();

			// 현재 블럭의 끝 페이지 번호 구하기
			pi.setEndNumOfCurrentPagingBlock();
		}

		return pi;
	}

	/**
	 * @methodName : getAllPlaceOrder
	 * @author : sjy
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 * @returnValue : @param searchCondition
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:57:54
	 * @description : 검색 조건에 맞는 발주 목록 가져오기
	 */
	@Override
	public Map<String, Object> getAllPlaceOrder(PlaceOrderCondition searchCondition, int curPageNo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		PagingInfo pi = createSearchedPlaceOrderPagingInfo(curPageNo, searchCondition);
		List<AdminPlaceOrderDTO> list = poDAO.selectPlaceOrderByCondition(searchCondition, pi);

		result.put("placeOrderList", list);
		result.put("pagingInfo", pi);

		return result;
	}

	private PagingInfo createSearchedPlaceOrderPagingInfo(int curPageNo, PlaceOrderCondition searchCondition) throws Exception {
		PagingInfo pi = new PagingInfo();

		pi.setPageNo(curPageNo);

		pi.setTotalPostCnt(poDAO.selectSearchedTotalPostCnt(searchCondition));

		if (pi.getTotalPostCnt() > 0) {
			// 총 페이지 수 구하기
			pi.setTotalPageCnt();

			// 보여주기 시작할 글의 row index 번호
			pi.setStartRowIndex();

			// 몇 개의 페이징 블럭이 나오는 지
			pi.setTotalPagingBlockCnt();

			// 현재 페이지가 속한 페이징 블럭 번호 구하기
			pi.setPageBlockOfCurrentPage();

			// 현재 블럭의 시작 페이지 번호 구하기
			pi.setStartNumOfCurrentPagingBlock();

			// 현재 블럭의 끝 페이지 번호 구하기
			pi.setEndNumOfCurrentPagingBlock();
		}

		return pi;
	}

	/**
	 * @methodName : getAllCarryingOut
	 * @author : sjy
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 * @returnValue : @param searchCondition
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:58:00
	 * @description : 검색 조건에 맞는 반출 목록 가져오기
	 */
	@Override
	public Map<String, Object> getAllCarryingOut(CarryingOutSearchCondition searchCondition, int curPageNo)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		PagingInfo pi = createSearchedCarryingOutPagingInfo(curPageNo, searchCondition);

		List<AdminCarryingOutDTO> list = coDAO.selectAllCarryingOutByCondition(searchCondition, pi);
		System.out.println(searchCondition.toString());
		System.out.println(list);
		result.put("carryingOutList", list);
		result.put("pagingInfo", pi);

		return result;
	}

	private PagingInfo createSearchedCarryingOutPagingInfo(int curPageNo, CarryingOutSearchCondition searchCondition) {
		PagingInfo pi = new PagingInfo();

		pi.setPageNo(curPageNo);

		pi.setTotalPostCnt(coDAO.selectSearchedTotalPostCnt(searchCondition));

		if (pi.getTotalPostCnt() > 0) {
			// 총 페이지 수 구하기
			pi.setTotalPageCnt();

			// 보여주기 시작할 글의 row index 번호
			pi.setStartRowIndex();

			// 몇 개의 페이징 블럭이 나오는 지
			pi.setTotalPagingBlockCnt();

			// 현재 페이지가 속한 페이징 블럭 번호 구하기
			pi.setPageBlockOfCurrentPage();

			// 현재 블럭의 시작 페이지 번호 구하기
			pi.setStartNumOfCurrentPagingBlock();

			// 현재 블럭의 끝 페이지 번호 구하기
			pi.setEndNumOfCurrentPagingBlock();
		}

		return pi;
	}

	/**
	 * @methodName : addProduct
	 * @author : sjy
	 * @param product
	 * @param images
	 * @return
	 * @throws Exception
	 * @returnValue : @param product
	 * @returnValue : @param images
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:58:19
	 * @description : 상품 저장
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addProduct(AdminProductVO product, List<String> images) throws Exception {
		boolean result = false;
		product.getProduct_description().replace("\r\n", "<br />");

		if (pDAO.insertProduct(product) != 0) {
			AdminStockVO stock = new AdminStockVO(-1, product.getProduct_code(), product.getIs_auto_order(),
					product.getSafety_stock_quantity(), product.getStock_quantity());
			if (sDAO.insertProductStock(stock) != 0) {
				if (images.size() > 0) {
					for (String path : images) {
						piDAO.insertProductImg(path, product.getProduct_code());
					}
					result = true;
				}
			}

		}
		return result;
	}

	/**
	 * @methodName : addPlaceOrder
	 * @author : sjy
	 * @param products
	 * @return
	 * @throws Exception
	 * @returnValue : @param products
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:58:36
	 * @description : 발주 저장
	 */
	@Override
	public boolean addPlaceOrder(List<AdminPlaceOrderDTO> products) throws Exception {
		boolean result = false;
		int placeOrderNo = poDAO.selectMaxPlaceOrderNo();
		
		for (AdminPlaceOrderDTO product : products) {
			product.setPlace_order_no(placeOrderNo);
		}
		
		if (poDAO.insertPlaceOrder(products) != 0) {
			result = true;
		}

		return result;
	}

	/**
	 * @methodName : completeReceiving
	 * @author : sjy
	 * @param receiving
	 * @return
	 * @throws Exception
	 * @returnValue : @param receiving
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:58:42
	 * @description : 입고 상태 완료 변경
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean completeReceiving(AdminReceivingDTO receiving) throws Exception {
		boolean result = false;
		if (sDAO.updateStockManually(receiving) > 0) {
			if (rDAO.updateReceivingComplete(receiving) != 0) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * @methodName : completeCarryingOut
	 * @author : sjy
	 * @param carryingOut
	 * @return
	 * @throws Exception
	 * @returnValue : @param carryingOut
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:58:50
	 * @description : 반출 상태 완료 변경
	 */
	@Override
	public boolean completeCarryingOut(AdminCarryingOutDTO carryingOut) throws Exception {
		boolean result = false;

		if (coDAO.updateCarryingOutComplete(carryingOut) != 0) {
			result = true;
		}

		return result;
	}

	/**
	 * @methodName : modifyPlaceOrder
	 * @author : sjy
	 * @param products
	 * @return
	 * @throws Exception
	 * @returnValue : @param products
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:58:59
	 * @description : 발주 수정
	 */
	@Override
	public boolean modifyPlaceOrder(List<AdminPlaceOrderDTO> products) throws Exception {
		boolean result = false;

		for (AdminPlaceOrderDTO product : products) {
			if (poDAO.updatePlaceOrder(product) != 0) {
				result = true;
			} else {
				return result;
			}
		}

		return result;
	}

	/**
	 * @methodName : modifyReceiving
	 * @author : sjy
	 * @param modifyReceivingList
	 * @return
	 * @throws Exception
	 * @returnValue : @param modifyReceivingList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:59:10
	 * @description : 입고 수정
	 */
	@Override
	public boolean modifyReceiving(List<AdminReceivingDTO> modifyReceivingList) throws Exception {
		boolean result = false;

		for (AdminReceivingDTO receiving : modifyReceivingList) {
			if (rDAO.updateReceivingModify(receiving) != 0) {
				result = true;
			} else {
				return result;
			}
		}

		return result;
	}

	/**
	 * @methodName : modifyCarryingOut
	 * @author : sjy
	 * @param modifyCarryingOutList
	 * @return
	 * @throws Exception
	 * @returnValue : @param modifyCarryingOutList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:59:15
	 * @description : 반출 수정
	 */
	@Override
	public boolean modifyCarryingOut(List<AdminCarryingOutDTO> modifyCarryingOutList) throws Exception {
		boolean result = false;

		for (AdminCarryingOutDTO carryingOut : modifyCarryingOutList) {
			if (coDAO.updateCarryingOutModify(carryingOut) != 0) {
				result = true;
			} else {
				return result;
			}
		}

		return result;
	}

	/**
	 * @methodName : modifyProduct
	 * @author : sjy
	 * @param product
	 * @param images
	 * @return
	 * @throws Exception
	 * @returnValue : @param product
	 * @returnValue : @param images
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:59:20
	 * @description : 상품 수정
	 */
	@Override
	public boolean modifyProduct(AdminProductVO product, List<String> images) throws Exception {
		boolean result = false;
		product.getProduct_description().replace("\r\n", "<br />");

		if (pDAO.updateProductModify(product) != 0) {
			AdminStockVO stock = new AdminStockVO(-1, product.getProduct_code(), product.getIs_auto_order(),
					product.getSafety_stock_quantity(), product.getStock_quantity());
			sDAO.updateProductStockModify(stock);

			if (images.size() > 0) {
				piDAO.deleteAllProductImg(product.getProduct_code());
				for (String path : images) {
					piDAO.insertProductImg(path, product.getProduct_code());
				}
			}

			result = true;
		}
		return result;
	}

	/**
	 * @methodName : deleteProduct
	 * @author : sjy
	 * @param product_code
	 * @return
	 * @throws Exception
	 * @returnValue : @param product_code
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 5:59:28
	 * @description : 상품 삭제
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteProduct(String product_code) throws Exception {
		boolean result = false;

		if (piDAO.deleteAllProductImg(product_code) != 0) {
			if (sDAO.deleteStock(product_code) != 0) {
				rDAO.deleteReceiving(product_code);
				poDAO.deleteOrder(product_code);
				if (pDAO.deleteProduct(product_code) != 0) {
					result = true;
				}
			}
		}

		return result;
	}

	/**
	 * @methodName : cancleEventProducts
	 * @author : sjy
	 * @param productList
	 * @return
	 * @throws Exception
	 * @returnValue : @param productList
	 * @returnValue : @return
	 * @date : 2023. 11. 8. 오후 4:42:55
	 * @description : 이벤트 상품 등록 해제
	 */
	@Override
	public boolean cancleEventProducts(List<String> productList, String event_no) throws Exception {
		boolean result = false;

		for (String product_code : productList) {
			if (pDAO.updateEventProductsCancel(product_code) != 0) {
				if (elDAO.deleteEventProduct(product_code, event_no) != 0) {
					result = true;
				}
			} else {
				return result;
			}
		}

		return result;
	}

	/**
	 * @methodName : modifyEvent
	 * @author : sjy
	 * @param event
	 * @return
	 * @throws Exception
	 * @returnValue : @param event
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 8. 오후 5:18:47
	 * @description : 이벤트 수정
	 */
	@Override
	public boolean modifyEvent(AdminEventVO event) throws Exception {
		boolean result = false;

		if (eDAO.updateEvent(event) != 0) {
			result = true;
		} else {
			return result;
		}

		return result;
	}

	/**
	 * @methodName : addEvent
	 * @author : sjy
	 * @param event
	 * @param products
	 * @return
	 * @throws Exception
	 * @returnValue : @param event
	 * @returnValue : @param products
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 8. 오후 5:46:27
	 * @description : 이벤트 등록 및 상품 이벤트 등록
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean addEvent(AdminEventVO event, List<String> eventProductList) throws Exception {
		boolean result = false;
		if (eDAO.insertEvent(event) != 0) {
			System.out.println("이벤트 저장");
			result = true;
			// String todayfm = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
			String todayfm = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

			// yyyy-MM-dd 포맷 설정
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			// 비교할 date와 today를 데이터 포맷으로 변경
			Date date = dateFormat.parse((event.getEvent_start()).toString());
			Date today = new Date(dateFormat.parse(todayfm).getTime());

			// compareTo메서드를 통한 날짜비교
			int compare = date.compareTo(today);

			for (String product_code : eventProductList) {
				if (compare == 0) {
					if (pDAO.updateEventRegist(product_code) != 0) {
						elDAO.insertEventLog(event, product_code);
						result = true;
					} else {
						return result;
					}
				} else {
					elDAO.insertEventLog(event, product_code);
				}
			}
		}
		return result;
	}

	/**
	 * @methodName : deleteEvent
	 * @author : sjy
	 * @param event_no
	 * @return
	 * @throws Exception
	 * @returnValue : @param event_no
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 13. 오후 6:14:23
	 * @description : 이벤트 삭제
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteEvent(int event_no) throws Exception {
		boolean result = false;

		if (pDAO.updateAllEvent(event_no) != 0 || pDAO.selectProductsByEvtNo(event_no).size() == 0) {
			if (eDAO.deleteEvent(event_no) != 0)
				result = true;
		} else {
			return result;
		}

		return result;
	}

	/**
	 * @methodName : getSearchedEvent
	 * @author : sjy
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 * @returnValue : @param searchCondition
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 9. 오후 3:15:03
	 * @description : 검색 조건에 맞는 이벤트 가져오기
	 */
	@Override
	public Map<String, Object> getSearchedEvent(EventSearchCondition searchCondition, int curPageNo) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		PagingInfo pi = createSearchedEventPagingInfo(curPageNo, searchCondition);
		System.out.println(pi);

		List<AdminEventVO> list = eDAO.selectEventByCondition(searchCondition, pi);

		result.put("eventList", list);
		result.put("pagingInfo", pi);

		return result;
	}

	/**
	 * @methodName : createSearchedEventPagingInfo
	 * @author : sjy
	 * @param curPageNo
	 * @param searchCondition
	 * @return
	 * @throws Exception
	 * @returnValue : @param curPageNo
	 * @returnValue : @param searchCondition
	 * @returnValue : @param string
	 * @returnValue : @return
	 * @date : 2023. 11. 17. 오전 11:41:14
	 * @description : 이벤트 검색 페이징
	 */
	private PagingInfo createSearchedEventPagingInfo(int curPageNo, EventSearchCondition searchCondition)
			throws Exception {
		PagingInfo pi = new PagingInfo();

		pi.setPageNo(curPageNo);

		pi.setTotalPostCnt(eDAO.selectSearchedTotalPostCnt(searchCondition));

		if (pi.getTotalPostCnt() > 0) {
			// 총 페이지 수 구하기
			pi.setTotalPageCnt();

			// 보여주기 시작할 글의 row index 번호
			pi.setStartRowIndex();

			// 몇 개의 페이징 블럭이 나오는 지
			pi.setTotalPagingBlockCnt();

			// 현재 페이지가 속한 페이징 블럭 번호 구하기
			pi.setPageBlockOfCurrentPage();

			// 현재 블럭의 시작 페이지 번호 구하기
			pi.setStartNumOfCurrentPagingBlock();

			// 현재 블럭의 끝 페이지 번호 구하기
			pi.setEndNumOfCurrentPagingBlock();
		}

		return pi;
	}

	/**
	 * @methodName : deleteSelectedOrder
	 * @author : sjy
	 * @param orderList
	 * @return
	 * @throws Exception
	 * @returnValue : @param orderList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 9. 오후 5:32:44
	 * @description : 선택한 발주 삭제
	 */
	@Override
	public boolean deleteSelectedOrder(List<String> orderList) throws Exception {
		boolean result = false;

		for (String place_order_no : orderList) {
			if (poDAO.deleteSelectedOrder(place_order_no) != 0) {
				result = true;
			} else {
				return result;
			}
		}

		return result;
	}

	/**
	 * @methodName : deleteOrder
	 * @author : sjy
	 * @param place_order_no
	 * @param product_code
	 * @return
	 * @throws Exception
	 * @returnValue : @param place_order_no
	 * @returnValue : @param product_code
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 9. 오후 5:51:57
	 * @description : 발주 삭제
	 */
	@Override
	public boolean deleteOrder(int place_order_no, String product_code) throws Exception {
		boolean result = false;

		if (poDAO.deleteOrderByCode(place_order_no, product_code) != 0) {
			result = true;
		} else {
			return result;
		}

		return result;
	}

	@Override
	public PagingInfo createPagingInfo(int curPageNo, String table) throws Exception {
		PagingInfo pi = new PagingInfo();

		pi.setPageNo(curPageNo);

		// 전체 글의 갯수
		switch (table) {
		case "product":
			pi.setTotalPostCnt(pDAO.selectTotalPostCnt());
			break;
		case "placeOrder":
			pi.setTotalPostCnt(poDAO.selectTotalPostCnt());
			break;
		case "receiving":
			pi.setTotalPostCnt(rDAO.selectTotalPostCnt());
			break;
		case "carryingOut":
			pi.setTotalPostCnt(coDAO.selectTotalPostCnt());
			break;
		case "event":
			pi.setTotalPostCnt(eDAO.selectTotalPostCnt());
			break;
		default:
			break;
		}
		// 총 페이지 수 구하기
		pi.setTotalPageCnt();
		
		if (pi.getTotalPostCnt() > 0) {
			// 보여주기 시작할 글의 row index 번호
			pi.setStartRowIndex();

			// 몇 개의 페이징 블럭이 나오는 지
			pi.setTotalPagingBlockCnt();

			// 현재 페이지가 속한 페이징 블럭 번호 구하기
			pi.setPageBlockOfCurrentPage();

			// 현재 블럭의 시작 페이지 번호 구하기
			pi.setStartNumOfCurrentPagingBlock();

			// 현재 블럭의 끝 페이지 번호 구하기
			pi.setEndNumOfCurrentPagingBlock();
		}
		
		return pi;
	}

	/**
	 * @methodName : confirmOrder
	 * @author : sjy
	 * @throws Exception
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 12. 오후 11:14:17
	 * @description : 발주가 3일 지났는지 확인 후 지났다면 상태를 바꾸고 바꾼 발주를 입고에 insert
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void confirmOrder() throws Exception {
		// 3일 지난 발주의 정보 가져오기
		List<AdminPlaceOrderDTO> list = poDAO.selectPastThreeDaysOrder();
		// 3일 지난 발주 목록 업데이트
		int orderResult = poDAO.updateOrderConfirm();
		if (orderResult > 0) {
			if (slDAO.insertSchedulerLog("발주 완료", orderResult)) {
				// receiving 테이블에 insert
				int receivingResult = rDAO.insertReceiving(list);
				if (receivingResult > 0) {
					slDAO.insertSchedulerLog("입고 등록", receivingResult);
				}
			}
		}
	}

	/**
	 * @methodName : confirmReceiving
	 * @author : sjy
	 * @throws Exception
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 12. 오후 11:23:06
	 * @description : 3일 지난 입고 완료 처리
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void confirmReceiving() throws Exception {
		// 3일 지난 입고의 상품 코드 가져오기
		List<String> productList = pDAO.selectReceivingProductCode();
		if (productList.size() > 0) {
			for (String product_code : productList) {
				sDAO.updateStock(product_code);
			}
			int result = rDAO.updateReceivingConfirm();
			if (result > 0) {
				slDAO.insertSchedulerLog("입고 완료", result);
			}
		}
	}

	/**
	 * @methodName : confirmCarryingOut
	 * @author : sjy
	 * @throws Exception
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 12. 오후 11:23:09
	 * @description : 3일 지난 반출 완료 처리
	 */
	@Override
	public void confirmCarryingOut() throws Exception {
		int result = coDAO.updateCarryingOutConfirm();
		if (result > 0) {
			slDAO.insertSchedulerLog("반출 완료", result);
		}
	}

	/**
	 * @methodName : eventEnd
	 * @author : sjy
	 * @throws Exception
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 13. 오후 3:00:01
	 * @description : 이벤트 기간이 지난 이벤트에 해당하는 상품 일반상품으로 초기화
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void eventEnd() throws Exception {
		// 기간이 지난 이벤트 가져오기
		List<Integer> eventEndList = eDAO.selectEventEndList();
		// 지난 이벤트에 해당하는 상품 일반 상품으로 변경
		for (Integer event_no : eventEndList) {
			System.out.println(pDAO.updateEvent(event_no));
			pDAO.updateEvent(event_no);
		}
		if (eventEndList.size() > 0) {
			slDAO.insertSchedulerLog("이벤트 종료", eventEndList.size());
		}
	}

	/**
	 * @methodName : checkStock
	 * @author : sjy
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 13. 오후 9:51:02
	 * @description : 재고 확인
	 */
	@Override
	public Map<String, Object> checkStock() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminProductVO> list = sDAO.selectStockCheck();

		result.put("productList", list);

		return result;
	}

	/**
	 * @methodName : autoOrder
	 * @author : sjy
	 * @throws Exception
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 13. 오후 10:53:52
	 * @description : 자동 발주인 상품을 스케줄러를 사용해 발주 테이블에 insert
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void autoOrder() throws Exception {
		// 자동 발주이고 안전 재고보다 재고가 적은 상품 가져오기
		List<AdminProductVO> productList = pDAO.selectAutoOrder();

		// 이미 발주 테이블에 있는 지 확인
		for (int i = (productList.size() - 1); i > -1; i--) {
			AdminProductVO item = productList.get(i);
			if (poDAO.selectIsNotPlaced(item) != 0) {
				productList.remove(item);
			}
		}

		// 발주 테이블에 insert
		if (productList.size() > 0) {
			int result = poDAO.insertPlaceOrderAuto(productList);
			if (result > 0) {
				slDAO.insertSchedulerLog("자동 발주", result);
			}
		}
	}

	@Override
	public Map<String, Object> getDashBoardData() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		int productCount = pDAO.selectProductsCount();
		int saledCount = doDAO.selectSaledCount();
		int soldOutCount = sDAO.selectSoldOutCount();
		int carriedOutCount = coDAO.selectCarriedOutCount();
		List<AdminProductRank> list = doDAO.selectFiveTopList();

		result.put("productCount", productCount);
		result.put("saledCount", saledCount);
		result.put("soldOutCount", soldOutCount);
		result.put("carriedOutCount", carriedOutCount);
		result.put("rankingList", list);

		return result;
	}

	@Override
	public void updateEventScheduler() throws Exception {
		int result = pDAO.selectEventToUpdate();
		if (result > 0) {
			slDAO.insertSchedulerLog("이벤트 업데이트", result);
			pDAO.updateEventScheduler();
		}
	}

	@Override
	public Map<String, Object> getProductsByEvtNoFromLog(int event_no) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<AdminProductVO> list = pDAO.selectProductsByEvtNoFromLog(event_no);

		result.put("productList", list);

		return result;
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
