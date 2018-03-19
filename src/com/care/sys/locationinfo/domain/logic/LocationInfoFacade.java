package com.care.sys.locationinfo.domain.logic;

import java.util.List;
import com.care.sys.locationinfo.domain.LocationInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface LocationInfoFacade {
	
	public List<DataMap> getLocationInfo(LocationInfo vo) throws SystemException;
	
	public int insertLocationInfo(LocationInfo vo) throws SystemException;
	
	public int insertLocationInfoBackup(LocationInfo vo) throws SystemException;
	
	public int deleteLocationInfo(LocationInfo vo) throws SystemException;
	
	public List<DataMap> getLocationListInfo(LocationInfo vo) throws SystemException;
	
	public DataList getLocationInfoListByVo(LocationInfo vo) throws SystemException;

	public int updateLocationInfo(LocationInfo vo) throws SystemException;
	
	public List<DataMap> getLocationInfoGroupByTime(LocationInfo vo) throws SystemException;

	public int insertGpsLocationInfo(LocationInfo vo)throws SystemException;

	public DataList getNineOneOneLocationInfoListByVo(LocationInfo vo)throws SystemException;

	public List<DataMap> getNineOneOneLocationInfoList(LocationInfo vo)throws SystemException;

	public int insertNineOneOneLocationInfo(LocationInfo vo)throws SystemException;

	public int insertNineOneOneWarnInfo(LocationInfo vo)throws SystemException;

	public int insertNineOneOneBindInfo(LocationInfo vo)throws SystemException;

	public int insertNineOneOneQiyaInfo(LocationInfo vo)throws SystemException;

	public List<DataMap> insertNineOneOneGetSetInfo(LocationInfo vo)throws SystemException;

	public int updateSetInfoStatus(LocationInfo vo)throws SystemException;


}
