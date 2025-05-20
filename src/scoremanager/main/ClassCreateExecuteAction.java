package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassCreateExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception{
		try{
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");
			Map<String, String> errors = new HashMap<>();

			String classnum = req.getParameter("classnum");// クラスコード
			String name = req.getParameter("name");// クラス名

			// この後に条件分岐でエラー文字設定と同じページに戻る処理
            // 入力バリデーション
            if (classnum == null || classnum.trim().isEmpty()) {
                errors.put("classnum", "クラスコードを入力してください。");
            }
            if (name == null || name.trim().isEmpty()) {
                errors.put("name", "クラス名を入力してください。");
            }
            if (!errors.isEmpty()) {
                // 入力値の再セット
                req.setAttribute("classnum", classnum);
                req.setAttribute("name", name);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("class_create.jsp").forward(req, res);
                return;
            }

			ClassNum classNum = new ClassNum();
			classNum.setClassNum(classnum);
			classNum.setName(name);
			classNum.setSchool(teacher.getSchool());

			ClassNumDao dao = new ClassNumDao();
			ClassNum duplication = dao.get(classnum, teacher.getSchool());

			if (duplication != null) {
                errors.put("duplication", "クラスコードが重複しています。");
                req.setAttribute("classnum", classnum);
                req.setAttribute("name", name);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("class_create.jsp").forward(req, res);
				return;
			}
				boolean result = dao.save(classNum);

				// boolean型の場合if(result)でおけ。
				if (result){
					req.getRequestDispatcher("class_create_done.jsp").forward(req, res);
				} else {
					req.getRequestDispatcher("/error.jsp").forward(req, res);
				}

		} catch (Exception e){
			req.getRequestDispatcher("/error.jsp").forward(req, res);
			e.printStackTrace();
		}
	}
}
