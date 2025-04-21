package bean;

public class Test implements java.io.Serializable {
	/* フィールド*/
	private int student_id;
	private String subject_cd;
	private School school;
	private int no;
	private int point;
	private int class_num;

	public int getStudent_id() {
		return student_id;
	}
	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}
	public String getSubject_cd() {
		return subject_cd;
	}
	public void setSubject_cd(String subject_cd) {
		this.subject_cd = subject_cd;
	}

	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getClass_num() {
		return class_num;
	}
	public void setClass_num(int class_num) {
		this.class_num = class_num;
	}

}
