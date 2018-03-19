package com.care.sys.roleinfo.domain.logic;

import java.util.List;

import com.care.sys.roleinfo.domain.RoleInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.lang.SystemException;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public interface RoleInfoFacade {

	public String getRoleInfoPK() throws SystemException;

	public Integer getRoleInfoCount(RoleInfo vo) throws SystemException;

	public Integer getRoleInfoCount(DataParamMap dmap) throws SystemException;

	public List<DataMap> getRoleInfo(RoleInfo vo) throws SystemException;

	public DataList getDataRoleInfoListByVo(RoleInfo vo) throws SystemException;

	public DataList getDataRoleInfoListByMap(DataParamMap dmap) throws SystemException;

	public int updateRoleInfo(RoleInfo vo) throws SystemException;

	public int insertRoleInfo(RoleInfo vo) throws SystemException;

	public int deleteRoleInfo(RoleInfo vo) throws SystemException;

    public String getRoleFuncTree(String roleCode) throws SystemException;

    public int insertRoleFuncInfo(String roleCode,String funcStr) throws SystemException;
}
