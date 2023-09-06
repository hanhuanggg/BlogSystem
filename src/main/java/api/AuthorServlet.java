package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/author")
public class AuthorServlet extends HttpServlet {
    private ObjectMapper objectMapper=new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String blogId=req.getParameter("blogId");
        if(blogId==null){
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("the parameter is illegal,lack of \'blogId\'");
            return;
        }

        BlogDao blogDao=new BlogDao();
        Blog blog=blogDao.selectById(Integer.parseInt(blogId));
        if(blog==null){
            //说明数据库中没有对应的blog
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("no found pointed blog:blogId="+blogId);
            return;
        }

        //找到了
        UserDao userDao=new UserDao();
        User author=userDao.selectByUserID(blog.getUserId());
        String respJson=objectMapper.writeValueAsString(author);
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write(respJson);
    }
}
