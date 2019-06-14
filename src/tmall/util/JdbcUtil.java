package tmall.util;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 数据库连接工具
 * @author Administrator
 */
public class JdbcUtil {
	
	private Connection conn = null;
	private PreparedStatement psmt = null;
	
	
	static{
		//加载驱动
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
