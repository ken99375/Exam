/// 96,97でエラー


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

public class TestRegistAction extends Action {
  public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    req.setCharacterEncoding("UTF-8");

	 // ユーザ（教師）データを取得する
	 HttpSession session = req.getSession();
	 Teacher teacher = (Teacher)session.getAttribute("user");

	 // ユーザ情報がない場合はエラーメッセージを出す。
	 if (teacher != null) {
         req.setAttribute("error", "ユーザー情報が取得できませんでした。ログインし直してください。");
         req.getRequestDispatcher("error.jsp").forward(req, res);
         return;
	 }
	 // クラス・科目データを取得する
	 ClassNumDao classDao = new ClassNumDao();
	 SubjectDao subjectDao = new SubjectDao();

	 // ユーザが所属している学校に基づいてクラス一覧と科目一覧を所得する
	 List<String> classList = classDao.filter(teacher.getSchool());
	 List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

     // JSPから入力値を取得（リクエストパラメータ）
     String entYearStr = req.getParameter("f1");      // 入学年度
     String classNum = req.getParameter("f2");         // クラス番号
     String subjectCd = req.getParameter("f3");        // 科目コード
     String noStr = req.getParameter("f4");         // 回数

	 // 入学年度と回数を0でデフォルト値とする
	 int entYear = 0;
	 int no = 0;

	 // エラーメッセージを格納する
	 Map<String, String> errors = new HashMap<>();

     // 入学年度のバリデーション
     try {
         if (entYearStr != null && !entYearStr.isEmpty()) {
             entYear = Integer.parseInt(entYearStr);
         } else {
             errors.put("f1", "入学年度を入力してください。");
         }
     } catch (NumberFormatException e) {
         errors.put("f1", "入学年度は数字で入力してください。");
     }

     // クラスが未選択の場合
     if (classNum == null || classNum.equals("0")) {
         errors.put("f2", "クラスを選択してください。");
     }

     // 科目が未選択の場合
     if (subjectCd == null || subjectCd.equals("0")) {
         errors.put("f3", "科目を選択してください。");
     }

     // 回数のバリデーション
     try {
         if (noStr != null && !noStr.isEmpty()) {
             no = Integer.parseInt(noStr);
             if (no <= 0) {
                 errors.put("f4", "回数は1以上の数字で入力してください。");
             }
         } else {
             errors.put("f4", "回数を入力してください。");
         }
     } catch (NumberFormatException e) {
         errors.put("f4", "回数は数字で入力してください。");
     }
     // 成績データのリストを初期化
     List<Test> testList = null;

     // 入力チェックエラーがなければDBから成績データを取得
     // TestDaoがないためエラー表示
     if (errors.isEmpty()) {
//////////////         TestDao testDao = new TestDao();
/////////////         testList = testDao.filter(teacher.getSchool().getCd(), entYear, classNum, subjectCd, no);
     }

     // 入力内容と結果・エラーをJSPに渡す（再表示・状態保持用）
     req.setAttribute("f1", entYearStr);
     req.setAttribute("f2", classNum);
     req.setAttribute("f3", subjectCd);
     req.setAttribute("f4", noStr);

     req.setAttribute("classList", classList);     // クラスの選択肢
     req.setAttribute("subjectList", subjectList); // 科目の選択肢
     req.setAttribute("testList", testList);       // 検索結果の成績リスト
     req.setAttribute("errors", errors);           // 入力エラーメッセージ

	 // test_regist.jspへフォワードする
	 req.getRequestDispatcher("test_regist.jsp").forward(req, res);

 }
}