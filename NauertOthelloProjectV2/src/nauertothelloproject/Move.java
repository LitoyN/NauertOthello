
package nauertothelloproject;

/**
 *
 * @author elliot
 */
public class Move {
    
    public static final int ME = 1;
    public static final int OPPONENT = -1;
    public static final String[] COLUMNS = {"A","B","C","D","E","F","G","H"};

    
    int moveInt;
    String moveString;
    double moveValue;
    
    public Move(String movestring){
        if(movestring.equalsIgnoreCase("pass")){
            moveInt = -1;
            moveString = movestring;
            moveValue = 0;
        }
        else{
            moveInt = stringToMove(movestring);
            moveString = movestring;
            moveValue = 0;
        }
    }
    
    public Move(int moveint){
        if(moveint <= 0){
            moveInt = moveint;
            moveString = "   ";
            moveValue = 0;
        }
        else{
            moveInt = moveint;
            moveString = moveToString(moveint);
            moveValue = 0;
        }
    }
    
    public Move(int moveint, String movestring){
        moveInt = moveint;
        moveString = movestring;
        moveValue = 0;
    }
    
    public Move(){
        moveInt = 0;
        moveString = "  ";
        moveValue = 0;
    }
    
    public int getMoveInt(){
        return moveInt;
    }
    
    public String getMoveString(){
        return moveString;
    }
    
    public double getMoveValue(){
        return moveValue;
    }
    
    public void setMoveValue(double value){
        moveValue = value;
    }

    
    public String moveToString(int move){
        String moveOutput;
        int row;
        String column;
        row = move/10;
        column = COLUMNS[(move-1)%10];
        
        moveOutput = column + " " + row;
        
        return moveOutput;
    }
    
    private int stringToMove(String inputString){
        int moveInt = 0;
        String column = inputString.substring(0,1);
        int columnInt = 0;
        boolean foundCol = false;
        int i = 0;
        while(!foundCol){
            if(COLUMNS[i].equalsIgnoreCase(column)){
                System.out.println("C COLUMN: " + COLUMNS[i]);
                foundCol = true;
                columnInt = i +1;
            }
            i++;
        }
        int row = Integer.parseInt(inputString.substring(2,3));
        System.out.println("C ROW: " + row);
        
        moveInt = 10 * row;
        moveInt = moveInt + columnInt;
        System.out.println("C move in int form: " + moveInt);
        return moveInt;
    }
    
    @Override
    public boolean equals(Object object){
        boolean sameSame = false;

        if (object != null && object instanceof Move)
        {
            sameSame = this.moveInt == ((Move) object).moveInt;
        }

        return sameSame;
    }
    
}
