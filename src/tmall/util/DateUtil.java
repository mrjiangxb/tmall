package tmall.util;

import java.sql.Timestamp;

/**
 * util.Date 与 sql.Date 类型转换
 * @author JXB
 *
 */
public class DateUtil {
	public static java.sql.Timestamp d_t(java.util.Date d){
		if(d==null){
			return null;
		}
		return new java.sql.Timestamp(d.getTime());
	}
	
	public static java.util.Date t_d(java.sql.Timestamp t){
		if(t==null){
			return null;
		}
		return new java.util.Date(t.getTime());
	}
}
