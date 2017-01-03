package wiki_extraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.SAXException;

public class Extraction {
	//Picard : 10 sec
	//Français : 45 min
	//Anglais : 2h50

	public static void main(String[] args) throws IOException, InterruptedException {
		//Delete all files
		FileWriter out = new FileWriter("article-outlink.xml", false);
		out.close();
		out = new FileWriter("article-infobox.xml", false);
		out.close();
		out = new FileWriter("article-category.xml", false);
		out.close();
		out = new FileWriter("category-parent.xml", false);
		out.close();
		
		File input = new File("dump/pcdwiki.xml");
		BufferedReader br = new BufferedReader(new FileReader(input));
	    String line;
	    boolean page=false;
	    ArrayList<String> body = new ArrayList<String>();
	    List<Thread> threads = new ArrayList<Thread>();
	    int counter=0;
	    
	    long tStart = System.currentTimeMillis();
	    long tStop;
	    
	    while ((line = br.readLine()) != null) {
	       if(line.contains("<page>"))
	    	   page = true;
	       else if(line.contains("</page>"))	{
	    	   page = false;
	    	   Thread t = new Thread(new Article(body));
	    	   t.start();
	    	   threads.add(t);
	    	   counter++;
	    	   body = new ArrayList<String>();
	    	   if(counter%100==0) {
		    	   tStop = System.currentTimeMillis();
		    	   System.out.println("Article parsed : " + counter + " out of " + (tStop - tStart) + " ms");
	    	   }
	       }
	       else if(page==true)
	    	   body.add(line+"\n");
	       
	    }
	    for (Thread t : threads)
	    	t.join();

	
	

}

	

}

