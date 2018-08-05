package cn.bizowner.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import cn.bizowner.utils.CommonUtils;

public class MqHelper {

	private static final String USERNAME = "admin";  
    private static final String PASSWORD = "admin";
    private static final String BROKEURL = "tcp://192.168.1.23:61616"; 
    
    
    
	public static void createTopic(String topicName) throws Exception
	{
		ConnectionFactory connectionFactory;//连接工厂  
        Connection connection = null;//连接  
        Session session;//会话  
        Destination destination;//消息的目的地  
        MessageProducer messageProducer;
        
        System.out.println("USERNAME:"+USERNAME);
        System.out.println("PASSWORD:"+PASSWORD);
        System.out.println("BROKEURL:"+BROKEURL);
        
        System.out.println("topicName:"+topicName);
          
        //实例化连接工厂  
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);  
        //通过连接工厂获取连接  
        try {  
           connection = connectionFactory.createConnection();  
           connection.start();//启动连接  
           //创建session,第一个参数是否有事务  
           session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);  
           //创建Topic主题  
           destination = session.createTopic(topicName);  
           messageProducer = session.createProducer(destination);    
           //提交事务  
           session.commit();  
             
       } 
       catch (Exception e) 
       {  
           throw  new Exception(e.getMessage());
       }
        finally
        {  
           if(connection != null){  
               try {  
                   connection.close();  
               } catch (JMSException e) {  
                   // TODO Auto-generated catch block  
                   e.printStackTrace();  
               }  
           }  
       }  
	}
	
	
	
	
	
	
	
	
	public static void sendMessage(String topicName,String strMes) throws Exception
	{
		ConnectionFactory connectionFactory;//连接工厂  
        Connection connection = null;//连接  
        Session session;//会话  
        Destination destination;//消息的目的地  
        MessageProducer messageProducer;
          
        //实例化连接工厂  
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);  
        //通过连接工厂获取连接  
        try 
        {  
           connection = connectionFactory.createConnection();  
           connection.start();//启动连接  
           //创建session,第一个参数是否有事务  
           session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);  
           //创建Topic主题  
           destination = session.createTopic(topicName);  
           messageProducer = session.createProducer(destination);  
           
           TextMessage message = session.createTextMessage(strMes);
           messageProducer.send(message);
           
           //提交事务  
           session.commit();  
             
        } 
        catch (Exception e) 
        {  
    	   throw  new Exception(e.getMessage());
        }
        finally
        {  
           if(connection != null){  
               try {  
                   connection.close();  
               } catch (JMSException e) {  
                   // TODO Auto-generated catch block  
                   e.printStackTrace();  
               }  
           }  
       }  
	}
	
	
	
	public static void main(String[] args)
	{
		
		try
		{
				String uuid = CommonUtils.getUUID();
				//createTopic(uuid);
				sendMessage("c85db580-8bde-4862-b0ba-d44eac859ea5","hello liujie");
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
				System.out.println(s);
		}
		
		
		
		
	}
}
