package com.care.sys.directiveinfo.domain.logic;

import java.util.List;

import com.care.sys.directiveinfo.dao.DirectiveInfoDao;
import com.care.sys.directiveinfo.domain.DirectiveInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class DirectiveInfoFacadeImpl implements DirectiveInfoFacade{
	
	private DirectiveInfoDao directiveInfoDao;
	

	public void setDirectiveInfoDao(DirectiveInfoDao directiveInfoDao) {
		this.directiveInfoDao = directiveInfoDao;
	}
	
	public DirectiveInfoFacadeImpl(){
		
	}

	public List<DataMap> getDirectiveInfo(DirectiveInfo vo) throws SystemException {
		return directiveInfoDao.getDirectiveInfo(vo);
	}

	public Integer insertDirectiveInfo(DirectiveInfo vo) throws SystemException {
		return directiveInfoDao.insertDirectiveInfo(vo);
	}

	public Integer updateDirectiveInfo(DirectiveInfo vo) throws SystemException {
		return directiveInfoDao.updateDirectiveInfo(vo);
	}

	public Integer getDirectiveInfoCount(DirectiveInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return directiveInfoDao.getDirectiveInfoCount(vo);
	}

	public DataList getDirectiveInfoListByVo(DirectiveInfo vo)
			throws SystemException {
		DataList list = new DataList(directiveInfoDao.getDirectiveInfoListByVo(vo));
		list.setTotalSize(directiveInfoDao.getDirectiveInfoCount(vo));
		return list;
	}

}
