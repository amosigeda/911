package com.care.sys.phonecountryinfo.domain.logic;

import java.util.List;
import com.care.sys.phonecountryinfo.dao.PhoneCountryInfoDao;
import com.care.sys.phonecountryinfo.domain.PhoneCountryInfo;
import com.godoing.rose.lang.DataMap;

public class PhoneCountryInfoFacadeImpl implements PhoneCountryInfoFacade {
	
	PhoneCountryInfoDao phoneCountryInfoDao;

	public void setPhoneCountryInfoDao(PhoneCountryInfoDao phoneCountryInfoDao) {
		this.phoneCountryInfoDao = phoneCountryInfoDao;
	}


	public List<DataMap> getPhoneCountryInfo(PhoneCountryInfo vo) {

		return phoneCountryInfoDao.getPhoneCountryInfo(vo);
	}

}
