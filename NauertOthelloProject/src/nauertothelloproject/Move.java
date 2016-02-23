/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nauertothelloproject;

import static nauertothelloproject.NauertOthelloProject.COLUMNS;

/**
 *
 * @author elliot
 */
public class Move {
    
    int moveInt;
    String moveString;
    double moveValue;
    
    public Move(String movestring){
        moveInt = stringToMove(movestring);
        moveString = movestring;
        moveValue = 0;
    }
    
    public Move(int moveint){
        moveInt = moveint;
        moveString = moveToString(moveint);
        moveValue = 0;
    }
    
    public Move(int moveint, String movestring){
        moveInt = moveint;
        moveString = movestring;
        moveValue = 0;
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
    
}
