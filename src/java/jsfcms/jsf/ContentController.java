package jsfcms.jsf;

import com.ghasemkiani.util.icu.PersianCalendar;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.util.ULocale;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jsfcms.dao.Content;
import jsfcms.jsf.util.JsfUtil;
import jsfcms.jsf.util.PaginationHelper;
import jsfcms.bean.ContentFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
import javax.servlet.http.HttpServletRequest;
import jsfcms.dao.Categary;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.util.Version;
import org.apache.myfaces.custom.fileupload.UploadedFile;
 
@ManagedBean(name = "contentController")
@SessionScoped
public class ContentController extends lucene implements Serializable {
//public class ContentController implements Serializable {

    private Content current=null;
    private DataModel items = null,itemsForContentList=null,itemsForContentHaveComment=null,itemsForContentList1=null,itemsInIndexPageInBlog=null,allContentInBlogMenuChooseByName=null;
    private DataModel menuitems = null,allContentInBlogMenu = null;
    private DataModel itemsComment = null; 
    @EJB
    private jsfcms.bean.ContentFacade ejbFacade;
    private PaginationHelper pagination, pagination2, pagination3, pagination4, pagination5, pagination6,paginationComment,paginationForContentList,paginationForContentHaveComment,paginationForContentList1,paginationInIndexPageInBlog,paginationAllContentInBlogMenu,paginationAllContentInBlogMenuChooseByName;
    private int selectedItemIndex;
    private int a = 0,var=10;
    public int b = 0;
    public int value = 1,it,ttemp=0;
    public String id, id1, cid,num,typeOfContentForCompound,searchWord,hrefValue;
    public Content contentSearch;
    public List<Content> ContentBlogChooseByDate=new ArrayList<Content>();
    public String cmsid="1";
    public lucene luceneObj = new lucene();
    
    public ContentController() {
        
    }
    
    public void destroyAll()
    {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        System.out.println("req.getParameter(\"cmsId\")" + req.getParameter("cmsId"));
        System.out.println("req.getParameter(\"pid\")" + req.getParameter("pid"));
        System.out.println("req.getParameter(\"pid1\")" + req.getParameter("pid1"));
        if (req.getParameter("cmsId") != null && req.getParameter("pid") == null && req.getParameter("pid1") == null) {
            current = null;
            items = null;
            itemsForContentList = null;
            itemsForContentHaveComment = null;
            itemsForContentList1 = null;
            itemsInIndexPageInBlog = null;
            allContentInBlogMenuChooseByName = null;
            menuitems = null;
            allContentInBlogMenu = null;
            itemsComment = null;
            pagination = null;
            pagination2 = null;
            pagination3 = null;
            pagination4 = null;
            pagination5 = null;
            pagination6 = null;
            paginationComment = null;
            paginationForContentList = null;
            paginationForContentHaveComment = null;
            paginationForContentList1 = null;
            paginationInIndexPageInBlog = null;
            paginationAllContentInBlogMenu = null;
            paginationAllContentInBlogMenuChooseByName = null;
            selectedItemIndex = 0;
            a = 0;
            var = 10;
            b = 0;
            value = 1;
            ttemp = 0;
            ContentBlogChooseByDate = new ArrayList<Content>();
        }
        cmsid = req.getParameter("cmsId");
    }
    
    public void initialValue(CategaryController c)
    {
        if (ttemp==0)
        {
            id=c.initialValueAndGetIt();
            ttemp=1;
        }
        
    }

    public int getTtemp() {
        return ttemp;
    }

    public void setTtemp(int ttemp) {
        this.ttemp = ttemp;
    }
    
    
    public void getCmsControllerAndFindOnItByAnIdToUseForCreate (CmsController c,int i)
    {
        //prepareCreate1();
        current.setCmsID(c.find(i));
    }
    
//---------------------start search---------------------------

    public String getSearchWord() {
        return searchWord; 
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }
    public List<Document> searching() throws Exception {
        if (searchWord != null && !searchWord.equals("") ){
            System.out.println("searchWord is " + searchWord);
             
            File indexDir = new File("/root/glass/glass3/glassfish/domains/domain1/applications/jsfcms/indexed/" + CMSID);
            if (indexDir.list().length > 0) {
                System.out.println("hello every one");
            StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
            return luceneObj.search1(indexDir, analyzer, searchWord);
            }
            else {
                System.out.println("bye every one");
                return null;
            }
        }
        else return null;
    }
    

    public String getHrefValue() {
        return hrefValue;
    }

    public void setHrefValue(String hrefValue) {
        this.hrefValue = hrefValue;
    }

    public Content getContentSearch() {
        return contentSearch;
    }

    public void setContentSearch(String a) {
        int b=Integer.parseInt(a);
        this.contentSearch = ejbFacade.find(b);
    }
    
    
   
//---------------------end search---------------------------

    
//---------------------start prsian date---------------------------
    ULocale  uLocale = new ULocale("fa_IR");
    TimeZone timeZone = TimeZone.getTimeZone(TimeZone.getDefault().getID());
    PersianCalendar calendar = new PersianCalendar( uLocale);


public String persiandate(Date d){
        if(d == null)
            return ".";
        calendar.setTime( d);
        DateFormat sds =(DateFormat)calendar.getDateTimeFormat(DateFormat.MEDIUM,DateFormat.NONE,uLocale);
        //date=sds.format(calendar.getTime());
        return sds.format(calendar.getTime());
    }
public String persiandate1(Date d){
        PersianCalendar calendar1 = new PersianCalendar( uLocale);
        calendar1.setTime( d);
        if (calendar1.get(2) == 0) return "فروردین";
        else if (calendar1.get(2) == 1) return "اردیبهشت";
        else if (calendar1.get(2) == 2) return "خرداد";
        else if (calendar1.get(2) == 3) return "تیر";
        else if (calendar1.get(2) == 4) return "مرداد";
        else if (calendar1.get(2) == 5) return "شهریور";
        else if (calendar1.get(2) == 6) return "مهر";
        else if (calendar1.get(2) == 7) return "آبان";
        else if (calendar1.get(2) == 8) return "آذر";
        else if (calendar1.get(2) == 9) return "دی";
        else if (calendar1.get(2) == 10) return "بهمن";
        else if (calendar1.get(2) == 11) return "اسفند";
        else return "نامشخص";
        
    }
    public int persiandate2(Date d){
        PersianCalendar calendar2 = new PersianCalendar( uLocale);
        calendar2.setTime( d);
        return calendar2.get(1);
        
    }
    
    public List<Content> getDateOfAllPosts()
    {
        List <Content> dp=new ArrayList<Content>();
        List <Content> dp1=new ArrayList<Content>();
        dp=getFacade().findRange_true("بلاگ", "typePageID", "type");
        try{
        dp1.add(dp.get(0));
        for (int i=1;i<dp.size();i++)
        {
            int k=0;
            for(int j=0;j<dp1.size();j++)
            {
                if ((dp.get(i).getDate().getYear() == dp1.get(j).getDate().getYear()) && (dp.get(i).getDate().getMonth() == dp1.get(j).getDate().getMonth()) ) k=1;
            }
            if (k==0) dp1.add(dp.get(i));
        }
        System.out.println("dp is " + dp);
        System.out.println("dp1 is " + dp1);
        return dp1;
        }
        catch (Exception e){
            return null;
            
            
        }
    }
    
    public void getDateAndSetNewDate() 
    {
        
        
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String month = req.getParameter("month");
        String year = req.getParameter("year");
        System.out.println("current Date is " + month);
        if (month != null && year != null) {
            Date d1=new Date();
            Date d2=new Date();
            d1.setHours(1);
            d1.setMinutes(1);
            d1.setSeconds(1);
            d1.setDate(24);
            d1.setMonth(Integer.parseInt(month)-1);
            d1.setYear(Integer.parseInt(year));
            d2.setHours(1);
            d2.setMinutes(1);
            d2.setSeconds(1);
            d2.setDate(24);
            d2.setMonth(Integer.parseInt(month));
            d2.setYear(Integer.parseInt(year));
            System.out.println("new Date is " + d1);
            ContentBlogChooseByDate=getFacade().findRange_true_date("بلاگ", "typePageID", "type", d1, d2);
            System.out.println("ContentBlogChooseByDate is " + ContentBlogChooseByDate);
        }
                
    }

    public List<Content> getContentBlogChooseByDate() {
        return ContentBlogChooseByDate;
    }

    public void setContentBlogChooseByDate() {
        ContentBlogChooseByDate.clear();
    }
    
//---------------------end prsian date---------------------------
    public Content getCurrent()
    {
        return current;
    }

    public Content getContent1(int i) {
        Content con;
        con=getFacade().find(i);
        if (con.getVisibility() == true) return con;
        return null;
    }
    public int getVar() {//this function is for the counting of pagination in content list
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }
    
    public int getVar1() {//this function is for that ajax that can update the list
        paginationForContentList=null;
        itemsForContentList=null;
        paginationForContentList1=null;
        itemsForContentList1=null;
        return var;
    }

    
    public void setVisibilityOnOff(int a,boolean b) {
        current = getFacade().find(a);
        current.setVisibility(b);
        updateContent();
    }

    public String getTypeOfContentForCompound() {
        return typeOfContentForCompound;
    }

    public void setTypeOfContentForCompound() {
        this.typeOfContentForCompound = "";
    }

    public String removeContent(int index) throws Exception{
       Content c= getFacade().find(index);
       getFacade().remove(getFacade().find(index));
       
        
       luceneObj.mainRemove(index);
       
       recreateModel();//برای اینکه آیتمز نول شود و لیست که نمایش داده می شود لیستی باشد که مورد حذف شده را نداشته باشد
       if(c.getTypePageID().getType().equals("خبر")) return "News_list";
       else if(c.getTypePageID().getType().equals("متن")) return "Text_list";
       else if(c.getTypePageID().getType().equals("عکس")) return "Photo_list";
       else if(c.getTypePageID().getType().equals("تبلیغ")) return "Advertise_list";
       else if(c.getTypePageID().getType().equals("فایل")) return "File_list";
       else if(c.getTypePageID().getType().equals("ایستا")) return "Static_list";
       else if(c.getTypePageID().getType().equals("ترکیبی")) return "Compound_list";
       else if(c.getTypePageID().getType().equals("بلاگ")) return "Blog_list";
       else return "Content_list"; 
    }
    public String removeContentOfCompound(int index, int idc, CompoundController co) throws IOException,Exception {

        getFacade().remove(getFacade().find(index));
        
         
        luceneObj.main(idc,getContent1(idc).getHeader());

        co.setRankAgainFromOne(idc);

        recreateModel();//برای اینکه آیتمز نول شود و لیست که نمایش داده می شود لیستی باشد که مورد حذف شده را نداشته باشد


        ExternalContext ec1 = FacesContext.getCurrentInstance().getExternalContext();
        ec1.redirect("http://jsfcms.com/faces/admin/Content_edit.xhtml?cid=" + idc);
        return null;

    }
    public String removeContentFromContentList(int index) throws Exception{
       getFacade().remove(getFacade().find(index));
        
       luceneObj.mainRemove(index);
       recreateModel();//برای اینکه آیتمز نول شود و لیست که نمایش داده می شود لیستی باشد که مورد حذف شده را نداشته باشد
       return "Content_list"; 
    }
    public void setPaginationCommentNull()
    {
        paginationComment=null;
    }

    
    public void a() {
        
        System.out.println("the file name is " +  (current.getFile()));
    }

    public void rr() throws IOException {

        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String temp = req.getParameter("pid");
        String temp1 = req.getParameter("pid1");
        String temp2 = req.getParameter("pid2");
            if (temp != null) {
                id = temp; //for when the sended pid variable to paging.xhtml is null (when user click on the content)
            }

            id1 = temp1;

            System.out.println("pid is " + id);


            current = null;
            if (getFacade().count_true1(Integer.parseInt(id), "categaryID", "id", "متن", "typePageID", "type") != 0) {
                current = getFacade().findRange_true1(Integer.parseInt(id), "categaryID", "id", "متن", "typePageID", "type").get(0);//for default body in paging
                System.out.println("0-10101010101010101-1");
            }
            if (id1 != null) { //means user click on a content
                current = getFacade().find(Integer.parseInt(id1));
            }
            if ((temp != null || id == "1") && current != null) {// id==1 برای وقتیه که صفحه اصلی بدون هیچ متغیر ارسالی لود می شود
                id1 = (current.getId()).toString(); //if in paging, default content sets, so we exclude the default content id, in order to user can comment in default content
            }                                                             //if in paging, user content sets, so we exclude that content id (ofcourse in this case, this command is'nt suitable because id1 set in past)

            if (current != null ) {
                if (current.getIsStatic() == 1 && temp2 == null) {
                    System.out.println("header of the page is " + current.getHeader());
                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                    ec.redirect("http://jsfcms.com/faces/time/" + current.getNameStatic() + "?pid1=" + current.getId() + "&pid2=1");// چون در نویگیتور اسم صفحه های استاتیک نمی آمد و علتش هم بخاطر این بود که کارنت آپدیت نمیشد
                    // و علت آپدیت نشدن کارنت هم این بود که آی دی به صفحه ریدایرکت شده در همین خط کد لحاظ نشده بود. پس آن را لحاظ 
// پس آن را لحاظ کردیم. در ضمن شرط تمپ نامساوی نول هم اظافه کردیم که هنگام ریدایرک دوباره ریدایرکت نکند                    
                    
                }
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

 
    public Content getSelected() {
        if (current == null) {
            System.out.println("****&&&&&&&&&&************");
            current = new Content();
            selectedItemIndex = -1;
        }
        System.out.println("****&&&&&&&&&&*********%%%%%%%%%***");

        return current;
    }

    private ContentFacade getFacade() {
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
    public PaginationHelper getPaginationForContentHaveComment() {
        if (paginationForContentHaveComment == null) {
            paginationForContentHaveComment = new PaginationHelper(10) {
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
        return paginationForContentHaveComment;
    }
    public PaginationHelper getPaginationForContentList() {
        if (paginationForContentList == null) {
            paginationForContentList = new PaginationHelper(var) {
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
        return paginationForContentList;
    }
    public PaginationHelper getPaginationForContentList1() {
        if (paginationForContentList1 == null) {
            paginationForContentList1 = new PaginationHelper(var) {
                @Override
                //num=7 means that content is compound
                public int getItemsCount() {
                    if (num.equals("ترکیبی")) return getFacade().count(num,"typePageID","type");
                    else return getFacade().count_not_compound(num,"typePageID","type");
                }

                @Override
                public DataModel createPageDataModel() {
                    if (num.equals("ترکیبی")) return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()},num,"typePageID","type"));
                    else return new ListDataModel(getFacade().findRange_not_compound(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()},num,"typePageID","type"));
                }
            };
        }
        return paginationForContentList1;
    }
    public PaginationHelper getPaginationComment() {
        if (paginationComment == null) {
            paginationComment = new PaginationHelper(10) {
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
        return paginationComment;
    }

    public void seta(int a) {
        b = a;
        System.out.println(b + "%%%%%%%%%%%%%");
    }

    public PaginationHelper getPagination2() {

        if (pagination2 == null) {
            pagination2 = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count(Integer.parseInt(id), "categaryID", "id");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, Integer.parseInt(id), "categaryID", "id"));
                }
            };
        }
        return pagination2;
    }

    public PaginationHelper getPagination3() {
        if (pagination3 == null) {
            pagination3 = new PaginationHelper(getFacade().count_true(Integer.parseInt(id), "categaryID", "id", "typePageID", "type")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true(Integer.parseInt(id), "categaryID", "id", "typePageID", "type");
                }

                @Override
                public DataModel createPageDataModel() {
                    System.out.println("iiiidddd is " + id);
                    return new ListDataModel(getFacade().findRange_true(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, Integer.parseInt(id), "categaryID", "id", "typePageID", "type"));
                }
            };
        }
        return pagination3;
    }
    

    public PaginationHelper getPagination4() {
        if (pagination4 == null) {
            pagination4 = new PaginationHelper(getFacade().count_true_not_compound("تبلیغ","typePageID", "type")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true_not_compound("تبلیغ","typePageID", "type");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_true_not_compound(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "تبلیغ","typePageID", "type", Integer.parseInt(id), "categaryID", "id"));
                }
            };
        }
        return pagination4;
    }

    public PaginationHelper getPagination5() {
        if (pagination5 == null) {
            pagination5 = new PaginationHelper(getFacade().count_true_not_compound("خبر", "typePageID", "type", value, "typePageNewsID", "id")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true_not_compound("خبر", "typePageID", "type", value, "typePageNewsID", "id");
                }

                @Override
                public DataModel createPageDataModel() {
                    System.out.println("i am here " + value);
                    return new ListDataModel(getFacade().findRange_true_not_compound(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "خبر", "typePageID", "type" , value, "typePageNewsID", "id"));
                }
            };
        }
        return pagination5;
    }

    public PaginationHelper getPagination6() {
        if (pagination6 == null) {
            pagination6 = new PaginationHelper(getFacade().count_true_not_compound("عکس", "typePageID", "type")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true_not_compound("عکس", "typePageID", "type");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_true_not_compound(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "عکس", "typePageID", "type"));
                }
            };
        }
        return pagination6;
    }
    public PaginationHelper getPaginationAllContentInBlogMenu() {
        //if (paginationAllContentInBlogMenu == null) {
            paginationAllContentInBlogMenu = new PaginationHelper(getFacade().count_true("بلاگ", "typePageID", "type")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true("بلاگ", "typePageID", "type");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_true(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "بلاگ", "typePageID", "type"));
                }
            };
        //}
        return paginationAllContentInBlogMenu;
    }

    public PaginationHelper getPaginationInIndexPageInBlog(CategaryController c) {
        //if (paginationInIndexPageInBlog == null) {
        Categary instance=c.getCategary(it);
        if (instance.getInformation().equals("blog")) { //یعنی طرف روی بلاگ بالای صفحه کلیک کرده است
            paginationInIndexPageInBlog = new PaginationHelper(getFacade().count_true("بلاگ", "typePageID", "type")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true("بلاگ", "typePageID", "type");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_true(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, "بلاگ", "typePageID", "type"));
                }
            };
            //}
            return paginationInIndexPageInBlog;
        } else //یعنی طرف روی موضوعات موجود در بلاگ کلیک کرده است
        {
            paginationInIndexPageInBlog = new PaginationHelper(getFacade().count_true(it, "categaryID", "id")) {
                @Override
                public int getItemsCount() {
                    return getFacade().count_true(it, "categaryID", "id");
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange_true(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}, it, "categaryID", "id"));
                }
            };
            return paginationInIndexPageInBlog;
        }
    }

    public String prepareList() {
        recreateModel();
        System.out.println("MOMOMOMOMOMOMORORORORORORORO");
        
        current = new Content();//in order to the create page,news,... becomes empty after entering values
        selectedItemIndex = -1;

        return "Content_list";
    }
    public String prepareList1(CompoundController co) throws IOException{
        recreateModel();
        System.out.println("MOMOMOMOMOMOMORORORORORORORO");
        
        Content c = current;
        
        current = new Content();//in order to the create page,news,... becomes empty after entering values
        selectedItemIndex = -1;
        
        if(c.getTypePageID().getType().equals("خبر") && c.getIsCompound() == 0) return "News_list";
        else if(c.getTypePageID().getType().equals("متن") && c.getIsCompound() == 0) return "Text_list";
        else if(c.getTypePageID().getType().equals("عکس") && c.getIsCompound() == 0) return "Photo_list";
        else if(c.getTypePageID().getType().equals("تبلیغ") && c.getIsCompound() == 0) return "Advertise_list";
        else if(c.getTypePageID().getType().equals("فابل") && c.getIsCompound() == 0) return "File_list";
        else if(c.getTypePageID().getType().equals("ایستا") && c.getIsCompound() == 0) return "Static_list";
        else if(c.getTypePageID().getType().equals("ترکیبی") && c.getIsCompound() == 0) return "Compound_list";
        else if(c.getTypePageID().getType().equals("بلاگ") && c.getIsCompound() == 0) return "Blog_list";
        else if(c.getIsCompound() == 1)
        {
            ExternalContext ec1 = FacesContext.getCurrentInstance().getExternalContext();
            ec1.redirect("http://jsfcms.com/faces/admin/Content_edit.xhtml?cid=" + co.getCompound1(c.getId()).getIdc());
            return null;
        }
        else return "Content_list";         
        
        
    }
    public String prepareListForCompound() {
        recreateModel();
        current = new Content();//in order to the create page,news,... becomes empty after entering values
        selectedItemIndex = -1;
        typeOfContentForCompound = "";
        return "Compound_N";
    }
    public String prepareListForCompoundInCompound_NN() {
        recreateModel();
        current = new Content();//in order to the create page,news,... becomes empty after entering values
        selectedItemIndex = -1;
        typeOfContentForCompound = "";
        return "Compound_NN";
    }

    public String prepareView() throws IOException {
        current = (Content) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "paging";

    }

    public void prepareView2() throws IOException {
        b = 3;
        System.out.println("the variable b is" + b);
        current = (Content) getMenuItems().getRowData();
        System.out.println("the current is" + current.getBody());
        System.out.println("@@@@@@++++++++++@@@@@@@@@" + current.getBody());
        selectedItemIndex = pagination2.getPageFirstItem() + getMenuItems().getRowIndex();

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("http://jsfcms.com/faces/admin/paging.xhtml?faces-redirect=true");
    }

    public String prepareCreate() {
        current = new Content();
        selectedItemIndex = -1;
        return "Create";
    }
    public void prepareCreate1() {
        current = new Content();
    }

    public void create() {
        try {
            a = 1;
            getFacade().create(current);
             
            luceneObj.main(current.getId(),current.getHeader());
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContentCreated"));
            
            //return prepareList();
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            //return null;
        }
    }
    public String create1(CompoundController c,TagController tc, ContenthasTagController ctc,CmsController cms, int cmsId) throws Exception{
            System.out.println("c is " + c);
            System.out.println("tc is " + tc);
            System.out.println("ctc is " + ctc);
            
            System.out.println("BVCXZ");
            
            current.setCmsID(cms.find(cmsId));
            getFacade().create(current);
            
            System.out.println("BVCXZ 0");
            tc.createAll(ctc,current.getId(),cms,cmsId);
             
            luceneObj.main(current.getId(),current.getHeader());
            System.out.println("ZXCVB");
            c.setBase(2);
            c.setRandomValue(current.getId());
            recreateModel();
            prepareCreate1();
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContentCreated"));
            
            return "Compound_N";
       
    }
    public String createContentForCompoundForBasicPage() {
        try {
            typeOfContentForCompound = "";
            getFacade().create(current);
            recreateModel();
            return "Compound_N";
            
            
        } catch (Exception e) {
            return null;
        }
    }
    public void createContentForCompound() {
        try {
            System.out.println("I am here");
            typeOfContentForCompound = current.getTypePageID().getType();
            getFacade().create(current);
            recreateModel();
            System.out.println("I am her too " + typeOfContentForCompound);
            
            
        } catch (Exception e) {
            System.out.println("I am there");
            //return null;
        }
    }
    public void createContentForCompound1() {
        try {
            current=null;
            typeOfContentForCompound = current.getTypePageID().getType();
            getFacade().create(current);
            recreateModel();
            
            
            
        } catch (Exception e) {
            //return null;
        }
    }

    public String prepareEdit() {
        current = (Content) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            return prepareList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String update1(CompoundController co) {
        try {
            getFacade().edit(current);
            
             
            if (current.getCategaryID() != null && !current.getTypePageID().getType().equals("عکس") && !current.getTypePageID().getType().equals("خبر") && !current.getTypePageID().getType().equals("تبلیغ")) luceneObj.main(current.getId(),current.getHeader());//عکس و خبر و تبلیغ  رو در ایندکس گذاری قرار نمی دهیم
            else if (current.getCategaryID() == null && !current.getTypePageID().getType().equals("عکس") && !current.getTypePageID().getType().equals("خبر") && !current.getTypePageID().getType().equals("تبلیغ")) luceneObj.main(co.getCompound1(current.getId()).getIdc(), getContent1(co.getCompound1(current.getId()).getIdc()).getHeader());//یعنی این یک زیر کانتنت در کامپوند است
            
            return prepareList1(co);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void updateContent() {
            getFacade().edit(current);
            recreateModel();
    }
    public String updateForCompound(CompoundController co) {
        System.out.println("@@@@@@@@@@@@@@");

        try {
            getFacade().edit(current);
            
             
            luceneObj.main(co.getCompound1(current.getId()).getIdc(), getContent1(co.getCompound1(current.getId()).getIdc()).getHeader());
            
            
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContentUpdated"));
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            return prepareListForCompound();
        } catch (Exception e) {
            e.printStackTrace();
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            System.out.println("############");
            return null;
        }
    }
    public String updateForCompoundInCompound_NN(CompoundController co) {
        System.out.println("@@@@@@@@@@@@@@");

        try {
            getFacade().edit(current);
            
             
            luceneObj.main(co.getCompound1(current.getId()).getIdc(), getContent1(co.getCompound1(current.getId()).getIdc()).getHeader());
            
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContentUpdated"));
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            return prepareListForCompoundInCompound_NN();
        } catch (Exception e) {
            e.printStackTrace();
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            System.out.println("############");
            return null;
        }
    }

    public String destroy() {
        current = (Content) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ContentDeleted"));
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
    public DataModel getItemsForContentList() {
        if (itemsForContentList == null) {
            itemsForContentList = getPaginationForContentList().createPageDataModel();
        }
        return itemsForContentList;
    }
    public DataModel getItemsForContentList1(String a) {
        num=a;
        //if (itemsForContentList1 == null) { جدیدا سر ایجاد بلاگ، اینو کامنت کردم
            itemsForContentList1 = getPaginationForContentList1().createPageDataModel();
        //}
        return itemsForContentList1;
    }
    public DataModel getItemsForContentHaveComment() { // in Content_comment.xhtml
        itemsForContentHaveComment=null;
        itemsForContentHaveComment = getPaginationForContentHaveComment().createPageDataModel();
        return itemsForContentHaveComment;
    }

    public DataModel getMenuItems() {
        //menuitems=null;
        //if (menuitems == null) {
        menuitems = getPagination2().createPageDataModel();
        //}
        return menuitems;
    }

    public DataModel getMenuItems1() {
        //if (menuitems == null) {
        menuitems = getPagination3().createPageDataModel();
        //}
        return menuitems;
    }


    public DataModel getMenuItems2() {
        //if (menuitems == null) {
        System.out.println("PPPPPPPPPP");
        menuitems = getPagination4().createPageDataModel();
        //}
        return menuitems;
    }

    public DataModel getMenuItems3(int v) {
        //if (menuitems == null) {
        value = v;
        menuitems = getPagination5().createPageDataModel();
        //}
        return menuitems;
    }

    public DataModel getMenuItems4() {
        //if (menuitems == null) {
        menuitems = getPagination6().createPageDataModel();
        //}
        return menuitems;
    }
    public DataModel getAllContentInBlogMenu() {
        //if (menuitems == null) {
        allContentInBlogMenu = getPaginationAllContentInBlogMenu().createPageDataModel();
        
        //}
        return allContentInBlogMenu;
    }

    public DataModel getItemsInIndexPageInBlog(int t,CategaryController c) {
        
        it=t;
        if (it != 0) {
        itemsInIndexPageInBlog = getPaginationInIndexPageInBlog(c).createPageDataModel();
        }
        return itemsInIndexPageInBlog;
    }

    public void setMenuItems(DataModel Entity) {
        menuitems = Entity;
    }

    private void recreateModel() {
        items = null;
        itemsForContentList=null;//in content list
        itemsForContentHaveComment=null;//in content_comment list
        itemsForContentList1=null;//in advertise_list, News_list, ...
    }
    public void setNull() {
        itemsForContentList1=null;//in advertise_list, News_list, ...
        paginationForContentList1=null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPaginationForContentList().nextPage();
        recreateModel();
        return "List";
    }
    public String next1() {
        getPaginationForContentHaveComment().nextPage();
        recreateModel();
        return "List";
    }
    public String next2() {
        getPaginationForContentList1().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPaginationForContentList().previousPage();
        recreateModel();
        return "List";
    }
    public String previous1() {
        getPaginationForContentHaveComment().previousPage();
        recreateModel();
        return "List";
    }
    public String previous2() {
        getPaginationForContentList1().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Content getContent(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    public void getContentAndSetInCurrent(java.lang.Integer id) {
        current = ejbFacade.find(id);
    }

    public Content getContent1() throws IOException {
        System.out.println(" Integer.parseInt(id1) = " + id1 + "  "+ id);
        if (id1 != null) {
            return ejbFacade.find(Integer.parseInt(id1));
        } else {//فکر میکنم این شرط هیچ گاه اجرا نمیشود
            return ejbFacade.find(Integer.parseInt(id));
        }
    }

    @FacesConverter(forClass = Content.class)
    public static class ContentControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ContentController controller = (ContentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "contentController");
            return controller.getContent(getKey(value));
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
            if (object instanceof Content) {
                Content o = (Content) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Content.class.getName());
            }
        }
    }
    //-------------------------------for upload-----------------------------------------------
    public UploadedFile uploadedFile=null;
    

    public void upload11() throws IOException {
        System.out.println(uploadedFile.getName() + "$$$$$$$$$$$$$$$$$$");
        current.setFile(uploadedFile.getName());
        //update();
    }

    public String upload( int a, CompoundController co,TagController tc, ContenthasTagController ctc,CmsController cms,int cmsId) {
        
        if (a==0) {
            current.setCmsID(cms.find(cmsId));
            create();
        }
        
        System.out.println("c is " + co);
            System.out.println("tc is " + tc);
            System.out.println("ctc is " + ctc);
        
        tc.createAll(ctc,current.getId(),cms,cmsId);
        
        
        
                
        if (uploadedFile != null) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String name = "";
        String fileName = "";
        
        try {
            fileName = FilenameUtils.getName(current.getId() + "" + "_" + uploadedFile.getName());
            //current.setType(uploadedFile.getContentType());
            //current.setFsize(uploadedFile.getSize()+" byte");
            current.setFile(current.getId() + "" + "_" + uploadedFile.getName());

            inputStream = uploadedFile.getInputStream();

            name =  fileName;
            // current.setFilename(name);
            outputStream =
                    new FileOutputStream(new File("/root/glass/glass3/glassfish/domains/domain1/applications/jsfcms/images/" + name));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(String.format("  '%s'"
                    + " !", fileName)));

            System.out.println("Done!");
        } catch (IOException e) {
            
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }
        //try {
        //    current.setFile(uploadedFile.getBytes());}
        //catch (IOException e) {
        //    e.printStackTrace();
        //}
        uploadedFile=null;//برای نول شدن یعنی اینکه برای مورد بعدی که عکس آپلود می کند مقدار نداشته باشد
        }
        
        else 
        {
            if (a==0) current.setFile("فایلی آپلود نشده");
            
        }
        
        
        
        return update1(co);
        //update();
    }
    public String upload1(int m,CompoundController co) {
        
        
        if (uploadedFile != null) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String name = "";
        String fileName = "";
        
        try {
            fileName = FilenameUtils.getName(current.getId() + "" + "_" + uploadedFile.getName());
            //current.setType(uploadedFile.getContentType());
            //current.setFsize(uploadedFile.getSize()+" byte");
            current.setFile(current.getId() + "" + "_" + uploadedFile.getName());

            inputStream = uploadedFile.getInputStream();

            name =  fileName;
            // current.setFilename(name);
            outputStream =
                    new FileOutputStream(new File("/root/glass/glass3/glassfish/domains/domain1/applications/jsfcms/images/" + name));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(String.format("  '%s'"
                    + " !", fileName)));

            System.out.println("Done!");
        } catch (IOException e) {
            
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



        }
        //try {
        //    current.setFile(uploadedFile.getBytes());}
        //catch (IOException e) {
        //    e.printStackTrace();
        //}
        }
        else current.setFile("فایلی آپلود نشده");
        
        if(m==1) return updateForCompound(co);
        else return updateForCompoundInCompound_NN(co);
        //update();
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    //public FileInputStream getInFile() {
    //return infile;
    //}
    // public void setInFile(FileInputStream infile) {
    //this.infile = infile;
    // }
    //---------------------------------------------------------------------------------
}