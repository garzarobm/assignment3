package assignment3;

import java.util.*;

public class DictGraph {
	//new comment
	ArrayList<String> words;
	HashMap<String, ArrayList<String>> neighbors;
	
	public DictGraph(Set<String> dict) {
		
		words = new ArrayList<String>();
		neighbors = new HashMap<String, ArrayList<String>>();
		
		
		buildGraphNeighborhood(dict);
	}

	private void buildGraphNeighborhood(Set<String> dict) {
		for(String s: dict)
			neighbors.put(s, getNeighbors(s, dict));
	}

	private ArrayList<String> getNeighbors(String curr, Set<String> dictionary)
	{
		ArrayList<String> result = new ArrayList<>();
		for(String mutation: generateMutations(curr))
			if(dictionary.contains(mutation.toUpperCase()))
				result.add(mutation);
		return result;
	}
	private ArrayList<String> generateMutations(String curr) {
		ArrayList<String> result = new ArrayList<>();
		for(int i=0;i<curr.length();i++) {
			
			for(int j='a';j<='z';j++) {
				
				char[] curr_chars = curr.toLowerCase().toCharArray();
				curr_chars[i] = (char) j;
				
				String temp = new String(curr_chars);
				
				if(! temp.equals(curr.toLowerCase()))
					result.add(temp);	
				
			}
		}
		return result;
	}
		
}
