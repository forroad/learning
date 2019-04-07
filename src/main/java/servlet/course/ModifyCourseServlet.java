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
import java.util.List;

public class ModifyCourseServlet extends HttpServlet {
    private final CourseDao courseDao = new CourseDao();
    private final UserDao userDao = new UserDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数
        String courseName = request.getParameter("courseName");
        String new_courseName = request.getParameter("new_courseName");
        String new_teacher = request.getParameter("new_teacher");
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

        //设置新属性
        course.setCourseName(new_courseName);
        course.setTeacher(new_teacher);


        if(courseDao.modifyCourse(course)){
            JsonUtil.json(response,new Result("修改成功",course));
        }else {
            JsonUtil.json(response,new Result("修改失败，请稍后重试",null));
        }

    }
}
