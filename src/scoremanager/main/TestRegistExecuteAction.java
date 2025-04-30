///// 12,74,80,81でエラー


package scoremanager.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
//////////////////////		import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception, IOException {
    	HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

    	//  変数名の変更有

    	String[] studentNos = req.getParameterValues("student_no");
		String subjectCds = req.getParameter("subject_cd");
		int no = Integer.parseInt(req.getParameter("no"));
		String[] pointStr = req.getParameterValues("point");
		int[] points = new int[pointStr.length];
		for (int i = 0; i < pointStr.length; i++){
			points[i] = Integer.parseInt(pointStr[i]);
		}

        // 点数と回数を0でデフォルト値とする
//        int point = 0;
//        int no = 0;

        // 変数が配列だから入力チェックは特殊
//        // 入力チェック
//        boolean hasError = false;
//        StringBuilder errorMsg = new StringBuilder();
//
//        if (studentNo == null || studentNo.isEmpty()) {
//            hasError = true;
//            errorMsg.append("学生番号が未入力です。<br>");
//        }
//
//        if (subjectCd == null || subjectCd.equals("0")) {
//            hasError = true;
//            errorMsg.append("科目が未選択です。<br>");
//        }
//
//        try {
//            point = Integer.parseInt(pointStr);
//            if (point < 0 || point > 100) {
//                hasError = true;
//                errorMsg.append("点数は0〜100の範囲で入力してください。<br>");
//            }
//        } catch (NumberFormatException e) {
//            hasError = true;
//            errorMsg.append("点数は数字で入力してください。<br>");
//        }
//
//        try {
//            no = Integer.parseInt(noStr);
//            if (no <= 0) {
//                hasError = true;
//                errorMsg.append("回数は1以上で入力してください。<br>");
//            }
//        } catch (NumberFormatException e) {
//            hasError = true;
//            errorMsg.append("回数は数字で入力してください。<br>");
//        }

//        // 入力エラーがある場合は元の画面に戻す
//        if (hasError) {
//            req.setAttribute("error", errorMsg.toString());
//            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
//            return;
//        }

//        // Testオブジェクトを作成してデータを保存
//        Test test = new Test();
/////////        test.setStudent_id(studentNo);
//        test.setSubject_cd(subjectCd);
//        test.setPoint(point);
//        test.setNo(no);

        // TestDaoがないためエラー
///////        TestDao testDao = new TestDao();
///////        testDao.insert(test); // DBに登録

		SubjectDao sub_dao = new SubjectDao();
		Subject subject = sub_dao.get(subjectCds, teacher.getSchool());

		List<Test> updateTest = new ArrayList<>();
		StudentDao s_dao = new StudentDao();
		for (int i = 0; i < studentNos.length; i++){
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
    }
}
