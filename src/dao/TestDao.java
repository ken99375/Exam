package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao{
	private String baseSql = "select s.ent_year as ent_year, s.class_num as class_num, s.no as student_no, s.name as student_name,"
			+ " t.subject_cd, coalesce(t.no, ?) as no, t.point"
			+" from student as s"
			+" left join test as t"
			+" on s.no = t.student_no and t.school_cd = ? and t.subject_cd = ? and t.class_num = ? and t.no = ? "
			+" where s.ent_year = ? and s.class_num = ? ";
	// 登録する際にinsertかupdateかを区別するためのメソッド
//	public Test get(Student student, Subject subject, School school){
//
//	}
//
	// 結果をTestクラスのオブジェクトにセットするのをまとめて行う
	public List<Test> postFilter(ResultSet rSet, School school) throws SQLException, Exception{
		List<Test> list = new ArrayList<>();
		StudentDao  stuDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();
		while(rSet.next()){
			Test test = new Test();
			Subject sub = subDao.get(rSet.getString("subject_cd"), school);
			Student stu = stuDao.get(rSet.getString("student_no"));
			test.setStudent(stu);
			test.setClassNum(rSet.getString("class_num"));
			test.setSubject(sub);
			test.setSchool(school);
			test.setNo(rSet.getInt("no"));
			test.setPoint(rSet.getInt("point"));
			list.add(test);
		}
		return list;
	}

	// テストテーブルの科目コード、学校コード、回数、クラス番号で絞り込み、
	// SQLでTestListSbujectDaoと同じように絞り込み。（追加で回数を指定）
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception{
		List<Test> list = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rSet = null;
		try {
			connection = getConnection();
    		// プリペアードステートメントにSQL文をセット
			// このSQL文だと科目で絞り込めてない。

			String orderby = " order by student_no, t.no";
    		statement = connection.prepareStatement(baseSql + orderby);

    		statement.setInt(1, num);
    		statement.setString(2, school.getCd());
    		statement.setString(3, subject.getCd());
    		statement.setString(4, classNum);
    		statement.setInt(5, num);
    		statement.setInt(6, entYear);
    		statement.setString(7, classNum);

    		rSet = statement.executeQuery();
    		list = postFilter(rSet, school);

    	} catch (Exception e) {
    		e.printStackTrace();
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

	 // 成績登録
	public boolean save(List<Test> list) throws Exception{
		Connection con = getConnection();
		try {
			for (Test test : list){
				boolean result = save(test, con);
				if (!result) {
					// 一軒でも失敗があればrollbackしてfalse
					con.rollback();
					return false;
				}
			}
			// すべて成功したらcommit
			con.commit();
			return true;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	// 成績登録
	public boolean save(Test test, Connection connection) throws SQLException {
		String sql = "MERGE INTO test (student_no, subject_cd, school_cd, no, point, class_num) " +
	             "KEY (student_no, subject_cd, school_cd, no) " +
	             "VALUES (?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setString(1, test.getStudent().getNo());
	        statement.setString(2, test.getSubject().getCd());
	        statement.setString(3, test.getSchool().getCd());
	        statement.setInt(4, test.getNo());
	        statement.setInt(5, test.getPoint());
	        statement.setString(6, test.getClassNum());

	        int count = statement.executeUpdate();
	        System.out.println("✅ MERGE 実行成功: " + count + " rows affected");
	        return count > 0;
	    }
	}


}

