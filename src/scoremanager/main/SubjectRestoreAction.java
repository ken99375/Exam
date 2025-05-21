package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectRestoreAction extends Action {
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        SubjectDao dao = new SubjectDao();
        List<Subject> deletedClasses = dao.filterDeleted(teacher.getSchool());

        req.setAttribute("subject_list", deletedClasses);
        req.getRequestDispatcher("subject_restore_list.jsp").forward(req, res);
    }
}
