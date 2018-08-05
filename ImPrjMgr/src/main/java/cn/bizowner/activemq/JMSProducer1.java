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
  
/** 
 * ��Ϣ������ 
 *  
 * ��������ģʽ 
 * @author admin 
 * 
 */  
public class JMSProducer1 {  
    
	
	//private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;  
    //private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; 
    
    private static final String USERNAME = "admin";  
    private static final String PASSWORD = "admin";
    private static final String BROKEURL = "tcp://192.168.1.23:61616";  
      
    private static final int SENDNUM = 1;//���͵���Ϣ����  
      
      
    public static void main(String[] args) {  
         ConnectionFactory connectionFactory;//���ӹ���  
         Connection connection = null;//����  
         Session session;//�Ự  
         Destination destination;//��Ϣ��Ŀ�ĵ�  
         MessageProducer messageProducer;//��Ϣ������  
         
         
         System.out.println("USERNAME:"+USERNAME);
         System.out.println("PASSWORD:"+PASSWORD);
         System.out.println("BROKEURL:"+BROKEURL);
           
         //ʵ�������ӹ���  
         connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);  
         //ͨ�����ӹ�����ȡ����  
         try {  
            connection = connectionFactory.createConnection();  
            connection.start();//��������  
            //����session,��һ�������Ƿ�������  
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);  
            //����Topic����  
            destination = session.createTopic("FirstTopic2");  
            //������Ϣ������  
            messageProducer = session.createProducer(destination);  
              
            //sendMessage(session,messageProducer);  
              
            //�ύ����  
            session.commit();  
              
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
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
      
    /** 
     * ������Ϣ 
     * @throws Exception  
     */  
    public static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{  
        for (int i = 0; i < JMSProducer1.SENDNUM; i++) {  
            TextMessage message = session.createTextMessage("ActiveMQ ������������Ϣ"+i);  
            System.out.println("��������Ϣ��"+ "ActiveMQ ������������Ϣ"+i);  
            messageProducer.send(message);  
        }  
    }  
  
}  
