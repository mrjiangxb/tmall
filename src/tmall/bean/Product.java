package tmall.bean;

import java.util.Date;
import java.util.List;

/**
 * ��Ʒ
 * @author Administrator
 */
// û�п������  Ĭ�϶�Ϊ1
public class Product {
	private String name;
	private String subTitle;
	private float price;
	private Date createDate;
	private Category category;  //��Category���һ��ϵ
	private int id;
	private List<ProductImage> productImages; 
	private ProductImage firstProductImage;  //ȡ����һ�Ų�ƷͼƬ��������ҳչʾ
	private int reviewCount; //��������  
}































