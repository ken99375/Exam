package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
  public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

	 // ユーザ（教師）データを取得する
	 HttpSession session = req.getSession();
	 Teacher teacher = (Teacher)session.getAttribute("user");

	 // ユーザ情報がない場合はエラーメッセージを出す。
	 if (teacher != null) {
		 String schoolCode = teacher.getSchool().getCd();

		 // フォーム入力取得
		 // 学生番号
		 String student_id = req.getParameter("f1");
		 StudentDao stu_dao = new StudentDao();
		 Student student = stu_dao.get(student_id);

		 // 入力チェック用エラーメッセージ
		 int entYear = 0;
		 Map<String, String> errors = new HashMap<>();


		 //// この部分はjspで必須項目にしてるからなくてもいいが一応エラー文字追加

			 // 学生番号選択チェック
			 if (student_id == null || student_id.equals("0")) {
				 errors.put("f1","学生番号を選択してください");
			 }


	 // 成績データリストを初期化
	 // エラー回避・JSP連携の安全性確保のため
	 List<TestListStudent> testList = null;

	 // 入力エラーがなければ検索実行
	 // ただし、TestDaoがないためエラー
	 if (errors.isEmpty()) {
		 TestListStudentDao testDao = new TestListStudentDao();
		 testList = testDao.filter(student);
	 }

	 // 入力値と検索結果をリクエストスコープ（JSPへ引き渡し）へセット
	 LocalDate todaysDate = LocalDate.now(); // LcalDateインスタンスを取得
	 int year = todaysDate.getYear(); // 現在の年を取得
	// 先生の所属する学校のクラスリストを持ってくる
	ClassNumDao c_dao = new ClassNumDao();
	List<String> c_list = c_dao.filter(teacher.getSchool());
	// 先生の所属する学校の科目データを持ってくる
	SubjectDao sub_dao = new SubjectDao();
	List<Subject> sub_list = sub_dao.filter(teacher.getSchool());
	List<Integer> entYearSet = new ArrayList<>();
	 // 10年前から1年後まで年をリストに追加
	 for (int i = year -10; i < year + 10; i++) {
		 entYearSet.add(i);
	 }

	req.setAttribute("c_list", c_list);
	req.setAttribute("sub_list", sub_list);
	 req.setAttribute("f1", student_id);
	 req.setAttribute("testList", testList);
	 req.setAttribute("erroes", errors);

	 } else {
		 // ユーザ情報が取得できなかった場合
		 req.setAttribute("error", "ユーザー情報が取得できませんでした。ログインし直してください");
	 }

	 // test_list_student.jspへフォワード
	 req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
  }
}
