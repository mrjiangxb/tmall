package tmall.util;
/**
 * util.Date 与 sql.Date 类型转换
 * @author Administrator
 *
 */
public class DateUtil {
	public static java.sql.Date d_sd(java.util.Date d){
		if(d==null){
			return null;
		}
		return new java.sql.Date(d.getTime());
	}
	
	public static java.util.Date sd_d(java.sql.Date sd){
		if(sd==null){
			return null;
		}
		return new java.util.Date(sd.getTime());
	}
}
