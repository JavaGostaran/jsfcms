package jsfcms.jsf;


import java.io.File;
import javax.faces.bean.ManagedBean;
import jsfcms.bean.Globals;


@ManagedBean(name = "createDir")
public class CreateDir extends Globals{

    
    
    public void main() {
        
       File f = new File("/home/ahmadi/NetBeansProjects/jsfcms10/web/indexed/" + cmsId);
       f.mkdir();


    }
   
}