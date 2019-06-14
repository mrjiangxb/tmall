package tmall.bean;

import java.util.Date;
import java.util.List;

/**
 * 产品
 * @author Administrator
 */
// 没有库存属性  默认都为1
public class Product {
	private String name;
	private String subTitle;
	private float price;
	private Date createDate;
	private Category category;  //与Category多对一关系
	private int id;
	private List<ProductImage> productImages; 
	private ProductImage firstProductImage;  //取出第一张产品图片用于搜索页展示
	private int reviewCount; //评价数量  
}































