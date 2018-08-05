package cn.bizowner.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * @ClassName: JSONHelper 
 * @Description: TODO(JSON工具类) 
 * @author hong_xuanguo
 * @date 2015年10月13日 下午4:51:31 
 *
 */
public class JSONHelper {
	
	//来自apache的common-logging的包  也是日志
	private static Log log = LogFactory.getLog(JSONHelper.class);
	
	/**
	 * 将不含日期时间格式的Java对象系列化为Json资料格式
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject) {
		String jsonString = "[]";
		if (CommonUtils.isEmpty(pObject)) {
			log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSON.parseArray(JSON.toJSONString(pObject));
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(pObject));
				jsonString = jsonObject.toString();
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	
	
	
	
	
	public static final String encodeObject2JsonWithNull(Object pObject) {
		String jsonString = "[]";
		if (CommonUtils.isEmpty(pObject)) {
			log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			if (pObject instanceof ArrayList) {
				jsonString = JSON.toJSONString(pObject);  
			} else {
				jsonString = JSON.toJSONString(pObject, SerializerFeature.WriteMapNullValue);  
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	
	
	
	
	
	
	
	/**
	 * 将含有日期时间格式的Java对象系列化为Json资料格式<br>
	 * Json-Lib在处理日期时间格式是需要实现其JsonValueProcessor接口,所以在这里提供一个重载的方法对含有<br>
	 * 日期时间格式的Java对象进行序列化
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject, String pFormatString) {
		String jsonString = "[]";
		if (CommonUtils.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
//			JsonConfig cfg = new JsonConfig();
			SerializeConfig mapping = new SerializeConfig(); 
//			cfg.registerJsonValueProcessor(java.sql.Timestamp.class, new SimpleDateFormatSerializer(pFormatString));
//			cfg.registerJsonValueProcessor(java.util.Date.class, new SimpleDateFormatSerializer(pFormatString));
//			cfg.registerJsonValueProcessor(java.sql.Date.class, new SimpleDateFormatSerializer(pFormatString));
			mapping.put(java.util.Date.class, new SimpleDateFormatSerializer(pFormatString)); 
			mapping.put(java.sql.Date.class, new SimpleDateFormatSerializer(pFormatString));
			mapping.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer(pFormatString));
			if (pObject instanceof ArrayList) {
//				JSONArray jsonArray = JSONArray.(pObject, mapping);
//				jsonString = jsonArray.toString();
				jsonString = JSON.toJSONString(pObject, mapping,SerializerFeature.WriteMapNullValue);  
			} else {
//				JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
//				jsonString = jsonObject.toString();
				jsonString = JSON.toJSONString(pObject, mapping,SerializerFeature.WriteMapNullValue);  
			}
		}
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}
	
	public static final String encodeList2PageJson(List list, String dataFormat) {
		String subJsonString = "";
		if (CommonUtils.isEmpty(dataFormat)) {
			subJsonString = encodeObject2Json(list);
		} else {
			subJsonString = encodeObject2Json(list, dataFormat);
		}
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + subJsonString);
		}
		return subJsonString;
	}
	
	/**
	 * 将单一Json对象解析为DTOJava对象
	 * 
	 * @param jsonString
	 *            简单的Json对象
	 * @return T
	 */
	public static <T> T parseSingleJson2Object(String jsonString, Class<T> cls) {
		T t = null;
		if (CommonUtils.isEmpty(jsonString)) {
			return t;
		}
        try {
            t = (T) JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
        	log.warn("Json序列化错误.请检查!");
        }
        return t;
	}
	
	/**
	 * 将Json对象解析为List对象
	 * 
	 * @param jsonString
	 * @return List
	 */
	public static List<JSONObject> parseSingleJson2List(String jsonString) {
		List<JSONObject> objs = new ArrayList<JSONObject>();
		if (CommonUtils.isEmpty(jsonString)) {
			return objs;
		}
		JSONArray jsonAry = JSON.parseArray(jsonString);
		if (jsonAry.size() > 0) {
			for (int i = 0;i < jsonAry.size(); i ++) {
				JSONObject jsonObj = jsonAry.getJSONObject(i);
				objs.add(jsonObj);
			}
		}
        return objs;
	}
	
	public static void main(String[] args) {
//		String json = "[{'lng':108.900923,'lat':34.251248},{'lng':108.900923,'lat':34.251248}]";
//		List objs = JSONHelper.parseSingleJson2List(json);
//		System.out.println(objs.size()+"<<<<<<<<===========");
		String json = "{'name':'test','creTime':1466240990000}";
		List lst = new ArrayList();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", "test");
		jsonObj.put("creTime", new Date());
		lst.add(jsonObj);
		String outStr = JSONHelper.encodeList2PageJson(lst, "yyyy-MM-dd HH:mm:ss");
		System.out.println(outStr);
	}

}
