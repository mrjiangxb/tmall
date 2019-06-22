package tmall.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.Tuser;
import tmall.util.JdbcUtil;

public class OrderItemDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	OrderItem bean = new OrderItem();
	List<Object> params = new ArrayList<Object>();
	List<OrderItem> beans = new ArrayList<OrderItem>();
	
	public int getTotal(){
		int total = 0;
		sql = "select count(*) from orderitem";
		rs = jdbc.doIt(sql);
		try {
			if(rs.next()){
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(OrderItem bean){
		sql = "insert into orderitem values(null, ?, ?, ?)";
		Connection conn = jdbc.getConnection();
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			
			pstm.setInt(1, bean.getProduct().getId());
			//订单项在创建的时候，是没有订单信息的
            if(bean.getOrder()==null)
                pstm.setInt(2, -1);
            else
                pstm.setInt(2, bean.getOrder().getId()); 
            
			pstm.setInt(2, bean.getOrder().getId());
			pstm.setInt(3, bean.getUser().getId());
			pstm.execute();
			rs = pstm.getGeneratedKeys();
			if(rs.next()){
				int id = rs.getInt(1);
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(OrderItem bean){
		sql = "update orderitem set pid= ?, oid=?, tuid=? where id = ?";
		params.add(bean.getProduct().getId());
		params.add(bean.getOrder().getId());
		params.add(bean.getUser().getId());
		params.add(bean.getId());
		jdbc.updatePreparedStatement(sql, params);
	}
	
	public void delete(int id){
		sql = "delete * from orderitem where id = "+id;
		jdbc.doIt(sql);
	}
	
	public OrderItem get(int id){
		sql = "select * from orderitem where id = "+id;
		rs = jdbc.doIt(sql);
		try {
			if(rs.next()){
				int pid = bean.getProduct().getId();
				int oid = bean.getOrder().getId();
				int tuid = bean.getUser().getId();
				Product product = new ProductDAO().get(pid);
				Tuser user = new TuserDAO().get(tuid);
				
				bean.setId(id);
				bean.setProduct(product);
				if(oid != -1){
					Order order = new OrderDAO().get(oid);
					bean.setOrder(order);
				}
				bean.setUser(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<OrderItem> listByUser(int tuid, int start, int count){
		sql = "select * from (select id, pid, oid, tuid, rownum num from orderitem  where tuid = ? and oid = -1 order by id desc) where num between ? and ?";
		params.add(tuid);
		params.add(start);
		params.add(count);
		rs = jdbc.query(sql, params);
		try {
			while(rs.next()){
				int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");
                Product product = new ProductDAO().get(pid);
                Tuser user = new TuserDAO().get(tuid);
                if(oid != -1){
                    Order order= new OrderDAO().get(oid);
                    bean.setOrder(order);                  
                }
                bean.setProduct(product);
                bean.setUser(user);
                bean.setId(id);               
                beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<OrderItem> listByUser(int uid) {
        return listByUser(uid, 0, Short.MAX_VALUE);
    }
	
	 public List<OrderItem> listByOrder(int oid, int start, int count){
		 sql = "select * from (select id, pid, oid, tuid, rownum num from orderitem  where oid = ? order by id desc) where num between ? and ?";
			params.add(oid);
			params.add(start);
			params.add(count);
			rs = jdbc.query(sql, params);
			try {
				while(rs.next()){
					int id = rs.getInt(1);
	                int pid = rs.getInt("pid");
	                int tuid = rs.getInt("tuid");
	                Product product = new ProductDAO().get(pid);
	                Tuser user = new TuserDAO().get(tuid);
	                if(oid != -1){
	                    Order order= new OrderDAO().get(oid);
	                    bean.setOrder(order);                  
	                }
	                bean.setProduct(product);
	                bean.setUser(user);
	                bean.setId(id);               
	                beans.add(bean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 return beans;
	 }
	
	public List<OrderItem> listByOrder(int oid) {
        return listByOrder(oid, 0, Short.MAX_VALUE);
    }
	
	/**
	 * 为订单设置订单项集合
	 * @param os
	 */
	public void fill(List<Order> os){   
		for(Order o : os){
			List<OrderItem> ois = listByOrder(o.getId());
			float total = 0;
			int totalNum = 0;
			for(OrderItem oi : ois){
				total+=oi.getProduct().getPrice();
				totalNum+=oi.getNumber();
			}
			o.setTotal(total);
			o.setOrderItems(ois);
			o.setTotalNumber(totalNum);
		}
	}
	
	/**
	 * 为订单设置订单项集合
	 * @param o
	 */
	public void fill(Order o) {
        List<OrderItem> ois=listByOrder(o.getId());
        float total = 0;
        for (OrderItem oi : ois) {
             total+=oi.getNumber()*oi.getProduct().getPrice();
        }
        o.setTotal(total);
        o.setOrderItems(ois);
    }
	
	 public List<OrderItem> listByProduct(int pid, int start, int count){
		 sql = "select * from (select id, pid, oid, tuid, rownum num from orderitem  where pid = ? order by id desc) where num between ? and ?";
			params.add(pid);
			params.add(start);
			params.add(count);
			rs = jdbc.query(sql, params);
			try {
				while(rs.next()){
					int id = rs.getInt(1);
	                int oid = rs.getInt("oid");
	                int tuid = rs.getInt("tuid");
	                Product product = new ProductDAO().get(pid);
	                Tuser user = new TuserDAO().get(tuid);
	                if(oid != -1){
	                    Order order= new OrderDAO().get(oid);
	                    bean.setOrder(order);                  
	                }
	                bean.setProduct(product);
	                bean.setUser(user);
	                bean.setId(id);               
	                beans.add(bean);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		 return beans;
	 }
	
	public List<OrderItem> listByProduct(int pid){
		return listByProduct(pid, 0, Short.MAX_VALUE);
	}
}






















