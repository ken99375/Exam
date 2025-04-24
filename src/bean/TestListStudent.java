package bean;

public class TestListStudent implements java.io.Serializable{
	private String subjectName;
	private String subjectCd;
	private int num;
	private int point;

	// 以下ゲッター
	public String getSubjectName(){
		return this.subjectName;
	}

	public String getSubjectCd(){
		return this.subjectCd;
	}

	public int getNum(){
		return this.num;
	}

	public int getPoint(){
		return this.point;
	}

	// 以下セッター
	public void setSubjectName(String subjectName){
		this.subjectName = subjectName;
	}

	public void setSubjectCd(String subjectCd){
		this.subjectCd = subjectCd;
	}

	public void setNum(int num){
		this.num = num;
	}

	public void setPoint(int point){
		this.point = point;
	}
}
