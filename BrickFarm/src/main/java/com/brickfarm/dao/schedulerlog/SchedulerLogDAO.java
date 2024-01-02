package com.brickfarm.dao.schedulerlog;

public interface SchedulerLogDAO {
	
	// 스케줄러 로그 남기는 공용메서드
	boolean insertSchedulerLog(String description, int count) throws Exception;

}
