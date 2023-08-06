package model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 与数据库建立连接
 */
public class DBUtil {
private static DataSource dataSource=new MysqlDataSource();

static{
    ((MysqlDataSource)dataSource).setURL("jdbc:mysql://127.0.0.1:3306/blogsystem?characterEncoding=utf8&useSSL=false");
    ((MysqlDataSource)dataSource).setUser("root");
    ((MysqlDataSource)dataSource).setPassword("123456");
}

public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
}

public static void close(Connection connection, Statement statement, ResultSet resultSet) {
    if (resultSet != null) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    if (statement != null) {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    if (connection != null) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

}
