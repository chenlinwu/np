package wordBreakII;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.naming.spi.DirStateFactory.Result;

public class Solution {
	public static List<String> wordBreak(String s, Set<String> dict) {
		List<String> resultList = new LinkedList<String>();
		if (s.isEmpty() || dict.isEmpty()) {
		}else{
			findword(resultList, new LinkedList<String>(),new LinkedList<String>(), s, dict);
		}
		return resultList;

	}
	public static void findword(List<String> results,List<String> listf, List<String> listb, String s,Set<String> dict){
		if(s.isEmpty()) {
			if (!listf.isEmpty() || !listb.isEmpty()) {
				String ansf = new String();
				String ansb = new String();
				for (Iterator<String> iterator = listf.iterator(); iterator.hasNext();) {
					ansf += (String) iterator.next()+" ";
				}
				for (int i= listb.size()-1; i>=0; i--) {
					ansb += listb.get(i)+" ";
				}
				String result = ansf+ansb;
				results.add(result.substring(0, ansf.length()-1));
			}
		}

		for (int i = 0; i < s.length(); i++) {
			String currentWord = s.substring(0, i+1);
			if (isWord(currentWord, dict)) {
				List<String> newList = new LinkedList<String>(listf);
				newList.add(currentWord);
				findwordReverse(results,newList, listb,s.substring(i+1), dict);
			}
		}
		
	}
	
	public static void findwordReverse(List<String> results,List<String> listf, List<String> listb, String s,Set<String> dict){
		if(s.isEmpty()) {
			if (!listf.isEmpty() || !listb.isEmpty()) {
				String ansf = new String();
				String ansb = new String();
				for (Iterator<String> iterator = listf.iterator(); iterator.hasNext();) {
					ansf += (String) iterator.next()+" ";
				}
				for (int i= listb.size()-1; i>=0; i--) {
					ansb += listb.get(i)+" ";
				}
				String result = ansf+ansb;
				results.add(result.substring(0, result.length()-1));
			}
		}

		for (int i = s.length()-1; i >=0 ; i--) {
			String currentWord = s.substring(i);
			if (isWord(currentWord, dict)) {
				List<String> newList = new LinkedList<String>(listb);
				newList.add(currentWord);
				findword(results,listf,newList, s.substring(0,i), dict);
			}
		}
		
	}
	public static boolean isWord(String s, Set<String> dict){
		return dict.contains(s.toLowerCase());
	}

	public static void main(String[] args) {
		String string= "catsanddog";
		String[] dictArray = new String[]{"cat", "cats", "and", "sand", "dog"};
		Set<String> dictSet= new HashSet<String>();
		for (int i = 0; i < dictArray.length; i++) {
			dictSet.add(dictArray[i]);	
		}
		System.out.println(wordBreak(string, dictSet));

	}

}
