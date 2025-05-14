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

    /**
     * エラーメッセージをセットして指定されたJSPにフォワードする共通処理
     */
    public void errorBack(HttpServletRequest req, HttpServletResponse res,
                          Map<String, String> errors, String jsp)
            throws ServletException, IOException {
        req.setAttribute("errors", errors);
        req.getRequestDispatcher(jsp).forward(req, res);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession();
        // セッションからログイン中の教師情報を取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        Map<String, String> errors = new HashMap<>();

        try {
            // パラメータの受け取り
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String entYearStr = req.getParameter("entYear");
            String classnum = req.getParameter("classNum");

            int entYear = 0; // 入学年度を整数として保持
            boolean attend = true; // 登録時点では在籍中として固定

            // 入学年度のバリデーション（未入力および数値変換エラー）
            if (entYearStr == null || entYearStr.isEmpty()) {
                errors.put("entyear", "入学年度を選択してください。");
                errorBack(req, res, errors, "student_create.jsp");
                return;
            } else {
                try {
                    entYear = Integer.parseInt(entYearStr);
                } catch (NumberFormatException e) {
                    errors.put("entyear", "入学年度の形式が正しくありません。");
                    errorBack(req, res, errors, "student_create.jsp");
                    return;
                }
            }

            // 氏名のバリデーション
            if (name == null || name.isEmpty()) {
                errors.put("name", "氏名が入力されていません。");
                errorBack(req, res, errors, "student_create.jsp");
                return;
            }

            // 学生番号のバリデーション
            if (no == null || no.isEmpty()) {
                errors.put("no", "学生番号が入力されていません。");
                errorBack(req, res, errors, "student_create.jsp");
                return;
            }

            // クラスのバリデーション
            if (classnum == null || classnum.isEmpty()) {
                errors.put("classnum", "クラスが入力されていません。");
                errorBack(req, res, errors, "student_create.jsp");
                return;
            }

            // 学生番号の重複チェック（学校単位）
            StudentDao dao = new StudentDao();
            Student duplication = dao.get(no, teacher.getSchool().getCd());

            if (duplication != null) {
                errors.put("no", "その学生番号は既に登録されています。");
                errorBack(req, res, errors, "student_create.jsp");
                return;
            }

            // 学生情報のオブジェクト作成
            Student student = new Student();
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classnum);
            student.setEntYear(entYear);
            student.setAttend(attend);
            student.setSchool(teacher.getSchool());

            // 学生の登録処理（成功時は完了ページへ）
            boolean result = dao.save(student);

            if (result) {
                req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
            } else {
                // 登録失敗時（主にSQL例外などを想定、メッセージを一般化）
                errors.put("system", "学生の登録に失敗しました。時間をおいて再度お試しください。");
                errorBack(req, res, errors, "student_create.jsp");
            }

        } catch (Exception e) {
            // 例外発生時にはエラーページへフォワード
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
