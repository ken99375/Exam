package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception{
		try{
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");

			// jspで必須項目にしてるからいいと思うけど
			// アクションクラス側でもパラメータ有り無しでエラー表示したい場合
			// String cd = "";
			// String name = "";
			String cd = req.getParameter("cd");
			String name = req.getParameter("name");
			// この後に条件分岐でエラー文字設定と同じページに戻る処理

			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());

			SubjectDao dao = new SubjectDao();
			boolean result = dao.save(subject);

			// boolean型の場合if(result)でおけ。
			if (result){
				req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
			} else {
				req.getRequestDispatcher("/error.jsp").forward(req, res);
			}
		} catch (Exception e){
//			req.getRequestDispatcher("/error.jsp").forward(req, res);
			e.printStackTrace();
		}
	}
}
