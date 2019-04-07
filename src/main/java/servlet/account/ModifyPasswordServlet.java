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
import java.io.IOException;

public class ModifyPasswordServlet extends HttpServlet {
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
        //设置新密码
        user.setPassword(AccountUtil.EncodePassword(password,user.getSalt()));
        //保存用户信息
        if(userDao.modifyUser(user)){
            JsonUtil.json(response,new Result("修改成功",user));
        }else{
            JsonUtil.json(response,new Result("服务器错误，请稍后重试！",null));
        }
    }
}
