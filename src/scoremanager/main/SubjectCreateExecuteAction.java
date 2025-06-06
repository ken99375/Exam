package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception{
		try{
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");
			Map<String, String> errors = new HashMap<>();

			String cd = req.getParameter("cd");
			String name = req.getParameter("name");

			// ★ここにログを追加
			System.out.println("teacher.school_cd: " + teacher.getSchool().getCd());
			System.out.println("入力されたcd: " + cd);
			System.out.println("入力されたname: " + name);
			// ✅ ログ出力ここに入れる！
			System.out.println("teacher.school_cd: " + teacher.getSchool().getCd());
			System.out.println("teacher.school オブジェクト: " + teacher.getSchool());

			School school = teacher.getSchool();
			System.out.println("School.class: " + school.getClass().getName());


			// この後に条件分岐でエラー文字設定と同じページに戻る処理
            // 入力バリデーション
            if (cd == null || cd.trim().isEmpty()) {
                errors.put("cd", "科目コードを入力してください。");
            } else if (cd.trim().length() != 3) {
                errors.put("cd", "科目コードは3文字で入力してください。");

            }
            if (name == null || name.trim().isEmpty()) {
                errors.put("name", "科目名を入力してください。");
            }
            if (!errors.isEmpty()) {
                // 入力値の再セット
                req.setAttribute("cd", cd);
                req.setAttribute("name", name);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("subject_create.jsp").forward(req, res);
                return;
            }

			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());

			SubjectDao dao = new SubjectDao();
			Subject duplication = dao.allGet(cd, teacher.getSchool());

			if (duplication != null) {
                errors.put("duplication", "科目コードが重複しています。");
                req.setAttribute("cd", cd);
                req.setAttribute("name", name);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("subject_create.jsp").forward(req, res);
				return;
			}
				boolean result = dao.save(subject);

				// boolean型の場合if(result)でおけ。
				if (result){
					req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
				} else {
					req.getRequestDispatcher("/error.jsp").forward(req, res);
				}

		} catch (Exception e){
			req.getRequestDispatcher("/error.jsp").forward(req, res);
			e.printStackTrace();
		}
	}
}
