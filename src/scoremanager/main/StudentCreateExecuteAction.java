package scoremanager.main;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		PrintWriter out = res.getWriter();
		try {

			HttpSession session = req.getSession();
			// セッション保持中の教師のもつ学校情報で追加処理をしたいので。
			Teacher teacher = (Teacher)session.getAttribute("user");
			Map<String, String> errors = new HashMap<>();
			//Map<String, List<String>> errors = new HashMap<>();

			String no = "";
			int entYear = 0;
			String entYearStr = "";
			String  name = "";
			String classnum = "";
			boolean attend = true; // 登録時に在籍する学生としてデフォルト設定

			// フォームからのパラメータ受け取り
			no = req.getParameter("no");
			name = req.getParameter("name");
			entYearStr = req.getParameter("entYear");
			classnum = req.getParameter("classNum");

			// パラメータ取得後の未入力時は登録ページへ戻る
			if (entYearStr == null || entYearStr.isEmpty()){
				errors.put("entyear", "入学年度を選択してください。");
				req.setAttribute("errors", errors);
				req.getRequestDispatcher("student_create.jsp").forward(req, res);
			} else {
				entYear = Integer.parseInt(entYearStr);
			}
			// この部分はjspで必須項目にしてるからなくてもいいが一応エラー文字追加
			if (name == null || name.isEmpty()) {
				errors.put("name", "氏名が入力されていません。");
				req.setAttribute("errors", errors);
				req.getRequestDispatcher("student_create.jsp").forward(req, res);
			}
			// この部分はjspで必須項目にしてるからなくてもいいが一応エラー文字追加
			if (no == null || no.isEmpty()) {
				errors.put("no", "学生番号が入力されていません。");

				req.setAttribute("errors", errors);
				req.getRequestDispatcher("student_create.jsp").forward(req, res);
			}
			if (classnum == null || classnum.isEmpty()) {
				errors.put("classnum", "クラスが入力されていません。");
				req.setAttribute("errors", errors);
				req.getRequestDispatcher("student_create.jsp").forward(req, res);
			}



			Student student = new Student();
			student.setNo(no);
			student.setName(name);
			student.setClassNum(classnum);
			student.setEntYear(entYear);
			student.setAttend(attend);
			student.setSchool(teacher.getSchool());

			StudentDao dao = new StudentDao();
			// 追加行数が０より大きい場合trueを返す処理が返されるので
			// 追加が失敗したときfalseが返され、エラーページへ飛ぶ。
			boolean result = dao.save(student);

			// boolean型の場合if(result)でおけ。
			if (result){
				req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
			} else {
				req.getRequestDispatcher("/error.jsp").forward(req, res);
			}

		} catch (Exception e){
			//JSPへフォワード 7
//			req.getRequestDispatcher("/error.jsp").forward(req, res);
			e.printStackTrace(out);
		}
	}
}