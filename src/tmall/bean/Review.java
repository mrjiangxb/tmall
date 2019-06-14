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
	private Tuser user;        //关联用户
	private Product product;   //关联产品
	private int id;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatDate() {
		return creatDate;
	}
	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	public Tuser getUser() {
		return user;
	}
	public void setUser(Tuser user) {
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
