package com.care.sys.directiveinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.directiveinfo.dao.DirectiveInfoDao;
import com.care.sys.directiveinfo.domain.DirectiveInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapDirectiveInfoDao extends SqlMapClientDaoSupport implements DirectiveInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapDirectiveInfoDao.class);

	public List<DataMap> getDirectiveInfo(DirectiveInfo vo)
			throws DataAccessException {
		logger.debug("getDirectiveInfo(DirectiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDirectiveInfo", vo);
	}

	public Integer insertDirectiveInfo(DirectiveInfo vo) throws DataAccessException {
		logger.debug("insertDirectiveInfo(DirectiveInfo vo)");
		return getSqlMapClientTemplate().update("insertDirectiveInfo", vo);
	}

	public Integer updateDirectiveInfo(DirectiveInfo vo) throws DataAccessException {
		logger.debug("updateDirectiveInfo(DirectiveInfo vo)");
		return getSqlMapClientTemplate().update("updateDirectiveInfo", vo);
	}

	public Integer getDirectiveInfoCount(DirectiveInfo vo)
			throws DataAccessException {
		logger.debug("getDirectiveInfoCount(DirectiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getDirectiveInfoCount", vo);
	}

	public List<DataMap> getDirectiveInfoListByVo(DirectiveInfo vo)
			throws DataAccessException {
		logger.debug("getDirectiveInfoListByVo(DirectiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDirectiveInfoListByVo", vo);
	}
}
