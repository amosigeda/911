package com.care.sys.roleinfo.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.care.sys.roleinfo.domain.RoleInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;


/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface RoleInfoDao{

	public String getRoleInfoPK()throws DataAccessException;

	public Integer getRoleInfoCount(RoleInfo vo)throws DataAccessException;

	public Integer getRoleInfoCount(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getRoleInfo(RoleInfo vo)throws DataAccessException;

	public List<DataMap> getRoleInfoListByMap(DataParamMap dmap)throws DataAccessException;

	public List<DataMap> getRoleInfoListByVo(RoleInfo vo)throws DataAccessException;

	public int updateRoleInfo(RoleInfo vo)throws DataAccessException;

	public int insertRoleInfo(RoleInfo vo)throws DataAccessException;

	public int deleteRoleInfo(RoleInfo vo)throws DataAccessException;
}
