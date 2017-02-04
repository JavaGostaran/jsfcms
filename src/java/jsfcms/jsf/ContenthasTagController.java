package jsfcms.jsf;

import java.io.IOException;
import jsfcms.dao.ContenthasTag;
import jsfcms.jsf.util.JsfUtil;
import jsfcms.jsf.util.PaginationHelper;
import jsfcms.bean.ContenthasTagFacade;

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
import jsfcms.dao.ContenthasTagPK;

@ManagedBean(name = "contenthasTagController")
@SessionScoped
public class ContenthasTagController implements Serializable {

    private ContenthasTag current;
    private DataModel items = null,itemsList = null;
    @EJB
    private jsfcms.bean.ContenthasTagFacade ejbFacade;
    private PaginationHelper pagination,paginationList;
    private int selectedItemIndex;
    public int index,tempIndex;
    public List<ContenthasTag> list=new ArrayList<ContenthasTag>();

    public ContenthasTagController() {
    }
    
    public void getCmsControllerAndFindOnItByAnIdToUseForCreate (CmsController c,int i)
    {
        prepareCreate1();
        current.setCmsID(c.find(i));
    }
    
    public int getTempIndex() {
        System.out.println("tempIndex is " + tempIndex);
        return tempIndex;
    }
    
    public void setTempIndex(int tempIndex) {
        this.tempIndex = tempIndex;
    }

    public List<ContenthasTag> getList() {
        System.out.println("list size is " + list.size() );
        return list;
    }

    public void setList(List<ContenthasTag> list) {
        this.list = list;
    }
    
    public void rr() throws IOException{
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String temp = req.getParameter("tid");
        System.out.println("temp and tid are " + temp);
        if (temp != null) {
            list = getFacade().findRange1(Integer.parseInt(temp), "tag", "id");
        }
        else{
            list.clear();
        }
            
    }

    public void removeContenthasTag(int content,int tag) {
        if (!getFacade().findRange(content, "content", "id", tag, "tag", "id").isEmpty()) {
            getFacade().remove(getFacade().findRange(content, "content", "id", tag, "tag", "id").get(0));
        }
        
    }
    public ContenthasTag getContenthasTag(int i) {
        return getFacade().find(i);
    }
    public ContenthasTag getContenthasTagCurrent() {
        return current;
    }
    
    public ContenthasTag getSelected() {
        if (current == null) {
            current = new ContenthasTag();
            current.setContenthasTagPK(new jsfcms.dao.ContenthasTagPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private ContenthasTagFacade getFacade() {
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
    public PaginationHelper getPaginationList() {
        //if (paginationList == null) {
            paginationList = new PaginationHelper(getFacade().count(index,"content","id")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count(index,"content","id");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()},index,"content","id"));
                }
            };
        //}
        return paginationList;
    }
   

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (ContenthasTag) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ContenthasTag();
        current.setContenthasTagPK(new jsfcms.dao.ContenthasTagPK());
        selectedItemIndex = -1;
        return "Create";
    }
    public void prepareCreate1() {
        current = new ContenthasTag();
        current.setContenthasTagPK(new jsfcms.dao.ContenthasTagPK());
    }
    public void prepareCreateNew() {
        current = new ContenthasTag();
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContenthasTagCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public void create1(int contentId,int tagId,CmsController cms,int cmsId) {
        try {
            prepareCreateNew();
            current.setContenthasTagPK(new ContenthasTagPK(contentId, tagId));
            current.setCmsID(cms.find(cmsId));
            getFacade().create(current);
            
        } catch (Exception e) {
        }
    }

    public String prepareEdit() {
        current = (ContenthasTag) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContenthasTagUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
  

    public String destroy() {
        current = (ContenthasTag) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContenthasTagDeleted"));
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
    public DataModel getItemsList(int a) {
        index=a;
        //if (itemsList == null) {
            itemsList = getPaginationList().createPageDataModel();
        //}
        return itemsList;
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

    @FacesConverter(forClass = ContenthasTag.class)
    public static class ContenthasTagControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContenthasTagController controller = (ContenthasTagController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contenthasTagController");
            return controller.ejbFacade.find(getKey(value));
        }

        jsfcms.dao.ContenthasTagPK getKey(String value) {
            jsfcms.dao.ContenthasTagPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new jsfcms.dao.ContenthasTagPK();
            key.setContentID(Integer.parseInt(values[0]));
            key.setTagID(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(jsfcms.dao.ContenthasTagPK value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value.getContentID());
            sb.append(SEPARATOR);
            sb.append(value.getTagID());
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ContenthasTag) {
                ContenthasTag o = (ContenthasTag) object;
                return getStringKey(o.getContenthasTagPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ContenthasTag.class.getName());
            }
        }
    }
}
