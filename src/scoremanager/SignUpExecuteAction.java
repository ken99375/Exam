package scoremanager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TeacherDao;
import tool.Action;

public class SignUpExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// 入力された学校コードの取得
		String schoolCd = req.getParameter("school_cd");
		String url = "";
		List<String> errors = new ArrayList<>();

		// 学校コードの存在確認（teacher テーブルを使う）
		TeacherDao teacherDao = new TeacherDao();  // 変更ポイント
		boolean validSchool = teacherDao.authenticate(schoolCd);  // 変更ポイント

		if (validSchool) {
			// 正常：セッション作成＆保存
			HttpSession session = req.getSession(true);
			session.setAttribute("school_cd", schoolCd);

			// リダイレクト
			url = "signup_account.jsp";
			res.sendRedirect(url);
		} else {
			// エラー処理
			errors.add("学校コードが存在しません。もう一度確認してください。");
			req.setAttribute("errors", errors);
			req.setAttribute("school_cd", schoolCd);

			url = "signup_school.jsp";
			req.getRequestDispatcher(url).forward(req, res);
		}
	}
}
