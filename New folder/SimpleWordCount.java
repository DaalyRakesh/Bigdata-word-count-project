import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * A simplified implementation of MapReduce word count algorithm in plain Java
 * without requiring Hadoop framework
 */
public class SimpleWordCount {

    // The Map function
    public static Map<String, Integer> map(String document) {
        Map<String, Integer> results = new HashMap<>();
        
        StringTokenizer tokenizer = new StringTokenizer(document);
        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase();
            results.put(word, results.getOrDefault(word, 0) + 1);
        }
        
        return results;
    }
    
    // The Reduce function
    public static Map<String, Integer> reduce(List<Map<String, Integer>> mapResults) {
        Map<String, Integer> finalResults = new HashMap<>();
        
        // Combine all intermediate results
        for (Map<String, Integer> result : mapResults) {
            for (Map.Entry<String, Integer> entry : result.entrySet()) {
                String word = entry.getKey();
                Integer count = entry.getValue();
                finalResults.put(word, finalResults.getOrDefault(word, 0) + count);
            }
        }
        
        return finalResults;
    }
    
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: SimpleWordCount <input_file>");
            System.exit(1);
        }
        
        String inputFile = args[0];
        
        // Read the input file
        String content = new String(Files.readAllBytes(Paths.get(inputFile)));
        
        // Split the input file into chunks (simulating data distribution)
        List<String> chunks = splitIntoChunks(content, 1000); // 1000 characters per chunk
        
        System.out.println("Splitting input into " + chunks.size() + " chunks");
        
        // The Map phase (can be parallelized)
        ExecutorService executor = Executors.newFixedThreadPool(
            Math.min(chunks.size(), Runtime.getRuntime().availableProcessors())
        );
        
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();
        for (String chunk : chunks) {
            futures.add(executor.submit(() -> map(chunk)));
        }
        
        // Collect map results
        List<Map<String, Integer>> mapResults = new ArrayList<>();
        for (Future<Map<String, Integer>> future : futures) {
            mapResults.add(future.get());
        }
        
        executor.shutdown();
        
        // The Reduce phase
        Map<String, Integer> wordCounts = reduce(mapResults);
        
        // Sort results by word
        List<Map.Entry<String, Integer>> sortedCounts = new ArrayList<>(wordCounts.entrySet());
        sortedCounts.sort(Map.Entry.comparingByKey());
        
        // Write results to output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            for (Map.Entry<String, Integer> entry : sortedCounts) {
                writer.write(entry.getKey() + "\t" + entry.getValue());
                writer.newLine();
            }
        }
        
        System.out.println("Word count completed. Results written to output.txt");
    }
    
    // Helper method to split text into chunks
    private static List<String> splitIntoChunks(String text, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < text.length(); i += chunkSize) {
            chunks.add(text.substring(i, Math.min(i + chunkSize, text.length())));
        }
        return chunks;
    }
} 