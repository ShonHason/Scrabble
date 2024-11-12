package test;
import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {

    private Map<String, Dictionary> dmap;

    // Singleton instance
    private static DictionaryManager dm;

    // Private constructor for Singleton pattern
    public DictionaryManager() {
        dmap = new HashMap<>();
    }

    // Synchronized method to get the singleton 
    public static synchronized DictionaryManager get() {
        if (dm == null) {
            dm = new DictionaryManager();
        }
        return dm;
    }

    public boolean query(String... args){
        String str = args[args.length - 1];   
        int flag = 0;
        for(int i = 0 ; i < args.length - 1 ; i++){
            if (!dmap.containsKey(args[i])){
                dmap.put(args[i], new Dictionary(args[i]));}
        }
        for(int i = 0 ; i < args.length - 1 ; i++){
           if(dmap.get(args[i]).query(str)){
            flag++;
           }}
        if(flag == 0){
            return false;
        }
        return true;
    }
    public boolean challenge(String... args){
        int flag = 0;
        String str = args[args.length - 1]; 
                for(int i = 0 ; i < args.length - 1 ; i++){
            if (!dmap.containsKey(args[i])){ 
                dmap.put(args[i], new Dictionary(args[i]));}
        }
        for(int i = 0 ; i < args.length - 1 ; i++){
            if(dmap.get(args[i]).challenge(str)){
             flag++;
            }}
        if(flag == 0){
             return false;}
         return true;

    }








    public int getSize(){
        return dmap.size();
    }
}

