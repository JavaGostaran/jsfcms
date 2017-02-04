/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsfcms.dao.Compound;

/**
 *
 * @author ahmadi
 */
@Stateless
public class CompoundFacade extends AbstractFacade<Compound> {
    @PersistenceContext(unitName = "jsfcms10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompoundFacade() {
        super(Compound.class);
    }
    
}
