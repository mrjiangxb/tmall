package tmall.bean;
/**
 * 用户
 * @author Administrator
 *
 */
public class Tuser {
	private String password;
	private String name;
	private int id;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 对用户名进行加"*"处理
	 * @return
	 */
	public String getAnonymousName(){
		if(name==null){
			return null;
		}
		if(name.length()<=1){
			return "*";
		}
		if(name.length()==2){
			return name.substring(0, 1)+"*";
		}
		
		char[] cs = name.toCharArray();
		for(int i=1; i<cs.length-1;i++){
			cs[i]='*';
		}
		return new String(cs);
	}
}


















