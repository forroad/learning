package dao;

import bean.Resource;
import org.hibernate.Session;
import util.HibernateUtil;

public class ResourceDao {

    //资源保存
    public boolean saveResource(Resource resource){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.save(resource);
            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("资源保存失败");
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }

    //资源删除
    public boolean deleteResource(Resource resource){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            session.delete(resource);
            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("资源删除失败");
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }

    //修改资源
    public boolean modifyCourse(Resource resource){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            Resource resource_1 = session.get(Resource.class,resource.getResource_id());
            resource_1.setResourceName(resource.getResourceName());
            resource_1.setCatalog(resource.getCatalog());
            resource_1.setDescription(resource.getDescription());
            session.flush();
            session.getTransaction().commit();
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("资源修改失败");
            e.printStackTrace();
            return false;
        }finally {
            HibernateUtil.closeSession();
        }
        return true;
    }
}
