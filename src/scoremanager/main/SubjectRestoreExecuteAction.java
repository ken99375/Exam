package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectRestoreExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");

			String cd = req.getParameter("cd");
			String name = req.getParameter("name");

			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());

			SubjectDao dao = new SubjectDao();
			boolean result = dao.restore(subject);

			if (result){
				req.getRequestDispatcher("subject_restore_done.jsp").forward(req, res);
			}else {
				req.getRequestDispatcher("/error.jsp").forward(req, res);
			}

		} catch(Exception e){
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
