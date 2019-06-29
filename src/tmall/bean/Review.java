package tmall.bean;

import java.util.Date;

/**
 * 评价
 * @author Administrator
 *
 */
public class Review {
	private String content;
	private Date creatDate;
	private User user;        //关联用户
	private Product product;   //关联产品
	private int id;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateDate() {
		return creatDate;
	}
	public void setCreateDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
