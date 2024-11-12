package test;

import java.util.Arrays;


public final class Word {
    public Tile[] tiles ;
    public int col;
    public int row;  
	public boolean vertical; 
    public boolean isLegal;
    public Word(Tile[] newTiles , int row , int col , boolean vertical){
        this.tiles = new Tile[newTiles.length];
        System.arraycopy(newTiles, 0, tiles, 0, newTiles.length);
            this.col = col ;
            this.row = row ;
            this.vertical = vertical ;
            isLegal = true;

            if (!inBounds()) {
                isLegal = false;
            }
            
            
        }
        

    public Tile[] getTiles(){
        return tiles;
    }
    public void setTiles(Tile[] tiles){
        this.tiles = tiles;
        int size = this.newtilesSize();
        if (vertical){
        if(row + size > 14){
            isLegal = false;
        }}
  
        else{
            if(col + tiles.length > 14){
                isLegal = false;
        }
}
        
    }
    public int getCol(){
        return this.col;
    }
    public int getRow(){
        return this.row;
    }
    public boolean getVer(){
        return this.vertical;
    }
    @Override
    public int hashCode() {
        // Use default hashCode implementation if needed
        return super.hashCode();
    }
    @Override 
    public boolean equals(Object newWord){
    if (this == newWord){
        return true;
    }
    
    if(newWord==null || newWord.getClass() !=getClass()){
        return false;
    }
    Word nw=(Word)newWord;
    
    return(Arrays.equals(this.tiles, nw.tiles) &&
    this.col == nw.col &&
    this.row == nw.row &&
    this.vertical == nw.vertical);
    }
    public boolean containsNull(){
            for (Tile tile : tiles) {
                if (tile == null) {
                    return true;
                }
            }
        return false;
    }

    public boolean inBounds(){
    int size = newtilesSize();
    if(col < 0 || col > 14 || row < 0 || row > 14){
    return false;}
    if(vertical){
            if(row + size > 14){
                return false;
            }
    }
    else{
            if(col + size > 14){
                return false;
            }
    }
    return true;
    }
    public int newtilesSize(){
        int size = 0;
        for (Tile newTile : tiles) {
            if (newTile != null) {
                size++;
            }
        }
        return size;
    }
}


