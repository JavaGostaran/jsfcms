/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsfcms.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Root;
import jsfcms.dao.UserRoles;
import jsfcms.jsf.CmsController;
import jsfcms.jsf.util.JsfUtil;
import jsfcms.jsf.util.PaginationHelper;

/**
 *
 * @author ahmadi
 */



import jsfcms.dao.UserRoles;
import jsfcms.jsf.util.JsfUtil;
import jsfcms.jsf.util.PaginationHelper;
import jsfcms.bean.UserRolesFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "Globals")
@SessionScoped


public class Globals {
   public static int cmsId=1 ;
   


    public static int getCmsId() {
        System.out.println("getCmsId has value " + cmsId);
        return cmsId;
    }

    public static void setCmsId(int cmsId) {
        Globals.cmsId = cmsId;
        System.out.println("globals.cmsid is " + Globals.cmsId);
    }
}
