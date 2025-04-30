

package scoremanager.main;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Test;
//////////////////////		import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception, IOException {
        // 入力パラメータの取得
        String studentNo = req.getParameter("student_no"); // 学生番号
        String subjectCd = req.getParameter("subject_cd"); // 科目コード
        String pointStr = req.getParameter("point");       // 点数
        String noStr = req.getParameter("no");       // 回数

        // 点数と回数を0でデフォルト値とする
        int point = 0;
        int no = 0;

        // 入力チェック
        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder();

        if (studentNo == null || studentNo.isEmpty()) {
            hasError = true;
            errorMsg.append("学生番号が未入力です。<br>");
        }

        if (subjectCd == null || subjectCd.equals("0")) {
            hasError = true;
            errorMsg.append("科目が未選択です。<br>");
        }

        try {
            point = Integer.parseInt(pointStr);
            if (point < 0 || point > 100) {
                hasError = true;
                errorMsg.append("点数は0〜100の範囲で入力してください。<br>");
            }
        } catch (NumberFormatException e) {
            hasError = true;
            errorMsg.append("点数は数字で入力してください。<br>");
        }

        try {
            no = Integer.parseInt(noStr);
            if (no <= 0) {
                hasError = true;
                errorMsg.append("回数は1以上で入力してください。<br>");
            }
        } catch (NumberFormatException e) {
            hasError = true;
            errorMsg.append("回数は数字で入力してください。<br>");
        }

        // 入力エラーがある場合は元の画面に戻す
        if (hasError) {
            req.setAttribute("error", errorMsg.toString());
            req.getRequestDispatcher("test_regist.jsp").forward(req, res);
            return;
        }

        // Testオブジェクトを作成してデータを保存
        Test test = new Test();
///////        test.setStudent_id(studentNo);
        test.setSubject_cd(subjectCd);
        test.setPoint(point);
        test.setNo(no);

        // TestDaoがないためエラー
///////        TestDao testDao = new TestDao();
///////        testDao.insert(test); // DBに登録

        // 登録完了画面へフォワード
        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}
