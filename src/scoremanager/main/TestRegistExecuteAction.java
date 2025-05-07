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
			Map<String, String> errors = new HashMap<>();
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");

			// 学生番号、科目コード、テスト番号、点数をリスト形式で受け取る
			String[] studentNos = req.getParameterValues("studentNo");
			String subjectCds = req.getParameter("subjectCd"); // 科目コードとテスト番号は絞り込んでるから一意
			int no = Integer.parseInt(req.getParameter("no"));
			String[] pointStr = req.getParameterValues("po");
			int[] points = new int[pointStr.length];
			for (int i = 0; i < pointStr.length; i++){
				try {
					int tyeckpoint = Integer.parseInt(pointStr[i]);
					if (tyeckpoint < 0 || 100 < tyeckpoint){
						errors.put("po" + i, "0~100の範囲で入力してください");
					}
					points[i] = tyeckpoint;
				} catch(NumberFormatException e){
					errors.put("po" + i, "数値で入力してください");
				}
			}
			if (!errors.isEmpty()){
				errorBack(req, res, errors, "TestRegist.action");
				return;
			}

			// 科目コードから受け取るからdaoで科目オブジェクトを取ってくる
			SubjectDao sub_dao = new SubjectDao();
			Subject subject = sub_dao.get(subjectCds, teacher.getSchool());

			List<Test> updateTest = new ArrayList<>();
			StudentDao s_dao = new StudentDao();
			for (int i = 0; i < studentNos.length; i++){
				// 学生番号はRegistActionクラスで一意に決まってるから
				// testオブジェクトには学生オブジェクトを使用して代入
				Student student = s_dao.get(studentNos[i]);

				Test test = new Test();
				test.setStudent(student);
				test.setClassNum(student.getClassNum());
				test.setSubject(subject);
				test.setSchool(teacher.getSchool());
				test.setNo(no);
				test.setPoint(points[i]);
				updateTest.add(test);
			}

			TestDao test_dao = new TestDao();
			boolean result = test_dao.save(updateTest);

			// boolean型の場合if(result)でおけ。
			if (result){
				req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
			} else {
				req.getRequestDispatcher("/error.jsp").forward(req, res);
			}

		} catch (Exception e){
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
