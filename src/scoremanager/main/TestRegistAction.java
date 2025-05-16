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
import bean.Test;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		try {
			// エラーメッセージの格納Map
			Map<String, String> errors = new HashMap<>();
			// セッションからユーザーを取得
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");
			// 科目DAOのインスタンスを作成
			SubjectDao sub_dao = new SubjectDao();

			// 入学年度を取得し、数値に変換する
			int entYear = 0;
			String entYearStr = req.getParameter("ent_year");

				if (entYearStr != null ){
					entYear = Integer.parseInt(entYearStr);
				}
			// 入学年度が指定されている場合...
			if (entYear != 0 ){
				// クラス、科目コード、回数を取得
				String classNum = req.getParameter("class_num");
				String subjectCd = req.getParameter("cd");
				int times = Integer.parseInt(req.getParameter("times"));

				// 科目コードと学校情報から科目を取得
				Subject subject = sub_dao.get(subjectCd, teacher.getSchool());

				// 処理到達のチェック
				System.out.println(entYear);
				// テストDAOから、指定条件に一致するテスト一覧を取得
				TestDao testDao = new TestDao();
				List<Test> test_li = testDao.filter(entYear, classNum, subject, times, teacher.getSchool());

				// 処理到達のチェック
				System.out.println(test_li);
				// JSPに渡すデータをリクエストスコープにセット
				req.setAttribute("test_li", test_li);
				req.setAttribute("subject", subject);
				req.setAttribute("times", times);
			}
			// 処理到達のチェック
			System.out.println(entYear);

			LocalDate todaysDate = LocalDate.now(); // LcalDateインスタンスを取得
			 int year = todaysDate.getYear(); // 現在の年を取得
			// 先生の所属する学校のクラスリストを取得
			ClassNumDao c_dao = new ClassNumDao();
			List<String> c_list = c_dao.filter(teacher.getSchool());
			if (c_list.isEmpty()) {
				errors.put("c_error", "クラスが存在しません");
			}
			// 先生の所属する学校の科目データを持ってくる
			List<Subject> sub_list = sub_dao.filter(teacher.getSchool());
			if (sub_list.isEmpty()) {
				errors.put("sub_error", "科目が存在しません");
			}
			List<Integer> entYearSet = new ArrayList<>();
			 // 10年前から1年後まで年をリストに追加
			 for (int i = year -10; i < year + 10; i++) {
				 entYearSet.add(i);
			 }
			// エラー文字設定されたとき元のページへ戻る
			if (!errors.isEmpty()){
				req.setAttribute("errors", errors);
				errorBack(req, res, errors, "error.jsp");
				return;
			}

			// JSPに渡す補助データをリクエストスコープに補助
			req.setAttribute("c_list", c_list);
			req.setAttribute("sub_list", sub_list);
			req.setAttribute("ent_year_set", entYearSet);

			// test.regist.jspにフォワード
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);

		} catch (Exception e){
			// エラーページのログ出力＆エラーページにフォワード
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
