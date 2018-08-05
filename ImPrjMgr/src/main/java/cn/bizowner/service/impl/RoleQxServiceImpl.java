package cn.bizowner.service.impl; 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.mapper.RoleQxMapper;
import cn.bizowner.model.RoleQx;
import cn.bizowner.model.RoleQxInfo;
import cn.bizowner.service.RoleQxService;
import cn.bizowner.utils.CommonUtils;



@Service("roleQxService")
public class RoleQxServiceImpl  implements RoleQxService {
 
	@Autowired
	private RoleQxMapper  roleQxMapper;
	
	
	
	@Override
	public List<RoleQxInfo> getEnableRoleQxList(String userId,String parentId) throws Exception
	{ 
		
		List<RoleQxInfo> listRoleQxInfo = roleQxMapper.getRoleQxList(userId, parentId);
 
		return listRoleQxInfo;
	}
 
	
	
	@Override
	public List<RoleQxInfo> getRoleQxList(String roleId,String parentId) throws Exception
	{

		
		//List<RoleQxInfo>  listRoleQxInfo = roleQxMapper.getFunctionListByParentId(parentId);
		
		List<RoleQxInfo>  listRoleQxInfo = roleQxMapper.getFunctionListByParentId(roleId, parentId);
		
		/*for(int i=0;i<listRoleQxInfo.size();i++)
		{
			String funcId = listRoleQxInfo.get(i).getFuncId();
			
			
			int count = roleQxMapper.isRecordExist(roleId, funcId);
			
			if(count == 0)
			{
				listRoleQxInfo.get(i).setEnableFlag(false);
			}
			else 
			{
				listRoleQxInfo.get(i).setEnableFlag(true);
			}
		}*/
		
		return listRoleQxInfo;
	}
	
	
 

	@Override
	public void updateRoleQxList(final List<RoleQx> listRoleQx) throws Exception
	{

					for(int i=0;i<listRoleQx.size();i++)
					{
						
							RoleQx roleQx = listRoleQx.get(i);
						
							String roleId = roleQx.getRoleId();
							String funcId = roleQx.getFuncId();
							
							int ret = roleQxMapper.isRecordExist(roleId, funcId);
							
							if(ret == 0)
							{
									String uuid = CommonUtils.getUUID();
									roleQx.setId(uuid);
									roleQxMapper.addRoleQx(roleQx);
							}
							else
							{
									roleQxMapper.updateRoleQx(roleQx);
							}
					}
	}

	
}
