package jsfcms.jsf;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import jsfcms.bean.Globals;

public class htmlUnit extends Globals{

public String homePage(int idPage,int CMSID) throws Exception {
    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    final WebClient webClient = new WebClient();
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setJavaScriptEnabled(false);
    final HtmlPage page = webClient.getPage("http://jsfcms.com/faces/ui/Index.xhtml?pid1=" + idPage + "&cmsId=" + CMSID);
    //Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());

    final String pageAsXml = page.asXml();
    //Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

    final String pageAsText = page.asText();
    //Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));

    webClient.closeAllWindows();
    
    System.out.println("pageAsXml is " +  pageAsXml);
    System.out.println("pageAsText is " +  pageAsText);
            
    return pageAsText;
}
}