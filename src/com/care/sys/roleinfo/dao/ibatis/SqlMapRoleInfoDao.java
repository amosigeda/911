package com.care.sys.roleinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.roleinfo.dao.RoleInfoDao;
import com.care.sys.roleinfo.domain.RoleInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.log.LogFactory;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class SqlMapRoleInfoDao extends SqlMapClientDaoSupport implements RoleInfoDao{

Log logger = LogFactory.getLog(SqlMapRoleInfoDao.class);
	public SqlMapRoleInfoDao (){
	}

	public String getRoleInfoPK()throws DataAccessException{
		logger.debug("getRoleInfoPK()");
		return (String)getSqlMapClientTemplate().queryForObject("getRoleInfo_PK", null);
	}

	public Integer getRoleInfoCount(RoleInfo vo)throws DataAccessException{
		logger.debug("getRoleInfoCount(RoleInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getRoleInfoCount", vo);
	}

	public Integer getRoleInfoCount(DataParamMap dmap)throws DataAccessException{
		logger.debug("getRoleInfoCount(DataParamMap dmap)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getRoleInfoCountMap", dmap);
	}

	public List<DataMap> getRoleInfo(RoleInfo vo)throws DataAccessException{
		logger.debug("getRoleInfo(RoleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getRoleInfo", vo);
	}

	public List<DataMap> getRoleInfoListByVo(RoleInfo vo)throws DataAccessException{
		logger.debug("getRoleInfoListByVo(RoleInfo vo)");
		return getSqlMapClientTemplate().queryForList("getRoleInfoListByVo", vo);
	}

	public List<DataMap> getRoleInfoListByMap(DataParamMap dmap)throws DataAccessException{
		logger.debug("getRoleInfoListByMap(DataParamMap dmap)");
		return getSqlMapClientTemplate().queryForList("getRoleInfoListByMap", dmap);
	}

	public int updateRoleInfo(RoleInfo vo)throws DataAccessException{
		logger.debug("updateRoleInfo(RoleInfo vo)");
		return getSqlMapClientTemplate().update("updateRoleInfo", vo);
	}

	public int insertRoleInfo(RoleInfo vo)throws DataAccessException{
		logger.debug("insertRoleInfo(RoleInfo vo)");
		return getSqlMapClientTemplate().update("insertRoleInfo", vo);
	}

	public int deleteRoleInfo(RoleInfo vo)throws DataAccessException{
		logger.debug("deleteRoleInfo(RoleInfo vo)");
		return getSqlMapClientTemplate().update("deleteRoleInfo", vo);
	}
}
