package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{
	private String baseSql;
	public List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		try {
			while (rSet.next()) {
				TestListSubject testsubject = new TestListSubject();
				testsubject.setEntYear(rSet.getInt("ent_year"));
				testsubject.setStudentNo(rSet.getString("student_no"));
				testsubject.setStudentName(rSet.getString("student_name"));
				testsubject.setClassNum(rSet.getString("class_num"));
				list.add(testsubject);
			}
		} catch (NullPointerException e) {
    	    e.printStackTrace();
		}
		return list;
	}
	// 指定された入学年度、クラス番号、科目、学校 に合致するテストの情報をデータベースから取得し、その結果を返す。
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

		List<TestListSubject> list = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rSet = null;
		// SQL文のソート
//        String order = "order by cd asc";s
		try {
			connection = getConnection();
    		// プリペアードステートメントにSQL文をセット
    		statement = connection.prepareStatement(
    				 "select (select ent_year form student where class_num = ? and school_cd = ?) as ent_year, "
    				 + "(select name form student where class_num = ? and school_cd = ? and ent_year = ?) as student_name,"
    				 + "student_no, class_num "
    				 + "form test where student_no like in (select no form student where class_num = ? and school_cd = ? and ent_year = ?)"
    				 + " and school_cd = ?  and subject_cd = ? and class_num = ?");

    		statement.setString(1, classNum);
    		statement.setString(2, school.getCd());
    		statement.setString(3, classNum);
    		statement.setString(4, school.getCd());
    		statement.setInt(5, entYear);
    		statement.setString(6, classNum);
    		statement.setString(7, school.getCd());
    		statement.setInt(8, entYear);
    		statement.setString(9, school.getCd());
    		statement.setString(10, subject.getCd());
    		statement.setString(11, classNum);



    		rSet = statement.executeQuery();
    		 list = postFilter(rSet);
    		while (rSet.next()) {

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
