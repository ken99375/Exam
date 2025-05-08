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

// 学生に紐づく成績を取得して表示するアクションクラス
public class TestListStudentExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		try {
			// エラーメッセージを保持するメソッドを初期化
			Map<String, String> errors = new HashMap<>();
			// 	セッションからログイン中の教師情報を取得
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");

			// DAOの初期化
			// データベースのアクセスのために必要
			TestListStudentDao tls_dao = new TestListStudentDao();
			StudentDao stu_dao = new StudentDao();

			// 送られてきたパラメータで学生を特定できるらしい。
			// データベースの設計が甘い可能性有り。
			// パラメータから学生コードを取得して、該当学生を取得。
			String stu_cd = req.getParameter("cd");
			Student student = stu_dao.get(stu_cd);

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
			// 	入学年度をリスト化する
			List<Integer> entYearSet = new ArrayList<>();
			// 10年前から1年後まで年をリストに追加
			for (int i = year -10; i < year + 10; i++) {
				entYearSet.add(i);
			}
			// JSPへ渡すデータをリクエストにセット
			req.setAttribute("student_name", student.getName());// 学生名
			req.setAttribute("c_list", c_list);					// クラス一覧
			req.setAttribute("sub_list", sub_list);				// 科目一覧
			req.setAttribute("ent_year_set", entYearSet);		// 入学年度セット
			// 特定した学生で成績を取得する
			List<TestListStudent> stu_list = tls_dao.filter(student);
				// 成績が存在しない場合はエラーメッセージを表示する
				if (stu_list == null){
					errors.put("ets", "この学生のテスト履歴はありません");
				}
				// エラー文字設定されたとき元のページへ戻る
				if (!errors.isEmpty()){
					req.setAttribute("errors", errors);
					errorBack(req, res, errors, "test_list_student.jsp");
					return;
				}
			// デバッグ用出力
			System.out.println(stu_list);
			// 成績リストをtesu_list_student.jspにフォワードする
			req.setAttribute("stu_list", stu_list);
			req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
		} catch(Exception e) {
			// 例外が発生した場合、エラーページにフォワードする
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
