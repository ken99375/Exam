package scoremanager.main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

    /**
     * エラー発生時にフォームへ戻す処理（エラー情報＋セレクト項目の再セット含む）
     */
    public void errorBack(HttpServletRequest req, HttpServletResponse res,
                          Map<String, String> errors, String jsp)
            throws ServletException, IOException {
        req.setAttribute("errors", errors);

        // ▼ フォーム再描画用データ（入学年度・クラス）の再セット
        try {
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");

            // 入学年度のリストを現在の年を中心に ±10年生成
            int currentYear = LocalDate.now().getYear();
            List<Integer> entYearList = new ArrayList<>();
            for (int i = currentYear - 10; i <= currentYear + 10; i++) {
                entYearList.add(i);
            }
            req.setAttribute("ent_year_set", entYearList);

            // クラス番号リストをDBから取得
            ClassNumDao classNumDao = new ClassNumDao();
            List<String> classNumList = classNumDao.filter(teacher.getSchool());
            req.setAttribute("class_num_set", classNumList);
        } catch (Exception e) {
            e.printStackTrace(); // ログ出力のみ（画面表示は続行）
        }
        // ▲ 再描画用データのセット完了

        // エラー付きで指定JSPにフォワード
        req.getRequestDispatcher(jsp).forward(req, res);
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res)
            throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user"); // ログイン教師取得
        Map<String, String> errors = new HashMap<>();

        try {
            // フォームから送られてくるパラメータの取得
            String no = req.getParameter("no");
            String name = req.getParameter("name");
            String entYearStr = req.getParameter("entYear");
            String classnum = req.getParameter("classNum");

            int entYear; // ← ここでは初期化しない（正しい値をパースする）
            boolean attend = true; // 登録時は在籍中をデフォルトに

            // 入学年度のバリデーション（未選択または"0"）
            if (entYearStr == null || entYearStr.isEmpty() || entYearStr.equals("0")) {
                errors.put("entyear", "入学年度を選択してください。");
                errorBack(req, res, errors, "student_create.jsp");
                return;
            }

            // ← ★ 修正ポイント：バリデーション通過後にパースする
            entYear = Integer.parseInt(entYearStr);

            // 学生番号のバリデーション
            if (no == null || no.isEmpty()) {
                errors.put("no", "学生番号が入力されていません。");
                errorBack(req, res, errors, "student_create.jsp");
                return;
            }

            // 氏名のバリデーション
            if (name == null || name.isEmpty()) {
                errors.put("name", "氏名が入力されていません。");
                errorBack(req, res, errors, "student_create.jsp");
                return;
            }

            // クラスのバリデーション
            if (classnum == null || classnum.isEmpty()) {
                errors.put("classnum", "クラスが入力されていません。");
                errorBack(req, res, errors, "student_create.jsp");
                return;
            }

            // 学生番号の重複チェック（学校ごと）
            StudentDao dao = new StudentDao();
            Student duplication = dao.get(no, teacher.getSchool().getCd());
            if (duplication != null) {
                errors.put("no", "その学生番号は既に登録されています。");
                errorBack(req, res, errors, "student_create.jsp");
                return;
            }

            // 学生オブジェクトの作成と値のセット
            Student student = new Student();
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classnum);
            student.setEntYear(entYear); // ← 正しくセットされる！
            student.setAttend(attend);
            student.setSchool(teacher.getSchool());

            // 学生情報の保存処理
            boolean result = dao.save(student);
            if (result) {
                // 成功時は完了画面へ遷移
                req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
            } else {
                // 保存失敗時（通常は発生しにくいが保険）
                errors.put("system", "学生の登録に失敗しました。時間をおいて再試行してください。");
                errorBack(req, res, errors, "student_create.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace(); // デバッグ用ログ
            req.getRequestDispatcher("/error.jsp").forward(req, res); // 汎用エラー画面へ
        }
    }

}
