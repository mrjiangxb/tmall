package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DateUtil;
import tmall.util.JdbcUtil;

public class ProductDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	Product bean = new Product(); 
	List<Object> params = new ArrayList<Object>();
	List<Product> beans = new ArrayList<Product>();
	
	public int getTotal(int cid){
		int total = 0;
		sql = "select count(*) from product where cid="+cid;
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
	
	public void add(Product bean){
		sql = "insert into ProductImage values(null,?,?,?,?,?,?)";
		Connection conn = jdbc.getConnection();
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, bean.getName());
			pstm.setString(2, bean.getSubTitle());
			pstm.setFloat(3, bean.getPrice());
			pstm.setInt(4, bean.getCategory().getId());//外键为Category主键
			pstm.setTimestamp(5, DateUtil.d_t(bean.getCreateDate()));
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
	
	public void update(Product bean){
		sql = "update product set name=?, subtitle=?, price=?, cid=?, creatdate=? where id=?";
		params.add(bean.getName());
		params.add(bean.getSubTitle());
		params.add(bean.getPrice());
		params.add(bean.getCategory().getId());
		params.add(DateUtil.d_t(bean.getCreateDate()));
		params.add(bean.getId());
		jdbc.updatePreparedStatement(sql, params);
	}
	
	public void delete(int id){
		sql = "delete from product where id= "+id;
		jdbc.doIt(sql);
	}
	
	/**
	 * 设置产品展示图
	 * @param p
	 */
	public void setFirstProductImage(Product p) {
        List<ProductImage> pis= new ProductImageDAO().list(p);
        if(!pis.isEmpty())
            p.setFirstProductImage(pis.get(0));    
    }
	
	public Product get(int id){
		sql = "select * from product where id="+id;
		rs=jdbc.doIt(sql);
		try {
			if(rs.next()){
				String name = rs.getString("name");
                String subTitle = rs.getString("subTitle");
                float price = rs.getFloat("price");
                int cid = rs.getInt("cid");
                Date createDate = DateUtil.t_d(rs.getTimestamp("createDate"));
               
                bean.setName(name);
                bean.setSubTitle(subTitle);
                bean.setPrice(price);
                Category category = new CategoryDAO().get(cid);
                bean.setCategory(category);
                bean.setCreateDate(createDate);
                bean.setId(id);
                setFirstProductImage(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	//查询分类下的产品
	public List<Product> list(int cid, int start, int count){
		Category category = new CategoryDAO().get(cid);//cid外键为Category主键
		sql = "select * from Product where cid = ? order by id desc limit ?,? ";
		params.add(cid);
		params.add(start);
		params.add(count);
		rs = jdbc.query(sql, params);
		try {
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String subtitle = rs.getString("subtitle");
				float price = rs.getFloat("price");
				Date creatdate = DateUtil.d_t(rs.getTimestamp("creatdate"));
				
				bean.setId(id);
				bean.setName(name);
				bean.setSubTitle(subtitle);
				bean.setPrice(price);
				bean.setCategory(category);
				bean.setCreateDate(creatdate);
				setFirstProductImage(bean);
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	//查询分类下的产品
	public List<Product> list(int cid){
		return list(cid,0,Short.MAX_VALUE);
	}
	
	public List<Product> list(int start, int count){
		sql = "select id,name,subtitle,price,cid,createdate from (select id,name,subtitle,price,cid,createdate,rownum as num from product) where num between ? and ?";
		params.add(start);
		params.add(count);
		rs = jdbc.query(sql, params);
		try {
			while(rs.next()){
				int id = rs.getInt("id");
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				String subtitle = rs.getString("subtitle");
				float price = rs.getFloat("price");
				Date creatdate = DateUtil.d_t(rs.getDate("creatdate"));
				
				bean.setId(id);
				bean.setName(name);
				bean.setSubTitle(subtitle);
				bean.setPrice(price);
				bean.setCreateDate(creatdate);
				
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	/*
	 * 为分类填充产品集合
	 */
	public void fill(Category category){
		List<Product> products = this.list(category.getId());
	}
	public void fill(List<Category> categorys){
		for(Category category : categorys){
			fill(category);
		}
	}
	
	/*
	 * 8个产品放到集合中，显示为一行
	 */
	public void fillByRow(List<Category> categorys){
		int productNumEachRow = 8;
		for(Category category : categorys){
			List<Product> products = category.getProducts();
			List<List<Product>> productsByRow = new ArrayList();
			for(int i=0; i<products.size(); i+=productNumEachRow){
				int size = i+productNumEachRow;
				size = size>products.size()?products.size():size;
				List<Product> productsOfEachRow = products.subList(i, size);
				productsByRow.add(productsOfEachRow);
			}
			category.setProductsByRow(productsByRow);
		}
	}
	
	/*
	 * 为产品设置评价数量
	 */
	public void setReviewNumber(Product product){
		int reviewCount = new ReviewDAO().getCount(product.getId());
		product.setReviewCount(reviewCount);
	}
	
	public void setReviewNumber(List<Product> products){
		for(Product product : products){
			setReviewNumber(product);
		}
	}
	
	public List<Product> search(String keyword, int start, int count){
		if(keyword==null || keyword.trim().length()==0){  //trim() 删除头尾空白符的字符串
			return beans;
		}
		sql = "select id,name,subtitle,price,cid,createdate from (select id,name,subtitle,price,cid,createdate,rownum as num from product where name like ?) where num between ? and ?";
		params.add("%"+keyword.trim()+"%");
		params.add(start);
		params.add(count);
		rs = jdbc.query(keyword, params);
		try {
			while(rs.next()){
				int id = rs.getInt("id");
				int cid = rs.getInt("cid");
				String name = rs.getString("name");
				String subtitle = rs.getString("subtitle");
				float price = rs.getFloat("price");
				Date creatdate = DateUtil.d_t(rs.getDate("creatdate"));
				
				bean.setId(id);
				bean.setName(name);
				bean.setSubTitle(subtitle);
				bean.setPrice(price);
				bean.setCreateDate(creatdate);
				
				Category category = new CategoryDAO().get(cid);
				bean.setCategory(category);
				beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
		
	}
}






