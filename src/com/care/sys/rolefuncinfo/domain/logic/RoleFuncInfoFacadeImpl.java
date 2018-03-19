package com.care.sys.rolefuncinfo.domain.logic;

import java.util.List;

import com.care.sys.rolefuncinfo.dao.RoleFuncInfoDao;
import com.care.sys.rolefuncinfo.domain.RoleFuncInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.lang.SystemException;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class RoleFuncInfoFacadeImpl implements RoleFuncInfoFacade {

	private RoleFuncInfoDao roleFuncInfoDao;
	public RoleFuncInfoFacadeImpl (){
	}

	public void setRoleFuncInfoDao(RoleFuncInfoDao roleFuncInfoDao){
		this.roleFuncInfoDao = roleFuncInfoDao;
	}

	public String getRoleFuncInfoPK() throws SystemException{
		return roleFuncInfoDao.getRoleFuncInfoPK() ;
	}

	public Integer getRoleFuncInfoCount(RoleFuncInfo vo) throws SystemException{
		return roleFuncInfoDao.getRoleFuncInfoCount(vo);
	}

	public Integer getRoleFuncInfoCount(DataParamMap dmap) throws SystemException{
		return roleFuncInfoDao.getRoleFuncInfoCount(dmap);
	}

	public List<DataMap> getRoleFuncInfo(RoleFuncInfo vo) throws SystemException{
		return roleFuncInfoDao.getRoleFuncInfo(vo);
	}

	public DataList getDataRoleFuncInfoListByVo(RoleFuncInfo vo) throws SystemException{
		DataList list = new DataList(roleFuncInfoDao.getRoleFuncInfoListByVo(vo));
		list.setTotalSize(roleFuncInfoDao.getRoleFuncInfoCount(vo));
		return list;
	}

	public DataList getDataRoleFuncInfoListByMap(DataParamMap dmap) throws SystemException{
		DataList list = new DataList(roleFuncInfoDao.getRoleFuncInfoListByMap(dmap));
		list.setTotalSize(roleFuncInfoDao.getRoleFuncInfoCount(dmap));
		return list;
	}

	public int updateRoleFuncInfo(RoleFuncInfo vo) throws SystemException{
		return roleFuncInfoDao.updateRoleFuncInfo(vo);
	}

	public int insertRoleFuncInfo(RoleFuncInfo vo) throws SystemException{
		return roleFuncInfoDao.insertRoleFuncInfo(vo);
	}

	public int deleteRoleFuncInfo(RoleFuncInfo vo) throws SystemException{
		return roleFuncInfoDao.deleteRoleFuncInfo(vo);
	}
}
