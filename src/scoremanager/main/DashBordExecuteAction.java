package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.DashBord;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.DashBordDao;
import dao.SubjectDao;
import tool.Action;

public class DashBordExecuteAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");
			SubjectDao sub_dao = new SubjectDao();
			Map<String, String> errors = new HashMap<>();

			int entYear = 0;
			int times = 0;
			String entYearStr = req.getParameter("ent_year");
			String classNum = req.getParameter("class_num");
			String subjectCd = req.getParameter("cd");
			String timesStr = req.getParameter("times");
			if (entYearStr != null ){
				entYear = Integer.parseInt(entYearStr);
			}
			if (timesStr != null) {
				times = Integer.parseInt(timesStr);
			}
			if ((entYearStr == null || "0".equals(entYearStr))
					  && ( classNum   == null || "0".equals(classNum))
					  && ( subjectCd  == null || "0".equals(subjectCd))
					  && ( timesStr      == null || "0".equals(timesStr))){
				errors.put("filter", "入学年度とクラスと科目と回数を選択してください");
				String backUrl = String.format("DashBord.action?ent_year=%s&class_num=%s&cd=%s&times=%s",
                        entYear, classNum, subjectCd, times);
                errorBack(req, res, errors, backUrl);
				return;
			}
			if (!"0".equals(entYearStr)
					  && !"0".equals(classNum)
					  && !"0".equals(subjectCd)
					  && !"0".equals(timesStr)){

				Subject subject = sub_dao.get(subjectCd, teacher.getSchool());
				System.out.println(entYear);
				DashBordDao dashDao = new DashBordDao();
				// 棒グラフの100点までの範囲を6分割で引数に。
				List<int[]> barRanges = new ArrayList<>();
				barRanges.add(new int[]{0, 29});
				barRanges.add(new int[]{30, 59});
				barRanges.add(new int[]{60, 69});
				barRanges.add(new int[]{70, 89});
				barRanges.add(new int[]{80, 89});
				barRanges.add(new int[]{90, 100});
				// 円グラフで三分割
				List<int[]> paiRanges = new ArrayList<>();
				paiRanges.add(new int[]{0, 59});
				paiRanges.add(new int[]{60, 79});
				paiRanges.add(new int[]{80, 100});
				// 円グラフと棒グラフのデータをそれぞれ取得
				DashBord barDashbord = dashDao.get(entYear, classNum, subject, times, teacher.getSchool(), barRanges);
				DashBord paiDashbord = dashDao.get(entYear, classNum, subject, times, teacher.getSchool(), paiRanges);
				// リクエスト属性にデータをセット
				System.out.println(barDashbord.getDistribution());
				System.out.println(paiDashbord.getDistribution());
				req.setAttribute("barDashbord", barDashbord);
				req.setAttribute("paiDashbord", paiDashbord);
				req.setAttribute("subject", subject);
				req.setAttribute("times", times);
			}
			// ↓フォームでのデータをここで設定↓
			LocalDate todaysDate = LocalDate.now(); // LcalDateインスタンスを取得
			 int year = todaysDate.getYear(); // 現在の年を取得
			// 先生の所属する学校のクラスリストを持ってくる
			ClassNumDao c_dao = new ClassNumDao();
			List<ClassNum> c_list = c_dao.filter(teacher.getSchool());
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

			req.getRequestDispatcher("dashbord.jsp").forward(req, res);
		} catch (Exception e){
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
