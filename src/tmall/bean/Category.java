package tmall.bean;

import java.util.List;
/**
 * ����
 * @author Administrator
 *
 */
public class Category {
	private String name;
	private int id;
	List<Product> products;    //���ڼ����б��ڷ������
	List<List<Product>> productsByRow;  //Ϊ������ҳ��״�����ķ��������ұ���ʾ��Ʒ�б�
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Category [name="+name+"]";     //�����ã�ʵ��ҵ�񲻻����
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
