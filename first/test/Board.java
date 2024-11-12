package test;

import java.util.ArrayList;

public class Board {
    
    private static Board board;
    public Tile[][] gameBoard = new Tile[15][15];
    public char[][] bonusBoard = new char[15][15];    
    public boolean isEmpty = true;

    Board() {
        initializeBonusBoard();
    }

    private void initializeBonusBoard() {
        for (int i = 0; i < bonusBoard.length; i++) {
            for (int j = 0; j < bonusBoard[i].length; j++) {
                bonusBoard[i][j] = getBonusType(i, j);
            }
        }
    }

    private char getBonusType(int i, int j) {
        if (isTripleWordScore(i, j)) return 'R';
        if (isDoubleWordScore(i, j)) return 'Y';
        if (isTripleLetterScore(i, j)) return 'B';
        if (isDoubleLetterScore(i, j)) return 'C';
        if (isCenterTile(i, j)) return 'S';
        return '.';
    }

    private boolean isTripleWordScore(int i, int j) {
        return (i == 0 && (j == 0 || j == 7 || j == 14)) ||
               (i == 7 && (j == 0 || j == 14)) ||
               (i == 14 && (j == 0 || j == 7 || j == 14));
    }

    private boolean isDoubleWordScore(int i, int j) {
        return (i == j && (i > 0 && i < 5 || i > 9 && i < 14)) ||
               (i + j == 14 && (i > 9 || i < 5));
    }

    private boolean isTripleLetterScore(int i, int j) {
        return (i == 1 && (j == 5 || j == 9)) ||
               (i == 5 && (j == 1 || j == 5 || j == 9 || j == 13)) ||
               (i == 9 && (j == 1 || j == 5 || j == 9 || j == 13)) ||
               (i == 13 && (j == 5 || j == 9));
    }

    private boolean isDoubleLetterScore(int i, int j) {
        return (i == 0 && (j == 3 || j == 11)) ||
               (i == 14 && (j == 3 || j == 11)) ||
               (i == 2 && (j == 6 || j == 8)) ||
               (i == 3 && (j == 0 || j == 7 || j == 14)) ||
               (i == 11 && (j == 0 || j == 7 || j == 14)) ||
               (i == 12 && (j == 6 || j == 8)) ||
               (i == 6 && (j == 2 || j == 6 || j == 8 || j == 12)) ||
               (i == 8 && (j == 2 || j == 6 || j == 8 || j == 12)) ||
               (i == 7 && (j == 3 || j == 11));
    }

    private boolean isCenterTile(int i, int j) {
        return i == 7 && j == 7;
    }
    
    public void printBonusBoard() {
        System.out.println("Bonus Board Layout:");
        for (char[] bonusBoard1 : bonusBoard) {
            for (int j = 0; j < bonusBoard1.length; j++) {
                System.out.print(bonusBoard1[j] + " ");
            }
            System.out.println();
        }
    }
    public static  Board getBoard(){
        if (board == null){
            board = new Board();
        }
        return board;
    }
    public Tile[][] getTiles(){

        return gameBoard;
    }
   

    public boolean boardLegal(Word newWord) { 
        if (!newWord.isLegal) {
            return false;
        }
        if (!newWord.inBounds()) {
            return false;
        }
        if (!isNotOverlappingTile(newWord)) {
            return false;
        }
    
        int i = newWord.row;
        int j = newWord.col; 
        int counter = 0;
    
        if (isEmpty) {
            return isFirstWordLegal(newWord);
        }
    
        // Check if the newWord contains null tiles
        if (newWord.containsNull()) {
            for (int index = 0; index < newWord.tiles.length; index++) {
                if (newWord.tiles[index] == null) {
                    if (gameBoard[i][j] == null) {
                        return false;
                    }
                    counter++;
                }
                if (newWord.vertical) {
                    i++;
                } else {
                    j++;
                }
            }
            return counter > 0;
        } else {
            // Check adjacent tiles to ensure connectivity
            for (int index = 0; index < newWord.tiles.length; index++) {
                if (newWord.vertical) {
                    if(index == 0 && i - 1 > 0 && gameBoard[i-1][j]!= null ){
                        return true;
                    }
                    if (newWord.col == 0 && j + 1 < gameBoard[i].length && gameBoard[i][j + 1] != null ) {
                        return true;
                    }
                    if (newWord.col == 14 && j - 1 >= 0 && gameBoard[i][j - 1] != null) {
                        return true;
                    }
                    if (newWord.col != 0 && newWord.col != 14 && 
                        (j + 1 < gameBoard[i].length && gameBoard[i][j + 1] != null ||
                         j - 1 >= 0 && gameBoard[i][j - 1] != null)) {
                        return true;
                    }
                    if(index == newWord.tiles.length - 1 && gameBoard[i+1][j] != null && i + 1 < gameBoard[i].length ){
                         return true;
                    
                    }
                    i++;
                } else {
                    if(index == 0 && j - 1 > 0 && gameBoard[i][j-1]!= null ){
                        return true;}
                    if (newWord.row == 0 && i + 1 < gameBoard.length && gameBoard[i + 1][j] != null) {
                        return true;
                    }
                    if (newWord.row == 14 && i - 1 >= 0 && gameBoard[i - 1][j] != null) {
                        return true;
                    }
                    if (newWord.row != 0 && newWord.row != 14 && 
                        (i + 1 < gameBoard.length && gameBoard[i + 1][j] != null ||
                         i - 1 >= 0 && gameBoard[i - 1][j] != null)) {
                        return true;
                    }
                    if(index == newWord.tiles.length - 1 && gameBoard[i][j+1] != null && j + 1 < gameBoard[i].length ){
                        return true;}

                    j++;
                }
            }
        }
        return false;
    }
    

   public boolean isFirstWordLegal(Word newWord){
    int i = newWord.row;
    int j = newWord.col;
    for (Tile tile : newWord.tiles) {
        if (bonusBoard[i][j]=='S'){
            return true;
        }
        if (newWord.vertical){
            i++;}
        else{
            j++;}
    }
    return false;}
   
   public boolean isNotOverlappingTile(Word newWord){
    int i = newWord.row;
    int j = newWord.col;
    for(int index = 0 ; index < newWord.tiles.length - 1 ; index++){
        if(newWord.tiles[index] != null && gameBoard[i][j] != null){
            return false;}
            if(newWord.vertical){
            i++;
            }
            else{
            j++; 
        }
   }
   return true;
    }

    public boolean dictionaryLegal(){
        return true;
    }
    public ArrayList<Word> getWords(Word newWord){
        ArrayList<Word> newConnectedWords = new ArrayList<>();        
        if (!boardLegal(newWord)){
        return newConnectedWords ;}
        int row = newWord.row;
        int col = newWord.col;
        int startIndex ;
        int endIndex  ;
        int start;
        int end;
        if(newWord.getVer()){
        start = startCheck(newWord, row-1 , col);
        end = endCheck(newWord, row+1, col,1);
        }
        else{
        start = startCheck(newWord, row , col-1);
        end = endCheck(newWord, row, col+1,1);
        }

            newConnectedWords.add(mainWordMaker(newWord, start, end));
        
        for (int i = 0; i < newWord.tiles.length; i++) {
            if (gameBoard[row][col]==null && newWord.tiles[i] != null ){
                if (newWord.vertical){
                    if (col != 0){
                    startIndex = leftVetricalSide(row, col - 1);}
                    else{
                        startIndex = col;
                    }
                    if(col != 14){
                    endIndex = rightVetricalSide(row, col + 1);}
                    else{
                    endIndex = col;
                    }}
                else{
                    if(row != 0){
                    startIndex = topNotVertical(col, row-1);}
                    else{
                    startIndex = row;
                    }
                    if(row != 14){
                    endIndex = bottomNotVertical(col, row+1);}
                    else{
                    endIndex = 14 ;
                    }
                }
                if (endIndex-startIndex!=0 && newWord.vertical){
                    newConnectedWords.add(unVerticalwordMaker( startIndex, endIndex ,  newWord , i ));
                }
                if(endIndex-startIndex!=0 && !newWord.vertical){
                    newConnectedWords.add(verticalwordMaker( startIndex, endIndex ,  newWord , i ));
                    
                }
                
                }
                if(newWord.vertical){
                    row++;
                }
                else{
                    col++;
            }
            
        }
        return newConnectedWords;
    }
    
    public Word mainWordMaker(Word newWord, int start, int end) {
        Tile[] word;
        if (start == 0) {
            end++;
            word = new Tile[end - start];
        } else {
            word = new Tile[end - start];
        }
        
        int i = 0;
        int j = 0;
        
        Word newMainWord;
    
        if (newWord.vertical) {
            newMainWord = new Word(word, start, newWord.col, newWord.vertical);
        } else {
            newMainWord = new Word(word, newWord.row, start, newWord.vertical);
        }
    
        if (newWord.getVer()) {
            while (start != end) {
                if (start < newWord.getRow()) {
                    word[i] = gameBoard[start][newWord.getCol()];
                } else {
                    if(j<newWord.tiles.length){
                    if (newWord.tiles[j] != null) {
                        word[i] = newWord.tiles[j];
                    } else {
                        word[i] = gameBoard[start][newWord.getCol()];
                    }}
                    else{
                        word[i] = gameBoard[start][newWord.getCol()];
                    }
                    j++;
                }
                i++;
                start++;
            }
        } else {
            while (start != end) {
                if (start < newWord.getCol()) {
                    word[i] = gameBoard[newWord.getRow()][start];
                } else {
                    if(j<newWord.tiles.length){
                        if (newWord.tiles[j] != null) {
                        word[i] = newWord.tiles[j];
                         }
                        else {
                        word[i] = gameBoard[newWord.getRow()][start];
                        }
                    }
                    else{
                        word[i] = gameBoard[newWord.getRow()][start];
                    }
                       
                }
                j++;
                i++;
                start++;
            }
        }
    
        newMainWord.setTiles(word);
        return newMainWord;
    }
    
    public Word unVerticalwordMaker(int startIndex , int endIndex , Word mainWord ,int index){
        Tile[] word = new Tile[endIndex-startIndex+1];
        int row = mainWord.getRow();
        Word newWord = new Word(word, row + index , startIndex ,!mainWord.vertical);
        int i = 0 ;

           while(startIndex <= endIndex){
                
                if(gameBoard[row+index][startIndex]!=null){
                    word[i] = gameBoard[row+index][startIndex];
                }
                else{
                    word[i]=mainWord.tiles[index];
                }
                startIndex++;
                i++;
            }
        
        newWord.setTiles(word);
        return newWord;
    }


    public Word verticalwordMaker(int startIndex , int endIndex , Word mainWord ,int index){
        Tile[] word = new Tile[endIndex-startIndex+1];
        int col = mainWord.getCol();
        Word newWord = new Word(word, startIndex,col+index,!mainWord.vertical);
        int i = 0 ;
        
           while(startIndex <= endIndex){
                
                if(gameBoard[startIndex][col+index] == null){
                    word[i]= mainWord.tiles[index];}
                else{
                    word[i] = gameBoard[startIndex][col+index];
                }
                startIndex++;
                i++;
            }
            
        
        newWord.setTiles(word);
        return newWord;
    }

    public int bottomNotVertical(int col, int row){
        if (gameBoard[row][col] != null && row <= 14 ){
            if (row == 14){
                return row;
            }
            return bottomNotVertical(col, row+1);
        }
        else{
            return row - 1 ;
        }
    }
    
    public int topNotVertical(int col, int row){
        if (gameBoard[row][col] != null && row >= 0 ){
            if (row == 0){
                return row;
            }
            return topNotVertical(col, row-1);
        }
            return row + 1 ;
        
    } 
            
    public int leftVetricalSide(int row , int col){
        if (gameBoard[row][col] != null && col >=0){
            if (col == 0){
                return col ;
            }
            return leftVetricalSide(row, col - 1); 
        }
            return col + 1 ;
        
    }

    public int rightVetricalSide(int row, int col){
        if (gameBoard[row][col] != null && col <= 14){
            if (col == 14){
                return col;
            }
            return rightVetricalSide(row, col + 1); 
        }
        else{
            return col - 1 ;
        }
    }
    
    
    public int startCheck(Word newWord, int row, int col) {
        if (newWord.vertical) {
            if (row > 0 && gameBoard[row][col] != null) {
                return startCheck(newWord, row - 1, col);  // Recursively check upwards
            } else {
                return row + 1;  // Adjust by returning the row after finding the tile
            }
        } else {
            if (col > 0 && gameBoard[row][col] != null) {
                return startCheck(newWord, row, col - 1);  // Recursively check leftwards
            } else {
                return col + 1;  // Adjust by returning the column after finding the tile
            }
        }
    }
    public int endCheck(Word newWord, int row , int col ,int flag){
        if (newWord.vertical) {
            if (flag == 1){
            row = row + newWord.tiles.length ;
            }
            if (row < 14 && gameBoard[row][col] != null) {
                return endCheck(newWord, row , col+1,0);  // Recursively check upwards
            } else {
                return row - 1 ;  // Adjust by returning the row after finding the tile
            }
        } else {
            if (flag == 1){
            col = col + newWord.tiles.length ;
            }
            if (col < 14 && gameBoard[row][col] != null) {
                return endCheck(newWord, row, col + 1,0);  // Recursively check leftwards
            } else {
                return col - 1;  // Adjust by returning the column after finding the tile
            }
        }
    }

    public int getScore(Word newWord){

        if(!boardLegal(newWord)){
            return 0 ;}
        int fullScore = 0;
        ArrayList <Word> wordsArray = getWords(newWord);
        for(int i = 0 ; i < wordsArray.size() ; i++){
           fullScore += wordScore(wordsArray.get(i));
        }
        return fullScore;
    }
    public int wordScore(Word newWord){
        int wordBonusX = 1;
        int finalScore = 0;
        int score = 0 ;
        int row = newWord.getRow();
        int col = newWord.getCol();
        for(int i = 0 ; i < newWord.tiles.length ; i ++){
            if (newWord.tiles[i]==null ){
                continue;

            }
                switch (bonusBoard[row][col]) {
                    case '.' : 
                        score = newWord.tiles[i].score;
                        break;
                    case 'R' :
                        score = newWord.tiles[i].score;
                        wordBonusX = wordBonusX*3;
                        break;

                    case 'Y' : 
                        score = newWord.tiles[i].score;
                        wordBonusX = wordBonusX*2;
                        break;
                    case 'B' :
                        score = newWord.tiles[i].score*3;
                        break;
                    case 'C' :
                        score = newWord.tiles[i].score*2;
                        break;
                    case 'S' : 
                        if (isEmpty){
                            wordBonusX = wordBonusX*2;}
                        score = newWord.tiles[i].score;
                        break;
                }
                finalScore += score;
                if (newWord.vertical){
                    row++;}
                else{
                    col++;}
            }      
            return finalScore*wordBonusX;
        }

    public int tryPlaceWord(Word newWord){
        if(!newWord.isLegal){
            return 0 ;
        }
        if (!dictionaryLegal()){
            return 0;
        }    
        int totalScore=getScore(newWord);
        if (totalScore == 0){
            return totalScore;
        }
        int col = newWord.getCol();
        int row = newWord.getRow();

        for(Tile tile : newWord.tiles){
            if(gameBoard[row][col]==null){
            gameBoard[row][col] = tile;}
            if(newWord.getVer()){
                row++;
            }
            else{
                col++;
            }

        }
        isEmpty=false;
        return totalScore;
    }

    public void printGameBoard() {
        System.out.println("Game Board Layout:");
        
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[i].length; j++) {
                if (gameBoard[i][j] == null) {
                    System.out.print(". ");
                } else {
                    System.out.print(gameBoard[i][j].letter + " ");
                }
            }

            System.out.println(i);
        }
    }
    
}
    
        
        
           
    