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
		
		while(!input.get(0).equals("/quit") && !input.get(1).equals("/quit")) {
			
			ArrayList<String> ladder = getWordLadderDFS(input.get(0),input.get(1));
			printLadder(ladder);
			input = parse(kb);
			
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
		
		if(first_word.equals("/Quit")) {
			return new ArrayList<String>();
		}
		
		String second_word = keyboard.next();
		
		if(second_word.equals("/Quit")) {
			return new ArrayList<String>();
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
		
		
		
//		if(start == null){
//			ArrayList<String> nullCase = new ArrayList<String>();
//			nullCase.add("exception");
//			
//			return nullCase;
//		}
//		if(start.equals(end)) {
//			b.add(end);
//			return b;
//		}
//			a.add(start);
//			String next = getUnvisitedNeighbor(a, start);
//			
//			ArrayList<String> temp = recursiveDFS(a, b, next, end);
//			if(temp == null) {
////				next = getUnvisitedNeighbor
////				return temp;
//			}
//			return temp;
	
		
		
	}
	public static String getUnvisitedNeighbor(HashSet<String> a, String start) {
		
		for(int i = 0; i < dict_graph.neighbors.get(start.toUpperCase()).size(); i++) {
			String temp = dict_graph.neighbors.get(start.toUpperCase()).get(i);
			if (!a.contains(temp)) {
				return temp;
			}
			
		}
		return null;
		
	}
    public static ArrayList<String> getWordLadderBFS(String start, String end) {
		
		// TODO some code
		//Set<String> dict = makeDictionary();
		// TODO more code
		
		return null; // replace this line later with real return
	}
    
	
	public static void printLadder(ArrayList<String> ladder) {
		
		for(int i=0;i<ladder.size();i++) {
			System.out.println(ladder.get(i));
		}
	}
	// TODO
	// Other private static methods here


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
