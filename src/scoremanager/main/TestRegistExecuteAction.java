package scoremanager.main;

import java.util.ArrayList;
import java.util.Arrays;
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

public class TestRegistExecuteAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
        	// エラーメッセージ格納用マップ
            Map<String, String> errors = new HashMap<>();
         // セッションからログイン中のuser(teacher)を取得
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");

            // フォームから取得するパラメータ
            String[] studentNos = req.getParameterValues("studentNo");
            String[] pointStrs = req.getParameterValues("po");
            String[] testNos = req.getParameterValues("no");

            // 科目コード（cd）は共通なので1件だけ取得
            String subjectCd = req.getParameter("cd");

            // 検索条件（エラー時の戻り先用）
            String entYear = req.getParameter("ent_year");
            String classNum = req.getParameter("class_num");
            String times = req.getParameter("times");

            // ログ出力：受信データ確認
            System.out.println("=== フォームから受信したデータ ===");
            System.out.println("studentNos: " + Arrays.toString(studentNos));
            System.out.println("pointStrs: " + Arrays.toString(pointStrs));
            System.out.println("testNos: " + Arrays.toString(testNos));
            System.out.println("cd (subjectCd): " + subjectCd);
            System.out.println("entYear: " + entYear + ", classNum: " + classNum + ", times: " + times);

            // 点数バリデーション
            int[] points = new int[pointStrs.length];
            for (int i = 0; i < pointStrs.length; i++) {
                try {
                    int point = Integer.parseInt(pointStrs[i]);
                    if (point < 0 || point > 100) {
                        errors.put("po" + i, "0~100の範囲で入力してください");
                    }
                    points[i] = point;
                } catch (NumberFormatException e) {
                    errors.put("po" + i, "数値で入力してください");
                }
            }

            // エラーがある場合は入力画面に戻す
            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                String backUrl = String.format("TestRegist.action?ent_year=%s&class_num=%s&cd=%s&times=%s",
                        entYear, classNum, subjectCd, times);
                errorBack(req, res, errors, backUrl);
                return;
            }

            // DAOのインスタンス生成
            StudentDao studentDao = new StudentDao();
            SubjectDao subjectDao = new SubjectDao();
            List<Test> testList = new ArrayList<>();

            // 共通の科目オブジェクトを取得（全件に使う）
            Subject subject = subjectDao.get(subjectCd, teacher.getSchool());
            if (subject == null) {
                System.out.println("subjectDao.get(" + subjectCd + ", school) => null");

                Map<String, String> cdError = new HashMap<>();
                cdError.put("cd", "科目情報が取得できません");
                req.setAttribute("errors", cdError);

                String backUrl = String.format("TestRegist.action?ent_year=%s&class_num=%s&cd=%s&times=%s",
                        entYear, classNum, subjectCd, times);
                errorBack(req, res, errors, backUrl);
                return;
            }

            for (int i = 0; i < studentNos.length; i++) {
                System.out.println("=== [" + i + "] 学生データ取得 ===");

                Student student = studentDao.get(studentNos[i]);
                if (student == null) {
                    System.out.println("studentDao.get(" + studentNos[i] + ") => null");
                    errors.put("po" + i, "学生情報が存在しません: " + studentNos[i]);
                    continue;
                }

                if (teacher.getSchool() == null) {
                    System.out.println("teacher.getSchool() => null");
                    errors.put("po" + i, "学校情報が取得できません");
                    continue;
                }

                // テスト番号を取得
                int testNo = Integer.parseInt(testNos[i]);

                // テストオブジェクトを作成し、各項目を設定
                Test test = new Test();
                test.setStudent(student);
                test.setClassNum(student.getClassNum());
                test.setSubject(subject);
                test.setSchool(teacher.getSchool());
                test.setNo(testNo);
                test.setPoint(points[i]);

                // 登録リストに追加
                testList.add(test);
            }

            // 学生取得や学校情報に関するエラーがある場合は戻す
            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                String backUrl = String.format("TestRegist.action?ent_year=%s&class_num=%s&cd=%s&times=%s",
                        entYear, classNum, subjectCd, times);
                errorBack(req, res, errors, backUrl);
                return;
            }

            // テスト情報をデータベースに保存
            TestDao testDao = new TestDao();
            boolean result = testDao.save(testList);

            // 保存成功時：完了ページへ、失敗時：エラーページへ遷移
            if (result) {
                req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
            } else {
                System.out.println("TestDao.save() returned false");
                req.getRequestDispatcher("/error.jsp").forward(req, res);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}

