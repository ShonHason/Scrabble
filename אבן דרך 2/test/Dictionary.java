package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dictionary {

    private final CacheManager lruCacheManager;
    private final CacheManager lfuCacheManager;
    private final BloomFilter bf;
    private IOSearcher ioSearcher = new IOSearcher();
    private String[] fileNames;
    public Dictionary(String...fileNames) {
        lruCacheManager = new CacheManager(400 , new LRU()); // for existance words
        lfuCacheManager = new CacheManager(100 , new LFU()); // for unexistance words
        this.fileNames = fileNames;
		bf = new BloomFilter(256,"MD5","SHA1");
        for(String file : fileNames){
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+"); // Splits the line into words by spaces
                    for (String word : words) {
                        bf.add(word);
                    }
                }      
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }

    public boolean  query(String word){
        if(lruCacheManager.query(word)){
            return true;
        }
        if(lfuCacheManager.query(word)){
            return false;
        }
        if(bf.contains(word)){
            lruCacheManager.add(word);
            return true;
        }
        else{
            lfuCacheManager.add(word);
            return false;
        }
    }
    public boolean challenge(String word){

        boolean existance = ioSearcher.search(word,fileNames);
            if(existance){
            lruCacheManager.add(word);
            return true;

        }
        else{
            lfuCacheManager.add(word);
            return false;
        }
    }
    


    
}
