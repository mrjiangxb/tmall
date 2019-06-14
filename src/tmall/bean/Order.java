package tmall.bean;

import java.util.Date;
import java.util.List;

/**
 * 订单
 * @author Administrator
 *
 */
public class Order {
	private String orderCode;
	private String address;
	private String post;
	private String receiver;
	private String mobile;
	private String userMessage;
	private Date createDage;
	private Date confirmDate;
	private Tuser user;
	private int id;
	private List<OrderItem> orderItems;
	private float total;
	private int totalNumber;
	private String status;
	
	public String getStatusDesc(){
		String desc = "未知";
		switch(status){
			case OrderDAO.waitPay:
				desc="待付款";
				break;
			case OrderDAO.waitDelivery:
				desc="待发货";
				break;
			case OrderDAO.waitReview:
				desc="待评价";
				break;
			case OrderDAO.finish:
				desc="完成";
				break;
			case OrderDAO.delete:
				desc="删除";
				break;
			default:
				desc="未知";
		}
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public Date getCreateDage() {
		return createDage;
	}
	public void setCreateDage(Date createDage) {
		this.createDage = createDage;
	}
	public Date getConfirmDate() {
		return confirmDate;
	}
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	public Tuser getUser() {
		return user;
	}
	public void setUser(Tuser user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
