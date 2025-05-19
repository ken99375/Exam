package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassListAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception{
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");
			ClassNumDao c_dao = new ClassNumDao();

			List<String> c_list = c_dao.filter(teacher.getSchool());
			req.setAttribute("c_list", c_list);

			req.getRequestDispatcher("class_list.jsp").forward(req, res);

		} catch (Exception e){
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
