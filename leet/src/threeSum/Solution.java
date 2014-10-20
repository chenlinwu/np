package threeSum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Solution {
	public static List<List<Integer>> threeSum(int[] num) {
		Set<Integer> visited = new HashSet<Integer>();
		List<List<Integer>> result= new LinkedList<List<Integer>>();
		for (int i = 0; i < num.length; i++) {

			int firstNum=num[i];
			if (!visited.contains(firstNum)) {
				List<Integer> secondNumList=twoSum(num, i+1, 0-firstNum,visited);
				if (secondNumList.size()==0) {
					continue;
				}

				for (int secondNum: secondNumList) {
					Integer[] sol = new Integer[3];
					sol[0]=firstNum;
					sol[1]=secondNum;
					sol[2]=0-firstNum-secondNum;
					Arrays.sort(sol);
					result.add(Arrays.asList(sol));
				}
				visited.add(firstNum);
			}

		}
		return result;
	}

	public static List<Integer> twoSum(int[] numList, int begin, int sum,Set<Integer> exclude){
		Set<Integer> visited = new HashSet<Integer>();
		List<Integer> secondNumList = new LinkedList<Integer>();
		for (int i = begin; i < numList.length; i++) {
			if (!visited.contains(numList[i])) {
				if(exclude.contains(numList[i])||exclude.contains(sum-numList[i])||
						visited.contains(sum-numList[i])) continue;
				
				if (hasNum(numList, i+1, sum-numList[i])) {
					secondNumList.add(numList[i]);
				}
				visited.add(numList[i]);
			}

		}
		return secondNumList;

	}
	public static boolean hasNum(int[] numList, int begin, int num){
		for (int i=begin;i<numList.length;i++) {
			if (numList[i]==num) {
				return true;
			}
		}
		return false;
	}



	public static void main(String[] args) {
		int[] num=new int[]{-1,0,1,0,0};
		for (List<Integer> list: threeSum(num)) {
			System.out.println(list);
		}

	}

}
