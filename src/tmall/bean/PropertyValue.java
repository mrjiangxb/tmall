package tmall.bean;
/**
 * 属性值
 * @author Administrator
 *
 */
public class PropertyValue {
	private String value;
	private Product product;    //关联产品
	private Property property;  //关联属性
	private int id;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
