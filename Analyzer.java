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
     * Implement this method in Part 4
     */
    public static Map<String, ObservationTally> wordTallies(List<Sentence> sentences) {
    	if(sentences == null || sentences.isEmpty()) {return new HashMap<String, ObservationTally>();}
    	for(Sentence sen: sentences) {
    		if(sen!=null || sen.getText() !=null) {
    			
    			
    		}
    	}
    	
    	
        return null;
    }


    /*
     * Implement this method in Part 5
     */
    public static Map<String, Double> calculateScores(Map<String, ObservationTally> tallies) {
        return null;
    }

    /*
     * Implement this method in Part 6
     */
    public static double calculateSentenceScore(Map<String, Double> wordScores, String text) {
        return 0;
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
