/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Numan Gilani
 * ng22457
 * 16345
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Git URL:
 * Fall 2018
 */


package assignment3;
import java.util.*;
import java.io.*;

public class Main {
	
	// static variables and constants only here.
	static public DictGraph dict_graph;
	
	public static void main(String[] args) throws Exception {
		
		Scanner kb;	// input Scanner for commands
		PrintStream ps;	// output file, for student testing and grading only
		// If arguments are specified, read/write from/to files instead of Std IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps);			// redirect output to ps
		} else {
			kb = new Scanner(System.in);// default input from Stdin
			ps = System.out;			// default output to Stdout
		}
		initialize();
		
		// TODO methods to read in words, output ladder
		ArrayList<String> input = new ArrayList<String>();
		input = parse(kb);
		
		if(input == null) return;
		
		while(!input.get(0).equals("/quit") && !input.get(1).equals("/quit")) {
			
			//ArrayList<String> ladder = getWordLadderDFS(input.get(0),input.get(1));
			ArrayList<String> ladder = getWordLadderBFS(input.get(0), input.get(1));
			printLadder(ladder);
			input = parse(kb);
			
			if(input == null) break;
			
		}
	}
	
	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests.  So call it 
		// only once at the start of main.
				
		Set<String> dict = makeDictionary();
		dict_graph = new DictGraph(dict);
		
		
		
	}
	
	/**
	 * @param keyboard Scanner connected to System.in
	 * @return ArrayList of Strings containing start word and end word. 
	 * If command is /quit, return empty ArrayList. 
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		// TO DO
		
		String first_word = keyboard.next();
		
		if(first_word.equals("/quit")) {
			return null;
		}
		
		String second_word = keyboard.next();
		
		if(second_word.equals("/quit")) {
			return null;
		}
		
		
		ArrayList<String> words = new ArrayList<String>();
		
		words.add(first_word);
		words.add(second_word);
		
		
		
		return words;
	}
	
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		// Returned list should be ordered start to end.  Include start and end.
		// If ladder is empty, return list with just start and end.
		// TODO some code
		
		start = start.toLowerCase();
		end = end.toLowerCase();
		
		HashSet<String> checkedValues = new HashSet<String>();
		ArrayList<String> ladder = new ArrayList<String>();
		ladder = recursiveDFS(checkedValues, start, end);
		if (ladder == null) {
			ArrayList<String> failedCase = new ArrayList<String>();
			failedCase.add(start);
			failedCase.add(end);
			return failedCase;
		}
		else {
		    Collections.reverse(ladder);
		    return ladder;
		}
		
	}

	
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
    	
    	start = start.toLowerCase();
		end = end.toLowerCase();
    	
    	HashMap<String, String> previous = new HashMap<String, String>();
    	ArrayList<String> words = new ArrayList<String>(dict_graph.neighbors.keySet());
    	int sizeOfDict = dict_graph.neighbors.size();
    	
    	for(int i = 0; i < sizeOfDict; i++) {
    		previous.put(words.get(i), "");
    	}
        	
    	ArrayList<String> queue = new ArrayList<String>();
    	HashSet<String> visited = new HashSet<String>();
    	ArrayList<String> ladder = new ArrayList<String>();
    	boolean done = false;
    	boolean found = false;
    	
    	queue.add(start);
    	visited.add(start);
    	
    	while(!queue.isEmpty()) {
    		
    		String temp = queue.remove(0);
    		
    		   	
        	for(String n: dict_graph.neighbors.get(temp.toUpperCase())){
        		
        		if(!visited.contains(n)) {
    			visited.add(n);
    			previous.put(n, temp);
    			queue.add(n);
        		}
        		
        		if (n.equals(end)) {
        			found = true;
        			done = true;
        			break;
        		}
        		        		
        	}
        	
        	if(done == true) {
        	break;
        	}
        	
    	}
    	
    	
    	String current = new String(end);
    	
    	while(current != start && found == true) {
    		ladder.add(current);
    		current = previous.get(current);
    	}
    	
    	ladder.add(current);
    	
    	Collections.reverse(ladder);
    	
    	if(found == true) {
    		return ladder;
    	}else {
    		ArrayList<String> failedCase = new ArrayList<String>();
			failedCase.add(start);
			failedCase.add(end);
			return failedCase;
    	}
    	
    }
    	
    
	//Method to print ladder
	public static void printLadder(ArrayList<String> ladder) {
		
		int rung_num = ladder.size() - 2;
		
		if(rung_num == 0 && !dict_graph.neighbors.get(ladder.get(0).toUpperCase()).contains(ladder.get(1))) {
			System.out.println("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(1) + ".");
			return;
		}else {
			System.out.println("a " + rung_num + "-rung word ladder exists between " + ladder.get(0) + " and " + ladder.get(ladder.size()-1) + ".");
		}
		
		for(int i=0;i<ladder.size();i++) {
			System.out.println(ladder.get(i));
		}
	}
	// TODO
	// Other private static methods here

	//recursive helper function for DFS algorithm
	public static ArrayList<String> recursiveDFS( HashSet<String> a, String start, String end){
		a.add(start);
		if(start.equals(end)) {
			
			ArrayList<String> done = new ArrayList<String>();
			done.add(end);
			return done;
		}
		for(String neighbor: dict_graph.neighbors.get(start.toUpperCase())) {
			ArrayList<String> local = new ArrayList<String>();
			if(a.contains(neighbor)) {
				continue;
			}
			local = recursiveDFS(a, neighbor, end);
			if (local != null) {
				local.add(start);
				return local;
			}
			
		}
		
		return null;
				
	}
	
	//method to get unvisited neighbor for word start, used in DFS algorithm
	public static String getUnvisitedNeighbor(HashSet<String> a, String start) {
		
		for(int i = 0; i < dict_graph.neighbors.get(start.toUpperCase()).size(); i++) {
			String temp = dict_graph.neighbors.get(start.toUpperCase()).get(i);
			if (!a.contains(temp)) {
				return temp;
			}
			
		}
		return null;
		
	}

	/* Do not modify makeDictionary */
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}
