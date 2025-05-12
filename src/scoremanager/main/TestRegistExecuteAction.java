package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		try {
			// エラーメッセージを保持するMapを初期化
			Map<String, String> errors = new HashMap<>();
			// セッションからユーザーデータ（teacher）を取得
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");

			// フォームから受取る情報（複数生徒の点数登録に対応）
			String[] studentNos = req.getParameterValues("studentNo");// 生徒番号
			String subjectCds = req.getParameter("subjectCd"); // 科目コード (単数)
			int no = Integer.parseInt(req.getParameter("no")); // テスト番号
			String[] pointStr = req.getParameterValues("po"); // 点数（複数）

			// 点数をint型に変換し、数値に異常がないか審査する
			int[] points = new int[pointStr.length];
			for (int i = 0; i < pointStr.length; i++){
				try {
					int tyeckpoint = Integer.parseInt(pointStr[i]);
					// 点数が0～100の範囲かチェックする
					if (tyeckpoint < 0 || 100 < tyeckpoint){
						errors.put("po" + i, "0~100の範囲で入力してください");
					}
					points[i] = tyeckpoint;
				} catch(NumberFormatException e){
					// 数値でない場合はエラーを出す
					errors.put("po" + i, "数値で入力してください");
				}
			}
			// エラーが発生したら入力画面に戻す
			if (!errors.isEmpty()){
				errorBack(req, res, errors, "TestRegist.action");
				return;
			}

			// 科目コードから受け取るからdaoで科目オブジェクトを取ってくる
			SubjectDao sub_dao = new SubjectDao();
			Subject subject = sub_dao.get(subjectCds, teacher.getSchool());

			// テスト登録用のTestオブジェクトを構築
			List<Test> updateTest = new ArrayList<>();
			StudentDao s_dao = new StudentDao();

			// 学生情報を取得
			for (int i = 0; i < studentNos.length; i++){
				// 学生番号はRegistActionクラスで一意に決まってるから
				// testオブジェクトには学生オブジェクトを使用して代入
				Student student = s_dao.get(studentNos[i]);

				Test test = new Test();
				test.setStudent(student);
				test.setClassNum(student.getClassNum());// クラス情報を学生から取得
				test.setSubject(subject);// 科目
				test.setSchool(teacher.getSchool());// 所属学校
				test.setNo(no);// テスト番号
				test.setPoint(points[i]);// 点数
				updateTest.add(test);// 登録リストに追加
			}

			// Daoを使用してテスト情報を保存
			TestDao test_dao = new TestDao();
			boolean result = test_dao.save(updateTest);

			// boolean型の場合if(result)でおけ。
			// 登録成功--->完了ページ
			// 登録失敗--->エラーページへフォワード
			if (result){
				req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
			} else {
				req.getRequestDispatcher("/error.jsp").forward(req, res);
			}

		} catch (Exception e){
			// エラーページのログ出力＆エラーページにフォワード
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}