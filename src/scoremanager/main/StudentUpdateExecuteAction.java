package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

  public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    req.setCharacterEncoding("UTF-8");

    // 入力値取得
    // 回数
    String no = req.getParameter("no");
    // 学生名
    String name = req.getParameter("name");
    // 入学年度
    int entYear = Integer.parseInt(req.getParameter("entYear"));
    // クラス番号
    String classNum = req.getParameter("classNum");
    // 在学中フラグ
    boolean attend = req.getParameter("attend") != null;

    // セッションから先生を取得して、所属学校を取得
    Teacher teacher = (Teacher) req.getSession().getAttribute("user");
    School school = teacher.getSchool();

    // 学生情報をセット
    Student student = new Student();
    student.setNo(no);
    student.setName(name);
    student.setEntYear(entYear);
    student.setClassNum(classNum);
    student.setAttend(attend);
    student.setSchool(school);

    // 更新
    StudentDao dao = new StudentDao();
    boolean result = dao.save(student);

    // boolean型の場合if(result)でおけ。
    if (result){
		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	} else {
		req.getRequestDispatcher("/error.jsp").forward(req, res);
	}
  }
}

