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
		FileWriter out = new FileWriter("count_per_category.xml", false);
		out.close();
		FileWriter out2 = new FileWriter("value_per_article.xml", false);
		out2.close();
		long tStart = System.currentTimeMillis();
	    long tStop;
		
	  //=========== PART 1 =============
	    //create objects from xml files
		String infobox = readFile("article-infobox.xml");
	    PropertyList listProp = new PropertyList(infobox);
	    //listProp.getProperties();
	    //listProp.displaySet(false);
	    String category = readFile("article-category.xml");
	    PropertyList listCat = new PropertyList(category);
	    //listCat.getCategories();
	    //listCat.displaySet(false);
	    //Create list with number of appearance in each category, for each existing property
	    AssociationList listAss = new AssociationList(listProp.getProperties(),listCat.getCategories());
	    listAss.createAssociation();
	    listAss.createAssociationReverse();
	    
	    //=========== PART 2 =============
	    PropertyList listPropVal = new PropertyList(infobox);
	    //listPropVal.getProperties2values();
	    //listPropVal.displaySet(true);
	    //Create a list with value in each article, for each existing category
	    AssociationList listAssVal = new AssociationList(listPropVal.getProperties2values());
	    listAssVal.createAssociationVal();
	    
	    
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

