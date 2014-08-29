package trieComplete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 
 * Test based on different name list size, parsed from cmd argument
 * Search size fixed 1000
 *
 */


public class TestTrie2 {

	public static final int N_SEARCH = 500;//search for one combination repeatedly 500 times
	public static int N_NAME;

	public static void main(String[] args) throws IOException {
		if(args.length==0) {
			System.out.println("usage: TestTrie <N_WORD>");
			return;
		}
		try{
		 N_NAME=Integer.parseInt(args[0]);
		}catch(NumberFormatException notNumeric){
			System.out.println("arg must be integer");
			return;
		}
		
		//initialization
		long startTime = System.currentTimeMillis();
		//get dictionary
		String path = "designers.txt";
		String[] nameList=TrieComplete.openFile(path);
		//generate namelists w/ different sizes
		Random random = new Random(System.currentTimeMillis());
		String[] randomNameList = new String[N_NAME];
		
		for (int i = 0; i < N_NAME; i++) {
			randomNameList[i] = nameList[random.nextInt(nameList.length)];
		}
		
		//creates the trie tree
		TrieComplete t = new TrieComplete(randomNameList);
		TrieComplete.DEBUG_ON = false;
		TrieComplete.OUTPUT_ON = false;
		long endTime = System.currentTimeMillis();
		long initTime =  endTime-startTime;
		
		System.out.println(t);
		System.out.println("Initialization time: " + initTime +" ms");
		
		
		//generalize keywords 
		List<String> inputList = new ArrayList<String>();

		//min_char
		for(String[] list : t.nameLists){
			for(String s:list){
				if(s!=null && s.length()>=TrieComplete.MIN_CHAR) inputList.add(s.substring(0, TrieComplete.MIN_CHAR));
			}
		}
			int nWord = 1000;

			int[] randomNumberList = new int[nWord];

			for (int i = 0; i < nWord; i++) {
				randomNumberList[i] = random.nextInt(inputList.size());
			}
			//Search
			startTime = System.currentTimeMillis();
			for (int i = 0; i < nWord; i++) {

				String input = inputList.get(randomNumberList[i]);

				List<String> results = null;
				for (int j = 0; j < N_SEARCH; j++) {
					results = t.Search(input);
				}
				//print results
				if (TrieComplete.DEBUG_ON) {
					for (String s: results) {
						TrieComplete.printUnicode(" - " + s);
					}
					System.out.println("number of results: "+results.size());
				}

			}
			endTime = System.currentTimeMillis();
			System.out.println(N_SEARCH+" search for "+nWord+" names:");
			System.out.println(N_NAME+" names in the dictionary");
			System.out.print("Trie Size:");
			for (int i = 0; i < TrieComplete.WORD_COUNT; i++) {
				System.out.print(t.LookUpTries[i].size+" ");
			}
			System.out.println();
			System.out.println("Search time: "+(endTime-startTime)+" ms");
		}

	}


