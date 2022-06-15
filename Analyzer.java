/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <Enter all external resources and collaborations here.>
 *
 * Note external code may reduce your score but appropriate citation is required
 * to avoid academic integrity violations. Please see the Course Syllabus as
 * well as the university code of academic integrity:
 *    https://catalog.upenn.edu/pennbook/code-of-academic-integrity/
 *
 * Signed,
 * Author: Zhaoqin Wu
 * Penn email: zhaoqinw@seas.upenn.edu>
 * Date: 2022-06-14
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Analyzer {
    /*
     * This method takes as input a (nullable) filename, reads the given file from the filesystem
     * and returns a non-null List of Sentence objects parsed from the valid lines of the file in the order in which they are encountered. 
     * Invalid lines should be ignored and not entered into the output list.
     */
    public static List<Sentence> readFile(String filename) {
    	if(filename == null) {return new ArrayList<Sentence>();}
    	
    	List<Sentence> Senlists = new ArrayList<Sentence>();
    	File f = new File(filename);
    	String regex = "^(?<score>[+-]?[0-2])\\s(?<text>.*)$";
    	Scanner scr;
    	
    	
    	try {
    		scr = new Scanner(f);
    		while(scr.hasNextLine()) {
    			String line = scr.nextLine();
    			if(line.matches(regex)) {
    				String[] splitted = line.split("\\s", 2);
    				int score = Integer.parseInt(splitted[0]);
    				String text = splitted[1];
    				Sentence newsentence = new Sentence(score,text.trim());
    				Senlists.add(newsentence);
    			}  			
    		}
    		scr.close();
    		
    	} catch(FileNotFoundException e) {
    		return new ArrayList<Sentence>();
    	}
    	
        return Senlists;
    }

    /*
     * This method takes as input a (nullable) List of (nullable) Sentence objects and outputs a (non-null) List containing every word,
     * converted to lowercase, encountered in the input List in the order in which it was encountered.
     * Null Sentence objects and invalid words (i.e. tokens not starting with a letter) should both be ignored.
     * If the input parameter is null, the output should be an empty List. You may select any implementation of java.util.List for the output.
     */
    public static List<String> allOccurrences(List<Sentence> sentences) {
    	List<String> words = new ArrayList<String>();
    	if(sentences == null || sentences.size() == 0) {return new ArrayList<String>();}
    	
    	for(Sentence sen: sentences) {
    		if(sen!=null) {
    			String[] strs = sen.getText().split("\\s");
    			for(String token: strs) {
    				if(Character.isLetter(token.charAt(0))) {
    					words.add(token.toLowerCase());
    				}
    				                                      
    			}
    			                      
    	
    		}
    	}

        return words;
    }

    /*
     * This method is identical to Analyzer.allOccurrences, except that the output should
     * be in the form of any implementation of java.util.Set;
     * in other words, the output will not have duplicates and insertion order need not be preserved.
     */
    public static Set<String> uniqueWords(List<Sentence> sentences) {
    	Set<String> words = new HashSet<String>();
    	if(sentences == null || sentences.size() == 0) {return new HashSet<String>();}
    	
    	for(Sentence sen: sentences) {
    		if(sen!=null) {
    			String[] strs = sen.getText().split("\\s");
    			for(String token: strs) {
    				if(Character.isLetter(token.charAt(0))) {
    					words.add(token.toLowerCase());
    				}
    				                                      
    			}
    			                      
    	
    		}
    	}

        return words;
    }

    /*
     * This method takes as input a (nullable) List of (nullable) Sentence objects and outputs a non-null Map, 
     * whose keys are the (valid, lowercased) words in each input Sentence and whose values are the final context scores, 
     * represented by ObservationTally, for that word. If the input List of Sentences is null or empty, the method should return an empty Map. 
     * If a Sentence object in the input List is null or if the text of a Sentence is null, 
     * this method should ignore it and continue processing the remaining Sentences. You may return any implementation of java.util.Map
     */
    public static Map<String, ObservationTally> wordTallies(List<Sentence> sentences) {
    	if(sentences == null || sentences.isEmpty()) {return new HashMap<String, ObservationTally>();}
    	Map<String, ObservationTally> words= new HashMap<String, ObservationTally>();
    	for(Sentence sen: sentences) {
    		if(sen!=null && sen.getText() !=null) {
    			String[] strs = sen.getText().split("\\s");
    			for(String token: strs) {
    				if(Character.isLetter(token.charAt(0))) {
    					if(words.containsKey(token)) {
    						ObservationTally update = words.get(token);
    						update.increaseTotal(sen.getScore());
    						words.put(token, update);		
    					}
    					else if (!words.containsKey(token)) {
    						ObservationTally obs = new ObservationTally(1,sen.getScore());
        					words.put(token, obs);  	
                        }				
    				}
    			} 			
    		}
    	}
    	
        return words;
    }


    /*
     * This method takes a (nullable) Map from (non-null) word to (non-null) ObservationTally and outputs a non-null Map 
     * with the original word as key and the word’s average sentiment score as value. If the input Map is null, 
     * return an empty Map. You may return any implementation of java.util.Map.
     */
    public static Map<String, Double> calculateScores(Map<String, ObservationTally> tallies) {
        if(tallies == null || tallies.isEmpty()) {return new HashMap<String, Double>();}
        Map<String, Double> map = new HashMap<String, Double>();
    	
    	if(tallies != null && !tallies.isEmpty()) {
    		for(Entry<String, ObservationTally> entry: tallies.entrySet()) {
    			map.put(entry.getKey(),entry.getValue().calculateScore());
    		}
    	}
    	
        return map;
    }

    /*
     * This method takes as input a (nullable) Map from (non-null) words to (non-null) sentiment scores as well as an arbitrary statement text and, 
     * using the sentiment scores for each word in this Map, outputs the sentiment score for the given statement text, 
     * which is the arithmetic mean score of all its (valid) words.
     */
    public static double calculateSentenceScore(Map<String, Double> wordScores, String text) {
       if(wordScores == null || wordScores.size() == 0 || text == null || text.isEmpty()) {
        	return 0;
        }
        
        double score = 0;
        int count = 0;
        
        String[] tokens = text.toLowerCase().split("\\s");
        for (String token : tokens) {
        	if(Character.isLetter(token.charAt(0))) {
        		if (wordScores.containsKey(token)) {
        			score += wordScores.get(token);
        		}
        		count++;
        	}
        }
        
        
        return score/(double)count;
    }

    /*
     * You do not need to modify this code but can use it for testing your program!
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify the name of the input file");
            return;
        }
        String filename = args[0];

        System.out.printf("Processing input from \"%s\".\n", filename);
        List<Sentence> sentences = Analyzer.readFile(filename);
        System.out.printf("%5d sentences read.\n", sentences.size());

        var aWords = allOccurrences(sentences);
        System.out.printf("%8d total words found.\n", aWords.size());

        Set<String> uWords = Analyzer.uniqueWords(sentences);
        System.out.printf("%8d unique words found.\n", uWords.size());

        Map<String, ObservationTally> tallies = wordTallies(sentences);
        System.out.printf("This should be the same number as the unique word count: %8d.\n", tallies.size());

        Map<String, Double> wordScores = Analyzer.calculateScores(tallies);

        String scoreAnother = "yes";
        Scanner in = new Scanner(System.in);
        while (scoreAnother.equals("yes")) {
            System.out.print("Please enter a sentence: ");
            System.out.flush();
            String sentence = in.nextLine();
            double score = Analyzer.calculateSentenceScore(wordScores, sentence);
            System.out.println("The sentiment score is " + score);

            boolean gotValidResponse = false;
            while (!gotValidResponse) {
                System.out.print("\nWould you like to score another sentence [yes/no]: ");
                System.out.flush();

                scoreAnother = in.nextLine().toLowerCase();
                switch (scoreAnother) {
                    case "yes":
                    case "no":
                        gotValidResponse = true;
                        break;
                    default:
                        System.out.println("Invalid response: " + scoreAnother);
                        gotValidResponse = false;
                }
            }
        }
        in.close();
    }
}
