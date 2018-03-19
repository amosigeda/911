package com.care.sys.deviceactiveinfo.dao.ibatis;

import java.util.List;

import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.care.sys.deviceactiveinfo.dao.DeviceActiveInfoDao;
import com.care.sys.deviceactiveinfo.domain.DeviceActiveInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapDeviceActiveInfoDao extends SqlMapClientDaoSupport implements DeviceActiveInfoDao{

	Log logger = LogFactory.getLog(SqlMapDeviceActiveInfoDao.class);
	
	public List<DataMap> getDeviceActiveInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getDeviceActiveInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveInfo", vo);
	}
	
	public List<DataMap> getDisturbInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getDisturbInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDisturbInfo", vo);
	}	

	public int getDeviceActiveInfoCount(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getDeviceActiveInfoCount(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getDeviceActiveInfoCount", vo);
	}

	public int insertDeviceActiveInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertDeviceActiveInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertDeviceActiveInfo", vo);
	}

	public int updateDeviceActiveInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateDeviceActiveInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("updateDeviceActiveInfo", vo);
	}

	public int deleteDeviceActiveInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("deleteDeviceActiveInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("deleteDeviceActiveInfo", vo);
	}

	public List<DataMap> getDeviceActiveInfoListByVo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getDeviceActiveInfoListByVo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveInfoListByVo", vo);
	}
	
	public List<DataMap> setClock(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("setClock(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("setClock", vo);
	}

	public int getDeviceActiveInfoCountGroupByTime(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getDeviceActiveInfoCountGroupByTime(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getDeviceActiveInfoCountGroupByTime", vo);
	}

	public List<DataMap> getDeviceActiveInfoGroupByTime(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getDeviceActiveInfoGroupByTime(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveInfoGroupByTime", vo);
	}

	public List<DataMap> getDeviceActiveHistory(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getDeviceActiveHistory(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveHistory", vo);
	}

	public int getDeviceActiveHistoryCount(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getDeviceActiveHistoryCount(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().queryForObject("getDeviceActiveHistoryCount", vo);
	}

	public List<DataMap> getDeviceActiveHistoryListByVo(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("getDeviceActiveHistoryListByVo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveHistoryListByVo", vo);
	}

	
	public int insertDeviceActiveHistory(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("insertDeviceActiveHistory(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().update("insertDeviceActiveHistory", vo);
	}

	public int updateDeviceActiveHistory(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateDeviceActiveHistory(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().update("updateDeviceActiveHistory", vo);
	}

	//ZST@20151104
	public List<DataMap> getDeviceActiveLocationInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		logger.debug("updateDeviceActiveHistory(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceActiveLocationInfo", vo);
	}

	public int insertSportInfo(DeviceActiveInfo vo) throws DataAccessException {
		logger.debug("insertSportInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertSportInfo", vo);
	}

	public int insertCallInfo(DeviceActiveInfo vo) throws DataAccessException {
		logger.debug("insertCallInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertCallInfo", vo);
	}

	public List<DataMap> getDeviceAddFriendInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getDeviceAddFriendInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getDeviceAddFriendInfo", vo);
	}

	public int insertAddFriendInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("insertAddFriendInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertAddFriendInfo", vo);
	}
	
	public List<DataMap> getAddFriends(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getAddFriends(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getAddFriends", vo);
	}
	public List<DataMap> getCallInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getCallInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getCallInfo", vo);
	}
	public List<DataMap> getTalkGroup(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getTalkGroup(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getTalkGroup", vo);
	}
	public List<DataMap> getSportInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getSportInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSportInfo", vo);
	}
	public List<DataMap> getMediaInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getMediaInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getMediaInfo", vo);
	}

	public List<DataMap> getSportTypeInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getSportTypeInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getSportTypeInfo", vo);
	}

	public List<DataMap> getAllCallInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getAllCallInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getAllCallInfo", vo);
	}

	public List<DataMap> getCityWeatherInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getCityWeatherInfo(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getCityWeatherInfo", vo);
	}
	
	public List<DataMap> getmaxtime(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getmaxtime(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getmaxtime", vo);
	}
	
	public List<DataMap> getlal(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getlal(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getlal", vo);
	}
	
	public List<DataMap> getminitime(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("getminitime(DeviceActiveInfo vo)");
		return getSqlMapClientTemplate().queryForList("getminitime", vo);
	}

	public int insertWeatherInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("insertWeatherInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("insertWeatherInfo", vo);
	}

	public int updateWeatherInfo(DeviceActiveInfo vo)
			throws DataAccessException {
		logger.debug("updateWeatherInfo(DeviceActiveInfo vo)");
		return (Integer)getSqlMapClientTemplate().update("updateWeatherInfo", vo);
	}
}
