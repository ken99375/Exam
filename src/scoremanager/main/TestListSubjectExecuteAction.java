package scoremanager.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
  public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    req.setCharacterEncoding("UTF-8");
	 // ユーザ（教師）データを取得する
	 HttpSession session = req.getSession();
	 Teacher teacher = (Teacher)session.getAttribute("user");

	 // ユーザ情報がない場合はエラーメッセージを出す。
	 if (teacher != null) {		 String schoolCode = teacher.getSchool().getCd();

		 // フォーム入力取得
		 // 入学年度
		 String entYearStr = req.getParameter("f1");
		 // クラス番号
		 String classNum = req.getParameter("f2");
		 // 科目コード
		 String subjectCd = req.getParameter("f3");

		 // 入力チェック用エラーメッセージ
		 int entYear = 0;
		 Map<String, String> errors = new HashMap<>();


		 //// この部分はjspで必須項目にしてるからなくてもいいが一応エラー文字追加
		 // 入学年度int型チェック
		 if (entYearStr != null && !entYearStr.isEmpty()) {
			 try {
				 entYear = Integer.parseInt(entYearStr);
			 } catch (NumberFormatException e) {
				 errors.put("f1","入学年度は数字で入力してください");
			 }
		 }
		 // クラス選択チェック
		 if (classNum == null || classNum.equals("0")) {
			 errors.put("f2","クラスを選択してください");
		 }
		 // 科目選択チェック
		 if (subjectCd == null || subjectCd.equals("0")) {
			 errors.put("f3","科目を選択してください");
		 }



	 // データ取得
	 List<String> classList = new ClassNumDao().filter(teacher.getSchool());
	 List<Subject> subjectList = new SubjectDao().filter(teacher.getSchool());

	 // 成績データリストを初期化
	 // エラー回避・JSP連携の安全性確保のため
	 List<Test> testList = null;

	 // 入力エラーがなければ検索実行
	 // ただし、TestDaoがないためエラー
	 if (errors.isEmpty()) {
//		 TestDao testDao = new TestDao();
//		 testList = testDao.filter(schoolCode, entYear, classNum, subjectCd);
	 }

	 // 入力値と検索結果をリクエストスコープ（JSPへ引き渡し）へセット
	 req.setAttribute("f1", entYearStr);
	 req.setAttribute("f2", classNum);
	 req.setAttribute("f3", subjectCd);
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
