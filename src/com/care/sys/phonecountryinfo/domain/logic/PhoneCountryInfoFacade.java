package com.care.sys.phonecountryinfo.domain.logic;

import java.util.List;
import com.care.sys.phonecountryinfo.domain.PhoneCountryInfo;
import com.godoing.rose.lang.DataMap;

public interface PhoneCountryInfoFacade {
	
	public List<DataMap> getPhoneCountryInfo(PhoneCountryInfo vo);
}
