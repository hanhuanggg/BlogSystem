package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public User selectByUserID(int userId){
        User user=new User();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;

        try {
            connection=DBUtil.getConnection();
            String sql="select * from user where userID=?;";
            statement=connection.prepareStatement(sql);
            statement.setInt(1,userId);
            resultSet=statement.executeQuery();
            if(resultSet.next()){
               user.setUserId(resultSet.getInt("userId"));
               user.setUsername(resultSet.getString("username"));
               user.setPassword(resultSet.getString("password"));
               return user;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }

        return null;
    }

    public User selectByUserName(String userName){
        User user=new User();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;

        try {
            connection=DBUtil.getConnection();
            String sql="select * from user where username=?;";
            statement=connection.prepareStatement(sql);
            statement.setString(1,userName);
            resultSet=statement.executeQuery();
            if(resultSet.next()){
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }

        return null;

    }
}
