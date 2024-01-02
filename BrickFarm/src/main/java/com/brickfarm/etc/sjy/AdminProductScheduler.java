package com.brickfarm.etc.sjy;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.brickfarm.service.admin.product.ProductManagingService;

@Component
public class AdminProductScheduler {
	@Inject
	private ProductManagingService pmService;

	@Scheduled(cron = "0 0 0 * * *")
	public void producstScheduler() throws Exception {
		pmService.confirmOrder();
		pmService.confirmReceiving();
		pmService.confirmCarryingOut();
		pmService.eventEnd();
		pmService.autoOrder(); 
		pmService.updateEventScheduler();
	}

}
