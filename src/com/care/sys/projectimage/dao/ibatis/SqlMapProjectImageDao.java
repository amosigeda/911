package com.care.sys.projectimage.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.projectimage.dao.ProjectImageDao;
import com.care.sys.projectimage.domain.ProjectImage;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapProjectImageDao extends SqlMapClientDaoSupport implements
		ProjectImageDao {
	
	Log logger =LogFactory.getLog(SqlMapProjectImageDao.class);
	public List<DataMap> getProjectImage(ProjectImage vo)
			throws DataAccessException {
		logger.debug("getProjectImage(ProjectImage vo)");
		return getSqlMapClientTemplate().queryForList("getProjectImage",vo);
	}
	
	public List<DataMap> getProjectImageListByVo(ProjectImage vo)
			throws DataAccessException {
		logger.debug("getProjectImageListByVo(ProjectImage vo)");
		return getSqlMapClientTemplate().queryForList("getProjectImageListByVo", vo);
	}

	public Integer getProjectImageListCountByVo(ProjectImage vo)
			throws DataAccessException {
		logger.debug("getProjectImageListCountByVo(ProjectImage vo)");
		return (Integer) getSqlMapClientTemplate().queryForObject("getProjectImageListCountByVo", vo);
	}

	public Integer updateProjectImage(ProjectImage vo)
			throws DataAccessException {
		logger.debug("updateProjectImage(ProjectImage vo)");
		return getSqlMapClientTemplate().update("updateProjectImage", vo);
	}

	public Integer insertProjectImage(ProjectImage vo)
			throws DataAccessException {
		logger.debug("insertProjectImage(ProjectImage vo)");
		return (Integer) getSqlMapClientTemplate().update("insertProjectImage", vo);
	}

	public Integer updateProjectImagePath(ProjectImage vo)
			throws DataAccessException {
		logger.debug("updateProjectImagePath(ProjectImage vo)");
		return getSqlMapClientTemplate().update("updateProjectImagePath", vo);
	}

}
