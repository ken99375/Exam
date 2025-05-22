package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class SignUp2Action extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		//JSPへフォワード
		req.getRequestDispatcher("signup_account.jsp").forward(req, res);
	}
}