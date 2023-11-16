package ie.atu.sw;

import java.util.*;

public interface Lexicon {
    /**
     * Gets the sentiment score for a given word.
     *
     * @param word The word for which to retrieve the sentiment score.
     * @return The sentiment score for the given word.
     */
    int getSentimentScore(String word);

    /**
     * Adds a new word and its sentiment score to the lexicon.
     *
     * @param word  The word to add to the lexicon.
     * @param score The sentiment score associated with the word.
     */
    void addWord(String word, int score);

    /**
     * Removes a word from the lexicon.
     *
     * @param word The word to remove from the lexicon.
     * @return true if the word was successfully removed, false otherwise.
     */
    boolean removeWord(String word);

    /**
     * Checks if the lexicon contains a specific word.
     *
     * @param word The word to check in the lexicon.
     * @return true if the lexicon contains the word, false otherwise.
     */
    boolean containsWord(String word);

    /**
     * Gets all words in the lexicon.
     *
     * @return A set of all words in the lexicon.
     */
    Set<String> getAllWords();
}
