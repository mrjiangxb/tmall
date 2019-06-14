package tmall.bean;
/**
 * 属性
 * @author Administrator
 *
 */
public class Property {
	private String name;
	private Category category;  //类别
	private int id;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
