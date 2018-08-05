package cn.bizowner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bizowner.mapper.CommonMapper;
import cn.bizowner.service.CommonService;



@Service("commonService")
public class CommonServiceImpl implements CommonService {
 
	@Autowired
	private CommonMapper commonMapper;
	
	
	@Override
	public String getAssetBigClassByAssetClass(String assetClass){
		
		String assetBigClass = "通用设备";

		
		String stdCode = commonMapper.getTopParentStdCodeByAssetClassName(assetClass);
		if(stdCode != null)
		{
					if(stdCode.startsWith("1"))
					{
						assetBigClass = "土地、房屋及构筑物";
					}
					else if(stdCode.startsWith("2"))
					{
						assetBigClass = "通用设备";
					}
					else if(stdCode.startsWith("3"))
					{
						assetBigClass = "专用设备";
					}
					else if(stdCode.startsWith("4"))
					{
						assetBigClass = "文物和陈列品";
					}
					else if(stdCode.startsWith("5"))
					{
						assetBigClass = "图书、档案";
					}
					else if(stdCode.startsWith("601") || stdCode.startsWith("602") || stdCode.startsWith("603") || stdCode.startsWith("604"))
					{
						assetBigClass = "家具、用具、装具及动植物";
					}
					else
					{
						assetBigClass = "无形资产";
					}
		}
		return assetBigClass;
	}
}
