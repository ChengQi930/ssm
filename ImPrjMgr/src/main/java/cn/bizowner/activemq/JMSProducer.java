package cn.bizowner.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import cn.bizowner.activemq.MqHelper;
import cn.bizowner.model.DingDingExamineResult;


/**
 * 
 * 生产消息
 * @author Administrator
 *
 */
public class JMSProducer {
	private static final String USERNAME = "admin";  
    private static final String PASSWORD = "admin";
    private static final String BROKEURL = "tcp://192.168.1.63:61616"; 
    
   public static void main(String topicName,String Result) {
	   ConnectionFactory connectionFactory;//连接工厂  
       Connection connection = null;//连接  
       Session session;//会话  
       Destination destination;//消息的目的地  
       MessageProducer messageProducer;// 消息生产者
       
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
          //创建消息队列
          /*destination = session.createQueue("FirstTopic");*/
           destination = session.createTopic(topicName);
          //创建消息的发送者
          messageProducer = session.createProducer(destination); 
          
          sendMessages(session, messageProducer,Result); // 发送消息
          //提交事务  
          session.commit();  
      } 
      catch (Exception e) 
      {  
          e.getMessage();
      }
       finally
       {  
          if(connection != null){  
              try {  
                  connection.close();  
              } catch (JMSException e) {  
                  e.printStackTrace();  
              }  
          }
      }
}
   /**
    * 发送消息
    * @param session 会话
    * @param messageProducer 消息生产者
    */
	private static void sendMessages(Session session, MessageProducer messageProducer,String Result) throws JMSException {
		
           TextMessage message = session.createTextMessage(Result);
           messageProducer.send(message);
       
   }
}
