package com.brickfarm.dao.memberAddressBook;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.brickfarm.dto.user.syt.UserAddressBookDTO;
import com.brickfarm.vo.user.psj.UserAddressBookVO;
import com.brickfarm.vo.user.syt.UserAddressVO;

@Repository
public class MemberAddressBookDAOImpl implements MemberAddressBookDAO {
	
	@Inject
	private SqlSession ses;
	
	private static String ns = "com.brickfarm.mappers.MemberAddressBookMapper";
	
	// ==[송지영]==========================================================================================================================================
	// ==================================================================================================================================================

	// ==[김미형]==========================================================================================================================================
	// ==================================================================================================================================================
	// ==[박상진]==========================================================================================================================================
	@Override
	public UserAddressBookVO selectDefaultAddressBook(int member_no) throws Exception {
		
		return ses.selectOne(ns +".selectDefaultAddressBook", member_no);
	}
	@Override
	public List<UserAddressBookVO> selectAllAdressBook(int member_no) throws Exception {
		return ses.selectList(ns +".selectAllAddressBook", member_no);
	}
	@Override
	public int updateDefaultAddress(int member_address_book_no) throws Exception {
		return ses.update(ns + ".updateDefaultAddress", member_address_book_no);
	}
	@Override
	public int insertAddressBook(UserAddressBookVO userAdressBookVO) throws Exception {
		
		return ses.insert(ns +".insertAddressBook", userAdressBookVO);
	}

	@Override
	public int selectDefaultAddress(int member_no) throws Exception {
		Object result = ses.selectOne(ns + ".selectDefaultAddress", member_no);
		if(result == null) {
			return 0;
		}
		return ses.selectOne(ns + ".selectDefaultAddress", member_no);
	}




	@Override
	public UserAddressBookVO selectAddressInfo(int member_address_book_no) {
		return ses.selectOne(ns + ".selectAddressInfo", member_address_book_no);
	}


	@Override
	public int updateAddressBook(UserAddressBookVO addressBookVO) throws Exception {
		return ses.update(ns +".updateAddressBook", addressBookVO);
	}


	@Override
	public List<String> selectAddressBookNameList(int member_no) {
		return ses.selectList(ns + ".selectAddressBookNameList", member_no);
	}


	@Override
	public int deleteAddressBook(int member_address_book_no) {
		return ses.delete(ns + ".deleteAddressBook", member_address_book_no);
	}
	

	// ==================================================================================================================================================

	// ==[송영태]==========================================================================================================================================
	/**
	 * @methodName : selectAddressBook
	 * @author : syt
	 * @param member_no
	 * @return
	 * @returnValue : @param member_no
	 * @returnValue : @return
	 * @date : 2023. 11. 14. 오후 2:59:39
	 * @description : 배송지 테이블 정보 가져옴
	 */
	@Override
	public List<UserAddressVO> selectAddressBook(int member_no) throws Exception {
		return ses.selectList(ns + ".selectAddressBook", member_no);
	}
	
	/**
	 * @methodName : insertAddressBookByOrderPage
	 * @author : syt
	 * @param addressBook
	 * @return
	 * @returnValue : @param addressBook
	 * @returnValue : @return
	 * @date : 2023. 11. 17. 오후 4:07:16
	 * @description : 배송지 테이블 추가
	 */
	@Override
	public int insertAddressBookByOrderPage(UserAddressBookDTO addressBook) throws Exception {
		return ses.insert(ns + ".insertAddressBookByOrderPage", addressBook);
	}
	// ==================================================================================================================================================

	
	// ==[김용진]==========================================================================================================================================
	// ==================================================================================================================================================
}
