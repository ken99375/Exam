package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		//ローカル変数の宣言 1
		String url = "";
		HttpSession session=req.getSession();

		//ビジネスロジック 4
		if (session.getAttribute("user") != null) {
			session.invalidate();
		}

		//JSPへフォワード 7
		url = "logout.jsp";
		req.getRequestDispatcher(url).forward(req, res);
	}

}
