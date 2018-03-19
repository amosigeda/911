package com.care.sys.projectinfo.domain.logic;

import java.util.List;

import com.care.sys.projectinfo.domain.ProjectInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface ProjectInfoFacade {
	public List<DataMap> getProjectName(ProjectInfo vo) throws SystemException;
	
	public List<DataMap> getProjectInfo(ProjectInfo vo) throws SystemException;
	
	public DataList getProjectInfoListByVo(ProjectInfo vo) throws SystemException;
	
	public int getProjectInfoListCountByVo(ProjectInfo vo) throws SystemException;
	
	public int getProjectInfoMaxId(ProjectInfo vo) throws SystemException;
	
	public int getProjectInfoCount(ProjectInfo vo) throws SystemException;
	
	public int insertProjectInfo(ProjectInfo vo) throws SystemException;

	public int insertRelevanceInfo2(ProjectInfo vo)throws SystemException;
	
	public int updatePorjectInfo(ProjectInfo vo) throws SystemException;

	public List<DataMap> getProjectStatus(ProjectInfo vo)throws SystemException;

	public String getProjectRoleInfo(String projectId)throws SystemException;

	public int insertRoleProjectFuncInfo(String roleCode, String funcStr)throws SystemException;

	public List<DataMap> getProjectLocationSwitchInfo(ProjectInfo vo)throws SystemException;

	public DataList getProjectLocationSwitchInfoBvo(ProjectInfo vo)throws SystemException;

	public int updatePorjectSwitchInfo(ProjectInfo vo)throws SystemException;


}
