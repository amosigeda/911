package com.care.sys.sysloginfo.domain.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.care.sys.sysloginfo.dao.SysLogInfoDao;
import com.care.sys.sysloginfo.domain.SysLogInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.lang.SystemException;

/* rose1.2 to files 
 * rose anthor:wlb  1.0 version by time 2005/12/12  
 * rose anthor:wlb  1.1 version by time 2006/06/06  
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class SysLogInfoFacadeImpl implements SysLogInfoFacade {

	private SysLogInfoDao sysLogInfoDao;

	public SysLogInfoFacadeImpl() {
	}

	public void setSysLogInfoDao(SysLogInfoDao sysLogInfoDao) {
		this.sysLogInfoDao = sysLogInfoDao;
	}

	public String getSysLogInfoPK() throws SystemException {
		return sysLogInfoDao.getSysLogInfoPK();
	}

	public Integer getSysLogInfoCount(SysLogInfo vo) throws SystemException {
		return sysLogInfoDao.getSysLogInfoCount(vo);
	}

	public Integer getSysLogInfoCount(DataParamMap dmap) throws SystemException {
		return sysLogInfoDao.getSysLogInfoCount(dmap);
	}

	public List<DataMap> getSysLogInfo(SysLogInfo vo) throws SystemException {
		return sysLogInfoDao.getSysLogInfo(vo);
	}
	
	public List<DataMap> getSysLogInfoDesc(SysLogInfo vo) throws SystemException {
		return sysLogInfoDao.getSysLogInfoDesc(vo);
	}
	
	public DataList getDataSysLogInfoListByVo(SysLogInfo vo)
			throws SystemException {
		DataList list = new DataList(sysLogInfoDao.getSysLogInfoListByVo(vo));
		list.setTotalSize(sysLogInfoDao.getSysLogInfoCount(vo));
		return list;
	}

	public DataList getDataSysLogInfoListByMap(DataParamMap dmap)
			throws SystemException {
		DataList list = new DataList(sysLogInfoDao.getSysLogInfoListByMap(dmap));
		list.setTotalSize(sysLogInfoDao.getSysLogInfoCount(dmap));
		return list;
	}

	public int updateSysLogInfo(SysLogInfo vo) throws SystemException {
		return sysLogInfoDao.updateSysLogInfo(vo);
	}

	public int insertSysLogInfo(SysLogInfo vo) throws SystemException {
		return sysLogInfoDao.insertSysLogInfo(vo);
	}

	public int insertSysLogInfo(String user, String log, String ip) throws SystemException {
		SysLogInfo vo = new SysLogInfo();
		vo.setUserName(user);
		vo.setIp(ip);
		vo.setLogDate(new Date());
		vo.setLogs(log);
		return sysLogInfoDao.insertSysLogInfo(vo);
	}

	public int deleteSysLogInfo(SysLogInfo vo) throws SystemException {
		return sysLogInfoDao.deleteSysLogInfo(vo);
	}

	public int deleteSysLogInfos(SysLogInfo vo) throws SystemException {
		return sysLogInfoDao.deleteSysLogInfos(vo);
	}
	public void deleteSynSysLogInfo() throws SystemException {
		/* ��ʽ */
		SimpleDateFormat DF_NORMAL = new SimpleDateFormat("yyyy-MM-dd");

		/* ��ȡ��ǰʱ�� */
		Date cur = new Date(System.currentTimeMillis());

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(cur);

		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		/* ÿ��6����ͬ��ǰһ���sms��� */
		if (hour < 6) {
			return;
		}
		/* ǰ10�� */
		calendar.add(Calendar.DAY_OF_MONTH, -10);

		Date bakTime = calendar.getTime();
		String curdate = DF_NORMAL.format(bakTime);

		SysLogInfo vo = new SysLogInfo();
		vo.setCondition(" logDate <'" + curdate + " 23:59:59' ");
		
		//sysLogInfoDao.deleteSysLogInfo(vo);    �Զ����ݹر�
		//sysLogInfoDao.deleteSysLogInfos(vo);

	}

	public void insertBeifenRecord(SysLogInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		sysLogInfoDao.insertBeifenRecord(vo);
	}

	public DataList getBeifenRecord(SysLogInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		DataList list=new DataList(sysLogInfoDao.getBeifenRecord(vo));
		list.setTotalSize(sysLogInfoDao.getBeifenRecordCount(vo));
		return list;
	}

	public int updateOutDate(SysLogInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return sysLogInfoDao.updateOutDate(vo);
	}

	public int selectSysLogInfoCount(SysLogInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return sysLogInfoDao.selectSysLogInfoCount(vo);
	}
}
