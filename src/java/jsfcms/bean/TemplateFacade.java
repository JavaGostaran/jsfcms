/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsfcms.dao.Template;

/**
 *
 * @author ahmadi
 */
@Stateless
public class TemplateFacade extends AbstractFacade<Template> {
    @PersistenceContext(unitName = "jsfcms10PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TemplateFacade() {
        super(Template.class);
    }
    
}
