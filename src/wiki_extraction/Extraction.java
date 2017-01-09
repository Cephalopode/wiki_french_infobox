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
		FileWriter out = new FileWriter("property_frequency.xml", false);
		out.close();
		long tStart = System.currentTimeMillis();
	    long tStop;
		
		File input = new File("article-infobox.xml");
		BufferedReader br = new BufferedReader(new FileReader(input));
	    String line;
	    
	    String expression="";
	    while ((line = br.readLine()) != null) {
	    	expression += line;
	    }
	    PropertyList list = new PropertyList(expression);
	    list.getProperties();
	    tStop = System.currentTimeMillis();
	    System.out.println("total time : " + (tStop - tStart) + " ms");
	    
	    
	
	

}

	

}

