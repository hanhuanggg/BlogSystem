package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
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
        resp.setContentType("application/json;charset=utf8");
        resp.getWriter().write(jsonResp);
        }else {
            //博客详情页
           Blog blog=new Blog();
           blog=blogDao.selectById(Integer.parseInt(blogId));
           String jsonResp=objectMapper.writeValueAsString(blog);
           resp.setContentType("application/json;charset=utf8");
           resp.getWriter().write(jsonResp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //判断是否登录状态
        HttpSession httpSession=req.getSession(false);
        if(httpSession==null){
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("can not post blog because you have not logan in");
            return;
        }

        User user=(User)httpSession.getAttribute("user");
        if(user==null){
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("can not post blog because you have not logan in");
            return;
        }

        req.setCharacterEncoding("utf8");
        String title=req.getParameter("title");
        String content=req.getParameter("content");
        if(title==null||"".equals(title)||content==null||"".equals(content)){
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("can not post because the data might be wrong");
            return;
        }

        //构造blog对象
        Blog blog=new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setUserId(user.getUserId());
        blog.setDatetime(new Timestamp((System.currentTimeMillis())));


        BlogDao blogDao=new BlogDao();
        blogDao.add(blog);

        resp.sendRedirect("blog_list.html");

    }
}
