package wiki_extraction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AssociationList {
	HashMap<String,HashSet<String>> properties, categories;
	HashMap<String,HashMap<String, Integer>> association;

	public AssociationList(HashMap properties_, HashMap categories_) {
		properties = properties_;
		categories = categories_;
		association = new HashMap<String,HashMap<String, Integer>>();
	}
	public void createAssociation() {
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
	      System.out.println(association.toString());
	      System.out.println("prop : " + properties.size() + ", cat : " + categories.size() + ", ass : " + association.size());
	}
}
