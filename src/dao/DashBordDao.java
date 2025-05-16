package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.DashBord;
import bean.RangeCount;
import bean.School;
import bean.Subject;

public class DashBordDao extends Dao{
	public DashBord get(int entYear, String classNum, Subject subject, int num, School school, List<int[]> ranges) throws Exception{
		StringBuilder sb = new StringBuilder("select s.ent_year as 入学年度, s.class_num as クラス番号, t.subject_cd as 科目コード, t.no as テスト番号, ");
		DashBord dashbord = new DashBord();
		for (int i = 0; i < ranges.size(); i++){
			int lo = ranges.get(i)[0], hi = ranges.get(i)[1];
			sb.append(" sum(case when t.point between ? and ? then 1 else 0 end ) ").append(" as cnt_").append(lo).append("_").append(hi).append(", ");
		}

		sb.append(" AVG(t.point) as 平均点, ").append(" MAX(t.point) as 最高点, ").append(" MIN(t.point) as 最低点 ")
		.append(" FROM STUDENT as s")
		.append("  LEFT JOIN TEST as t ")
		.append("    ON s.no = t.student_no AND t.school_cd   = ? AND t.subject_cd  = ?")
		.append(" WHERE s.ent_year   = ? AND s.school_cd = ? AND s.class_num = ? AND t.no = ?")
		.append(" GROUP BY s.ent_year, s.school_cd, s.class_num, t.no");
		// java 7から従来のfinal形式で書かなくてよくなった。
		try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sb.toString())){
			System.out.println(sb);
			int idx = 1;
			for (int[] r : ranges){
				statement.setInt(idx++, r[0]);
				statement.setInt(idx++, r[1]);
			}
			statement.setString(idx++, school.getCd());
			statement.setString(idx++, subject.getCd());
			statement.setInt(idx++, entYear);
			statement.setString(idx++, school.getCd());
			statement.setString(idx++, classNum);
			statement.setInt(idx, num);

			// 結果の取得
			try (ResultSet rSet = statement.executeQuery()){
				// RangeCountのリストでDashBordのフィールド定義することで分割も範囲も柔軟にできるようになった
				List<RangeCount> dist = new ArrayList<>();
				// 科目コードから科目のオブジェクトを取得するためdaoを呼び出し
				SubjectDao sub_dao = new SubjectDao();
				if (rSet.next()){
					dashbord.setEntYear(rSet.getInt("入学年度"));
					dashbord.setClassNum(rSet.getString("クラス番号"));
					String subject_cd = rSet.getString("科目コード");
					Subject subject2 = sub_dao.get(subject_cd, school);
					dashbord.setSubject(subject2);
					dashbord.setTestNo(rSet.getInt("テスト番号"));
					for (int[] r : ranges){
						int lo = r[0], hi = r[1];
						String col = "cnt_"  + lo + "_" + hi;
						int cnt = rSet.getInt(col);
						RangeCount distribution = new RangeCount();
						// ここおかしい
						distribution.setCount(cnt);
						distribution.setLower(lo);
						distribution.setUpper(hi);
						dist.add(distribution);
					}
					dashbord.setDistribution(dist);
					dashbord.setAvg(rSet.getDouble("平均点"));
					dashbord.setMax(rSet.getInt("最高点"));
					dashbord.setMin(rSet.getInt("最低点"));
				}
				System.out.println(dashbord.getDistribution());
			}
		} catch (Exception e) {
    		e.printStackTrace();
    		throw e;
    	}
		return dashbord;
	}
}
