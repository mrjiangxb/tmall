package tmall.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Tuser;
import tmall.util.JdbcUtil;

public class TuserDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	Tuser bean = new Tuser();
	List<Object> params = new ArrayList<Object>();
	List<Tuser> beans = new ArrayList<Tuser>();
	
	/**
	 * @return 总数
	 */
	public int getTotal(){
		int total = 0;
		sql = "select count(*) from Tuser";
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
	 * @param tuser
	 */
	public void add(Tuser bean){
		sql = "insert into Tuser values(null,?,?)";
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
	 * @param tuser
	 */
	public void update(Tuser bean){
		sql = "update tuser set name=? ,password=? where id=?";
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
		sql = "delete from tuser where id= "+id;
		jdbc.doIt(sql);
	}
	
	/**
	 * @param id
	 * @return tuser
	 */
	public Tuser get(int id){
		sql = "select * from tuser where id="+id;
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
	
	//返回Tuser结果集
	public List<Tuser> list(int start, int count){
		sql = "select id,name,password from (select id,name,password,rownum as num from Tuser order by id desc) where num between ? and ?";
		params.add(start);
		params.add(count);
		beans = jdbc.queryPreparedStatement(sql, params, Tuser.class);
		return beans;
	}
	
	/*
	 * 根据用户名获取对象
	 * 注册的时候，需要判断某个用户是否已经存在，账号密码是否正确等操作
	 */
	public Tuser get(String name){
		sql = "select * from tuser where name=?";
		params.add(name);
		beans = jdbc.queryPreparedStatement(name, params, Tuser.class);
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
	public Tuser get(String name,String password){
		sql = "select * from tuser where name=? and password=?";
		params.add(name);
		params.add(password);
		beans = jdbc.queryPreparedStatement(name, params, Tuser.class);
		bean = beans.get(0);
		return bean;
	}
	
	public List<Tuser> list() {
	    return list(0, Short.MAX_VALUE);
	}
}











