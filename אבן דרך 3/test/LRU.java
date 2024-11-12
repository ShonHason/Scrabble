package test;
import java.util.LinkedList;
import java.util.Queue;

public class LRU implements  CacheReplacementPolicy {


    private Queue <String> lruQueue = new LinkedList<>();
    private int size = lruQueue.size();
    @Override
    public void add(String word){
        if(lruQueue.contains(word)){
            lruQueue.remove(word);
        }
        lruQueue.add(word);
        
    }
    @Override
    public String remove(){
        String removedWord;
        removedWord = lruQueue.poll();
        return removedWord;
    }

    public Queue getQueue(){
        return this.lruQueue;
    }
    public int getSize(){
        return this.size;
    }
}
