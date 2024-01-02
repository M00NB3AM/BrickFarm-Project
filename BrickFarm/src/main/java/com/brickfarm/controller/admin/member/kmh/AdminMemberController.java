package com.brickfarm.controller.admin.member.kmh;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.brickfarm.controller.HomeController;
import com.brickfarm.dto.admin.kmh.AdminAvailableCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminGiveCouponDTO;
import com.brickfarm.dto.admin.kmh.AdminLogDTO;
import com.brickfarm.dto.admin.kmh.AdminMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminMessageDTO;
import com.brickfarm.dto.admin.kmh.AdminNoActiveMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminOrderMemberDTO;
import com.brickfarm.dto.admin.kmh.AdminWithdrawMemberDTO;
import com.brickfarm.service.admin.member.MemberManagingService;
import com.brickfarm.vo.admin.kmh.AdminChartVO;
import com.brickfarm.vo.admin.kmh.AdminCouponLogVO;
import com.brickfarm.vo.admin.kmh.AdminCouponVo;
import com.brickfarm.vo.admin.kmh.AdminDetailOrderInfoVO;
import com.brickfarm.vo.admin.kmh.AdminInactiveMemberVO;
import com.brickfarm.vo.admin.kmh.AdminLoginLogVO;
import com.brickfarm.vo.admin.kmh.AdminMemberDashboardVO;
import com.brickfarm.vo.admin.kmh.AdminMemberOrderVO;
import com.brickfarm.vo.admin.kmh.AdminMemberVO;
import com.brickfarm.vo.admin.kmh.AdminOrderMemberVO;
import com.brickfarm.vo.admin.kmh.AdminPointLogVO;
import com.brickfarm.vo.admin.kmh.AdminWithdrawMemberVO;

@Controller
@RequestMapping("admin/member/*")
public class AdminMemberController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Inject
	private MemberManagingService mMService;

	@RequestMapping("dashboard")
	public String dashboard(Model model) throws Exception {
		
		AdminMemberDashboardVO dashboard = mMService.countAboutMember();
		List<AdminMemberVO> recentRegistMember = mMService.findRecentRegistMember();
		List<AdminOrderMemberVO> orderListMember = mMService.findBestCustomer();
		model.addAttribute("dashboard", dashboard);
		model.addAttribute("recentRegistMember", recentRegistMember);
		model.addAttribute("orderListMember", orderListMember);
		

		return "admin/member/dashboard";
	}
	
	@RequestMapping("takeTimeToOrder")
	public ResponseEntity<Object> takeTimeToOrder(){
		ResponseEntity<Object> result = null;
	
		try {
			
			AdminChartVO chartList = mMService.findTimeToOrder();
			
			result = new ResponseEntity<Object>(chartList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>("fail", HttpStatus.FORBIDDEN);
		}
		
		return result;
		
	}

	@RequestMapping("memberList")
	public String memberList(Model model, AdminMemberDTO tmpMember) throws Exception {
		
		List<AdminMemberVO> selectedMember = mMService.findMember(tmpMember);
		model.addAttribute("selectedMember", selectedMember);

		return "admin/member/searchMemberList";
	}

	@RequestMapping("manageMember")
	public String mangeMember(AdminMemberDTO loginMember, Model model) {
		System.out.println(loginMember.toString());
		loginMember.setDate_start("");
		loginMember.setDate_end("");
		
		try {
			List<AdminLoginLogVO> loginList = mMService.findLoginMember(loginMember);
			model.addAttribute("loginList", loginList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/member/loginLogList";
	}

	@RequestMapping(value = "coupon", method = RequestMethod.GET)
	public String coupon(Model model, String status) throws Exception {
		List<AdminCouponVo> couponList = mMService.findAllCoupon();
		model.addAttribute("couponList", couponList);
		if(status != null) {
			model.addAttribute("status", status);
		}
		return "admin/member/coupon";
	}
	
	@RequestMapping(value = "coupon", method = RequestMethod.POST)
	public ResponseEntity<List<AdminCouponVo>> selectOptionCoupon(@RequestBody AdminCouponDTO couponOption) {
		ResponseEntity<List<AdminCouponVo>> result = null;
		List<AdminCouponVo> couponList = null;
		try {
			couponList = mMService.findAllCoupon(couponOption);
			result = new ResponseEntity<List<AdminCouponVo>>(couponList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<List<AdminCouponVo>>(couponList, HttpStatus.FORBIDDEN);
		}
		
		return result;
	
	}
	
	
	@RequestMapping(value = "changeAvailableCoupon", method = RequestMethod.POST)
	public ResponseEntity<String> changeAvailableCoupon(@RequestBody AdminAvailableCouponDTO couponInfo) {
		ResponseEntity<String> result = null;
		try {
			mMService.changeAvailableCoupon(couponInfo);
			result = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
			
		}
		
		return result;
	
	}
	

	@RequestMapping("makeCoupon")
	public String makeCoupon(AdminCouponDTO tmpCoupon, Model model) throws Exception {

		if (tmpCoupon.getMember_grade_name().equals("all")) {
			tmpCoupon.setMember_grade_name(null);
		}
		mMService.makeCoupon(tmpCoupon);

		return "redirect: coupon";
	}

	@RequestMapping("deleteCoupon")
	public ResponseEntity<String> deleteCoupon(@RequestParam("delCouponNo") String coupon_policy_no) {
		ResponseEntity<String> result = null;
		try {
			if(mMService.removeCoupon(coupon_policy_no)) {
				result = new ResponseEntity<String>("success", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	
		return result;
		
	}
	
	@RequestMapping("giveCouponToMember")
	public ResponseEntity<String> giveCouponToMember(@RequestBody AdminGiveCouponDTO couponInfo) {
		ResponseEntity<String> result = null;
		try {
			if(mMService.giveCouponToMember(couponInfo)) {
				 result = new ResponseEntity<String>("success", HttpStatus.OK);
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}
	
		return result;
		
	}
	
//	@RequestMapping("giveCoupon")
//	public ResponseEntity<String> giveCoupon(@RequestBody AdminGiveCouponDTO couponInfo) {
//		System.out.println("controller 단 : " + couponInfo.toString());
//		ResponseEntity<String> result = null;
//		
//		try {
//			mMService.giveCouponToGrade(couponInfo);
//			result = new ResponseEntity<String>("success", HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
//		}
//		
//		return result;
//	}
	
	@RequestMapping("giveCoupon")
	public String giveCoupon(AdminGiveCouponDTO couponInfo) throws Exception {
		String status = "fail";
		if(mMService.giveCouponToGrade(couponInfo)) {
			status = "success";
		};
		return "redirect:coupon?status=" + status;
	}
	
	@RequestMapping(value = "countAvailableCoupon", method = RequestMethod.POST)
	public ResponseEntity<Object> countAvailableCoupon(@RequestBody AdminGiveCouponDTO delCouponNo){
		ResponseEntity<Object> result = null;
		
		try {
			int availableCoupon = mMService.countAvailableCoupon(delCouponNo);
			result = new ResponseEntity<Object>(availableCoupon, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>("fail", HttpStatus.FORBIDDEN);
		}
		
		
		return result;
	}

	@RequestMapping(value = "searchMember", method = RequestMethod.POST)
	public String findMember(AdminMemberDTO tmpMember, Model model) throws Exception {

		System.out.println(tmpMember.toString());
		List<AdminMemberVO> selectedMember = mMService.findMember(tmpMember);
		model.addAttribute("selectedMember", selectedMember);

		return "admin/member/searchMemberList";
	}
	
	@RequestMapping(value = "searchMember", method = RequestMethod.GET)
	public ResponseEntity<Object> findMember(@RequestParam int member_no) throws Exception {
		
		ResponseEntity<Object> result = null;
		AdminMemberDTO tmpMember = new AdminMemberDTO();
		tmpMember.setMember_no(member_no);
		
		try {
			List<AdminMemberVO> selectedMember = mMService.findMember(tmpMember);
			result = new ResponseEntity<Object>(selectedMember, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>("fail", HttpStatus.FORBIDDEN);
		}
		
		
		return result;

	}

	@RequestMapping("orderMember")
	public String findOrderMember(AdminOrderMemberDTO tmpMember, Model model) {

		List<AdminOrderMemberVO> orderMember;
		try {
			orderMember = mMService.findOrderMember(tmpMember);
			model.addAttribute("orderMember", orderMember);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "admin/member/orderMemberList";
	}

	@RequestMapping("inactiveMember")
	public String findInactiveMember(AdminNoActiveMemberDTO tmpMember, Model model) {

		List<AdminInactiveMemberVO> inactiveMember;
		try {
			inactiveMember = mMService.findInactiveMember(tmpMember);
			model.addAttribute("inactiveMember", inactiveMember);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/member/inactiveMemberList";
	}

	@RequestMapping(value = "withdrawMember", method = RequestMethod.POST)
	public String findWithdrawMember(AdminNoActiveMemberDTO tmpMember, Model model) {
		try {
			List<AdminWithdrawMemberVO> withdrawMember = mMService.findWithdrawMember(tmpMember);
			model.addAttribute("withdrawMember", withdrawMember);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/member/withdrawMemberList";
	}
	
	@RequestMapping(value = "withdrawMember", method = RequestMethod.GET)
	public ResponseEntity<Object> findWithMember(@RequestParam(name="member_no") int member_no, @RequestParam(name="pageNo", defaultValue = "1") int pageNo) throws Exception {
		

		ResponseEntity<Object> result = null;
		AdminNoActiveMemberDTO tmpMember = new AdminNoActiveMemberDTO();
		tmpMember.setMember_no(member_no);
		
		try {
			Map<String, Object> selectedMember = mMService.findWithdrawMemberInfo(tmpMember, pageNo);
			result = new ResponseEntity<Object>(selectedMember, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<Object>("fail", HttpStatus.FORBIDDEN);
		}
		
		
		return result;

	}

	@RequestMapping(value="deleteMember", method = RequestMethod.POST)
	public ResponseEntity<String> removeMember(@RequestBody AdminWithdrawMemberDTO tmpdelMember) {
		ResponseEntity<String> result = null;

		try {
			if (mMService.removeMemberTransaction(tmpdelMember)) {
				result = new ResponseEntity<String>("success", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}

		return result;

	}

	@RequestMapping("loginLogList")
	public String findLoginLogList(AdminMemberDTO loginMember, Model model) {
		System.out.println(loginMember.toString());
		try {
			List<AdminLoginLogVO> loginList = mMService.findLoginMember(loginMember);
			model.addAttribute("loginList", loginList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "admin/member/loginLogList";
	}

	@RequestMapping("couponList")
	public String findCouponList(AdminLogDTO couponLog, Model model) {
		
		try {
			List<AdminCouponLogVO> couponLogList = mMService.findCouponLog(couponLog);
			model.addAttribute("couponLogList", couponLogList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/member/couponList";
	}

	@RequestMapping("pointAccrualList")
	public String findPointAccrualList(AdminLogDTO pointLog, Model model) {
		try {
			List<AdminPointLogVO> pointLogList = mMService.findPointLog(pointLog);

			model.addAttribute("pointLogList", pointLogList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "admin/member/pointAccrualList";
	}
	
	@RequestMapping("activeMember")
	public ResponseEntity<String> activeMember(@RequestParam String member_id){
		ResponseEntity<String> result = null;
		
		try {
			if (mMService.activeMember(member_id)) {
				result = new ResponseEntity<String>("success", HttpStatus.OK);
			} else {
				result = new ResponseEntity<String>("problem", HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}

		return result;
	}
	
//	@Inject
//	SendMessageService sMService;
	
//	@RequestMapping(value="sendMessage", method = RequestMethod.POST)
//	public String sendSms(@RequestBody AdminMessageDTO messageDto, Model model) throws Exception {
//		AdminMessageResponseDTO response = sMService.sendSms(messageDto);
//		System.out.println(response.toString());
//		model.addAttribute("response", response);
//		return "admin/member/result";
//	}
//	
	
	@RequestMapping("sendMessageToMember")
	public ResponseEntity<String> sendMessageToMember(@RequestBody AdminMessageDTO tmpMemssage){
		ResponseEntity<String> result = null;
		
		try {
			mMService.sendMessageToMember(tmpMemssage);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<String>("fail", HttpStatus.FORBIDDEN);
		}

		return result;
	}
	
	@RequestMapping("showOrderList")
	public ResponseEntity<Object> findOrderList(
			@RequestParam("member_no") int member_no, 
			@RequestParam(name = "pageNo", defaultValue = "1") int pageNo, 
			@RequestParam(name="withdraw_member", defaultValue = "false") boolean withdraw_member
		){
		ResponseEntity<Object> result = null;
		try {
			Map<String, Object> memberOrderList = mMService.findOrderList(member_no, pageNo, withdraw_member);
			result = new ResponseEntity<Object>(memberOrderList, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new ResponseEntity<Object>("fail", HttpStatus.FORBIDDEN);
		}
		

		return result;
	}
	
	@RequestMapping("showDetailOrder")
	public ResponseEntity<Object> findDetailOrder(@RequestParam("merchant_uid") String merchant, @RequestParam(name = "withdraw_member", defaultValue = "false") boolean withdraw_member){
		ResponseEntity<Object> result = null;
		  try {
			AdminDetailOrderInfoVO detailedOrderList = mMService.findDetailOrder(merchant, withdraw_member);
			result = new ResponseEntity<Object>(detailedOrderList, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new ResponseEntity<Object>("fail", HttpStatus.FORBIDDEN);
		}

		return result;
	}
	

}
