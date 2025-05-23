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
import bean.TestListStudent;


public class TestListStudentDao extends Dao{
	private String baseSql = "select test.student_no, test.subject_cd, test.school_cd, test.no, test.point, test.class_num "
			+ "from test"
			+ " left join student "
			+ " on student.no = test.student_no where test.student_no = ? and test.school_cd = ? and student.is_attend = true ";


	// filterメソッドのSQL結果をまとめて処理するためのpostfilter
	// studentDaoでもやっていた。
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception{
		// リストを初期化
    	List<TestListStudent> list = new ArrayList<>();
    	SubjectDao sub_dao = new SubjectDao();
    	try {
    	    // リザルトセットを全権走査

    	    while (rSet.next()) {
    	        // 学生で特定された成績のインスタンスを初期化
    	        TestListStudent tl_s = new TestListStudent();
    	        SchoolDao scl_dao = new SchoolDao();
    	        School school = scl_dao.get(rSet.getString("school_cd"));
    	        Subject subject = sub_dao.get(rSet.getString("subject_cd"), school);

    	        // 成績インスタンスに検索結果をセット
    	        tl_s.setSubjectName(subject.getName());
    	        tl_s.setSubjectCd(rSet.getString("subject_cd"));
    	        tl_s.setNum(rSet.getInt("no"));
    	        tl_s.setPoint(rSet.getInt("point"));
    	        // リストに追加
    	        list.add(tl_s);
    	    }
    	} catch (SQLException | NullPointerException e) {
    	    e.printStackTrace();
    	}
    	return list;
	}

	// 特定の学生の成績リストを取得
	public List<TestListStudent> filter(Student student){
		List<TestListStudent> list = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rSet = null;
		String order = "order by subject_cd asc";

		try {
        	// 接続の取得
        	connection = getConnection();
            statement = connection.prepareStatement(baseSql + order);
            statement.setString(1, student.getNo());
            statement.setString(2, student.getSchool().getCd());
            rSet = statement.executeQuery();

            // 結果のセッティングはpostFilterで集中処理
            list = postFilter(rSet);


        } catch(SQLException e){
        	e.printStackTrace();
        	// ログを記録する場合ここに追加記述
        }
        catch (Exception e) {
            e.printStackTrace();
         // ログを記録する場合ここに追加記述
        } finally {
			// try・catch両方でリソースを閉じる
			try {
				if (statement != null){
					statement.close();
				}
				if (rSet != null){
					rSet.close();
				}
				if (connection != null){
					connection.close();
				}
			} catch(SQLException e){
				// リソースクローズ時のエラー
				e.printStackTrace();
			}
		}

        return list;
	}
}
