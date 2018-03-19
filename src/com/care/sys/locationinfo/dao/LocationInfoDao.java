package com.care.sys.locationinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.locationinfo.domain.LocationInfo;
import com.godoing.rose.lang.DataMap;

public interface LocationInfoDao {
	
	public int insertLocationInfo(LocationInfo vo) throws DataAccessException;
	
	public int insertLocationInfoBackup(LocationInfo vo) throws DataAccessException;
	
	public int deleteLocationInfo(LocationInfo vo) throws DataAccessException;
	
	public List<DataMap> getLocationInfo(LocationInfo vo) throws DataAccessException;
	
	public List<DataMap> getLocationListInfo(LocationInfo vo) throws DataAccessException;
		
	public int getLocationInfoCount(LocationInfo vo) throws DataAccessException;

	public int updateLocationInfo(LocationInfo vo) throws DataAccessException;
	
	public List<DataMap> getLocationInfoGroupByTime(LocationInfo vo) throws DataAccessException;

	public int insertGpsLocationInfo(LocationInfo vo)throws DataAccessException;

	public List<DataMap> getNineOneOneLocationInfo(LocationInfo vo)throws DataAccessException;

	public int getNineOneOneLocationInfoCount(LocationInfo vo)throws DataAccessException;

	public List<DataMap> getNineOneOneLocationInfoList(LocationInfo vo)throws DataAccessException;

	public int insertNineOneOneLocationInfo(LocationInfo vo)throws DataAccessException;

	public int insertNineOneOneWarnInfo(LocationInfo vo)throws DataAccessException;

	public int insertNineOneOneBindInfo(LocationInfo vo)throws DataAccessException;

	public int insertNineOneOneQiyaInfo(LocationInfo vo)throws DataAccessException;

	public List<DataMap> insertNineOneOneGetSetInfo(LocationInfo vo)throws DataAccessException;

	public int updateSetInfoStatus(LocationInfo vo)throws DataAccessException;



	
}
