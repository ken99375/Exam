package scoremanager.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

	// エラー文字を設定してページを戻すコードが多すぎるからここでまとめる
		public void errorBack(HttpServletRequest req, HttpServletResponse res,
				Map<String, String> errors, String jsp
				) throws ServletException, IOException{
			req.setAttribute("errors", errors);
			req.getRequestDispatcher(jsp).forward(req, res);
		}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		HttpSession session = req.getSession();
		// セッション保持中の教師のもつ学校情報で追加処理をしたいので。
		Teacher teacher = (Teacher)session.getAttribute("user");
		Map<String, String> errors = new HashMap<>();
		//Map<String, List<String>> errors = new HashMap<>();
		try {
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
				errorBack(req, res, errors, "student_create.jsp");
				return;
			} else {
				entYear = Integer.parseInt(entYearStr);
			}
			// この部分はjspで必須項目にしてるからなくてもいいが一応エラー文字追加
			if (name == null || name.isEmpty()) {
				errors.put("name", "氏名が入力されていません。");
				errorBack(req, res, errors, "student_create.jsp");
				return;
			}
			// この部分はjspで必須項目にしてるからなくてもいいが一応エラー文字追加
			if (no == null || no.isEmpty()) {
				errors.put("no", "学生番号が入力されていません。");
				errorBack(req, res, errors, "student_create.jsp");
				return;
			}
			if (classnum == null || classnum.isEmpty()) {
				errors.put("classnum", "クラスが入力されていません。");
				errorBack(req, res, errors, "student_create.jsp");
				return;
			}



			// ここから作成処理
			StudentDao dao = new StudentDao();
			// シングルアクセスの場合はこのエラー表示設定でおｋ
			Student duplication = dao.get(no);
			if (duplication != null){
				errors.put("no", "その学生番号は既に登録されています。");
				errorBack(req, res, errors, "student_create.jsp");
				// フォワード後に処理を抜けるためreturnを記述。
				return;
			} else if (duplication == null){
				// 重複がない場合追加
				Student student = new Student();
				student.setNo(no);
				student.setName(name);
				student.setClassNum(classnum);
				student.setEntYear(entYear);
				student.setAttend(attend);
				student.setSchool(teacher.getSchool());
				// 追加行数が０より大きい場合trueを返す処理があるので
				// 追加が失敗したときfalseが返され、エラーページへ飛ぶ。
				boolean result = dao.save(student);
				// boolean型の場合if(result)でおけ。
				if (result){
					req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
					return;
				}
				if (!result){
					// Dao層でデータベースの重複チェック
					errors.put("no", "その学生番号は既に登録されています。");
					errorBack(req, res, errors, "student_create.jsp");
					// フォワード後に処理を抜けるためreturnを記述。
					return;
				}
			}
		} catch (Exception e){
			//JSPへフォワード 7
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}