package com.brickfarm.service.user.payment;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.brickfarm.dao.couponheld.CouponHeldDAO;
import com.brickfarm.dao.couponlog.CouponLogDAO;
import com.brickfarm.dao.detailedorder.DetailedOrderDAO;
import com.brickfarm.dao.member.MemberDAO;
import com.brickfarm.dao.memberAddressBook.MemberAddressBookDAO;
import com.brickfarm.dao.ordersheet.OrderSheetDAO;
import com.brickfarm.dao.payment.PaymentDAO;
import com.brickfarm.dao.pointsusagelog.PointsUsageLogDAO;
import com.brickfarm.dao.product.ProductDAO;
import com.brickfarm.dao.shoppingcart.ShoppingCartDAO;
import com.brickfarm.dao.stock.StockDAO;
import com.brickfarm.dto.user.syt.CancelDataDTO;
import com.brickfarm.dto.user.syt.UserAddressBookDTO;
import com.brickfarm.dto.user.syt.UserCompleteDataDTO;
import com.brickfarm.dto.user.syt.UserCouponHeldDTO;
import com.brickfarm.dto.user.syt.UserCouponLogDTO;
import com.brickfarm.dto.user.syt.UserDetailedOrderDTO;
import com.brickfarm.dto.user.syt.UserOrderProductDTO;
import com.brickfarm.dto.user.syt.UserOrdersheetDTO;
import com.brickfarm.dto.user.syt.UserPaymentDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.dto.user.syt.UserPointsUsageLogDTO;
import com.brickfarm.etc.syt.PaymentInfo;
import com.brickfarm.vo.user.syt.UserAddressVO;
import com.brickfarm.vo.user.ysh.UserMemberVO;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {
	@Inject
	private CouponHeldDAO couponHeldDao;
	@Inject
	private PaymentDAO paymentDao;
	@Inject
	private OrderSheetDAO orderSheetDao;
	@Inject
	private CouponLogDAO couponLogDao;
	@Inject
	private PointsUsageLogDAO pointsUsageLogDao;
	@Inject
	private MemberDAO memberDao;
	@Inject
	private DetailedOrderDAO detailedOrderDao;
	@Inject
	private ShoppingCartDAO shoppingCartDao;
	@Inject
	private StockDAO stockDao;
	@Inject
	private ProductDAO productDao;
	@Inject
	private MemberAddressBookDAO memberAddressBookDao;

	// ==[송지영]==========================================================================================================================================
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
	/**
	 * @methodName : findCouponHeld
	 * @author : syt
	 * @param member_no
	 * @return
	 * @throws Exception
	 * @returnValue : @param member_no
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 28. 오후 5:05:16
	 * @description : 회원이 가지고 있는 쿠폰정보 얻기
	 */
	@Override
	public List<UserCouponHeldDTO> findCouponHeld(int member_no) throws Exception {
		return couponHeldDao.selectCouponHeld(member_no);
	}

	/**
	 * @methodName : makeCompleteOrderByDataToUse
	 * @author : syt
	 * @param ordersheet
	 * @param payment
	 * @param completeData
	 * @param product_code
	 * @param quantity
	 * @param member
	 * @return
	 * @throws Exception 
	 * @returnValue : @param ordersheet
	 * @returnValue : @param payment
	 * @returnValue : @param completeData
	 * @returnValue : @param product_code
	 * @returnValue : @param quantity
	 * @returnValue : @param member
	 * @returnValue : @return
	 * @date : 2023. 11. 18. 오후 5:04:56
	 * @description : 결제에 필요한 객체 만드는 메서드
	 */
	@Override
	public Map<String, Object> makeCompleteOrderByDataToUse(UserOrdersheetDTO ordersheet, UserPaymentDTO payment,
			UserCompleteDataDTO completeData, List<UserPaymentListDTO> paymentList, UserMemberVO member) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String errorCode = "000";
		
		String verifyCode = verifyAll(member.getMember_no(), payment.getPoint_pay_money(), ordersheet.getCoupon_held_no(), paymentList);
		if (verifyCode.equals("000")) {	
			// 검증을 위한 금액 계산 ( 밑에 100대신 validationPrice )
			int validationPrice = getValidationPrice(paymentList, ordersheet.getCoupon_held_no(), payment.getPost_money(), payment.getPoint_pay_money());
			// 결제액이 0원이 아닐때 결제API 검증을함.
			if(completeData.getPay_money() != 0) {
				// 검증함수 호출하여 리턴 값 받아오기
				IamportResponse<Payment> responsePayment = PaymentInfo.getInstance().verifyPayment(validationPrice, payment.getImp_uid());
				if (responsePayment != null) { // 검증 성공하면
					
					List<UserAddressVO> addressBookList = getAddressBook(member.getMember_no());
					
					// ordersheet 값 설정
					ordersheet.setMerchant_uid(responsePayment.getResponse().getMerchantUid());
					ordersheet.setMember_no(member.getMember_no());
					
					ordersheet.setTotal_pay_money(completeData.getPay_money() + payment.getPoint_pay_money());
					if (ordersheet.getMemo().equals("")) {
						ordersheet.setMemo(null);
					}
					
					// payment 값 설정
					payment.setMerchant_uid(responsePayment.getResponse().getMerchantUid());
					payment.setTotal_pay_money(ordersheet.getTotal_pay_money());
					payment.setTotal_discounted_price(ordersheet.getTotal_pay_money() - payment.getPost_money());
					
					// 카드,현금에 따른 payment, ordersheet 값 설정
					if (completeData.getPaymentType().equals("vbank")) {
						payment.setCash_pay_money(completeData.getPay_money());
						payment.setVirtual_account_bank_brand(responsePayment.getResponse().getVbankName());
						payment.setVirtual_account_number(responsePayment.getResponse().getVbankNum());
						payment.setDeposit_deadline(new Timestamp(responsePayment.getResponse().getVbankDate().getTime()));
						ordersheet.setDelivery_state("결제대기");
						
					} else if (completeData.getPaymentType().equals("card")) {
						Timestamp now = new Timestamp(System.currentTimeMillis());
						payment.setDeposit_time(now);
						payment.setCard_pay_money(completeData.getPay_money());
						payment.setCard_number(responsePayment.getResponse().getCardNumber().toString());
						
						if (!responsePayment.getResponse().getPayMethod().equals("point")) {
							payment.setCard_brand(responsePayment.getResponse().getCardName().toString());
						} else {
							payment.setCard_brand("카카오페이");
						}
						ordersheet.setDelivery_state("배송준비중");
					}
					
					List<UserOrderProductDTO> orderProductList = productDao.selectProductDataAllList(paymentList);
					
					int total_price = 0;  // 이벤트 할인 적용 X 
					int total_product_price = 0;  // 이벤트 할인 적용
					int total_discounted_price = 0;  // 이벤트 할인 적용 * 쿠폰적용
					// UserDetailedOrderDTO 만들기
					List<UserDetailedOrderDTO> detailedOrderList = new ArrayList<UserDetailedOrderDTO>();
					for (int i = 0; i < orderProductList.size(); i++) {
						UserDetailedOrderDTO detailedOrder = null;
						int product_price = Integer.parseInt(orderProductList.get(i).getProduct_price().replace(",", ""));
						int event_Discounted_price = Integer.parseInt(orderProductList.get(i).getEvent_product_price().replace(",", ""));
						int discounted_price = Math.round(event_Discounted_price * (1 - completeData.getDiscount_rate()));
						String product_code = orderProductList.get(i).getProduct_code();
						int quantity = orderProductList.get(i).getQuantity();
						
						total_price += product_price * quantity;
						total_product_price += event_Discounted_price * quantity;
						total_discounted_price += discounted_price * quantity;
						
						if (completeData.getPaymentType().equals("vbank")) {
							detailedOrder = new UserDetailedOrderDTO(-1, ordersheet.getMerchant_uid(), product_code,
									event_Discounted_price, discounted_price, quantity, "결제대기");
						} else if (completeData.getPaymentType().equals("card")) {
							detailedOrder = new UserDetailedOrderDTO(-1, ordersheet.getMerchant_uid(), product_code,
									event_Discounted_price, discounted_price, quantity, "결제완료");
						}
						detailedOrderList.add(detailedOrder);
					}
					int coupon_discounted_price = total_product_price - total_discounted_price;
					
					// 이벤트할인된 총합 금액 넣어주기
					ordersheet.setTotal_product_price(total_product_price);
					
					// 주문자 새로운 정보 입력 (저장 체크는 안한상황)
					if (ordersheet.getRecipient() == null) {
						if(addressBookList.size() > 0) {
							ordersheet.setRecipient(addressBookList.get(0).getRecipient());
							ordersheet.setRecipient_zip_code(addressBookList.get(0).getZip_code());
							ordersheet.setRecipient_address(addressBookList.get(0).getAddress());
							ordersheet.setRecipient_phone(addressBookList.get(0).getPhone_number());
						} else {
							ordersheet.setRecipient(member.getMember_name());
							ordersheet.setRecipient_zip_code(member.getZip_code());
							ordersheet.setRecipient_address(member.getAddress());
							ordersheet.setRecipient_phone(member.getPhone_number());
						}
						
					} else {
						// 주소 편집
						String address = ordersheet.getRecipient_address();
						String detaile_address = completeData.getRecipient_detaile_address();
						ordersheet.setRecipient_address(address + ", " + detaile_address);
					}
					
					// 새로고침 예외처리
					if (hasPaymentByImpUid(payment.getImp_uid())) {
						if (inputCompleteOrder(ordersheet, payment, detailedOrderList, completeData)) {
							result.put("ordersheet", ordersheet);
							result.put("orderProductList", orderProductList);
							result.put("payment", payment);
							result.put("errorCode", errorCode);
							result.put("total_price", total_price);
							result.put("coupon_discounted_price", coupon_discounted_price);
						}
					} else {
						result.put("ordersheet", ordersheet);
						result.put("orderProductList", orderProductList);
						result.put("payment", payment);
						result.put("errorCode", errorCode);
						result.put("total_price", total_price);
						result.put("coupon_discounted_price", coupon_discounted_price);
					}
					
				} else { // 결제 검증 실패
					if (completeData.getPaymentType().equals("card")) {
						BigDecimal cancel_request_amount = new BigDecimal(completeData.getPay_money());
						BigDecimal checksum = new BigDecimal(completeData.getPay_money());
						CancelDataDTO cancelData = new CancelDataDTO(payment.getImp_uid(), cancel_request_amount, "검증실패",
								checksum);
						PaymentInfo.getInstance().cancelPaymentByImpUid(cancelData);	
					}
					
					errorCode = "001";
					result.put("errorCode", errorCode);
				}
				
			// 결제금액이 0원이라 결제 API를 사용하지 않았음.
			} else if(completeData.getPay_money() == 0) {
				// 결제 금액과 디비 금액 검증성공
				if(completeData.getPay_money() == validationPrice) {
					List<UserAddressVO> addressBookList = getAddressBook(member.getMember_no());
					
					// ordersheet 값 설정
					ordersheet.setMerchant_uid(PaymentInfo.getInstance().createMerchantUid(member.getMember_id()));
					ordersheet.setMember_no(member.getMember_no());
					
					ordersheet.setTotal_pay_money(completeData.getPay_money() + payment.getPoint_pay_money());
					ordersheet.setDelivery_state("배송준비중");
					if (ordersheet.getMemo().equals("")) {
						ordersheet.setMemo(null);
					}
					
					// payment 값 설정
					payment.setMerchant_uid(ordersheet.getMerchant_uid());
					payment.setTotal_pay_money(ordersheet.getTotal_pay_money());
					payment.setTotal_discounted_price(ordersheet.getTotal_pay_money() - payment.getPost_money());
					Timestamp now = new Timestamp(System.currentTimeMillis());
					payment.setDeposit_time(now);
					
					List<UserOrderProductDTO> orderProductList = productDao.selectProductDataAllList(paymentList);
					int total_price = 0;  // 이벤트 할인 적용 X 
					int total_product_price = 0;  // 이벤트 할인 적용
					int total_discounted_price = 0;  // 이벤트 할인 적용 * 쿠폰적용
					// UserDetailedOrderDTO 만들기
					List<UserDetailedOrderDTO> detailedOrderList = new ArrayList<UserDetailedOrderDTO>();
					for (int i = 0; i < orderProductList.size(); i++) {
						UserDetailedOrderDTO detailedOrder = null;
						int product_price = Integer.parseInt(orderProductList.get(i).getProduct_price().replace(",", ""));
						int event_Discounted_price = Integer.parseInt(orderProductList.get(i).getEvent_product_price().replace(",", ""));
						int discounted_price = Math.round(event_Discounted_price * (1 - completeData.getDiscount_rate()));
						String product_code = orderProductList.get(i).getProduct_code();
						int quantity = orderProductList.get(i).getQuantity();
						
						total_price += product_price * quantity;
						total_product_price += event_Discounted_price * quantity;
						total_discounted_price += discounted_price * quantity;
						
						detailedOrder = new UserDetailedOrderDTO(-1, ordersheet.getMerchant_uid(), product_code, event_Discounted_price, discounted_price, quantity, "결제완료");
						
						detailedOrderList.add(detailedOrder);
					}
					
					int coupon_discounted_price = total_product_price - total_discounted_price;
					
					// 이벤트할인된 총합 금액 넣어주기
					ordersheet.setTotal_product_price(total_product_price);
					
					// 주문자 새로운 정보 입력 (저장 체크는 안한상황)
					if (ordersheet.getRecipient() == null) {
						if(addressBookList.size() > 0) {
							ordersheet.setRecipient(addressBookList.get(0).getRecipient());
							ordersheet.setRecipient_zip_code(addressBookList.get(0).getZip_code());
							ordersheet.setRecipient_address(addressBookList.get(0).getAddress());
							ordersheet.setRecipient_phone(addressBookList.get(0).getPhone_number());
						} else {
							ordersheet.setRecipient(member.getMember_name());
							ordersheet.setRecipient_zip_code(member.getZip_code());
							ordersheet.setRecipient_address(member.getAddress());
							ordersheet.setRecipient_phone(member.getPhone_number());
						}
						
					} else {
						// 주소 편집
						String address = ordersheet.getRecipient_address();
						String detaile_address = completeData.getRecipient_detaile_address();
						ordersheet.setRecipient_address(address + ", " + detaile_address);
					}
					
					// 새로고침 예외처리
					if (hasPaymentByImpUid(payment.getImp_uid())) {
						if (inputCompleteOrder(ordersheet, payment, detailedOrderList, completeData)) {
							result.put("ordersheet", ordersheet);
							result.put("orderProductList", orderProductList);
							result.put("payment", payment);
							result.put("errorCode", errorCode);
							result.put("total_price", total_price);
							result.put("coupon_discounted_price", coupon_discounted_price);
						}
					} else {
						result.put("ordersheet", ordersheet);
						result.put("orderProductList", orderProductList);
						result.put("payment", payment);
						result.put("errorCode", errorCode);
						result.put("total_price", total_price);
						result.put("coupon_discounted_price", coupon_discounted_price);
					}
					
				} else {
					// 검증 실패
					errorCode = "001";
					result.put("errorCode", errorCode);
				}
				
			} // 0원 else 닫는것

		} else {
			// 유효성 3가지 중 1개라도 실패
			if (completeData.getPaymentType().equals("card")) {
				BigDecimal cancel_request_amount = new BigDecimal(completeData.getPay_money());
				BigDecimal checksum = new BigDecimal(completeData.getPay_money());
				CancelDataDTO cancelData = new CancelDataDTO(payment.getImp_uid(), cancel_request_amount, "검증실패",
						checksum);
				PaymentInfo.getInstance().cancelPaymentByImpUid(cancelData);
			}
			
			errorCode = verifyCode;
			result.put("errorCode", errorCode);

		}
	
		return result;

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean inputCompleteOrder(UserOrdersheetDTO ordersheet, UserPaymentDTO payment,
			List<UserDetailedOrderDTO> detailedOrderList, UserCompleteDataDTO completeData) throws Exception {
		boolean result = false;
		// stock 테이블 update (재고 빼주기)
		if (stockDao.updateStockOnStockQuantity(detailedOrderList) == detailedOrderList.size()) {
			// ordersheet 테이블 insert
			if (orderSheetDao.insertOrderSheet(ordersheet) == 1) {
				// detailed_order테이블 insert
				if (detailedOrderDao.insertDetailedOrder(detailedOrderList) == detailedOrderList.size()) {
					// payment 테이블 넣고 insert
					if (paymentDao.insertPayment(payment) == 1) {
						// shopping_cart 테이블 delete
						if (shoppingCartDao.deleteShoppingCartList(ordersheet.getMember_no(), detailedOrderList) == detailedOrderList.size()) {

							// 쿠폰 사용했으면
							if (ordersheet.getCoupon_held_no() != -1) {
								// coupon_held 테이블 update
								if (couponHeldDao.updateCouponHeldOnAvailableCoupon(ordersheet.getCoupon_held_no()) == 1) {									
									// coupon_log 테이블 insert
									UserCouponLogDTO couponLog = new UserCouponLogDTO(ordersheet.getMerchant_uid(), ordersheet.getCoupon_held_no(), "사용");
									if (couponLogDao.insertCouponLog(couponLog) != 1) {
										// 실패
										throw new Exception();
									} 									
								} else {
									throw new Exception();
								}
								
							}

							// 적립금 사용했으면
							if (payment.getPoint_pay_money() != 0) {
								// member 테이블 update
								if (memberDao.updataMemberOnAccrualAmount(ordersheet.getMember_no(), payment.getPoint_pay_money()) == 1) {
									// points_usage_log 테이블 insert
									UserPointsUsageLogDTO pointsUsageLog = new UserPointsUsageLogDTO(ordersheet.getMerchant_uid(), ordersheet.getMember_no(), payment.getPoint_pay_money(), "사용");
									if (pointsUsageLogDao.insertPointsUsageLog(pointsUsageLog) != 1) {
										//실패
										throw new Exception();
									}									
								} else {
									throw new Exception();
								}
								
							}

							// 주소 저장 체크 했으면
							if (completeData.getMake_address().equals("on")) {
								UserAddressBookDTO addressBook = new UserAddressBookDTO(ordersheet.getMember_no(),
										"신규주소지", ordersheet.getRecipient_address(), ordersheet.getRecipient_zip_code(),
										ordersheet.getRecipient(), ordersheet.getRecipient_phone());
								if (memberAddressBookDao.insertAddressBookByOrderPage(addressBook) != 1) {
									//실패
									throw new Exception();
								}
								
							}

							result = true;
						}
					}
				}
			}
		}

		if(result == false) {
			if (completeData.getPaymentType().equals("card")) {
				BigDecimal cancel_request_amount = new BigDecimal(completeData.getPay_money());
				BigDecimal checksum = new BigDecimal(completeData.getPay_money());
				CancelDataDTO cancelData = new CancelDataDTO(payment.getImp_uid(), cancel_request_amount, "DB 저장 실패",
						checksum);
				PaymentInfo.getInstance().cancelPaymentByImpUid(cancelData);	
			}
			
			throw new Exception();
		}
			
		return result;
	}

	// 결제 데이터 있는지 확인(새로고침시 사용)
	/**
	 * @methodName : hasPaymentByImpUid
	 * @author : syt
	 * @param imp_uid
	 * @return
	 * @throws Exception
	 * @returnValue : @param imp_uid
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 8. 오후 4:08:12
	 * @description : 결제 데이터 존재유무 확인(새로고침시 사용)
	 */
	@Override
	public boolean hasPaymentByImpUid(String imp_uid) throws Exception {
		boolean result = false;
		if (paymentDao.selectPaymentByImpUid(imp_uid) == 0) {
			result = true;
		}
		return result;
	}

	/**
	 * @methodName : getMember
	 * @author : syt
	 * @param member_no
	 * @return
	 * @throws Exception
	 * @returnValue : @param member_no
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 8. 오후 4:07:45
	 * @description : 세션정보를 이용해 유저상세정보 가져오기
	 */
	@Override
	public UserMemberVO getMember(int member_no) throws Exception {
		return memberDao.getMember(member_no);
	}

	/**
	 * @methodName : getAddressBook
	 * @author : syt
	 * @param member_no
	 * @return
	 * @throws Exception
	 * @returnValue : @param member_no
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 14. 오후 3:00:24
	 * @description : 배송지 테이블 정보 가져옴
	 */
	@Override
	public List<UserAddressVO> getAddressBook(int member_no) throws Exception {
		return memberAddressBookDao.selectAddressBook(member_no);
	}

	/**
	 * @methodName : getDetailedOrder
	 * @author : syt
	 * @param shoppingCartNoList
	 * @return
	 * @throws Exception
	 * @returnValue : @param shoppingCartNoList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 8. 오후 6:08:01
	 * @description : 장바구니에서 넘어온 상품 디테일정보 가져옴(쇼핑카트)
	 */
	@Override
	public List<UserOrderProductDTO> getDetailedOrder(List<Integer> shoppingCartNoList) throws Exception {
		return shoppingCartDao.selectShoppingCartDataList(shoppingCartNoList);
	}

	/**
	 * @methodName : verifyAll
	 * @author : syt
	 * @param member_id
	 * @param point_pay_money
	 * @param coupon_held_no
	 * @param product_code
	 * @param quantity
	 * @returnValue : @param member_id
	 * @returnValue : @param point_pay_money
	 * @returnValue : @param coupon_held_no
	 * @returnValue : @param product_code
	 * @returnValue : @param quantity
	 * @date : 2023. 11. 8. 오후 7:44:16
	 * @description : 포인트,쿠폰 / 재고 유효성 검사 통합
	 */
	@Override
	public String verifyAll(int member_no, int point_pay_money, int coupon_held_no, List<UserPaymentListDTO> paymentList) throws Exception {
		String result = null;
		int intVerify = 9000;

		int verifyQuantity = 1;
		int verifyCoupon = 1;
		int verifyPoint = 1;
		
		// 재고 검증 (성공 0 , 실패 음수)
		if (stockDao.isVerifyStock(paymentList) != 0) { 
			verifyQuantity = -1;
			intVerify += 200;
		} 
		
		// 쿠폰 검증 (성공 1 , 실패 0)
		if (coupon_held_no != -1) {
			if(memberDao.verifyCoupon(member_no, coupon_held_no) != 1) {
				verifyCoupon = -1;	
				intVerify += 20;
			} 		
		} 
		
		// 포인트 검증 (성공1, 실패 -1)
		if (point_pay_money > 0) {
			if(memberDao.verifyPoint(member_no, point_pay_money) != 1) {
				verifyPoint = -1;
				intVerify += 2;
			}		
		} 
		
		// 완전 검증
		if (verifyQuantity == 1 && verifyCoupon == 1 && verifyPoint == 1) {
			result = "000";
		} else {
			String verify = Integer.toString(intVerify);
			result = verify.substring(verify.length()-3, verify.length());
		}
		
		return result;
	}

	/**
	 * @methodName : getValidationPrice
	 * @author : syt
	 * @param product_code
	 * @param quantity
	 * @param coupon_held_no
	 * @param post_money
	 * @param point_pay_money
	 * @return
	 * @returnValue : @param product_code
	 * @returnValue : @param quantity
	 * @returnValue : @param coupon_held_no
	 * @returnValue : @param post_money
	 * @returnValue : @param point_pay_money
	 * @returnValue : @return
	 * @date : 2023. 11. 15. 오후 8:36:19
	 * @description : [결제페이지] API 데이터 검증을 위한 값 계산하기
	 */
	@Override
	public int getValidationPrice(List<UserPaymentListDTO> paymentList, int coupon_held_no, int post_money, int point_pay_money) throws Exception {
		
		int totalPrice = detailedOrderDao.getValidationPrice(paymentList);
		float couponDiscountRate = 0;
		if (coupon_held_no != -1) {
			couponDiscountRate = couponHeldDao.getValidationRate(coupon_held_no);
		}

		int validationPrice = totalPrice - (Math.round(totalPrice * couponDiscountRate)) + post_money - point_pay_money;

		return validationPrice;
	}

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
