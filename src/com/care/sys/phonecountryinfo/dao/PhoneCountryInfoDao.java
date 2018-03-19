package com.care.sys.phonecountryinfo.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import com.care.sys.phonecountryinfo.domain.PhoneCountryInfo;
import com.godoing.rose.lang.DataMap;

public interface PhoneCountryInfoDao {
	
	public List<DataMap> getPhoneCountryInfo(PhoneCountryInfo vo) throws DataAccessException;
}
