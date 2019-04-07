package servlet.course;

import bean.Course;
import bean.Result;
import bean.User;
import dao.CourseDao;
import dao.UserDao;
import util.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddCourseServlet extends HttpServlet {
    private final CourseDao courseDao = new CourseDao();
    private final UserDao userDao = new UserDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String courseName = request.getParameter("courseName");
        String teacher = request.getParameter("teacher");
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
        if(course != null){
            JsonUtil.json(response,new Result("课程已存在",null));
            return;
        }

        //新建课程实例
        course = new Course();
        course.setCourseName(courseName);
        course.setTeacher(teacher);
        course.setUser_id(user.getUser_id());

        //保存课程信息
        if( courseDao.saveCourse(course)){
            JsonUtil.json(response,new Result("添加课程成功",course));
        }else{
            JsonUtil.json(response,new Result("服务器错误，请稍后重试！",null));
        }
    }
}
