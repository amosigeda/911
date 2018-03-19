package com.care.sys.sysregisterinfo.dao.ibatis;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.sysregisterinfo.dao.RegisterInfoDao;
import com.care.sys.sysregisterinfo.domain.UserInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;


public class SqlMapRegisterInfoDao extends SqlMapClientDaoSupport implements RegisterInfoDao{

	Log logger = LogFactory.getLog(SqlMapRegisterInfoDao.class);
	
	public List<DataMap> getRegisterInfo(UserInfo vo) throws DataAccessException {
		logger.debug("getRegisterInfo(UserInfo vo)");
		return getSqlMapClientTemplate().queryForList("getRegisterInfo", vo);
	}
		
	public List<DataMap> getRegisterInfoListByVo(UserInfo vo)
			throws DataAccessException {
		logger.debug("getRegisterInfoListByVo(UserInfo vo)");
		return getSqlMapClientTemplate().queryForList("getRegisterInfoListByVo", vo);
	}

	public Integer getRegisterInfoCount(UserInfo vo) throws DataAccessException {
		logger.debug("getRegisterInfoCount(UserInfo vo)");
		return (Integer) getSqlMapClientTemplate().queryForObject("getRegisterInfoCount", vo);
	}

	public Integer getRegisterInfoMaxId(UserInfo vo) throws DataAccessException {
		logger.debug("getRegisterInfoMaxId(UserInfo vo)");
		return (Integer) getSqlMapClientTemplate().queryForObject("getRegisterInfoMaxId", vo);
	}

	public Integer insertRegisterInfo(UserInfo vo)
			throws DataAccessException {
		 logger.debug("insertRegisterInfo(UserInfo vo)");
		return (Integer) getSqlMapClientTemplate().update("insertRegisterInfo",vo);
	}

	public Integer updateRegisterInfo(UserInfo vo) throws DataAccessException {
		logger.debug("updateRegisterInfo(UserInfo vo)");
		return getSqlMapClientTemplate().update("updateRegisterInfo",vo);
	}

	public Integer updateGroupCode(UserInfo vo) throws DataAccessException {
		logger.debug("updateGroupCode(UserInfo vo)");
		return getSqlMapClientTemplate().update("updateGroupCode",vo);
	}

	public Integer updateTag(UserInfo vo) throws DataAccessException {
		logger.debug("updateTag(UserInfo vo)");
		return getSqlMapClientTemplate().update("updateTag", vo);
	}
	

}
