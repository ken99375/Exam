package bean;
import java.util.Map;

public class TestListSubject implements java.io.Serializable{
	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	// 一つのフィールドで二つの情報を保持
	private Map<Integer, Integer> points;

	// 以下ゲッター
	public int getEntYear(){
		return this.entYear;
	}

	public String getStudentNo(){
		return this.studentNo;
	}

	public String getStudentName(){
		return this.studentName;
	}

	// getClassNum(): String
	public String getClassNum(){
		return this.classNum;
	}

	// getPoints() Map<Integer, Integer>
	public Map<Integer, Integer> getPoints(){
		return this.points;
	}

	// getPoint(key : int):String
//	public String getPoint(int subjectId) {
//        // pointsマップから指定された科目IDに対応する得点を取得
//        Integer point = points.get(subjectId);
//
//        // 得点がnullの場合は、"Not Available"を返す
//        if (point != null) {
//            return String.valueOf(point); // 得点があればその値をStringとして返す
//        } else {
//            return "Not Available"; // 得点がなければ"Not Available"を返す
//        }
//    }

	// putPoint(key:int, value:int): void

	// 以下セッター
	public void setEntYear(int entYear){
		this.entYear = entYear;
	}

	public void setStudentNo(String studentNo){
		this.studentNo = studentNo;
	}

	public void setStudentName(String studentName){
		this.studentName = studentName;
	}

	public void setClassNum(String classNum){
		this.classNum = classNum;
	}
}
