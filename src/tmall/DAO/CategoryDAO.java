package tmall.DAO;

import java.beans.Beans;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category; //引入自己写的Category类
import tmall.util.JdbcUtil;
/**
 * 建立对于Category对象的ORM映射
 * @author JXB
 */
public class CategoryDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	List<Object> params = new ArrayList<Object>();
	/**
	 * @return 总数
	 */
	public int getTotal(){
		int total = 0;
		sql = "select count(*) from Category";
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
	public void add(Category category){
		sql = "insert into category values(null,?)";
		Connection conn = jdbc.getConnection();
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, category.getName());
			pstm.execute();
			rs = pstm.getGeneratedKeys(); //获取自增的主键
			if(rs.next()){
				int id = rs.getInt(1);
				category.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 改
	 * @param category
	 */
	public void update(Category category){
		sql = "update category set name=? where id=?";
		params.add(category.getName());
		params.add(category.getId());
		jdbc.updatePreparedStatement(sql, params);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id){
		sql = "delete from category where id= "+id;
		jdbc.doIt(sql);
	}
	
	/**
	 * @param id
	 * @return Category
	 */
	public Category get(int id){
		Category category = null;
		sql = "select * from category where id="+id;
		rs=jdbc.doIt(sql);
		try {
			if(rs.next()){
				category = new Category();
				String name = rs.getString("name");//表中第二列name
				category.setName(name);
				category.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}
	
	public List<Category> list() {
	    return list(0, getTotal());
	}
	
	//返回Category结果集
	public List<Category> list(int start, int count){
		List<Category> categorys = new ArrayList<Category>();
		sql = "select id,name from (select id,name,rownum as num from Category order by id desc) where num between ? and ?";
		params.add(start);
		params.add(count);
		categorys = jdbc.queryPreparedStatement(sql, params, Category.class);
		return categorys;
	}
}














