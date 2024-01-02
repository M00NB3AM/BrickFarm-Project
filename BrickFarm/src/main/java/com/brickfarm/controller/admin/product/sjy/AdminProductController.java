package com.brickfarm.controller.admin.product.sjy;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.brickfarm.service.admin.product.ProductManagingService;
import com.brickfarm.vo.admin.sjy.AdminAddEventDataVO;
import com.brickfarm.vo.admin.sjy.AdminAddProductDataVO;
import com.brickfarm.vo.admin.sjy.AdminEventVO;
import com.brickfarm.vo.admin.sjy.AdminProductCategoryVO;
import com.brickfarm.vo.admin.sjy.AdminProductVO;

@Controller
@RequestMapping("/admin/product/*")
public class AdminProductController {

	@Inject
	private ProductManagingService pmService;

	@RequestMapping("dashboard")
	public void getDashboard(Model model) throws Exception {

		Map<String, Object> map = pmService.getDashBoardData();
		
		model.addAttribute("productCount", (int) map.get("productCount"));
		model.addAttribute("saledCount", (int) map.get("saledCount"));
		model.addAttribute("soldOutCount", (int) map.get("soldOutCount"));
		model.addAttribute("carriedOutCount", (int) map.get("carriedOutCount"));
		model.addAttribute("rankingList", (List<AdminProductRank>) map.get("rankingList"));
	}

	@RequestMapping("checkStock")
	public ResponseEntity<Map<String, Object>> checkStock() {

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> productMap = pmService.checkStock();

			result = new ResponseEntity<Map<String, Object>>(productMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("event")
	public void getEventAll(Model model, @RequestParam(value = "pageNo", defaultValue = "1") int curPageNo)
			throws Exception {
		Map<String, Object> eventMap = pmService.getAllEvent(curPageNo);

		model.addAttribute("eventList", (List<AdminEventVO>) eventMap.get("eventList"));
		model.addAttribute("pagingInfo", (PagingInfo) eventMap.get("pagingInfo"));
	}

	@RequestMapping(value = "event", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getEventAll() {

		ResponseEntity<Map<String, Object>> result = null;
		try {
			Map<String, Object> eventMap = pmService.getAllEvent();

			result = new ResponseEntity<Map<String, Object>>(eventMap, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "getSearchedEvent", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getSearchedEvent(@RequestBody EventSearchCondition searchCondition,
			@RequestParam(value = "pageNo", defaultValue = "1") int curPageNo) {

		if ((searchCondition.getDiscount_rate().get("min_rate")) != null) {
			searchCondition
					.setMin_rate(Float.parseFloat(String.valueOf(searchCondition.getDiscount_rate().get("min_rate"))));
		}

		if ((searchCondition.getDiscount_rate().get("max_rate")) != null) {
			searchCondition
					.setMax_rate(Float.parseFloat(String.valueOf(searchCondition.getDiscount_rate().get("max_rate"))));
		}


		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> eventMap = pmService.getSearchedEvent(searchCondition, curPageNo);
			eventMap.put("searchCondition", searchCondition);

			result = new ResponseEntity<Map<String, Object>>(eventMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("event/{event_no}")
	public ResponseEntity<Map<String, Object>> getEventAll(@PathVariable("event_no") int event_no) throws Exception {

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> eventMap = pmService.getEventDetail(event_no);
			Map<String, Object> productMap = pmService.getProductsByEvtNo(event_no);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("event", eventMap.get("event"));
			map.put("productList", productMap.get("productList"));

			result = new ResponseEntity<Map<String, Object>>(map, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("eventLog/{event_no}")
	public ResponseEntity<Map<String, Object>> getEventLogAll(@PathVariable("event_no") int event_no) throws Exception {

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> eventMap = pmService.getEventDetail(event_no);
			Map<String, Object> productMap = pmService.getProductsByEvtNoFromLog(event_no);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("event", eventMap.get("event"));
			map.put("productList", productMap.get("productList"));

			result = new ResponseEntity<Map<String, Object>>(map, HttpStatus.ACCEPTED);

		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "cancleEventProducts", method = RequestMethod.POST)
	public ResponseEntity<String> cancleEventProducts(@RequestBody HashMap<String, Object> data) throws Exception {
		ResponseEntity<String> result = null;

		List<String> productList = (List<String>) data.get("cancleProducts");
		String event_no = (String) data.get("event_no");

		try {
			if (pmService.cancleEventProducts(productList, event_no)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;

	};

	@RequestMapping(value = "modifyEvent", method = RequestMethod.POST)
	public ResponseEntity<String> modifyEvent(@RequestBody AdminEventVO event) {
		ResponseEntity<String> result = null;

		try {
			if (pmService.modifyEvent(event)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "addEvent", method = RequestMethod.POST)
	public ResponseEntity<String> addEvent(@RequestBody AdminAddEventDataVO data) {
		ResponseEntity<String> result = null;

		AdminEventVO event = data.getEvent();
		List<String> eventProductList = data.getEventProductList();

		try {
			if (pmService.addEvent(event, eventProductList)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "deleteEvent", method = RequestMethod.POST)
	public ResponseEntity<String> deleteEvent(@RequestBody int event_no) {
		ResponseEntity<String> result = null;

		try {
			if (pmService.deleteEvent(event_no)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("listAll")
	public void listAll(Model model) throws Exception {
		Map<String, Object> productMap = pmService.getAllProducts();
		Map<String, Object> categoryMap = pmService.getAllCategory();
		Map<String, Object> eventMap = pmService.getAllEvent();

		model.addAttribute("productList", (List<AdminProductVO>) productMap.get("productList"));
		model.addAttribute("category", (List<AdminProductCategoryVO>) categoryMap.get("category"));
		model.addAttribute("eventList", (List<AdminEventVO>) eventMap.get("eventList"));

	}

	@RequestMapping(value = "getProducts", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getProducts(
			@RequestParam(value = "pageNo", defaultValue = "1") int curPageNo) {
		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> productMap = pmService.getAllProducts(curPageNo);
			Map<String, Object> categoryMap = pmService.getAllCategory();
			Map<String, Object> eventMap = pmService.getAllEvent();

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("productList", productMap.get("productList"));
			map.put("category", categoryMap.get("category"));
			map.put("eventList", eventMap.get("eventList"));
			map.put("pagingInfo", productMap.get("pagingInfo"));

			result = new ResponseEntity<Map<String, Object>>(map, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return result;

	}

	@RequestMapping(value = "listAll", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getProductsByCondition(
			@RequestBody ProductSearchCondition searchCondition) throws Exception {
		searchCondition.setAll(searchCondition.getSales_status().get("all").toString());
		searchCondition.setIs_new(searchCondition.getSales_status().get("is_new").toString());
		searchCondition.setNot_display(searchCondition.getSales_status().get("not_display").toString());
		searchCondition.setDisplay(searchCondition.getSales_status().get("display").toString());
		searchCondition.setEvent_no(Integer.parseInt((searchCondition.getSales_status().get("event_no")).toString()));
		if (!(searchCondition.getStock_quantity().get("maximum_stock")).equals("")) {
			searchCondition.setMaximum_stock(
					Integer.parseInt(String.valueOf(searchCondition.getStock_quantity().get("maximum_stock"))));
		}
		if (!(searchCondition.getStock_quantity().get("minimum_stock")).equals("")) {
			searchCondition.setMinimum_stock(
					Integer.parseInt(String.valueOf(searchCondition.getStock_quantity().get("minimum_stock"))));
		}
		if (!(searchCondition.getProduct_price().get("maximum_price")).equals("")) {
			searchCondition.setMaximum_price(
					Integer.parseInt(String.valueOf(searchCondition.getProduct_price().get("maximum_price"))));
		}

		if (!(searchCondition.getProduct_price().get("minimum_price")).equals("")) {
			searchCondition.setMinimum_price(
					Integer.parseInt(String.valueOf(searchCondition.getProduct_price().get("minimum_price"))));
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		String maxDate = formatter
				.format(formatter.parse(String.valueOf(searchCondition.getProduct_regist_date().get("max_date"))));
		String minDate = formatter
				.format(formatter.parse(String.valueOf(searchCondition.getProduct_regist_date().get("min_date"))));
		searchCondition.setMax_date(maxDate);
		searchCondition.setMin_date(minDate);

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> productMap = pmService.getAllProducts(searchCondition);
			productMap.put("searchCondition", searchCondition);

			result = new ResponseEntity<Map<String, Object>>(productMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "getProductsByConditionPaging", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getProductsByConditionPaging(
			@RequestBody ProductSearchCondition searchCondition,
			@RequestParam(value = "pageNo", defaultValue = "1") int curPageNo) throws Exception {
		searchCondition.setAll(searchCondition.getSales_status().get("all").toString());
		searchCondition.setIs_new(searchCondition.getSales_status().get("is_new").toString());
		searchCondition.setNot_display(searchCondition.getSales_status().get("not_display").toString());
		searchCondition.setDisplay(searchCondition.getSales_status().get("display").toString());
		searchCondition.setEvent_no(Integer.parseInt((searchCondition.getSales_status().get("event_no")).toString()));
		if (!(searchCondition.getStock_quantity().get("maximum_stock")).equals("")) {
			searchCondition.setMaximum_stock(
					Integer.parseInt(String.valueOf(searchCondition.getStock_quantity().get("maximum_stock"))));
		}
		if (!(searchCondition.getStock_quantity().get("minimum_stock")).equals("")) {
			searchCondition.setMinimum_stock(
					Integer.parseInt(String.valueOf(searchCondition.getStock_quantity().get("minimum_stock"))));
		}
		if (!(searchCondition.getProduct_price().get("maximum_price")).equals("")) {
			searchCondition.setMaximum_price(
					Integer.parseInt(String.valueOf(searchCondition.getProduct_price().get("maximum_price"))));
		}

		if (!(searchCondition.getProduct_price().get("minimum_price")).equals("")) {
			searchCondition.setMinimum_price(
					Integer.parseInt(String.valueOf(searchCondition.getProduct_price().get("minimum_price"))));
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		String maxDate = formatter
				.format(formatter.parse(String.valueOf(searchCondition.getProduct_regist_date().get("max_date"))));
		String minDate = formatter
				.format(formatter.parse(String.valueOf(searchCondition.getProduct_regist_date().get("min_date"))));
		searchCondition.setMax_date(maxDate);
		searchCondition.setMin_date(minDate);

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> productMap = pmService.getAllProducts(searchCondition, curPageNo);
			productMap.put("searchCondition", searchCondition);

			result = new ResponseEntity<Map<String, Object>>(productMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("category")
	public ResponseEntity<Map<String, Object>> getCategory() {

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> categoryMap = pmService.getAllCategory();

			result = new ResponseEntity<Map<String, Object>>(categoryMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("listAll/{product_code}")
	public ResponseEntity<Map<String, Object>> getProductDetail(@PathVariable("product_code") int product_code) {

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> productMap = pmService.getProductDetail(product_code);
			Map<String, Object> productImgMap = pmService.getAllImgByCode(product_code);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("product", productMap.get("product"));
			map.put("imgList", productImgMap.get("imgList"));

			result = new ResponseEntity<Map<String, Object>>(map, HttpStatus.ACCEPTED);
			
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("addProduct")
	public void addProductGet(Model model) throws Exception {
		Map<String, Object> eventMap = pmService.getAllEvent();

		model.addAttribute("eventList", (List<AdminEventVO>) eventMap.get("eventList"));
	}

	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public ResponseEntity<String> addProductPost(@RequestBody AdminAddProductDataVO data) {
		ResponseEntity<String> result = null;

		AdminProductVO product = data.getProduct();
		List<String> images = data.getImages();

		try {
			if (pmService.addProduct(product, images)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "modifyProduct", method = RequestMethod.POST)
	public ResponseEntity<String> modifyProduct(@RequestBody AdminAddProductDataVO data) {
		ResponseEntity<String> result = null;

		AdminProductVO product = data.getProduct();
		List<String> images = data.getImages();

		try {
			if (pmService.modifyProduct(product, images)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("deleteProduct/{product_code}")
	public ResponseEntity<String> deleteProduct(@PathVariable("product_code") String product_code) {
		ResponseEntity<String> result = null;

		try {
			if (pmService.deleteProduct(product_code)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("placeOrder")
	public void placeOrder(Model model, @RequestParam(value = "pageNo", defaultValue = "1") int curPageNo) throws Exception {
		Map<String, Object> orderMap = pmService.getAllPlaceOrder(curPageNo);

		model.addAttribute("placeOrderList", (List<AdminPlaceOrderDTO>) orderMap.get("placeOrderList"));
		model.addAttribute("pagingInfo", (PagingInfo) orderMap.get("pagingInfo"));
	}

	@RequestMapping(value = "addPlaceOrder", method = RequestMethod.POST)
	public ResponseEntity<String> addPlaceOrder(@RequestBody List<AdminPlaceOrderDTO> products) {
		ResponseEntity<String> result = null;

		try {
			if (pmService.addPlaceOrder(products)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "modifyPlaceOrder", method = RequestMethod.POST)
	public ResponseEntity<String> modifyPlaceOrder(@RequestBody List<AdminPlaceOrderDTO> modifyOrderList) {
		ResponseEntity<String> result = null;

		try {
			if (pmService.modifyPlaceOrder(modifyOrderList)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "placeOrder", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getPlaceOrderByCondition(
			@RequestBody PlaceOrderCondition searchCondition, @RequestParam(value = "pageNo", defaultValue = "1") int curPageNo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		searchCondition.setAll(searchCondition.getIs_placed().get("all").toString());
		searchCondition.setWait(searchCondition.getIs_placed().get("wait").toString());
		searchCondition.setComplete(searchCondition.getIs_placed().get("complete").toString());
		if (!(searchCondition.getPlaced_date().get("max_date")).equals("")) {
			String maxDate = formatter
					.format(formatter.parse(String.valueOf(searchCondition.getPlaced_date().get("max_date"))));
			searchCondition.setMax_date(maxDate);
		} else {
			searchCondition.setMax_date("");
		}
		if (!(searchCondition.getPlaced_date().get("min_date")).equals("")) {
			String minDate = formatter
					.format(formatter.parse(String.valueOf(searchCondition.getPlaced_date().get("min_date"))));
			searchCondition.setMin_date(minDate);
		} else {
			searchCondition.setMin_date("");
		}

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> orderMap = pmService.getAllPlaceOrder(searchCondition, curPageNo);
			orderMap.put("searchCondition", searchCondition);
			result = new ResponseEntity<Map<String, Object>>(orderMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("placeOrder/{place_order_no}")
	public ResponseEntity<Map<String, Object>> getPlaceOrderByNo(@PathVariable("place_order_no") int place_order_no) {

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> orderMap = pmService.getPlaceOrderByNo(place_order_no);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("placeOrderList", orderMap.get("placeOrderList"));

			result = new ResponseEntity<Map<String, Object>>(map, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "deleteSelectedOrder", method = RequestMethod.POST)
	public ResponseEntity<String> deleteSelectedOrder(@RequestBody List<String> orderList) throws Exception {
		ResponseEntity<String> result = null;

		try {
			if (pmService.deleteSelectedOrder(orderList)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;

	};

	@RequestMapping(value = "deleteOrder", method = RequestMethod.POST)
	public ResponseEntity<String> deleteOrder(@RequestBody Map<String, Object> order) throws Exception {
		int place_order_no = Integer.parseInt(order.get("place_order_no").toString());
		String product_code = order.get("product_code").toString();

		ResponseEntity<String> result = null;

		try {
			if (pmService.deleteOrder(place_order_no, product_code)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;

	};

	@RequestMapping("receiving")
	public void receiving(Model model, @RequestParam(value = "pageNo", defaultValue = "1") int curPageNo)
			throws Exception {
		Map<String, Object> receivingMap = pmService.getAllReceiving(curPageNo);

		model.addAttribute("receivingList", (List<AdminReceivingDTO>) receivingMap.get("receivingList"));
		model.addAttribute("pagingInfo", (PagingInfo) receivingMap.get("pagingInfo"));
	}

	@RequestMapping(value = "completeReceiving", method = RequestMethod.POST)
	public ResponseEntity<String> completeReceiving(@RequestBody AdminReceivingDTO receiving) throws Exception {
		ResponseEntity<String> result = null;

		try {
			if (pmService.completeReceiving(receiving)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "receiving", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getReceivingByCondition(
			@RequestBody ReceivingSearchCondition searchCondition,
			@RequestParam(value = "pageNo", defaultValue = "1") int curPageNo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		searchCondition.setAll(searchCondition.getIs_received().get("all").toString());
		searchCondition.setWait(searchCondition.getIs_received().get("wait").toString());
		searchCondition.setComplete(searchCondition.getIs_received().get("complete").toString());
		if (!(searchCondition.getReceiving_date().get("max_date")).equals("")) {
			String maxDate = formatter
					.format(formatter.parse(String.valueOf(searchCondition.getReceiving_date().get("max_date"))));
			searchCondition.setMax_date(maxDate);
		} else {
			searchCondition.setMax_date("");
		}
		if (!(searchCondition.getReceiving_date().get("min_date")).equals("")) {
			String minDate = formatter
					.format(formatter.parse(String.valueOf(searchCondition.getReceiving_date().get("min_date"))));
			searchCondition.setMin_date(minDate);
		} else {
			searchCondition.setMin_date("");
		}

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> receivingMap = pmService.getAllReceiving(searchCondition, curPageNo);
			receivingMap.put("searchCondition", searchCondition);

			result = new ResponseEntity<Map<String, Object>>(receivingMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("receiving/{receiving_no}")
	public ResponseEntity<Map<String, Object>> getReceivingByNo(@PathVariable("receiving_no") int receiving_no) {

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> receivingMap = pmService.getReceivingByNo(receiving_no);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("receivingList", receivingMap.get("receivingList"));

			result = new ResponseEntity<Map<String, Object>>(map, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "modifyReceiving", method = RequestMethod.POST)
	public ResponseEntity<String> modifyReceiving(@RequestBody List<AdminReceivingDTO> modifyReceivingList) {
		ResponseEntity<String> result = null;

		try {
			if (pmService.modifyReceiving(modifyReceivingList)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("carryingOut")
	public void carryingOut(Model model, @RequestParam(value = "pageNo", defaultValue = "1") int curPageNo)
			throws Exception {
		Map<String, Object> carryingOutMap = pmService.getAllCarryingOut(curPageNo);

		model.addAttribute("carryingOutList", (List<AdminCarryingOutDTO>) carryingOutMap.get("carryingOutList"));
		model.addAttribute("pagingInfo", (PagingInfo) carryingOutMap.get("pagingInfo"));
	}

	@RequestMapping(value = "carryingOut", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> getCarryingOutByCondition(
			@RequestBody CarryingOutSearchCondition searchCondition,
			@RequestParam(value = "pageNo", defaultValue = "1") int curPageNo) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		searchCondition.setAll(searchCondition.getIs_carried_out().get("all").toString());
		searchCondition.setWait(searchCondition.getIs_carried_out().get("wait").toString());
		searchCondition.setComplete(searchCondition.getIs_carried_out().get("complete").toString());
		if (!(searchCondition.getCarrying_out_date().get("max_date")).equals("")) {
			String maxDate = formatter
					.format(formatter.parse(String.valueOf(searchCondition.getCarrying_out_date().get("max_date"))));
			searchCondition.setMax_date(maxDate);
		} else {
			searchCondition.setMax_date("");
		}
		if (!(searchCondition.getCarrying_out_date().get("min_date")).equals("")) {
			String minDate = formatter
					.format(formatter.parse(String.valueOf(searchCondition.getCarrying_out_date().get("min_date"))));
			searchCondition.setMin_date(minDate);
		} else {
			searchCondition.setMin_date("");
		}

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> carryingOutMap = pmService.getAllCarryingOut(searchCondition, curPageNo);
			carryingOutMap.put("searchCondition", searchCondition);
			result = new ResponseEntity<Map<String, Object>>(carryingOutMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "completeCarryingOut", method = RequestMethod.POST)
	public ResponseEntity<String> completeCarryingOut(@RequestBody AdminCarryingOutDTO carryingOut) throws Exception {
		ResponseEntity<String> result = null;

		try {
			if (pmService.completeCarryingOut(carryingOut)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping("carryingOut/{carrying_out_no}")
	public ResponseEntity<Map<String, Object>> getCarryingOutByNo(
			@PathVariable("carrying_out_no") int carrying_out_no) {

		ResponseEntity<Map<String, Object>> result = null;

		try {
			Map<String, Object> carryingOutMap = pmService.getCarryingOutByNo(carrying_out_no);

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("carryingOutList", carryingOutMap.get("carryingOutList"));

			result = new ResponseEntity<Map<String, Object>>(map, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

	@RequestMapping(value = "modifyCarryingOut", method = RequestMethod.POST)
	public ResponseEntity<String> modifyCarryingOut(@RequestBody List<AdminCarryingOutDTO> modifyCarryingOutList) {
		ResponseEntity<String> result = null;

		try {
			if (pmService.modifyCarryingOut(modifyCarryingOutList)) {
				result = new ResponseEntity<String>(HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		return result;
	}

}
