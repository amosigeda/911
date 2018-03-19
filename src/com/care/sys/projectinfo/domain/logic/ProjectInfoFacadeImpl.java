package com.care.sys.projectinfo.domain.logic;

import java.util.ArrayList;
import java.util.List;

import com.care.common.config.ServiceBean;
import com.care.common.lang.CommTools;
import com.care.sys.dynamicInfo.dao.DynamicInfoDao;
import com.care.sys.dynamicInfo.domain.DynamicInfo;
import com.care.sys.projectinfo.dao.ProjectInfoDao;
import com.care.sys.projectinfo.domain.ProjectInfo;
import com.godoing.rose.lang.DataList;
import com.godoing.rose.lang.DataMap;
import com.godoing.rose.lang.SystemException;

public class ProjectInfoFacadeImpl implements ProjectInfoFacade{
	
	private ProjectInfoDao projectInfoDao;
	private DynamicInfoDao dynamicInfoDao;

	public ProjectInfoFacadeImpl(){
		
	}
	
	public void setProjectInfoDao(ProjectInfoDao projectInfoDao) {
		this.projectInfoDao = projectInfoDao;
	}
	
	public void setDynamicInfoDao(DynamicInfoDao dynamicInfoDao) {
		this.dynamicInfoDao = dynamicInfoDao;
	}

	public List<DataMap> getProjectInfo(ProjectInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.getProjectInfo(vo);
	}
	public List<DataMap> getProjectName(ProjectInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.getProjectName(vo);
	}

	public DataList getProjectInfoListByVo(ProjectInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(projectInfoDao.getProjectInfoListByVo(vo));
		list.setTotalSize(projectInfoDao.getProjectInfoListCountByVo(vo));
		return list;
	}

	public int getProjectInfoListCountByVo(ProjectInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.getProjectInfoListCountByVo(vo);
	}

	public int getProjectInfoMaxId(ProjectInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.getProjectInfoMaxId(vo);
	}

	public int insertProjectInfo(ProjectInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.insertProjectInfo(vo);
	}

	public int insertRelevanceInfo2(ProjectInfo vo) throws SystemException {
		return projectInfoDao.insertRelevanceInfo2(vo);
	}

	public int getProjectInfoCount(ProjectInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.getProjectInfoCount(vo);
	}

	public int updatePorjectInfo(ProjectInfo vo) throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.updatePorjectInfo(vo);
	}

	public List<DataMap> getProjectStatus(ProjectInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		return projectInfoDao.getProjectStatus(vo);
	}

	
	 public String getProjectRoleInfo(String projectId) throws SystemException{
	        ProjectInfo rvo=new ProjectInfo();
            rvo.setRoleCodeP(projectId);
	        List<DataMap> rlist=projectInfoDao.getProjectRoleInfo(rvo);//查询在这个表里这个ID有的功能
	        ArrayList<String> rfList = new ArrayList<String>();
	        for(int i = 0; i < rlist.size(); i++){
	            DataMap map = rlist.get(i);
	            rfList.add((String)map.get("mName"));//用map通过名取值放在ArrayList里
	        }

	        StringBuffer dt = new StringBuffer("d = new dTree('d');\r\t");//从这里开始拼字符串
	        /*StringBuffer在进行字符串处理时
	         * */
	        dt.append("d.add(0,-1,'菜单列表');\r\t"); //首行
	        DynamicInfo vo=new DynamicInfo();
	        vo.setCondition("effect ='1' ");
	        List<DataMap> list = dynamicInfoDao.getDynamicFuncInfo(vo);//查找生效的用户
	        
	        if (list.size() > 0 ) {
	            dtRoleFunc(list, "super", dt, 0,rfList);
	        }
	        dt.append("document.write(d);");//为什么用这个结尾
	        return dt.toString();
	    }
	 int dtRoleFunc(List<DataMap> list, String sCode, StringBuffer sb, int treeid,ArrayList<String> rfList) {
	     //list生效用户的集合  sCode ,sCode-"super"
		 int s = treeid; 
	        for (int i = 0; i < list.size(); i++) {
	            DataMap map = list.get(i);
	            if (sCode.equals ((String) map.get("superId"))) {//第一个是super话
	                treeid++; 
	                String fName = (String)map.get("mName");
	                String cd = "";
	                if(rfList.contains(fName)) cd = "checked";
	                sb.append("d.add(" + treeid + "," + s + ",\"<input type='checkbox' name='ckbox' value='" + map.get("mName") +  "' superId='" + map.get("superId") +"' cname='" + map.get("nc_name") +
	                          "'  onclick='clkBoxs(this)'" + cd + " >" +
	                          map.get("nc_name") +
	                    "\",'#','','mainFrame','');\r\t");
	                treeid = dtRoleFunc(list, (String) map.get("mName"), sb, treeid,rfList);
	            }
	        }
	        return treeid;
	    }

	public int insertRoleProjectFuncInfo(String roleCode, String funcStr) throws SystemException {
    	String projectNo="";
    	List<DataMap> list = null;
    	ProjectInfo proInfo=new ProjectInfo();
    	proInfo.setCondition("id ="+roleCode);
    	list=ServiceBean.getInstance().getProjectInfoFacade().getProjectInfo(proInfo);
    
    	if(!list.isEmpty()){
    		projectNo=list.get(0).getAt("project_no").toString();
    	}
    	  ProjectInfo vo=new ProjectInfo();
        String[] funcs = funcStr.split(",");
        vo.setRoleCodeP(roleCode);
        vo.setProjectCode(projectNo);
          List<DataMap> rlist = projectInfoDao.getRoleProjectFuncInfo(vo);
        
        String[] fs = new String[rlist.size()];
        for(int i = 0; i < rlist.size(); i++){
            DataMap map = rlist.get(i);
            String funcc = (String)map.get("mName");
            fs[i] = funcc;
        }

        String[] in = CommTools.compareStr(fs,funcs);
        for(int i = 0; i < in.length; i++){
            vo.setmName(in[i]);

                  projectInfoDao.insertProjectRoleFuncInfo(vo);
        }
        
        String[] del = CommTools.compareStr(funcs,fs);
        for(int i = 0; i < del.length; i++){
            vo.setmName(del[i]);
            projectInfoDao.deleteProjectRoleFunInfo(vo);
        }
        return 0;
    }

	public List<DataMap> getProjectLocationSwitchInfo(ProjectInfo vo)
			throws SystemException {
		return projectInfoDao.getProjectLocationSwitchInfo(vo);
	}

	public DataList getProjectLocationSwitchInfoBvo(ProjectInfo vo)
			throws SystemException {
		// TODO Auto-generated method stub
		DataList list = new DataList(projectInfoDao.getProjectLocationSwitchInfoBvo(vo));
		list.setTotalSize(projectInfoDao.getProjectLocationInfoListCountByVo(vo));
		return list;
	}

	public int updatePorjectSwitchInfo(ProjectInfo vo) throws SystemException {
		return projectInfoDao.updatePorjectSwitchInfo(vo);
	}

}
