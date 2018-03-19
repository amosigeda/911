package com.care.sys.projectimage.domain.logic;

import java.util.List;
import com.care.sys.projectimage.domain.ProjectImage;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public interface ProjectImageFacade {
	public List<DataMap> getProjectImage(ProjectImage vo) throws SystemException;
		
	public List<DataMap> getProjectImageListByVo(ProjectImage vo) throws SystemException;
	
	public Integer getProjectImageListCountByVo(ProjectImage vo) throws SystemException;
	
	public Integer updateProjectImage(ProjectImage vo) throws SystemException;
	
	public Integer insertProjectImage(ProjectImage vo) throws SystemException;
	
	public Integer updateProjectImagePath(ProjectImage vo) throws SystemException;
}
