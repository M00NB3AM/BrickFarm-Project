package com.brickfarm.dao.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.etc.sjy.EventSearchCondition;
import com.brickfarm.etc.sjy.PagingInfo;
import com.brickfarm.vo.admin.sjy.AdminEventVO;

@Repository
public class EventDAOImpl implements EventDAO {

	@Inject
	private SqlSession ses;

	private static String ns = "com.brickfarm.mappers.EventMapper";

	// ==[송지영]==========================================================================================================================================
	@Override
	public List<AdminEventVO> selectAllEventPaging(PagingInfo pi) throws Exception {
		return ses.selectList(ns + ".selectAllEventPaging", pi);
	}

	@Override
	public List<AdminEventVO> selectAllEvent() throws Exception {
		return ses.selectList(ns + ".selectAllEvent");
	}

	@Override
	public List<AdminEventVO> selectEventDetail(int event_no) throws Exception {
		return ses.selectList(ns + ".selectEventDetail", event_no);
	}

	@Override
	public int updateEvent(AdminEventVO event) throws Exception {
		return ses.update(ns + ".updateEvent", event);
	}

	@Override
	public int insertEvent(AdminEventVO event) throws Exception {
		return ses.insert(ns + ".insertEvent", event);
	}

	@Override
	public List<AdminEventVO> selectEventByCondition(EventSearchCondition searchCondition, PagingInfo pi) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search_word", searchCondition.getSearch_word());
		map.put("search_type", searchCondition.getSearch_type());
		map.put("event_start", searchCondition.getEvent_start());
		map.put("event_end", searchCondition.getEvent_end());
		map.put("min_rate", searchCondition.getMin_rate());
		map.put("max_rate", searchCondition.getMax_rate());
		map.put("discount_rate", searchCondition.getDiscount_rate());
		map.put("startRowIndex", pi.getStartRowIndex());
		map.put("viewPostCntPerPage", pi.getViewPostCntPerPage());
		return ses.selectList(ns + ".selectEventByCondition", map);
	}

	@Override
	public int deleteEvent(int event_no) throws Exception {
		return ses.delete(ns + ".deleteEvent", event_no);
	}

	@Override
	public int selectTotalPostCnt() throws Exception {
		return ses.selectOne(ns + ".selectTotalPostCnt");
	}

	@Override
	public List<Integer> selectEventEndList() throws Exception {
		return ses.selectList(ns + ".selectEventEndList");
	}

	@Override
	public int selectSearchedTotalPostCnt(EventSearchCondition searchCondition) {
		return ses.selectOne(ns + ".selectSearchedTotalPostCnt", searchCondition);
	}
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[이경민]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[염세환]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[박상진]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
