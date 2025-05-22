package scoremanager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TeacherDao;
import tool.Action;

public class SignUp2ExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 入力値取得
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String name = req.getParameter("name");

        // セッションから school_cd を取得
        HttpSession session = req.getSession(false);
        String schoolCd = (session != null) ? (String) session.getAttribute("school_cd") : null;

        List<String> errors = new ArrayList<>();

        // 入力チェック
        if (id == null || id.isEmpty()) {
            errors.add("IDを入力してください。");
        }
        if (name == null || name.isEmpty()) {
            errors.add("氏名を入力してください。");
        } else if (name.length() > 10) {
            errors.add("氏名は10文字以内で入力してください。");
        }
        if (password == null || password.isEmpty()) {
            errors.add("パスワードを入力してください。");
        }
        if (confirmPassword == null || confirmPassword.isEmpty()) {
            errors.add("確認用パスワードを入力してください。");
        }
        if (password != null && confirmPassword != null && !password.equals(confirmPassword)) {
            errors.add("パスワードと確認用パスワードが一致しません。");
        }
        if (schoolCd == null || schoolCd.isEmpty()) {
            errors.add("学校コードがセッションに存在しません。最初からやり直してください。");
        }

        // エラーがあれば戻る
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("id", id);
            req.setAttribute("name", name);
            req.getRequestDispatcher("signup_account.jsp").forward(req, res);
            return;
        }


        TeacherDao dao = new TeacherDao();
        if (dao.get(id) != null) {
            errors.add("このIDは既に使用されています。別のIDを入力してください。");
            req.setAttribute("errors", errors);
            req.setAttribute("id", id);
            req.setAttribute("name", name);
            req.getRequestDispatcher("signup_account.jsp").forward(req, res);
            return;
        }
        try {
            boolean success = dao.insertTeacher(id, password, name, schoolCd);

            if (success) {
                res.sendRedirect("signup_account_done.jsp");
            } else {
                errors.add("登録に失敗しました。");
                req.setAttribute("errors", errors);
                req.setAttribute("id", id);
                req.setAttribute("name", name);
                req.getRequestDispatcher("signup_account.jsp").forward(req, res);
            }

        } catch (Exception e) {
            e.printStackTrace();
            errors.add("システムエラーが発生しました: " + e.getMessage());
            req.setAttribute("errors", errors);
            req.setAttribute("id", id);
            req.setAttribute("name", name);
            req.getRequestDispatcher("signup_account.jsp").forward(req, res);
        }

    }
}
