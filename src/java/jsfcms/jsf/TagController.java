package jsfcms.jsf;

import jsfcms.dao.Tag;
import jsfcms.jsf.util.JsfUtil;
import jsfcms.jsf.util.PaginationHelper;
import jsfcms.bean.TagFacade;

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

@ManagedBean(name = "tagController") 
@SessionScoped
public class TagController implements Serializable {

    private Tag current;
    private DataModel items = null,itemsInCombobox = null;
    @EJB
    private jsfcms.bean.TagFacade ejbFacade;
    private PaginationHelper pagination,paginationInCombobox;
    private int selectedItemIndex;
    public String tagListObj1="",tagListObj2="";
     List <String> tagList= new ArrayList<String>();

    public TagController() {
    }
    
    public void getCmsControllerAndFindOnItByAnIdToUseForCreate (CmsController c,int i)
    {
        prepareCreate1();
        current.setCmsID(c.find(i));
    }
    
    public void clearTag()
    {
        tagListObj1="";
        tagListObj2="";
        tagList.clear();
    }

    public String getTagListObj1() { 
        return tagListObj1;
    }

    public void setTagListObj1(String tagListObj1) {
        this.tagListObj1 = tagListObj1;
    }

    

    public String getTagListObj2() {
        return tagListObj2;
    }

    public void setTagListObj2(String tagListObj2) {
        this.tagListObj2 = tagListObj2;
    }

    
    public void setTagListObjToTagList()
    {
        System.out.println("tagListObj1 is " + tagListObj1);
        System.out.println("tagListObj2 is " + tagListObj2);
            if (tagListObj2.equals("") || tagListObj2==null) {
                tagList.add(tagListObj1);

            }
            if (!tagListObj2.equals("") && tagListObj2!=null) {
                tagList.add(tagListObj2);
            }
        
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
    
    public Tag getTag(int i)
    {
        return getFacade().find(i);
    }
    public void removeTag(int i)
    {
        getFacade().remove(getFacade().find(i));
    }
    
    public Tag getSelected() {
        if (current == null) {
            current = new Tag();
            selectedItemIndex = -1;
        }
        return current;
    }
  

    private TagFacade getFacade() {
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
    public PaginationHelper getPaginationInComboBox() {
        //if (paginationInCombobox == null) {
            paginationInCombobox = new PaginationHelper(getFacade().count()) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        //}
        return paginationInCombobox;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Tag) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Tag();
        selectedItemIndex = -1;
        return "Create";
    }

    public void prepareCreate1() {
        current = new Tag();
        selectedItemIndex = -1;
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TagCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public void createAll(ContenthasTagController ctc,int contentId,CmsController cms,int cmsId) {
        System.out.println("BVCXZ 0.5");
        try { 
            System.out.println("BVCXZ 1");
            for (int i = 0; i < tagList.size(); i++) {  
                System.out.println("BVCXZ 2");
                if (getFacade().findRangeItemsInTagTable(tagList.get(i)).isEmpty()) {
                    current = new Tag();
                    current.setTagName(tagList.get(i));
                    current.setCmsID(cms.find(cmsId));
                    getFacade().create(current);
                }
                if (getFacade().findRangeItemsInTagTable(tagList.get(i)).isEmpty()) 
                    ctc.create1(contentId, current.getId(),cms,cmsId);
                else
                    ctc.create1(contentId, getFacade().findRangeItemsInTagTable(tagList.get(i)).get(0).getId(),cms,cmsId);
            }
            System.out.println("BVCXZ 3");
            tagList.clear();
            tagListObj1=null;
            tagListObj2=null;
        } catch (Exception e) {
            System.out.println("BVCXZ 4");
        }
    }

    public String prepareEdit() {
        current = (Tag) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TagUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Tag) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TagDeleted"));
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
    public DataModel getItemsInComboBox() {
        //if (itemsInCombobox == null) {
            itemsInCombobox = getPaginationInComboBox().createPageDataModel();
        //}
        return itemsInCombobox;
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

    @FacesConverter(forClass = Tag.class)
    public static class TagControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TagController controller = (TagController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tagController");
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
            if (object instanceof Tag) {
                Tag o = (Tag) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Tag.class.getName());
            }
        }
    }
}
