package com.brickfarm.service.admin.product;

import java.util.List;
import java.util.Map;

import com.brickfarm.dto.admin.sjy.AdminCarryingOutDTO;
import com.brickfarm.dto.admin.sjy.AdminPlaceOrderDTO;
import com.brickfarm.dto.admin.sjy.AdminReceivingDTO;
import com.brickfarm.etc.sjy.CarryingOutSearchCondition;
import com.brickfarm.etc.sjy.EventSearchCondition;
import com.brickfarm.etc.sjy.PagingInfo;
import com.brickfarm.etc.sjy.PlaceOrderCondition;
import com.brickfarm.etc.sjy.ProductSearchCondition;
import com.brickfarm.etc.sjy.ReceivingSearchCondition;
import com.brickfarm.vo.admin.sjy.AdminEventVO;
import com.brickfarm.vo.admin.sjy.AdminProductVO;

public interface ProductManagingService {
	// ==[송지영]==========================================================================================================================================
	// 모든 상품 가져오기
	Map<String, Object> getAllProducts() throws Exception;
	// 모든 이미지 가져오기
	Map<String, Object> getAllImgByCode(int product_code) throws Exception;
	// 입고 목록 가져오기
	Map<String, Object> getAllReceiving(int curPageNo) throws Exception;
	// 발주 목록 가져오기
	Map<String, Object> getAllPlaceOrder(int curPageNo) throws Exception;
	// 반출 목록 가져오기
	Map<String, Object> getAllCarryingOut(int curPageNo) throws Exception;
	// 이벤트 목록 가져오기
	Map<String, Object> getAllEvent(int curPageNo) throws Exception;
	// 카테고리 가져오기
	Map<String, Object> getAllCategory() throws Exception;
	// 상품 상세 정보 가져오기
	Map<String, Object> getProductDetail(int product_code) throws Exception;
	// 이벤트 상세 정보 가져오기
	Map<String, Object> getEventDetail(int event_no) throws Exception;
	// 발주 번호로 목록 가져오기
	Map<String, Object> getPlaceOrderByNo(int place_order_no) throws Exception; 
	// 입고 번호로 목록 가져오기
	Map<String, Object> getReceivingByNo(int receiving_no) throws Exception; 
	// 반출 번호로 목록 가져오기
	Map<String, Object> getCarryingOutByNo(int carrying_out_no) throws Exception;
	// 이벤트 번호로 목록 가져오기
	Map<String, Object> getProductsByEvtNo(int event_no) throws Exception;
	// 상품 검색
	Map<String, Object> getAllProducts(ProductSearchCondition searchCondition) throws Exception;
	// 입고 검색
	Map<String, Object> getAllReceiving(ReceivingSearchCondition searchCondition, int curPageNo) throws Exception;
	// 발주 검색
	Map<String, Object> getAllPlaceOrder(PlaceOrderCondition searchCondition, int curPageNo) throws Exception;
	// 반출 검색
	Map<String, Object> getAllCarryingOut(CarryingOutSearchCondition searchCondition, int curPageNo) throws Exception;
	// 상품 등록
	boolean addProduct(AdminProductVO product, List<String> images) throws Exception;
	// 발주 등록
	boolean addPlaceOrder(List<AdminPlaceOrderDTO> products) throws Exception;
	// 입고 완료
	boolean completeReceiving(AdminReceivingDTO receiving) throws Exception;
	// 반출 완료
	boolean completeCarryingOut(AdminCarryingOutDTO carryingOut) throws Exception;
	// 발주 수정
	boolean modifyPlaceOrder(List<AdminPlaceOrderDTO> products) throws Exception;
	// 선택한 발주 삭제
	boolean deleteSelectedOrder(List<String> orderList) throws Exception;
	// 발주 삭제
	boolean deleteOrder(int place_order_no, String product_code) throws Exception;
	// 입고 수정
	boolean modifyReceiving(List<AdminReceivingDTO> modifyReceivingList) throws Exception;
	// 반출 수정
	boolean modifyCarryingOut(List<AdminCarryingOutDTO> modifyCarryingOutList) throws Exception;
	// 상품 수정
	boolean modifyProduct(AdminProductVO product, List<String> images) throws Exception;
	// 상품 삭제
	boolean deleteProduct(String product_code) throws Exception;
	// 이벤트 등록 해제
	boolean cancleEventProducts(List<String> productList, String event_no) throws Exception;
	// 이벤트 수정
	boolean modifyEvent(AdminEventVO event) throws Exception;
	// 이벤트 등록
	boolean addEvent(AdminEventVO event, List<String> eventProductList) throws Exception;
	// 이벤트 검색
	Map<String, Object> getSearchedEvent(EventSearchCondition searchCondition, int curPageNo) throws Exception;
	// 이벤트 삭제
	boolean deleteEvent(int event_no) throws Exception;
	// 페이지네이션
	PagingInfo createPagingInfo(int curPageNo, String table) throws Exception;
	// 발주 확인
	void confirmOrder() throws Exception;
	// 입고 확인
	void confirmReceiving() throws Exception;
	// 반출 확인
	void confirmCarryingOut() throws Exception;
	// 이벤트 확인
	void eventEnd() throws Exception;
	// 안전 재고 확인
	Map<String, Object> checkStock() throws Exception;
	// 자동 발주
	void autoOrder() throws Exception;
	// 대시보드 데이터
	Map<String, Object> getDashBoardData() throws Exception;
	// 스케쥴러로 상품에 이벤트 업데이트
	void updateEventScheduler() throws Exception;
	// 이벤트 로그에서 상품 가져오기
	Map<String, Object> getProductsByEvtNoFromLog(int event_no) throws Exception;
	// 이벤트 목록 가져오기
	Map<String, Object> getAllEvent() throws Exception;
	// 상품 페이징
	Map<String, Object> getAllProducts(int curPageNo) throws Exception;
	// 검색 상품 페이징
	Map<String, Object> getAllProducts(ProductSearchCondition searchCondition, int curPageNo) throws Exception;
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
