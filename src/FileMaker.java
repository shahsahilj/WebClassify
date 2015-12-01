import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

public class FileMaker {
	
	public static void main(String args[]) {
		Map<String,Integer> treemaproot=new TreeMap<String, Integer>();
		String filename="Root.txt";
		treemaproot.put("ABC", 12);
		treemaproot.put("ABsC", 2);
		creating(treemaproot,filename);
		
	}
	//Creates the file "filename" based on the content summary in the filemap
	
	public static void creating(Map<String,Integer> filemap, String filename){
		try{
			File file = new File(filename);
		      // creates the file
			PrintWriter printWriter = new PrintWriter (file);
			for(Map.Entry<String, Integer> entry: filemap.entrySet()){
				printWriter.println(entry.getKey()+"#"+entry.getValue());
			}
		    printWriter.close ();       
		}
		catch(Exception e){
		}
		
	}

}
