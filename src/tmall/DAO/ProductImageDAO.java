package tmall.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.bean.Property;
import tmall.util.JdbcUtil;

public class ProductImageDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	ProductImage bean = new ProductImage();
	List<Object> params = new ArrayList<Object>();
	List<ProductImage> beans = new ArrayList<ProductImage>();
	
	public int getTotal(){
		int total = 0;
		sql = "select count(*) from productimage ";
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
	
	public void add(ProductImage bean){
		sql = "insert into ProductImage values(null,?)";
		Connection conn = jdbc.getConnection();
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, bean.getProduct().getId());//外键为Category主键
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
	public void update(ProductImage bean){
		sql = "update productImage set id=? ,pid=?";
		params.add(bean.getId());
		params.add(bean.getProduct().getId());
		jdbc.updatePreparedStatement(sql, params);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void delete(int id){
		sql = "delete from productImage where id= "+id;
		jdbc.doIt(sql);
	}
	
	public ProductImage get(int id){
		sql = "select * from productImage where id="+id;
		rs=jdbc.doIt(sql);
		try {
			if(rs.next()){
				int pid = rs.getInt("pid");
				Product product = new ProductDAO().get(pid);//该属性对应的类
				bean.setId(id);
				bean.setProduct(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	//查询指定产品下，某种类型的ProductImage
	public List<ProductImage> list(Product p, int start, int count){
		sql = "select * from ProductImage where pid =? order by id desc limit ?,? ";
		params.add(p.getId());
		params.add(start);
		params.add(count);
		beans = jdbc.queryPreparedStatement(sql, params, ProductImage.class);
		return beans;
	}
	
	public List<ProductImage> list(Product p) {
	    return list(p, 0, Short.MAX_VALUE);
	}
}


