package dao;

import bean.Course;
import bean.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;
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

    //查询课程
    public Course findCourse(String courseName,Integer user_id){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            String hql = "from Course course where course.courseName = ? and course.user_id = ?";
            Query query = session.createQuery(hql);
            query.setParameter(0,courseName);
            query.setParameter(1,user_id);
            List<Course> courses = query.list();
            session.getTransaction().commit();
            for (Course course : courses) {
                return course;
            }
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("查询课程失败");
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.closeSession();
        }
        return null;
    }
    //根据用户id查询所有课程
    public List<Course> findAllCourse(Integer user_id){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            String hql = "from Course course where course.user_id = ?";
            Query query = session.createQuery(hql);
            query.setParameter(0,user_id);
            List<Course> courses = query.list();
            session.getTransaction().commit();
            return courses;
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("查询课程失败");
            e.printStackTrace();
            return new ArrayList<Course>();
        }finally {
            HibernateUtil.closeSession();
        }
    }

}
