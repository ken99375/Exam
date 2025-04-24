package scoremanager.main;

import java.io.IOException;
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

				// jspに渡すデータをセット(不明)
				 req.setAttribute("classList", classlist);
				 req.setAttribute("subjectList", subjectList);

		 } else {
			 req.setAttribute("error", "ユーザー情報が取得できませんでした。ログインし直してください。");
		 }


		 //
		 req.getRequestDispatcher("test_list.jsp").forward(req,res);

	}
}
