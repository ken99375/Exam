package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

    public ClassNum get(String class_num, School school) throws Exception {
    	// クラス番号インスタンスを初期化
        ClassNum classNum = new ClassNum();
        // データベースへのコネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement("select * from class_num where class_num = ? and school_cd = ?");
            // プリペアードステートメントに値をバインド
            statement.setString(1, class_num);
            statement.setString(2, school.getCd());
            // プリペアードステートメントを実行
            ResultSet rSet = statement.executeQuery();
            // 学校Daoを初期化
            SchoolDao sDao = new SchoolDao();
            if (rSet.next()) {
                // リザルトセットが存在する場合
                // クラス番号インスタンスに検索結果をセット
            	classNum.setName(rSet.getString("name"));
                classNum.setClassNum(rSet.getString("class_num"));
                classNum.setSchool(sDao.get(rSet.getString("school_cd")));
            } else {
                // リザルトセットが存在しない場合
                // クラス番号インスタンスにnullをセット
                classNum = null;
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

        return classNum;
    }

    public List<ClassNum> filter(School school) throws Exception {
        // リストを初期化
        List<ClassNum> list = new ArrayList<>();
        // データベースへのコネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement("select * from class_num where school_cd = ? order by class_num");
            // プリペアードステートメントに学校コードをバインド
            statement.setString(1, school.getCd());
            // プリペアードステートメントを実行
            ResultSet rSet = statement.executeQuery();
            // リザルトセットを全件走査
            while (rSet.next()) {
                ClassNum classNum = new ClassNum();
                classNum.setSchool(school);
                classNum.setName(rSet.getString("name"));
                classNum.setClassNum(rSet.getString("class_num"));
                list.add(classNum);
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

    public boolean save(ClassNum classNum) throws Exception {
        Connection conn = getConnection();

        String sql = "INSERT INTO CLASS_NUM (CLASS_NUM, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, classNum.getClassNum());
        stmt.setString(2, classNum.getName());
        stmt.setString(3, classNum.getSchool().getCd());

        int rows = stmt.executeUpdate();

        stmt.close();
        conn.close();

        return rows > 0;
    }
}
//
//    public boolean save(ClassNum classNum, String newClassNum) throws Exception {
//
//    }