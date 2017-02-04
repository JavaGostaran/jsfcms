package jsfcms.jsf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jsfcms.dao.Settings;
import jsfcms.jsf.util.JsfUtil;
import jsfcms.jsf.util.PaginationHelper;
import jsfcms.bean.SettingsFacade;

import java.io.Serializable;
import java.util.Random;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

@ManagedBean(name = "settingsController")
@SessionScoped
public class SettingsController implements Serializable {

    private Settings current,current_fa,current_en,current_sp,persion,english,spanish;
    private DataModel items = null;
    @EJB
    private jsfcms.bean.SettingsFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    public int lang_ID=2,template_type=2;
    public String dir="ltr", right="left" , left="right";//برای اینکه دیفالت ما فارسی باشد، باید تمام این ها برعکس شده و خط بالا، یک شود
    

    public SettingsController() {
    }
    
    public void createStaticContext(CmsController cms)
    {
        persion = new Settings();
        persion.setSiteTitle("سایت");
        persion.setSiteMenuTitle("منوهای سایت");
        persion.setSiteAdvertiseTitle("تبلیغات");
        persion.setSiteAdvertiseMessage("تبلیغاتی درباره");
        persion.setSiteNewsTitle("اخبار");
        persion.setSitePhotosTitle("گالری عکس");
        persion.setSiteUpperTitle("جاواگستران");
        persion.setSiteFooterTitle("جاواگستران");
        persion.setSiteIcon("");
        persion.setSiteLogo("");
        persion.setSiteCommentSend("لطفا نظر خود را ارسال نمایید:");
        persion.setSiteCommentTitle("لیست نظرات کاربران");
        persion.setSiteCommentMessageAlert("اگر قصد ارسال نظر دارید، لطفا در سایت ما عضو شوید.");
        persion.setSiteCommentMessageSuccess("نظر شما با موفقیت ارسال شد و پس از تایید مدیر، نمایش داده می شود.");
        persion.setSiteCommentSender("کاربر");
        persion.setSiteCommentSended("نظر");
        persion.setSiteCommentSendButton("ارسال نظر");
        persion.setSiteWriterPost("نوشته شده توسط");
        persion.setSiteDatePost("در تاریخ");
        persion.setSiteLogin("ورود");
        persion.setSiteLogout("خروج");
        persion.setSiteRegister("ثبت نام");
        persion.setSiteRegisterName("نام و نام خانوادگی");
        persion.setSiteRegisterUserName("نام کاربری");
        persion.setSiteRegisterPassword("رمز عبور");
        persion.setSiteRegisterEmail("ایمیل");
        persion.setSiteRegisterButton("ثبت نام");
        persion.setSiteWelcomeTitle("خوش آمدید");
        persion.setSiteWelcomeMessage("ثبت نام مقدماتی شما با موفقیت به پایان رسیده است. برای تکمیل ثبت نام لطفا به آدرسی ایمیلی که وارد کرده اید مراجعه نمایید و روی لینکی که برای شما فرستاده شده است کلیک نمایید.  با تشکر");
        persion.setCmsID(cms.returnCurrent());
        create(persion);
        
        english = new Settings();
        english.setSiteTitle("site");
        english.setSiteMenuTitle("Menu");
        english.setSiteAdvertiseTitle("Advertise");
        english.setSiteAdvertiseMessage("Advertise about");
        english.setSiteNewsTitle("News");
        english.setSitePhotosTitle("Photo gallery");
        english.setSiteUpperTitle("Java Gostaran");
        english.setSiteFooterTitle("Java Gostaran");
        english.setSiteIcon("");
        english.setSiteLogo("");
        english.setSiteCommentSend("Please send us your comment:");
        english.setSiteCommentTitle("Users comments list");
        english.setSiteCommentMessageAlert("If desire to send comment, please register in our site.");
        english.setSiteCommentMessageSuccess("Your comment sended successfully and show after that applied with admin.");
        english.setSiteCommentSender("User");
        english.setSiteCommentSended("Comment");
        english.setSiteCommentSendButton("Send Comment");
        english.setSiteWriterPost("Write with");
        english.setSiteDatePost("at");
        english.setSiteLogin("Login");
        english.setSiteLogout("Exit");
        english.setSiteRegister("Register");
        english.setSiteRegisterName("Name");
        english.setSiteRegisterUserName("Username");
        english.setSiteRegisterPassword("Password");
        english.setSiteRegisterEmail("Email");
        english.setSiteRegisterButton("Register");
        english.setSiteWelcomeTitle("Welcome");
        english.setSiteWelcomeMessage("Your first stage registration finished successfully. To complete your registeration, please go to your entered email and click on the link that is send to you. Thanks");
        english.setCmsID(cms.returnCurrent());
        create(english);
        
        spanish = new Settings();
        spanish.setSiteTitle("site");
        spanish.setSiteMenuTitle("Menú");
        spanish.setSiteAdvertiseTitle("Anunciar");
        spanish.setSiteAdvertiseMessage("Publicidad sobre");
        spanish.setSiteNewsTitle("Noticias");
        spanish.setSitePhotosTitle("Galería de fotos");
        spanish.setSiteUpperTitle("Java Gostaran");
        spanish.setSiteFooterTitle("Java Gostaran");
        spanish.setSiteIcon("");
        spanish.setSiteLogo("");
        spanish.setSiteCommentSend("Por favor, envíanos tu comentario:");
        spanish.setSiteCommentTitle("Lista de Usuarios comentarios");
        spanish.setSiteCommentMessageAlert("Si el deseo de enviar comentarios, por favor registrarse en nuestro sitio.");
        spanish.setSiteCommentMessageSuccess("Tu comentario mandó correctamente y espectáculo después de que se aplica con administrador.");
        spanish.setSiteCommentSender("Usuario");
        spanish.setSiteCommentSended("Comentario");
        spanish.setSiteCommentSendButton("Enviar comentario");
        spanish.setSiteWriterPost("Escribir con");
        spanish.setSiteDatePost("en");
        spanish.setSiteLogin("Login");
        spanish.setSiteLogout("Salida");
        spanish.setSiteRegister("Registrar");
        spanish.setSiteRegisterName("Nombre");
        spanish.setSiteRegisterUserName("Nombre de usuario");
        spanish.setSiteRegisterPassword("Contraseña");
        spanish.setSiteRegisterEmail("Email");
        spanish.setSiteRegisterButton("Registrar");
        spanish.setSiteWelcomeTitle("Bienvenido");
        spanish.setSiteWelcomeMessage("Su primera etapa de inscripción ha finalizado correctamente. Para completar su registeration, por favor vaya a su correo electrónico introducido y haga clic en el enlace que se envía a usted. gracias");
        spanish.setCmsID(cms.returnCurrent());
        create(spanish);
    }
    
    public void getCmsControllerAndFindOnItByAnIdToUseForCreate (CmsController c,int i)
    {
        prepareCreate1();
        current.setCmsID(c.find(i));
    }
    
    public void SettingsController() {
        
    }

    public int getLang_ID() {
        return lang_ID;
    }

    public void setLang_ID(int lang_ID) {
        this.lang_ID = lang_ID;
        if (lang_ID == 1) {
            setDir("rtl");
            setRight("right");
            setLeft("left");
            setTemplate_type(1);
        }
        else {
            setDir("ltr");
            setRight("left");
            setLeft("right");
            setTemplate_type(2);
        }
  
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public int getTemplate_type() {
        return template_type;
    }

    public void setTemplate_type(int template_type) {
        this.template_type = template_type;
    }

    
    
    
    
    
    
    public void find() {
        if(!getFacade().findRange().isEmpty())
            current=getFacade().findRange().get(lang_ID - 1);
        else current = new Settings();
        
    }
    public void find_lang() {
        current_fa=getFacade().findRange().get(0);
        current_en=getFacade().findRange().get(1);
        current_sp=getFacade().findRange().get(2);
        
    }

    public Settings getSelected() {
        if (current == null) {
            current = new Settings();
            selectedItemIndex = -1;
        }
        return current;
    }
    public Settings getSelected_fa() {
        if (current_fa == null) {
            current_fa = new Settings();
            selectedItemIndex = -1;
        }
        return current_fa;
    }
    public Settings getSelected_en() {
        if (current_en == null) {
            current_en = new Settings();
            selectedItemIndex = -1;
        }
        return current_en;
    }
    public Settings getSelected_sp() {
        if (current_sp == null) {
            current_sp = new Settings();
            selectedItemIndex = -1;
        }
        return current_sp;
    }

    private SettingsFacade getFacade() {
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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Settings) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Settings();
        selectedItemIndex = -1;
        return "Create";
    }
    public void prepareCreate1() {
        current = new Settings();
        selectedItemIndex = -1;
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SettingsCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public void create(Settings set) {
        try {
            getFacade().create(set);
        } catch (Exception e) {
        }
    }

    public String prepareEdit() {
        current = (Settings) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SettingsUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public void update1() {
        try {
            System.out.println("<<<<<<<" + current.getSiteAdvertiseTitle() + ">>>>>>>>");
            getFacade().edit(current_fa);
            getFacade().edit(current_en);
            getFacade().edit(current_sp);
            System.out.println("hi hello");
        } catch (Exception e) {
            System.out.println("hello hi");
        }
    }

    public String destroy() {
        current = (Settings) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SettingsDeleted"));
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

    @FacesConverter(forClass = Settings.class)
    public static class SettingsControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SettingsController controller = (SettingsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "settingsController");
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
            if (object instanceof Settings) {
                Settings o = (Settings) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Settings.class.getName());
            }
        }
        

    }
        public UploadedFile uploadedFile=null;
        
    public UploadedFile getUploadedFile() {
        if (uploadedFile != null) System.out.println("uploadedfile " + uploadedFile.getName());
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    public void upload(int a) {

        if (uploadedFile != null) {
            System.out.println("uploadedfile isssss " + uploadedFile.getName());


            InputStream inputStream = null;
            OutputStream outputStream = null;
            String name = "";
            String fileName = "";
            Random rand = new Random();

            try {
                fileName = FilenameUtils.getName(rand + "" + "_" + uploadedFile.getName());
                //current.setType(uploadedFile.getContentType());
                //current.setFsize(uploadedFile.getSize()+" byte");
                if (a == 1) {
                    current_fa.setSiteIcon(rand + "" + "_" + uploadedFile.getName());
                    current_en.setSiteIcon(rand + "" + "_" + uploadedFile.getName());
                    current_sp.setSiteIcon(rand + "" + "_" + uploadedFile.getName());
                } else {
                    current_fa.setSiteLogo(rand + "" + "_" + uploadedFile.getName());
                    current_en.setSiteLogo(rand + "" + "_" + uploadedFile.getName());
                    current_sp.setSiteLogo(rand + "" + "_" + uploadedFile.getName());
                }
                System.out.println("uploadedfile is " + fileName);
                inputStream = uploadedFile.getInputStream();

                name = fileName;
                // current.setFilename(name);
                outputStream =
                        new FileOutputStream(new File("/home/ahmadi/NetBeansProjects/jsfcms10/web/images/" + name));

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

            update1();
            uploadedFile = null;
        }
    }
        

}
