package dao;

import bean.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.omg.CORBA.StringHolder;
import util.HibernateUtil;

import java.util.List;

public class UserDao {

    //用户保存
    public boolean saveUser(User user){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("用户保存失败");
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }

    //用户注销
    public boolean deleteUser(User user){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("用户删除失败");
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }

    //用户修改
    public boolean modifyUser(User user){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            User user_1 = session.get(User.class,user.getUser_id());
            user_1.setUsername(user.getUsername());
            user_1.setPassword(user.getPassword());
            user_1.setSalt(user.getSalt());
            session.flush();
            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("用户修改失败");
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }

    //用户查询
    public User findUser(String username){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            String hql = "from User user where user.username = ?";
            Query query = session.createQuery(hql);
            query.setParameter(0,username);
            List<User> users = query.list();
            for (User user : users) {
                return user;
            }
            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("用户查询失败");
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.closeSession();
        }
        return null;
    }
}
