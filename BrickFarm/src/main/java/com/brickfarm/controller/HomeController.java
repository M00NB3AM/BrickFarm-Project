package com.brickfarm.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brickfarm.service.admin.board.AdminBoardService;
import com.brickfarm.service.admin.order.AdminOrderService;
import com.brickfarm.service.admin.statistics.AdminStatisticsService;
import com.brickfarm.service.user.product.ProductService;
import com.brickfarm.vo.user.lkm.UserProductVO;
import com.brickfarm.vo.admin.kyj.board.AdminInquiriesGraphDataVO;
import com.brickfarm.vo.admin.kyj.index.AdminMemberAndPointsAccrualInfoBy7DaysVO;
import com.brickfarm.vo.admin.kyj.index.AdminTotalSalesStatByPeriodVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Inject
	private ProductService productService;
	
	@Inject
	public AdminOrderService orderService;
	
	@Inject
	private AdminStatisticsService statisticsService;
	
	@Inject
	private AdminBoardService boardService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
//		logger.info("home");
		
		// 베스트 상품 select
		List<UserProductVO> bestProductList;
		try {
			bestProductList = productService.getBestProductForIndex();
			model.addAttribute("bestProductList", bestProductList);
			
			// 신제품 select
			List<UserProductVO> newProductList = productService.getNewProductForIndex();
			model.addAttribute("newProductList", newProductList);
			
			// 메인 할인 상품 select
			List<UserProductVO> discountProductList = productService.getDiscountProductForIndex();
			model.addAttribute("discountProductList", discountProductList);
			
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "user/index";
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Model model) {
//		logger.info("관리자 index 페이지 요청");
		
		try {
			Map<String, Object> result = orderService.getDashboardData();
			int dashboardCount = (int) result.get("dashboardCount");
			String dashboardSales = (String) result.get("dashboardSales");
			int dashboardDeliveryCount = (int) result.get("dashboardDeliveryCount");
			int dashboardStateCount = (int) result.get("dashboardStateCount");
			
			AdminTotalSalesStatByPeriodVO totalSalesStatBy7Days = statisticsService.getTotalSalesStatByPeriod(7);
			AdminTotalSalesStatByPeriodVO totalSalesStatBy30Days = statisticsService.getTotalSalesStatByPeriod(30);
			List<AdminMemberAndPointsAccrualInfoBy7DaysVO> memberAndPointsAccrualInfoBy7Days = statisticsService.getMemberAndPointsAccrualInfoBy7Days();
			List<AdminInquiriesGraphDataVO> inquiriesGraphData = boardService.getInquiriesGraphData();
			String inquiriesGraphDataJsonString = new ObjectMapper().writeValueAsString(inquiriesGraphData);
			
			model.addAttribute("dashboardCount", dashboardCount);
			model.addAttribute("dashboardSales", dashboardSales);
			model.addAttribute("dashboardDeliveryCount", dashboardDeliveryCount);
			model.addAttribute("dashboardStateCount", dashboardStateCount);
			model.addAttribute("totalSalesStatBy7Days", new ObjectMapper().writeValueAsString(totalSalesStatBy7Days));
			model.addAttribute("totalSalesStatBy30Days", new ObjectMapper().writeValueAsString(totalSalesStatBy30Days));
			model.addAttribute("memberAndPointsAccrualInfoBy7Days", new ObjectMapper().writeValueAsString(memberAndPointsAccrualInfoBy7Days));
			model.addAttribute("inquiriesGraphData", inquiriesGraphDataJsonString);
			model.addAttribute("status", "success");
		} catch (SQLException | JsonProcessingException e) {
			e.printStackTrace();
			model.addAttribute("status", "error");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("status", "error");
		}
		
		return "admin/index";
	}
	

}
