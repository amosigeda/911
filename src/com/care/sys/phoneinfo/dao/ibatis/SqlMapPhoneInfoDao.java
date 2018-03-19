package com.care.sys.phoneinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.phoneinfo.dao.PhoneInfoDao;
import com.care.sys.phoneinfo.domain.PhoneInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapPhoneInfoDao extends SqlMapClientDaoSupport implements PhoneInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapPhoneInfoDao.class);
	public SqlMapPhoneInfoDao(){
		
	}

	public List<DataMap> getPhoneInfo(PhoneInfo vo) throws DataAccessException {
		logger.debug("getPhoneInfo(PhoneInfo vo)");
		return getSqlMapClientTemplate().queryForList("getPhoneInfo", vo);
	}

	public int insertPhoneInfo(PhoneInfo vo) throws DataAccessException {
		logger.debug("insertPhoneInfo(PhoneInfo vo)");
		return getSqlMapClientTemplate().update("insertPhoneInfo", vo);
	}

	public int updatePhoneInfo(PhoneInfo vo) throws DataAccessException {
		logger.debug("updatePhoneInfo(PhoneInfo vo)");
		return getSqlMapClientTemplate().update("updatePhoneInfo", vo);
	}

	public int getPhoneInfoCount(PhoneInfo vo) throws DataAccessException {
		logger.debug("getPhoneInfoCount(PhoneInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getPhoneInfoCount", vo);
	}

	public List<DataMap> getPhoneInfoListByVo(PhoneInfo vo)
			throws DataAccessException {
		logger.debug("getPhoneInfoListByVo(PhoneInfo vo)");
		return getSqlMapClientTemplate().queryForList("getPhoneInfoListByVo", vo);
	}

	public List<DataMap> getPhoneManageListByVo(PhoneInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getPhoneManageListByVo(PhoneInfo vo)");
		return getSqlMapClientTemplate().queryForList("getPhoneManageListByVo", vo);
	}

	public int insertPhoneManage(PhoneInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertPhoneManage(PhoneInfo vo)");
		return getSqlMapClientTemplate().update("insertPhoneManage", vo);
	}

	public int getPhoneManageCount(PhoneInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getPhoneManageCount(PhoneInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getPhoneManageCount", vo);
	}

	public Integer getPhoneManageMaxId(PhoneInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getPhoneManageMaxId(PhoneInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getPhoneManageMaxId", vo);
	}

	public int getPhoneInfoCountByVo(PhoneInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("getPhoneInfoCountByVo", vo);
	}

}
