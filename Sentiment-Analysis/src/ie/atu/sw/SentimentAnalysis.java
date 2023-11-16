package ie.atu.sw;

import java.util.*;
import java.util.concurrent.*;

public class SentimentAnalysis {
    private Lexicon lexicon;
    private List<String> tweets;
    private double overallSentiment;

    public SentimentAnalysis(Lexicon lexicon, List<String> tweets) {
        this.lexicon = lexicon;
        this.tweets = tweets;
        this.overallSentiment = 0.0;
    }
    

	public double analyzeSentiment() {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (String tweet : tweets) {
            executor.submit(() -> {
                double sentiment = processTweet(tweet);
                //updateOverallSentiment(sentiment);
                overallSentiment += sentiment;
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return overallSentiment / tweets.size();
    }

	private double processTweet(String tweet) {
		 String[] words = tweet.split("\\s+");
	        double tweetSentiment = 0.0;

	        for (String word : words) {
	            tweetSentiment += lexicon.getSentimentScore(word.toLowerCase());
	        }

	        return tweetSentiment;
	}
}
