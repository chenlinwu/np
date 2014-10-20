package maxProfit;

import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.List;

public class Solution {
	
	static List<Integer> buys = new ArrayList<Integer>();
	static List<Integer> sells = new ArrayList<Integer>();
	
	public static int maxProfit(int[] prices) {
		int max=0;
		if (prices.length<2) {
			return max;
		}

		if(prices[0]<prices[1]) buys.add(prices[0]);
		for (int i = 1; i < prices.length-1; i++) {
			if (prices[i]<=prices[i-1] && prices[i]<prices[i+1]) {
				buys.add(prices[i]);
			}
			else if(prices[i]>prices[i-1] && prices[i]>=prices[i+1]){
				sells.add(prices[i]);
			}
		}
		if (prices[prices.length-1]>prices[prices.length-2]) {
			sells.add(prices[prices.length-1]);
		}
		if (buys.size()!=sells.size()) {
			System.out.print("size doesn't match");
			return max;
		}
		int[] priceDiff = new int[buys.size()];
		for (int i = 0; i < buys.size(); i++) {
			priceDiff[i]=sells.get(i)-buys.get(i);
		}
		if(buys.size()<=2){
			for (int i = 0; i < priceDiff.length; i++) {
				max+=priceDiff[i];
			}
			return max;
		}
		else{
			int[] max1 = new int[buys.size()-1];
			max1[0]=priceDiff[0];
			for (int i = 1; i < max1.length; i++) {
				//max profit selling at i
				max1[i]=Math.max(priceDiff[i], max1[i-1]+sells.get(i)-sells.get(i-1));			
			}
			int[] max2_ = new int[buys.size()-1];
			int[] max2 = new int[buys.size()-1];
			max2_[max2_.length-1]=priceDiff[priceDiff.length-1];
			max2[max2.length-1] = max2_[max2_.length-1];
			for (int i = max2_.length-2; i >= 0; i--) {
				//max profit buying at i
				max2_[i]=Math.max(priceDiff[i+1], max2_[i+1]+buys.get(i+2)-buys.get(i+1));
				//max profit buying at i or larger
				max2[i]=Math.max(max2_[i],max2[i+1]);
			
			}
			for (int i = 0; i < max2.length; i++) {
				if (max1[i]+max2[i]>max) {
					max=max1[i]+max2[i];
				}
			}

			
			
			
		}
		return max;
        
        
    }
//	public static int extendlow(int[] diffs, int lowIndex ,int from){
//		int newLow = lowIndex;
//		for (int i = from; i < lowIndex; i++) {
//			if (buys.get(i) < buys.get(lowIndex)) {
//				newLow=i;
//			}
//		}
//		return newLow;
//	}
//	
//	public static int extendhigh(int[] diffs, int highIndex,int to){
//		int newHigh = highIndex;
//		for (int i = highIndex; i < to; i++) {
//			if (sells.get(i) > sells.get(highIndex)) {
//				newHigh=i;
//			}
//		}
//		return newHigh;
//	}
	
	

	public static void main(String[] args) {
		int[] prices=new int[]{1,4,7,5,6,2,5,1,9,7,9,7,0,6,8
				};
		System.out.println(maxProfit(prices));
		

	}

}
