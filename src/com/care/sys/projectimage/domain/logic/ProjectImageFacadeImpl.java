package com.care.sys.projectimage.domain.logic;

import java.util.List;

import com.care.sys.projectimage.dao.ProjectImageDao;
import com.care.sys.projectimage.domain.ProjectImage;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class ProjectImageFacadeImpl implements ProjectImageFacade {
	private ProjectImageDao projectImageDao;
	
	public void setProjectImageDao(ProjectImageDao projectImageDao) {
		this.projectImageDao = projectImageDao;
	}

	public List<DataMap> getProjectImage(ProjectImage vo) throws SystemException {
		return projectImageDao.getProjectImage(vo);
	}

	public List<DataMap> getProjectImageListByVo(ProjectImage vo)
			throws SystemException {
		DataList list = new DataList(projectImageDao.getProjectImageListByVo(vo));
		list.setTotalSize(projectImageDao.getProjectImageListCountByVo(vo));
		return list;
	}

	public Integer getProjectImageListCountByVo(ProjectImage vo)
			throws SystemException {
		
		return projectImageDao.getProjectImageListCountByVo(vo);
	}

	public Integer updateProjectImage(ProjectImage vo) throws SystemException {
		
		return projectImageDao.updateProjectImage(vo);
	}

	public Integer insertProjectImage(ProjectImage vo) throws SystemException {
		
		return projectImageDao.insertProjectImage(vo);
	}

	public Integer updateProjectImagePath(ProjectImage vo)
			throws SystemException {

		return projectImageDao.updateProjectImagePath(vo);
	}
}
