package com.care.sys.rolefuncinfo.domain.logic;

import java.util.List;

import com.care.sys.rolefuncinfo.domain.RoleFuncInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.lang.SystemException;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface RoleFuncInfoFacade {

	public String getRoleFuncInfoPK() throws SystemException;

	public Integer getRoleFuncInfoCount(RoleFuncInfo vo) throws SystemException;

	public Integer getRoleFuncInfoCount(DataParamMap dmap) throws SystemException;

	public List<DataMap> getRoleFuncInfo(RoleFuncInfo vo) throws SystemException;

	public DataList getDataRoleFuncInfoListByVo(RoleFuncInfo vo) throws SystemException;

	public DataList getDataRoleFuncInfoListByMap(DataParamMap dmap) throws SystemException;

	public int updateRoleFuncInfo(RoleFuncInfo vo) throws SystemException;

	public int insertRoleFuncInfo(RoleFuncInfo vo) throws SystemException;

	public int deleteRoleFuncInfo(RoleFuncInfo vo) throws SystemException;
}
