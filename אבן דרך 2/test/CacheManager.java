package test;

import java.util.ArrayList;
import java.util.HashSet;

public class CacheManager {

    private final int maxSize;
    private final CacheReplacementPolicy policy;
    private final HashSet<String> stringSet;

    public CacheManager(int maxSize, CacheReplacementPolicy policy) {
        this.maxSize = maxSize;
        this.policy = policy;
        this.stringSet = new HashSet<>();
    }

    public boolean query(String word){
        if (stringSet.isEmpty())
            return false;
        
        if (stringSet.contains(word))
            return true;
        return false;
        }
    public void add(String word){
        
        if(stringSet.size()>=maxSize){
            String removedWord = policy.remove();
            stringSet.remove(removedWord);
        }
        if(stringSet.contains(word)){
            policy.add(word);
        }
        else{
            stringSet.add(word);
            policy.add(word);
        }
        
    }
    
}
