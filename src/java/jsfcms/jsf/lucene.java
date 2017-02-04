package jsfcms.jsf;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import junit.framework.Assert;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

@ManagedBean(name = "lucene")
public class lucene extends htmlUnit {
    
    public static int CMSID;

    public static int getCMSID() {
        System.out.println("CMSID is2 " + CMSID);
        return CMSID;
    }

    public static void setCMSID(int CMSID) {
        
        lucene.CMSID = CMSID;
        System.out.println("CMSID is0 " + CMSID);
        System.out.println("lucene.CMSID is1 " + lucene.CMSID);
    }

   
    
    

    

    public void createDir(int num)
    {
        File f = new File("/root/glass/glass3/glassfish/domains/domain1/applications/jsfcms/indexed/" + num );
       f.mkdir();
       
    }
    
    public void main(int idPage,String headerPage) throws Exception {
        
        htmlUnit htmlunit = new htmlUnit();
        String pageAsText=null;
        pageAsText = htmlunit.homePage(idPage,getCMSID());//getting all of the words in Index.xhtml page
        
        System.out.println("CMSID was " + lucene.CMSID);
        File indexDir = new File("/root/glass/glass3/glassfish/domains/domain1/applications/jsfcms/indexed/" +  getCMSID());
        //File dataDir = new File("/home/ahmadi/Downloads");
        //String suffix = "html";
        lucene indexer = new lucene();
        //int numIndex = indexer.index(indexDir, dataDir, suffix, querystr);
        //int numIndex = indexer.index(indexDir, dataDir, suffix);
        //System.out.println("Total files indexed " + numIndex);
        indexer.index(indexDir, pageAsText, idPage , headerPage);
        
        
        


    }
    public void mainRemove(int idPage) throws Exception,IOException,CorruptIndexException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        File indexDir = new File("/root/glass/glass3/glassfish/domains/domain1/applications/jsfcms/indexed/" + getCMSID() + "/");
        
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_30, analyzer);
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), config);
        Term term=new Term("address" ,"http://jsfcms.com/faces/ui/Index.xhtml?pid1=" + idPage + "&cmsId=" + getCMSID());
        indexWriter.deleteDocuments(new TermQuery(term));
        System.out.println("termquery is " + new TermQuery(term));
        indexWriter.close();

    }

    public void index(File indexDir, String pageAsText, int idPage, String headerPage) throws Exception {
//public int index(File indexDir, File dataDir, String suffix) throws Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_30, analyzer);

        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), config);
        
        //indexWriter.setUseCompoundFile(false);
        indexFileWithIndexWriter(indexWriter, pageAsText, idPage, headerPage);
        int numIndexed = indexWriter.maxDoc();
        //indexWriter.optimize();
        indexWriter.close();

        //search1(indexDir, analyzer);

        //return numIndexed;
        
    }

    


    public void indexFileWithIndexWriter(IndexWriter indexWriter, String pageAsText, int idPage, String headerPage) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Term term=new Term("address" , "http://jsfcms.com/faces/ui/Index.xhtml?pid1=" + idPage + "&cmsId=" + getCMSID());

        System.out.println("term is " + term.text());
        
        indexWriter.deleteDocuments(new TermQuery(term));
        
        Document doc = new Document();
        
        System.out.println("adding " + doc.get("path"));
        
        
       
        doc.add(new Field("id", "" +  idPage, Field.Store.YES, Field.Index.NOT_ANALYZED)); 
        //doc.add(new TextField("contents", pageAsText, Field.Store.YES));
        doc.add(new Field("contents", pageAsText, Field.Store.YES, Field.Index.ANALYZED));
        //doc.add(new TextField("filename", headerPage, Field.Store.YES));
        doc.add(new Field("filename", headerPage, Field.Store.YES, Field.Index.ANALYZED));
        //doc.add(new TextField("address", "http://localhost:8080/jsfcms10/faces/ui/Index.xhtml?pid1=" + idPage, Field.Store.YES));
        doc.add(new Field("address","http://jsfcms.com/faces/ui/Index.xhtml?pid1=" + idPage + "&cmsId=" + getCMSID(), Field.Store.YES, Field.Index.NOT_ANALYZED)); // this field not analyzed because we want to make query from it
        
        indexWriter.addDocument(doc);
        System.out.println("doc is " + doc);
    }

    public List<Document> search1(File indexDir, StandardAnalyzer analyzer, String querystr) throws IOException, ParseException //public void search1(File indexDir,StandardAnalyzer analyzer) throws IOException, ParseException
    {
        List<Document> a = new ArrayList<Document>();
        //String querystring =  "آیه"; 

        // the "title" arg specifies the default field to use
        // when no field is explicitly specified in the query.
        Query q = new QueryParser(Version.LUCENE_30, "contents", analyzer).parse(querystr);

        // 3. search
        int hitsPerPage = 10;
        IndexReader reader = DirectoryReader.open(FSDirectory.open(indexDir));
        IndexSearcher searcher = new IndexSearcher(reader);
        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
        searcher.search(q, collector);
        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        // 4. display results
        System.out.println("Found " + hits.length + " hits.");
        
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("filename") + "\t" + querystr);
            a.add(d);
        }
        System.out.println("ok");
        return a;
    }
}