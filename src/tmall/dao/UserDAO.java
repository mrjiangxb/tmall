package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.User;
import tmall.util.JdbcUtil;

public class UserDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	User bean = new User();
	List<Object> params = new ArrayList<Object>();
	List<User> beans = new ArrayList<User>();
	
	/**
	 * @return 总数
	 */
	public int getTotal(){
		int total = 0;
		sql = "select count(*) from user";
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
	 * @param user
	 */
	public void add(User bean){
		sql = "insert into user values(null,?,?)";
		Connection conn = jdbc.getConnection();
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, bean.getName());
			pstm.setString(2, bean.getPassword());
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
	 * @param user
	 */
	public void update(User bean){
		sql = "update user set name=? ,password=? where id=?";
		params.add(bean.getName());
		params.add(bean.getPassword());
		params.add(bean.getId());
		jdbc.updatePreparedStatement(sql, params);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id){
		sql = "delete from user where id= "+id;
		jdbc.doIt(sql);
	}
	
	/**
	 * @param id
	 * @return user
	 */
	public User get(int id){
		sql = "select * from user where id="+id;
		rs=jdbc.doIt(sql);
		try {
			if(rs.next()){
				String name = rs.getString("name");
				String password = rs.getString("password");
				bean.setName(name);
				bean.setPassword(password);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	//返回user结果集
	public List<User> list(int start, int count){
		sql = "select * from User order by id desc limit ?,? ";
		params.add(start);
		params.add(count);
		beans = jdbc.queryPreparedStatement(sql, params, User.class);
		return beans;
	}
	
	/*
	 * 根据用户名获取对象
	 * 注册的时候，需要判断某个用户是否已经存在，账号密码是否正确等操作
	 */
	public User get(String name){
		sql = "select * from tuser where name=?";
		params.add(name);
		beans = jdbc.queryPreparedStatement(name, params, User.class);
		bean = beans.get(0);
		return bean;
	}
	
	public boolean isExist(String name) {
		bean = get(name);
        return bean!=null;
    }
	
	/*
	 * 根据账号和密码获取对象
	 */
	public User get(String name,String password){
		sql = "select * from user where name=? and password=?";
		params.add(name);
		params.add(password);
		beans = jdbc.queryPreparedStatement(name, params, User.class);
		bean = beans.get(0);
		return bean;
	}
	
	public List<User> list() {
	    return list(0, Short.MAX_VALUE);
	}
}











