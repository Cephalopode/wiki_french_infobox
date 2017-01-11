package wiki_extraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

	public static void main(String[] args) throws IOException, InterruptedException {
		//Delete all files
		FileWriter out = new FileWriter("property_frequency.xml", false);
		out.close();
		long tStart = System.currentTimeMillis();
	    long tStop;
		
		String infobox = readFile("article-infobox.xml");
	    PropertyList listProp = new PropertyList(infobox);
	    listProp.getProperties();
	    //listProp.displaySet();
	    
	    String category = readFile("article-category.xml");
	    PropertyList listCat = new PropertyList(category);
	    listCat.getCategories();
	    //listCat.displaySet();
	    AssociationList listAss = new AssociationList(listProp.getProperties(),listCat.getCategories());
	    listAss.createAssociation();
	    
	    
	    tStop = System.currentTimeMillis();
	    System.out.println("total time : " + (tStop - tStart) + " ms");


	}
	public static String readFile(String file) throws IOException {
		File input = new File(file);
		BufferedReader br = new BufferedReader(new FileReader(input));
	    String line;
	    
	    String expression="";
	    while ((line = br.readLine()) != null) {
	    	expression += line;
	    }
	    return expression;
	}

	

}

