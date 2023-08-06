package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于数据库中的blog和服务器进行存取数据
 */
public class BlogDao {
    /**
     * 往blog表中存数据
     */
    public void add(Blog blog){
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        try {
            //1.建立连接
            connection=DBUtil.getConnection();
            //2.写sql语句
            String sql="insert into blog values(null,?,?,?,?);";
            statement=connection.prepareStatement(sql);
            statement.setString(1,blog.getTitle());
            statement.setString(2,blog.getContent());
            statement.setTimestamp(3,blog.getDatetime());
            statement.setInt(4,blog.getBlogId());
            //3.执行语句
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //4.关闭连接
            DBUtil.close(connection,statement,resultSet);
        }
    }

    /**
     * 根据blogId取数据
     */
    public Blog selectById(int blogId){
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        Blog blog=new Blog();

        try {
            //1.建立连接
            connection=DBUtil.getConnection();
            //2.sql语句
            String sql="select * from blog where blogId=?;";
            statement=connection.prepareStatement(sql);
            statement.setInt(1,blogId);
            //3.执行语句
            resultSet=statement.executeQuery();
            //4.遍历查询结果
            if(resultSet.next()){
                blog.setBlogId(resultSet.getInt("blogId"));
                blog.setTitle(resultSet.getString("title"));
                blog.setContent(resultSet.getString("content"));
                blog.setDatetime(resultSet.getTimestamp("datetime"));
                blog.setUserId(resultSet.getInt("userId"));
            //5.返回结果
                return blog;
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }
        return null;
    }

    /**
     * 获取blog表中的所有记录
     */
    public List<Blog> selectAll(){
        List<Blog> list=new ArrayList<>();
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;

        try {
            connection=DBUtil.getConnection();
            String sql="select * from blog order by postTime desc;";
            statement=connection.prepareStatement(sql);
            resultSet=statement.executeQuery();
            while (resultSet.next()){
                Blog blog=new Blog();
                blog.setBlogId(resultSet.getInt("blogId"));
                String content=resultSet.getString("content");
                //list页只展示部分blog内容
                if(content.length()>100){
                    content=content.substring(0,100)+"...";
                }
                blog.setContent(content);
                blog.setTitle(resultSet.getString("title"));
                blog.setDatetime(resultSet.getTimestamp("datetime"));
                blog.setUserId(resultSet.getInt("userId"));

                list.add(blog);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,statement,resultSet);
        }

        return null;
    }

    /**
     *删除某条blog
     */
    public void deleteById(int blogId){
        Connection connection=null;
        PreparedStatement statement=null;

        try {
            connection=DBUtil.getConnection();
            String sql="delete from blog where blogId=?;";
            statement=connection.prepareStatement(sql);
            statement.setInt(1,blogId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(connection,statement,null);
        }

    }


}
