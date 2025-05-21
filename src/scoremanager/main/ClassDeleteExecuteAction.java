package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassDeleteExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");

            String classnum = req.getParameter("classnum");

            System.out.println("クラス番号: " + classnum);
            System.out.println("教師: " + teacher);
            System.out.println("学校: " + (teacher != null ? teacher.getSchool() : "null"));

            if (classnum == null || classnum.isEmpty()) {
                throw new IllegalArgumentException("クラス番号が指定されていません。");
            }

            if (teacher == null || teacher.getSchool() == null) {
                throw new IllegalStateException("ログインユーザーまたは学校情報が null です。");
            }

            ClassNum classNum = new ClassNum();
            classNum.setClassNum(classnum);
            classNum.setSchool(teacher.getSchool());

            ClassNumDao dao = new ClassNumDao();
            boolean result = dao.deleteAttend(classNum);

            if (result) {
                req.getRequestDispatcher("class_delete_done.jsp").forward(req, res);
            } else {
                req.setAttribute("message", "該当クラスが存在しないか、削除できませんでした。");
                req.getRequestDispatcher("/error.jsp").forward(req, res);
            }

        } catch (Exception e) {
            e.printStackTrace(); // ★ ログ出力
            req.setAttribute("message", "エラーが発生しました：" + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
