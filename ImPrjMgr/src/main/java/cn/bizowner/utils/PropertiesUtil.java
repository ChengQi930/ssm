package cn.bizowner.utils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * 读写配置文件
 */
public class PropertiesUtil {

	public static Properties prop = new Properties();
   
	 /**
     * 取出值
     * @param k
     * @param filepath
     * @return
     */

    public static String getValue(String k, String filepath){
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filepath));
            prop.load(in);     ///加载属性列表
             Iterator<String> it=prop.stringPropertyNames().iterator();
             while(it.hasNext()){
                 String key=it.next();
                 if (key.equals(k)){
                     return prop.getProperty(key);
                 }
             }
             in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
        		if(in != null)
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        }
        return "";
    }
    /**
     * 设置键值
     * @param filepath
     * @param map 
     */
    public static void setValue(String filepath,String parameterName,String parameterValue){
    	///保存属性到文件
        FileOutputStream oFile =null;
        try {
            System.out.println("s"+filepath);
            oFile = new FileOutputStream(filepath);
            prop.setProperty(parameterName, parameterValue);
            prop.store(oFile, null);
            oFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
        		if(oFile != null)
					try {
						oFile.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        }
    }
}
