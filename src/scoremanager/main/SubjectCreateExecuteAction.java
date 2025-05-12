package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

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
			Map<String, String> errors = new HashMap<>();

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
			Subject duplication = dao.allGet(cd, teacher.getSchool());
			if (duplication != null) {
				errors.put("duplication", "科目コードが重複しています");
				errorBack(req, res, errors, "subject_create.jsp");
				return;
			} else {
				boolean result = dao.save(subject);

				// boolean型の場合if(result)でおけ。
				if (result){
					req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
				} else {
					req.getRequestDispatcher("/error.jsp").forward(req, res);
				}
			}
		} catch (Exception e){
//			req.getRequestDispatcher("/error.jsp").forward(req, res);
			e.printStackTrace();
		}
	}
}
