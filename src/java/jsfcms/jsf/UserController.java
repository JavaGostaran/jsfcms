package jsfcms.jsf;

import java.io.IOException;
import jsfcms.dao.User;
import jsfcms.jsf.util.JsfUtil;
import jsfcms.jsf.util.PaginationHelper;
import jsfcms.bean.UserFacade;

import java.io.Serializable;
import java.util.Random;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import jsfcms.bean.Globals;
import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean(name = "userController")
@SessionScoped
public class UserController extends Globals implements Serializable  {

 
    private User current=new User(),newCurrent,tempCurrent;
    private DataModel items = null;
    @EJB
    private jsfcms.bean.UserFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    public int var,ind,userIntered=2;
    public String cmsidForForeign="";
    public int varForLuceneError,registered=0;
    public UserController() {
    }

    public int getRegistered() {
        int temp=registered;
        registered=0;
        return temp;
    }

    public void setRegistered(int registered) {
        this.registered = registered;
    }

    
    public int getVarForLuceneError() {
        return varForLuceneError;
    }

    public void setVarForLuceneError(int varForLuceneError) {
        this.varForLuceneError = varForLuceneError;
    }
    
    
    
    public void findUserByName(String name,String username)
    {
        System.out.println("name is " + name);
        System.out.println("username is " + username);
        int cmsId1=getFacade().findUser(username).get(0).getCmsID().getId();
        setCmsId(cmsId1);
        
    }

    public String getCmsidForForeign() {
        return cmsidForForeign;
    }

    public void setCmsidForForeign(String cmsidForForeign) {
        this.cmsidForForeign = cmsidForForeign;
    }
    
    
    public void setCmsIdInGlobalVariable(int ci) {
        if (varForLuceneError == 1) {
            System.out.println("this is in thmeplate ui " + ci);
            System.out.println("this is in thmeplate ui " + current.getId());

            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String cmsid = req.getParameter("cmsId");


            System.out.println("cmsiiiid is " + cmsid);
            if (cmsid != null && !cmsid.equals("")) {
                cmsidForForeign = cmsid;
                setCmsId(Integer.parseInt(cmsid));
                if (getFacade().findRange().isEmpty()) {
                    setCmsId(1);
                }
            } else//forexample when a button action, load page without any parameter
            {
                System.out.println("ci " + ci);

                if (ci != 0) {
                    System.out.println("ci " + ci);
                    setCmsId(current.getCmsID().getId());
                } else if (ci == 0 && !cmsidForForeign.equals("")) {
                    setCmsId(Integer.parseInt(cmsidForForeign));
                } else setCmsId(1);
            }
        }

    }

    public void setCmsIdInGlobalVariableInAdminThemplate(int ci) {
        System.out.println("this is in thmeplate admin " + ci);
        System.out.println("this is in thmeplate admin " + current.getId());
            System.out.println("ci " + ci);
            
            if (ci != 0)
            {
                System.out.println("ci " + ci);
                setCmsId(current.getCmsID().getId());
            }
            
        }
            
        
    
    
    public void getCmsControllerAndFindOnItByAnIdToUseForCreate (CmsController c,int i)
    {
        prepareCreate2();
        tempCurrent.setCmsID(c.find(i));
    }
    
    public void FindUser()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String temp = req.getParameter("uid");
        tempCurrent = getFacade().find(Integer.parseInt(temp));
    }
    
    public String removeUser(int index) {
       getFacade().remove(getFacade().find(index));
       recreateModel();//برای اینکه آیتمز نول شود و لیست که نمایش داده می شود لیستی باشد که مورد حذف شده را نداشته باشد
       return "User_list";
    }
    
    public void setEnableOnOff(int i, Boolean b) 
    {
        tempCurrent = getFacade().find(i);
        tempCurrent.setEnabled(b);
        update2();
    }

    public int getVar() {//this function is for the counting of pagination in category list
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }
    
    public int getVar1() {//this function is for that ajax that can update the list
        pagination=null;
        items=null;
        return var;
    }
    
 /*   public String forgot(int number) {
        searchByusername(current.getEmail());
        String username = current.getName() + "(" + current.getUsername() + ")";
        String password = current.getPassword();

        String matn = "";
        emailing sender;
        String subject = "بازیابی کلمه عبور و نام کاربری در سامانه sodasell.com";
        sender = new emailing(matn, subject, "text/html", current.getUsername());
        sender.start();
        return "thanks";
    }
*/
    public void activation() throws IOException{
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String userId = req.getParameter("userId");
        String shash = req.getParameter("hash");
        searchByusername1(Integer.parseInt(userId));
        if (tempCurrent.getHash().equals(shash)) {
            tempCurrent.setEnabled(true);
            update2();
        }
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect("http://jsfcms.com/faces/admin/Index.xhtml");


    }
    
    
  
    public void setInd() { // baraye inke age ettelaate ghalat vard kard va create anjam nashod, ind 0 shavad, va lla 1 shavad
        System.out.println("ind = " + ind);
        ind = 0;
    }
    
    public User find(int i) {
        //System.out.println("my i is " + i);
        return getFacade().find(i);
    }    

    
    public int getUserIntered ()
    {
        return userIntered;
    }
    
    public int findUserRegistered(String u,String p)
    {
        if (getFacade().count_user(u,p) == 1) {
            current=getFacade().findRange_user(u,p).get(0);
        }
        else current=null;
        System.out.println("i am here");
        return (getFacade().count_user(u,p)); // یعنی رکوردهایی را که که دو ستون آنها برابر با یو و پی است تعدادشان را بیاور.
    }

    public User getSelected() {
        if (current == null){
            current = new User();
            selectedItemIndex = -1;
        }
        System.out.println("#########");
        return current;
    }
    public User getSelected1() {
        if (tempCurrent == null){
            tempCurrent = new User();
            selectedItemIndex = -1;
        }
        System.out.println("#########");
        return tempCurrent;
    }
    public void currentNull() {
            current = null;
    }
//////////////////////////////// for logout////////////////////////////////////    
    public User getSelected2() {
        return current;
    }

    public void searchByusername() {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            System.out.println("###############!!!!!!" + username);
            if (username.equals("anonymousUser")) {
                current = null;
            } else {

                if (!getFacade().findUser(username).isEmpty())
                current = getFacade().findUser(username).get(0);
                //current = getFacade().find(username);
                
            }
        } catch (NullPointerException w) {
            current = null;
        } catch (NoResultException ne) {
            current = null;
        }

    }    
    public void searchByusername(int m) {
                current = getFacade().find(m);
    }    
    public void searchByusername1(int m) {
                tempCurrent = getFacade().find(m);
    }    
//////////////////////////////////////////////////////////////////////////////    
    public User getSelectedNew() {
        if (newCurrent == null) {
        newCurrent = getFacade().find(1);
        }
        System.out.println("name of user is " + newCurrent.getName());
        return newCurrent;
    }

    private UserFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(var) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();

        System.out.println("http://embassysystem.com/faces/ui/Activation.xhtml?userId=" + current.getId() + "&hash=" + current.getHash() + current.getEmail());
        emailing mnm = new emailing("<a href='http://jsfcms.com/faces/ui/Activation.xhtml?userId=" + current.getId() + "&hash=" + current.getHash()+"'>"+"http://jsfcms.com/faces/ui/Activation.xhtml?userId=" + current.getId() + "&hash=" + current.getHash() +"</a>", "فعال سازی نام کاربری", "text/html",  current.getEmail());
        
        mnm.start();
        
        ind=1;
        System.out.println("ind is " + ind);
        return "Welcome.xhtml";
    }
    public String prepareListForNewUserBuyCMS() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        recreateModel();

        System.out.println("http://jsfcms.com/faces/ui/Activation.xhtml?userId=" + current.getId() + "&hash=" + current.getHash() + current.getEmail());
        emailing mnm = new emailing("<a href='http://jsfcms.com/faces/ui/Activation.xhtml?userId=" + current.getId() + "&hash=" + current.getHash()+"'>"+"http://jsfcms.com/faces/ui/Activation.xhtml?userId=" + current.getId() + "&hash=" + current.getHash() +"</a>", "فعال سازی نام کاربری", "text/html",  current.getEmail());
        
        mnm.start();
        
        ind=1;
        System.out.println("ind is " + ind);
        registered=1;
        return "Register_CMS.xhtml";
    }

    public String prepareView() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new User();
        selectedItemIndex = -1;
        return "Create";
    }
    public void prepareCreate1() {
        current = new User();
        selectedItemIndex = -1;
    }
    public void prepareCreate2() {
        tempCurrent = new User();
        selectedItemIndex = -1;
    }
    
        UploadedFile up;

    public UploadedFile getUp() {
        return up;
    }

    public void setUp(UploadedFile up) {
        this.up = up;
    }
    public String test(){
        System.out.println("#################################"+up);
        System.out.println(FilenameUtils.getBaseName(up.getName()));
        return "";
//        System.out.println(up.getName());
    }
    public String create() {
        try {
            getFacade().create(current);
            prepareCreate();
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            return prepareList();
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String create2() {
        try {
            tempCurrent.setHash(new Random().nextInt(1000000) + "ac_tvip!@" + new Random().nextInt(20000));
            getFacade().create(tempCurrent);
            prepareCreate2();
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            return "User_list";
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String create1() {
        ind=0;
        System.out.println("123456789");
        try {
            current.setHash(new Random().nextInt(1000000) + "ac_tvip!@" + new Random().nextInt(20000));
            getFacade().create(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            System.out.println("123456789");


            return prepareList();
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            System.out.println("987654321s");
            ind=0;
            return null;
        }
    }
    public String createUserForNewCMS(CmsController cms,CategaryController cat,SettingsController set, SettingController set1,lucene l) {
        ind=0;
        //System.out.println("123456789");
        try {
            cms.createNewCMS();
            cat.createCategaryRoot(cms);//همان رکوردی با نام منوی اصلی که باید به ازای هر کاربر اضافه شود
            set.createStaticContext(cms);//برای هر سی ام اس، سه زبان برای متون ثابت، ثبت می کند
            set1.createNewSetting(cms);
            
            current.setCmsID(cms.returnCurrent());
            current.setHash(new Random().nextInt(1000000) + "ac_tvip!@" + new Random().nextInt(20000));
            
            getFacade().create(current);
            cms.currentToNull();
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            System.out.println("123456789");
            
            l.createDir(current.getCmsID().getId());//create a folder for each cms, to put indexes in it

            return prepareListForNewUserBuyCMS();
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            System.out.println("987654321s");
            ind=0;
            return null;
        }
    }

    public String prepareEdit() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(newCurrent);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
            System.out.println("sdfsdfsadfawrfawr23q");
             System.out.println("count" + findUserRegistered(newCurrent.getUsername(),newCurrent.getPassword()));
            if (findUserRegistered(newCurrent.getUsername(),newCurrent.getPassword()) == 1)
            {
                userIntered = 1;
                System.out.println("userIntered be"+userIntered);
                return "Index.xhtml";
            }
            else {
            userIntered = 0;
            System.out.println("userIntered was"+userIntered);
            return "Login.xhtml";
            }
        } catch (Exception e) {
            System.out.println("??????????????????");
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String update2() {
        try {
            getFacade().edit(tempCurrent);
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    public String update3() {
        try {
            getFacade().edit(tempCurrent);
            return "User_list";
        } catch (Exception e) {
            return null;
        }
    }
    public String update1() {
        
            getFacade().edit(newCurrent);
            System.out.println("sdfsdfsadfawrfawr23q");
            System.out.println("count" + findUserRegistered(newCurrent.getUsername(),newCurrent.getPassword()));
            if (findUserRegistered(newCurrent.getUsername(),newCurrent.getPassword()) == 2)
            {
                userIntered = 1;
                System.out.println("userIntered be"+userIntered);
                return "Index.xhtml";
            }
            else {
            userIntered = 0;
            System.out.println("userIntered was"+userIntered);
            return "Login.xhtml";
            }
        }

    public String destroy() {
        current = (User) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        //if (items == null) {
            items = getPagination().createPageDataModel();
        //}
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public User getUser(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = User.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getUser(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof User) {
                User o = (User) object;
                return getStringKey(o.getUsername());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + User.class.getName());
            }
        }
    }
}
