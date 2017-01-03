package wiki_extraction;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Article implements Runnable {
	ArrayList<String> body_array;
	String body="";
	String title;
	FileWriter out;
	
	
	public Article(ArrayList<String> body2) throws IOException {
		body_array=body2;
		for(String s : body_array){
			body = body.concat(s);
		}
		
	}


	public void outlink() throws IOException {    
		out = new FileWriter("article-outlink.xml", true);
		out.write(title + "     ");
		final Matcher m = Pattern.compile("\\[\\[(?![Ff]ile|[Ff]ichier)(?![Cc]ategory|[Cc]atégorie)(.+?)(\\|.+?)?\\]\\]").matcher(body);
		
		while(m.find()) {
			out.write(m.group(1)+";");
		}
		out.close();
	}
	public void infobox() throws IOException {
		boolean hasInfobox=false;
		out = new FileWriter("article-infobox.xml", true);
		final Matcher m = Pattern.compile("\\{([iI]nfobox|[iI]nfoboète.+)[\\s\\S]([.(\\s\\S)]+?)\\n}}").matcher(body);
		
		while(m.find()) {
			if(hasInfobox==false) {
				hasInfobox=true;
				out.write(title + "     ");
			}
			out.write(m.group(1)+":::::");
			final Matcher n = Pattern.compile("\\|(.+?)\\s*=[^\\S\\n]*(.*)").matcher(m.group(2));
			while(n.find()) {
				String group2 = Jsoup.parse(n.group(2)).text();
				group2 = group2.replaceAll("<[^>]*>[^>]*<[^>]*>", "");
				out.write(n.group(1)+"::::="+group2+"::::;");
			}
		}
		out.close();
	}
	public void category() throws IOException {
		out = new FileWriter("article-category.xml", true);
		out.write(title + "     ");
		final Matcher m = Pattern.compile("\\[\\[([Cc]atégorie|[Cc]ategory):(.+?)(\\|.+?)?\\]\\]").matcher(body);
		while(m.find()) {
			out.write(m.group(2)+";");
		}
		
		out.close();
	}
	public void parent(String cat_title) throws IOException {
		out = new FileWriter("category-parent.xml", true);
		out.write(cat_title + "     ");
		final Matcher m = Pattern.compile("\\[\\[([Cc]atégorie|[Cc]ategory):(.+?)(\\|.+?)?\\]\\]").matcher(body);
		while(m.find()) {
			out.write(m.group(2)+";");
		}
		
		out.close();
	}
	
	
	
	public void run() {
		try {
			final Matcher m = Pattern.compile("<title>(.+?)<\\/title>").matcher(body);
			if(m.find())
				title = m.group(1);
			else
				System.out.println("No title");
			
			final Matcher a = Pattern.compile("([Cc]atégorie|[Cc]ategory):(.+)").matcher(title);
			if(a.find()) {
				parent(a.group(2));
			}
			else {
				//System.out.println("start : " + title);
				outlink();
				infobox();
				category();
				//System.out.println("finish : " + title);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
	
}