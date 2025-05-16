package scoremanager.main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception, IOException {
		 HttpSession session = req.getSession();
		 Teacher teacher = (Teacher)session.getAttribute("user");


//			Connection conn = (Connection) session.getAttribute("conn");
//
//			 // 接続情報を取得して表示
//           DatabaseMetaData meta = conn.getMetaData();
//           System.out.println("接続中のDB URL: " + meta.getURL());

		 String entYearStr = ""; // 入力された入学年度
		 String classNum = ""; // 入力されたクラス番号
		 String isAttendStr = ""; // 入力された在学フラグ

		 int entYear = 0; // 入学年度
		 boolean isAttend = false; // 在学フラグ
		 List<Student> students = null; // 学生リスト
		 LocalDate todaysDate = LocalDate.now(); // LcalDateインスタンスを取得
		 int year = todaysDate.getYear(); // 現在の年を取得
		 StudentDao sDao = new StudentDao(); // 学生Dao
		 ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		 Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメータの取得 2
		 entYearStr = req.getParameter("f1");
		 classNum = req.getParameter("f2");
		 isAttendStr = req.getParameter("f3");

		 if (isAttendStr != null) {
			    // 在学フラグを立てる
			    isAttend = true;
			    // リクエストに在学フラグをセット
			    req.setAttribute("f3", isAttendStr);
			}
		 // ビジネスロジック 4
		 if (entYearStr != null) {
			 // 数値に変換
			 entYear = Integer.parseInt(entYearStr);
		 }
		 // リストを初期化
		 List<Integer> entYearSet = new ArrayList<>();
		 // 10年前から1年後まで年をリストに追加
		 for (int i = year -10; i < year + 1; i++) {
			 entYearSet.add(i);
		 }

		// DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());

		if (entYear != 0 && !classNum.equals("0")) {
		    // 入学年度とクラス番号を指定
		    students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
		} else if (entYear != 0 && classNum.equals("0")) {
		    // 入学年度のみ指定
		    students = sDao.filter(teacher.getSchool(), entYear, isAttend);
		} else if (!classNum.equals("0")) {
		    // クラスのみ指定（入学年度なし）
		    students = sDao.filter(teacher.getSchool(), classNum, isAttend); // このDAOメソッドが必要
		} else if (isAttend) {
		    // 在学中のみ指定（入学年度・クラスともに未指定）
		    students = sDao.filter(teacher.getSchool());
		} else {
		    // 条件未指定（または在学中のみ）
		    students = sDao.filter(teacher.getSchool(), isAttend);
		}


		// レスポンス値をセット 6
		// リクエストに入学年度をセット
		 req.setAttribute("f1", entYear);
		// リクエストにクラス番号をセット
		 req.setAttribute("f2", classNum);
		// 在学フラグが送信されていた場合

		// リクエストに学生リストをセット
		req.setAttribute("students", students);
		// リクエストにデータをセット。
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);

		// JSPへフォワード 7
		req.getRequestDispatcher("student_list.jsp").forward(req,res);
	}
}