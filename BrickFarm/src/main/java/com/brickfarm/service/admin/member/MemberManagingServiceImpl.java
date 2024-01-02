package com.brickfarm.service.admin.member;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.brickfarm.auth.SendMessageService;
import com.brickfarm.dao.admin.AdminDAO;
import com.brickfarm.dao.couponheld.CouponHeldDAO;
import com.brickfarm.dao.couponlog.CouponLogDAO;
import com.brickfarm.dao.couponpolicy.CouponPolicyDAO;
import com.brickfarm.dao.detailedorder.DetailedOrderDAO;
import com.brickfarm.dao.inactivemember.InactiveMemberDAO;
import com.brickfarm.dao.loginlog.LoginLogDAO;
import com.brickfarm.dao.member.MemberDAO;
import com.brickfarm.dao.ordersheet.OrderSheetDAO;
import com.brickfarm.dao.pointsaccurallog.PointsAccuralLogDAO;
import com.brickfarm.dao.pointsusagelog.PointsUsageLogDAO;
import com.brickfarm.dao.product.ProductDAO;
import com.brickfarm.dao.schedulerlog.SchedulerLogDAO;
//import com.brickfarm.dao.schedulerlog.SchedulerLogDAO;
import com.brickfarm.dao.withdrawmember.WithdrawMemberDAO;
import com.brickfarm.dto.admin.kmh.AdminAvailableCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminGiveCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminLogDTO;
import com.brickfarm.dto.admin.kmh.AdminMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminMessageDTO;
import com.brickfarm.dto.admin.kmh.AdminMessageResponseDTO;
import com.brickfarm.dto.admin.kmh.AdminNoActiveMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminOrderMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminWithdrawMemberDTO;
import com.brickfarm.etc.kmh.PaginationInfo;
import com.brickfarm.vo.admin.kmh.AdminChartVO;
import com.brickfarm.vo.admin.kmh.AdminCouponLogVO;
import com.brickfarm.vo.admin.kmh.AdminCouponVo;
import com.brickfarm.vo.admin.kmh.AdminDetailOrderInfoVO;
import com.brickfarm.vo.admin.kmh.AdminDetailOrderProductsVO;
import com.brickfarm.vo.admin.kmh.AdminInactiveMemberVO;
import com.brickfarm.vo.admin.kmh.AdminLoginLogVO;
import com.brickfarm.vo.admin.kmh.AdminMemberDashboardVO;
import com.brickfarm.vo.admin.kmh.AdminMemberOrderVO;
import com.brickfarm.vo.admin.kmh.AdminMemberVO;
import com.brickfarm.vo.admin.kmh.AdminOrderMemberVO;
import com.brickfarm.vo.admin.kmh.AdminPointLogVO;
import com.brickfarm.vo.admin.kmh.AdminWithdrawMemberVO;
import com.brickfarm.vo.admin.ysh.AdminVO;

@Service
public class MemberManagingServiceImpl implements MemberManagingService {

	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	@Inject
	private MemberDAO mDao;

	@Inject
	private InactiveMemberDAO iMDao;

	@Inject
	private OrderSheetDAO oDao;

	@Inject
	private WithdrawMemberDAO wMDao;

	@Inject
	private CouponPolicyDAO cPDao;

	@Inject
	private CouponHeldDAO cHDao;

	@Inject
	private CouponLogDAO cLDao;

	@Inject
	private LoginLogDAO lLDao;
	
	@Inject
	private PointsAccuralLogDAO pALDao;
	
	@Inject
	private PointsUsageLogDAO pULDao;
	
	@Inject
	private DetailedOrderDAO dDao;
	
	@Inject
	private ProductDAO pDao;
	
	@Inject
	private SendMessageService sMService;
	
	@Inject
	private SchedulerLogDAO sLDao;


	/**
	 * @methodName : CountAboutMember
	 * @author : kmh
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @date : 2023. 10. 7. 오후 4:31:30
	 * @description : 대시보드에 들어갈 최근 가입 회원, 방문 회원, 탈퇴회원, 전체 회원 수 구하는 메서드
	 */
	@Override
	public AdminMemberDashboardVO countAboutMember() throws Exception {

		return mDao.selectMemberDashboardCount();

	}

	/**
	 * @methodName : findRecentRegistMember
	 * @author : kmh
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 7. 오후 5:35:22
	 * @description : 최근 가입한 회원 조회(5명 제한)
	 */
	@Override
	public List<AdminMemberVO> findRecentRegistMember() throws Exception {

		return mDao.selectRecentRegistMember();
	}

	/**
	 * 전체 회원 조회를 위한 메서드
	 * 
	 * @author : kmh
	 * @param : tmpMember - 조회하기 위해 입력한 값
	 * @return
	 * @throws Exception
	 * @returnValue : @param tmpMember - 회원 조회를 위해 검색 조건에 입력되어 있는 값
	 * @returnValue : @return DB에서 조회된 값
	 * @date : 2023. 10. 7. 오후 4:07:50
	 * @description : 검색 조건에 입력되는 값을 파라메터로 받아 해당 조건에 맞는 회원 모두를 조회하여 출력한다.
	 */
	@Override
	public List<AdminMemberVO> findMember(AdminMemberDTO tmpMember) throws Exception {

		return mDao.selectMember(tmpMember);
	}

	/**
	 * @methodName : findInactiveMember
	 * @author : kmh
	 * @param tmpMember
	 * @return
	 * @throws Exception
	 * @returnValue : @param tmpMember
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 7. 오후 7:22:29
	 * @description : 휴먼 회원 테이블 조회
	 */
	@Override
	public List<AdminInactiveMemberVO> findInactiveMember(AdminNoActiveMemberDTO tmpMember) throws Exception {

		return iMDao.selectInactiveMember(tmpMember);
	}

	/**
	 * @methodName : findOrderMember
	 * @author : kmh
	 * @param tmpMember
	 * @return
	 * @throws Exception
	 * @returnValue : @param tmpMember
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 10. 오후 7:18:32
	 * @description : 주문 이력 있는 회원 조회
	 */
	@Override
	public List<AdminOrderMemberVO> findOrderMember(AdminOrderMemberDTO tmpMember) throws Exception {

		return oDao.selectOrderMember(tmpMember);
	}

	/**
	 * @methodName : findWithdrawMember
	 * @author : kmh
	 * @param tmpMember
	 * @return
	 * @throws Exception
	 * @returnValue : @param tmpMember
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 11. 오후 7:01:27
	 * @description : 탈퇴 회원 조회
	 */
	@Override
	public List<AdminWithdrawMemberVO> findWithdrawMember(AdminNoActiveMemberDTO tmpMember) throws Exception {

		return wMDao.selectWithdrawMember(tmpMember);
	}

	/**
	 * @methodName : findAllCoupon
	 * @author : kmh
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 14. 오후 9:18:54
	 * @description : 쿠폰 목록 조회
	 */
	@Override
	public List<AdminCouponVo> findAllCoupon() throws Exception {

		return cPDao.selectCouponList();
	}

	/**
	 * @methodName : findAllCoupon
	 * @author : kmh
	 * @param couponOption
	 * @return
	 * @returnValue : @param couponOption
	 * @returnValue : @return
	 * @date : 2023. 10. 18. 오후 8:29:50
	 * @description : 쿠폰 옵셔널 조회
	 */
	@Override
	public List<AdminCouponVo> findAllCoupon(AdminCouponDTO couponOption) throws Exception {

		return cPDao.selectCouponList(couponOption);
	}

	/**
	 * @methodName : removeMember
	 * @author : kmh
	 * @param tmpdelMember
	 * @throws Exception
	 * @returnValue : @param tmpdelMember
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 16. 오후 7:23:52
	 * @description : 회원 탈퇴 시키기
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeMemberTransaction(AdminWithdrawMemberDTO tmpdelMember) throws Exception {
		
		boolean result = false;
		if (wMDao.insertWithdrawMember(tmpdelMember) == 1) {
			if(oDao.updateWithdrawMember(tmpdelMember) >= 0) {				
				if (mDao.deleteMember(tmpdelMember.getMember_id()) == 1) {
					result = true;
				}
			}

		}

		if(!result) {
			throw new Exception();
		}
		
		return result;

	}

	/**
	 * @methodName : makeCoupon
	 * @author : kmh
	 * @param tmpCoupon
	 * @return
	 * @throws Exception
	 * @returnValue : @param tmpCoupon
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 16. 오후 8:49:57
	 * @description : 쿠폰 만들기
	 */
	@Override
	public boolean makeCoupon(AdminCouponDTO tmpCoupon) throws Exception {

		boolean result = false;

		if (cPDao.insertCouponPolicy(tmpCoupon) > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * @methodName : removeCoupon
	 * @author : kmh
	 * @param coupon_policy_no
	 * @return
	 * @throws Exception
	 * @returnValue : @param coupon_policy_no
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 18. 오후 7:47:17
	 * @description : 쿠폰 삭제
	 */
	@Override
	public boolean removeCoupon(String coupon_policy_no) throws Exception {
		boolean result = false;

		if (cPDao.deleteCoupon(coupon_policy_no) > 0) {
			result = true;
		}
		return result;
	}

	/**
	 * @methodName : giveCouponToMember
	 * @author : kmh
	 * @param couponInfo
	 * @return
	 * @throws Exception
	 * @returnValue : @param couponInfo
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 23. 오후 8:02:25
	 * @description : 아이디별 쿠폰 일괄지급
	 */
	@Override
	public boolean giveCouponToMember(AdminGiveCouponDTO couponInfo) throws Exception {

		return cHDao.insertCouponToMember(couponInfo);
	}

	/**
	 * @methodName : findLoginMember
	 * @author : kmh
	 * @param loginMember
	 * @return
	 * @throws Exception
	 * @returnValue : @param loginMember
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 30. 오후 3:06:10
	 * @description : 로그인 이력 조회
	 */
	@Override
	public List<AdminLoginLogVO> findLoginMember(AdminMemberDTO loginMember) throws Exception {

		return lLDao.selectLoginLog(loginMember);
	}

	/**
	 * @methodName : findCouponLog
	 * @author : kmh
	 * @param couponLog
	 * @return
	 * @throws Exception
	 * @returnValue : @param couponLog
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 10. 30. 오후 5:34:50
	 * @description : 쿠폰 사용 / 적립 로그 조회
	 */
	@Override
	public List<AdminCouponLogVO> findCouponLog(AdminLogDTO couponLog) throws Exception {

		List<AdminCouponLogVO> result = null;

		if (couponLog.getUseORaccrual().equals("use")) {
			result = cLDao.selectCouponUseLog(couponLog);
		} else if (couponLog.getUseORaccrual().equals("all") || couponLog.getUseORaccrual().equals("accrual")) {
			result = cLDao.selectCouponAllLog(couponLog);
		}
		return result;
	}

	/**
	 * @methodName : findPointLog
	 * @author : kmh
	 * @param pointLog
	 * @return
	 * @throws Exception 
	 * @returnValue : @param pointLog
	 * @returnValue : @return
	 * @date : 2023. 11. 1. 오후 4:03:33
	 * @description : 포인트 사용 / 적립 조회
	 */
	@Override
	public List<AdminPointLogVO> findPointLog(AdminLogDTO pointLog) throws Exception {
		List<AdminPointLogVO> result = new ArrayList<AdminPointLogVO>();
		

		if (pointLog.getUseORaccrual().equals("use")) {
			result.addAll(pULDao.selectPointUseLog(pointLog));	
			
		} else if (pointLog.getUseORaccrual().equals("accrual")) {
			result.addAll(pALDao.selectPointAccrualLog(pointLog));
			
		} else if(pointLog.getUseORaccrual().equals("all")) {
			result.addAll(pULDao.selectPointUseLog(pointLog));
			result.addAll(pALDao.selectPointAccrualLog(pointLog));
			
		}
		return result;

	}

	/**
	 * @methodName : giveCouponToGrade
	 * @author : kmh
	 * @param couponInfo
	 * @return
	 * @throws Exception
	 * @returnValue : @param couponInfo
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 2. 오후 1:09:17
	 * @description : 등급별 쿠폰 지급
	 */
	@Override
	public boolean giveCouponToGrade(AdminGiveCouponDTO couponInfo) throws Exception {
		boolean result = false;
		if(cHDao.insertCouponToGrade(couponInfo) > -1) {
			result = true;
		};
		return result; 
	}

	/**
	 * @methodName : updateInactvieMember
	 * @author : kmh
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 5. 오후 8:30:25
	 * @description : 6개월 내 로그인 기록 없는 회원 inactive_status : Y 업데이트, 휴먼회원 테이블에 insert
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateInactiveMember() throws Exception {
		
		boolean result = false;
		
		List<Integer> member_no = lLDao.selectInactiveMemerInSixMonth();
		if(member_no.size() > 0) {
			if(member_no.size() == mDao.updateInactiveStatus(member_no)) {
				if(member_no.size() == iMDao.insertInactiveMember(member_no)) {
					result = sLDao.insertSchedulerLog("휴면 회원 전환", member_no.size());
				};
			};
		} else if(member_no.size() == 0) {
			result = sLDao.insertSchedulerLog("휴면회원 전환", 0);
		}
		
		if(!result) {
			throw new Exception();
		}
		
		return result;
	}

	/**
	 * @methodName : giveBirthDayCoupon
	 * @author : kmh
	 * @param mmDD
	 * @throws Exception
	 * @returnValue : @param mmDD
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 5. 오후 8:35:42
	 * @description : 생일 쿠폰 발급
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean giveBirthDayCoupon(String mmDD) throws Exception {
		boolean result = false;
		List<String> birthDayMemberCnt = mDao.selectBirthDayMember(mmDD);
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat date = new SimpleDateFormat("yyyy년 MM월 dd일");
		SimpleDateFormat reverseTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		cal.add(Calendar.HOUR_OF_DAY, 9);
		String reverse = reverseTime.format(cal.getTime());
		
		cal.add(Calendar.DAY_OF_MONTH, 29);		
		
		if(birthDayMemberCnt.size() > 0) {
			int givenBirthdayCouponCnt = cHDao.insertBirthDayCoupon(mmDD);
			if(birthDayMemberCnt.size() == givenBirthdayCouponCnt) {
				List<AdminMessageDTO> messageDto = new ArrayList<AdminMessageDTO>();
				for(String phoneNumber: birthDayMemberCnt ) {
					String newNum = phoneNumber.replaceAll("[^0-9]", "");
					AdminMessageDTO birthDayMember = new AdminMessageDTO();
					birthDayMember.setTo(newNum);
					birthDayMember.setContent("고객님! 생일 축하합니다. 생일 기념 할인 쿠폰일 발급되었습니다. 본 쿠폰은 오늘부터 " + date.format(cal.getTime()) + "까지 이용 가능합니다.");
					messageDto.add(birthDayMember);
				}
				AdminMessageResponseDTO response = sMService.sendSms(messageDto, reverse);
				if(response.getStatusCode().equals("202")) {
					result = sLDao.insertSchedulerLog("생일쿠폰 지급", givenBirthdayCouponCnt);

				}
			};
		} else {
			result = sLDao.insertSchedulerLog("생일쿠폰 지급", 0);
		}
		
		if(!result) {
			throw new Exception();
		}
		
		return result;
		
	}

	/**
	 * @methodName : expireCoupon
	 * @author : kmh
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 5. 오후 8:41:53
	 * @description : 쿠폰 만료 시키기
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean expireCoupon() throws Exception {

		boolean result = false;
		
		List<Integer> expireCouponList = cHDao.selectExpirationCoupon();
		if(expireCouponList.size() > 0) {
			if(expireCouponList.size() == cHDao.updateAvailableCoupon(expireCouponList)) {
				if(expireCouponList.size() == cLDao.insertExpireCoupon(expireCouponList)) {
					result = sLDao.insertSchedulerLog("쿠폰 만료 처리", expireCouponList.size());
				};
			};
		} else if(expireCouponList.size() == 0){
			result = sLDao.insertSchedulerLog("쿠폰 만료 처리", 0);
		}
		
		if(!result) {
			throw new Exception();
		}
		
		return result;
	}

	/**
	 * @methodName : updateMemberGrade
	 * @author : kmh
	 * @throws Exception
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 6. 오후 12:45:28
	 * @description : 매월 1일 등급 update 및 쿠폰 지급 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateMemberGrade() throws Exception {
		
		boolean normal = false;
		boolean silver = false;
		boolean gold = false;
		boolean result = false;
		// 최근 6개월간 구매금액 50만원 이하인 사람 select , update 등급 : 일반
		List<AdminMemberVO> normalMember = mDao.selectMemberGrade("일반");
		if(normalMember.size() > 0) {
			System.out.println(normalMember.size());
			System.out.println(mDao.updateMemberGrade(normalMember, "일반"));
			if(normalMember.size() == mDao.updateMemberGrade(normalMember, "일반")) {
				normal = true;
			};
		} else if(normalMember.size() == 0) {
			normal = true;
		}
		
		// 최근 6개월간 구매금액 50만원 이상인 사람 select, update 등급 : 실버
		List<AdminMemberVO> silverMember = mDao.selectMemberGrade("실버");
		if(silverMember.size() > 0) {
			if(silverMember.size() == mDao.updateMemberGrade(silverMember, "실버")) {
				silver = true;
			};
		} else if(silverMember.size() == 0) {
			silver = true;
		}
		
		List<AdminMemberVO> goldMember = mDao.selectMemberGrade("골드");
		// 최근 6개월간 구매금액 100만원 이상인 사람 select, update 등급 : 골드 
		if(goldMember.size() > 0) {
			if(goldMember.size() == mDao.updateMemberGrade(goldMember, "골드")) {
				gold = true;
			};
		} else if(goldMember.size() == 0) {
			gold = true;
		}
		
		if(normal && silver && gold) {
			//insert 쿠폰 : 실버 등급 쿠폰
			AdminGiveCouponDTO tmpcouponInfo = new AdminGiveCouponDTO();
			tmpcouponInfo.setMember_grade_name("실버");
			tmpcouponInfo.setCoupon_policy_no(26);
			if(silverMember.size() == cHDao.insertCouponToGrade(tmpcouponInfo)) {
				// insert 쿠폰 : 골드 등급 쿠폰
				tmpcouponInfo.setCoupon_policy_no(27);
				tmpcouponInfo.setMember_grade_name("골드");
				if(goldMember.size() == cHDao.insertCouponToGrade(tmpcouponInfo)) {
					result = sLDao.insertSchedulerLog("등급 업데이트 완료", normalMember.size()+silverMember.size()+goldMember.size());
				}
			}
		}
		
		if(!result) {
			throw new Exception();
		}
		
		return result;
		
	}

	/**
	 * @methodName : changeAvailableCoupon
	 * @author : kmh
	 * @param couponInfo
	 * @return
	 * @throws Exception 
	 * @returnValue : @param couponInfo
	 * @returnValue : @return
	 * @date : 2023. 11. 6. 오후 7:37:19
	 * @description : 쿠폰 상태(수동 - Y, 자동 - N, 삭제 - D) 업데이트
	 */
	@Override
	public boolean changeAvailableCoupon(AdminAvailableCouponDTO couponInfo) throws Exception {
		boolean result = false;
		if(couponInfo.getChange_coupon_no().size() ==  cPDao.updateAvailable(couponInfo)) {
			result = true;
		};
		
		if(!result) {
			throw new Exception();
		}
		return result;
	}

	
	/**
	 * @methodName : countAvailableCoupon
	 * @author : kmh
	 * @param delCouponNo
	 * @return
	 * @throws Exception 
	 * @returnValue : @param delCouponNo
	 * @returnValue : @return
	 * @date : 2023. 11. 6. 오후 3:57:13
	 * @description : 삭제할 쿠폰을 보유한 회원 수
	 */
	@Override
	public int countAvailableCoupon(AdminGiveCouponDTO delCouponNo) throws Exception {
		// TODO Auto-generated method stub
		
		return cHDao.selectAvailableCoupon(delCouponNo);
	}

	/**
	 * @methodName : activeMember
	 * @author : kmh
	 * @param member_id
	 * @return
	 * @throws Exception
	 * @returnValue : @param member_id
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 7. 오후 5:42:19
	 * @description : 휴먼회원 활성화 시키기
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean activeMember(String member_id) throws Exception{
		boolean result = false;
		if(iMDao.updateActiveMember(member_id)) {
			if(mDao.updateInactiveStatus(member_id)) {
				result = true;				
			}
		}
		
		if(!result) {
			throw new Exception();
		}
		
		return result;
	}

	/**
	 * @methodName : findBestCustomer
	 * @author : kmh
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 17. 오후 3:42:13
	 * @description : 1개월 내 주문 건수 가장 많은 순으로 회원 찾기
	 */
	@Override
	public List<AdminOrderMemberVO> findBestCustomer() throws Exception {
		
		return oDao.selectBestCustomer();
	}

	/**
	 * @methodName : sendMessageToMember
	 * @author : kmh
	 * @param tmpMemssage
	 * @return
	 * @throws Exception
	 * @returnValue : @param tmpMemssage
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 20. 오후 4:03:41
	 * @description : 고객들에게 문자 보내기
	 */
	@Override
	public boolean sendMessageToMember(AdminMessageDTO tmpMemssage) throws Exception {

		boolean result = false;
		
		List<String> phoneNumberList = mDao.selectMemberPhoneNumber(tmpMemssage);
		if(phoneNumberList.size() > 0) {
			List<AdminMessageDTO> messageDto = new ArrayList<AdminMessageDTO>();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat reverseTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String reverse = reverseTime.format(cal.getTime());
			
			for(String phoneNumber: phoneNumberList ) {
				String newNum = phoneNumber.replaceAll("[^0-9]", "");
				AdminMessageDTO member = new AdminMessageDTO();
				member.setTo(newNum);
				member.setContent(tmpMemssage.getContent());
				messageDto.add(member);
			}
			
			AdminMessageResponseDTO response = sMService.sendSms(messageDto, reverse);
			if(response.getStatusCode().equals("202")) {
				result = true;
			}
			
		}
		
		if(!result) {
			throw new Exception();
		}
		
		return result;
	}
	
	/**
	 * @methodName : findOrderList
	 * @author : kmh
	 * @param member_no
	 * @throws Exception
	 * @returnValue : @param member_no
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 22. 오후 3:04:44
	 * @description : 회원 주문 내역 가져오기
	 */
	@Override
	@Transactional
	public Map<String, Object> findOrderList(int member_no, int pageNo, boolean withdraw_member) throws Exception {
		
		int total_count = oDao.selectOrderListCount(member_no);

		PaginationInfo pi = new PaginationInfo();
		pi.setPageNo(pageNo);
		pi.setTotalCount(total_count);
		pi.setTotalPage(total_count, pi.getRowOfNums());
		pi.setStartRowIndex();
		pi.setTotalGroup();
		pi.setPageBlockOfCurrentPage();
		pi.setStartNumOfCurPageGroup();
		pi.setEndNumOfCurPageGroup();
		
		List<AdminMemberOrderVO> orderList = null;
		if(withdraw_member) {
			orderList = wMDao.selectMemberOrderList(member_no, pi);
		} else {
			
			orderList = oDao.selectMemberOrderList(member_no, pi);
		}
		Map<String, Object> memberOrderInfo = new HashMap<String, Object>();
		memberOrderInfo.put("pagingInfo", pi);
		memberOrderInfo.put("orderList", orderList);
		
		return memberOrderInfo;
		
	}
	

	/**
	 * @methodName : findDetailOrder
	 * @author : kmh
	 * @param merchant
	 * @return
	 * @throws Exception
	 * @returnValue : @param merchant
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 23. 오후 5:38:02
	 * @description : 회원 주문 상세내역 조회
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public AdminDetailOrderInfoVO findDetailOrder(String merchant, boolean withdraw_member) throws Exception {
		
		AdminDetailOrderInfoVO orderDetail = null;
		if(withdraw_member) {
			orderDetail = dDao.selectOrderDetailByMerchantUIDInWithdrawMember(merchant);
		} else {
			orderDetail = dDao.selectOrderDetailByMerchantUID(merchant);
		}
		if(orderDetail != null) {
			List<AdminDetailOrderProductsVO> orderDetailProducts = pDao.selectDetailOrderProducts(merchant);
			if(orderDetailProducts.size() > 0) {
				orderDetail.setProductList(orderDetailProducts);
			}
		}
		
		return orderDetail;
	}

	@Override
	public Map<String, Object> findWithdrawMemberInfo(AdminNoActiveMemberDTO tmpMember, int pageNo) throws Exception {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> memberInfo = new HashMap<String, Object>();
		List<AdminWithdrawMemberVO> member = wMDao.selectWithdrawMember(tmpMember);
		
		int total_count = lLDao.selectLoginLogCount(tmpMember.getMember_no());
	
		
		PaginationInfo pi = new PaginationInfo();
		pi.setPageNo(pageNo);
		pi.setTotalCount(total_count);
		pi.setTotalPage(total_count, pi.getRowOfNums());
		pi.setStartRowIndex();
		pi.setTotalGroup();
		pi.setPageBlockOfCurrentPage();
		pi.setStartNumOfCurPageGroup();
		pi.setEndNumOfCurPageGroup();
		
		
		List<AdminLoginLogVO> loginLog = lLDao.selectWithdrawMemberLoginLog(tmpMember.getMember_no(), pi);
		
		memberInfo.put("member", member);
		memberInfo.put("loginLog", loginLog);
		
		result.put("pagingInfo", pi);
		result.put("memberInfo", memberInfo);
		
		return result;
	}
	
	/**
	 * @methodName : findTimeToOrder
	 * @author : kmh
	 * @return
	 * @throws Exception
	 * @returnValue : @return
	 * @returnValue : @throws Exception
	 * @date : 2023. 11. 28. 오후 6:34:00
	 * @description : 가입부터 주문까지 걸리는 시간 구하기
	 */
	@Override
	public AdminChartVO findTimeToOrder() throws Exception {
		return mDao.selectTimeToOrder();
	}


	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	@Inject
	private AdminDAO aDao;
	
	//관리자 로그인 -- 로그인시 아이디와 비밀번호 비교후 맞으면 role 가져오기
	@Override
	public String loginAdminCheck(String admin_id, String admin_password) throws Exception {
		AdminVO admin = new AdminVO();
		admin.setAdmin_id(admin_id);
		admin.setAdmin_password(admin_password);
		
		return aDao.loginAdminCheck(admin);
		
	}
	
	//관리자 로그인 -- 객체 가져오기
	@Override
	public AdminVO adminLogin(String admin_id, String admin_password) throws Exception {
		AdminVO admin = new AdminVO();
		admin.setAdmin_id(admin_id);
		admin.setAdmin_password(admin_password);
		
		return aDao.loginAdmin(admin);
	}

	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
