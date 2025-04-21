package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class LoginAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		//JSPへフォワード 7
		req.getRequestDispatcher("login.jsp").forward(req, res);
	}
}