/*
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
import junit.framework.Assert;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class testlucene1 {

    public List<Document> a = new ArrayList<Document>();
    public static  void main(String args) throws Exception {
        File indexDir = new File("/home/ahmadi/Downloads/indexed");
        File dataDir = new File("/home/ahmadi/Downloads");
        String suffix = "html";
        testlucene1 indexer = new testlucene1();
        //int numIndex = indexer.index(indexDir, dataDir, suffix, querystr);
        //int numIndex = indexer.index(indexDir, dataDir, suffix);
        //System.out.println("Total files indexed " + numIndex);
        indexer.index(indexDir, dataDir, suffix);


    }

    public void index(File indexDir, File dataDir, String suffix) throws Exception {
//public int index(File indexDir, File dataDir, String suffix) throws Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_30);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_30, analyzer);

        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexDir), config);
        //indexWriter.setUseCompoundFile(false);
        indexDirectory(indexWriter, dataDir, suffix);
        int numIndexed = indexWriter.maxDoc();
        //indexWriter.optimize();
        indexWriter.close();

        //search1(indexDir, analyzer);

        //return numIndexed;
        
    }

    public void indexDirectory(IndexWriter indexWriter, File dataDir, String suffix) throws IOException {
        File[] files = dataDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                indexDirectory(indexWriter, f, suffix);
            } else {
                indexFileWithIndexWriter(indexWriter, f, suffix);
            }
        }
    }

    public void indexFileWithIndexWriter(IndexWriter indexWriter, File f, String suffix) throws IOException {
        if (f.isHidden() || f.isDirectory() || !f.canRead() || !f.exists()) {
            return;
        }
        if (suffix != null && !f.getName().endsWith(suffix)) {
            return;
        }
        
        
        
        
        /*
        
         URL url;

            try {
                // get URL content
                //String u = URLEncoder.encode(matn, "UTF-8");
                String a = "http://www.google.com/";
                url = new URL(a);
                URLConnection conn = url.openConnection();

                // open the stream and put it into BufferedReader
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    System.out.println(url + "sended!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println(inputLine);
                }
                br.close();

                System.out.println("inputLine is " + inputLine);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        
        
        
          
        
        
        
        System.out.println("Indexing file " + f.getCanonicalPath());
        Document doc = new Document();
        
        System.out.println("adding " + doc.get("path"));
        
        doc.add(new TextField("contents", new FileReader(f)));
        doc.add(new TextField("filename", f.getCanonicalPath(), Field.Store.YES));
        indexWriter.addDocument(doc);
    }

    public List<Document> search1(File indexDir, StandardAnalyzer analyzer, String querystr) throws IOException, ParseException //public void search1(File indexDir,StandardAnalyzer analyzer) throws IOException, ParseException
    {
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
        return a;
    }
}
*/