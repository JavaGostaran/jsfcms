/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsfcms.dao.TypePage;

/**
 *
 * @author ahmadi
 */
@Stateless
public class TypePageFacade extends AbstractFacade<TypePage> {
    @PersistenceContext(unitName = "jsfcms10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypePageFacade() {
        super(TypePage.class);
    }
    
}
