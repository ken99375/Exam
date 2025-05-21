package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassDeleteExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");

			String classnum = req.getParameter("classnum");
			String name = req.getParameter("name");

            // クラス情報を更新
            ClassNum classNum = new ClassNum();
            classNum.setClassNum(classnum);
            classNum.setName(name);
            classNum.setSchool(teacher.getSchool());

			ClassNumDao dao = new ClassNumDao();
			boolean result = dao.deleteAttend(classNum);

			if (result){
				req.getRequestDispatcher("class_delete_done.jsp").forward(req, res);
			}else {
				req.getRequestDispatcher("/error.jsp").forward(req, res);
			}

		} catch(Exception e){
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
