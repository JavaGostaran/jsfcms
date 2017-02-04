package jsfcms.jsf;

import java.io.IOException;
import jsfcms.dao.Categary;
import jsfcms.jsf.util.JsfUtil;
import jsfcms.jsf.util.PaginationHelper;
import jsfcms.bean.CategaryFacade;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "categaryController")
@SessionScoped
public class CategaryController implements Serializable {

    private Categary current;
    private DataModel items = null;
    private DataModel menuitems = null;
    private DataModel menuitems0 = null;
    private DataModel menuitems1 = null;
    private DataModel itemsCategory = null;
    private DataModel itemsInComboBox = null;
    private DataModel itemsInComboBox1 = null;
    private DataModel itemsInComboBox2 = null;
    private DataModel itemsInComboBox3 = null;
    private DataModel menuItemsInTop = null,menuItemsInSubTop=null,menuitemsNew=null,itemThatIsBlog,itemsCategoryNotBlogSubject=null;
    @EJB
    private jsfcms.bean.CategaryFacade ejbFacade;
    private PaginationHelper pagination,pagination0,paginationInComboBox,paginationInComboBox1,paginationInComboBox2,paginationInComboBox3,paginationCategory,paginationInTop,paginationInSubTop,paginationNew,paginationItemThatIsBlog,paginationItemsCategoryNotBlogSubject;
    private PaginationHelper pagination3;
    private int selectedItemIndex,var=10;
    public String cid,id;
    public int subTop,ttemp=0;
    public Boolean bo=false;
    String cmsid="1";
    public CategaryController() {
    }

    public void destroyAll()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getParameter("cmsId")!= null && req.getParameter("pid") == null && req.getParameter("pid1") == null) {
            current=null;
            items = null;
            menuitems = null;
            menuitems0 = null;
            itemsCategory = null;
            itemsInComboBox = null;
            itemsInComboBox1 = null;
            itemsInComboBox2 = null;
            itemsInComboBox3 = null;
            menuItemsInTop = null;
            menuItemsInSubTop = null;
            menuitemsNew = null;
            itemThatIsBlog = null;
            itemsCategoryNotBlogSubject = null;
            pagination = null;
            pagination0 = null;
            paginationInComboBox = null;
            paginationInComboBox1 = null;
            paginationInComboBox2 = null;
            paginationInComboBox3 = null;
            paginationCategory = null;
            paginationInTop = null;
            paginationInSubTop = null;
            paginationNew = null;
            paginationItemThatIsBlog = null;
            paginationItemsCategoryNotBlogSubject = null;
            pagination3=null;
            selectedItemIndex = 0;
            var = 10;

            ttemp = 0;
            bo = false;
        }
        cmsid = req.getParameter("cmsId");
    }
    
    public void createCategaryRoot (CmsController cms)
    {
        current=new Categary();
        current.setName("منوی اصلی");
        current.setVisibility(false);
        current.setInformation("root");
        current.setParentID(null);
        current.setCmsID(cms.returnCurrent());
        create();
       
    }
    
    public Boolean getBo() {
        return bo;
    }

    public void setBo() {
        this.bo = !this.bo;
    }
    
    public void setBoFalse() {
        this.bo = false;
    }

    public int getTtemp() {
        return ttemp;
    }

    public void setTtemp(int ttemp) {
        this.ttemp = ttemp;
    }
    
    
    
    public void initialValue()
    {
        if (ttemp == 0) {
            if (!getFacade().find_true("usual", "information").isEmpty()) {
                id = getFacade().find_true("usual", "information").get(0).getId().toString();
            } else {
                id = "0";
            }
            ttemp = 1;
        }
        System.out.println("ID IS = " + id);
    }
    public String initialValueAndGetIt()
    {
        if(!getFacade().find_true("usual" , "information").isEmpty())
            id= getFacade().find_true("usual" , "information").get(0).getId().toString();
        else id="0";
        return id;
    }
    
    public void getCmsControllerAndFindOnItByAnIdToUseForCreate (CmsController c,int i)
    {
        prepareCreate1();
        current.setCmsID(c.find(i));
    }
    
    public Categary getCategaryThatIsBlog()
    {
        return getFacade().findRange_InComboBox2().get(0);
    }
    public int getVar() {//this function is for the counting of pagination in category list
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }
    
    public int getVar1() {//this function is for that ajax that can update the list
        paginationCategory=null;
        itemsCategory=null;
        return var;
    }

        public void setVisibilityOnOff(int a,boolean b) {
        current = getFacade().find(a);
        current.setVisibility(b);
        updateCategory();
    }
        
    public List<Categary> forNavigate(UserController u)
    {
        List<Categary> a = new ArrayList<Categary>();
        //in shart baraye on ast ke vaghti yek karbar 100 ta daste ijad mikonad va kharej mishavad va shakhse digari mikhahad varede cms khod shavad, dastehaye nafare ghabli ke dar hafeze hanuz hast be nafare jadid neshan dade nashavad.
        //if (!id.equals("0") && (FindCategary1(Integer.parseInt(id)).getCmsID().equals(u.getSelected().getCmsID()) || u.getSelected() == null )) {
        if (!id.equals("0")) {
            

                Categary c = FindCategary1(Integer.parseInt(id));
                a.add(c);

                Categary d = c;
                System.out.println("AAADDD " + id);
                while (!d.getParentID().getInformation().equals("root")) {
                    d = FindCategary1(d.getParentID().getId());
                    a.add(d);
                }
            }
        
        return a;
        
    }
    public List<Categary> forNavigateInTagList(int i)
    {
        if (i!=0){
            
        
        List<Categary> a=new ArrayList<Categary>();
        Categary c=FindCategary1(i);
        a.add(c);
        
        Categary d=c;
        while (!d.getParentID().getInformation().equals("root"))
        {
        d=FindCategary1(d.getParentID().getId());
        a.add(d);
        }
        
        return a;
        }
        else
            return null;
        
    }
    
    public String removeCategory(int index) {
       getFacade().remove(getFacade().find(index));
       recreateModel();//برای اینکه آیتمز نول شود و لیست که نمایش داده می شود لیستی باشد که مورد حذف شده را نداشته باشد
       ttemp=0;
       return "Categary_list";
    }
    public void rr(ContentController cc,UserController u,int a) throws IOException{
    HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    String temp = req.getParameter("pid");
    String temp1 = req.getParameter("pid1");


        if (temp != null) {
            id = temp;
       /*     
            //a is userController.selected.id
            if (a == 0 && !u.cmsidForForeign.equals("") && (FindCategary1(Integer.parseInt(id)).getCmsID().getId() != Integer.parseInt(u.cmsidForForeign))) {
                //in vaghti ettefagh miofte ke ye bigane, hey oon cmsId toolbar ro aaz mikone va mikhad male hamaro bebine
                //vaghti in karo mikard va cmsId ro avaz mikard, moshkeli ke pish miyumad in bud ke chon pid ro avaz nemikard
                //bkhatere hamin cms ba pidi ferestade mishod ke vojud nadasht
                //pas ba i ghet'e code, b barname migim ke har vaght hamchin moshkeli pish oomad, id ferestade shode ro avaz kon
                //va id ro barabare zir gharar bede.
                System.out.println("he he id is " + id );
                id = getFacade().find_true("usual", "information").get(0).getId().toString();
                System.out.println("he he id is " + id );
            }
         */   
            
            System.out.println("||||||||| id is " + id);
            current = getFacade().find(Integer.parseInt(id));
        }
        if (temp1 != null) {
            
            current = cc.getContent((Integer.parseInt(temp1))).getCategaryID();
            id=current.getId().toString();
            System.out.println("dd is " + id);
        }

    }
    
    public void FindContent() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String temp = req.getParameter("cid");
        if (temp != null) {
            cid = temp;
        }
        current = getFacade().find(Integer.parseInt(cid));
        System.out.println("cid is " + cid);
    }
    
    public String FindCategary()
    {
        if (!id.equals("0"))
        {
            Categary c = getFacade().find(Integer.parseInt(id));
            return c.getName();
        }
        else return null;
    }
    public Categary FindCategary1(int input)
    {
        return getFacade().find(input);
    }
    public Categary FindCategaryRoot()
    {
        return getFacade().find_Root().get(0);
    }
    
    public Categary FindCategaryNews(int d)
    {
        Categary c = getFacade().find(d);//پیدا کردن کتگوری ای که آی دی آن دی (در واقع اسم آن اخبار یا گالری) است
        return c;
    }

    public Categary getSelected() {
        if (current == null) {
            current = new Categary();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CategaryFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    System.out.println("getFacade().count() = " + getFacade().count());
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }
    public PaginationHelper getPaginationInTop() {
        if (paginationInTop == null) {
            System.out.println("getFacade().count_true " + getFacade().count_true("root","parentID","information"));
            paginationInTop = new PaginationHelper(getFacade().count_true("root","parentID","information")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true("root","parentID","information");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_true(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()},"root","parentID","information"));
                }
            };
        }
        return paginationInTop;
    }
    public PaginationHelper getPaginationInSubTop() {
        if (paginationInSubTop == null) {
            paginationInSubTop = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true(subTop,"parentID","id");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_true(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()},subTop,"parentID","id"));
                }
            };
        }
        return paginationInSubTop;
    }
    public PaginationHelper getPaginationCategory() {
        if (paginationCategory == null) {
            paginationCategory = new PaginationHelper(var) {
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
        return paginationCategory;
    }
    public PaginationHelper getPaginationItemsCategoryNotBlogSubject() {
        //if (paginationItemsCategoryNotBlogSubject == null) {
            paginationItemsCategoryNotBlogSubject = new PaginationHelper(var) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_InCategaryList();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_InCategaryList(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        //}
        return paginationItemsCategoryNotBlogSubject;
    }
    public PaginationHelper getPaginationInComboBox() {
        //if (paginationInComboBox == null) {
            paginationInComboBox = new PaginationHelper(getFacade().count_InComboBox()) { // for listing all of the categary in the combox that is not blog and sub blog
                @Override
                public int getItemsCount() {
                    return getFacade().count_InComboBox();
                }

                @Override
                public DataModel createPageDataModel() {
                    System.out.println("getFacade().count() = " + getFacade().count());
                    return new ListDataModel(getFacade().findRange_InComboBox(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        //}
        return paginationInComboBox;
    }
    public PaginationHelper getPaginationInComboBox1() {
        //if (paginationInComboBox1 == null) {
            paginationInComboBox1 = new PaginationHelper(getFacade().count_InComboBox1()) { // for listing all of the categary in the combox that is not sub blog
                @Override
                public int getItemsCount() {
                    return getFacade().count_InComboBox1();
                }

                @Override
                public DataModel createPageDataModel() {
                    System.out.println("getFacade().count() = " + getFacade().count());
                    return new ListDataModel(getFacade().findRange_InComboBox1(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        //}
        return paginationInComboBox1;
    }
    public PaginationHelper getPaginationInComboBox2() {
        //if (paginationInComboBox2 == null) {
            paginationInComboBox2 = new PaginationHelper(getFacade().count_InComboBox2()) { // for listing all of the categary in the combox that is sub blog
                @Override
                public int getItemsCount() {
                    return getFacade().count_InComboBox2();
                }

                @Override
                public DataModel createPageDataModel() {
                    System.out.println("getFacade().count() = " + getFacade().count());
                    return new ListDataModel(getFacade().findRange_InComboBox2(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        //}
        return paginationInComboBox2;
    }
    public PaginationHelper getPaginationInComboBox3() {
        //if (paginationInComboBox2 == null) {
            paginationInComboBox3 = new PaginationHelper(getFacade().count_InComboBox3()) { // for listing all of the categary in the combox that is sub blog
                @Override
                public int getItemsCount() {
                    return getFacade().count_InComboBox3();
                }

                @Override
                public DataModel createPageDataModel() {
                    System.out.println("getFacade().count() = " + getFacade().count());
                    return new ListDataModel(getFacade().findRange_InComboBox3(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        //}
        return paginationInComboBox3;
    }
public PaginationHelper getPagination0() {
        if (pagination0 == null) {
            pagination0 = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count("root","parentID","information");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()},"root","parentID","information"));
                }
            };
        }
        return pagination0;
    }
public PaginationHelper getPagination2() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count(Integer.parseInt(id),"parentID","id");
                }

                @Override
                public DataModel createPageDataModel() {
                    System.out.println("Integer.parseInt(id) = " + Integer.parseInt(id));
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()},Integer.parseInt(id),"parentID","id"));
                }
            };
        }
        return pagination;
    }
public PaginationHelper getPagination3() {
        if (pagination3 == null) {
            pagination3 = new PaginationHelper(getFacade().count_true(Integer.parseInt(id),"parentID","id")) {

                @Override
                public int getItemsCount() {
                    return getFacade().count_true(Integer.parseInt(id),"parentID","id");
                }

                @Override
                public DataModel createPageDataModel() {
                    System.out.println("hihihihi3");
                    return new ListDataModel(getFacade().findRange_true(new int[]{getPageFirstItem(), getPageFirstItem()+getPageSize()},Integer.parseInt(id),"parentID","id"));
                }
            };
        }
        return pagination3;
    }
public PaginationHelper getPaginationNew() {
        //if (paginationNew == null) {
            paginationNew = new PaginationHelper(getFacade().count_true("blog", "parentID", "information")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true("blog", "parentID", "information");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_true(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "blog", "parentID", "information"));
                }
            };
        //}
        return paginationNew;
    }
public PaginationHelper getPaginationItemThatIsBlog() {
        //if (paginationNew == null) {
            paginationItemThatIsBlog = new PaginationHelper(getFacade().count_true("blog", "information")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true("blog", "information");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_true(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "blog" , "information"));
                }
            };
        //}
        return paginationItemThatIsBlog;
    }
    public String prepareList() {
        Categary ct=current;
        recreateModel();
        if (ct.getParentID().getInformation().equals("blog")) return "Blog_subjects_list";
        else return "Categary_list";
    }

    public String prepareView() {
        current = (Categary) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Categary();
        selectedItemIndex = -1;
        return "Create";
    }
    public void prepareCreate1() {
        current = new Categary();
        selectedItemIndex = -1;
    }

    public String create() {
        try {
            
            System.out.println("current.getId" + current.getId());
            System.out.println("current.getName" + current.getName());
            System.out.println("current.getParentID" + current.getParentID());
            System.out.println("current.getCmsID" + current.getCmsID());
            ttemp=0;
            getFacade().create(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategaryCreated"));
            return prepareList();
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Categary) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategaryUpdated"));
            return prepareList();
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public void updateCategory() {
            getFacade().edit(current);
            recreateModel();
    }

    public String destroy() {
        current = (Categary) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CategaryDeleted"));
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
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }
    public DataModel getMenuItemsInTop() {
            menuItemsInTop = getPaginationInTop().createPageDataModel();
        return menuItemsInTop;
    }
    public DataModel getMenuItemsInSubTop(int a) {
        subTop=a;
        menuItemsInSubTop = getPaginationInSubTop().createPageDataModel();
        return menuItemsInSubTop;
    }
    public DataModel getItemsCategory() {
        if (itemsCategory == null) {
            itemsCategory = getPaginationCategory().createPageDataModel();
        }
        return itemsCategory;
    }
    public DataModel getItemsCategoryNotBlogSubject() {
        //if (itemsCategoryNotBlogSubject == null) {
            itemsCategoryNotBlogSubject = getPaginationItemsCategoryNotBlogSubject().createPageDataModel();
        //}
        return itemsCategoryNotBlogSubject;
    }
    public DataModel getItemsInComboBox() { //for combobox in content news, text, adveretise ,... create
        //if (itemsInComboBox == null) {
            itemsInComboBox = getPaginationInComboBox().createPageDataModel();
        //}
        return itemsInComboBox;
    }
    public DataModel getItemsInComboBox1() { //for combobox in categary create
        //if (itemsInComboBox == null) {
            itemsInComboBox1 = getPaginationInComboBox1().createPageDataModel();
        //}
        return itemsInComboBox1;
    }
    public DataModel getItemsInComboBox2() { //for combobox in blog create
        //if (itemsInComboBox == null) {
            itemsInComboBox2 = getPaginationInComboBox2().createPageDataModel();
        //}
        return itemsInComboBox2;
    }
    public DataModel getItemsInComboBox3() { //for combobox in blog pages create
        //if (itemsInComboBox == null) {
            itemsInComboBox3 = getPaginationInComboBox3().createPageDataModel();
        //}
        return itemsInComboBox3;
    }
    public DataModel getMenuItems0() {
        if (menuitems0 == null) {
            menuitems0 = getPagination0().createPageDataModel();
        }
        return menuitems0;
    }
    public DataModel getMenuItems() {
        if (menuitems == null) {
            menuitems = getPagination2().createPageDataModel();
        }
        return menuitems;
    }
    public DataModel getMenuItems1() {
        System.out.println("hihihihi2");
         menuitems1 = getPagination3().createPageDataModel();
        return menuitems1;
    }
    public DataModel getMenuItemsNew() {
        //if (menuitems == null) {
        menuitemsNew = getPaginationNew().createPageDataModel();
        //}
        return menuitemsNew;
    }
    public DataModel getItemThatIsBlog() {
        //if (menuitems == null) {
        itemThatIsBlog = getPaginationItemThatIsBlog().createPageDataModel();
        //}
        return itemThatIsBlog;
    }
    private void recreateModel() {
        items = null;
        itemsCategory = null;
        current=null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPaginationCategory().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPaginationCategory().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Categary getCategary(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Categary.class)
    public static class CategaryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CategaryController controller = (CategaryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "categaryController");
            return controller.getCategary(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Categary) {
                Categary o = (Categary) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Categary.class.getName());
            }
        }
    }
}
