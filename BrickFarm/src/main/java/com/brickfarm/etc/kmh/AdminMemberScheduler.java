package com.brickfarm.etc.kmh;

import java.text.DecimalFormat;
import java.util.Calendar;

import javax.inject.Inject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.brickfarm.service.admin.member.MemberManagingService;

@Component
public class AdminMemberScheduler {
	
	@Inject
	private MemberManagingService mMService;
	
	private Calendar today = Calendar.getInstance();
	
	
	@Scheduled(cron="0 0 0 * * *") // 매일 자정에 돌아가는 스케줄러(생일 쿠폰 지급, 휴먼회원 전환) cron="0 0 0 * * *"       "0 0/1 * 1/1 * *" : 검토용 1분마다
	public void daliyScheduler() throws Exception {
		
		String mmDD = new DecimalFormat("00").format(today.get(Calendar.MONTH) + 1) + new DecimalFormat("00").format(today.get(Calendar.DATE));
		
		mMService.giveBirthDayCoupon(mmDD);
	
		mMService.updateInactiveMember();
	
		mMService.expireCoupon();
		
	}
	
	@Scheduled(cron="0 0 0 1 * *") // 매월 1일 자정에 돌아가는 스케줄러 0 0 0 1 * *
	public void monthlyScheduler() throws Exception {
		
		mMService.updateMemberGrade();

	}

}
