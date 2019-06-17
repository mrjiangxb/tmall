package tmall.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.bean.Tuser;
import tmall.util.JdbcUtil;

public class PropertyDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	Property property = null;
	List<Object> params = new ArrayList<Object>();
	List<Property> propertys = new ArrayList<Property>();
	
	/*
	 * 获取某种分类下的属性总数，在分页显示的时候会用到
	 */
	public int getTotal(int cid){
		int total = 0;
		sql = "select count(*) from Property where cid="+cid;
		rs = jdbc.doIt(sql);
			try {
				while(rs.next()){
					total = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return total;
	}
	
	/**
	 * 增
	 * @param category
	 */
	public void add(Property property){
		sql = "insert into property values(null,?,?)";
		Connection conn = jdbc.getConnection();
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, property.getCategory().getId());//外键为Category主键
			pstm.setString(2, property.getName());
			pstm.execute();
			rs = pstm.getGeneratedKeys(); //获取自增的主键
			if(rs.next()){
				int id = rs.getInt(1);
				property.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 改
	 * @param property
	 */
	public void update(Property property){
		sql = "update property set name=? ,cid=? where id=?";
		params.add(property.getName());
		params.add(property.getCategory().getId());
		params.add(property.getId());
		jdbc.updatePreparedStatement(sql, params);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id){
		sql = "delete from property where id= "+id;
		jdbc.doIt(sql);
	}
	
	public Property get(int id){
		sql = "select * from property where id="+id;
		rs=jdbc.doIt(sql);
		try {
			if(rs.next()){
				property = new Property();
				String name = rs.getString("name");
				int cid = rs.getInt("cid");
				Category category = new CategoryDAO().get(cid);//该属性对应的类
				property.setName(name);
				property.setCategory(category);
				property.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return property;
	}
	
	//查询某个分类下的的属性对象
	public List<Property> list(int cid) {
	    return list(cid, 0, getTotal(cid));
	}
	//查询某个分类下的的属性对象
	public List<Property> list(int cid, int start, int count){
		sql = "select id,cid,name from (select id,cid,name,rownum as num from property where cid=? order by id desc) where num between ? and ?";
		params.add(cid);
		params.add(start);
		params.add(count);
		propertys = jdbc.queryPreparedStatement(sql, params, Property.class);
		return propertys;
	}
}











