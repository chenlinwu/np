package trieComplete;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class TrieComplete {

	private String[] people;
	int nPeople;
	public static final int MAX_RES = 10;//greater than 0
	public static final int MIN_CHAR = 2; //greater than 0
	public final boolean NORMALIZE = true; // put false for chinese
	public final static int WORD_COUNT = 3;//greater than 0
	
	public static boolean DEBUG_ON = false;//on for output tries and result paths
	public static boolean OUTPUT_ON = true;//on for output search info and results
	public static enum TRAVERSAL_ENUM {BFS,DFS};
	public final static TRAVERSAL_ENUM TRAVERSAL= TRAVERSAL_ENUM.BFS;
	
	public String[][] nameLists; 
	public Trie[] LookUpTries;
 

	//initialize a TrieComplete and create Tries
	public TrieComplete(String[] people) throws UnsupportedEncodingException{
		this.people = people;
		nPeople = people.length;
		
		nameLists = new String[WORD_COUNT][nPeople];
		LookUpTries = new Trie[WORD_COUNT];
		for (int i = 0; i < WORD_COUNT; i++) {
			nameLists[i]= new String[nPeople];
			LookUpTries[i]=new Trie("Trie number "+ (i+1));
		}

		//split the names and store in lowercase
		for(int i=0; i<nPeople; i++){
			getNameLists(i, people[i]);
		}
		if(NORMALIZE) {
			for (int j = 0; j < WORD_COUNT; j++) {
				for(int i=0; i<nPeople; i++){
					nameLists[j][i] = normalizeToAscii(nameLists[j][i]);
				}
			}
		}
		//create Tries corresponding to every name list
		createTries();
	
		//print tries
		if (DEBUG_ON) {
			for (int i = 0; i < WORD_COUNT; i++) {
				printUnicode((LookUpTries[i]));
			}

		}

	}


	public List<String> Search(String input) throws UnsupportedEncodingException {
		if(input == null) return new LinkedList<String>();
		if (OUTPUT_ON) {
			printUnicode("Search for: "+input);
		}
		//validate the input
		String s= toLowerCase(input.replace(" ", ""));
		if(s.length()<MIN_CHAR) return new LinkedList<String>();

		if(NORMALIZE) s = normalizeToAscii(s);

		//do search in the Tries
		List<String> results = new LinkedList<String>();
		//search tries in order
		for (int i = 0; i < WORD_COUNT-1; i++) {
			searchTrie(results, LookUpTries[i], s, MAX_RES-results.size());
			if (results.size()==MAX_RES)  {
				return results;
			}
		}
		//search the last trie (first name trie)
		searchTrie(results, LookUpTries[WORD_COUNT-1], s, MAX_RES-results.size());
		return results;

	}
	/**
 searchTrie: look input up in the trie, update the results, return the number of results found

	 **/
	public int searchTrie(List<String> results, Trie trie,String input, int maxRes){
		if(maxRes <=0) return 0;
		TrieNode endNode = trie.SearchFor(input);
		if (endNode != null) {
			//print the trie name and the route
			if(DEBUG_ON){
				System.out.println(trie.trieName);
				System.out.println(endNode.toString(0));
			}
			
			List<Integer> nameList = TRAVERSAL == TRAVERSAL_ENUM.DFS?endNode.DFcomplete(maxRes):
				endNode.BFComplete(maxRes);//stretch to the leaves
			for(int i:nameList){//add the full names to the results list
				results.add(people[i]);
			}
			return nameList.size();
		}
		return 0;
	}

	public void createTries(){
		
		for (int j = 0; j < WORD_COUNT; j++) {
			for(int i=0; i<nPeople; i++){
				LookUpTries[j].insert(nameLists[j][i], i);
			}
		}
	}
	private void getNameLists(int index, String name){
		if (WORD_COUNT == 1) {
			nameLists[0][index] = toLowerCase(name);
			return;
		}
		String[] words = name.split(" ");
		//last name (or single-word name)
		String lastName = words[words.length-1];
		nameLists[0][index] = toLowerCase(lastName);
		int firstNameEndPosition = name.length()-lastName.length();
		//middle names
		for(int i=1; i<WORD_COUNT-1 ; i++){
			if (words.length-1>i) {
				String middleName = words[words.length-1-i];
				nameLists[i][index] = toLowerCase(middleName);
				firstNameEndPosition-=middleName.length()+1;
			}else break;//note :a list maybe null
		}
		//first name
		String firstName = name.substring(0, firstNameEndPosition);
		nameLists[WORD_COUNT-1][index] = toLowerCase(firstName);
	}

	public static void main(String[] args) throws IOException {

		if(args.length==0) {
			System.out.println("usage: TrieComplete <keywords>");
			return;
		}
		String path = "designers.txt";
		//path = "short.txt";
		String[] nameList=openFile(path);//txt file that contains the dictionary

		String input = "";//word we search for from the command line
		for(int i=0; i<args.length ; i++){
			input+=args[i]+" ";
		}
		//initialization creates the trie tree
		TrieComplete t = new TrieComplete(nameList);
		if (OUTPUT_ON) {
			System.out.println(t);
		}
		List<String> results = t.Search(input);
		//print results
		if (OUTPUT_ON) {
			for (String s: results) {
				printUnicode(" - " + s);
			}
			System.out.println("number of results: "+results.size());
		}
			

	}
	public static String[] openFile(String path) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(path);
		BufferedReader textReader= new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"));
		final int MAX_NUM = 10000;
		List<String> lines = new ArrayList<String>();  
		String line = null;  
		int numOfLines = 0;
		while (numOfLines<MAX_NUM && (line = textReader.readLine()) != null)   
		{  
			lines.add(line); 
			numOfLines++;
		}  
		textReader.close();   
		return lines.toArray(new String[numOfLines]);  

	}

	public static void printUnicode(Object object ) throws UnsupportedEncodingException{
		PrintStream out = new PrintStream(System.out, true, "UTF-8");
		out.println(object);
	}

	public static String normalizeToAscii(String str) {
		if (str == null) return null;
		String normal = Normalizer.normalize(str,  Normalizer.Form.NFKD)
				.replaceAll("[^\\p{ASCII}]", "");
		return 	normal;
	}

	public static String toLowerCase(String s){
		if(s==null) return null;
		return s.toLowerCase();
	}

	public String toString(){
		

		String s = "MAX_RES "+MAX_RES+" WORD_COUNT "+WORD_COUNT+
				" Noramalize: "+NORMALIZE+" Traversal: "+TRAVERSAL;
		
		return s;
	}

}
