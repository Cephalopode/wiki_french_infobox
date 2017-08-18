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
	HashMap<String,Integer> articles_count;
	HashMap<String,HashMap<String, Integer>> association;
	HashMap<String,HashMap<String, String>> properties_val;
	HashMap<String,HashMap<String, String>> assoc_val;

	public AssociationList(HashMap properties_, HashMap categories_, HashMap articles_count_) {
		properties = properties_;
		categories = categories_;
		articles_count = articles_count_;
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
	    	  
	    	  
	    	  for(String prop : propSet) {
	    		  HashMap<String, Integer> catFreq_total;
	    		  if(association.get(prop)==null) 
	    			  catFreq_total=new HashMap<String, Integer>();
	    		  else {
	    			  catFreq_total = association.get(prop);
	    		  }
	    		  if(catSet != null) {
	    			  for(String cat : catSet) {
	    				  if(catFreq_total.containsKey(cat))
	    					  catFreq_total.put(cat, catFreq_total.get(cat) + 1);
	    				  else
	    					  catFreq_total.put(cat, 1);
	    			  }
	    			  catFreq_total.putAll(catFreq_total);
	    			  association.put(prop, catFreq_total);
	    		  }
	    	  }
	      }
	      // Divide all count values (m) by total number of entities in the concept (n)
	      Set ass_prop = association.entrySet();
	      // Get an iterator
	      Iterator j = ass_prop.iterator();
	      while(j.hasNext()) {
	    	  Map.Entry me = (Map.Entry)j.next();
	    	  HashMap<String, Integer> catMap = association.get(me.getKey());
	    	  for(Map.Entry<String, Integer> catEntry : catMap.entrySet()) {
	    		  int n = articles_count.get(catEntry.getKey());
	    		  catMap.put(catEntry.getKey(), catEntry.getValue()*1000 / n);
	    	  }
	    	  association.put(me.getKey().toString(), catMap);
	      }
	      
	   display(2, true);
	   System.out.println("prop : " + properties.size() + ", cat : " + categories.size());
	  System.out.println("ass : " + association.size());
	}
	public void createAssociationReverse() throws IOException {
	      Set set = properties.entrySet();
	      Iterator i = set.iterator();
	      while(i.hasNext()) {
	    	  Map.Entry me = (Map.Entry)i.next();
	    	  HashSet<String> propSet = properties.get(me.getKey());
	    	  HashSet<String> catSet = categories.get(me.getKey());
	    	  
	    	  if (catSet !=null) {
		    	  for(String cat : catSet) {
		    		  HashMap<String, Integer> propFreq_total;
		    		  if(association.get(cat)==null) 
		    			  propFreq_total=new HashMap<String, Integer>();
		    		  else {
		    			  propFreq_total = association.get(cat);
		    		  }
	    			  for(String prop : propSet) {
	    				  if(propFreq_total.containsKey(prop))
	    					  propFreq_total.put(prop, propFreq_total.get(prop) + 1);
	    				  else
	    					  propFreq_total.put(prop, 1);
	    			  }
	    			  propFreq_total.putAll(propFreq_total);
	    			  association.put(cat, propFreq_total);
		    	  }
	    	  }
	      }
	      System.out.println("prop : " + properties.size() + ", cat : " + categories.size());
	      display(1,true);
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
	 display(0,true);
		
	}
	private void display(int file, boolean save) throws IOException {
		
		FileWriter out;
		if(file==0)
			out = new FileWriter("value_per_article.xml", false);
		else if(file==1)
			out = new FileWriter("count_per_property.xml", false);
		else if(file==2)
			out = new FileWriter("count_per_category.xml", false);
		else
			out = null;
		
		// Get a set of the entries
		Set disp;
		if(file==0)
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
