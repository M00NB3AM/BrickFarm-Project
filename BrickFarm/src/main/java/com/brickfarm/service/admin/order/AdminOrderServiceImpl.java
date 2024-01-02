package com.brickfarm.service.admin.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brickfarm.dao.cancellationreturn.CancellationReturnDAO;
import com.brickfarm.dao.carryingout.CarryingOutDAO;
import com.brickfarm.dao.couponheld.CouponHeldDAO;
import com.brickfarm.dao.couponlog.CouponLogDAO;
import com.brickfarm.dao.detailedorder.DetailedOrderDAO;
import com.brickfarm.dao.exchange.ExchangeDAO;
import com.brickfarm.dao.member.MemberDAO;
import com.brickfarm.dao.ordersheet.OrderSheetDAO;
import com.brickfarm.dao.payment.PaymentDAO;
import com.brickfarm.dao.pointsaccurallog.PointsAccuralLogDAO;
import com.brickfarm.dao.pointsusagelog.PointsUsageLogDAO;
import com.brickfarm.dao.schedulerlog.SchedulerLogDAO;
import com.brickfarm.dto.admin.syt.AdminCancelDataDTO;
import com.brickfarm.dto.admin.syt.AdminCancelFailDTO;
import com.brickfarm.dto.admin.syt.AdminExchangeDTO;
import com.brickfarm.dto.admin.syt.AdminCancelReturnDTO;
import com.brickfarm.dto.admin.syt.AdminDeliveryDTO;
import com.brickfarm.dto.admin.syt.AdminDetailedOrderDTO;
import com.brickfarm.dto.admin.syt.AdminSearchDTO;
import com.brickfarm.dto.user.syt.CancelDataDTO;
import com.brickfarm.dto.user.syt.UserPaymentListDTO;
import com.brickfarm.etc.syt.Pagination;
import com.brickfarm.etc.syt.PaymentInfo;
import com.brickfarm.vo.admin.syt.AdminCancellationReturnVO;
import com.brickfarm.vo.admin.syt.AdminDeliveryDetailVO;
import com.brickfarm.vo.admin.syt.AdminDeliveryVO;
import com.brickfarm.vo.admin.syt.AdminExchangeVO;
import com.brickfarm.vo.admin.syt.AdminOrderVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerDataVO;
import com.brickfarm.vo.admin.syt.AdminSchedulerPaymentDataVO;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {

	@Inject
	public ExchangeDAO exchangeDao;
	@Inject
	public MemberDAO memberDao;
	@Inject
	public CancellationReturnDAO cancellationReturnDao;
	@Inject
	public DetailedOrderDAO detailedOrderDao;
	@Inject
	public OrderSheetDAO ordersheetDao;
	@Inject
	public PointsAccuralLogDAO pointsAccrualLogDao;
	@Inject
	public PaymentDAO paymentDao;
	@Inject
	public PointsUsageLogDAO pointsUsageLogDao;
	@Inject
	public CouponHeldDAO couponHeldDao;
	@Inject
	public CouponLogDAO couponLogDao;
	@Inject
	public CarryingOutDAO carryingOutDao;
	@Inject
	public SchedulerLogDAO schedulerLogDao;


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
	 * @methodName : cancelPayment
	 * @author : syt
	 * @param pkNumberList
	 * @return
	 * @throws Exception
	 * @returnValue : @param pkNumberList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오전 10:26:34
	 * @description : 결제취소 관련 메소드 공용화로 따로 분리
	 */
	@Transactional(rollbackFor = Exception.class)
	private Map<String, Object> cancelPayment(List<Integer> pkNumberList) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		List<Integer> successList = new ArrayList<Integer>();
		List<AdminCancelFailDTO> failList = new ArrayList<AdminCancelFailDTO>();
		List<AdminCancelReturnDTO> cancelReturnList = new ArrayList<AdminCancelReturnDTO>();

		for (int i = 0; i < pkNumberList.size(); i++) {
			// 취소 데이터 수집
			AdminCancelDataDTO AdminCancelData = cancellationReturnDao.getCancelData(pkNumberList.get(i));
			// 카드+현금 가격
			int check_pay = AdminCancelData.getChecksum();
			// 취소,반품 취소신청 금액
			int cancel_pay = AdminCancelData.getCancel_request_amount();
			// 총결제금액 - 취소 요청금액 (계속 무료배송이 유지되는지 확인)
			int postYN = AdminCancelData.getTotal_pay_money() - AdminCancelData.getCancel_request_amount();

			// 과실 여부에 따라 배송비 측정
			if (AdminCancelData.getWhat().equals("반품") && AdminCancelData.getNegligence().equals("N")) {
				// 소비자 과실 (택배비 발생)
				int postMoney = 2500;
				
				// 총결제금액 - 취소요청금액을 했을시 배송비 무료기준에 부합하는지
				if(postYN >= 50000) {
					// 무료기준 부합
					// (편도) 배송비 적용
					AdminCancelData.setAdd_post_money(postMoney);
					cancel_pay = cancel_pay - postMoney;
					
				} else if(postYN < 50000) {
					if (AdminCancelData.getPost_money() == 0) {
						// 기존에 배송비 없이 배송
						// (왕복) 배송비 적용
						AdminCancelData.setAdd_post_money(2 * postMoney);
						cancel_pay = cancel_pay - (2 * postMoney);
					} else if (AdminCancelData.getPost_money() != 0) {
						// 기존에 배송비 결제
						// (편도) 배송비 적용
						AdminCancelData.setAdd_post_money(postMoney);
						cancel_pay = cancel_pay - postMoney;
					}
				}
				
				
			}

			int point_amount = 0;
			int cancel_amount= 0;
			// 카드&현금 or 적립금 취소금액 계산
			if (cancel_pay > check_pay) { 
				// 적립금 취소 금액  
				point_amount = cancel_pay - check_pay;
				// 카드or현금 취소 금액
				cancel_amount = check_pay;
				
			} else if (cancel_pay <= check_pay) {
				// 카드or현금 취소 금액
				cancel_amount = cancel_pay;
			}
			
			// 카드or현금 금액 설정 (
			AdminCancelData.setChange_cancel_amount(cancel_amount);
			// 변경된 적립금 금액 설정
			AdminCancelData.setChange_point_pay_money(point_amount);

			if (AdminCancelData.getPay_method().equals("카드")) {
				// 결제 취소 API
				// 100 에 AdminCancelData.getCancel_request_amount(),
				// AdminCancelData.getChecksum()
				CancelDataDTO cancelData = new CancelDataDTO(AdminCancelData.getImp_uid(), new BigDecimal(100),
						AdminCancelData.getReason(), new BigDecimal(100));
				IamportResponse<Payment> response = PaymentInfo.getInstance().cancelPaymentByImpUid(cancelData);
				// 실제 취소 처리 끝나는곳
				// --------------------------------------------------------------------------------------------------

				if (response.getCode() == 0) {
					// 취소 성공
					if (paymentDao.updatePaymentOnCancel(AdminCancelData) == 1) {
						// 적립금반환이 있다면 적립금 반환로그 작성 및 멤버 적립금 반환
						if(AdminCancelData.getChange_point_pay_money() != 0) {
							if(pointsUsageLogDao.insertCancelPoint(AdminCancelData) == 1) {
								if(memberDao.updateCancelPoint(AdminCancelData) != 1) {
									// 실패시 로직
									throw new Exception();
								}
								
							} else {
								throw new Exception();
							}
						}
						
						// 성공 리스트에 담기
						successList.add(AdminCancelData.getPk_number());
						// payment 테이블 가격들 업데이트
						Timestamp now = new Timestamp(System.currentTimeMillis());
						AdminCancelReturnDTO cancelReturnData = new AdminCancelReturnDTO(AdminCancelData.getPk_number(),
								"완료", null, now, AdminCancelData.getAdd_post_money());
						cancelReturnList.add(cancelReturnData);
					} else {
						throw new Exception();
					}

				} else if (response.getCode() == 1) {
					// 취소 실패 리스트에 담기
					AdminCancelFailDTO fail = new AdminCancelFailDTO(AdminCancelData.getPk_number(), response.getMessage());
					failList.add(fail);
				}

			} else if (AdminCancelData.getPay_method().equals("현금")) {
				// 현금결제(무조건 성공)
				if (paymentDao.updatePaymentOnCancel(AdminCancelData) == 1) {
					// 적립금반환이 있다면 적립금 반환로그 작성 및 멤버 적립금 반환
					if(AdminCancelData.getChange_point_pay_money() != 0) {
						if(pointsUsageLogDao.insertCancelPoint(AdminCancelData) == 1) {
							if(memberDao.updateCancelPoint(AdminCancelData) != 1) {
								//실패시 로직
								throw new Exception();
							} 
							
						} else {
							throw new Exception();
						}
					}
					// 성공 리스트에 담기
					successList.add(AdminCancelData.getPk_number());
					// payment 테이블 가격들 업데이트
					Timestamp now = new Timestamp(System.currentTimeMillis());
					AdminCancelReturnDTO cancelReturnData = new AdminCancelReturnDTO(AdminCancelData.getPk_number(),
							"완료", null, now, AdminCancelData.getAdd_post_money());
					cancelReturnList.add(cancelReturnData);
				} else {
					throw new Exception();
				}
			}

		}
		result.put("successList", successList);
		result.put("failList", failList);
		result.put("cancelReturnList", cancelReturnList);

		return result;
	}
	
	/**
	 * @methodName : verifyTotalState
	 * @author : syt
	 * @param compareMerchantUidList
	 * @return
	 * @returnValue : @param compareMerchantUidList
	 * @returnValue : @return
	 * @date : 2023. 11. 21. 오후 1:02:36
	 * @description : 최종완료 변경해야하는것 검증후 처리하는 함수(공용)
	 */
	@Transactional(rollbackFor = Exception.class)
	private boolean verifyTotalState(List<String> compareMerchantUidList) throws Exception {
		boolean result = false;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		// 주문서의 최종상태 변경
		if (ordersheetDao.updateOrderSheetOnState(compareMerchantUidList, now) == compareMerchantUidList.size()) {
			// 결제액 0원이 아닌 List
			List<String> haveToMerchantUidList = paymentDao.selectPaymentMoney(compareMerchantUidList);
			// 포인트 적립로그에 적립
			if(haveToMerchantUidList.size() > 0) {
				if (pointsAccrualLogDao.insertPointAccrualLogByCompletion(haveToMerchantUidList) == haveToMerchantUidList.size()) {
					int count = 0;
					for(String haveToMerchantUid : haveToMerchantUidList) {
						count += memberDao.updateMemberOnAccumulate(haveToMerchantUid);
					}
					if(count == haveToMerchantUidList.size()) {
						result = true;
					}
				} 
			} else {
				result = true;
			}
		}
		
		
		return result;
	}
	
	/**
	 * @methodName : carryinOut
	 * @author : syt
	 * @param pkNumberList
	 * @param what
	 * @return
	 * @returnValue : @param pkNumberList
	 * @returnValue : @param what
	 * @returnValue : @return
	 * @date : 2023. 11. 22. 오전 11:02:25
	 * @description : 반출에 추가하기
	 */
	@Transactional(rollbackFor = Exception.class)
	private boolean carryinOut(List<Integer> pkNumberList, String what) throws Exception {
		boolean result = false;
		List<UserPaymentListDTO> carryinOutDataList = detailedOrderDao.selectCarryinOutDataList(pkNumberList, what);
		if(carryinOutDataList.size() == pkNumberList.size()) {
			int count = 0;
			for(int i = 0; i < carryinOutDataList.size(); i++) {
				if(carryingOutDao.selectHasCarryingOut(carryinOutDataList.get(i).getProduct_code()) == 1) {
					// 있으면 업데이트
					count += carryingOutDao.updateCarryingOutData(carryinOutDataList.get(i));
				} else {
					// 없으면 인설트
					count += carryingOutDao.insertCarryingOutData(carryinOutDataList.get(i));
				}	
			}
			
			if(count == carryinOutDataList.size()) {
				result = true;
			}
		}
		
		return result;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * @methodName : searchOrderExchange
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @return
	 * @throws ParseException
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @return
	 * @returnValue : @throws ParseException
	 * @date : 2023. 11. 2. 오후 6:41:44
	 * @description : [교환] 데이터(검색결과 적용포함) 로드
	 */
	@Override
	public Map<String, Object> searchOrderExchange(int pageNo, AdminSearchDTO searchDto) throws Exception {
		// 날짜 편집 및 DTO에 set
		if (searchDto.getDateRange() != null && !searchDto.getDateRange().equals("")) {
			String dateRange = searchDto.getDateRange().replace(" ", "");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			searchDto.setDateStart(formatter.parse(dateRange.split("-")[0]));
			searchDto.setDateEnd(formatter.parse(dateRange.split("-")[1]));
		}

		// 페이지네이션
		int totalDataCount = exchangeDao.selectTotalDataCount(searchDto);
		int showDataCountPerPage = 10;
		int showPageGroupsCount = 10;
		Pagination pagination = new Pagination(totalDataCount, showDataCountPerPage, pageNo, showPageGroupsCount);

		// DAO 호출 후 controller로 보내기
		List<AdminExchangeVO> exchangeList = exchangeDao.selectExchangeList(searchDto, pagination);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("exchangeList", exchangeList);
		result.put("pagination", pagination);

		return result;
	}

	/**
	 * @methodName : changeState
	 * @author : syt
	 * @param exchangeList
	 * @return
	 * @returnValue : @param exchangeList
	 * @returnValue : @return
	 * @date : 2023. 11. 2. 오후 6:42:05
	 * @description : [교환] 상태변경:확인,진행중
	 */
	@Override
	public boolean changeStateExchange(List<AdminExchangeDTO> exchangeList) throws Exception {
		boolean result = false;
		if (exchangeList.get(0).getState().equals("확인")) {
			if (exchangeDao.updateExchangeByStateCheck(exchangeList) == exchangeList.size()) {
				result = true;
			} 
			
		} else if (exchangeList.get(0).getState().equals("진행중")) {
			if (exchangeDao.updateExchangeByStateProcess(exchangeList) == exchangeList.size()) {
				result = true;
			} 
		}
		return result;
	}

	/**
	 * @methodName : changeStateByComplete
	 * @author : syt
	 * @param exchangeList
	 * @return
	 * @returnValue : @param exchangeList
	 * @returnValue : @return
	 * @date : 2023. 11. 2. 오후 6:44:10
	 * @description : [교환] 상태변경:완료 변경
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean changeStateExchangeByComplete(List<AdminExchangeDTO> exchangeList, List<Integer> exchangeNoList)
			throws Exception {
		boolean result = false;
		// 반출테이블 적용
		if(carryinOut(exchangeNoList, "교환")) {
			// 교환 상태 변경
			if (exchangeDao.updateExchangeByStateComplete(exchangeList) == exchangeList.size()) {
				// 정보 검증하러 바로가기
				List<String> compareMerchantUidList = ordersheetDao.compareCompleteStateExchange(exchangeNoList);
				if (compareMerchantUidList.size() > 0) {
					result = verifyTotalState(compareMerchantUidList);
				} else {
					result = true;
				}
				
			} 
		}
		
		if(result == false) {
			throw new Exception();
		}

		return result;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * @methodName : searchOrderReturn
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @return
	 * @throws ParseException
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @return
	 * @date : 2023. 11. 2. 오후 9:25:36
	 * @description : [반품] 데이터(검색결과 적용포함) 로드
	 */
	@Override
	public Map<String, Object> searchOrderReturn(int pageNo, AdminSearchDTO searchDto) throws Exception {
		// 날짜 편집 및 DTO에 set
		if (searchDto.getDateRange() != null && !searchDto.getDateRange().equals("")) {
			String dateRange = searchDto.getDateRange().replace(" ", "");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			searchDto.setDateStart(formatter.parse(dateRange.split("-")[0]));
			searchDto.setDateEnd(formatter.parse(dateRange.split("-")[1]));
		}

		// 페이지네이션
		int totalDataCount = cancellationReturnDao.selectTotalDataCount(searchDto, "반품");
		int showDataCountPerPage = 10;
		int showPageGroupsCount = 10;
		Pagination pagination = new Pagination(totalDataCount, showDataCountPerPage, pageNo, showPageGroupsCount);

		// DAO 호출 후 controller로 보내기
		List<AdminCancellationReturnVO> returnList = cancellationReturnDao.selectCancelReturnList(searchDto, pagination, "반품");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("returnList", returnList);
		result.put("pagination", pagination);

		return result;
	}

	/**
	 * @methodName : changeStateReturn
	 * @author : syt
	 * @param returnList
	 * @return
	 * @throws Exception
	 * @returnValue : @param returnList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 4. 오후 5:29:29
	 * @description : [반품] 상태변경:확인
	 */
	@Override
	public boolean changeStateReturn(List<AdminCancelReturnDTO> returnList) throws Exception {
		boolean result = false;
		if (cancellationReturnDao.updateCancelReturnByStateCheck(returnList, "반품") == returnList.size()) {
			result = true;
		} 
		return result;
	}

	/**
	 * @methodName : changeStateReturnByComplete
	 * @author : syt
	 * @param returnList
	 * @return
	 * @throws Exception
	 * @returnValue : @param returnList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 4. 오후 5:29:32
	 * @description : [반품] 상태변경:완료
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> changeStateReturnByComplete(List<Integer> returnNoList) throws Exception {
		Boolean verify = false;
		Map<String, Object> failListMap = new HashMap<String, Object>();

		// 취소 공용 메서드 갔다오기
		Map<String, Object> result = cancelPayment(returnNoList);

		List<Integer> successList = (List<Integer>) result.get("successList");
		List<AdminCancelFailDTO> failList = (List<AdminCancelFailDTO>) result.get("failList");
		List<AdminCancelReturnDTO> returnList = (List<AdminCancelReturnDTO>) result.get("cancelReturnList");

		// 밑에 메소드를 따로 만들어서 거기서 트랜잭션을 걸어야할듯?
		// 취소/반품테이블(한번에) 업데이트 returnList= 성공한애들 상태변경에 맞게 객체화
		if (successList.size() > 0) {
			// 반출테이블 적용
			if(carryinOut(successList, "반품")) {
				// 상태 완료로 변경
				if (cancellationReturnDao.updateCancelReturnByStateComplete(returnList, "반품") == returnList.size()) {
					// 정보 검증하러 바로가기 
					List<String> compareMerchantUidList = ordersheetDao.compareCompleteStateCancelReturn(successList);
					if (compareMerchantUidList.size() > 0) {
						verify = verifyTotalState(compareMerchantUidList);
					} else {
						// DB 저장은 성공했지만, 검증에 통과한것이 0개이다.
						verify = true;
					}
				}
			}
			
		} else {
			// API 취소 성공한것이 한개도 없다.
			verify = true;
		}
		
		if(verify) {
			failListMap.put("failList", failList);
		} else {
			throw new Exception();
		}

		return failListMap;
	}
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * @methodName : searchOrderCancel
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @return
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @return
	 * @date : 2023. 11. 6. 오후 12:49:32
	 * @description : [취소] 데이터(검색결과 적용포함) 로드
	 */
	@Override
	public Map<String, Object> searchOrderCancel(int pageNo, AdminSearchDTO searchDto) throws Exception {
		// 날짜 편집 및 DTO에 set
		if (searchDto.getDateRange() != null && !searchDto.getDateRange().equals("")) {
			String dateRange = searchDto.getDateRange().replace(" ", "");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			searchDto.setDateStart(formatter.parse(dateRange.split("-")[0]));
			searchDto.setDateEnd(formatter.parse(dateRange.split("-")[1]));
		}

		// 페이지네이션
		int totalDataCount = cancellationReturnDao.selectTotalDataCount(searchDto, "취소");
		int showDataCountPerPage = 10;
		int showPageGroupsCount = 10;
		Pagination pagination = new Pagination(totalDataCount, showDataCountPerPage, pageNo, showPageGroupsCount);

		// DAO 호출 후 controller로 보내기
		List<AdminCancellationReturnVO> cancelList = cancellationReturnDao.selectCancelReturnList(searchDto, pagination,
				"취소");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("cancelList", cancelList);
		result.put("pagination", pagination);

		return result;
	}

	/**
	 * @methodName : changeStateReturn
	 * @author : syt
	 * @param returnList
	 * @return
	 * @throws Exception
	 * @returnValue : @param returnList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 4:20:31
	 * @description : [취소] 상태변경:확인
	 */
	@Override
	public boolean changeStateCancel(List<AdminCancelReturnDTO> cancelList) throws Exception {
		boolean result = false;

		if (cancellationReturnDao.updateCancelReturnByStateCheck(cancelList, "취소") > 0) {
			result = true;
		}

		return result;
	}

	/**
	 * @methodName : changeStateReturnByComplete
	 * @author : syt
	 * @param returnNoList
	 * @return
	 * @throws Exception
	 * @returnValue : @param returnNoList
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 4:21:01
	 * @description : [취소] 상태변경:완료
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> changeStateCancelByComplete(List<Integer> cancelNoList) throws Exception {
		Boolean verify = false;
		Map<String, Object> failListMap = new HashMap<String, Object>();
		
		// 취소 공용 메서드 갔다오기
		Map<String, Object> result = cancelPayment(cancelNoList);

		List<Integer> successList = (List<Integer>) result.get("successList");
		List<AdminCancelFailDTO> failList = (List<AdminCancelFailDTO>) result.get("failList");
		List<AdminCancelReturnDTO> cancelList = (List<AdminCancelReturnDTO>) result.get("cancelReturnList");
		// 밑에 메소드를 따로 만들어서 거기서 트랜잭션을 걸어야할듯?
		// 취소/반품테이블(한번에) 업데이트 returnList= 성공한애들 상태변경에 맞게 객체화
		if (successList.size() > 0) {
			// 반출테이블 적용
			if(carryinOut(successList, "취소")) {
				// 상태 완료로 변경
				if (cancellationReturnDao.updateCancelReturnByStateComplete(cancelList, "취소") == cancelList.size()) {
					// 정보 검증하러 바로가기
					List<String> compareMerchantUidList = ordersheetDao.compareCompleteStateCancelReturn(successList);
					if (compareMerchantUidList.size() > 0) {
						verify = verifyTotalState(compareMerchantUidList);
					} else {
						// DB 저장은 성공했지만, 검증에 통과한것이 0개이다.
						verify = true;
					}
				}
			}
			
		} else {
			// API 취소 성공한것이 한개도 없다.
			verify = true;
		}
		
		if(verify) {
			failListMap.put("failList", failList);
		} else {
			throw new Exception();
		}

		return failListMap;
	}

	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * @methodName : searchOrder
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @return
	 * @throws Exception
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 7:30:28
	 * @description : [주문관리] 데이터(검색결과 적용포함) 로드
	 */
	@Override
	public Map<String, Object> searchOrder(int pageNo, AdminSearchDTO searchDto) throws Exception {
		// 날짜 편집 및 DTO에 set
		if (searchDto.getDateRange() != null && !searchDto.getDateRange().equals("")) {
			String dateRange = searchDto.getDateRange().replace(" ", "");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			searchDto.setDateStart(formatter.parse(dateRange.split("-")[0]));
			searchDto.setDateEnd(formatter.parse(dateRange.split("-")[1]));
		}

		if (searchDto.getTotalState().equals("process")) {
			searchDto.setTotalState("진행중");
		} else if (searchDto.getTotalState().equals("complete")) {
			searchDto.setTotalState("완료");
		}
		// 페이지네이션
		int totalDataCount = ordersheetDao.selectTotalDataCount(searchDto);
		int showDataCountPerPage = 10;
		int showPageGroupsCount = 10;
		Pagination pagination = new Pagination(totalDataCount, showDataCountPerPage, pageNo, showPageGroupsCount);


		// DAO 호출 후 controller로 보내기
		List<AdminOrderVO> orderList = ordersheetDao.selectOrderList(searchDto, pagination);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("orderList", orderList);
		result.put("pagination", pagination);

		return result;
	}
	
	/**
	 * @methodName : getOrderDetail
	 * @author : syt
	 * @param detailed_order_no
	 * @return
	 * @returnValue : @param detailed_order_no
	 * @returnValue : @return
	 * @date : 2023. 11. 23. 오후 3:21:15
	 * @description : [주문관리] 상세정보 얻기
	 */
	@Override
	public Map<String, Object> getOrderDetail(int detailed_order_no) throws Exception {
		List<AdminDeliveryDetailVO> orderDetailList = detailedOrderDao.selectOrderDetailList(detailed_order_no);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("orderDetailList", orderDetailList);
		return result;
	}

	/**
	 * @methodName : changeStateManageOrderPayment
	 * @author : syt
	 * @param detailedOrderList
	 * @param state
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @date : 2023. 11. 13. 오후 12:44:56
	 * @description : [주문관리] 결제상태(결제대기,결제완료)로 변경
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean changeStateManageOrderPayment(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception {
		boolean result = false;
		Timestamp now = null;
		if (state.equals("beforePayment")) {
			state = "결제대기";
		} else if (state.equals("afterPayment")) {
			state = "결제완료";
			now = new Timestamp(System.currentTimeMillis());
		}

		// 1) 디테일 오더 -> 결제 상태 변경
		if (detailedOrderDao.updateDetailedOrderOnPayment(detailedOrderList, state) == detailedOrderList.size()) {
			// 취소,반품,교환 테이블 딜리트 (되돌렸을때 로직)
			cancellationReturnDao.deleteCancelReturnByDetailedOrderNo(detailedOrderList);
			exchangeDao.deleteExchangeByDetailedOrderNo(detailedOrderList);
			
			
			// 예외 1 : 변경을 자주했을때 (카드결제면 2번을 안해야한다.)
			// 예외 2 : 배송상태가 이미 배송대기중,배송중,배송완료 라면 안해야한다.
			// 2) 결제테이블 -> 입금자명 몇 입금시간 업데이트
			if (paymentDao.updatePaymentOnDepositorName(detailedOrderList, now) >= 0) {
				if (state.equals("결제완료")) {
					state = "배송준비중";
				}
				// 주문서테이블 -> 배송상태 업데이트
				if (ordersheetDao.updateOrdersheetOnDeliveryPrepare(detailedOrderList, state) >= 0) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * @methodName : changeStateManageOrderExchange
	 * @author : syt
	 * @param detailedOrderList
	 * @param state
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @date : 2023. 11. 13. 오후 12:45:12
	 * @description : [주문관리] 결제상태(교환)로 변경
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean changeStateManageOrderExchange(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception {
		boolean result = false;
		// 1) 디테일 오더 -> 결제 상태 변경
		if (detailedOrderDao.updateDetailedOrderOnPayment(detailedOrderList, state) == detailedOrderList.size()) {
			// 되돌리기용 로직
			cancellationReturnDao.deleteCancelReturnByDetailedOrderNo(detailedOrderList);
			exchangeDao.deleteExchangeByDetailedOrderNo(detailedOrderList);
			// 2) 교환 테이블 인설트
			if (exchangeDao.insertExchangeList(detailedOrderList) == detailedOrderList.size()) {
				result = true;	
			} 
		}
		
		if(result == false) {
			throw new Exception();
		}
			
		return result;
	}

	/**
	 * @methodName : changeStateManageOrderCancelReturn
	 * @author : syt
	 * @param detailedOrderList
	 * @param state
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @date : 2023. 11. 13. 오후 12:45:25
	 * @description : [주문관리] 결제상태(취소,반품)로 변경
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean changeStateManageOrderCancelReturn(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception {
		boolean result = false;
		if (state.equals("cancel")) {
			state = "취소";
		} else if (state.equals("return")) {
			state = "반품";
		}

		// 1) 디테일 오더 -> 결제 상태 변경
		if (detailedOrderDao.updateDetailedOrderOnPayment(detailedOrderList, state) == detailedOrderList.size()) {
			// 되돌리기용 로직
			cancellationReturnDao.deleteCancelReturnByDetailedOrderNo(detailedOrderList);
			exchangeDao.deleteExchangeByDetailedOrderNo(detailedOrderList);
			
			// 2)취소반품테이블 인설트
			if (cancellationReturnDao.insertCancelReturnList(detailedOrderList, state) == detailedOrderList.size()) {
				result = true;
				
			}
		}
		
		if(result == false) {
			throw new Exception();
		}
		
		return result;
	}

	/**
	 * @methodName : changeStateManageOrderComplete
	 * @author : syt
	 * @param detailedOrderList
	 * @param state
	 * @return
	 * @returnValue : @param detailedOrderList
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @date : 2023. 11. 13. 오후 12:45:36
	 * @description : [주문관리] 결제상태(구매확정)로 변경
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean changeStateManageOrderComplete(List<AdminDetailedOrderDTO> detailedOrderList, String state) throws Exception {
		boolean result = false;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		// 1) 디테일 오더 -> 결제 상태 변경
		if (detailedOrderDao.updateDetailedOrderCompleteOnPayment(detailedOrderList, state, now) == detailedOrderList.size()) {
			// UID 얻어오기
			List<String> merchantUidList = detailedOrderDao.selectDetailedOrderGetUid(detailedOrderList);
			// 검증하기
			List<String> compareMerchantUidList = ordersheetDao.compareCompleteStateByUid(merchantUidList);
			if (compareMerchantUidList.size() > 0) {
				result = verifyTotalState(compareMerchantUidList);
			} else {
				// DB 저장은 성공했지만, 검증에 통과한것이 0개이다.
				result = true;
			}
		}
		
		if(result == false) {
			throw new Exception();
		}
		
		return result;
	}
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * @methodName : searchOrderDelivery
	 * @author : syt
	 * @param pageNo
	 * @param searchDto
	 * @return
	 * @throws Exception 
	 * @returnValue : @param pageNo
	 * @returnValue : @param searchDto
	 * @returnValue : @return
	 * @date : 2023. 11. 15. 오후 3:52:26
	 * @description : [배송관리] 데이터(검색결과 적용포함) 로드
	 */
	@Override
	public Map<String, Object> searchOrderDelivery(int pageNo, AdminSearchDTO searchDto) throws Exception {
		// 날짜 편집 및 DTO에 set
		if (searchDto.getDateRange() != null && !searchDto.getDateRange().equals("")) {
			String dateRange = searchDto.getDateRange().replace(" ", "");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			searchDto.setDateStart(formatter.parse(dateRange.split("-")[0]));
			searchDto.setDateEnd(formatter.parse(dateRange.split("-")[1]));
		}
		
		if (searchDto.getTotalState().equals("process")) {
			searchDto.setTotalState("진행중");
		} else if (searchDto.getTotalState().equals("complete")) {
			searchDto.setTotalState("완료");
		}

		// 페이지네이션
		int totalDataCount = ordersheetDao.selectTotalDeliveryDataCount(searchDto);
		int showDataCountPerPage = 10;
		int showPageGroupsCount = 10;
		Pagination pagination = new Pagination(totalDataCount, showDataCountPerPage, pageNo, showPageGroupsCount);

		// DAO 호출 후 controller로 보내기
		List<AdminDeliveryVO> deliveryList = ordersheetDao.selectDeliveryList(searchDto, pagination);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("deliveryList", deliveryList);
		result.put("pagination", pagination);

		return result;
	}
	
	/**
	 * @methodName : getOrderDeliveryDetail
	 * @author : syt
	 * @param merchant_uid
	 * @return
	 * @returnValue : @param merchant_uid
	 * @returnValue : @return
	 * @date : 2023. 11. 23. 오전 11:34:54
	 * @description : [배송관리] 상세정보 얻기
	 */
	@Override
	public Map<String, Object> getOrderDeliveryDetail(String merchant_uid) throws Exception {
		List<AdminDeliveryDetailVO> deliveryDetailList = detailedOrderDao.selectDeliveryDetailList(merchant_uid);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("deliveryDetailList", deliveryDetailList);

		return result;
	}

	/**
	 * @methodName : changeDeliveryState
	 * @author : syt
	 * @param deliveryList
	 * @param state
	 * @return
	 * @returnValue : @param deliveryList
	 * @returnValue : @param state
	 * @returnValue : @return
	 * @date : 2023. 11. 15. 오후 5:03:52
	 * @description : [배송관리] 상태변경
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean changeDeliveryState(List<AdminDeliveryDTO> deliveryList, String state) throws Exception {
		boolean result = false;
		if(state.equals("deliverywait")) {
			state = "배송대기중";
			Timestamp now = new Timestamp(System.currentTimeMillis());
			if(ordersheetDao.updateOrderSheetOnDeliveryWait(deliveryList, state, now) == deliveryList.size()) {
				result= true;
			}
			
		} else {
			if (state.equals("paymentwait")) {
				state = "결제대기";
			} else if (state.equals("prepare")) {
				state = "배송준비중";
			}

			if(ordersheetDao.updateOrderSheetOnDeliveryState(deliveryList, state) == deliveryList.size()) {
				result= true;
			}
		}
		
		if(result == false) {
			throw new Exception();
		}
		
		return result;
	}
	
	/**
	 * @methodName : getDashboardData
	 * @author : syt
	 * @returnValue : 
	 * @date : 2023. 11. 22. 오후 5:58:32
	 * @description : [대시보드] 사용할 데이터 얻기
	 */
	@Override
	public Map<String, Object> getDashboardData() throws Exception {
		// 오늘 주문량, 총매출 , 배송준비중 상태인 상품 갯수, 취소/교환/반품 신청갯수
		// 요일별 총매출 , 취소/반품 매출
		LocalDate now = LocalDate.now();
		int dashboardCount = ordersheetDao.selectDashboardCount(now);
		String dashboardSales = ordersheetDao.selectDashboardSales(now); //[결제테이블 조인]total_discounted_price - point_pay_money where total_state = "완료"
		int dashboardDeliveryCount = ordersheetDao.selectDashboardDeliveryCount(); // 배송준비중
		int dashboardStateCount = detailedOrderDao.selectDashboardStateCount();  // 취소 교환 반품 숫자 (각테이블 신청날짜)
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("dashboardCount", dashboardCount);
		result.put("dashboardSales", dashboardSales);
		result.put("dashboardDeliveryCount", dashboardDeliveryCount);
		result.put("dashboardStateCount", dashboardStateCount);
		
		return result;
	}

	/**
	 * @methodName : schedulerDepositDeadlineExpired
	 * @author : syt
	 * @param ago4Day
	 * @returnValue : @param ago4Day
	 * @date : 2023. 11. 20. 오후 5:13:05
	 * @description : [스케줄러] 입금기한 만료 처리
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void schedulerDepositDeadlineExpired() throws Exception {
		boolean result = false;
		// 삭제해야할 명부 생성
		List<AdminSchedulerDataVO> schedulerDataList = ordersheetDao.selectSchedulerDepositData();

		if(schedulerDataList.size() > 0) {
			// 취소신청되어있던거 삭제
			cancellationReturnDao.deleteSchedulerCancel(schedulerDataList);
			// 주문서 상세테이블 결제상태 취소로 업데이트
			if(detailedOrderDao.updateSchedulerDetailedOrderCancel(schedulerDataList) == schedulerDataList.size()) {	
				Timestamp now = new Timestamp(System.currentTimeMillis());
				// 취소 테이블 추가(바로 완료 상태로 인설트)
				if(cancellationReturnDao.insertSchedulerCancel(schedulerDataList, now) == schedulerDataList.size()) {	
					List<AdminSchedulerPaymentDataVO> schedulerPaymentDataList = paymentDao.getSchedulerPaymentData(schedulerDataList);
					//결제 테이블 최종상태 업데이트
					if (ordersheetDao.updateSchedulerOrderSheet(schedulerPaymentDataList, now) == schedulerPaymentDataList.size()) {
						// 결제 테이블 업데이트
						if(paymentDao.updateSchedulerPayment(schedulerPaymentDataList) == schedulerPaymentDataList.size()) {
							
							for(int i = 0; i < schedulerPaymentDataList.size(); i++) {
								// 포인트 반환이 있다면
								if(schedulerPaymentDataList.get(i).getPoint_pay_money() != 0 && schedulerPaymentDataList.get(i).getMember_no() != 0) {
									// 포인트 사용/반환 테이블 points_usage_log insert
									if(pointsUsageLogDao.insertSchedulerPointLog(schedulerPaymentDataList.get(i)) == 1) {
										// 멤버 테이블 업데이트
										if(memberDao.updateSchedulerPoint(schedulerPaymentDataList.get(i)) != 1) {
											//실패했다면
											throw new Exception();
										} 
										
									} else {
										throw new Exception();
									}
								}
								
								// 쿠폰사용이 있다면
								if(schedulerPaymentDataList.get(i).getCoupon_held_no() != 0) {
									// 쿠폰사용이력 삭제(번호만 있으면 됨.)
									if(couponLogDao.deleteSchedulerCoupon(schedulerPaymentDataList.get(i).getCoupon_held_no()) == 1) {
										// 쿠폰보유(N-> Y)
										if(couponHeldDao.updateSchedulerCouponYN(schedulerPaymentDataList.get(i).getCoupon_held_no()) != 1) {
											//실패했다면
											throw new Exception();
										}
										
									} else {
										throw new Exception();
									}
								}
								
							} // for문 종료
							
							result = true;
						}
					}
				}
			}
		} else {
			result = true;
		}
		
		if(result) {
			if(!schedulerLogDao.insertSchedulerLog("결제기한만료 취소처리", schedulerDataList.size())) {
				throw new Exception();
			} 
		} else {
			throw new Exception();
		}

	}

	/**
	 * @methodName : schedulerStateManageOrderComplete
	 * @author : syt
	 * @param ago11Day
	 * @returnValue : @param ago11Day
	 * @date : 2023. 11. 27. 오후 4:04:36
	 * @description : [스케줄러] 구매확정
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void schedulerStateManageOrderComplete() throws Exception {
		List<AdminDetailedOrderDTO> detailedOrderList = detailedOrderDao.selectSchedulerStateManageOrderComplete();
		// 변경할 사항이 있다면
		if(detailedOrderList.size() > 0) {
			if(!changeStateManageOrderComplete(detailedOrderList, "구매확정")) {
				//실패시 로직
				throw new Exception();
			}
		}
		
		if(!schedulerLogDao.insertSchedulerLog("결제상태변경(구매확정)", detailedOrderList.size())) {
			//실패시 로직
			throw new Exception();
		}
		
		
	}
	
	// 배송상태변경(배송중)
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void schedulerDelivery() throws Exception {
		// 배송중으로 상태변경
		int updateDeliveryCount = ordersheetDao.schedulerDelivery();
		
		if(!schedulerLogDao.insertSchedulerLog("배송상태변경(배송중)", updateDeliveryCount)) {
			throw new Exception();
		}

	}
	// 배송상태변경(배송완료)
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void schedulerDeliveryCompleted() throws Exception {
		int updateDeliveryCompletedCount = ordersheetDao.schedulerDeliveryCompleted();
		if(!schedulerLogDao.insertSchedulerLog("배송상태변경(배송완료)", updateDeliveryCompletedCount)) {
			throw new Exception();
		}
		
	}

	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
