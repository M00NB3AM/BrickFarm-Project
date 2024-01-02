package com.brickfarm.etc.syt;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.brickfarm.service.admin.order.AdminOrderService;

@Component
public class Scheduler {

	@Inject
	public AdminOrderService orderService;
	
	@Scheduled(cron= "0 30 0 * * *")
	private void autoState() {
		try {
			// 배송중으로 상태변경
			orderService.schedulerDelivery();
			// 배송완료로 상태 변경
			orderService.schedulerDeliveryCompleted();
			// 현금주문후 입금기한만료인 사람들 취소 처리
			orderService.schedulerDepositDeadlineExpired();
			// 구매확정으로 변경
			orderService.schedulerStateManageOrderComplete();	

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
