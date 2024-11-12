package test;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {
	private final BitSet bitset;
    private  String[] hashFunc;
    private final int bitsetSize;
    public BloomFilter(int bitsetSIZE, String[] hashFunc) {
        this.bitsetSize = bitsetSIZE;
        this.bitset = new BitSet(bitsetSIZE);
        this.hashFunc = hashFunc;
    }
    public BloomFilter(int bitsetSIZE, String alg1 , String alg2) {
        this.bitsetSize = bitsetSIZE;
        this.bitset = new BitSet(bitsetSIZE);
        this.hashFunc = new String[2];
        this.hashFunc[0] = alg1;
        this.hashFunc[1] = alg2;   
    }
    public String[] getHashFunc(){
        return this.hashFunc;
    }
    public BitSet getBitSet(){
        return this.bitset;
    }
    public int getSetsize(){
        return this.bitsetSize;
    }
    public void add(String word){
        MessageDigest[] hashFunctions = new MessageDigest[hashFunc.length];
        for(int i = 0 ; i < hashFunc.length ; i++){
            try {
                hashFunctions[i]= MessageDigest.getInstance(hashFunc[i]);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException("Invalid hash function: " + hashFunc[i], ex);
            }
            byte[] hashBytes = hashFunctions[i].digest(word.getBytes());  
            int hashValue = Math.abs(new BigInteger(hashBytes).intValue()) % bitsetSize;
            bitset.set(hashValue);
        }
    }
    
    public boolean contains(String word){

        MessageDigest[] hashFunctions = new MessageDigest[hashFunc.length];
        for(int i = 0 ; i < hashFunc.length ; i++){
            try {
                hashFunctions[i]= MessageDigest.getInstance(hashFunc[i]);
            } catch (NoSuchAlgorithmException ex) {
                throw new RuntimeException("Invalid hash function: " + hashFunc[i], ex);
            }
            byte[] hashBytes = hashFunctions[i].digest(word.getBytes());  
            int hashValue = Math.abs(new BigInteger(hashBytes).intValue() % bitsetSize);

            if(!bitset.get(hashValue)){
                return false; 
            }
            
        }
        return true;
    }
    @Override
    public String toString(){
        StringBuilder newString = new StringBuilder(bitsetSize);
        for(int i = 0; i < bitset.length() ; i++)
            if( bitset.get(i)){
               newString.append('1');
            }
            else{
                newString.append('0');

            }
           // newString.append( bitset.get(i) ? 1: 0 );

        return newString.toString();
        }
    
    }
    
    

