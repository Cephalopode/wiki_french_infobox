package wiki_extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyList {
	HashMap<String,HashSet<String>> articles;
	String body;
	
	public PropertyList(String body_) {
		articles = new HashMap<String,HashSet<String>>();
		body=body_;
	}
	public void getProperties() {
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
		
	}
	public void getCategories() {
		final Matcher article = Pattern.compile("(?::;|^)(.+?)\\s{5}(.*?)(?=:;)").matcher(body);
		while(article.find()) {
			final Matcher category = Pattern.compile("(.+?);").matcher(article.group(2));
			HashSet<String> categories = new HashSet<String>();
			while(category.find()) {
				categories.add(category.group(1));
			}
			articles.put(article.group(1),categories);
		}
		
	}
	public void displaySet() {
		 // Get a set of the entries
	      Set set = articles.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
	      
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			System.out.print(me.getKey() + " --- ");
			System.out.println(me.getValue().toString());
		}
	}
}
