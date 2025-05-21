package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassRestoreAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        ClassNumDao dao = new ClassNumDao();
        List<ClassNum> deletedClasses = dao.filterDeleted(teacher.getSchool());

        req.setAttribute("c_list", deletedClasses);
        req.getRequestDispatcher("class_restore_list.jsp").forward(req, res);
    }
}
