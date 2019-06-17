package tmall.bean;
/**
 * 产品图片
 * @author Administrator
 *
 */
public class ProductImage {
	private int id;
	private Product product; //与Product多对一关系
	
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
