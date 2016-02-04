/*

 */
package nauertothelloproject;

/**
 *
 * @author elliot
 */
public class NauertOthelloProject {

    public static final int EMPTY = 0;
    public static final int ME = 1;
    public static final int OPPONENT = -1;
    public static final int BORDER = -2;
    
    static Board gameBoard;
    static String myColor;
    
    public static void main(String[] args) {
        int currentPlayer = 1;
        boolean gameOver = false;
        
        myColor = getColor();
        gameBoard = new Board("b");
        
        if(myColor == "b")
            currentPlayer = ME;
        else
            currentPlayer = OPPONENT;
        
        while(!gameOver){
            
        }
    }
    
    private static String getColor(){
        String mycolor = "b";
        return myColor;
    }
    
}
