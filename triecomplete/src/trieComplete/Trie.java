package trieComplete;

import java.util.Map;

public class Trie {
	
	public TrieNode root;
	public String trieName;
	public int size;
	
	public Trie(String name){    
		root = new TrieNode('0',null); 
		trieName =name;
		size = 0;
	}    
	   
    //insert a new word to Trie
	//return false if name is invalid 
    public boolean insert(String name, int index)  {
    	//white spaces ignored, lowercased
    	if(name ==null) 
    		return false;
    	
        String word = name.replace(" ", "");
        int len = word.length(); 

        TrieNode curNode = root;
           
        for( int k = 0; k < len; k++)
        {
        	char ch = word.charAt(k);
            Map<Character,TrieNode> children = curNode.getChildren();            
            
            // If there is already a child for current character of given word 
            if( children.containsKey(ch)){
                curNode = children.get(ch);
            }
            // Else create a child
            else{              
                TrieNode temp = new TrieNode(ch,curNode);
                children.put(ch, temp);
                curNode = temp;
                size++;
            }
        }
        // Set leaf for last letter
        curNode.setLeaf();
        curNode.setIntValue(index);
        return true;
    }
    
    public boolean delete(String name){
    	if(name ==null) return false;
        String word = name.replace(" ", "");
        
        int len = word.length(); 
        if(len==0) return false;
        
    	TrieNode node=SearchFor(name);
    	if(node ==null) return false;
    	removeNode(node);
		return true;
    }
    
    public void removeNode(TrieNode node){

    	TrieNode parentNode = node.getParent();
    	if (node.getLetter()=='0' && parentNode == null) {
			return;
		}
    	parentNode.getChildren().remove(node.getLetter());
    	size--;
    	if ( parentNode.getChildren().isEmpty()) {
			removeNode(parentNode);
		}
    	
    }
    
    
    public TrieNode SearchFor(String word)  {
        
        int len = word.length();      
          
        // traverse down a Trie
        TrieNode curNode = root;   
          
        // Iterate through all chars of input string
        for( int k = 0 ; k < len; k++ )
        {    
            //current character of the word
            char ch = word.charAt(k);     
            Map<Character,TrieNode> children = curNode.getChildren();                        
            if( children.containsKey(ch) )
            {
               curNode = children.get(ch); //move down in Trie
            }
            else  return null;
        }
        return curNode;
    }
    


    public String toString(){

    	return trieName+"\t size: "+size+"\n"+root.toString(0)+"\n";
    }
     
}


