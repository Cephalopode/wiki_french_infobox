package wiki_extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//input : xml file from article_infobox and article_category
//outputs :
//getProperties a hashmap with every article mapped to its properties
//getCategories returns a hashmap with every article mapped to its categories
//getProperties2values returns a hashmap with every article mapped to its properties + values
public class PropertyList {
	HashMap<String,HashSet<String>> articles;
	HashMap<String,HashMap<String, String>> articles_val;
	String body;
	
	public PropertyList(String body_) {
		articles = new HashMap<String,HashSet<String>>();
		articles_val = new HashMap<String,HashMap<String,String>>();
		body=body_;
	}
	public HashMap<String,HashSet<String>> getProperties() {
		final Matcher article = Pattern.compile("(?:::::::;|^)(.+?)\\s{5}(.+?)(?=::::::;|$)").matcher(body);
		while(article.find()) {
			HashSet<String> properties = new HashSet<String>();
			final Matcher property = Pattern.compile("(?:::::;|:::::)(.+?)(::::=)").matcher(article.group(2));
			while(property.find()) {
				//System.out.println(property.group(1));
				properties.add(property.group(1));
			}
			articles.put(article.group(1),properties);
		}
		return articles;
	}
	public HashMap<String,HashSet<String>> getCategories() {
		final Matcher article = Pattern.compile("(?::;|^)(.+?)\\s{5}(.*?)(?=:;)").matcher(body);
		while(article.find()) {
			final Matcher category = Pattern.compile("(.+?);").matcher(article.group(2));
			HashSet<String> categories = new HashSet<String>();
			while(category.find()) {
				categories.add(category.group(1));
			}
			articles.put(article.group(1),categories);
		}
		return articles;
		
	}
	public HashMap<String,HashMap<String, String>> getProperties2values() {
		final Matcher article = Pattern.compile("(?:::::::;|^)(.+?)\\s{5}(.+?)(?=::::::;|$)").matcher(body);
		while(article.find()) {
			HashMap<String, String> properties = new HashMap<String,String>();
			final Matcher property = Pattern.compile("(?:::::;|:::::)(.+?)::::=(.*?)(?=::::;)").matcher(article.group(2));
			while(property.find()) {
				System.out.println(property.group(1));
				properties.put(property.group(1), property.group(2));
			}
			articles_val.put(article.group(1),properties);
		}
		return articles_val;
	}
	public void displaySet(boolean withValue) {
		 // Get a set of the entries
		Set set;
		if(withValue)
			set = articles_val.entrySet();
		else
	      set = articles.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
	      
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			System.out.print(me.getKey() + " --- ");
			System.out.println(me.getValue().toString());
		}
	}
}
