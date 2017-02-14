package wiki_extraction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//input : map of article to their properties + map of articles to their categories
//output : a map of properties to the articles where they appear and their occurencices number
public class AssociationList {
	HashMap<String,HashSet<String>> properties, categories;
	HashMap<String,HashMap<String, Integer>> association;
	HashMap<String,HashMap<String, String>> properties_val;
	HashMap<String,HashMap<String, String>> assoc_val;

	public AssociationList(HashMap properties_, HashMap categories_) {
		properties = properties_;
		categories = categories_;
		association = new HashMap<String,HashMap<String, Integer>>();
	}
	public AssociationList(HashMap<String,HashMap<String, String>> properties_val_) {
		properties_val = properties_val_;
		assoc_val = new HashMap<String,HashMap<String, String>>();
	}
	public void createAssociation() throws IOException {
		// Get a set of the entries
	      Set set = properties.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
	      while(i.hasNext()) {
	    	  Map.Entry me = (Map.Entry)i.next();
	    	  HashSet<String> propSet = properties.get(me.getKey());
	    	  HashSet<String> catSet = categories.get(me.getKey());
    		  //System.out.println(me.getKey().toString());
    		  System.out.println(me.getKey());
	    	  
	    	  for(String prop : propSet) {

    			
	    		  HashMap<String, Integer> catFreq_total;
	    		  if(association.get(prop)==null) 
	    			  catFreq_total=new HashMap<String, Integer>();
	    		  else {
	    			  catFreq_total = association.get(prop);
	    		  }
    			  for(String cat : catSet) {
    				  if(catFreq_total.containsKey(cat))
    					  catFreq_total.put(cat, catFreq_total.get(cat)+1);
    				  else
    					  catFreq_total.put(cat, 1);
    			  }
    			  catFreq_total.putAll(catFreq_total);
    			  association.put(prop, catFreq_total);
	    		  
	   
	    	  }
	    	  
	      }
	      
	   display(false, true);
	      
	      
	   //System.out.println(association.toString());
	   System.out.println("prop : " + properties.size() + ", cat : " + categories.size() + ", ass : " + association.size());
	}
	public void createAssociationVal() throws IOException {
		
	    for(Map.Entry<String, HashMap<String,String>> i : properties_val.entrySet()) {
	    	String art = i.getKey();
	    	for(Map.Entry<String, String> j : i.getValue().entrySet()) {
	    		HashMap<String,String> art_val = new HashMap<String,String>();
	    		
	    		//System.out.println(j.toString());
	    		if((assoc_val.get(j.getKey()))==null) {
	    			art_val.put(art, j.getValue());
	    			assoc_val.put(j.getKey(), art_val);
	    		}
	    		else {
	    			art_val = assoc_val.get(j.getKey());
	    			//check if art_val.getVal(art)==null
	    			art_val.put(art, j.getValue());
	    			assoc_val.put(j.getKey(), art_val);
	    		}
	    	}
	    }
	 display(true,true);
		
	}
	private void display(boolean withValue, boolean save) throws IOException {
		
		FileWriter out;
		if(withValue)
			out = new FileWriter("value_per_article.xml", false);
		else
			out = new FileWriter("count_per_category.xml", false);
		
		// Get a set of the entries
		Set disp;
		if(withValue)
	      disp = assoc_val.entrySet();
		else
			disp = association.entrySet();
	      // Get an iterator
	      Iterator j = disp.iterator();
	      while(j.hasNext()) {
	    	  Map.Entry me = (Map.Entry)j.next();
	    	  if(save)
	    		  out.write(me.toString() + "\n");
	    	  else
	    		  System.out.println(me.toString());
	      }
	}
}
