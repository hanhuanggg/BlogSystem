package api;

import model.User;
import model.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        String username=req.getParameter("username");
        String password=req.getParameter("password");

        if(username==null||"".equals(username)||password==null||"".equals(password)){
            String html="<h3>登录失败!缺少username 或者 password 字段</h3>";
            resp.getWriter().write(html);
            return;
        }

        //2.读数据库,查看数据是否存在切密码正确
        UserDAO userDAO=new UserDAO();
        User user= userDAO.selectByUserName("makabazi");
        if(user==null){
            String html="<h3>登录失败!用户名或者密码错误</h3>";
            resp.getWriter().write(html);
            return;
        }
        if(!password.equals(user.getPassword())){
            String html="<h3>登录失败!用户名或者密码错误</h3>";
            resp.getWriter().write(html);
            return ;
        }

        //用户密码验证成功,创建对话
        HttpSession session=req.getSession(true);
        session.setAttribute("user",user);

        resp.sendRedirect("blog_list.html");

    }
}

