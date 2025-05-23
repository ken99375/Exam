package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		try {
			Map<String, String> errors = new HashMap<>();
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");
			SubjectDao dao = new SubjectDao();

			req.setAttribute("tes", "科目");
			String entYearStr = req.getParameter("ent_year");
			String classNum = req.getParameter("class_num");
			String cd = req.getParameter("cd");

			LocalDate todaysDate = LocalDate.now(); // LcalDateインスタンスを取得
			 int year = todaysDate.getYear(); // 現在の年を取得
			// 先生の所属する学校のクラスリストを持ってくる
			ClassNumDao c_dao = new ClassNumDao();
			List<ClassNum> c_list = c_dao.filter(teacher.getSchool());
			if (c_list.isEmpty()) {
				errors.put("c_error", "クラスが存在しません");
			}
			// 先生の所属する学校の科目データを持ってくる

			List<Subject> sub_list = dao.filter(teacher.getSchool());
			if (sub_list.isEmpty()) {
				errors.put("sub_error", "科目が存在しません");
			}
			List<Integer> entYearSet = new ArrayList<>();
			 // 10年前から1年後まで年をリストに追加
			 for (int i = year -10; i < year + 10; i++) {
				 entYearSet.add(i);
			 }
				req.setAttribute("c_list", c_list);
				req.setAttribute("sub_list", sub_list);
				req.setAttribute("ent_year_set", entYearSet);

			int entYear = 0;
			// パラメータエラーチェック
			if (entYearStr == null || entYearStr.trim().isEmpty() ||
					classNum   == null || classNum.trim().isEmpty() ||
					cd  == null || cd.trim().isEmpty()){
				errors.put("filter", "入学年度とクラスと科目を選択してください");
				errorBack(req, res, errors, "test_list.jsp");
				return;
			} else {
				entYear = Integer.parseInt(entYearStr);
			}

			Subject subject = dao.get(cd, teacher.getSchool());
			req.setAttribute("item", subject.getName());
			// TestListSubjectDaoのメソッドを使用
			TestListSubjectDao tl_sub_dao = new TestListSubjectDao();
			List<TestListSubject> sub_li = tl_sub_dao.filter(entYear, classNum, subject, teacher.getSchool());
			if (sub_li.isEmpty()) {
				errors.put("etsub1", "学生情報が存在しませんでした");
			}
			// エラー文字設定されたとき元のページへ戻る
			if (!errors.isEmpty()){
				errorBack(req, res, errors, "test_list_student.jsp");
				return;
			}
			req.setAttribute("sub_li", sub_li);
//			System.out.println(sub_li);
			req.getRequestDispatcher("test_list_student.jsp").forward(req, res);;


		} catch (Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
