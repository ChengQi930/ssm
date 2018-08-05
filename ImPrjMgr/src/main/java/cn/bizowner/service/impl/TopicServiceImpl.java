package cn.bizowner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import cn.bizowner.mapper.PlanTopicMapper;
import cn.bizowner.mapper.PlanUserMapper;
import cn.bizowner.service.CommonService;
import cn.bizowner.service.TopicService;



@Service("topicService")
public class TopicServiceImpl implements TopicService {
 
	@Autowired
	private PlanUserMapper planUserMapper;
	
	@Autowired
	private PlanTopicMapper planTopicMapper;

	@Override
	public String getTopicId(String projectId, Integer type) throws Exception {

			String topicId = planTopicMapper.getTopicId(projectId, type);
			
			if(topicId == null)
			{
					throw new Exception("");
			}
			
			return topicId;
	}
	
}
