package cn.bizowner.servlet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.bizowner.model.Node;
import cn.bizowner.model.ProgressInfo;
import cn.bizowner.utils.SingleList;
import cn.bizowner.utils.TimeUtil;


public class InitServlet extends HttpServlet {

	
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}*/
	
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		String classessPath = this.getClass().getClassLoader().getResource("/").getPath();
		String webRootPath = classessPath.replace("WEB-INF/classes/", "");
		
		if("\\".equals(File.separator))
		{
			webRootPath = webRootPath.substring(1).replaceAll("/", "\\\\");
		}
		
		
		
		deleteSingleListThread th = new  deleteSingleListThread();
		
		Thread thread = new Thread(th);
		thread.start();
		
	}
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}
	
	
	public class deleteSingleListThread implements Runnable
	{
		
				@Override
				public void run() {
					
					
					try
					{
						SingleList singleList = SingleList.getInstance();
						while(true)
						{
									List<Node> listNode =  singleList.getNodeList();
									for(int i=0;i<listNode.size();i++)
									{
											
											ProgressInfo progressInfo = listNode.get(i).getProgressInfo();
											if(progressInfo.isFinishFlag() == true)
											{
														String finishTime = progressInfo.getFinishTime();
														long timeMillis = TimeUtil.getCurrTimeMillis();
														String time = TimeUtil.getYearMonthDayHourMinuteSecond(timeMillis+10*1000);
														if(time.compareTo(finishTime)>0)
														{
																String uuid = listNode.get(i).getUuid();
																singleList.delete(uuid);
																Thread.sleep(300000);
																continue;
														}
											}
											
									}
									Thread.sleep(300000);
						}
					}
					catch(Exception ex)
					{
							String s = ex.getMessage();
					}
					
					
					
				}
	}

}
