package bean;

public class Subject implements java.io.Serializable {
	/* フィールド*/
	private String school_cd;
	private String cd;
	private String name;

	public String getSchool_cd() {
		return school_cd;
	}
	public void setSchool_cd(String school_cd) {
		this.school_cd = school_cd;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
