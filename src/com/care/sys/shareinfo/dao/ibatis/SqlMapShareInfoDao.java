package com.care.sys.shareinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.shareinfo.dao.ShareInfoDao;
import com.care.sys.shareinfo.domain.ShareInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapShareInfoDao extends SqlMapClientDaoSupport implements ShareInfoDao{

	Log logger = LogFactory.getLog(SqlMapShareInfoDao.class);
	
	public List<DataMap> getShareInfo(ShareInfo vo)
			throws DataAccessException {
		logger.debug("getShareInfo(ShareInfo vo)");
		return getSqlMapClientTemplate().queryForList("getShareInfo", vo);
	}
	public int insertShareInfo(ShareInfo vo) throws DataAccessException {
		logger.debug("insertShareInfo(ShareInfo vo)");
		return getSqlMapClientTemplate().update("insertShareInfo", vo);
	}
	
	public int updateShareInfo(ShareInfo vo) throws DataAccessException {
		logger.debug("updateShareInfo(ShareInfo vo)");
		return getSqlMapClientTemplate().update("updateShareInfo", vo);
	}
	public int getShareInfoMaxCount(ShareInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getShareInfoMaxCount(ShareInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getShareInfoMaxCount", vo);
	}
	public int deleteShareInfo(ShareInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("deleteShareInfo(ShareInfo vo)");
		return getSqlMapClientTemplate().update("deleteShareInfo", vo);
	}
	public List<DataMap> getShareInfoListByVo(ShareInfo vo)
			throws DataAccessException {
		logger.debug("getShareInfoListByVo(ShareInfo vo)");
		return getSqlMapClientTemplate().queryForList("getShareInfoListByVo", vo);
	}
	public int getShareInfoCountByVo(ShareInfo vo) throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getShareInfoCountByVo(ShareInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getShareInfoCountByVo", vo);
	}

}
