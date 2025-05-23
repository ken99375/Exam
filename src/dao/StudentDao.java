package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

    private String baseSql = "select * from student where school_cd =?";

    private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
    	// リストを初期化
    	List<Student> list = new ArrayList<>();
    	try {
    	    // リザルトセットを全権走査
    	    while (rSet.next()) {
    	        // 学生インスタンスを初期化
    	        Student student = new Student();
    	        // 学生インスタンスに検索結果をセット
    	        student.setNo(rSet.getString("no"));
    	        student.setName(rSet.getString("name"));
    	        student.setEntYear(rSet.getInt("ent_year"));
    	        student.setClassNum(rSet.getString("class_num"));
    	        student.setAttend(rSet.getBoolean("is_attend"));
    	        student.setSchool(school);
    	        // リストに追加
    	        list.add(student);
    	    }
    	} catch (SQLException | NullPointerException e) {
    	    e.printStackTrace();
    	}
    	return list;

    }

    // 学生の一覧（学校、入学年度、クラス番号、在学フラグ）を表示する
    public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
    	// リストを初期化
    	List<Student> list = new ArrayList<>();
    	// コネクションを確立
    	Connection connection = getConnection();
    	// プリペアードステートメント
    	PreparedStatement statement = null;
    	// リザルトセット
    	ResultSet rSet = null;
    	// SQL文の条件
    	String condition = " and ent_year = ? and class_num = ?";
    	// SQL文のソート
    	String order = " order by no asc";
    	// SQL文の在学フラグ条件
    	String conditionIsAttend = "";
    	// 在学フラグがtrueの場合
    	if (isAttend) {
    	    conditionIsAttend = "and is_attend=true";
    	}

    	try {
    	    // プリペアードステートメントにSQL文をセット
    	    statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
    	    // プリペアードステートメントに学校コードをバインド
    	    statement.setString(1, school.getCd());
    	    // プリペアードステートメントに入学年度をバインド
    	    statement.setInt(2, entYear);
    	    // プリペアードステートメントにクラス番号をバインド
    	    statement.setString(3, classNum);
    	    // プライベートステートメントを実行
    	    rSet = statement.executeQuery();

    	 // リストへの格納処理を実行
    	    list = postFilter(rSet, school);
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

    // 学生の一覧（学校、入学年度、在学フラグ）を指定して表示する
    public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
        // リストを初期化
        List<Student> list = new ArrayList<>();
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet rSet = null;
        // SQL文の条件
        String condition = " and ent_year = ?";
        // SQL文のソート
        String order = " order by no asc";
        // SQL文の在学フラグ
        String conditionIsAttend = "";
        // 在学フラグがtrueだった場合
        if (isAttend) {
            conditionIsAttend = " and is_attend = true";
        }

        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
            // プリペアードステートメントに学校コードをバインド
            statement.setString(1, school.getCd());
            // プリペアードステートメントに入学年度をバインド
            statement.setInt(2, entYear);
            // プリペアードステートメントを実行
            rSet = statement.executeQuery();


            // リストへの格納処理を実行
            list = postFilter(rSet, school);
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
    // 学校の一覧（学校、クラス）を指定して表示する
 // クラスのみ指定での検索用（全年度対象）
    public List<Student> filter(School school, String classNum, boolean isAttend) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;
        List<Student> list = new ArrayList<>();

        String sql = "SELECT * FROM student WHERE school_cd = ? AND class_num = ?";
        if (isAttend) {
            sql += " AND is_attend = true";
        }
        sql += " ORDER BY no ASC";

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, school.getCd());
            statement.setString(2, classNum);
            rSet = statement.executeQuery();
            list = postFilter(rSet, school);
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }


    // 学生の一覧(学校、在学フラグ)を指定して表示する
    public List<Student> filter(School school, boolean isAttend) throws Exception {
        // リストを初期化
        List<Student> list = new ArrayList<>();
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet rSet = null;
        // SQL文のソート
        String order = "order by no asc";
        // SQL文の在学フラグ
        String conditionIsAttend = "";
        // 在学フラグがtrueの場合
        if (isAttend) {
            conditionIsAttend = "and is_attend = true";
        }

        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
            // プリペアードステートメントに学校コードをバインド
            statement.setString(1, school.getCd());
            // プリペアードステートメントを実行
            rSet = statement.executeQuery();
            // リストへの格納処理を実行
            list = postFilter(rSet, school);

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



    // 学生番号を指定して学生情報を取得する
    // ない場合はnullを返す

    public Student get(String no) throws Exception {
    	// 学生インスタンスを初期化
    	Student student = new Student();
    	// データベースへのコネクションを確立
    	Connection connection = getConnection();
    	// プリペアードステートメント
    	PreparedStatement statement = null;

    	try {
    		// プリペアードステートメントにSQL文をセット
    		statement = connection.prepareStatement("select * from student where no = ?");
    		// プリペアードステートメントに学生番号をバインド
    		statement.setString(1, no);
    		// プリペアードステートメントを実行
    		ResultSet rSet = statement.executeQuery();

    		// 学校Daoを初期化
    		SchoolDao schoolDao = new SchoolDao();

    		if (rSet.next()) {
    			// リザルトセットが存在する場合
    			// 学生インスタンスに検索結果をセット
    			student.setNo(rSet.getString("no"));
    	        student.setName(rSet.getString("name"));
    	        student.setEntYear(rSet.getInt("ent_year"));
    	        student.setClassNum(rSet.getString("class_num"));
    	        student.setAttend(rSet.getBoolean("is_attend"));
    	        // 学校フィールドには学校コードで検索した学校インスタンスをセット
    	        student.setSchool(schoolDao.get(rSet.getString("school_cd")));
    		} else {
    			// リザルトセットが存在しない場合
    			// 学生インスタンスにnullをセット
    			student = null;
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
    return student;
	}

    // 在学中フラグのみ絞り込める
 // 在学中の学生のみを絞り込んで取得する
    public List<Student> filter(School school) throws Exception {
        List<Student> list = new ArrayList<>();

        // DB接続
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // SQL文: 学校コードと在学中フラグで絞り込み
            String sql = "SELECT * FROM student WHERE school_cd = ? AND is_attend = true ORDER BY ent_year, class_num, no";

            // ステートメントの準備
            statement = connection.prepareStatement(sql);
            statement.setString(1, school.getCd());

            // 実行
            ResultSet result = statement.executeQuery();

            // 学校情報は引数から設定するので、SchoolDaoは不要
            while (result.next()) {
                Student student = new Student();

                student.setNo(result.getString("no"));
                student.setName(result.getString("name"));
                student.setEntYear(result.getInt("ent_year"));
                student.setClassNum(result.getString("class_num"));
                student.setAttend(result.getBoolean("is_attend"));
                student.setSchool(school); // 引数からセット

                list.add(student);
            }

            result.close(); // リザルトセットのクローズ

        } catch (Exception e) {
            throw e;
        } finally {
            // ステートメントのクローズ
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }

            // コネクションのクローズ
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



 // 学生番号と学校コードを指定して学生情報を取得する
    public Student get(String no, String schoolCd) throws Exception {
        Student student = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            String sql = "SELECT * FROM student WHERE no = ? AND school_cd = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, no);
            statement.setString(2, schoolCd);

            ResultSet rSet = statement.executeQuery();
            SchoolDao schoolDao = new SchoolDao();

            if (rSet.next()) {
                student = new Student();
                student.setNo(rSet.getString("no"));
                student.setName(rSet.getString("name"));
                student.setEntYear(rSet.getInt("ent_year"));
                student.setClassNum(rSet.getString("class_num"));
                student.setAttend(rSet.getBoolean("is_attend"));
                student.setSchool(schoolDao.get(rSet.getString("school_cd")));
            }

        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return student;
    }


	// 学生インスタンスをデータベースに保存するメソッド
	public boolean save(Student student) throws Exception {
	    // コネクションを確立
	    Connection connection = getConnection();
	    // プリペアードステートメント
	    PreparedStatement statement = null;
	    // 実行件数
	    int count = 0;

	    try {
	        // データベースから学生を取得
	        Student old = get(student.getNo());
	        if (old == null) {
	            // 学生が存在しなかった場合
	            // プリペアードステートメントにINSERT文をセット
	            statement = connection.prepareStatement(
	                    "insert into student (no, name, ent_year, class_num, is_attend, school_cd) values (?, ?, ?, ?, ?, ?)");

	            // プリペアードステートメントに値をバインド
	            statement.setString(1, student.getNo());
	            statement.setString(2, student.getName());
	            statement.setInt(3, student.getEntYear());
	            statement.setString(4, student.getClassNum());
	            statement.setBoolean(5, student.isAttend());
	            statement.setString(6, student.getSchool().getCd());
	        } else {
	            // 学生が存在した場合
	            // プリペアードステートメントにUPDATE文をセット
	            statement = connection.prepareStatement(
	                    "update student set name = ?, ent_year = ?, class_num = ?, is_attend = ? where no = ?");

	            // プリペアードステートメントに値をバインド
	            statement.setString(1, student.getName());
	            statement.setInt(2, student.getEntYear());
		        statement.setString(3, student.getClassNum());
		        statement.setBoolean(4, student.isAttend());
		        statement.setString(5, student.getNo());
	        }
	        // プリペアードステートメントを実行
	        count = statement.executeUpdate();

	    } catch(SQLIntegrityConstraintViolationException dupEx){
	    	// マルチアクセスが起きた場合に備えてSQLでの重複エラー表示
	        // SQLでinsertしようとした場合に重複が起きた際ここでキャッチ
	    	return false;
	    }catch (Exception e) {
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



}
