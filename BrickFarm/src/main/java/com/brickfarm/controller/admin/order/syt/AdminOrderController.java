package com.brickfarm.controller.admin.order.syt;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brickfarm.dto.admin.syt.AdminExchangeDTO;
import com.brickfarm.dto.admin.syt.AdminCancelReturnDTO;
import com.brickfarm.dto.admin.syt.AdminDeliveryDTO;
import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.admin.syt.AdminSearchDTO;
import com.brickfarm.service.admin.order.AdminOrderService;


@Controller
@RequestMapping("admin/order/*")
public class AdminOrderController {

	@Inject
	public AdminOrderService orderService;
	
	@GetMapping("exchange")
	public void loadManageOrderExchangeGet() {}
	
	/**
	 * @methodName : loadManageOrderExchangePost
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @param model
	 * @return
	 * @throws ParseException
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @param model
	 * @returnValue : @return
	 * @returnValue : @throws ParseException
	 * @date : 2023. 11. 2. 오후 6:41:00
	 * @description : [교환] 데이터(검색결과 적용포함) 로드
	 */
	@PostMapping("exchange/search")
	public ResponseEntity<Map<String, Object>> loadManageOrderExchangePost(@RequestParam(defaultValue = "1", value="pageNo") int pageNo, 
			@ModelAttribute AdminSearchDTO searchDto, Model model) {
		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> data = orderService.searchOrderExchange(pageNo, searchDto);
			data.put("searchDto", searchDto);
			result = new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}

	/**
	 * @methodName : changeStateInCheck
	 * @author : syt
	 * @param exchange_no
	 * @param post_number
	 * @return
	 * @returnValue : @param exchange_no
	 * @returnValue : @param post_number
	 * @returnValue : @return
	 * @date : 2023. 11. 2. 오후 5:27:41
	 * @description : [교환] 상태변경:확인,진행중,완료 통합
	 */
	@PostMapping("exchange/changeStateIn{state}")
	public ResponseEntity<Map<String, Object>> changeStateExchange(@RequestParam List<Integer> exchange_no, 
			@RequestParam(required=false) List<String> post_number, 
			@RequestParam(name="state") String state) {
		ResponseEntity<Map<String, Object>> result = null;
		
		List<AdminExchangeDTO> exchangeList = new ArrayList<AdminExchangeDTO>();
		for(int i =0; i < exchange_no.size(); i++) {	
			Timestamp now = new Timestamp(System.currentTimeMillis());
			AdminExchangeDTO exchange = null;
			if(state.equals("Check")) {
				exchange = new AdminExchangeDTO(exchange_no.get(i), "확인", null, now, null, null);
			} else if(state.equals("Process")) {
				exchange = new AdminExchangeDTO(exchange_no.get(i), "진행중", post_number.get(i), null, now, null);
			} else if(state.equals("Complete")) {
				exchange = new AdminExchangeDTO(exchange_no.get(i), "완료", null, null, null, now);
			}
			exchangeList.add(exchange);
		}
		
		try {
			if(state.equals("Check") || state.equals("Process")) {
				if(orderService.changeStateExchange(exchangeList)) {
					result = new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
				}
			} else if(state.equals("Complete")) {
				if(orderService.changeStateExchangeByComplete(exchangeList, exchange_no)) {
					result = new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
				}
			}
				
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
//	========================================================================================================================================================================
	
	@GetMapping("return")
	public void loadManageOrderReturnGet() {}
	
	/**
	 * @methodName : loadManageOrderReturnPost
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @param model
	 * @return
	 * @throws ParseException
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @param model
	 * @returnValue : @return
	 * @returnValue : @throws ParseException
	 * @date : 2023. 11. 2. 오후 9:23:18
	 * @description : [반품] 데이터(검색결과 적용포함) 로드
	 */
	@PostMapping("return/search")
	public ResponseEntity<Map<String, Object>> loadManageOrderReturnPost(@RequestParam(defaultValue = "1", value="pageNo") int pageNo, 
			@ModelAttribute AdminSearchDTO searchDto) {
		ResponseEntity<Map<String, Object>> result = null;
		
		try {
			Map<String, Object> data = orderService.searchOrderReturn(pageNo, searchDto);
			data.put("searchDto", searchDto);
			
			result = new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	/**
	 * @methodName : changeStateReturn
	 * @author : syt
	 * @param cancellation_return_no
	 * @param state
	 * @return
	 * @throws Exception
	 * @returnValue : @param cancellation_return_no
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 4. 오후 5:29:02
	 * @description : [반품] 상태변경:확인,완료 통합
	 */
	@PostMapping("return/changeStateIn{state}")
	public ResponseEntity<Map<String, Object>> changeStateReturn(@RequestParam List<Integer> cancellation_return_no, 
			@RequestParam(name="state") String state ) {
		
		ResponseEntity<Map<String, Object>> result = null;
		
		List<AdminCancelReturnDTO> returnList = new ArrayList<AdminCancelReturnDTO>();
		for(int i =0; i < cancellation_return_no.size(); i++) {	
			Timestamp now = new Timestamp(System.currentTimeMillis());
			if(state.equals("Check")) {
				AdminCancelReturnDTO returnData = new AdminCancelReturnDTO(cancellation_return_no.get(i), "확인", now, null, -1);
				returnList.add(returnData);
			} 
		}
		
		
		try {
			if(state.equals("Check")) {
				if(orderService.changeStateReturn(returnList)) {
					result = new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
				}
			} else if(state.equals("Complete")) {
				Map<String, Object> failList = orderService.changeStateReturnByComplete(cancellation_return_no);
				result = new ResponseEntity<Map<String, Object>>(failList, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
//	========================================================================================================================================================================	
	/**
	 * @methodName : loadManageOrderCancelGet
	 * @author : syt
	 * @throws Exception
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 9:16:49
	 * @description : [취소] GET
	 */
	@GetMapping("cancel")
	public void loadManageOrderCancelGet() {}
	
	/**
	 * @methodName : loadManageOrderCancelPost
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @param model
	 * @return
	 * @throws Exception
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @param model
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 9:16:47
	 * @description : [취소] 데이터(검색결과 적용포함) 로드
	 */
	@PostMapping("cancel/search")
	public ResponseEntity<Map<String, Object>> loadManageOrderCancelPost(@RequestParam(defaultValue = "1", value="pageNo") int pageNo, 
			@ModelAttribute AdminSearchDTO searchDto) {
		ResponseEntity<Map<String, Object>> result = null;
		
		try {
			Map<String, Object> data = orderService.searchOrderCancel(pageNo, searchDto);
			data.put("searchDto", searchDto);
			result = new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			
			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return result;
	}
	
	/**
	 * @methodName : changeStateCancel
	 * @author : syt
	 * @param cancellation_return_no
	 * @param state
	 * @return
	 * @throws Exception
	 * @returnValue : @param cancellation_return_no
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 9:19:03
	 * @description : [취소] 상태변경:확인,완료 통합
	 */
	@PostMapping("cancel/changeStateIn{state}")
	public ResponseEntity<Map<String, Object>> changeStateCancel(@RequestParam List<Integer> cancellation_return_no, 
			@RequestParam(name="state") String state ) {
		
		ResponseEntity<Map<String, Object>> result = null;
		
		List<AdminCancelReturnDTO> cancelList = new ArrayList<AdminCancelReturnDTO>();
		for(int i =0; i < cancellation_return_no.size(); i++) {	
			Timestamp now = new Timestamp(System.currentTimeMillis());
			if(state.equals("Check")) {
				AdminCancelReturnDTO returnData = new AdminCancelReturnDTO(cancellation_return_no.get(i), "확인", now, null, -1);
				cancelList.add(returnData);
			} 
		}
		
		try {
			if(state.equals("Check")) {
				if(orderService.changeStateCancel(cancelList)) {
					result = new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
				}
			} else if(state.equals("Complete")) {
				Map<String, Object> failList = orderService.changeStateCancelByComplete(cancellation_return_no);
				result = new ResponseEntity<Map<String, Object>>(failList, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
//	========================================================================================================================================================================
	@GetMapping("manageOrder")
	public void loadManageOrderGet() throws Exception {}
	
	/**
	 * @methodName : loadManageOrderPost
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @param model
	 * @return
	 * @throws Exception
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @param model
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 6:12:56
	 * @description : [주문관리] 데이터(검색결과 적용포함) 로드
	 */
	@PostMapping("manageOrder/search")
	public ResponseEntity<Map<String, Object>> loadManageOrderPost(@RequestParam(defaultValue = "1", value="pageNo") int pageNo, 
			@ModelAttribute AdminSearchDTO searchDto) {
		ResponseEntity<Map<String, Object>> result = null;
		try {
			Map<String, Object> data = orderService.searchOrder(pageNo, searchDto);
			data.put("searchDto", searchDto);
			
			result = new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		return result;
	}
	
	/**
	 * @methodName : loadManageOrderDetailPost
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @param model
	 * @return
	 * @throws Exception
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @param model
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 23. 오후 3:19:03
	 * @description : [주문관리] 디테일 정보 로드
	 */
	@PostMapping("manageOrder/detail")
	public ResponseEntity<Map<String, Object>> loadManageOrderDetailPost(@RequestParam int detailed_order_no) {
		ResponseEntity<Map<String, Object>> result = null;
		
		try {
			Map<String, Object> data = orderService.getOrderDetail(detailed_order_no);
			result = new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	/**
	 * @methodName : changeStateExchange
	 * @author : syt
	 * @param detailed_order_no
	 * @param state
	 * @return
	 * @throws Exception
	 * @returnValue : @param detailed_order_no
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 9. 오후 12:48:30
	 * @description : [주문관리] 상태 변경
	 */
	@PostMapping("manageOrder/changeStateIn{state}")
	public ResponseEntity<Map<String, Object>> changeStateManageOrderPost(@RequestParam List<Integer> detailed_order_no, @RequestParam(name="state") String state,
			@RequestParam(required=false) List<String> reason, @RequestParam(required=false) List<String> negligence, 
			@RequestParam(required=false) List<String> depositor_name, @RequestParam(required=false) List<Integer> cancel_money) {
		ResponseEntity<Map<String, Object>> result = null;

		List<AdminDetailedOrderDTO> detailedOrderList = new ArrayList<AdminDetailedOrderDTO>();
		for(int i =0; i < detailed_order_no.size(); i++) {	
			AdminDetailedOrderDTO detailedOrder = null;
			if(state.equals("beforePayment")) {
				detailedOrder = new AdminDetailedOrderDTO(detailed_order_no.get(i), null, null, null, -1);
			} else if(state.equals("afterPayment")) {
				detailedOrder = new AdminDetailedOrderDTO(detailed_order_no.get(i), depositor_name.get(i), null, null, -1);
			} else if(state.equals("cancel")) {
				detailedOrder = new AdminDetailedOrderDTO(detailed_order_no.get(i), null, reason.get(i), negligence.get(i), cancel_money.get(i));
			} else if(state.equals("exchange")) {
				detailedOrder = new AdminDetailedOrderDTO(detailed_order_no.get(i), null, reason.get(i), null, -1);
			} else if(state.equals("return")) {
				detailedOrder = new AdminDetailedOrderDTO(detailed_order_no.get(i), null, reason.get(i), negligence.get(i), cancel_money.get(i));
			} else if(state.equals("complete")) {
				detailedOrder = new AdminDetailedOrderDTO(detailed_order_no.get(i), null, null, null, -1);
			}
			detailedOrderList.add(detailedOrder);
		}
		
		try {
			if(state.equals("beforePayment") || state.equals("afterPayment")) {
				if(orderService.changeStateManageOrderPayment(detailedOrderList, state)) {
					result = new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
				}
			} else if(state.equals("exchange")) {
				if(orderService.changeStateManageOrderExchange(detailedOrderList, "교환")) {
					result = new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
				}
			} else if(state.equals("cancel") || state.equals("return")) {
				if(orderService.changeStateManageOrderCancelReturn(detailedOrderList, state)) {
					result = new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
				}
			} else if(state.equals("complete")) {
				if(orderService.changeStateManageOrderComplete(detailedOrderList, "구매확정")) {
					result = new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	// --------------------------------------------------------------------------------------------------------------------
	@GetMapping("delivery")
	public void loadManageOrderDeliveryGet() {}
	
	/**
	 * @methodName : loadManageOrderDeliveryPost
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @param model
	 * @return
	 * @throws Exception
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @param model
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 15. 오후 3:44:49
	 * @description : [배송관리] 데이터(검색결과 적용포함) 로드
	 */
	@PostMapping("delivery/search")
	public ResponseEntity<Map<String, Object>> loadManageOrderDeliveryPost(@RequestParam(defaultValue = "1", value="pageNo") int pageNo, 
			@ModelAttribute AdminSearchDTO searchDto) {
		ResponseEntity<Map<String, Object>> result = null;
		
		try {
			Map<String, Object> data = orderService.searchOrderDelivery(pageNo, searchDto);
			data.put("searchDto", searchDto);
			result = new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}
	/**
	 * @methodName : loadManageOrderDeliveryDetailPost
	 * @author : syt
	 * @param merchant_uid
	 * @return
	 * @throws Exception
	 * @returnValue : @param merchant_uid
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 23. 오전 11:35:06
	 * @description : [배송관리] 상세데이터 로드
	 */
	@PostMapping("delivery/detail")
	public ResponseEntity<Map<String, Object>> loadManageOrderDeliveryDetailPost(@RequestParam String merchant_uid) {
		ResponseEntity<Map<String, Object>> result = null;
		
		try {
			Map<String, Object> data = orderService.getOrderDeliveryDetail(merchant_uid);
			result =new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	/**
	 * @methodName : changeStateManageOrderDeliveryPost
	 * @author : syt
	 * @param detailed_order_no
	 * @param state
	 * @param post_number
	 * @return
	 * @throws Exception
	 * @returnValue : @param detailed_order_no
	 * @returnValue : @param state
	 * @returnValue : @param post_number
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 15. 오후 3:44:07
	 * @description : [배송관리] 상태 변경
	 */
	@PostMapping("delivery/changeState")
	public ResponseEntity<Map<String, Object>> changeStateManageOrderDeliveryPost(@RequestParam List<String> merchant_uid, @RequestParam(name="state") String state, @RequestParam(required=false) List<String> post_number) {
		ResponseEntity<Map<String, Object>> result = null;
		List<AdminDeliveryDTO> deliveryList = new ArrayList<AdminDeliveryDTO>();
		for(int i =0; i < merchant_uid.size(); i++) {	
			AdminDeliveryDTO delivery = new AdminDeliveryDTO();
			if(state.equals("paymentwait") || state.equals("prepare")) {
				delivery = new AdminDeliveryDTO(merchant_uid.get(i), null);
			} else if(state.equals("deliverywait")) {
				delivery = new AdminDeliveryDTO(merchant_uid.get(i), post_number.get(i));
			} 
			deliveryList.add(delivery);
		}
		
		try {
			if(orderService.changeDeliveryState(deliveryList, state)) {
				Map<String, Object> success = new HashMap<String, Object>();
				success.put("success", "success");
				result = new ResponseEntity<Map<String, Object>>(success, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			result = new ResponseEntity<Map<String, Object>>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	
	/**
	 * @methodName : loadManageOrderDashboardGet
	 * @author : syt
	 * @throws Exception
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 22. 오후 5:55:43
	 * @description : [대시보드]
	 */
	@GetMapping("dashboard")
	public void loadManageOrderDashboardGet(Model model) {
		
		try {
			Map<String, Object> result= orderService.getDashboardData();
			
			int dashboardCount = (int)result.get("dashboardCount");
			String dashboardSales = (String)result.get("dashboardSales");
			int dashboardDeliveryCount = (int)result.get("dashboardDeliveryCount");
			int dashboardStateCount = (int)result.get("dashboardStateCount");
			
			model.addAttribute("dashboardCount", dashboardCount);
			model.addAttribute("dashboardSales", dashboardSales);
			model.addAttribute("dashboardDeliveryCount", dashboardDeliveryCount);
			model.addAttribute("dashboardStateCount", dashboardStateCount);
			
		} catch (Exception e) {
			e.printStackTrace();

			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", "999");
			errorResponse.put("errorMessage", "시스템에러");
			
			model.addAttribute("errorResponse", errorResponse);
		}
	}

}
