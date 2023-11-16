package ie.atu.sw;

import java.io.*;
import java.util.*;

public class SimpleLexicon implements Lexicon {
	
	private Map<String, Integer> lexiconData;
	private String filePath;
	
    public SimpleLexicon(String filePath) {
    	this.filePath = filePath;
        lexiconData = new HashMap<>();
        initializeLexiconData(filePath); // Initialize lexicon data with default values or load from a file/database.
    }
    
    
    
    @Override
	public String toString() {
		return filePath;
	}



	private void initializeLexiconData(String filePath) {
        // Try loading from a default file
        if (!loadFromDefaultFile(filePath)) {
            // If loading from the default file fails, prompt the user to specify a file
            loadFromUserSpecifiedFile();
        }
    }
    
    private boolean loadFromDefaultFile(String filePath) {
        String defaultFilePath = filePath; // Adjust the default file path as needed

        try (BufferedReader reader = new BufferedReader(new FileReader(defaultFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String word = parts[0].trim().toLowerCase();
                    int score = Integer.parseInt(parts[1].trim());
                    lexiconData.put(word, score);
                }
            }
            return true; // Successfully loaded from the default file
        } catch (IOException | NumberFormatException e) {
            // Handle exceptions, or simply ignore and return false
            return false;
        }
    }
    
    private void loadFromUserSpecifiedFile() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the path of the lexicon file:");
        String filePath = scanner.nextLine().trim();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String word = parts[0].trim().toLowerCase();
                    int score = Integer.parseInt(parts[1].trim());
                    lexiconData.put(word, score);
                }
            }
            System.out.println("Lexicon loaded successfully from the specified file.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading lexicon from the specified file: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

	@Override
	public int getSentimentScore(String word) {
		return lexiconData.getOrDefault(word.toLowerCase(), 0);
	}

	@Override
	public void addWord(String word, int score) {
		lexiconData.put(word.toLowerCase(), score);
	}

	@Override
	public boolean removeWord(String word) {
		return lexiconData.remove(word.toLowerCase()) != null;
	}

	@Override
	public boolean containsWord(String word) {
		return lexiconData.containsKey(word.toLowerCase());
	}

	@Override
	public Set<String> getAllWords() {
		return lexiconData.keySet();
	}

}
