import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class DocFrequency {
	
	
	//This method returns a Map of keywords with their document frequency
	//It takes input as all the url and their page content in a sample
	
	public static Map<String,Integer> docFreq(HashMap<String,String> mapq){
		HashMap<String, Integer> result=new HashMap<String,Integer>();
		Iterator<String> url= mapq.keySet().iterator();
		while(url.hasNext()){
			ArrayList<String> allWords=new ArrayList<String>();
			String s=url.next();
			String s2=mapq.get(s);
			String[] words=s2.split(" ");
			allWords.add(words[0]);
			for (int i = 1; i < words.length; i++) {
				if(allWords.contains(words[i])==false)
					allWords.add(words[i]);
			}
			
			for(int j=0;j<allWords.size();j++)
			{
				String temp=allWords.get(j);
				if(result.containsKey(temp))
					result.put(temp,result.get(temp)+1);
				else
					result.put(temp, 1);
			}
		}
		Map<String, Integer> treemap = new TreeMap<String, Integer>(result); 
		return treemap;
		
	}

}
