package com.care.sys.phonecountryinfo.dao.ibatis;

import java.util.List;
import org.apache.commons.logging.Log;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.care.sys.phonecountryinfo.dao.PhoneCountryInfoDao;
import com.care.sys.phonecountryinfo.domain.PhoneCountryInfo;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.log.LogFactory;

public class SqlMapPhoneCountryInfoDao extends SqlMapClientDaoSupport implements PhoneCountryInfoDao{
	
	Log logger = LogFactory.getLog(SqlMapPhoneCountryInfoDao.class);
	public List<DataMap> getPhoneCountryInfo(PhoneCountryInfo vo)
			throws DataAccessException {
		logger.debug("getPhoneCountryInfo(PhoneCountryInfo vo)");
		return getSqlMapClientTemplate().queryForList("getPhoneCountryInfo", vo);
	}

}
