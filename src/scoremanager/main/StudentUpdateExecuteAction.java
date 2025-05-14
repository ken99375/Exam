package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    // エラー処理（studentとschoolも渡す）
    public void errorBack(HttpServletRequest req, HttpServletResponse res,
                          Map<String, String> errors, String jsp,
                          Student student, School school) throws Exception {

        req.setAttribute("errors", errors);
        req.setAttribute("student", student);

        // クラス選択リストの再セット
        ClassNumDao classNumDao = new ClassNumDao();
        req.setAttribute("class_num_set", classNumDao.filter(school));

        req.getRequestDispatcher(jsp).forward(req, res);
    }

    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        req.setCharacterEncoding("UTF-8");
        Map<String, String> errors = new HashMap<>();

        // 入力値取得
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        int entYear = Integer.parseInt(req.getParameter("entYear"));
        String classNum = req.getParameter("classNum");
        boolean attend = req.getParameter("attend") != null;

        Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        School school = teacher.getSchool();

        // 学生オブジェクトを構築（エラー時も使う）
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(attend);
        student.setSchool(school);

        // バリデーション
        if (name == null || name.isEmpty()) {
            errors.put("name", "氏名が入力されていません。");
        }

        if (classNum == null || classNum.isEmpty()) {
            errors.put("classNum", "クラスが入力されていません。");
        }

        if (!errors.isEmpty()) {
            errorBack(req, res, errors, "student_update.jsp", student, school);
            return;
        }

        // 更新
        StudentDao dao = new StudentDao();
        boolean result = dao.save(student);

        if (result) {
            req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
        } else {
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
