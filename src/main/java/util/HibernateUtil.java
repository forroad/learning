package util;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private static SessionFactory sessionFactory = null;
    static {
        try{
            Configuration cfg = new Configuration().configure();
            sessionFactory = cfg.buildSessionFactory();
        }catch (Exception e){
            System.err.println("创建会话工厂失败！");
            e.printStackTrace();
        }
    }

    //获取session
    public static Session getSession() throws HibernateException{
        Session session = (Session)threadLocal.get();
        if(session == null || !session.isOpen()){
            if (sessionFactory != null){
                rebuidSessionFactory();
            }
            session = (sessionFactory != null)?sessionFactory.openSession():null;
            threadLocal.set(session);
        }
        return session;
    }

    //重建会话工厂
    public static void rebuidSessionFactory(){
        try{
            //加载Hibernate配置文件
            Configuration cfg = new Configuration().configure();
            sessionFactory = cfg.buildSessionFactory();
        }catch (Exception e){
            System.err.println("创建会话工厂失败！");
            e.printStackTrace();
        }
    }

    //获取SessionFactory对象
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    //关闭session
    public static void closeSession() throws HibernateException{
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if(session != null){
            session.close();
        }
    }








}
