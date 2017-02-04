
package jsfcms.jsf;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import jsfcms.dao.Tag;

@ManagedBean(name = "interface")
@SessionScoped
public class Interface {
    

    public Interface()
    {
       
    }
    
    public void function(ContenthasTagController ctc,int contentId,List<String> tl)
    {
       TagController c=new TagController();
       c.tagList = tl;
      // c.createAll(ctc, contentId);
       
       
       
       
    }
    
    
}

    