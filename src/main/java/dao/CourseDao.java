package dao;

import bean.Course;
import org.hibernate.Session;
import util.HibernateUtil;

public class CourseDao {
    public static void main(String[] args) {
        Session session = null;
        Course course = new Course();
        course.setCourseName("Web技术");
        course.setTeacher("胡伦");
        course.setResources(null);
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(course);
            session.getTransaction().commit();
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("数据添加失败");
            e.printStackTrace();
        }finally {
            HibernateUtil.closeSession();
        }
    }

}
