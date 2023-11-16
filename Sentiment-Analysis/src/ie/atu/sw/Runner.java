package ie.atu.sw;

import java.io.*;
import java.util.*;

public class Runner {

	public static void main(String[] args) throws Exception {
		createFolderAndFile("out", "example.txt");//create output folder
		menu();		
		System.out.println("END");
/*		
		//You may want to include a progress meter in you assignment!
		System.out.print(ConsoleColour.YELLOW);	//Change the colour of the console text
		int size = 100;							//The size of the meter. 100 equates to 100%
		for (int i =0 ; i < size ; i++) {		//The loop equates to a sequence of processing steps
			printProgress(i + 1, size); 		//After each (some) steps, update the progress meter
			Thread.sleep(10);					//Slows things down so the animation is visible 

		}
*/
	}
	

	public static void createFolderAndFile(String folderName, String fileName) {
        // Create a File object for the folder
        File folder = new File(folderName);

        // Create the folder if it doesn't exist
        if (!folder.exists()) {
            boolean folderCreated = folder.mkdir();
            if (folderCreated) {
                System.out.println("Folder created successfully.");
            } else {
                System.err.println("Unable to create the folder.");
                return;
            }
        } else {
            System.out.println("Folder already exists.");
        }

        // Create a File object for the text file inside the folder
        File file = new File(folder, fileName);

        // Create the text file
        try (FileWriter writer = new FileWriter(file)) {
            // Write some content to the file
            writer.write("*Output*\n");

            System.out.println("Text file created successfully.");
        } catch (IOException e) {
            System.err.println("Unable to create the text file: " + e.getMessage());
        }
    }
	
	
	
	/*
	 *  Terminal Progress Meter
	 *  -----------------------
	 *  You might find the progress meter below useful. The progress effect 
	 *  works best if you call this method from inside a loop and do not call
	 *  System.out.println(....) until the progress meter is finished.
	 *  
	 *  Please note the following carefully:
	 *  
	 *  1) The progress meter will NOT work in the Eclipse console, but will
	 *     work on Windows (DOS), Mac and Linux terminals.
	 *     
	 *  2) The meter works by using the line feed character "\r" to return to
	 *     the start of the current line and writes out the updated progress
	 *     over the existing information. If you output any text between 
	 *     calling this method, i.e. System.out.println(....), then the next
	 *     call to the progress meter will output the status to the next line.
	 *      
	 *  3) If the variable size is greater than the terminal width, a new line
	 *     escape character "\n" will be automatically added and the meter won't
	 *     work properly.  
	 *  
	 * 
	 */
	
	public static void menu() {
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		Lexicon lexicon = new SimpleLexicon("./lexicons/afinn.txt");//sets default lexicon
		File folderTweets = new File("./tweets");
		FileMenuSystem tweetsMenu = new FileMenuSystem();
		
		int option = 0;
	while(option != -1) {
		System.out.println(ConsoleColour.WHITE);
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*             Virtual Threaded Sentiment Analyser          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify a Text File");
		System.out.println("(2) Specify a URL");
		System.out.println("(3) Specify an Output File (default: ./out.txt)");
		System.out.println("(4) Configure Lexicons");
		System.out.println("(5) Execute, Analyse and Report");
		System.out.println("(?) Optional Extras...");
		System.out.println("(-1) Quit");
		
		//Output a menu of options and solicit text from the user
				System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
				System.out.print("Select Option [1-4]>");
				option = myObj.nextInt();  // Read user input
				System.out.println();
				
				
				switch (option) {
				case 1: {
					System.out.println("************************************************************");
					System.out.println("*     		(1) Specify a Text File						   *");
					System.out.println("************************************************************");
					System.out.println();
					tweetsMenu.setFolder(folderTweets);
			        tweetsMenu.displayMenu();
					break;
				}
				case 2:{
					System.out.println("************************************************************");
					System.out.println("*     		(2) Specify a URL							   *");
					System.out.println("************************************************************");
					System.out.println();
					
					break;
				}
				case 3:{
					System.out.println("************************************************************");
					System.out.println("*     (3) Specify an Output File (default: ./out.txt)	   *");
					System.out.println("************************************************************");
					System.out.println();
					
					break;
				}
				case 4:{
					System.out.println("************************************************************");
					System.out.println("*  		   (4) Configure Lexicons	 					   *");
					System.out.println("************************************************************");
					System.out.println();
					break;
					
				}
				case 5:{
					System.out.println("************************************************************");
					System.out.println("*     		(5) Execute, Analyse and Report				   *");
					System.out.println("************************************************************");
					System.out.println();
			        
			        int choice = 0;
			        
			        while (choice != 2) {
			            System.out.println("Menu:");
			            System.out.println("1. Execute");
			            System.out.println("2. Exit");
			            System.out.print("Enter your choice: ");

			            choice = myObj.nextInt();
			            myObj.nextLine(); // Consume the newline character

			            switch (choice) {
			                case 1:
								List<String> tweets = tweetsMenu.getCurrentText();
						        SentimentAnalysis sentimentAnalysis = new SentimentAnalysis(lexicon, tweets);
						        double overallSentiment = sentimentAnalysis.analyzeSentiment();
						       System.out.println("*****************************************");
						       System.out.println("* Tweets Name: "+tweetsMenu.getCurrentTextFile().getName());
						       System.out.println("* Lexicon Name: "+lexicon.toString());
						        System.out.println("* Overall Sentiment: " + roundToDecimalPlaces(overallSentiment, 3)+" *");
						        System.out.println("* Average Sentiment: "+ roundToDecimalPlaces(overallSentiment/tweets.size(),3)+" *");
						        System.out.println("*****************************************");
			                    break;
			                case 2:
			                    System.out.println("Exiting the program. Goodbye!");
			                    break;
			                default:
			                    System.out.println("Invalid choice. Please try again.");
			            }
			        }
			        
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + option);
				}
	}
		
	}
	
	
	public static void printProgress(int index, int total) {
		if (index > total) return;	//Out of range
        int size = 50; 				//Must be less than console width
	    char done = '█';			//Change to whatever you like.
	    char todo = '░';			//Change to whatever you like.
	    
	    //Compute basic metrics for the meter
        int complete = (100 * index) / total;
        int completeLen = size * complete / 100;
        
        /*
         * A StringBuilder should be used for string concatenation inside a 
         * loop. However, as the number of loop iterations is small, using
         * the "+" operator may be more efficient as the instructions can
         * be optimized by the compiler. Either way, the performance overhead
         * will be marginal.  
         */
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
        	sb.append((i < completeLen) ? done : todo);
        }
        
        /*
         * The line feed escape character "\r" returns the cursor to the 
         * start of the current line. Calling print(...) overwrites the
         * existing line and creates the illusion of an animation.
         */
        System.out.print("\r" + sb + "] " + complete + "%");
        
        //Once the meter reaches its max, move to a new line.
        if (done == total) System.out.println("\n");
    }
	
    private static double roundToDecimalPlaces(double value, int decimalPlaces) {
        if (decimalPlaces < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, decimalPlaces);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}