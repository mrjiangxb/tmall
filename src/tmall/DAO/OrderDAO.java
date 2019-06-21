package tmall.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.Tuser;
import tmall.util.DateUtil;
import tmall.util.JdbcUtil;

public class OrderDAO {
	JdbcUtil jdbc;
	String sql;
	ResultSet rs;
	Order bean = new Order();
	List<Object> params = new ArrayList<Object>();
	List<Order> beans = new ArrayList<Order>();
	
	public static final String waitPay = "waitPay";
	public static final String waitDelivery = "waitDelivery";
	public static final String waitConfirm = "waitConfirm"; //待确认
	public static final String waitReview = "waitReview";
	public static final String finish = "finish";
	public static final String delete = "delete";
	
	public void add(Order bean){
		sql = "insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
		params.add(bean.getOrderCode());
		params.add(bean.getAddress());
		params.add(bean.getPost());
		params.add(bean.getReceiver());
		params.add(bean.getMobile());
		params.add(bean.getUserMessage());
		params.add(DateUtil.d_t(bean.getCreateDate()));
		params.add(DateUtil.d_t(bean.getPayDate()));
		params.add(DateUtil.d_t(bean.getDeliveryDate()));
		params.add(DateUtil.d_t(bean.getConfirmDate()));
		params.add(bean.getUser().getId());
		params.add(bean.getStatus());
		rs = jdbc.query(sql, params);
		try {
			if(rs.next()){
				int id = rs.getInt(1);
				bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Order bean){
		sql = "update order_ set ordercode= ?, address=?, post=?,receiver=?,mobile=? ,userMessage = ? , createDate =? , payDate =?, deliveryDate = ? , confirmDate =?, tuid=?, status=? where id = ?";
		params.add(bean.getOrderCode());
		params.add(bean.getAddress());
		params.add(bean.getPost());
		params.add(bean.getReceiver());
		params.add(bean.getMobile());
		params.add(bean.getUserMessage());
		params.add(DateUtil.d_t(bean.getCreateDate()));
		params.add(DateUtil.d_t(bean.getPayDate()));
		params.add(DateUtil.d_t(bean.getDeliveryDate()));
		params.add(DateUtil.d_t(bean.getConfirmDate()));
		params.add(bean.getUser().getId());
		params.add(bean.getStatus());
		params.add(bean.getId());
		jdbc.updatePreparedStatement(sql, params);
	}
	
	public void delete(int id){
		sql = "delete from order_ where id="+id;
		jdbc.doIt(sql);
	}
	
	public Order get(int id){
		sql = "select * from order_ where id="+id;
		rs = jdbc.doIt(sql);
		try {
			if(rs.next()){
				String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                int uid =rs.getInt("uid");
                Date createDate = DateUtil.t_d( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t_d( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t_d( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t_d( rs.getTimestamp("confirmDate"));
                
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                Tuser user = new TuserDAO().get(uid);
                bean.setUser(user);
                bean.setStatus(status);
                 
                bean.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<Order> list(int start, int count){
		sql = "select * from (select id,ordercode,address,post,receiver,mobile,usermessage,createdate,paydate,deliverydate,confirmdate,tuid,status,rownum as num from order_ order by id desc) where num between ? and ?";
		params.add(start);
		params.add(count);
		rs = jdbc.query(sql, params);
		try {
			while(rs.next()){
				String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                int tuid =rs.getInt("tuid");
                Date createDate = DateUtil.t_d( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t_d( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t_d( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t_d( rs.getTimestamp("confirmDate"));
                int id = rs.getInt("id");
                
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                Tuser tuser = new TuserDAO().get(tuid);
                bean.setUser(tuser);
                bean.setStatus(status);
                
                bean.setId(id);
                beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<Order> list() {
	    return list(0, Short.MAX_VALUE);
	}
	
	public List<Order> list(int tuid, String excludeStatus, int start, int count){
		sql = "select * from (select id,ordercode,address,post,receiver,mobile,usermessage,createdate,paydate,deliverydate,confirmdate,tuid,status,rownum as num from order_ where tuid = ? and status != ? order by id desc) where num between ? and ?";
		params.add(tuid);
		params.add(excludeStatus);
		params.add(start);
		params.add(count);
		rs = jdbc.query(sql, params);
		try {
			while(rs.next()){
				String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = DateUtil.t_d( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.t_d( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.t_d( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.t_d( rs.getTimestamp("confirmDate"));
                int id = rs.getInt("id");
                
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                Tuser tuser = new TuserDAO().get(tuid);
                bean.setUser(tuser);
                bean.setStatus(status);
                
                bean.setId(id);
                beans.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return beans;
	}
	
	public List<Order> list(int uid,String excludedStatus) {
        return list(uid,excludedStatus,0, Short.MAX_VALUE);
    }
}







