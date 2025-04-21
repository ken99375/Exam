package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Dao {
    static DataSource ds;// ds:接続先の変数

    // getConnectionメソッド:接続先のDB設定
    public Connection getConnection() throws Exception {
    	// 接続していなければ実行する
        if (ds == null) {
            InitialContext ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/exem");
        }
        return ds.getConnection();
    }
}