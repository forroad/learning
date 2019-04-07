package servlet.account;

import bean.Result;
import bean.User;
import dao.UserDao;
import util.AccountUtil;
import util.JsonUtil;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String username = request.getParameter("username");
        //判断用户名是否已注册
        if(userDao.findUser(username) != null){
            JsonUtil.json(response,new Result("用户名已存在",null));
            return;
        }
        String password = request.getParameter("password");
        String salt = System.currentTimeMillis() + "";
        //新建实例
        User user = new User();
        user.setUsername(username);
        user.setPassword(AccountUtil.EncodePassword(password,salt));
        user.setSalt(salt);
        //保存用户信息
        if(userDao.saveUser(user)){
            JsonUtil.json(response,new Result("注册成功",user));
        }else{
            JsonUtil.json(response,new Result("服务器错误，请稍后重试！",null));
        }
    }
}
