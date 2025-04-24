package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
  public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    req.setCharacterEncoding("UTF-8");
	 // ユーザ（教師）データを取得する
	 HttpSession session = req.getSession();
	 Teacher teacher = (Teacher)session.getAttribute("user");

	 // ユーザ情報がない場合はエラーメッセージを出す。
	 if (teacher != null) {
		 String schoolCode = teacher.getSchool().getCd();

		 // フォーム入力取得
		 // 学生番号
		 String student_id = req.getParameter("f1");

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
	 List<Test> testList = null;

	 // 入力エラーがなければ検索実行
	 // ただし、TestDaoがないためエラー
	 if (errors.isEmpty()) {
		 TestListStudentDao testDao = new TestListStudentDao();
		 testList = testDao.filter(student);
	 }

	 // 入力値と検索結果をリクエストスコープ（JSPへ引き渡し）へセット
	 req.setAttribute("f1", student_id);
	 req.setAttribute("classList", classList);
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
