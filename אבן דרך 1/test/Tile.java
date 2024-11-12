package test;
import java.util.Objects;
import java.util.Random;


public class Tile 
{

    public final char letter ;
    public final int score ;
	
    
    private Tile( char letter , int score )
    {
        this.letter = letter ;
        this.score = score ;
    
    }
@Override 

public boolean equals(Object o){

    if (this == o){
        return true; 
    }
    ///////////////
    if (o == null || this.getClass()!= o.getClass()){
    return false;
    }
    Tile tile = (Tile) o ;
    return score == tile.score && letter == tile.letter;
}
@Override
public int hashCode() {
    return Objects.hash(letter, score);
}

public static class Bag {
    int bagSize = 98 ; 
    private  final  int[] maxlettersNumBag = {
        9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
    };
    private final int[] lettersNumBag = maxlettersNumBag.clone();
    private final Tile[] tilesBag ;
    private static Bag bag;
 
    private Bag(){
        tilesBag = new Tile[] {
            new Tile('A', 1), new Tile('B', 3), new Tile('C', 3), new Tile('D', 2), new Tile('E', 1),
            new Tile('F', 4), new Tile('G', 2), new Tile('H', 4), new Tile('I', 1), new Tile('J', 8),
            new Tile('K', 5), new Tile('L', 1), new Tile('M', 3), new Tile('N', 1), new Tile('O', 1),
            new Tile('P', 3), new Tile('Q', 10), new Tile('R', 1), new Tile('S', 1), new Tile('T', 1),
            new Tile('U', 1), new Tile('V', 4), new Tile('W', 4), new Tile('X', 8), new Tile('Y', 4),
            new Tile('Z', 10)
        };
    }
    

    public Tile getRand()
    {
        if (size() == 0){
        return null;
        }
        
        Random random = new Random();
        while(true){
            int randomNumber = random.nextInt(26);
            
            if (lettersNumBag[randomNumber] > 0){
                lettersNumBag[randomNumber]-- ;
                bagSize-- ;
                return tilesBag[randomNumber];
            }
        }
    
    }

    public Tile getTile(char letter){
        if (letter >= 'A' && letter <= 'Z') {
            int index = letter - 'A'; // Convert letter to index (A=0, B=1, C=2, ...)
            if (lettersNumBag[index]>0){
            lettersNumBag[index]-- ;
            bagSize-- ;
            return tilesBag[index];
            }
            else{
                return null ;
            }
        }    
        return null ;
    }
    public void put(Tile tile){
        if (tile.letter >= 'A' && tile.letter <= 'Z') {
            int index = tile.letter - 'A'; // Convert letter to index (A=0, B=1, C=2, ...) 
            if (lettersNumBag[index] + 1 > maxlettersNumBag[index]){
                return;
            }
            lettersNumBag[index]++ ;
            bagSize++;
        }
    }
    public int size(){
        return bagSize;
    }

    public int[] getQuantities(){
        return lettersNumBag.clone();
    }

     public static  Bag getBag(){
        if (bag == null) {
            bag = new Bag();
        }
        return bag;
    }
}
}