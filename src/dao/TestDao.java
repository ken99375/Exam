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
			+ " coalesce(t.subject_cd, ?) as subject_cd, coalesce(t.no, ?) as no, t.point "
			+" from student as s "
			+" left join test as t "
			+" on s.no = t.student_no and t.school_cd = ? and t.subject_cd = ? and t.class_num = ? and t.no = ? "
			+" where s.ent_year = ? and s.class_num = ? ";
	 // 登録する際にinsertかupdateかを区別するためのメソッド
	public Test get(Student student, Subject subject, School school, int no) throws Exception{
		System.out.println("▶ test.getStudent() = " +student.getNo());
		 System.out.println("▶ test.getSubject() = " + subject.getCd());
		 System.out.println("▶ test.getSchool()  = " + school.getCd());
		 Connection connection = null;
		 PreparedStatement statement = null;
		 Test test = new Test();

		 try {
			 connection = getConnection();
			 statement = connection.prepareStatement("SELECT student_no, subject_cd, school_cd, no  "
			 		+ "FROM TEST "
			 		+ "where student_no = ? and subject_cd = ? "
			 		+ "and school_cd = ? and no = ?");

			 statement.setString(1, student.getNo());
			 statement.setString(2, subject.getCd());
			 statement.setString(3, school.getCd());
			 statement.setInt(4, no);
			 ResultSet rSet = statement.executeQuery();
			 StudentDao  stuDao = new StudentDao();
			 SubjectDao subDao = new SubjectDao();
			 if (rSet.next()) {
				Subject sub = subDao.get(rSet.getString("subject_cd"), school);
				Student stu = stuDao.get(rSet.getString("student_no"));
				test.setSchool(school);
				test.setStudent(stu);
				test.setSubject(sub);
				test.setNo(no);
			 } else {
				 test = null;
			 }
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
	    return test;

		}

	// 結果をTestクラスのオブジェクトにセットするのをまとめて行う
	public List<Test> postFilter(ResultSet rSet, School school) throws SQLException, Exception{
		List<Test> list = new ArrayList<>();
		StudentDao  stuDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();
		while(rSet.next()){
			Test test = new Test();
			Subject sub = subDao.get(rSet.getString("subject_cd"), school);
			System.out.println(sub);
			Student stu = stuDao.get(rSet.getString("student_no"));
			System.out.println(stu);
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

    		statement.setString(1, subject.getCd());
    		statement.setInt(2, num);
    		statement.setString(3, school.getCd());
    		statement.setString(4, subject.getCd());
    		System.out.println(subject.getCd());
    		statement.setString(5, classNum);
    		statement.setInt(6, num);
    		statement.setInt(7, entYear);
    		statement.setString(8, classNum);

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
				System.out.println("セーブ前");
				boolean result = save(test, con);
				System.out.println("セーブ後");
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
	public boolean save(Test test, Connection connection)throws Exception{
		System.out.println(test);
		Test testFilter = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
		System.out.println(test.getPoint());
		System.out.println(testFilter);
		if (testFilter == null){
			String sql = ""
					+ "insert into test values(?, ?, ?, ?, ?, ?) ";
			try (PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());

				int count = statement.executeUpdate();
				System.out.println(count);
				return count > 0;
			}
		} else {
			String sql = ""
					+ "update test set point = ?  "
					+ " where student_no = ? and subject_cd = ? "
					+ " and school_cd = ? and no = ? ";
			try (PreparedStatement statement = connection.prepareStatement(sql)){
				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getStudent().getNo());
				statement.setString(3, test.getSubject().getCd());
				statement.setString(4, test.getSchool().getCd());
				statement.setInt(5, test.getNo());
				int count = statement.executeUpdate();
				System.out.println(count);
				return count > 0;
			}
		}

	}

}
