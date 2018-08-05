package cn.bizowner.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public class ExcelUtils {
	
    public static int DEFAULT_COLOUMN_WIDTH = 17;
    
	
	public static JSONObject excel2json(String filePath){
		
		
		JSONObject json = null;
		
		 Workbook wb = null;
		
		try
		{
			// Excel�е���ʽ����Ҫ��Ϊ�˽��Excel���ֿ�ѧ���������
	        CellStyle cellStyle;
	        // ���Excel���ɵĶ���
	       
	        // �����2007�����ϰ汾����ʹ����Ҫ��Workbook�Լ�CellStyle
	        
	        File file = new File(filePath);
	        
	        if(file.getName().endsWith("xlsx")){
	            //System.out.println("��2007�����ϰ汾  xlsx");
	            wb = new XSSFWorkbook(file);
	            XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
	            cellStyle = wb.createCellStyle();
	            // ����Excel�е���ʽΪ�ı�
	            cellStyle.setDataFormat(dataFormat.getFormat("@"));
	        }else{
	            System.out.println("��2007���°汾  xls");
	            POIFSFileSystem fs = new POIFSFileSystem(file);
	            wb = new HSSFWorkbook(fs);
	            HSSFDataFormat dataFormat = (HSSFDataFormat) wb.createDataFormat();
	            cellStyle = wb.createCellStyle();
	            // ����Excel�е���ʽΪ�ı�
	            cellStyle.setDataFormat(dataFormat.getFormat("@"));
	        }

	        // sheet�����
	        int sheetsCounts = wb.getNumberOfSheets();
	        
	        List<Map<String, String>> listContent = new ArrayList<Map<String,String>>();
    
	        // ����ÿһ��sheet
	        
	        String[] cellNames={};
	        
	        for (int i = 0; i < sheetsCounts; i++) {
	            Sheet sheet = wb.getSheetAt(i);

	            // ����һ�е���ֵ��Ϊ���json��key
    
	            if(i == 0)
	            {
	            	// ȡ��һ���е�ֵ��Ϊkey
	                Row fisrtRow = sheet.getRow(0);
	                // ����һ�о�Ϊ�գ����ǿ�sheet�?�ñ����
	                if(null == fisrtRow){
	                    continue;
	                }
	                // �õ���һ���ж�����
	                int curCellNum = fisrtRow.getLastCellNum();
	                // ��ݵ�һ�е������������ͷ����
	                cellNames = new String[curCellNum];
	                // ���������һ�У�ȡ����һ�е�ÿ����ֵ���������У��͵õ������ű��JSON��key
	                for (int m = 0; m < curCellNum; m++) {
	                    Cell cell = fisrtRow.getCell(m);
	                    // ���ø��е���ʽ���ַ�
	                    cell.setCellStyle(cellStyle);
	                    cell.setCellType(Cell.CELL_TYPE_STRING);
	                    // ȡ�ø��е��ַ�ֵ
	                    cellNames[m] = cell.getStringCellValue();
	                }
	            }
	            
	            // �ӵڶ��������ÿһ��
	            int rowNum = sheet.getLastRowNum();
	            
	            for (int j = 1; j <= rowNum; j++) {
	            	
	                Map<String,String> map = new LinkedHashMap<String,String>();

	                // ȡ��ĳһ��
	                Row row = sheet.getRow(j);
	                int cellNum = row.getLastCellNum();
	                // ����ÿһ��
	                for (int k = 0; k < cellNum; k++) {
	                    Cell cell = row.getCell(k);

	                    cell.setCellStyle(cellStyle);
	                    cell.setCellType(Cell.CELL_TYPE_STRING);
	                    // ����õ�Ԫ�����ݵ�������
	                    map.put(cellNames[k],cell.getStringCellValue());
	                }
	                // ������е���ݵ��ñ��List��
	                listContent.add(map);
	            }
	 
	        }
	        
	        String headStr=StringUtils.join(cellNames, ",");
	        	
	        Map<String, Object> excelMap= new LinkedHashMap<String, Object>();
	        excelMap.put("head", headStr);
	        excelMap.put("content", listContent);
			
			json = JSONObject.parseObject(JSON.toJSONString(excelMap));
			
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
		}
		finally
		{
				if(wb != null)
					try {
						wb.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		
		return json;
        
    }
	
	
	
	
	
	
	
	
	
	
	public static JSONObject newExcel2json(String filePath) throws Exception{
		
		
		JSONObject json = null;
		
		 Workbook wb = null;
		
		try
		{
		        CellStyle cellStyle;
		       	        
		        File file = new File(filePath);
		        
		        if(file.getName().endsWith("xlsx")){
		            wb = new XSSFWorkbook(file);
		            XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
		            cellStyle = wb.createCellStyle();
		            cellStyle.setDataFormat(dataFormat.getFormat("@"));
		        }else{
		            POIFSFileSystem fs = new POIFSFileSystem(file);
		            wb = new HSSFWorkbook(fs);
		            HSSFDataFormat dataFormat = (HSSFDataFormat) wb.createDataFormat();
		            cellStyle = wb.createCellStyle();
		            cellStyle.setDataFormat(dataFormat.getFormat("@"));
		        }

		        int sheetsCounts = wb.getNumberOfSheets();
		        
		        
		        Map<String,String> headMap = new LinkedHashMap<String,String>();
		        
		        List<Map<String, String>> listContent = new ArrayList<Map<String,String>>();
	            
		        String[] cellNames={};
		        
		        for (int i = 0; i < sheetsCounts; i++) {
		            Sheet sheet = wb.getSheetAt(i);

	    
		            if(i == 0)
		            {
		                Row fisrtRow = sheet.getRow(0);
		                if(null == fisrtRow){
		                    continue;
		                }
		                int curCellNum = fisrtRow.getLastCellNum();
		                cellNames = new String[curCellNum];
		                for (int m = 0; m < curCellNum; m++) {
		                    Cell cell = fisrtRow.getCell(m);
		                    cell.setCellStyle(cellStyle);
		                    cell.setCellType(Cell.CELL_TYPE_STRING);
		                    cellNames[m] = cell.getStringCellValue();
		                    
		                    String key = "col"+(m+1);
		                    headMap.put(key, cell.getStringCellValue());
		                }
		            }
		            
		            int rowNum = sheet.getLastRowNum();
		            
		            for (int j = 1; j <= rowNum; j++) {
		            	
		                Map<String,String> map = new LinkedHashMap<String,String>();

		                Row row = sheet.getRow(j);
		                if(row != null)
		                {
		                		int cellNum = row.getLastCellNum();
				                for (int k = 0; k < cellNum; k++) {
				                    Cell cell = row.getCell(k);
				                    
				                    String key = "col"+(k+1);
				
				                    String value = "";
				                    if(cell != null)
				                    {
				                    		value = parseExcel(cell);
				                    }
				                    
				                	//String value = parseExcel(cell);
				                    map.put(key, value);
				                }
				                listContent.add(map);
		                }
		               
		            }
		 
		        }
		        	        	
		        Map<String, Object> excelMap= new LinkedHashMap<String, Object>();
		        excelMap.put("head", headMap);
		        excelMap.put("content", listContent);
				
				json = JSONObject.parseObject(JSON.toJSONString(excelMap));
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
				ex.printStackTrace();
				throw new Exception(s);
		}
		finally
		{
				if(wb != null)
					try {
						wb.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		
		return json;
        
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static JSONObject newExcel2json_cx(String filePath) throws Exception{
		
		
		JSONObject json = null;
		
		 Workbook wb = null;
		
		try
		{
		        CellStyle cellStyle;
		       	        
		        File file = new File(filePath);
		        
		        if(file.getName().endsWith("xlsx")){
		            wb = new XSSFWorkbook(file);
		            XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
		            cellStyle = wb.createCellStyle();
		            cellStyle.setDataFormat(dataFormat.getFormat("@"));
		        }else{
		            POIFSFileSystem fs = new POIFSFileSystem(file);
		            wb = new HSSFWorkbook(fs);
		            HSSFDataFormat dataFormat = (HSSFDataFormat) wb.createDataFormat();
		            cellStyle = wb.createCellStyle();
		            cellStyle.setDataFormat(dataFormat.getFormat("@"));
		        }

		        int sheetsCounts = wb.getNumberOfSheets();
		        
		        
		        Map<String,String> headMap = new LinkedHashMap<String,String>();
		        
		        List<Map<String, String>> listContent = new ArrayList<Map<String,String>>();
	            
		        String[] cellNames={};
		        
		        for (int i = 0; i < sheetsCounts; i++) {
		            Sheet sheet = wb.getSheetAt(i);

	    
		            if(i == 0)
		            {
		                Row fisrtRow = sheet.getRow(0);
		                if(null == fisrtRow){
		                    continue;
		                }
		                int curCellNum = fisrtRow.getLastCellNum();
		                cellNames = new String[curCellNum];
		                for (int m = 0; m < curCellNum; m++) {
		                    Cell cell = fisrtRow.getCell(m);
		                    cell.setCellStyle(cellStyle);
		                    cell.setCellType(Cell.CELL_TYPE_STRING);
		                    cellNames[m] = cell.getStringCellValue();
		                    
		                    String key = "col"+(m+1);
		                    headMap.put(key, cell.getStringCellValue());
		                }
		            }
		            
		            int rowNum = sheet.getLastRowNum();
		            
		            for (int j = 1; j <= rowNum; j++) {
		            	
		                Map<String,String> map = new LinkedHashMap<String,String>();

		                Row row = sheet.getRow(j);
		                int cellNum = row.getLastCellNum();
		                for (int k = 0; k < cellNum; k++) {
		                    Cell cell = row.getCell(k);
		                    
		                    String key = "col"+(k+1);
		
		                    String value = "";
		                    if(cell != null)
		                    {
		                    	
		                    		cell.setCellStyle(cellStyle);
		                    		cell.setCellType(Cell.CELL_TYPE_STRING);
		                    		value = cell.getStringCellValue();
		                    		//value = parseExcel(cell);
		                    }
		                    
		                	//String value = parseExcel(cell);
		                    map.put(key, value);
		                }
		                listContent.add(map);
		            }
		 
		        }
		        	        	
		        Map<String, Object> excelMap= new LinkedHashMap<String, Object>();
		        excelMap.put("head", headMap);
		        excelMap.put("content", listContent);
				
				json = JSONObject.parseObject(JSON.toJSONString(excelMap));
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
				throw new Exception(s);
		}
		finally
		{
				if(wb != null)
					try {
						wb.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		
		return json;
        
    }
	
	
	
	
	
	
	
	
	private static String parseExcel(Cell cell) {  
        String result = new String();  
        switch (cell.getCellType()) {  
        case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型  
            if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
                SimpleDateFormat sdf = null;  
                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
                        .getBuiltinFormat("h:mm")) {  
                    sdf = new SimpleDateFormat("HH:mm");  
                } else {// 日期  
                    sdf = new SimpleDateFormat("yyyy-MM-dd");  
                }  
                Date date = cell.getDateCellValue();  
                result = sdf.format(date);  
            } else if (cell.getCellStyle().getDataFormat() == 58) {  
                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                double value = cell.getNumericCellValue();  
                Date date = org.apache.poi.ss.usermodel.DateUtil  
                        .getJavaDate(value);  
                result = sdf.format(date);  
            } else {  
                /*double value = cell.getNumericCellValue();  
                CellStyle style = cell.getCellStyle();  
                DecimalFormat format = new DecimalFormat();  
                String temp = style.getDataFormatString();  
                // 单元格设置成常规  
                if (temp.equals("General")) {  
                    format.applyPattern("#");  
                }  
                result = format.format(value);		//读出的数据是180,000.123   
*/            	
            	double value  = cell.getNumericCellValue();  
            	result = new DecimalFormat("#.######").format(cell.getNumericCellValue());	//读出的数据是180000.123
            	
            	/*Double d = cell.getNumericCellValue();  
            	DecimalFormat df = new DecimalFormat("#.##");  
            	result = df.format(d); */ 
            }  
            break;  
        case HSSFCell.CELL_TYPE_STRING:// String类型  
            result = cell.getRichStringCellValue().toString();  
            break;  
        case HSSFCell.CELL_TYPE_BLANK:  
            result = "";  
        default:  
            result = "";  
            break;  
        }  
        return result;  
    }
	
	
	
	
	
	
	
	
	
	
	
	
public static JSONObject newExcelX2json(String filePath){
		
		
	JSONObject json = null;
	
	XSSFWorkbook wb = null;
	
	try
	{
		XSSFCellStyle cellStyle;
        
        File file = new File(filePath);
        
        wb = new XSSFWorkbook(file);
      
        XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
        cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(dataFormat.getFormat("@"));
       

        int sheetsCounts = wb.getNumberOfSheets();
        
        
        
        Map<String,String> headMap = new LinkedHashMap<String,String>();
        
        List<Map<String, String>> listContent = new ArrayList<Map<String,String>>();

        
        String[] cellNames={};
        
        for (int i = 0; i < sheetsCounts; i++) {
        	XSSFSheet sheet = wb.getSheetAt(i);

            // ����һ�е���ֵ��Ϊ���json��key

            if(i == 0)
            {
            	// ȡ��һ���е�ֵ��Ϊkey
                //Row fisrtRow = sheet.getRow(0);
                XSSFRow fisrtRow = sheet.getRow(0);
                // ����һ�о�Ϊ�գ����ǿ�sheet�?�ñ����
                if(null == fisrtRow){
                    continue;
                }
                // �õ���һ���ж�����
                int curCellNum = fisrtRow.getLastCellNum();
                // ��ݵ�һ�е������������ͷ����
                cellNames = new String[curCellNum];
                // ���������һ�У�ȡ����һ�е�ÿ����ֵ���������У��͵õ������ű��JSON��key
                for (int m = 0; m < curCellNum; m++) {
                	Cell cell = fisrtRow.getCell(m);
                     
                    // ���ø��е���ʽ���ַ�
                    cell.setCellStyle(cellStyle);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    // ȡ�ø��е��ַ�ֵ
                    //cellNames[m] = cell.getStringCellValue();
                    
                    String key = "col"+(m+1);
                    headMap.put(key, cell.getStringCellValue());
                    
                }
                
            }
            
            // �ӵڶ��������ÿһ��
            int rowNum = sheet.getLastRowNum();
            
            for (int j = 1; j <= rowNum; j++) {
            	
                Map<String,String> map = new LinkedHashMap<String,String>();

                // ȡ��ĳһ��
                XSSFRow row = sheet.getRow(j);
                if(row != null)
                {
                	int cellNum = row.getLastCellNum();
                    // ����ÿһ��
                    for (int k = 0; k < cellNum; k++) {
                        
                        Cell cell = row.getCell(k);
                        
                        String key = "col"+(k+1);

                    	String value = parseExcel(cell);
                                            
                        map.put(key, value);
                    }
                    // ������е���ݵ��ñ��List��
                    listContent.add(map);
                }
                
            }
 
        }
        
        	
        Map<String, Object> excelMap= new LinkedHashMap<String, Object>();
        excelMap.put("head", headMap);
        excelMap.put("content", listContent);
		
		json = JSONObject.parseObject(JSON.toJSONString(excelMap));
		
	}
	catch(Exception ex)
	{
			String s = ex.getMessage();
	}
	finally
	{
			if(wb != null)
				try {
					wb.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	return json;
        
    }
	
	
	
	
	
	
	
	
	
	public static JSONObject excelX2json(String filePath){
		
		
		JSONObject json = null;
		
		XSSFWorkbook wb = null;
		
		try
		{
			// Excel�е���ʽ����Ҫ��Ϊ�˽��Excel���ֿ�ѧ���������
			XSSFCellStyle cellStyle;
	        // ���Excel���ɵĶ���
	       
	        // �����2007�����ϰ汾����ʹ����Ҫ��Workbook�Լ�CellStyle
	        
	        File file = new File(filePath);
	        
            wb = new XSSFWorkbook(file);
           /* XSSFDataFormat dataFormat = (XSSFDataFormat) wb.createDataFormat();
            cellStyle = wb.createCellStyle();
            // ����Excel�е���ʽΪ�ı�
            cellStyle.setDataFormat(dataFormat.getFormat("@"));*/
	       

	        // sheet�����
	        int sheetsCounts = wb.getNumberOfSheets();
	        
	        List<Map<String, String>> listContent = new ArrayList<Map<String,String>>();
    
	        // ����ÿһ��sheet
	        
	        String[] cellNames={};
	        
	        for (int i = 0; i < sheetsCounts; i++) {
	        	XSSFSheet sheet = wb.getSheetAt(i);

	            // ����һ�е���ֵ��Ϊ���json��key
    
	            if(i == 0)
	            {
	            	// ȡ��һ���е�ֵ��Ϊkey
	                //Row fisrtRow = sheet.getRow(0);
	                XSSFRow fisrtRow = sheet.getRow(0);
	                // ����һ�о�Ϊ�գ����ǿ�sheet�?�ñ����
	                if(null == fisrtRow){
	                    continue;
	                }
	                // �õ���һ���ж�����
	                int curCellNum = fisrtRow.getLastCellNum();
	                // ��ݵ�һ�е������������ͷ����
	                cellNames = new String[curCellNum];
	                // ���������һ�У�ȡ����һ�е�ÿ����ֵ���������У��͵õ������ű��JSON��key
	                for (int m = 0; m < curCellNum; m++) {
	                	Cell cell = fisrtRow.getCell(m);
	                     
	                    // ���ø��е���ʽ���ַ�
	                    //cell.setCellStyle(cellStyle);
	                    //cell.setCellType(Cell.CELL_TYPE_STRING);
	                    // ȡ�ø��е��ַ�ֵ
	                    cellNames[m] = cell.getStringCellValue();
	                }
	                
	            }
	            
	            // �ӵڶ��������ÿһ��
	            int rowNum = sheet.getLastRowNum();
	            
	            for (int j = 1; j <= rowNum; j++) {
	            	
	                Map<String,String> map = new LinkedHashMap<String,String>();

	                // ȡ��ĳһ��
	                XSSFRow row = sheet.getRow(j);
	                int cellNum = row.getLastCellNum();
	                // ����ÿһ��
	                for (int k = 0; k < cellNum; k++) {
	                	Cell cell = row.getCell(k);

	                    //cell.setCellStyle(cellStyle);
	                    //cell.setCellType(Cell.CELL_TYPE_STRING);
	                    // ����õ�Ԫ�����ݵ�������
	                    map.put(cellNames[k],cell.getStringCellValue());
	                }
	                // ������е���ݵ��ñ��List��
	                listContent.add(map);
	            }
	 
	        }
	        
	        String headStr=StringUtils.join(cellNames, ",");
	        	
	        Map<String, Object> excelMap= new LinkedHashMap<String, Object>();
	        excelMap.put("head", headStr);
	        excelMap.put("content", listContent);
			
			json = JSONObject.parseObject(JSON.toJSONString(excelMap));
			
		}
		catch(Exception ex)
		{
				String s = ex.getMessage();
		}
		finally
		{
				if(wb != null)
					try {
						wb.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}
		
		return json;
        
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
     * ����Excel 97(.xls)��ʽ ���������
     * @param title ������ 
     * @param headMap ����-����
     * @param jsonArray ��ݼ�
     * @param datePattern ���ڸ�ʽ��null����Ĭ�����ڸ�ʽ
     * @param colWidth �п� Ĭ�� ����17���ֽ�
     * @param out �����
     */
    public static void export2Excel(Map<String, String> headMap,JSONArray jaContent,String filePath) {

    	
    	OutputStream outXls = null;
    	HSSFWorkbook workbook = null;
    	
    	try
    	{
    		outXls = new FileOutputStream(filePath);

            workbook = new HSSFWorkbook();
            
            
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 20);
            titleFont.setBoldweight((short) 700);
            titleStyle.setFont(titleFont);
           
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            //headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	
            HSSFFont headerFont = workbook.createFont();
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	
            headerStyle.setFont(headerFont);
            
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            //cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            HSSFFont cellFont = workbook.createFont();
            cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            cellStyle.setFont(cellFont);
         
            HSSFSheet sheet = workbook.createSheet();
            
            
            int minBytes = DEFAULT_COLOUMN_WIDTH;
            int[] arrColWidth = new int[headMap.size()];
            
            String[] properties = new String[headMap.size()];
            String[] headers = new String[headMap.size()];
            int ii = 0;
            for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();) 
            {
                String fieldName = iter.next();

                properties[ii] = fieldName;
                headers[ii] = headMap.get(fieldName);

                int bytes = fieldName.getBytes().length;
                arrColWidth[ii] =  bytes < minBytes ? minBytes : bytes;
                sheet.setColumnWidth(ii,arrColWidth[ii]*256);
                ii++;
            }
            
            
            
            
            int rowIndex = 0;
            
            if(jaContent.size() == 0)			//如果内容是空的,只写入列头
            {
	            	HSSFRow headerRow = sheet.createRow(0);
	                for(int i=0;i<headers.length;i++)
	                {
	                    headerRow.createCell(i).setCellValue(headers[i]);
	                    headerRow.getCell(i).setCellStyle(headerStyle);
	
	                }
            }
            else		//如果内容不为空,以65535为单位进行分页写
            {
	            	for (Object obj : jaContent) {
	                    if(rowIndex == 65535 || rowIndex == 0){
	                        if ( rowIndex != 0 ) sheet = workbook.createSheet();
	
	                       
	
	                        HSSFRow headerRow = sheet.createRow(0);
	                        for(int i=0;i<headers.length;i++)
	                        {
	                            headerRow.createCell(i).setCellValue(headers[i]);
	                            headerRow.getCell(i).setCellStyle(headerStyle);
	
	                        }
	                        rowIndex = 1;
	                    }
	                    JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
	                    HSSFRow dataRow = sheet.createRow(rowIndex);
	                    for (int i = 0; i < properties.length; i++)
	                    {
	                        HSSFCell newCell = dataRow.createCell(i);
	
	                        Object o =  jo.get(properties[i]);
	                        String cellValue = "";
	                        if(o!=null)
	                        {
	                        	cellValue = o.toString();
	                        }
	                        newCell.setCellValue(cellValue);
	                        newCell.setCellStyle(cellStyle);
	                    }
	                    rowIndex++;
	                }
            }
            
            
            
            workbook.write(outXls);
    	}
    	catch(Exception ex)
    	{
    			String s = ex.getMessage();
    	}
    	finally
    	{
    			try
    			{
    				if(workbook != null)	workbook.close();
        			if(outXls != null)		outXls.close();
    			}
    			catch(Exception ex){}
    	}
    	
    	
    }
    /**
     * ����Excel 2007 OOXML (.xlsx)��ʽ
     * @param title ������
     * @param headMap ����-��ͷ
     * @param jsonArray ��ݼ�
     * @param datePattern ���ڸ�ʽ����nullֵ��Ĭ�� ������
     * @param colWidth �п� Ĭ�� ����17���ֽ�
     * @param out �����
     */
    public static void export2ExcelX(Map<String, String> headMap,JSONArray jaContent,String filePath) {
        
    	
    	OutputStream outXls = null;
    	SXSSFWorkbook workbook = null;
    	
    	try
    	{
    		outXls = new FileOutputStream(filePath);
    		
    		// ����һ��������
            workbook = new SXSSFWorkbook();//����
            //workbook.setCompressTempFiles(true);
             //��ͷ��ʽ
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Font titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 20);
            titleFont.setBoldweight((short) 700);
            titleStyle.setFont(titleFont);
            // ��ͷ��ʽ
            CellStyle headerStyle = workbook.createCellStyle();
            //headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            Font headerFont = workbook.createFont();
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headerStyle.setFont(headerFont);
            // ��Ԫ����ʽ
            CellStyle cellStyle = workbook.createCellStyle();
            //cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            Font cellFont = workbook.createFont();
            cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            cellStyle.setFont(cellFont);
            // ���һ��(�����)���
            SXSSFSheet sheet = workbook.createSheet();
            
            int minBytes = DEFAULT_COLOUMN_WIDTH;//�����ֽ���
            int[] arrColWidth = new int[headMap.size()];
            
            String[] properties = new String[headMap.size()];
            String[] headers = new String[headMap.size()];
            int ii = 0;
            for (Iterator<String> iter = headMap.keySet().iterator(); iter.hasNext();) 
            {
                String fieldName = iter.next();

                properties[ii] = fieldName;
                headers[ii] = headMap.get(fieldName);

                int bytes = fieldName.getBytes().length;
                arrColWidth[ii] =  bytes < minBytes ? minBytes : bytes;
                sheet.setColumnWidth(ii,arrColWidth[ii]*256);
                ii++;
            }
            
            
            // �������ݣ����������
            int rowIndex = 0;
            for (Object obj : jaContent) {
                if(rowIndex == 65535 || rowIndex == 0){
                    if ( rowIndex != 0 ) sheet = workbook.createSheet();//�����ݳ����ˣ����ڵڶ�ҳ��ʾ


                    SXSSFRow headerRow = sheet.createRow(0); //��ͷ rowIndex =0
                    for(int i=0;i<headers.length;i++)
                    {
                        headerRow.createCell(i).setCellValue(headers[i]);
                        headerRow.getCell(i).setCellStyle(headerStyle);

                    }
                    rowIndex = 1;//������ݴ� rowIndex=2��ʼ
                }
                JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
                SXSSFRow dataRow = sheet.createRow(rowIndex);
                for (int i = 0; i < properties.length; i++)
                {
                    SXSSFCell newCell = dataRow.createCell(i);

                    Object o =  jo.get(properties[i]);
                    String cellValue = o.toString();
                    newCell.setCellValue(cellValue);
                    newCell.setCellStyle(cellStyle);
                }
                rowIndex++;
            }
            
            workbook.write(outXls);
    	}
    	catch(Exception ex)
    	{
    			String s = ex.getMessage();
    	}
    	finally
    	{
    			try
    			{
    				if(workbook != null)	workbook.close();
        			if(outXls != null)		outXls.close();
    			}
    			catch(Exception ex){}
    	}
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void exportDiffAccount(Map<String, String> unUpHeadMap,JSONArray unUpJaContent,Map<String, String> changeHeadMap,JSONArray changeJaContent,Map<String, String> downHeadMap,JSONArray downJaContent,String filePath) {

    	
    	OutputStream outXls = null;
    	HSSFWorkbook workbook = null;
    	
    	try
    	{
    		outXls = new FileOutputStream(filePath);

            workbook = new HSSFWorkbook();
            
            
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont titleFont = workbook.createFont();
            titleFont.setFontHeightInPoints((short) 20);
            titleFont.setBoldweight((short) 700);
            titleStyle.setFont(titleFont);
           
            HSSFCellStyle headerStyle = workbook.createCellStyle();
            
            //设置边框
            headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            
            //水平居中
            headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	
            
            
            HSSFFont headerFont = workbook.createFont();
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	
            headerStyle.setFont(headerFont);
            
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            HSSFFont cellFont = workbook.createFont();
            cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            cellStyle.setFont(cellFont);
            
            
            
            
            HSSFCellStyle remarkCellStyle = workbook.createCellStyle();
            HSSFFont remarkCellFont = workbook.createFont();
            remarkCellFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            remarkCellFont.setColor(HSSFColor.RED.index);
            remarkCellStyle.setFont(remarkCellFont);
            
            
         
            HSSFSheet sheet = workbook.createSheet();
            
            
            
            int minBytes = DEFAULT_COLOUMN_WIDTH;
            
            
            int[] arrColWidth = new int[unUpHeadMap.size()];
            
            String[] properties = new String[unUpHeadMap.size()];
            String[] headers = new String[unUpHeadMap.size()];
            int ii = 0;
            for (Iterator<String> iter = unUpHeadMap.keySet().iterator(); iter.hasNext();) 
            {
                String fieldName = iter.next();

                properties[ii] = fieldName;
                headers[ii] = unUpHeadMap.get(fieldName);

                int bytes = fieldName.getBytes().length;
                arrColWidth[ii] =  bytes < minBytes ? minBytes : bytes;
                sheet.setColumnWidth(ii,arrColWidth[ii]*256);
                ii++;
            }
            
            
            
            int rowIndex = 0;

        	HSSFRow headerRow = sheet.createRow(0);
            for(int i=0;i<headers.length;i++)
            {
                headerRow.createCell(i).setCellValue(headers[i]);
                headerRow.getCell(i).setCellStyle(headerStyle);

            }
            rowIndex++;

        	for (Object obj : unUpJaContent) {
                
                JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
                HSSFRow dataRow = sheet.createRow(rowIndex);
                for (int i = 0; i < properties.length; i++)
                {
                    HSSFCell newCell = dataRow.createCell(i);

                    Object o =  jo.get(properties[i]);
                    String cellValue = "";
                    if(o!=null)
                    {
                    	cellValue = o.toString();
                    }
                    newCell.setCellValue(cellValue);
                    newCell.setCellStyle(cellStyle);
                }
                rowIndex++;
            }
            
            
            
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,0,2));  
            HSSFRow dataRowRemark = sheet.createRow(rowIndex);
            Cell remarkCell = dataRowRemark.createCell(0);
            remarkCell.setCellValue("注意:该资产未上账,请在久其进行上账");
            remarkCell.setCellStyle(remarkCellStyle);
           
            
            
            
            
            
            
            
            
            sheet = workbook.createSheet();
        
            arrColWidth = new int[changeHeadMap.size()];
            
            properties = new String[changeHeadMap.size()];
            headers = new String[changeHeadMap.size()];
            ii = 0;
            for (Iterator<String> iter = changeHeadMap.keySet().iterator(); iter.hasNext();) 
            {
                String fieldName = iter.next();

                properties[ii] = fieldName;
                headers[ii] = changeHeadMap.get(fieldName);

                int bytes = fieldName.getBytes().length;
                arrColWidth[ii] =  bytes < minBytes ? minBytes : bytes;
                sheet.setColumnWidth(ii,arrColWidth[ii]*256);
                ii++;
            }
            
            
            
            rowIndex = 0;
            
            
        	headerRow = sheet.createRow(0);
            for(int i=0;i<headers.length;i++)
            {
                headerRow.createCell(i).setCellValue(headers[i]);
                headerRow.getCell(i).setCellStyle(headerStyle);

            }
            rowIndex++;
          
        	for (Object obj : changeJaContent) {
                
                JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
                HSSFRow dataRow = sheet.createRow(rowIndex);
                for (int i = 0; i < properties.length; i++)
                {
                    HSSFCell newCell = dataRow.createCell(i);

                    Object o =  jo.get(properties[i]);
                    String cellValue = "";
                    if(o!=null)
                    {
                    	cellValue = o.toString();
                    }
                    newCell.setCellValue(cellValue);
                    newCell.setCellStyle(cellStyle);
                }
                rowIndex++;
            }
            
            
            
            sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,0,2));              
            dataRowRemark = sheet.createRow(rowIndex);
            remarkCell = dataRowRemark.createCell(0);
            remarkCell.setCellValue("注意:该资产数据发生变更,请在久其里面进行调账");
            remarkCell.setCellStyle(remarkCellStyle);
            
            
            
            
            
            
            
            sheet = workbook.createSheet();
            
            arrColWidth = new int[downHeadMap.size()];
            
            properties = new String[downHeadMap.size()];
            headers = new String[downHeadMap.size()];
            ii = 0;
            for (Iterator<String> iter = downHeadMap.keySet().iterator(); iter.hasNext();) 
            {
                String fieldName = iter.next();

                properties[ii] = fieldName;
                headers[ii] = downHeadMap.get(fieldName);

                int bytes = fieldName.getBytes().length;
                arrColWidth[ii] =  bytes < minBytes ? minBytes : bytes;
                sheet.setColumnWidth(ii,arrColWidth[ii]*256);
                ii++;
            }
            
            
            
            rowIndex = 0;
            
        	headerRow = sheet.createRow(0);
            for(int i=0;i<headers.length;i++)
            {
                headerRow.createCell(i).setCellValue(headers[i]);
                headerRow.getCell(i).setCellStyle(headerStyle);

            }
            rowIndex++;
   
            for (Object obj : downJaContent) {
                    
                    JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
                    HSSFRow dataRow = sheet.createRow(rowIndex);
                    for (int i = 0; i < properties.length; i++)
                    {
                        HSSFCell newCell = dataRow.createCell(i);

                        Object o =  jo.get(properties[i]);
                        String cellValue = "";
		                if(o!=null)
		                {
		                	cellValue = o.toString();
		                }
		                newCell.setCellValue(cellValue);
		                newCell.setCellStyle(cellStyle);
                    }	
                    rowIndex++;
        	}
            
            
            
            
            
        	sheet.addMergedRegion(new CellRangeAddress(rowIndex,rowIndex,0,2));  
        	dataRowRemark = sheet.createRow(rowIndex);
            remarkCell = dataRowRemark.createCell(0);
            remarkCell.setCellValue("注意:该资产已下账,请在久其里面进行下账");
            remarkCell.setCellStyle(remarkCellStyle);
            
            
            
            
            
            workbook.setSheetName(0, "未上账");
            workbook.setSheetName(1, "变更");
            workbook.setSheetName(2, "已下账");
            
            workbook.write(outXls);
    	}
    	catch(Exception ex)
    	{
    			String s = ex.getMessage();
    	}
    	finally
    	{
    			try
    			{
    				if(workbook != null)	workbook.close();
        			if(outXls != null)		outXls.close();
    			}
    			catch(Exception ex){}
    	}
    	
    	
    }
	
}
