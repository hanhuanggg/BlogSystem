package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    ObjectMapper objectMapper=new ObjectMapper();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        String username=req.getParameter("username");
        String password=req.getParameter("password");

        if(username==null||"".equals(username)||password==null||"".equals(password)){
            String html="<h3>Login failed,please input password</h3>";
            resp.getWriter().write(html);
            return;
        }

        //2.读数据库,查看数据是否存在切密码正确
        UserDao userDAO=new UserDao();
        User user= userDAO.selectByUserName(username);
        if(user==null){
            String html="<h3>Login failed,userId or password wrong</h3>";
            resp.getWriter().write(html);
            return;
        }
        if(!password.equals(user.getPassword())){
            String html="<h3>Login failed,userId or password wrong</h3>";
            resp.getWriter().write(html);
            return ;
        }

        //用户密码验证成功,创建对话
        HttpSession session=req.getSession(true);
        session.setAttribute("user",user);

        resp.sendRedirect("blog_list.html");

    }

    /**
     * 该方法获取登录状态
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset:utf-8");
       HttpSession session=req.getSession(false);
       if(session==null){
           //则说明没有登录,返回一个空对象
           User user=new User();
           String respJson=objectMapper.writeValueAsString(user);
           resp.getWriter().write(respJson);
           return;
       }

       User user=(User)session.getAttribute("user");
       if(user==null){
           user=new User();
           String respJson=objectMapper.writeValueAsString(user);
           resp.getWriter().write(respJson);
           return;
       }

       String respJson=objectMapper.writeValueAsString(user);
       resp.getWriter().write(respJson);
    }
}

