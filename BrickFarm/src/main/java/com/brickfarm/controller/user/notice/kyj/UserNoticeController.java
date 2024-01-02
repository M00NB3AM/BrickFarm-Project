package com.brickfarm.controller.user.notice.kyj;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.brickfarm.etc.kyj.PaginationInfo;
import com.brickfarm.etc.kyj.SearchCriteria;
import com.brickfarm.service.user.notice.UserNoticeService;
import com.brickfarm.vo.user.kyj.UserNoticeCategoryVO;
import com.brickfarm.vo.user.kyj.UserNoticeVO;

@Controller
@RequestMapping("/notice/*")
public class UserNoticeController {
//	private static final Logger logger = LoggerFactory.getLogger(UserNoticeController.class);
	
	@Inject
	private UserNoticeService nService;

	/**
	 * @methodName : list
	 * @author : kyj
	 * @return
	 * @throws SQLException
	 * @returnValue : @return
	 * @date : 2023. 10. 2. 오후 7:04:47
	 * @description : 공지사항 기본 목록 페이지와 매핑된 메서드, DB로부터 받아온 데이터로 공지사항 페이지를 출력해준다.
	 */
	@GetMapping("list")
	public String noticeList(Model model, @RequestParam(value = "pageNo", defaultValue = "1") int curPageNo,
			@RequestParam(value = "rowCountPerPage", defaultValue = "10") int rowCountPerPage,
			@RequestParam(value = "searchType", defaultValue = "") String searchType,
			@RequestParam(value = "searchWord", defaultValue = "") String searchWord,
			@RequestParam(value = "searchCategory", defaultValue = "0") int searchCategory) throws SQLException {
		// logger.info("공지사항 목록 요청");

		SearchCriteria sc = SearchCriteria.builder()
				.searchType(searchType)
				.searchWord(searchWord)
				.searchCategory(searchCategory)
				.build();
		PaginationInfo pi = nService.createPaginationInfo(curPageNo, rowCountPerPage, sc);
		// System.out.println(pi.toString());
		// System.out.println(sc.toString());
 
		List<UserNoticeVO> list = nService.getNoticeListByCondition(pi, sc);
		List<UserNoticeVO> fixedList = nService.getFixedNoticeList();
		List<UserNoticeCategoryVO> noticeCategoryList = nService.getAllNoticeCategoryList();
		
		model.addAttribute("noticeList", list);
		model.addAttribute("fixedNoticeList", fixedList);
		model.addAttribute("noticeCategoryList", noticeCategoryList);
		model.addAttribute("pagingInfo", pi);
		model.addAttribute("searchInfo", sc);
		
		return "/user/notice/noticeList";
	}
	
	@GetMapping("detail")
	public String noticeDetail(Model model, @RequestParam(value="no", defaultValue="0") int noticeBoardNo) throws SQLException {
		// logger.info("공지사항 상세보기 요청");
		
		if(noticeBoardNo == 0) {
			model.addAttribute("status", "notFound");
			return "redirect:/notice/list";
		}
		
		UserNoticeVO notice = nService.getNoticeByNoticeBoardNo(noticeBoardNo);
		if(notice == null) {
			model.addAttribute("status", "notFound");
			return "redirect:/notice/list";
		}
		
		model.addAttribute("notice", notice);
		
		return "/user/notice/noticeDetail";
	}
	
	@GetMapping("membership")
	public String membershipPage() {
		// logger.info("등급 및 혜택 페이지 요청");
		
		return "/user/notice/membership";
	}
}
