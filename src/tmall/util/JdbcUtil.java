package tmall.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 数据库连接工具
 * @author 
 */
public class JdbcUtil {
	
	private Connection connection = null;
	private PreparedStatement pstm = null;
	private ResultSet rs=null;
	private String url = "jdbc:oracle:thin:@localhost:1521/orcl";
	private String username = "scott";
	private String password = "admin";
	
	
	static{
		//加载驱动
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection(){
		try {
			connection = DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	/**
	 * @param sql
	 * @return sql查询的结果集
	 */
	public ResultSet doIt(String sql){
		getConnection();
		try {
			pstm = connection.prepareStatement(sql);
			rs = pstm.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	//增删改
	public void updatePreparedStatement(String sql,List<Object> params){
		getConnection();
		try {
			pstm = connection.prepareStatement(sql);
			if(params!=null){
				for(int i=0;i<params.size();i++){
					pstm.setObject(i+1, params.get(i));
				}
				pstm.execute();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List queryPreparedStatement(String sql,List<Object> params,Class clazz){
		getConnection();
		List<Object> result = new ArrayList<Object>();
		try {
			pstm = connection.prepareStatement(sql);
			
			if(params!=null){
				for(int i=0;i<params.size();i++){
					pstm.setObject(i+1, params.get(i));
				}
			}
			
			rs = pstm.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();//获取每列的名字
			//获取列的数量
			int count = rsmd.getColumnCount();
			//存储所有列的名字
			List<String> column = new ArrayList<String>();
			for(int i=0;i<count;i++){
				column.add(rsmd.getColumnName(i+1));
			}
			
			while(rs.next()){
				//创建对象
				Object obj = clazz.newInstance();
				
				for(int i=0;i<count;i++){
					String name = column.get(i).toLowerCase();
					
					//根据列名获取属性
					Field f = clazz.getDeclaredField(name);
					f.setAccessible(true);//设置后可访问私有
					//获取属性的类型
					String type = f.getType().getName();
					if("int".equals(type) || "java.lang.Integer".equals(type)){
						int val = rs.getInt(name);
						f.set(obj, val);
					}else if("double".equals(type) || "java.lang.Double".equals(type)){
						double val = rs.getDouble(name);
						f.set(obj, val);
					}else if("float".equals(type) || "java.lang.Float".equals(type)){
						float val = rs.getFloat(name);
						f.set(obj, val);
					}else if("java.lang.String".equals(type)){
						String val = rs.getString(name);
						f.set(obj, val);
					}else if("java.util.Date".equals(type)){
						Date val = rs.getDate(name);
						f.set(obj, val);
					}else if("boolean".equals(type) || "java.lang.Boolean".equals(type)){
						boolean val = rs.getBoolean(name);
						f.set(obj, val);
					}
				}
				result.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public void close(){
		try {
			if(pstm!=null){
				pstm.close();
			}
			if(connection!=null){
				connection.close();
			}
			if(rs!=null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
