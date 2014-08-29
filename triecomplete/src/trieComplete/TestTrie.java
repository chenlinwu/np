package trieComplete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Search size vary from 100 to <arg input>, interval 100
 * 
 * 
 **/


public class TestTrie {

	public static final int N_SEARCH = 500;//search for one combination repeatedly 500 times
	public static int N_WORD;//max search size

	public static void main(String[] args) throws IOException {
		if(args.length==0) {
			System.out.println("usage: TestTrie <N_WORD>");
			return;
		}
		try{
		 N_WORD=Integer.parseInt(args[0]);
		}catch(NumberFormatException notNumeric){
			System.out.println("arg must be integer");
			return;
		}
		
		//initialization
		long startTime = System.currentTimeMillis();
		//get dictionary
		String path = "designers.txt";
		String[] nameList=TrieComplete.openFile(path);
		//creates the trie tree
		TrieComplete t = new TrieComplete(nameList);
		TrieComplete.DEBUG_ON = false;
		TrieComplete.OUTPUT_ON = false;
		long endTime = System.currentTimeMillis();
		long initTime =  endTime-startTime;
		
		System.out.println(t);
		System.out.println("Initialization time: " + initTime +" ms");
		
		//generalize keywords 
		List<String> inputList = new ArrayList<String>();
		//- names
//		for(String[] list : t.nameLists){
//			for(String s:list){
//				if(s!=null) inputList.add(s);
//			}
//		}
		//or min_char
		for(String[] list : t.nameLists){
			for(String s:list){
				if(s!=null && s.length()>=TrieComplete.MIN_CHAR) inputList.add(s.substring(0, TrieComplete.MIN_CHAR));
			}
		}

		for(int nWord = 100 ; nWord <= N_WORD ;nWord+=100){

			int[] randomNumberList = new int[nWord];
			Random random = new Random(System.currentTimeMillis());

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
			System.out.println("Search time: "+(endTime-startTime)+" ms");
		}

	}

}
