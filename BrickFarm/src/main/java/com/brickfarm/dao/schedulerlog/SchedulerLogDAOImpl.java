package com.brickfarm.dao.schedulerlog;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class SchedulerLogDAOImpl implements SchedulerLogDAO {
	
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.SchedulerLogMapper";
	
	
	@Override
	public boolean insertSchedulerLog(String description, int count) throws Exception {
		boolean result = false;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("description", description);
		params.put("count", count);
		
		if(ses.insert(ns + ".insertSchedulerLog", params) == 1) {
			result = true;
		}
		
		return result;
	}

}
