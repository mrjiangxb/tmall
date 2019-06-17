package tmall.util;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

/**
 * 属性封装成对象
 * @author Administrator
 */
public class Conversion {

	public static void req_obj(Object obj,HttpServletRequest request){
		
		try {
			Class clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();
			
			for(Field f:fields){
				f.setAccessible(true);
				//属性名字
				String name = f.getName();//empno ename 
				//属性类型
				Class cls = f.getType();
				//是否数组
				if(!cls.isArray()){
					//获取返回类型的字符串表示形式
					String type = cls.getName();
					//从请求中获取指定属性的值
					String value = request.getParameter(name);
					
					if(type.equals("java.lang.String")){
						//给obj对象的f属性赋值
						f.set(obj, value);
					}else if(type.equals("int") || type.equals("java.lang.Integer")){
						f.set(obj, Integer.parseInt(value));
					}else if(type.equals("double") || type.equals("java.lang.Double")){
						f.set(obj, Double.parseDouble(value));
					}else if(type.equals("float") || type.equals("java.lang.Float")){
						f.set(obj, Float.parseFloat(value));
					}else if(type.equals("java.util.Date")){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						f.set(obj, sdf.parse(value));
					}
				}else{
					//获取返回类型的字符串表示形式
					String type = cls.getName();
					String[] ss = request.getParameterValues(name);
					if(int[].class.getName().equals(type) || Integer[].class.getName().equals(type)){
						
						int[] array = new int[ss.length];
						
						for(int i=0;i<ss.length;i++){
							array[i]=Integer.parseInt(ss[i]) ;
						}
						
						f.set(obj, array);
						
					}else if(String[].class==cls){
						f.set(obj, ss);
					}else if(double[].class.getName().equals(type) || Double[].class.getName().equals(type)){
						
						double[] array = new double[ss.length];
						
						for(int i=0;i<ss.length;i++){
							array[i]=Double.parseDouble(ss[i]) ;
						}
						
						f.set(obj, array);
						
						
					}
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}






