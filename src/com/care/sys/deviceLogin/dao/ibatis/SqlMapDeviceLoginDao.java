package com.care.sys.deviceLogin.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.deviceLogin.dao.DeviceLoginDao;
import com.care.sys.deviceLogin.domain.DeviceLogin;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapDeviceLoginDao extends SqlMapClientDaoSupport implements DeviceLoginDao{

	Log logger = LogFactory.getLog(SqlMapDeviceLoginDao.class);
	
	public Integer getDeviceLoginCount(DeviceLogin vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("getDeviceLoginCount", vo);
	}

	public List<DataMap> getDeviceLogin(DeviceLogin vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("getDeviceLogin", vo);
	}

	public List<DataMap> getDataDeviceLoginListByVo(DeviceLogin vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("getDataDeviceLoginListByVo", vo);
	}

	public int updateDeviceLogin(DeviceLogin vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("updateDeviceLogin", vo);
	}

	public int insertDeviceLogin(DeviceLogin vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("insertDeviceLogin", vo);
	}

	public int deleteDeviceLogin(DeviceLogin vo) throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().update("deleteDeviceLogin", vo);
	}

	public List<DataMap> getDeviceLoginGroupByProject(DeviceLogin vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList("getDeviceLoginGroupByProject", vo);
	}

	public int getDeviceLoginCountGroupByProject(DeviceLogin vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("getDeviceLoginCountGroupByProject", vo);
	}

}
