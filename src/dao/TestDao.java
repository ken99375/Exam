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
	private String baseSql;
	// セーブする際に何かを確認するためのもの？
//	public Test get(Student student, Subject subject, School school){
//
//	}
//
	// 結果をTestクラスのオブジェクトにセットするのをまとめて行う
//	public List<Test> postFilter(ResultSet rSet, School school){
//
//	}
//
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
    		statement = connection.prepareStatement("select s.ent_year as ent_year, s.class_num as class_num, s.no as student_no, s.name as student_name, t.no, t.point"
    				+" from test as t"
    				+" left join student as s"
    				+" on s.no = t.student_no and s.ent_year = ?"
    				+" where t.school_cd = ? and t.subject_cd = ? and t.class_num = ? and t.no = ?"
    				+" order by student_no, no");

    		statement.setInt(1, entYear);
    		statement.setString(2, school.getCd());
    		statement.setString(3, subject.getCd());
    		statement.setString(4, classNum);
    		statement.setInt(5, num);

    		rSet = statement.executeQuery();
    		StudentDao  stuDao = new StudentDao();
    		while(rSet.next()){
    			Test test = new Test();
    			Student stu = stuDao.get(rSet.getString("student_no"));
    			test.setStudent(stu);
    			test.setClassNum(rSet.getString("class_num"));
    			test.setSubject(subject);
    			test.setSchool(school);
    			test.setNo(rSet.getInt("no"));
    			test.setPoint(rSet.getInt("point"));
    			list.add(test);
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
    return list;

	}

	 // 成績登録
	public boolean save(List<Test> list) throws Exception{
		Connection connection = getConnection();

		PreparedStatement statement = null;

		int count = 0;

		try {

			for (Test test : list){
				statement = connection.prepareStatement(""
						+ "update test set point = ? "
						+ "where student_no = ? and subject_cd = ? "
						+ "and school_cd = ? and no = ?");

				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getStudent().getNo());
				statement.setString(3, test.getSubject().getCd());
				statement.setString(4, test.getSchool().getCd());
				statement.setInt(5, test.getNo());

				count += statement.executeUpdate();
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

	    if (count > 0) {
	        // 実行件数が1件以上ある場合
	        return true;
	    } else {
	        // 実行件数が0件の場合
	        return false;
	    }
	}

	// 成績登録
//	public boolean save(Test test, Connection connection){
//
//	}
}
