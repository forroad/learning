package dao;

import bean.Course;
import bean.User;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.zip.CheckedOutputStream;

public class Test {
    public static void main(String[] args) {
        CourseDao courseDao = new CourseDao();
        UserDao userDao = new UserDao();

        User user = userDao.findUser("zyc");
        //user.setUsername("zyc");
        userDao.deleteUser(user);
    }

    public static Course getCorse(){
        CourseDao courseDao = new CourseDao();
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            Course course = session.get(Course.class,1);
            System.out.println(course.getCourseName());
            return course;
        }catch (Exception e){
            System.out.println("数据查询失败");
            e.printStackTrace();
            return null;
        }
    }
}
