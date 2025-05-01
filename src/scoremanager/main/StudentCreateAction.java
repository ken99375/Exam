package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class StudentCreateAction extends Action{
	public void execute(HttpServletRequest req, HttpServletResponse res
			) throws Exception {
		// 一度このクラスを通して登録されているクラス情報の設定
		// 入学年度は設定せずに今年度から５引いた値から１０個表示
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher)session.getAttribute("user");

			//入学年度現在の年から-10と+10の合計20個から選択できるようにする
			LocalDate todayDate = LocalDate.now(); // LocaDateインスタンスを取得

			int year = todayDate.getYear(); // 現在の年を取得
			// リストを初期化
			List<Integer> ent_year_list = new ArrayList<>();
			//  10年前から一年後まで年をリストに追加
			for (int i = year -10; i < year + 11; i++){
				ent_year_list.add(i);
			}

			req.setAttribute("ent_year_set", ent_year_list);
			// クラス番号一覧も取得
		    ClassNumDao classNumDao = new ClassNumDao();
		    List<String> classNums = classNumDao.filter(teacher.getSchool());
		    req.setAttribute("class_num_set", classNums);
			req.getRequestDispatcher("student_create.jsp").forward(req, res);
		} catch (Exception e){
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}

	}
}

