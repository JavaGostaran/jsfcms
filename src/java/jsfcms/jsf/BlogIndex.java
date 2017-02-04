
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

@ManagedBean(name = "blogIndex")
@SessionScoped
public class BlogIndex {
    public String type[]=new String[2];
    public String arrow[]=new String[2];
    public String float_[]=new String[2];
    public String text_align[]=new String[2];

    public BlogIndex()
    {
        type[0]= "activity terques";
        arrow[0]="arrow";
        float_[0]="right";
        text_align[0]="left";
        
        type[1]= "activity alt terques";
        arrow[1]="arrow-alt";
        float_[1]="left";
        text_align[1]="right";
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String[] getArrow() {
        return arrow;
    }

    public void setArrow(String[] arrow) {
        this.arrow = arrow;
    }

    public String[] getFloat_() {
        return float_;
    }

    public void setFloat_(String[] float_) {
        this.float_ = float_;
    }

    public String[] getText_align() {
        return text_align;
    }

    public void setText_align(String[] text_align) {
        this.text_align = text_align;
    }
    
}

    