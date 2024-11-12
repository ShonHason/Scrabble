package test;


import java.util.HashMap;
import java.util.Map;

public class LFU implements  CacheReplacementPolicy {
    private Map<String, Integer> lfuMap = new HashMap<>();
    private  int size ;
    
    public LFU(){
        size = 0 ;

    }

    @Override
    public void add(String word){
        if(lfuMap.containsKey(word)){
            lfuMap.put(word, lfuMap.get(word) + 1);
            size ++;
        }
        else{

            lfuMap.put(word, 1);   
            size ++;
        }

  }
   
    @Override
    public String remove(){
        String lfuKey = null;
        int minFrequancy = Integer.MAX_VALUE;


        for(Map.Entry<String, Integer> entry : lfuMap.entrySet()){
            if (entry.getValue() < minFrequancy){
                minFrequancy = entry.getValue();
                lfuKey = entry.getKey();
            }
        }
        if(lfuKey != null){
            lfuMap.remove(lfuKey);
        }
        size--;
        return lfuKey;
    }

    public Map getMap(){
        return this.lfuMap;
    }
    public int getSize(){
        return this.size;
    }

}

