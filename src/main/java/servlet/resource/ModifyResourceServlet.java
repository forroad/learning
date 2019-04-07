package servlet.resource;

import bean.Course;
import bean.Resource;
import bean.Result;
import bean.User;
import dao.CourseDao;
import dao.ResourceDao;
import dao.UserDao;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ModifyResourceServlet extends HttpServlet {
    private final CourseDao courseDao = new CourseDao();
    private final ResourceDao resourceDao = new ResourceDao();
    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String resourceName = request.getParameter("resourceName");
        String new_resourceName = request.getParameter("new_resourceName");
        String courseName = request.getParameter("courseName");
        String username = request.getParameter("username");

        //查询用户
        User user = userDao.findUser(username);
        //判断用户是否存在
        if(user == null){
            JsonUtil.json(response,new Result("用户不存在",null));
            return;
        }

        //判断课程是否存在
        Course course = courseDao.findCourse(courseName,user.getUser_id());
        if(course == null){
            JsonUtil.json(response,new Result("课程不存在",null));
            return;
        }

        //判断资源是否已存在
        Resource resource = resourceDao.findResource(resourceName,course.getCourse_id());
        if(resource == null){
            JsonUtil.json(response,new Result("资源不存在",null));
            return;
        }

        resource.setResourceName(new_resourceName);

        if(resourceDao.modifyResource(resource)){
            JsonUtil.json(response,new Result("修改资源名称成功",resource));
        }else {
            JsonUtil.json(response,new Result("服务器错误，请稍后重试！",null));
        }
    }
}
