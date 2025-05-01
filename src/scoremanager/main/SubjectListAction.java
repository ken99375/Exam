package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");

			SubjectDao dao = new SubjectDao();

			List<Subject> list = dao.filter(teacher.getSchool());

			session.setAttribute("subject_list", list);

			req.getRequestDispatcher("subject_list.jsp").forward(req, res);

		} catch(Exception e) {
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
