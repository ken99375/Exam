package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class ClassCreateAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		req.getRequestDispatcher("class_create.jsp").forward(req, res);
	}
}