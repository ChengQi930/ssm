package cn.bizowner.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.sql.Time;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.util.ResourceUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

	public class WordUtil {
	    //配置信息,代码本身写的还是很可读的,就不过多注解了
	    private static Configuration configuration = null;
	    //这里注意的是利用WordUtils的类加载器动态获得模板文件的位置
	    private static final String templateFolder = WordUtil.class.getResource("/").getPath()+"templates";
	    static {
	        configuration = new Configuration();
	        configuration.setDefaultEncoding("utf-8");
	        try {
	            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	   }
	 
	    private WordUtil() {
	        throw new AssertionError();
	    }
	 
	    public static void exportMillCertificateWord(HttpServletRequest request, HttpServletResponse response, Map map ,String WordName) throws IOException {
	        Template freemarkerTemplate = configuration.getTemplate("QCBG.ftl");  //模板
	        File file = null;
	        InputStream fin = null;
	        ServletOutputStream out = null;
	        try {
	            // 调用工具类的createDoc方法生成Word文档
	            file = createDoc(map,freemarkerTemplate,WordName);
	            fin = new FileInputStream(file);
	 
	            response.setCharacterEncoding("utf-8");
	            response.setContentType("application/msword");   //设置生成多页的word文档标题！
	            
			       //设置文件下载头  
	            String  fileName = WordName+DateCalculate.getproNo()+".doc";
	            response.setHeader("Content-Disposition", "attachment;filename="
	                    .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));
	            out = response.getOutputStream();
	            byte[] buffer = new byte[512];  // 缓冲区
	            int bytesToRead = -1;
	            // 通过循环将读入的Word文件的内容输出到浏览器中
	            while((bytesToRead = fin.read(buffer)) != -1) {
	                out.write(buffer, 0, bytesToRead);
	            }
			}
	        finally {
	            if(fin != null) fin.close();
	            if(out != null) out.close();
	            if(file != null) file.delete(); // 删除临时文件
	        }
	    }
	 
	    private static File createDoc(Map<?, ?> dataMap, Template template,String WordName) {
	        String name =  WordName+".doc";
	        File f = new File(name);
	        Template t = template;
	        try {
	            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
	            Writer w = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
	            t.process(dataMap, w);
	            w.close();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            throw new RuntimeException(ex);
	        }
	        return f;
	    }
	}

