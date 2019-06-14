package tmall.bean;
/**
 * ¶©µ¥Ïî
 * @author Administrator
 *
 */
public class OrderItem {
	private int id;
	private int number;
	private Product product;
	private Order order;
	private Tuser user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Tuser getUser() {
		return user;
	}
	public void setUser(Tuser user) {
		this.user = user;
	}
	
}
