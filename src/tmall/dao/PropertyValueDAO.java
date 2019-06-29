package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.JdbcUtil;

public class PropertyValueDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	PropertyValue bean = new PropertyValue();
	List<Object> params = new ArrayList<Object>();
	List<PropertyValue> beans = new ArrayList<PropertyValue>();
	
	public void add(PropertyValue bean){
		sql = "insert into propertyvalue values(null,?,?,?)";
		Connection conn = jdbc.getConnection();
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, bean.getProduct().getId());;
			pstm.setInt(2,bean.getProperty().getId());
			pstm.setString(3,bean.getValue());
			pstm.execute();
			rs = pstm.getGeneratedKeys();
			if(rs.next()){
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
			
			
		
	}
	
	public void update(PropertyValue bean){
		sql = "update propertyvalue set pid = ?, ptid = ?; value = ?, where id = ?";
		params.add(bean.getProduct().getId());
		params.add(bean.getProperty().getId());
		params.add(bean.getValue());
		params.add(bean.getId());
		jdbc.updatePreparedStatement(sql, params);
	}
	
	public void delete(int id){
		sql = "delete * from propertyvalue where id = "+id;
		jdbc.doIt(sql);
	}
	
	/**
	 * 根据属性id和产品id，获取一个PropertyValue对象
	 * @param ptid
	 * @param pid
	 * @return PropertyValue
	 */
	public PropertyValue get(int ptid, int pid){
		sql = "select * from propertyvalue where ptid = "+ptid+" and pid = "+pid;
		rs = jdbc.doIt(sql);
		try {
			if(rs.next()){
				int id = rs.getInt("id");
				String value = rs.getString("value");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				
				bean.setId(id);
				bean.setProduct(product);
				bean.setProperty(property);
				bean.setValue(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/*
	 * 初始化某个产品对应的属性值，初始化逻辑：
     * 1.  根据分类获取所有的属性 
     * 2.  遍历每一个属性
     * 2.1 根据属性和产品，获取属性值 
	 * 2.2   如果属性值不存在，就创建一个属性值对象
	 */
	public void init(Product product){
		List<Property> propertys = new PropertyDAO().list(product.getCategory().getId());
		for(Property property : propertys){
			PropertyValue pv = get(property.getId(),product.getId());
			if(pv==null){
				pv = new PropertyValue();
				pv.setProduct(product);
				pv.setProperty(property);
				this.add(pv);
			}
		}
	}
	
	//查询某个产品下所有的属性值
	public List<PropertyValue> list(int pid){
		sql = "select * from propertyvalue where pid = "+pid;
		rs = jdbc.doIt(sql);
		try {
			while(rs.next()){
				int id = rs.getInt("id");
				int ptid = rs.getInt("ptid");
				String value = rs.getString("value");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				
				bean.setId(id);
				bean.setProduct(product);
				bean.setProperty(property);
				bean.setValue(value);
				
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
}















