package dao;

import bean.Course;
import bean.Resource;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

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
    public boolean modifyResource(Resource resource){
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

    //查询资源
    public Resource findResource(String resourceName, Integer course_id){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            String hql = "from Resource resource where resource.resourceName = ? and resource.course_id = ?";
            Query query = session.createQuery(hql);
            query.setParameter(0,resourceName);
            query.setParameter(1,course_id);
            List<Resource> resources = query.list();
            session.getTransaction().commit();
            for (Resource resource : resources) {
                return resource;
            }
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("查询资源失败");
            e.printStackTrace();
            return null;
        }finally {
            HibernateUtil.closeSession();
        }
        return null;
    }

    //根据课程id所有资源
    public List<Resource> findAllResource(Integer course_id){
        Session session = null;
        try{
            session = HibernateUtil.getSession();
            session.beginTransaction();
            String hql = "from Resource resource where resource.course_id = ?";
            Query query = session.createQuery(hql);
            query.setParameter(0,course_id);
            List<Resource> resources = query.list();
            session.getTransaction().commit();
            return resources;
        }catch (Exception e){
            if(session != null) session.getTransaction().rollback();
            System.out.println("查询资源失败");
            e.printStackTrace();
            return new ArrayList<Resource>();
        }finally {
            HibernateUtil.closeSession();
        }
    }

}



