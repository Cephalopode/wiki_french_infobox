package wiki_extraction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getXloreData {
	HashSet<String> categories;
	String body;
	String path = "/Volumes/Macintosh HD/xlore20160407/wikiExtractResults/";
	int counter;
	
	public getXloreData() {
		categories = new HashSet<String>();
	}
	public void saveConcepts() throws IOException {
		FileWriter delete = new FileWriter("frwiki-concepts.dat", false);
		delete.close();
		FileWriter out = new FileWriter("frwiki-concepts.dat", true);
		BufferedReader br = new BufferedReader(new FileReader(path + "/frwiki-category.dat"));
		String line;
		counter = 0;
		while ((line = br.readLine()) != null) {
			final Matcher lineCategoriesMatch = Pattern.compile("^.+?\\t\\t(.+?)$").matcher(line);
			if(lineCategoriesMatch.find()) {
				final Matcher categoriesMatch = Pattern.compile("(.+?);").matcher(lineCategoriesMatch.group(1));
				while(categoriesMatch.find()) {
					String cat = categoriesMatch.group(1) + "\n";
					if(categories.add(cat)){
						//System.out.println(cat);
						out.write(cat);
					}
					else {
						//System.out.println("EXISTS");
					}
					counter = counter + 1;
					if(counter % 100000 == 0) System.out.println(Thread.currentThread().getName() + " : " + counter);
				}
			}
			
		}
		out.close();
		
	}
	public void saveTriplets() throws IOException {
		FileWriter delete = new FileWriter("frwiki-triplets.dat", false);
		delete.close();
		FileWriter out = new FileWriter("frwiki-triplets.dat", true);
		BufferedReader br = new BufferedReader(new FileReader(path + "/frwiki-infobox-tmp.dat"));
		String line;
		counter = 0;
		Pattern article_pattern = Pattern.compile("(?:\\n|^)(.+?)(?:\\t\\t(.+?))*(?=\\n|$)");
		String selection = "";
		while ((line = br.readLine()) != null) {
			Matcher article = article_pattern.matcher(selection + line);
			//if the "selection + line" string contains an infobox
			if(article.find()) {
				if(article.group(1).contains("Jeanne Duval")) {
					if(true){System.out.print("found");}
				}
				outputStr(out,article.group(1) + "\t\t");
				//if this infobox complains properties
				if(article.group(2)!=null) {
					final Matcher property = Pattern.compile("(?:::::;|:::::)(.+?)::::=(.*?)(?=::::;|$)").matcher(article.group(2));
					while(property.find()) {
						outputStr(out,property.group(1) + " = " + property.group(2) + "; ");
					}
				}
				outputStr(out,"\n");
				selection="";
				counter = counter + 1;
				if(counter % 1000 == 0) System.out.println(Thread.currentThread().getName() + " : " + counter);
			}
			else {
				selection = selection + line;
			}
		}
		out.close();
	}
	private void outputStr(FileWriter file, String content) throws IOException {
		if(file == null) {
			System.out.print(content);
		}
		else {
			file.write(content);
		}
			
	}
	


	
}
