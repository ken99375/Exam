package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{


	 //所属校と科目コードで絞り込んで科目テーブルの一行を返す
	public Subject get(String cd, School school) throws Exception{
		// 科目インスタンスを初期化
    	Subject subject = new Subject();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
    		// プリペアードステートメントにSQL文をセット
    		statement = connection.prepareStatement("select * from subject where cd = ? and school_cd = ? and is_attend = true");
    		// プリペアードステートメントに学生番号をバインド
    		statement.setString(1, cd);
    		statement.setString(2, school.getCd());
    		// プリペアードステートメントを実行
    		ResultSet rSet = statement.executeQuery();

    		if (rSet.next()) {
            	// 科目オブジェクトにselectの結果を設定
            	subject.setCd(rSet.getString("cd"));
            	subject.setName(rSet.getString("name"));
            	subject.setSchool(school);
    		} else {
    			// リザルトセットが存在しない場合
    			// 学生インスタンスにnullをセット
    			subject = null;
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
    return subject;
	}
	 //所属校と科目コードで絞り込んで科目テーブルの一行を返す
	public Subject allGet(String cd, School school) throws Exception{
		// 科目インスタンスを初期化
   	Subject subject = new Subject();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
   		// プリペアードステートメントにSQL文をセット
   		statement = connection.prepareStatement("select * from subject where cd = ? and school_cd = ? ");
   		// プリペアードステートメントに学生番号をバインド
   		statement.setString(1, cd);
   		statement.setString(2, school.getCd());
   		// プリペアードステートメントを実行
   		ResultSet rSet = statement.executeQuery();

   		if (rSet.next()) {
           	// 科目オブジェクトにselectの結果を設定
           	subject.setCd(rSet.getString("cd"));
           	subject.setName(rSet.getString("name"));
           	subject.setSchool(school);
   		} else {
   			// リザルトセットが存在しない場合
   			// 学生インスタンスにnullをセット
   			subject = null;
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
   return subject;
}

	// 所属校に登録された科目一覧を返すメソッド
	public List<Subject> filter(School school){
		// リストを初期化
        List<Subject> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rSet = null;
        // SQL文のソート
        String order = "order by cd asc";

        try {
        	// 接続の取得
        	connection = getConnection();

            statement = connection.prepareStatement("select * from subject where school_cd = ? and is_attend = true " + order);
            statement.setString(1, school.getCd());

            rSet = statement.executeQuery();

            // リストへの格納処理を実行
            while (rSet.next()){
            	// 科目インスタンスを初期化
            	Subject subject = new Subject();
            	// 科目オブジェクトにselectの結果を設定
            	subject.setCd(rSet.getString("cd"));
            	subject.setName(rSet.getString("name"));
            	subject.setSchool(school);
            	list.add(subject);
            }


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

	// 科目の追加メソッド　
	// アクションクラスで設定されたオブジェクトを使用して
	// updateに当てはめる
	public boolean save(Subject subject) throws Exception{
		// コネクションを確立
	    Connection connection = getConnection();
	    // プリペアードステートメント
	    PreparedStatement statement = null;
	    // 実行件数
	    int count = 0;

	    try {
	        // データベースから科目を取得
	        Subject old = allGet(subject.getCd(), subject.getSchool());
	        System.out.println(old);

	        if (old == null) {
	            // 学生が存在しなかった場合
	            // プリペアードステートメントにINSERT文をセット
	            statement = connection.prepareStatement(
	                    "insert into subject(school_cd, cd, name) values(?, ?, ?) ");

	            // プリペアードステートメントに値をバインド
	            statement.setString(1, subject.getSchool().getCd());
	            statement.setString(2, subject.getCd());
	            statement.setString(3, subject.getName());

	        } else {
	            // 科目が存在した場合
	            // プリペアードステートメントにUPDATE文をセット
	            statement = connection.prepareStatement(
	                    "update subject set  name = ?,  is_attend = true where school_cd = ? and cd = ?");

	            // プリペアードステートメントに値をバインド
	            statement.setString(1, subject.getName());
	            statement.setString(2, subject.getSchool().getCd());
	            statement.setString(3, subject.getCd());
	        }
	        // プリペアードステートメントを実行
	        count = statement.executeUpdate();

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

	// 科目の削除メソッド
	// delete・update文って帰ってくるものは削除行数だけじゃなかったけ？
	public boolean delete(Subject subject) throws Exception{
		// コネクションを確立
	    Connection connection = getConnection();
	    // プリペアードステートメント
	    PreparedStatement statement = null;
	    // 実行件数
	    int count = 0;

	    try {
	        statement = connection.prepareStatement("delete subject where school_cd = ? and cd = ? and name = ?");
	        statement.setString(1, subject.getSchool().getCd());
	        statement.setString(2, subject.getCd());
	        statement.setString(3, subject.getName());

	        // プリペアードステートメントを実行
	        count = statement.executeUpdate();

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

	// 科目の削除メソッド
	// delete・update文って帰ってくるものは削除行数だけじゃなかったけ？
	public boolean deleteAttend(Subject subject) throws Exception{
		// コネクションを確立
	    Connection connection = getConnection();
	    // プリペアードステートメント
	    PreparedStatement statement = null;
	    // 実行件数
	    int count = 0;

	    try {
	        statement = connection.prepareStatement("update subject set is_attend = false where school_cd = ? and cd = ?");
	        statement.setString(1, subject.getSchool().getCd());
	        statement.setString(2, subject.getCd());


	        // プリペアードステートメントを実行
	        count = statement.executeUpdate();

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
}
