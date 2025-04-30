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
// private String baseSql;
//	public List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
//		List<TestListSubject> list = new ArrayList<>();
//		try {
//
//			}
//		} catch (NullPointerException e) {
//    	    e.printStackTrace();
//		}
//		return list;
//	}

	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

		List<TestListSubject> list = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rSet = null;
		Map<Integer, Integer> points = null;
		// SQL文のソート
//        String order = "order by cd asc";s
		try {
			connection = getConnection();
    		// プリペアードステートメントにSQL文をセット
			// このSQL文だと科目で絞り込めてない。
    		statement = connection.prepareStatement("select s.ent_year as ent_year, s.class_num as class_num, s.no as student_no, s.name as student_name, t.no, t.point"
+" from test as t"
+" left join student as s"
+" on s.no = t.student_no and s.ent_year = ?"
+" where t.school_cd = ? and t.subject_cd = ? and t.class_num = ?"
+" order by student_no, no");

    		statement.setInt(1, entYear);
    		statement.setString(2, school.getCd());
    		statement.setString(3, subject.getCd());
    		statement.setString(4, classNum);

    		rSet = statement.executeQuery();

    		TestListSubject current = null;
    		String prevStudentNo = null;

    		while (rSet.next()) {
    			String stuNo = rSet.getString("student_no");
    			if (!stuNo.equals(prevStudentNo)){
    				current = new TestListSubject();
    				current.setEntYear(rSet.getInt("ent_year"));
    				current.setStudentNo(stuNo);
    				current.setStudentName(rSet.getString("student_name"));
    				current.setClassNum(rSet.getString("class_num"));
    				points = new HashMap<>();
    				list.add(current);

    				prevStudentNo = stuNo;
    			}
    			int testNo = rSet.getInt("no");
    			int point = rSet.getInt("point");
    			points.put(testNo, point);
    			current.setPoints(points);
//    			System.out.println(current.getPoints());
    		}

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
