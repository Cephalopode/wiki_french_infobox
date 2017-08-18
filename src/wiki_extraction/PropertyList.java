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
//getNbArticlesPerCat returns a hashmap with every cateory mapped to the # of articles it contains. getCategories must be run beforehand to construct "article"
//getCategories returns a hashmap with every article mapped to its categories
//getProperties2values returns a hashmap with every article mapped to its properties + values
public class PropertyList {
	HashMap<String,HashSet<String>> articles;
	HashMap<String,Integer> articles_count;
	HashMap<String,HashMap<String, String>> articles_val;
	String body;
	
	public PropertyList(String body_) {
		articles = new HashMap<String,HashSet<String>>();
		articles_count = new HashMap<String, Integer>();
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
	public HashMap<String, Integer> getNbArticlesPerCat() {
		articles_count = new HashMap<String,Integer>();
		for(Map.Entry<String,HashSet<String>> catEntry : articles.entrySet()) {
			for(String cat : catEntry.getValue()) {
				if(articles_count.containsKey(cat))
					articles_count.put(cat, articles_count.get(cat) + 1);
				else
					articles_count.put(cat, 1);
			}
		}
			
		return articles_count;
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
				properties.put(property.group(1), property.group(2));
			}
			articles_val.put(article.group(1),properties);
		}
		return articles_val;
	}
	public void displaySet(String objectToDisplay) {
		 // Get a set of the entries
		Set set;
		if(objectToDisplay == "articles_val")
			set = articles_val.entrySet();
		else if(objectToDisplay == "articles")
	      set = articles.entrySet();
		else if(objectToDisplay == "articles_count")
			set = articles_count.entrySet();
		else
			set = null;
	      // Get an iterator
	      Iterator i = set.iterator();
	      
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next();
			System.out.print(me.getKey() + " --- ");
			System.out.println(me.getValue().toString());
		}
	}
}
