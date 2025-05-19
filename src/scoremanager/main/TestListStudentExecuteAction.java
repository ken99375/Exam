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

public class TestListStudentExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		try {
			Map<String, String> errors = new HashMap<>();
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");
			LocalDate todaysDate = LocalDate.now(); // LcalDateインスタンスを取得
			 int year = todaysDate.getYear(); // 現在の年を取得
			// 先生の所属する学校のクラスリストを持ってくる
			ClassNumDao c_dao = new ClassNumDao();
			List<String> c_list = c_dao.filter(teacher.getSchool());
			if (c_list.isEmpty()) {
				errors.put("c_error", "クラスが存在しません");
			}
			// 先生の所属する学校の科目データを持ってくる
			SubjectDao sub_dao = new SubjectDao();
			List<Subject> sub_list = sub_dao.filter(teacher.getSchool());
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

			TestListStudentDao tls_dao = new TestListStudentDao();
			StudentDao stu_dao = new StudentDao();

			// 送られてきたパラメータで学生を特定できるらしい。
			// データベースの設計が甘い可能性有り。
			String stu_cd = req.getParameter("cd");
			Student student = stu_dao.get(stu_cd);
			req.setAttribute("tes", "学生");
			if (student == null){
				errors.put("ets1", "成績情報が存在しませんでした");
				req.setAttribute("errors", errors);
				errorBack(req, res, errors, "test_list_student.jsp");
				return;
			}

			// 特定した学生で成績をとってくる
			List<TestListStudent> stu_list = tls_dao.filter(student);
			if (stu_list == null || stu_list.isEmpty()){
				System.out.println("生徒はいるが成績がない生徒");
				req.setAttribute("student", student);
				errors.put("ets2", "成績情報が存在しませんでした");
			}
			// エラー文字設定されたとき元のページへ戻る
			if (!errors.isEmpty()){
				req.setAttribute("errors", errors);
				errorBack(req, res, errors, "test_list_student.jsp");
				return;
			}
			req.setAttribute("item", student.getName());

			System.out.println(stu_list);
			req.setAttribute("stu_list", stu_list);
			req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
		} catch(Exception e) {
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
