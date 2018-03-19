package com.care.sys.roleinfo.domain.logic;

import java.util.ArrayList;
import java.util.List;

import com.care.common.config.ServiceBean;
import com.care.common.lang.CommTools;
import com.care.sys.funcinfo.dao.FuncInfoDao;
import com.care.sys.funcinfo.domain.FuncInfo;
import com.care.sys.rolefuncinfo.dao.RoleFuncInfoDao;
import com.care.sys.rolefuncinfo.domain.RoleFuncInfo;
import com.care.sys.roleinfo.dao.RoleInfoDao;
import com.care.sys.roleinfo.domain.RoleInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.DataParamMap;
import com.godoing.rose.lang.SystemException;

/* rose1.2 to files
 * rose anthor:wlb  1.0 version by time 2005/12/12
 * rose anthor:wlb  1.1 version by time 2006/06/06
 * rose anthor:wlb  1.2 version by time 2006/12/27*/
public class RoleInfoFacadeImpl implements RoleInfoFacade {

	private RoleInfoDao roleInfoDao;
    private RoleFuncInfoDao roleFuncInfoDao;
    private FuncInfoDao funcInfoDao;
	public RoleInfoFacadeImpl (){
	}

	public void setRoleInfoDao(RoleInfoDao roleInfoDao){
		this.roleInfoDao = roleInfoDao;
	}

    public void setRoleFuncInfoDao(RoleFuncInfoDao roleFuncInfoDao){
        this.roleFuncInfoDao = roleFuncInfoDao;
    }
    public void setFuncInfoDao(FuncInfoDao funcInfoDao){
        this.funcInfoDao = funcInfoDao;
    }

	public String getRoleInfoPK() throws SystemException{
		return roleInfoDao.getRoleInfoPK() ;
	}

	public Integer getRoleInfoCount(RoleInfo vo) throws SystemException{
		return roleInfoDao.getRoleInfoCount(vo);
	}

	public Integer getRoleInfoCount(DataParamMap dmap) throws SystemException{
		return roleInfoDao.getRoleInfoCount(dmap);
	}

	public List<DataMap> getRoleInfo(RoleInfo vo) throws SystemException{
		return roleInfoDao.getRoleInfo(vo);
	}

	public DataList getDataRoleInfoListByVo(RoleInfo vo) throws SystemException{
		DataList list = new DataList(roleInfoDao.getRoleInfoListByVo(vo));
		list.setTotalSize(roleInfoDao.getRoleInfoCount(vo));
		return list;
	}

	public DataList getDataRoleInfoListByMap(DataParamMap dmap) throws SystemException{
		DataList list = new DataList(roleInfoDao.getRoleInfoListByMap(dmap));
		list.setTotalSize(roleInfoDao.getRoleInfoCount(dmap));
		return list;
	}

	public int updateRoleInfo(RoleInfo vo) throws SystemException{
		return roleInfoDao.updateRoleInfo(vo);
	}

	public int insertRoleInfo(RoleInfo vo) throws SystemException{
		return roleInfoDao.insertRoleInfo(vo);
	}

	public int deleteRoleInfo(RoleInfo vo) throws SystemException{
		return roleInfoDao.deleteRoleInfo(vo);
	}

    //ȡ�ý�ɫȨ���б�
    public String getRoleFuncTree(String roleCode) throws SystemException{
        RoleFuncInfo rvo = new RoleFuncInfo();//�ҵ���ǰ��ɫ���е�
        rvo.setRoleCode(roleCode);
        List<DataMap> rlist = roleFuncInfoDao.getRoleFuncInfo(rvo);
        ArrayList<String> rfList = new ArrayList<String>();
         for(int i = 0; i < rlist.size(); i++){
              DataMap map = rlist.get(i);
            rfList.add((String)map.get("funcCode"));
        }

        //��
        StringBuffer dt = new StringBuffer("d = new dTree('d');\r\t");
        dt.append("d.add(0,-1,'权限列表');\r\t"); //������
        //�ҵ�����Ȩ���б�
        FuncInfo vo = new FuncInfo();
        vo.setCondition("statu ='1' ");
        List<DataMap> list = funcInfoDao.getFuncInfo(vo);
        if (list.size() > 0 ) {
            dtRoleFunc(list, "super", dt, 0,rfList);
        }
        dt.append("document.write(d);");
        return dt.toString();
    }

    int dtRoleFunc(List<DataMap> list, String sCode, StringBuffer sb, int treeid,ArrayList<String> rfList) {
        int s = treeid; //���������treeid
        for (int i = 0; i < list.size(); i++) {
            DataMap map = list.get(i);
            if (sCode.equals ((String) map.get("superCode"))) {
                treeid++; //������½�� +1
                String fName = (String)map.get("funcCode");
                String cd = "";
                if(rfList.contains(fName)) cd = "checked";
                sb.append("d.add(" + treeid + "," + s + ",\"<input type='checkbox' name='ckbox' value='" +
                          map.get("funcCode") +  "' superCode='" + map.get("superCode") +
                          "' cname='" + map.get("funcName") +
                          "'  onclick='clkBoxs(this)'" + cd + " >" +
                          map.get("funcName") +
                    "\",'#','','mainFrame','');\r\t");
                treeid = dtRoleFunc(list, (String) map.get("funcCode"), sb, treeid,rfList);
            }
        }
        return treeid;
    }

    //����
    public int insertRoleFuncInfo(String roleCode,String funcStr) throws SystemException{
    	String roleDesc ="";
    	List<DataMap> list = null;
    	RoleInfo roleInfo = new RoleInfo(); 
    	roleInfo.setCondition("id ="+roleCode);
    	list = ServiceBean.getInstance().getRoleInfoFacade().getRoleInfo(roleInfo);
    	if(!list.isEmpty()){
    		roleDesc = list.get(0).getAt("roleDesc").toString();    		
    	}
        RoleFuncInfo vo = new RoleFuncInfo();
        String[] funcs = funcStr.split(",");
        vo.setRoleCode(roleCode);
        vo.setUserCode(roleDesc);
        List<DataMap> rlist = roleFuncInfoDao.getRoleFuncInfo(vo);//ȡ��ԭ����
        String[] fs = new String[rlist.size()];
        for(int i = 0; i < rlist.size(); i++){
            DataMap map = rlist.get(i);
            String funcc = (String)map.get("funcCode");
            fs[i] = funcc;
        }

//        SalerInfo salerInfo = new SalerInfo();
//        List<DataMap> salerList = ServiceBean.getInstance().getSalerInfoFacade().getSalerInfo(salerInfo);
//        
//        CustomInfo customInfo = new CustomInfo();
//        List<DataMap> customList = ServiceBean.getInstance().getCustomInfoFacade().getCustomInfo(customInfo);
//     
        
        String[] in = CommTools.compareStr(fs,funcs);//ȡ��������
        for(int i = 0; i < in.length; i++){
            vo.setFuncCode(in[i]);
//            if("saler".equals(roleCode)){   //������������
//            	vo.setUserCode("admin");    //adminһ��Ҫ��
//        		roleFuncInfoDao.insertRoleFuncInfo(vo);
//            	for(int j=0;j<salerList.size();j++){
//                	vo.setUserCode(""+salerList.get(j).getAt("salerCode"));
//                	roleFuncInfoDao.insertRoleFuncInfo(vo);
//                }
//
//            }else if("custom".equals(roleCode)) { //�ͻ�����
//            	vo.setUserCode("admin");    //adminһ��Ҫ��
//        		roleFuncInfoDao.insertRoleFuncInfo(vo);
//            	for(int k=0;k<customList.size();k++){
//            		vo.setUserCode(""+customList.get(k).getAt("customCode"));
//            		roleFuncInfoDao.insertRoleFuncInfo(vo);
//            	}
//            }else{
            	vo.setUserCode(roleDesc);
            	roleFuncInfoDao.insertRoleFuncInfo(vo);
//            }
        }
        
        String[] del = CommTools.compareStr(funcs,fs);//ȡ��������
        for(int i = 0; i < del.length; i++){
            vo.setFuncCode(del[i]);
            roleFuncInfoDao.deleteRoleFuncInfo(vo);
        }
        return 0;
    }
}
