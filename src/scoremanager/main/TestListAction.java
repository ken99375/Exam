package scoremanager.main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestListAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception, IOException {
		 // ユーザ（教師）データを取得する
		 HttpSession session = req.getSession();
		 Teacher teacher = (Teacher)session.getAttribute("user");

		 LocalDate todaysDate = LocalDate.now(); // LcalDateインスタンスを取得
		 int year = todaysDate.getYear(); // 現在の年を取得
		 // ユーザ情報があった場合、→
		 	// ユーザが所属しているクラスと科目データを取得する
		 // ない場合はエラーメッセージを出す。
		 if (teacher != null) {

				// ログインユーザーの学校コードをもとにクラス情報を取得
			 	ClassNumDao classDao = new ClassNumDao();
				List<String> classlist = classDao.filter(teacher.getSchool());

				// ログインユーザーの学校コードをもとに科目情報を取得
				SubjectDao subjectDao = new SubjectDao();
				List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

				List<Integer> entYearSet = new ArrayList<>();
				 // 10年前から1年後まで年をリストに追加
				 for (int i = year -10; i < year + 10; i++) {
					 entYearSet.add(i);
				 }

				// jspに渡すデータをセット(不明)
				 req.setAttribute("classList", classlist);
				 req.setAttribute("subjectList", subjectList);
				 req.setAttribute("ent_year_set", entYearSet);
				 req.getRequestDispatcher("test_list.jsp").forward(req,res);
		 } else {
			 req.setAttribute("error", "ユーザー情報が取得できませんでした。ログインし直してください。");
		 }
	}
}
