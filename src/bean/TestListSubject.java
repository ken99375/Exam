package bean;
import java.util.Map;

public class TestListSubject implements java.io.Serializable{
	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	 // 一回目、二回目の点数をそれぞれ保持させる
	// つまり一人につき、二行出てくるようにする
	// でもjspで使用したいのはそれぞれを合わせた一行、、、
	// アクションクラスがとんでもないことになるかも
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

	public String getClassNum(){
		return this.classNum;
	}

	// 下のメソッドでセットしたものを取り出すメソッド
	// TestListSubjectDaoクラスで使用すると思う。
	public Map<Integer, Integer> getPoints(){
		return this.points;
	}

	public String getPoint(int key) {
		Integer value = this.points.get(key);
		// nullチェックしてから文字列化
		 return value != null ? value.toString() : null;
	 }

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

	// 一回目と二回目の点数をセットするためのメソッド
	public void setPoints(Map<Integer, Integer> points){
		this.points = points;
	}

	// ここをDaoクラスで使用して、TestSujectのMapに設定するという事かも
	// putPoint(key:int, value:int): void
	public void putPoint(int key, int value){
		this.points.put(key, value);
	}
}
