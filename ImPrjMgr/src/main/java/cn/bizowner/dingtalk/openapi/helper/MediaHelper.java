package cn.bizowner.dingtalk.openapi.helper;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.RedirectLocations;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cn.bizowner.utils.HttpUtils;

public class MediaHelper {
	
	public static final String TYPE_IMAGE = "image";
	public static final String TYPE_VOICE = "voice";
	public static final String TYPE_VIDEO = "video";
	public static final String TYPE_FILE = "file";
	
	
	/*public static class MediaUploadResult {
		public String type;
		public String media_id;
		public String created_at;
	}
	

	public static UploadResult upload(String accessToken, String type, File file) throws Exception {
		
		MediaService mediaService = ServiceFactory.getInstance().getOpenService(MediaService.class);
		UploadResult uploadResult = mediaService.uploadMediaFile(accessToken, type, file);
		
		return uploadResult;
		String url = Env.OAPI_HOST + "/media/upload?" +
				"access_token=" + accessToken + "&type="  + type;
		JSONObject response = HttpHelper.uploadMedia(url, file);
		if (!response.containsKey("type") || !response.containsKey("media_id") || 
				response.containsKey("created_at")) {
			return JSON.parseObject(response.toJSONString(), MediaUploadResult.class);
		}
		else {
			throw new OApiResultException("type or media_id or create_at");
		}
	}
	
	
	public static void download(String accessToken, String mediaId, String fileDir) throws Exception {
		
		String url = Env.OAPI_HOST + "/media/get?" +
				"access_token=" + accessToken + "&media_id="  + mediaId;
		JSONObject response = HttpHelper.downloadMedia(url, fileDir);
		System.out.println(response);
	}*/
	
	
	
	
	public static JSONObject uploadMedia(String url, String filePath) throws Exception {
        /*HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);

        HttpEntity requestEntity = MultipartEntityBuilder.create().addPart("media",
        		new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
        httpPost.setEntity(requestEntity);

        try {
            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                                   + ", url=" + url);
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");

                JSONObject result = JSON.parseObject(resultStr);
                if (result.getInteger("errcode") == 0) {
                    // 成功
                	result.remove("errcode");
                	result.remove("errmsg");
                    return result;
                } else {
                    System.out.println("request url=" + url + ",return value=");
                    System.out.println(resultStr);
                    int errCode = result.getInteger("errcode");
                    String errMsg = result.getString("errmsg");
                    throw new Exception(errMsg);
                }
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (response != null) try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;*/
		
		
		String str = HttpUtils.doFileUpload(url, null, filePath, "media");
		
		return JSONObject.parseObject(str);
		
    }
	
	
	public static boolean downloadMedia(String url, String fileDir) throws Exception {
        		return HttpUtils.doFileDownload(url, null, fileDir);
    }
}
