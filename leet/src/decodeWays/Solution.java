package decodeWays;

public class Solution {

	public static int numDecodings(String s) {
		if(s==null) return 0;
		int len = s.length();
		if(len==0 ) return 0;

		if(s.charAt(0)==48) return 0;

		int[] maxRes = new int[len+1];

		int i=0;
		maxRes[i++]=1;
		maxRes[i++]=1;
		for(;i<=len;i++){
			if(s.charAt(i-1)==48){
				if(has2Mappings(s.charAt(i-2), s.charAt(i-1))){
					maxRes[i]=maxRes[i-2];
				}else{
					return 0;
				}
			}else{
				if(has2Mappings(s.charAt(i-2), s.charAt(i-1))){
					maxRes[i]=maxRes[i-1]+maxRes[i-2];
				}
				else maxRes[i]=maxRes[i-1];
			}

		}
		/*
		if(s.charAt(len-1)==48){
			return maxRes[i-2];
		}
		*/
		return maxRes[--i];

	}
	private static boolean has2Mappings(char fst, char sec){
		int n = (fst-48)*10+sec-48;
		return (n<=26 && n>=10);
	}
	public static void main(String[] args) {
		String s = "210202110";
		System.out.println(numDecodings(s));
	}
}