package tmall.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.Tuser;
import tmall.util.DateUtil;
import tmall.util.JdbcUtil;

public class ReviewDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	Review bean = new Review();
	List<Object> params = new ArrayList<Object>();
	List<Review> beans = new ArrayList<Review>();
	
	public int getTotal(int pid){
		int total = 0;
		sql = "select count(*) from review where pid="+pid;
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

	public void add(Review bean){
		sql = "insert into review values (null,?,?,?,?)";
		Connection conn = jdbc.getConnection();
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, bean.getContent());
			pstm.setInt(2, bean.getUser().getId());
			pstm.setInt(3, bean.getProduct().getId());
			pstm.setTimestamp(4, DateUtil.d_t(bean.getCreateDate()));
			pstm.execute();
			rs = pstm.getGeneratedKeys();
			if(rs.next()){
				int id = rs.getInt("id");
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Review bean){
		sql = "update review set content=?, tuid=?, pid=?, createdate=? where id=?";
		params.add(bean.getContent());
		params.add(bean.getUser().getId());
		params.add(bean.getProduct().getId());
		params.add(DateUtil.d_t(bean.getCreateDate()));
		params.add(bean.getId());
		jdbc.updatePreparedStatement(sql, params);
	}
	
	public void delete(int id){
		sql = "delete from review where id= "+id;
		jdbc.doIt(sql);
	}
	
	public Review get(int id){
		sql = "select * from review where id="+id;
		rs = jdbc.doIt(sql);
		try {
			while(rs.next()){
				String content = rs.getString("content");
				int tuid = rs.getInt("tuid");
				int pid = rs.getInt("pid");
				Date createdate = DateUtil.t_d(rs.getTimestamp("createdate"));
				Tuser user = new TuserDAO().get(tuid);
				Product product = new ProductDAO().get(pid);
				
				bean.setId(id);
				bean.setContent(content);
				bean.setUser(user);
				bean.setCreateDate(createdate);
				bean.setProduct(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public int getCount(int pid){
		sql = "select count(*) from review where pid="+pid;
		rs = jdbc.doIt(sql);
		try {
			while(rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<Review> list(int pid, int start, int count){
		sql = "select id,content,tuid,pid,createdate from(select id,content,tuid,pid,createdate,rownum as num from review where pid=? order by id desc) where num between ? and ?";
		params.add(pid);
		params.add(start);
		params.add(count);
		rs = jdbc.query(sql, params);
		try {
			while(rs.next()){
				int id = rs.getInt("id");
				String content = rs.getString("content");
				int tuid = rs.getInt("tuid");
				Date createdate = DateUtil.t_d(rs.getTimestamp("createdate"));
				Product product = new ProductDAO().get(pid);
				Tuser user = new TuserDAO().get(tuid);
				
				bean.setContent(content);
                bean.setCreateDate(createdate);
                bean.setId(id);    
                bean.setProduct(product);
                bean.setUser(user);
                beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<Review> list(int pid){
		return list(pid, 0, Short.MAX_VALUE);
	}
	
	public boolean isExist(String content, int pid){
		sql = "select * from review where content=? and pid=?";
		params.add(content);
		params.add(pid);
		rs = jdbc.query(content, params);
		try {
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}















