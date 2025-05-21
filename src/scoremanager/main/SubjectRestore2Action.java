package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectRestore2Action extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");

            String cd = req.getParameter("cd");

            // パラメータチェック
            if (cd == null || cd.isEmpty()) {
                throw new IllegalArgumentException("科目コードが指定されていません。");
            }

            // 論理削除されているデータも含めて取得（getではなくallGetを使う！）
            SubjectDao dao = new SubjectDao();
            Subject subject = dao.allGet(cd, teacher.getSchool());

            if (subject == null) {
                req.setAttribute("message", "該当する科目が見つかりません。");
                req.getRequestDispatcher("/error.jsp").forward(req, res);
                return;
            }

            req.setAttribute("subject", subject);  // JSPで${subject.cd}など使えるようにする

            // 確認用のJSPへ
            req.getRequestDispatcher("subject_restore.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "エラーが発生しました：" + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
