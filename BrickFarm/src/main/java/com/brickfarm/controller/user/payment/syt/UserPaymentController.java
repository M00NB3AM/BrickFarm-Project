package com.brickfarm.controller.user.payment.syt;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brickfarm.dto.user.syt.UserCouponHeldDTO;
import com.brickfarm.dto.user.syt.UserOrderProductDTO;
import com.brickfarm.dto.user.syt.UserOrdersheetDTO;
import com.brickfarm.dto.user.syt.UserPaymentDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.dto.user.syt.UserCompleteDataDTO;
import com.brickfarm.etc.syt.PaymentInfo;
import com.brickfarm.service.user.payment.UserPaymentService;
import com.brickfarm.vo.user.syt.UserAddressVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;

@Controller
@RequestMapping("/user/payment/*")
public class UserPaymentController {
	
	@Inject
	private UserPaymentService paymentService;
	
	@PostMapping(value="orderPage")
	public void findOrderPagePost(@RequestParam List<Integer> shoppingCartItem, HttpServletRequest request, Model model) {
		UserMemberVO sessionMember = (UserMemberVO)request.getSession().getAttribute("loginMemberInfo");
		try {
			if(sessionMember != null) {
				UserMemberVO member = paymentService.getMember(sessionMember.getMember_no());
				// 회원 주소록 받아오기
				List<UserAddressVO> addressBookList = paymentService.getAddressBook(sessionMember.getMember_no());
				
				for(int i = 0; i < addressBookList.size(); i++) {
					String address = addressBookList.get(i).getAddress();
					int addressLength = address.length();
					int indexAddress = address.lastIndexOf(", ");
					
					addressBookList.get(i).setAddress(address.substring(0, indexAddress));
					addressBookList.get(i).setDetaile_address(address.substring(indexAddress + 2, addressLength));
				}
				
				// 쇼핑카트VO (가칭 product) 에서 필요한거 뽑아서 만들기(
				List<UserOrderProductDTO> list_detailed_order = paymentService.getDetailedOrder(shoppingCartItem);
				//보유쿠폰 리스트	
				List<UserCouponHeldDTO> list_coupon_held = paymentService.findCouponHeld(member.getMember_no()); 
				// 결제번호 만들기
				String merchant_uid = PaymentInfo.getInstance().createMerchantUid(member.getMember_id());
				String IMP = PaymentInfo.getInstance().IMP;
				model.addAttribute("IMP", IMP);
				model.addAttribute("merchant_uid", merchant_uid);
				model.addAttribute("list_coupon_held", list_coupon_held);
				model.addAttribute("list_detailed_order", list_detailed_order);
				model.addAttribute("member", member);
				model.addAttribute("addressBookList", addressBookList);
				
			} else {
				int errorCode = 900;
				Map<String, Object> errorResponse = new HashMap<String, Object>();
				errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
				errorResponse.put("errorCode", errorCode);
				errorResponse.put("errorMessage", "세션오류 : 로그인 확인후 이용해주세요.");

				model.addAttribute("errorResponse", errorResponse);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			int errorCode = 999;
			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorCode", errorCode);
			errorResponse.put("errorMessage", "시스템에러 : 다시 시도해주세요.");

			model.addAttribute("errorResponse", errorResponse);
		}
		
	}		
	
	@PostMapping(value="orderCompletePage")   
	public void inputCompleteOrder(@ModelAttribute UserOrdersheetDTO ordersheet, @ModelAttribute UserPaymentDTO payment, @ModelAttribute UserCompleteDataDTO completeData, 
			@RequestParam List<String> product_code, @RequestParam List<Integer> quantity, HttpServletRequest request, Model model) {
		//세션 정보를 통해 멤버 정보 받아오기.
		UserMemberVO sessionMember = (UserMemberVO)request.getSession().getAttribute("loginMemberInfo");
		
		List<UserPaymentListDTO> paymentList = new ArrayList<UserPaymentListDTO>();
		for (int i = 0; i < product_code.size(); i++) {
			UserPaymentListDTO paymentData = new UserPaymentListDTO(product_code.get(i), quantity.get(i));
			paymentList.add(paymentData);
		}
		
		try {
			UserMemberVO member = paymentService.getMember(sessionMember.getMember_no());
			Map<String, Object> result = paymentService.makeCompleteOrderByDataToUse(ordersheet, payment, completeData, paymentList, member);
			
			String errorCode = (String)result.get("errorCode");
			if(errorCode == "000") {
				UserOrdersheetDTO ordersheetData = (UserOrdersheetDTO)result.get("ordersheet");
				UserPaymentDTO paymentData = (UserPaymentDTO)result.get("payment");
				List<UserOrderProductDTO> orderProductList = (List<UserOrderProductDTO>)result.get("orderProductList");
				int total_price = (int)result.get("total_price");
				int coupon_discounted_price = (int)result.get("coupon_discounted_price");
				
				model.addAttribute("ordersheet", ordersheetData);
				model.addAttribute("payment", paymentData);
				model.addAttribute("orderProductList", orderProductList);
				model.addAttribute("paymentType", completeData.getPaymentType());
				model.addAttribute("total_price", total_price);
				model.addAttribute("coupon_discounted_price", coupon_discounted_price);
			} 

			// 검증 실패에 대한 에러코드 (쿠폰,포인트,재고)
			model.addAttribute("errorCode", errorCode);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			Map<String, Object> errorResponse = new HashMap<String, Object>();
			errorResponse.put("createdAt", new Timestamp(System.currentTimeMillis()));
			errorResponse.put("errorMessage", "시스템에러");
			
			int errorCode = 999;
			
			model.addAttribute("errorCode", errorCode);
			model.addAttribute("errorResponse", errorResponse);
		}
		
	}
	
}
