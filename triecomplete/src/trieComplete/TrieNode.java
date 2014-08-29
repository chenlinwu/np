package trieComplete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;


public class TrieNode {
	private char letter;    //key
	private boolean isLeaf; 
	private List<Integer> intValues; //empty if not leaf
	private Map<Character,TrieNode> children;
	private TrieNode parent;

	public TrieNode(char ch, TrieNode parent)  {
		letter = ch;
		children = new HashMap<Character,TrieNode>();
		isLeaf = false;
		intValues = new LinkedList<Integer>();
		this.parent = parent;
	}    

	/** 
	 * find at most max number descendant leaves of this node
	 * uses DFS
	 */

	public List<Integer> DFcomplete(int max){
		
		int nResults = 0;
		List<Integer> results = new ArrayList<Integer>();
		if(isLeaf){//find a leaf
			for (int i : intValues) {
				results.add(i);
				nResults++;
				if(nResults==max) return results;
			}
		}

		for(Entry<Character,TrieNode> e : children.entrySet()){
			List<Integer> subRt = e.getValue().DFcomplete(max-nResults);
			results.addAll(subRt);
			nResults += subRt.size();
			if(nResults == max) break;
		}
		return results;   
	} 

	public List<Integer> BFComplete(int max){
		int nResults = 0;
		List<Integer> results = new ArrayList<Integer>();
		
		if(isLeaf){//if this is a leaf
			for (int i : intValues) {
				results.add(i);
				nResults++;
				if(nResults==max) return results;
			}
		}
		Queue<TrieNode> q = new LinkedList<TrieNode>();
		q.add(this);
		while(!q.isEmpty()){
			TrieNode u =  q.poll();
			for(Entry<Character,TrieNode> v : u.children.entrySet()){

				if (v.getValue().isLeaf) {
					for (int i : v.getValue().getIntValues()) {
						results.add(i);
						nResults++;
						if(nResults==max) return results;
					}
				}
				q.add(v.getValue());
			}
		}
		return results;
	}

	//getters and setters
	public Map<Character,TrieNode> getChildren() { 
		return children;  
	}    
	public char getLetter(){  
		return letter;   
	}    
	public void setLeaf(){   
		isLeaf=true;     
	}    
	public boolean isLeaf()                           {  
		return isLeaf;   
	}
	public void setIntValue(int value){
		intValues.add(value);
	}
	public TrieNode getParent() {
		return parent;
	}
	public List<Integer> getIntValues() {
		return intValues;
	}

	// print the subTree rooted by the node
	public String toString(int level){

		StringBuffer buffer=new StringBuffer();
		if (isLeaf) {
			buffer.append((char)(letter));
		}
		else{
			buffer.append(letter);
		}
		buffer.append("--");
		if(!children.keySet().isEmpty()) {
			boolean isFirstChild= true;
			for(Entry<Character, TrieNode> e: children.entrySet()){
				if(!isFirstChild) {
					buffer.append("\n");
					for(int i=0; i<level;i++){
						buffer.append("|  ");
					}
					buffer.append("|--");
				}
				buffer.append(e.getValue().toString(level+1));
				isFirstChild = false;

			}
		}

		return buffer.toString();
	}

}



