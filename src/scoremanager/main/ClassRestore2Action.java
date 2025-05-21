package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassRestore2Action extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");

            // クラスコードを取得
            String classnum = req.getParameter("classnum");

            // DAOを使って対象のクラス情報を取得
            ClassNumDao dao = new ClassNumDao();
            ClassNum classInfo = dao.get(classnum, teacher.getSchool());

            // クラス情報をリクエストにセットしてJSPへ
            req.setAttribute("classInfo", classInfo);

            // class_delete.jsp にフォワード
            req.getRequestDispatcher("class_restore.jsp").forward(req, res);
        } catch (Exception e) {
            e.printStackTrace(); // ログ出力（開発中は有用）
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}