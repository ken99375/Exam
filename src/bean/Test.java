package bean;

public class Test implements java.io.Serializable {
	/* フィールド*/
	private Student student;
	private String classNum;
	private Subject subject;
	private School school;
	private int no;
	private int point;

	// 以下セッター
	public void setStudent(Student student){
		this.student = student;
	}

	public void setClassNum(String classNum){
		this.classNum = classNum;
	}

	public void setSubject(Subject subject){
		this.subject = subject;
	}

	public void setSchool(School school){
		this.school = school;
	}

	public void setNo(int no){
		this.no = no;
	}

	public void setPoint(int point){
		this.point = point;
	}

	// 以下ゲッター
	public Student getStudent(){
		return this.student;
	}

	public String getClassNum(){
		return this.classNum;
	}

	public Subject getSubject(){
		return this.subject;
	}

	public School getSchool(){
		return this.school;
	}

	public int getNo(){
		return this.no;
	}

	public int getPoint(){
		return this.point;
	}
}
