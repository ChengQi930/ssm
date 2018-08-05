package cn.bizowner.crm.helper;

import java.util.Iterator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.bizowner.crm.env.Env;

public class Test {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		
		try
		{
			CrmHelper crmHelper = new 	CrmHelper();
			
			String sessionId = crmHelper.doLogin(Env.vtigerUSR, Env.vtigerUSRKEY);
			
			String accountname = "";
			
			//JSONArray result = crmHelper.doQuery("SELECT accountname,bill_city,bill_country FROM Accounts   where accountname like '%"+accountname+"%' order by createdtime desc limit 0,10 ",sessionId);
			JSONArray result = crmHelper.doQuery("SELECT accountname,bill_city,bill_country FROM Accounts   where accountname like '%"+accountname+"%' order by createdtime desc limit 0,10 ",sessionId);
			if (result == null)
				return ;

			System.out.println("# Result Rows " + result.size());

			System.out.println("# " + crmHelper.getResultColumns(result));

			Iterator resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				JSONObject row = (JSONObject) resultIterator.next();
				Iterator rowIterator = row.keySet().iterator();

				System.out.println("---");
				while (rowIterator.hasNext()) {
					Object key = rowIterator.next();
					Object val = row.get(key);
					System.out.println(" " + key + " : " + val);
				}
			}
		}
		catch(Exception ex)
		{
					String s = ex.getMessage();
					System.out.println(ex.getMessage());
		}
		
		
		
	}

}
