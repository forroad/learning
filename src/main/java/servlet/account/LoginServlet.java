package servlet.account;

import bean.Result;
import bean.User;
import dao.UserDao;
import util.AccountUtil;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //查询用户
        User user = userDao.findUser(username);
        //判断用户是否存在
        if(user == null){
            JsonUtil.json(response,new Result("用户不存在",null));
            return;
        }
        //判断密码是否相等
        if(!user.getPassword().equals(AccountUtil.EncodePassword(password,user.getSalt()))){
            JsonUtil.json(response,new Result("密码错误",null));
            return;
        }
        //信息符合，登录成功，将登录信息存入会话中
        HttpSession session = request.getSession();
        JsonUtil.json(response,new Result("登录成功",user));
        session.setAttribute("username",username);
    }
}
