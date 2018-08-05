package cn.bizowner.handler;

import java.sql.CallableStatement;    
import java.sql.PreparedStatement;    
import java.sql.ResultSet;    
import java.sql.SQLException;    
    
import org.apache.ibatis.type.JdbcType;    
import org.apache.ibatis.type.TypeHandler;    
    
/**  
 * @author   
 * java中的boolean和jdbc中的char之间转换;true-Y;false-N  
 */    
public class BooleanTypeHandler implements TypeHandler {    
    
    /* (non-Javadoc)  
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.ResultSet, java.lang.String)  
     */    
    @Override    
    public Object getResult(ResultSet arg0, String arg1) throws SQLException {    
        String str = arg0.getString(arg1);    
        Boolean rt = Boolean.FALSE;    
        if (str.equalsIgnoreCase("Y")){    
            rt = Boolean.TRUE;    
        }    
        return rt;     
    }    
    
    /* (non-Javadoc)  
     * @see org.apache.ibatis.type.TypeHandler#getResult(java.sql.CallableStatement, int)  
     */    
    @Override    
    public Object getResult(CallableStatement arg0, int arg1)    
            throws SQLException {    
        Boolean b = arg0.getBoolean(arg1);    
        return b == true ? "Y" : "N";    
    }    
    
    /* (non-Javadoc)  
     * @see org.apache.ibatis.type.TypeHandler#setParameter(java.sql.PreparedStatement, int, java.lang.Object, org.apache.ibatis.type.JdbcType)  
     */    
    /*@Override    
    public void setParameter(PreparedStatement arg0, int arg1, Object arg2,    
            JdbcType arg3) throws SQLException {    
        Boolean b = (Boolean) arg2;    
        String value = (Boolean) b == true ? "Y" : "N";    
        arg0.setString(arg1, value);    
    }*/
    
    
    @Override    
    public void setParameter(PreparedStatement arg0, int arg1, Object arg2,    
            JdbcType arg3) throws SQLException {    
        Boolean b = (Boolean) arg2;    
        String value = (Boolean) b == true ? "Y" : "N";    
        arg0.setString(arg1, value);    
    }
    
    
   /* @Override    
    public void setParameter(ResultSet arg0, String arg1) throws SQLException {    
        String str = arg0.getString(arg1);    
        Boolean rt = Boolean.FALSE;    
        if (str.equalsIgnoreCase("Y")){    
            rt = Boolean.TRUE;    
        }    
        return rt;     
    }   */
    
    

	@Override
	public Object getResult(ResultSet arg0, int arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}    
}    
