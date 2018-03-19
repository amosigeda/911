
package com.care.sys.monitorinfo.domain.logic;

import java.util.List;

import com.care.sys.monitorinfo.dao.MonitorInfoDao;
import com.care.sys.monitorinfo.domain.MonitorInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class MonitorInfoFacadeImpl implements MonitorInfoFacade {

	private MonitorInfoDao monitorInfoDao;

	public MonitorInfoFacadeImpl() {
	}

	public void setMonitorInfoDao(MonitorInfoDao monitorInfoDao) {
		this.monitorInfoDao = monitorInfoDao;
	}

	public List<DataMap> getMonitorInfo(MonitorInfo vo) throws SystemException {
		return monitorInfoDao.getMonitorInfo(vo);
	}
	
	public DataList getDataMonitorInfoListByVo(MonitorInfo vo)
			throws SystemException {
		DataList list = new DataList(monitorInfoDao.getMonitorInfoListByVo(vo));
		list.setTotalSize(monitorInfoDao.getMonitorInfoCount(vo));
		return list;
	}

	public int updateMonitorInfo(MonitorInfo vo) throws SystemException {
		return monitorInfoDao.updateMonitorInfo(vo);
	}

	public int insertMonitorInfo(MonitorInfo vo) throws SystemException {
		return monitorInfoDao.insertMonitorInfo(vo);
	}

	public List<DataMap> getVisitInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.getVisitInfo(vo);
	}

	public DataList getVisitInfoListByVo(MonitorInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(monitorInfoDao.getVisitInfoListByVo(vo));
		list.setTotalSize(monitorInfoDao.getVisitInfoCount(vo));
		return list;
	}

	public int updateVisitInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.updateVisitInfo(vo);
	}

	public int insertVisitInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.insertVisitInfo(vo);
	}

	public List<DataMap> getSwitchInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.getSwitchInfo(vo);
	}

	public int updateSwitchInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.updateSwitchInfo(vo);
	}

	public int insertMonitorInfoBf(MonitorInfo io) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.insertMonitorInfoBf(io);
	}

	public int insertVisitBackup(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.insertVisitBackup(vo);
	}

	public int deleteVisit(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.deleteVisit(vo);
	}

	public int deleteMonitorInfo(MonitorInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return monitorInfoDao.deleteMonitorInfo(vo);
	}

}
