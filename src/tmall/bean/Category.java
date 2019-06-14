package tmall.bean;

import java.util.List;
/**
 * 分类
 * @author Administrator
 *
 */
public class Category {
	private String name;
	private int id;
	List<Product> products;    //放在集合中便于分类检索
	List<List<Product>> productsByRow;  //为放在集合中便于分类检索了在首页竖状导航的分类名称右边显示产品列表
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Category [name="+name+"]";     //测试用，实际业务不会调用
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<List<Product>> getProductsByRow() {
		return productsByRow;
	}
	public void setProductsByRow(List<List<Product>> productsByRow) {
		this.productsByRow = productsByRow;
	}
}
