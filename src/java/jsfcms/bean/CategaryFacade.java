/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsfcms.dao.Categary;

/**
 *
 * @author ahmadi
 */
@Stateless
public class CategaryFacade extends AbstractFacade<Categary> {
    @PersistenceContext(unitName = "jsfcms10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategaryFacade() {
        super(Categary.class);
    }
    
}
