package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.Student;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {
  public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    String no = req.getParameter("no");

    StudentDao studentDao = new StudentDao();
    Student student = studentDao.get(no);

    req.setAttribute("student", student);

    // クラス番号一覧も取得
    ClassNumDao classNumDao = new ClassNumDao();
    List<ClassNum> classNums = classNumDao.filter(student.getSchool());
    req.setAttribute("class_num_set", classNums);

    // JSPへフォワード
    req.getRequestDispatcher("student_update.jsp").forward(req, res);
  }
}