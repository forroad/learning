package dao;

import bean.Course;
import org.hibernate.Session;
import util.HibernateUtil;

import java.util.zip.CheckedOutputStream;

public class CourseDao {

    //课程添加或修改
    public boolean saveCourse(Course course){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(course);
            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("课程保存失败");
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }

    //课程删除
    public boolean deleteCourse(Course course){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.delete(course);
            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("课程删除失败");
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }

    //修改课程
    public boolean modifyCourse(Course course){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            Course course_1 = session.get(Course.class,course.getCourse_id());
            course_1.setCourseName(course.getCourseName());
            course_1.setTeacher(course.getTeacher());
            course_1.setResources(course.getResources());
            session.flush();
            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("课程修改失败");
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }
}
