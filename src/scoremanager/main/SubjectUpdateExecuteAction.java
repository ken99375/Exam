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

public class SubjectUpdateExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");
			Map<String, String> errors = new HashMap<>();

			String cd = req.getParameter("cd");
			String name = req.getParameter("name");

			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());

			SubjectDao dao = new SubjectDao();
			// 別画面で削除されていた場合のチェック機能
			Subject sub = dao.get(cd, teacher.getSchool());
			System.out.println(sub);
			if (sub == null) {
				errors.put("dele", "科目が存在していません");
				req.setAttribute("errors", errors);
				errorBack(req, res, errors, "subject_update.jsp");
				return;
			}

			boolean result = dao.save(subject);

			// boolean型の場合if(result)でおけ。
			if (result){
				req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
			} else {
				req.getRequestDispatcher("/error.jsp").forward(req, res);
			}

		} catch (Exception e){
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
