package tmall.comparator;

import java.util.Comparator;

import tmall.bean.Product;

public class ProductAllComparator implements Comparator<Product>{
	@Override
	public int compare(Product p1, Product p2) {
		//返回值为int类型，大于0表示正序，小于0表示逆序
		return p2.getReviewCount()*p2.getSaleCount()-p1.getReviewCount()*p1.getSaleCount();
	}
}
