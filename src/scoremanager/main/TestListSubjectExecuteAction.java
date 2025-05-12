package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
  public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    req.setCharacterEncoding("UTF-8");
	 // ユーザ（教師）データを取得する
	 HttpSession session = req.getSession();
	 Teacher teacher = (Teacher)session.getAttribute("user");

		 // ユーザ情報がない場合はエラーメッセージを出す。
		 if (teacher != null) {
			 String schoolCode = teacher.getSchool().getCd();

		 // フォーム入力取得
		 // 入学年度
		 String entYearStr = req.getParameter("ent_year");
		 // クラス番号
		 String classNum = req.getParameter("class_num");
		 // 科目コード
		 String subjectCd = req.getParameter("cd");




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

		 // SubjectDaoのgetメソッドを使い、
		 		//所属校と科目コードで絞り込んで科目テーブルの一行を返す
		 SubjectDao sub_dao = new SubjectDao();
		 Subject subject = sub_dao.get(subjectCd, teacher.getSchool());

		 System.out.print(subject);
	 // entYear
	// classNum
	// subject
		 // TestListSubjectDaoのfilterメソッドを使い、
		 		// 指定された入学年度、クラス番号、科目、学校 に合致するテストの情報を取得して返す。
		 TestListSubjectDao tl_sub_dao = new TestListSubjectDao();
		 List<TestListSubject> tl_sub = tl_sub_dao.filter(entYear, classNum, subject, teacher.getSchool());

		 LocalDate todaysDate = LocalDate.now(); // LcalDateインスタンスを取得
		 int year = todaysDate.getYear(); // 現在の年を取得
		// 先生の所属する学校のクラスリストを持ってくる
		ClassNumDao c_dao = new ClassNumDao();
		List<String> c_list = c_dao.filter(teacher.getSchool());
		// 先生の所属する学校の科目データを持ってくる
		List<Subject> sub_list = sub_dao.filter(teacher.getSchool());
		List<Integer> entYearSet = new ArrayList<>();
		 // 10年前から1年後まで年をリストに追加
		 for (int i = year -10; i < year + 10; i++) {
			 entYearSet.add(i);
		 }

		req.setAttribute("c_list", c_list);
		req.setAttribute("sub_list", sub_list);
		req.setAttribute("ent_year_set", entYearSet);
		 req.setAttribute("tl_sub", tl_sub);

	 } else {
		 // ユーザ情報が取得できなかった場合
		 req.setAttribute("error", "ユーザー情報が取得できませんでした。ログインし直してください");
	 }

	 // test_list_student.jspへフォワード
	 req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
  }
}
