package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class StudentCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		//JSPへフォワード 7
		req.getRequestDispatcher("student_create.jsp").forward(req, res);
	}
}