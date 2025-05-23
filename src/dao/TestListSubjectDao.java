package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{
	private String baseSql= "select s.ent_year as ent_year, s.class_num as class_num, s.no as student_no, s.name as student_name,  t1.point as 第一回, t2.point as 第二回 "
			+ "from student as s ";
	public List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		try {
			System.out.println("デバッグ用");
    		while (rSet.next()) {
    			TestListSubject bean = new TestListSubject();
    			String stuNo = rSet.getString("student_no");
    			bean.setEntYear(rSet.getInt("ent_year"));
    			bean.setStudentNo(stuNo);
   				bean.setStudentName(rSet.getString("student_name"));
   				bean.setClassNum(rSet.getString("class_num"));
   				Map<Integer, Integer> points = new HashMap<>();
   				int point1 = rSet.getInt("第一回");
   				if (!rSet.wasNull()){
   					points.put(1, point1);
   				}
    			int point2 = rSet.getInt("第二回");
    			if (!rSet.wasNull()){
    				points.put(2, point2);
    			}
    			bean.setPoints(points);

    			System.out.println(bean.getPoint(1) +":"+ bean.getPoint(2));
    			list.add(bean);
    		}
		} catch (NullPointerException e) {
    	    e.printStackTrace();
		}
		return list;
	}

	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

		List<TestListSubject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		// SQL文のソート
//        String order = "order by cd asc";s
		try {
			connection = getConnection();
    		// プリペアードステートメントにSQL文をセット
			// このSQL文だと科目で絞り込めてない。
    		statement = connection.prepareStatement(baseSql +""
    				+ "left join test as t2"
 +" on s.no = t2.student_no and t2.no = '2' and t2.subject_cd = ?"
+" left join test as t1"
+" on s.no = t1.student_no and t1.school_cd = ? "
+ "and t1.subject_cd = ? and t1.class_num = ? and "
+"  t1.no = '1'"
+" where s.school_cd = ?  and s.ent_year = ?  and s.class_num = ?"
+" and (t1.point is not null or t2.point is not null) and s.is_attend = true "
+" order by s.no");

    		statement.setString(1, subject.getCd());
    		statement.setString(2, school.getCd());
    		statement.setString(3, subject.getCd());
    		statement.setString(4, classNum);
    		statement.setString(5, school.getCd());
    		statement.setInt(6, entYear);
    		statement.setString(7, classNum);

    		rSet = statement.executeQuery();

    		list = postFilter(rSet);

    	} catch (Exception e) {
    		throw e;
    	} finally {
    		// プリペアードステートメントを閉じる
    		if (statement != null) {
    			try {
    				statement.close();
    			} catch (SQLException sqle) {
    				throw sqle;
    		}
    	}
    	// コネクションを閉じる
    	if (connection != null) {
    		try {
    			connection.close();
    		} catch (SQLException sqle) {
    			throw sqle;
    		}
    	}
    }
    return list;
	}
}
