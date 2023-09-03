package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    ObjectMapper objectMapper=new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String blogId=req.getParameter("blogId");
        BlogDao blogDao=new BlogDao();
        if(blogId==null){
            //博客列表页
        List<Blog> blogs=new ArrayList<>();
        blogs=blogDao.selectAll();
        String str=blogs.toString();
        String jsonResp=objectMapper.writeValueAsString(blogs);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(jsonResp);
        }else {
            //博客详情页
           Blog blog=new Blog();
           blog=blogDao.selectById(Integer.parseInt(blogId));
           String jsonResp=objectMapper.writeValueAsString(blog);
           resp.setContentType("application/json;charset=utf-8");
           resp.getWriter().write(jsonResp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
