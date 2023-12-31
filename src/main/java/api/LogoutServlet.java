package api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession=req.getSession(false);
        if(httpSession==null){
            //未登录状态
            resp.setContentType("text/html;charset=utf8");
            resp.getWriter().write("not login in");
            return;
        }
        httpSession.removeAttribute("user");
        resp.sendRedirect("blog_login.html");
    }
}
