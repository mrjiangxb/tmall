package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.bean.User;
import tmall.util.JdbcUtil;

public class PropertyDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	Property bean = new Property();
	List<Object> params = new ArrayList<Object>();
	List<Property> beans = new ArrayList<Property>();
	
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
	 * @param property
	 */
	public void add(Property bean){
		sql = "insert into property values(null,?,?)";
		Connection conn = jdbc.getConnection();
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bean.getCategory().getId());//外键为Category主键
			pstm.setString(2, bean.getName());
			pstm.execute();
			rs = pstm.getGeneratedKeys(); //获取自增的主键
			if(rs.next()){
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 改
	 * @param property
	 */
	public void update(Property bean){
		sql = "update property set name=? ,cid=? where id=?";
		params.add(bean.getName());
		params.add(bean.getCategory().getId());
		params.add(bean.getId());
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
				String name = rs.getString("name");
				int cid = rs.getInt("cid");
				Category category = new CategoryDAO().get(cid);//该属性对应的类
				bean.setName(name);
				bean.setCategory(category);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	//查询某个分类下的的属性对象
	public List<Property> list(int cid, int start, int count){
		sql = "select * from Property where cid = ? order by id desc limit ?,? ";
		params.add(cid);
		params.add(start);
		params.add(count);
		beans = jdbc.queryPreparedStatement(sql, params, Property.class);
		return beans;
	}
	
	//查询某个分类下的的属性对象
	public List<Property> list(int cid) {
	    return list(cid, 0, Short.MAX_VALUE);
	}
}











