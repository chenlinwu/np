package NQueens;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Solution {
	static int N=0;
	public static List<String[]> solveNQueens(int n) {
		N=n;
		List<int[]> sols = new LinkedList<int[]>();
		List<String[]> results= new LinkedList<String[]>();
		//for every row
		solveRow(new char[N][N], 0, new int[N], sols);

		for (int[] intResult:sols) {
			//one int transform to one string
			String[] stringResult= new String[N];
			for (int i = 0; i < intResult.length; i++) {
				char[] rowCharArray = new char[N];
				for (int j = 0; j < N; j++) {
					rowCharArray[j]='.';
				}
				rowCharArray[intResult[i]]='Q';
				String rowString = new String(rowCharArray);
				stringResult[i]=rowString;
			}
			results.add(stringResult);
		}

		return results;	

	}
	static int Nsols=0;
    public static int totalNQueens(int n) {
        N=n;

		solveRow2(new char[N][N], 0, new int[N]);
		return Nsols;	
    }
	public static void solveRow2(char[][] board, int row, int[] rec) {
		if(board.length==0) {
			Nsols++;
			return;
		}
		for (int j = 0; j < N; j++) {
			if (board[0][j]!='.') {
				rec[row] = j;
				char[][] nextBoard = clear(board,j);
				solveRow2(nextBoard,row+1,rec);
			}
		}
	}
	
	public static void solveRow(char[][] board, int row, int[] rec, List<int[]> sols){
		if(board.length==0) {
			int[] sol = Arrays.copyOf(rec, N);
			sols.add(sol); 
			return;
		}
		for (int j = 0; j < N; j++) {
			if (board[0][j]!='.') {
				rec[row] = j;
				char[][] nextBoard = clear(board,j);
				solveRow(nextBoard,row+1,rec,sols);
			}
		}
	}


	public static char[][] clear(char[][] board, int col){
		int nRows = board.length-1;
		char[][] newBoard = new char[nRows][N];
		for(int i=0; i<nRows; i++){
			for(int j=0; j<N; j++){
				newBoard[i][j]=board[i+1][j];
			}
			//clear col
			newBoard[i][col]='.';
			//clear diagonal
			int shift = i+1;
			if(col-shift >= 0) {
				newBoard[i][col-shift]='.';
			}
			if(col+shift < N) {
				newBoard[i][col+shift]='.';
			}

		}
		return newBoard;

	}
	 
	public static void main(String[] args) {
		int n=5;
		System.out.println(totalNQueens(n));
		List<String[]> ansList = solveNQueens(n);
		for (String[] strings:ansList) {
			for (int i = 0; i < strings.length; i++) {
				System.out.println(strings[i]);
			}
			System.out.println();
		}
		

	}

}
