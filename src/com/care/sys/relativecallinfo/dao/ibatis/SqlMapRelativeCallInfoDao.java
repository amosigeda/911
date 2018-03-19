package com.care.sys.relativecallinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.relativecallinfo.dao.RelativeCallInfoDao;
import com.care.sys.relativecallinfo.domain.RelativeCallInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapRelativeCallInfoDao extends SqlMapClientDaoSupport implements RelativeCallInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapRelativeCallInfoDao.class);
	public SqlMapRelativeCallInfoDao(){
		
	}
	public List<DataMap> getRelativeCallInfo(RelativeCallInfo vo)
			throws DataAccessException {
		logger.debug("getRelativeCallInfo(RelativeCallInfo vo)");
		return getSqlMapClientTemplate().queryForList("getRelativeCallInfo", vo);
	}
	public int insertRelativeCallInfo(RelativeCallInfo vo)
			throws DataAccessException {
		logger.debug("insertRelativeCallInfo(RelativeCallInfo vo)");
		return getSqlMapClientTemplate().update("insertRelativeCallInfo", vo);
	}
	public int updateRelativeCallInfoStatus(RelativeCallInfo vo)
			throws DataAccessException {
		logger.debug("updateRelativeCallInfoStatus(RelativeCallInfo vo)");
		return getSqlMapClientTemplate().update("updateRelativeCallInfoStatus", vo);
	}
	public int updateRelativeCallInfo(RelativeCallInfo vo)
			throws DataAccessException {
		logger.debug("updateRelativeCallInfo(RelativeCallInfo vo)");
		return getSqlMapClientTemplate().update("updateRelativeCallInfo", vo);
	}
	public int deleteRelativeCallInfo(RelativeCallInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("deleteRelativeCallInfo(RelativeCallInfo vo)");
		return getSqlMapClientTemplate().update("deleteRelativeCallInfo", vo);
	}
	public int getMaxCountRelativeCallInfo(RelativeCallInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getMaxCountRelativeCallInfo(RelativeCallInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getMaxCountRelativeCallInfo", vo);
	}
	public int getRelativeCallInfoCount(RelativeCallInfo vo)
			throws DataAccessException {
		logger.debug("getRelativeCallInfoCount(RelativeCallInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getRelativeCallInfoCount", vo);
	}
	public List<DataMap> getRelativeCallInfoListByVo(RelativeCallInfo vo)
			throws DataAccessException {
		logger.debug("getRelativeCallInfoListByVo(RelativeCallInfo vo)");
		return getSqlMapClientTemplate().queryForList("getRelativeCallInfoListByVo", vo);
	}
	public int getRelativeCallInfoCountByVo(RelativeCallInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getRelativeCallInfoCountByVo(RelativeCallInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getRelativeCallInfoCountByVo", vo);
	}

}
