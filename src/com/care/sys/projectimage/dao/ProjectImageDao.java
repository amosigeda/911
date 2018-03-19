package com.care.sys.projectimage.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.care.sys.projectimage.domain.ProjectImage;
import com.godoing.rose.lang.DataMap;

public interface ProjectImageDao {
	public List<DataMap> getProjectImage(ProjectImage vo) throws DataAccessException;
	
	public List<DataMap> getProjectImageListByVo(ProjectImage vo) throws DataAccessException;
	
	public Integer getProjectImageListCountByVo(ProjectImage vo) throws DataAccessException;
	
	public Integer updateProjectImage(ProjectImage vo) throws DataAccessException;
	
	public Integer insertProjectImage(ProjectImage vo) throws DataAccessException;
	
	public Integer updateProjectImagePath(ProjectImage vo) throws DataAccessException;
}
