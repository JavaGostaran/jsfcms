package jsfcms.jsf;

import java.io.IOException;
import jsfcms.dao.Compound;
import jsfcms.jsf.util.JsfUtil;
import jsfcms.jsf.util.PaginationHelper;
import jsfcms.bean.CompoundFacade;

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

@ManagedBean(name = "compoundController")
@SessionScoped
public class CompoundController implements Serializable {

    private Compound current,current1;
    private DataModel items = null,items1 = null,items2=null;
    @EJB
    private jsfcms.bean.CompoundFacade ejbFacade;
    private PaginationHelper pagination,pagination1,pagination2;
    private int selectedItemIndex;
    public int random,rank,idInIdc,base,idcOfCompound;
    public lucene luceneObj = new lucene();

    public CompoundController() {
    }
    
    public void getCmsControllerAndFindOnItByAnIdToUseForCreate (CmsController c,int i)
    {
        prepareCreate1();
        current.setCmsID(c.find(i));
    }
    
 
    public void addNewCompoundAfterCompoundListInContentEdit(ContentController c,int ind) throws IOException
    {
        base=1;
        c.getContentAndSetInCurrent(ind);
            ExternalContext ec1 = FacesContext.getCurrentInstance().getExternalContext();
            ec1.redirect("http://jsfcms.com/faces/admin/Compound_NN.xhtml");
    }
    
    public void setRankAgainFromOne(int idc)
    {
        for (int i=0;i < getFacade().count(idc, "idc");i++)
        {
            System.out.println("first rank is " + getFacade().findRange_asc(idc, "idc").get(i).getRankContent());

            Compound c= getFacade().findRange_asc(idc, "idc").get(i);
            c.setRankContent(i+1);
            

            updateCompoundAfterSetRankAgainFromOne(c);
            
            System.out.println("second rank is " + getFacade().findRange_asc(idc, "idc").get(i).getRankContent());
        }
        
    }
    public int getCountCompound(int i)
    {
        return (getFacade().count(i,"idc"));
    }
    public Compound getCompound1(int i)
    {
        return ((getFacade().findRange_In_compoundController(i,"contentID","id")).get(0));
    }
    
    public String returnCompound_N ()
    {
        return "Compound_N";
    }
    public void setIdInIdc (int b)
    {
        idInIdc=b;
    }
    public int getIdInIdc ()
    {
        return idInIdc;
    }
    
    public Compound findCompound (int a)
    {
        return current=getFacade().find(a);
    }
    
    public void setRankValueUpForList(int i,int r) {
        
        current= getFacade().findCompound(i,r).get(0);
        current1= getFacade().findCompound(i,r-1).get(0);
        current.setRankContent(r-1);
        current1.setRankContent(r);
        updateCompound();

        System.out.println("setRankValueUpForList " + current.getIdc());
        
    }
    public void setRankValueDownForList(int i,int r) {
        current= getFacade().findCompound(i,r).get(0);
        current1= getFacade().findCompound(i,r+1).get(0);
        current.setRankContent(r+1);
        current1.setRankContent(r);
        updateCompound();
        System.out.println("setRankValueDownForList " + current.getIdc());
    }
    public void setBase(int a) {
        base=a;
    }
    public int getBase() {
        System.out.println("getBase is " + base);
        return base;
    }
    public void setRankValue() {
        rank=1;
    }
    public int getRankValue() {
        return rank;
    }
    public void setRankValueForWhenAddNewCompoundAfterBuildedCompoundListInPast(int a) {
        base=2;
        rank=getFacade().count(a, "idc") + 1;//باید ببینم قبلا چندتا کامپوند وارد شده، سپس رنک رو از بعد از رنک آخرین کامپوند وارد شده در قبل، قرار دهیم
    }
    public int getRankValueForWhenAddNewCompoundAfterBuildedCompoundListInPast() {
        return rank;
    }
    public void setRankValueUp() {
        rank=rank+1;
    }
    public void setRandomValue(int a) {
        random=a;
    }
    public int getRandomValue() {
        return random;
    }

    
    
    public Compound getSelected() {
        if (current == null) {
            current = new Compound();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CompoundFacade getFacade() {
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
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }
    public PaginationHelper getPagination1() {
        //if (pagination1 == null) {
            pagination1 = new PaginationHelper(getFacade().count()) {
                @Override
                public int getItemsCount() {
                    return getFacade().count(random, "idc");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_asc(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()},random, "idc"));
                }
            };
        //}
        return pagination1;
    }
    public PaginationHelper getPagination2() {
        //if (pagination1 == null) {
            pagination2 = new PaginationHelper(getFacade().count()) {
                @Override
                public int getItemsCount() {
                    return getFacade().count(idcOfCompound, "idc");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_asc(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()},idcOfCompound, "idc"));
                }
            };
        //}
        return pagination2;
    }

    public String prepareList() {
        recreateModel();
        return null;
    }

    public String prepareView() {
        current = (Compound) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Compound();
        selectedItemIndex = -1;
        return "Create";
    }
    public void prepareCreate1() {
        current = new Compound();
        selectedItemIndex = -1;
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage("llllllllllllll"+ResourceBundle.getBundle("/Bundle").getString("CompoundCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String create1(ContentController c) {
        try {
            getFacade().create(current);
            
             
            luceneObj.main(current.getIdc(),c.getContent1(current.getIdc()).getHeader());
            
            JsfUtil.addSuccessMessage("llllllllllllll"+ResourceBundle.getBundle("/Bundle").getString("CompoundCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Compound) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CompoundUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public void updateCompound() {
        try {
            getFacade().edit(current);
            getFacade().edit(current1);
        } catch (Exception e) {
        }
    }
    public void updateCompoundAfterSetRankAgainFromOne(Compound c) {
        try {
            getFacade().edit(c);
        } catch (Exception e) {
        }
    }

    public String destroy() {
        current = (Compound) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CompoundDeleted"));
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
    public DataModel getItems1() {
            items1 = getPagination1().createPageDataModel();
        return items1;
    }
    public DataModel getItems2(int i) {
        idcOfCompound=i;
        items2 = getPagination2().createPageDataModel();
        return items2;
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

    @FacesConverter(forClass = Compound.class)
    public static class CompoundControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompoundController controller = (CompoundController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "compoundController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Compound) {
                Compound o = (Compound) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Compound.class.getName());
            }
        }
    }
}
