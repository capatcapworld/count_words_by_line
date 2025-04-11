package com.capworld;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordCounter {

    public void start() {
        Map<String, Integer> numberOfFoundWords = new HashMap<>();

        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("shakespeare_historical_plays.txt")).getFile());

        try (BufferedReader reader = new BufferedReader(new FileReader(file.getPath()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Split line into words
                String[] wordsOnLine = line.toLowerCase().split("\\W");
                for (String word : wordsOnLine) {
                    if (!word.isEmpty()) {
                        numberOfFoundWords.put(word, numberOfFoundWords.getOrDefault(word, 0) + 1);
                    }
                }
            }

            // Use a priority queue to get top entries
            PriorityQueue<Map.Entry<String, Integer>> topWords = new PriorityQueue<>(
                    Map.Entry.comparingByValue(Integer::compare)

            );

            for (Map.Entry<String, Integer> wordWithCounts : numberOfFoundWords.entrySet()) {
                topWords.offer(wordWithCounts);
                if (topWords.size() > 10) {
                    topWords.poll(); // Pull of 11th element
                }
            }

            // Extract entries and sort in descending order
            List<Map.Entry<String, Integer>> topWordResult = new ArrayList<>(topWords);
            topWordResult.sort((count1, count2) -> count2.getValue().compareTo(count1.getValue()));

            int i = 1;
            for (Map.Entry<String, Integer> wordAndCount : topWordResult) {
                System.out.println( i++ + " : " + wordAndCount.getKey() + ": " + wordAndCount.getValue());
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

    }
}
