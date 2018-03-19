package com.care.sys.devicephoneinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.devicephoneinfo.dao.DevicePhoneInfoDao;
import com.care.sys.devicephoneinfo.domain.DevicePhoneInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapDevicePhoneInfoDao extends SqlMapClientDaoSupport implements DevicePhoneInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapDevicePhoneInfoDao.class);

	public List<DataMap> getDevicePhoneInfo(DevicePhoneInfo vo)
			throws DataAccessException {
		logger.debug("getDevicePhoneInfo(DevicePhoneInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDevicePhoneInfo", vo);
	}

	public List<DataMap> getDevicePhoneInfoListByVo(DevicePhoneInfo vo) throws DataAccessException{
		logger.debug("getDevicePhoneInfoListByVo(DevicePhoneInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDevicePhoneInfoListByVo", vo);
	}
	public Integer getDevicePhoneInfoListCountByVo(DevicePhoneInfo vo)
			throws DataAccessException {
		logger.debug("getDevicePhoneInfoListCountByVo(DevicePhoneInfo vo)");
		return (Integer) getSqlMapClientTemplate().queryForObject("getDevicePhoneInfoListCountByVo", vo);
	}
	
	public int insertDevicePhoneInfo(DevicePhoneInfo vo)
			throws DataAccessException {
		logger.debug("insertDevicePhoneInfo(DevicePhoneInfo vo)");
		return (Integer) getSqlMapClientTemplate().update("insertDevicePhoneInfo", vo);
	}
	
}
