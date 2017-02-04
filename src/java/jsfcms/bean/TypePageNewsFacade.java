/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsfcms.dao.TypePageNews;

/**
 *
 * @author ahmadi
 */
@Stateless
public class TypePageNewsFacade extends AbstractFacade<TypePageNews> {
    @PersistenceContext(unitName = "jsfcms10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypePageNewsFacade() {
        super(TypePageNews.class);
    }
    
}
