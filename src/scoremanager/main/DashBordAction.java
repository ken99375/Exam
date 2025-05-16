package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class DashBordAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception{
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");
			SubjectDao sub_dao = new SubjectDao();

			LocalDate todaysDate = LocalDate.now(); // LcalDateインスタンスを取得
			 int year = todaysDate.getYear(); // 現在の年を取得
			// 先生の所属する学校のクラスリストを持ってくる
			ClassNumDao c_dao = new ClassNumDao();
			List<String> c_list = c_dao.filter(teacher.getSchool());
			// 先生の所属する学校の科目データを持ってくる

			List<Subject> sub_list = sub_dao.filter(teacher.getSchool());
			List<Integer> entYearSet = new ArrayList<>();
			 // 10年前から1年後まで年をリストに追加
			 for (int i = year -10; i < year + 10; i++) {
				 entYearSet.add(i);
			 }
			req.setAttribute("c_list", c_list);
			req.setAttribute("sub_list", sub_list);
			req.setAttribute("ent_year_set", entYearSet);
			req.getRequestDispatcher("dashbord.jsp").forward(req, res);;
		} catch(Exception e){
			e.printStackTrace();
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
