/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.bean;

import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Root;

/**
 *
 * @author ahmadi
 */
@SessionScoped

public abstract class AbstractFacade<T> extends Globals{
    private Class<T> entityClass;
    

    
    //private int cmsId;

  
    
    
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
 
    public void setCmdsId(int i)
    {
        System.out.println("i is" + i);
        cmsId=i;
        System.out.println("cmsId is" + getCmsId());
    }
    
    //ok
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId()));
        return getEntityManager().createQuery(cq).getResultList();
    }
    //ok
    public List<T> findAll_true() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"),true),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        return getEntityManager().createQuery(cq).getResultList();
    }
    //ok
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId()));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    //ok
    public List<T> findRange1(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    public List<T> findRange_userRoles_without_cmsID(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    //ok
    public List<T> findRange_InComboBox1(int[] range) { //ok
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        
        
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId()),
                getEntityManager().getCriteriaBuilder().notEqual(c.get("information"),"blog"),
                getEntityManager().getCriteriaBuilder().notEqual(c.get("information"),"root"),
                getEntityManager().getCriteriaBuilder().notEqual(c.get("parentID").get("information"),"blog")
               
                
                ));
     
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    //ok
    public List<T> findRange_InComboBox2(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
            getEntityManager().getCriteriaBuilder().equal(c.get("information"),"blog"),
            getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    public List<T> find_Root() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
            getEntityManager().getCriteriaBuilder().equal(c.get("information"),"root"),
            getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
    //ok
    public List<T> findRange_InComboBox2() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
            getEntityManager().getCriteriaBuilder().equal(c.get("information"),"blog"),
            getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
    //ok
    public List<T> findRange_InComboBox3(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
            getEntityManager().getCriteriaBuilder().equal(c.get("parentID").get("information"),"blog"),
            getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    //ok
    public List<T> findRange_InComboBox(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().notEqual(c.get("parentID").get("information"),"blog"),
                getEntityManager().getCriteriaBuilder().notEqual(c.get("information"),"blog"),
                getEntityManager().getCriteriaBuilder().notEqual(c.get("information"),"root"),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    //ok
    public List<T> findRange_InCategaryList(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println("cms cms is " + getCmsId());
        cq.where(
                
                getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId()),
                getEntityManager().getCriteriaBuilder().notEqual(c.get("parentID").get("information"), "blog"),
                getEntityManager().getCriteriaBuilder().notEqual(c.get("information"), "root")
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

//ok
public List<T> findRange(int[] range,int id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id+":id"+col+":col"+ids+":ids");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids),id),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange(int[] range,String id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id+":id"+col+":col"+ids+":ids");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids),id),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRangeForContentHaveComment(int[] range,String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().notEqual(c.get(col),null),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findUser(String username) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get("username"),username)
                //getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
//ok
public List<T> findRange_true(int[] range, int id, String col, String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_true(int[] range, String s, String col, String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(s + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), s),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_true(String s, String col, String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(s + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), s),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
//ok
public List<T> findRange_true_date(String s, String col, String ids,Date first,Date second) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println("first is " + first);
        System.out.println("second is " + second);
        System.out.println(c.get(col).toString());
        System.out.println(s + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), s),
                getEntityManager().getCriteriaBuilder().between(c.<Date>get("date"), first, second),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
//ok
public List<T> findRange_true(int[] range, String s, String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col), s),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_true_not_compound(int[] range, int id, String col, String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("isCompound"), 0),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_true_not_compound(int[] range, String id, String col, String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("isCompound"), 0),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_not_compound(int[] range, int id, String col, String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("isCompound"), 0),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_not_compound(int[] range, String id, String col, String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("isCompound"), 0),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_true(int[] range, int id, String col, String ids, int id1, String col1, String ids1) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), id1),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_true_not_compound(int[] range, int id, String col, String ids, int id1, String col1, String ids1) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("isCompound"), 0),
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), id1),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_true_not_compound(int[] range, String id, String col, String ids, int id1, String col1, String ids1) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("isCompound"), 0),
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), id1),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_true(int id, String col, String ids, int id1, String col1, String ids1) {//عین بالایی فقط رینج رو حذف کردیم
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), id1),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
public List<T> findRange(int id, String col, String ids, int id1, String col1, String ids1) {//عین بالایی فقط رینج رو حذف کردیم
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), id1),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                )
                );
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
//ok
public List<T> findRange_true1(int id, String col, String ids, String id1, String col1, String ids1) {//عین بالایی فقط رینج رو حذف کردیم
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId()),
                getEntityManager().getCriteriaBuilder().or(
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), id1),
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), "ایستا"),
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), "ترکیبی")
                )
                )
                );
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }

//ok
public List<T> findRange_true(int[] range, int id, String col, String ids, String col1, String ids1) {//عین بالایی فقط رینج رو حذف کردیم
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id + ":id" + col + ":col" + ids + ":ids");
        System.out.println("hihihihihi");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId()),
                getEntityManager().getCriteriaBuilder().or(
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), "متن"),
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), "ایستا"),
                getEntityManager().getCriteriaBuilder().equal(c.get(col1).get(ids1), "ترکیبی")
                )
                )
                );
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
//ok
public List<T> findRange_user(String col, String ids, String col1, String ids1) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col), ids),
                getEntityManager().getCriteriaBuilder().equal(c.get(col1), ids1)
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
//ok
public  List<T> findRange(int id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id+":id"+col+":col"+ids+":ids");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        //q.setMaxResults(range[1] - range[0]);
        //q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public  List<T> findRange1(int id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id+":id"+col+":col"+ids+":ids");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        //q.setMaxResults(range[1] - range[0]);
        //q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public  List<T> findRangeItemsInTagTable(String value) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get("tagName"), value),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
//ok
public  List<T> findRange_In_compoundController(int id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).get(ids).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id+":id"+col+":col"+ids+":ids");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
                
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        //q.setMaxResults(range[1] - range[0]);
        //q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public  List<T> findCompound(int i,int r) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get("idc"), i),
                getEntityManager().getCriteriaBuilder().equal(c.get("rankContent"), r),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
public  List<T> find_true(int i,String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col), i),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), 1),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
public  List<T> find_true(String s,String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col), s),
                getEntityManager().getCriteriaBuilder().equal(c.get("visibility"), 1),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"), getCmsId())
                ));
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
//ok
public List<T> findRange(int[] range,int id,String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id+":id"+col+":col"+":ids");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col),id),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_asc(int[] range,int id,String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id+":id"+col+":col"+":ids");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col),id),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        cq.orderBy(getEntityManager().getCriteriaBuilder().asc(c.get("rankContent")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
public List<T> findRange_asc(int id,String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        System.out.println(c.get(col).toString());
        System.out.println(c.get(col).toString());
        System.out.println(id+":id"+col+":col"+":ids");
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get(col),id),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                ));
        cq.orderBy(getEntityManager().getCriteriaBuilder().asc(c.get("rankContent")));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
//ok
public List<T> findRange() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                );
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
//ok
public List<T> findRange_user(String u,String p) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> c = cq.from(entityClass);
        cq.select(c);
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(c.get("username"),u),
                getEntityManager().getCriteriaBuilder().equal(c.get("password"),p),
                getEntityManager().getCriteriaBuilder().notEqual(c.get("id"),1),
                getEntityManager().getCriteriaBuilder().equal(c.get("cmsID").get("id"),getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        //q.setMaxResults(range[1] - range[0]);
        //q.setFirstResult(range[0]);
        return q.getResultList();
    }
//ok
     public int count(int id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                ));
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
//ok
     public int count(String id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                ));
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
//ok
     public int count_user(String u,String p) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                 getEntityManager().getCriteriaBuilder().equal(rt.get("username"), u),
                 getEntityManager().getCriteriaBuilder().equal(rt.get("password"), p),
                 getEntityManager().getCriteriaBuilder().notEqual(rt.get("id"),1),
                 getEntityManager().getCriteriaBuilder().notEqual(rt.get("cmsID").get("id"),getCmsId())
                )
                );
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_true(int id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_true(String s,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), s),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
     }

     //ok
     public int count_true(String s,String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col), s),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_true_not_compound(int id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get("isCompound"), false),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_true_not_compound(String id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get("isCompound"), false),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_not_compound(int id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("isCompound"), false),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_not_compound(String id,String col,String ids) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("isCompound"), false),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_true(int id,String col,String ids,int id1,String col1,String ids1) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get(ids1), id1),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_true_not_compound(int id,String col,String ids,int id1,String col1,String ids1) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get("isCompound"), false),
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get(ids1), id1),
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get("cmsID").get("id"), getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_true_not_compound(String id,String col,String ids,int id1,String col1,String ids1) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get("isCompound"), false),
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get(ids1), id1),
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get("cmsID").get("id"), getCmsId())
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_true1(int id,String col,String ids,String id1,String col1,String ids1) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId()),
                getEntityManager().getCriteriaBuilder().or(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get(ids1), id1),
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get(ids1), "ایستا"),
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get(ids1), "ترکیبی")
                )
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_true(int id,String col,String ids,String col1,String ids1) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col).get(ids), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId()),
                getEntityManager().getCriteriaBuilder().or(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get(ids1), "متن"),
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get(ids1), "ایستا"),
                getEntityManager().getCriteriaBuilder().equal(rt.get(col1).get(ids1), "ترکیبی")
                )
                )
                );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count(int id,String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                ));
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int count_true(int id,String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get(col), id),
                getEntityManager().getCriteriaBuilder().equal(rt.get("visibility"), true),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
        )
        );
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
     public int countForContentHaveComment(String col) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().notEqual(rt.get(col), null),
                getEntityManager().getCriteriaBuilder().notEqual(rt.get("cmsID").get("id"), getCmsId())
                ));
        
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
     //ok
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId()));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    public int count1() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    public int count_userRoles_without_cmsID() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    //ok
    public int count_InComboBox1() {
       javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId()),
                getEntityManager().getCriteriaBuilder().notEqual(rt.get("information"),"blog"),
                getEntityManager().getCriteriaBuilder().notEqual(rt.get("information"),"root"),
                getEntityManager().getCriteriaBuilder().notEqual(rt.get("parentID").get("information"), "blog")
                ));
                
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    //ok
    public int count_InComboBox2() {
       javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get("information"),"blog"),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    //ok
    public int count_InComboBox3() {
       javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get("parentID").get("information"),"blog"),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    //ok
    public int count_InComboBox() {
       javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().notEqual(rt.get("parentID").get("information"), "blog"),
                getEntityManager().getCriteriaBuilder().notEqual(rt.get("information"), "blog"),
                getEntityManager().getCriteriaBuilder().notEqual(rt.get("information"), "root"),
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId())
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    //ok
    public int count_InCategaryList() {
       javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        cq.where(getEntityManager().getCriteriaBuilder().and(
                getEntityManager().getCriteriaBuilder().equal(rt.get("cmsID").get("id"), getCmsId()),
                getEntityManager().getCriteriaBuilder().notEqual(rt.get("parentID").get("information"), "blog"),
                getEntityManager().getCriteriaBuilder().notEqual(rt.get("information"), "root")
                ));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
