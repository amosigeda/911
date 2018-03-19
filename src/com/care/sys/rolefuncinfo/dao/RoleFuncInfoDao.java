package com.care.sys.rolefuncinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.rolefuncinfo.domain.RoleFuncInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;


/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface RoleFuncInfoDao{

	public String getRoleFuncInfoPK()throws DataAccessException;

	public Integer getRoleFuncInfoCount(RoleFuncInfo vo)throws DataAccessException;

	public Integer getRoleFuncInfoCount(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getRoleFuncInfo(RoleFuncInfo vo)throws DataAccessException;

	public List<DataMap> getRoleFuncInfoListByMap(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getRoleFuncInfoListByVo(RoleFuncInfo vo)throws DataAccessException;

	public int updateRoleFuncInfo(RoleFuncInfo vo)throws DataAccessException;

	public int insertRoleFuncInfo(RoleFuncInfo vo)throws DataAccessException;

	public int deleteRoleFuncInfo(RoleFuncInfo vo)throws DataAccessException;
}
