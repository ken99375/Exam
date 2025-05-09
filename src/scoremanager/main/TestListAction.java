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
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

// 成績一覧表示するクラス
public class TestListAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {

		try {
			// エラーメッセージを保持するMapを初期化
			Map<String, String> errors = new HashMap<>();

			// セッションからログイン情報(teacher)を取得する
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");

			// 今日の日付を取得し、現在の年を取得する
			LocalDate todaysDate = LocalDate.now();
			 int year = todaysDate.getYear();

			// 先生の所属する学校のクラスリストを取得する
			ClassNumDao c_dao = new ClassNumDao();
			List<String> c_list = c_dao.filter(teacher.getSchool());

				// クラスが存在しない場合はエラーメッセージを表示する
				if (c_list.isEmpty()) {
					errors.put("c_error", "クラスが存在しません");
				}
			// 先生の所属する学校の科目データを取得する
			SubjectDao sub_dao = new SubjectDao();
			List<Subject> sub_list = sub_dao.filter(teacher.getSchool());
				// 科目が存在しない場合はエラーメッセージを表示する
				if (sub_list.isEmpty()) {
					errors.put("sub_error", "科目が存在しません");
				}
				// エラー文字設定されたとき元のページへ戻る
				if (!errors.isEmpty()){
					req.setAttribute("errors", errors);
					errorBack(req, res, errors, "test_list.jsp");
					return;
				}
			// 入学年度をリスト化する
			List<Integer> entYearSet = new ArrayList<>();
			 // 10年前から1年後まで年をリストに追加
			 for (int i = year -10; i < year + 10; i++) {
				 entYearSet.add(i);
			 }
			// JSPへ渡すデータをリクエストにセット
			req.setAttribute("c_list", c_list);			// クラス一覧
			req.setAttribute("sub_list", sub_list);		// 科目一覧
			req.setAttribute("ent_year_set", entYearSet);// 入学年度セット

			// test_list.jspにフォワードする
			req.getRequestDispatcher("test_list.jsp").forward(req, res);

		} catch (Exception e){
			// 例外が発生した場合、エラーページにフォワードする
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
