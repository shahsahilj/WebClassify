import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class DbClassify {
	static String computers[]={"cpu", "java","module", "multimedia", "perl", "vb", "agp card", "application windows", "applet code", "array code", "audio file", "avi file", "bios", "buffer code", "bytes code", "shareware", "card drivers", "card graphics", "card pc", "pc windows"};
	static String sports[]={"laker", "ncaa", "pacers", "soccer", "teams", "wnba", "nba", "avg league", "avg nba", "ball league", "ball referee", "ball team", "blazers game", "championship team", "club league", "fans football", "game league"};
	static String health[]={"acupuncture","aerobic","aerobics","aids","cancer","cardiology","cholesterol","diabetes","diet","fitness","hiv","insulin","nurse","squats","treadmill","walkers","calories fat","carb carbs","doctor health","doctor medical","eating fat","fat muscle","health medicine","health nutritional","hospital medical","hospital patients","medical patient","medical treatment","patients treatment"};
	static String disease[]={"aids","cancer","dental","diabetes","hiv","cardiology","aspirin cardiologist","aspirin cholesterol","blood heart","blood insulin","cholesterol doctor","cholesterol lipitor","heart surgery","radiation treatment"};
	static String hardware[]={"bios","motherboard","board fsb","board overclocking","fsb overclocking","bios controller ide","cables drive floppy"};
	static String fitness[]={"aerobic","fat","fitness","walking","workout","acid diets","bodybuilding protein","calories protein","calories weight","challenge walk","dairy milk","eating protein","eating weight","exercise protein","exercise weight"};
	static String soccer[]={"uefa","leeds","bayern","bundesliga","premiership","lazio","mls","hooliganism","juventus","liverpool","fifa"};
	static String programming[]={"actionlistener","algorithms","alias","alloc","ansi","api","applet","argument","array","binary","boolean","borland","char","class","code","compile","compiler","component","container","controls","cpan","java","perl"};
	static String basketball[]={"nba","pacers","kobe","laker","shaq","blazers","knicks","sabonis","shaquille","laettner","wnba","rebounds","dunks"};
	int root_size=computers.length+sports.length+health.length;
	int comp_size=hardware.length+programming.length;
	int health_size=disease.length+fitness.length;
	int sports_size=basketball.length+soccer.length;
	static HashMap<String,String> root_docs=new HashMap<String,String>();
	static HashMap<String,String> computer_docs=new HashMap<String,String>();
	static HashMap<String,String> health_docs=new HashMap<String,String>();
	static HashMap<String,String> sports_docs=new HashMap<String,String>();
	static ArrayList<String> root_urls=new ArrayList<String>();
	static ArrayList<String> computer_urls=new ArrayList<String>();
	static ArrayList<String> health_urls=new ArrayList<String>();
	static ArrayList<String> sports_urls=new ArrayList<String>();
	static boolean comp_flag=false;
	static boolean sports_flag=false;
	static boolean health_flag=false;
	static String accountKey="";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		accountKey=args[0];
		double ts=Double.parseDouble(args[1]);
		int tc=Integer.parseInt(args[2]);
		classify(ts,tc,args[3]);

	}
	
	public static void classify(double ts,int tc,String site)
	{
		ArrayList<String> finalresult=new ArrayList<String>();
		boolean classified[]= new boolean[9];
		//String categories[]={"Computers","Hardware","Programming","Health","Fitness","Disease","Sports","Soccer","Basketball"};
		int computerCount=0,healthCount=0,sportsCount=0;
		double computerSp=0,healthSp=0,sportsSp=0;
		System.out.println("Classifying....\n");
		
		//Calculating the coverage and the specificity and further classifying based on tthe threshold values
		computerCount=calculate(computers,site,root_urls);
		healthCount=calculate(health,site,root_urls);
		sportsCount=calculate(sports,site,root_urls);
		long total=computerCount+healthCount+sportsCount;
		computerSp=(double)computerCount/total;
		healthSp=(double)healthCount/total;
		sportsSp=(double)sportsCount/total;
		System.out.println("Computers- Specificty:"+computerSp+" Coverage:"+computerCount);
		System.out.println("Health- Specificty:"+healthSp+" Coverage:"+healthCount);
		System.out.println("Sports- Specificty:"+sportsSp+" Coverage:"+sportsCount);
		if(computerCount>=tc && computerSp>=ts){
			classified[0]=true;
			int hardwareCount=0,programCount=0;
			double hardwareSp=0,programSp=0;
			hardwareCount=calculate(hardware,site,computer_urls);
			programCount=calculate(programming,site,computer_urls);
			long ctotal=hardwareCount+programCount;
			hardwareSp=(double)hardwareCount/ctotal;
			programSp=(double)programCount/ctotal;
			if(hardwareCount>=tc&& hardwareSp>=ts)
				classified[1]=true;
			if(programCount>=tc&&programSp>=ts)
				classified[2]=true;
			System.out.println("Computers/Hardware- Specificty:"+hardwareSp+" Coverage:"+hardwareCount);
			System.out.println("Computers/Programming- Specificty:"+programSp+" Coverage:"+programCount);
		}
		if(healthCount>=tc && healthSp>=ts){
			classified[3]=true;
			int fitnessCount=0,diseaseCount=0;
			double fitnessSp=0,diseaseSp=0;
			fitnessCount=calculate(fitness,site,health_urls);
			diseaseCount=calculate(disease,site,health_urls);
			long ctotal=fitnessCount+diseaseCount;
			fitnessSp=(double)fitnessCount/ctotal;
			diseaseSp=(double)diseaseCount/ctotal;
			if(fitnessCount>=tc&& fitnessSp>=ts )
				classified[4]=true;
			if(diseaseCount>=tc&&diseaseSp>=ts)
				classified[5]=true;
			System.out.println("Health/Fitness- Specificty:"+fitnessSp+" Coverage:"+fitnessCount);
			System.out.println("Health/Disease- Specificty:"+diseaseSp+" Coverage:"+diseaseCount);
		}
		if(sportsCount>=tc && sportsSp>=ts){
			classified[6]=true;
			int soccerCount=0,basketballCount=0;
			double soccerSp=0,basketballSp=0;
			soccerCount=calculate(soccer,site,sports_urls);
			basketballCount=calculate(basketball,site,sports_urls);
			long ctotal=soccerCount+basketballCount;
			soccerSp=(double)soccerCount/ctotal;
			basketballSp=(double)basketballCount/ctotal;
			if(soccerCount>=tc&&soccerSp>=ts)
				classified[7]=true;
			if(basketballCount>=tc&&basketballSp>=ts)
				classified[8]=true;
			System.out.println("Sports/Basketball- Specificty:"+basketballSp+" Coverage:"+basketballCount);
			System.out.println("Sports/Soccer- Specificty:"+soccerSp+" Coverage:"+soccerCount);
		}
		
		//Checking which categories the site has been classified into
		if(classified[0]==false&&classified[3]==false&&classified[6]==false)
			finalresult.add("Root");
		else{
			if(classified[0]==true){
				finalresult.add("Root/Computers");
				if(classified[1]==true){
					finalresult.remove("Root/Computers");
					finalresult.add("Root/Computers/Hardware");
				}
				if(classified[2]==true){
					finalresult.remove("Root/Computers");
					finalresult.add("Root/Computers/Programming");
				}
			}
			if(classified[3]==true){
				finalresult.add("Root/Health");
				if(classified[4]==true){
					finalresult.remove("Root/Health");
					finalresult.add("Root/Health/Fitness");
				}
				if(classified[5]==true){
					finalresult.remove("Root/Health");
					finalresult.add("Root/Health/Disease");
				}
			}
			if(classified[6]==true){
				finalresult.add("Root/Sports");
				if(classified[7]==true){
					finalresult.remove("Root/Sports");
					finalresult.add("Root/Sports/Soccer");
				}
				if(classified[8]==true){
					finalresult.remove("Root/Sports");
					finalresult.add("Root/Sports/Basketball");
				}
			}
		}
		System.out.println();
		System.out.println("The "+site+" has been classified as:");
		for (int j = 0; j < finalresult.size(); j++) {
			System.out.println(finalresult.get(j));
		}
		System.out.println();
		System.out.println();
		getPages(root_docs, root_urls);
		getPages(computer_docs, computer_urls);
		getPages(health_docs, health_urls);
		getPages(sports_docs, sports_urls);
		
		for (int j = 0; j < finalresult.size(); j++) {
			if(finalresult.get(j).contains("Computers"))
				comp_flag=true;
			if(finalresult.get(j).contains("Health"))
				health_flag=true;
			if(finalresult.get(j).contains("Sports"))
				sports_flag=true;
		}
		generateFiles(site);
		
	}
	
	//This method returns the count for the category and stores the urls that will be used to make the content summary
	public static int calculate(String arr[],String site,ArrayList<String> urls)
	{
		int t=0;int i;
		DocumentQ temp;
		for(i=0;i<arr.length;i++)
		{
			temp=BingCount.getDocs(arr[i], site, accountKey);
			t+=temp.docCount;
			for (int j = 0; j < 4; j++) {
				if(temp.url[j]!=null){
					urls.add(temp.url[j]);
				}
			}
		}
		return t;
	}
	
	//This method retrieves the pages from the url by calling the GetWordsLynx.runLynx method and stores it in a HashMap
	public static void getPages(HashMap<String, String> docs, ArrayList<String> urls){
		if(urls.isEmpty())
			return;
		for (int i = 0; i < urls.size(); i++) {
			String page="";
			String urltemp=urls.get(i);
			System.out.println("Getting page "+urltemp);
			System.out.println();
			page=GetWordsLynx.runLynx(urltemp);
			docs.put(urls.get(i), page);
		}
	}
	
	//This method decides which files have to be created based on the classification
	//It generates the Map for the respective file by calling the DocFrequency.docFreq method
	//For creating the file the FileMaker.creating method is called
	public static void generateFiles(String site){
		HashMap<String, String> t=new HashMap<String, String>();
		
		Map<String,Integer> treemaproot=new TreeMap<String, Integer>();
		Map<String,Integer> treemapcomp=new TreeMap<String, Integer>();
		Map<String,Integer> treemaphealth=new TreeMap<String, Integer>();
		Map<String,Integer> treemapsports=new TreeMap<String, Integer>();
		t.putAll(root_docs);
		String filename="";
		if(comp_flag==true){
			t.putAll(computer_docs);
			treemapcomp=DocFrequency.docFreq(computer_docs);
			filename="Computers-"+site+".txt";
			FileMaker.creating(treemapcomp, filename);
		}
		if(sports_flag==true){
			t.putAll(sports_docs);
			treemapsports=DocFrequency.docFreq(sports_docs);
			filename="Sports-"+site+".txt";
			FileMaker.creating(treemapsports, filename);
		}
		if(health_flag==true){
			t.putAll(health_docs);
			treemaphealth=DocFrequency.docFreq(health_docs);
			filename="Health-"+site+".txt";
			FileMaker.creating(treemaphealth, filename);
		}
		
		treemaproot=DocFrequency.docFreq(t);
		filename="Root-";
		filename+=site+".txt";
		FileMaker.creating(treemaproot, filename);
		
	}


}
