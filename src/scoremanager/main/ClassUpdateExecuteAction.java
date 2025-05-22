package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassUpdateExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");
            Map<String, String> errors = new HashMap<>();

            String classnum = req.getParameter("classnum");
            String name = req.getParameter("name");

            // ログ出力
            System.out.println("更新対象 classnum: " + classnum);
            System.out.println("入力された name: " + name);
            System.out.println("school_cd: " + teacher.getSchool().getCd());

            ClassNumDao dao = new ClassNumDao();
            ClassNum c = dao.get(classnum, teacher.getSchool());
            if (c == null) {
            	errors.put("dele", "クラスが存在しません");
            	req.setAttribute("errors", errors);
            	errorBack(req, res, errors, "class_update.jsp");
				return;
            }

            // 入力バリデーション
            if (classnum == null || classnum.trim().isEmpty()) {
                errors.put("classnum", "クラスコードを入力してください。");
            } else if (classnum.trim().length() != 3) {
                errors.put("classnum", "クラスコードは3文字で入力してください。");
            }

            if (name == null || name.trim().isEmpty()) {
                errors.put("name", "クラス名を入力してください。");
            }

            if (!errors.isEmpty()) {
                // 入力値とエラー情報をセットして戻る
                req.setAttribute("classnum", classnum);
                req.setAttribute("name", name);
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("class_update.jsp").forward(req, res);
                return;
            }

            // クラス情報を更新
            ClassNum classNum = new ClassNum();
            classNum.setClassNum(classnum);
            classNum.setName(name);
            classNum.setSchool(teacher.getSchool());

            boolean result = dao.update(classNum);  // ← ここ重要！

            if (result) {
                req.getRequestDispatcher("class_update_done.jsp").forward(req, res);
            } else {
                req.getRequestDispatcher("/error.jsp").forward(req, res);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}