package cn.bizowner.sqlite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SqliteHelper {
	   
		//final static Logger logger = LoggerFactory.getLogger(SqliteHelper.class);
	    
	    private Connection connection;
	    private Statement statement;
	    private ResultSet resultSet;
	    private String dbFilePath;
	    
	    /**
	     * ���캯��
	     * @param dbFilePath sqlite db �ļ�·��
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public SqliteHelper(String dbFilePath) throws ClassNotFoundException, SQLException {
	        this.dbFilePath = dbFilePath;
	        connection = getConnection(dbFilePath);
	        
	        
	        System.out.println("dbFilePath="+dbFilePath);
	    }
	    
	    
	    
	    public void setSqlTran() throws Exception
	    {
	    	connection.setAutoCommit(false);
	    }
	    
	    
	    
	    public void commit() throws Exception
	    {
	    	connection.commit();
	    }
	    
	    
	    
	    
	    
	    /**
	     * ��ȡ��ݿ�����
	     * @param dbFilePath db�ļ�·��
	     * @return ��ݿ�����
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public Connection getConnection(String dbFilePath) throws ClassNotFoundException, SQLException {
	        Connection conn = null;
	        Class.forName("org.sqlite.JDBC");
	        conn = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
	        return conn;
	    }
	    
	    /**
	     * ִ��sql��ѯ
	     * @param sql sql select ���
	     * @param rse ����������
	     * @return ��ѯ���
	     * @throws SQLException
	     * @throws ClassNotFoundException
	     */
	    public List<Map<String, Object>> executeQuery(String sql) throws SQLException, ClassNotFoundException {
	        
	    	
	    	System.out.println("sql="+sql);
	    	
	    	List<Map<String, Object>> listMap  =null;
	    	
    		ResultSet resultSet = getStatement().executeQuery(sql);
    		
    		listMap = convertList(resultSet);
       
            destroyed();
	        
	    	return listMap;
	    }
	    
	    
	    
	    /**
	     * ִ����ݿ����sql���
	     * @param sql
	     * @return ��������
	     * @throws SQLException
	     * @throws ClassNotFoundException
	     */
	    public int executeUpdate(String sql) throws SQLException, ClassNotFoundException {
	        try {
	            int c = getStatement().executeUpdate(sql);
	            return c;
	        } finally {
	            destroyed();
	        }
	        
	    }
	    
	    
	    
	    
	    /*public int executeSql(String sql) throws SQLException, ClassNotFoundException {
	        try {
	            int c = getStatement().execute(sql, columnNames)
	            return c;
	        } finally {
	            destroyed();
	        }
	        
	    }*/
	    
	    

	    /**
	     * ִ�ж��sql�������
	     * @param sqls
	     * @throws SQLException
	     * @throws ClassNotFoundException
	     */
	    public void executeUpdate(String...sqls) throws SQLException, ClassNotFoundException {
	        try {
	            for (String sql : sqls) {
	                getStatement().executeUpdate(sql);
	            }
	        } finally {
	            destroyed();
	        }
	    }
	    
	    /**
	     * ִ����ݿ���� sql List
	     * @param sqls sql�б�
	     * @throws SQLException
	     * @throws ClassNotFoundException
	     */
	    public void executeUpdate(List<String> sqls) throws SQLException, ClassNotFoundException {
	        try {
	            for (String sql : sqls) {
	                getStatement().executeUpdate(sql);
	            }
	        } finally {
	            destroyed();
	        }
	    }
	    
	     
	    
	    
	    public int executeSql(String sql, Object[] obj) throws SQLException, ClassNotFoundException 
	    {
	        
	    	PreparedStatement prep = null;
	    	
	    	try {
	    		
	            prep = connection.prepareStatement(sql);
	            
	            for(int i=0;i<obj.length;i++)
	            {
	            	prep.setObject(i+1, obj[i]);
	            }
	            
	            prep.execute();
	            
	        }
	        finally 
	        {
	        	if(prep != null)		prep.close();
	            destroyed();
	        }
	        return 0;
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    public List<Map<String, Object>> transQuery(String sql, Object[] obj) throws SQLException, ClassNotFoundException 
	    {
	        
	    	PreparedStatement prep = null;
	    	
	    	
	    	List<Map<String, Object>> listMap = null;
	    	
	    	try {
	    		
	            prep = connection.prepareStatement(sql);
	            
	            for(int i=0;i<obj.length;i++)
	            {
	            	prep.setObject(i+1, obj[i]);
	            }
	             
	            ResultSet rs = prep.executeQuery();
	            
	            listMap = convertList(rs);
	            
	        }
	        finally 
	        {
	        	if(prep != null)		prep.close(); 
	        }
	        return listMap;
	    }
	    
	    
	    
	    
	    
	    
	    
	    public List<Map<String, Object>> transQuery(String sql) throws SQLException, ClassNotFoundException 
	    {
	        
	    	PreparedStatement prep = null;
	    	
	    	
	    	List<Map<String, Object>> listMap = null;
	    	
	    	try {
	    		
	            prep = connection.prepareStatement(sql);
	             
	            ResultSet rs = prep.executeQuery();
	            
	            listMap = convertList(rs);
	            
	        }
	        finally 
	        {
	        	if(prep != null)		prep.close(); 
	        }
	        return listMap;
	    }
	    
	    
	    
	    
	    public void transExecute(String sql, Object[] obj) throws SQLException, ClassNotFoundException 
	    {
	        
	    	PreparedStatement prep = null;
	    	 
	    	try {
	    		
	            prep = connection.prepareStatement(sql);
	            
	            for(int i=0;i<obj.length;i++)
	            {
	            	prep.setObject(i+1, obj[i]);
	            }
	              
	            prep.execute();
	            
	        }
	        finally 
	        {
	        	if(prep != null)		prep.close(); 
	        }
	        return  ;
	    }
	    
	    
	    
	    
	    
	    public void executeSqlTrans(List<String> listSql) throws Exception
		{
				try
				{
					connection.setAutoCommit(false); 
					for(int i=0;i<listSql.size();i++)
					{
						getStatement().executeUpdate(listSql.get(i));
					}
					connection.commit();
				}
				finally
				{
					destroyed();
				}
		}
	    
	    
	    
	    
	    
	    public void executeSqlTrans(List<String> listSql, List<Object[]> listObj) throws Exception
		{
		    	PreparedStatement prep = null;
		    	
		    	try {
		    		
		    		connection.setAutoCommit(false); 
		    		
		    		for(int i=0;i<listSql.size();i++)
		    		{
		    				String sql = listSql.get(i);
		    				Object[] obj = listObj.get(i);
		    				
		    				prep = connection.prepareStatement(sql);
		    				
		    				 for(int j=0;j<obj.length;j++)
		 		             {
		 		            		prep.setObject(j+1, obj[j]);
		 		             }
		    				 
		    				 prep.execute();
		    		}
		    		
		            connection.commit();
		            
		        }
		        finally 
		        {
		        	if(prep != null)		prep.close();
		            destroyed();
		        }
		        return ;
		}
	    
	    
	    
	    private Connection getConnection() throws ClassNotFoundException, SQLException {
	        if (null == connection) connection = getConnection(dbFilePath);
	        return connection;
	    }
	    
	    private Statement getStatement() throws SQLException, ClassNotFoundException {
	        if (null == statement) statement = getConnection().createStatement();
	        return statement;
	    }
	    
	    /**
	     * ��ݿ���Դ�رպ��ͷ�
	     */
	    public void destroyed() {
	        try {
	            if (null != connection) {
	                connection.close();
	                connection = null;
	            }
	            
	            if (null != statement) {
	                statement.close();
	                statement = null;
	            }
	            
	            if (null != resultSet) {
	                resultSet.close();
	                resultSet = null;
	            }
	        } catch (SQLException e) {
	            //logger.error("Sqlite��ݿ�ر�ʱ�쳣", e);
	        }
	    }
	    
	    
	    
	    
	   private static List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
		   List<Map<String, Object>> listContent = new ArrayList<Map<String,Object>>();
	        ResultSetMetaData md = rs.getMetaData();
	        int columnCount = md.getColumnCount();
	        while (rs.next()) {
	            Map rowData = new HashMap();
	            for (int i = 1; i <= columnCount; i++) {
	                rowData.put(md.getColumnName(i), rs.getObject(i));
	            }
	            listContent.add(rowData);
	        }
	        return listContent;
	}


}
