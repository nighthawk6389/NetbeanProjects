/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package School;

/**
 *
 * @author Mavis Beacon
 */
public class Queens2 {
    
    int SIZEBOARD=8;

    public static void main(String args []){
        Queens2 q=new Queens2();
    }//end public

    public Queens2(){
        char board[][]=new char[SIZEBOARD][SIZEBOARD];

        for(int x=0;x<SIZEBOARD;x++){
            for(int y=0;y<SIZEBOARD;y++){
                board[x][y]=' ';
            }//end for y
        }//end for x

        printIt(board);
        board[2][2]='Q';
        System.out.println(checkPiece(board,1,1));
        printIt(board);


    }//end defualt

    private boolean recursiveQueens(char[][]board,int current){
        if(current>SIZEBOARD){
            System.out.println("Hit Base Case");
            return true;
        }//end if basecase

        for(int x=0;x<SIZEBOARD;x++){
            for(int y=0;y<SIZEBOARD;y++){
              if(checkPiece(board,x,y)){

              }//end if
            }//end y
        }//end x

        return false;
    }//end

    public boolean checkPiece(char[][]board,int x,int y){

        //end Vertical check
        for(int z=0;z<SIZEBOARD;z++){
            if(board[x][z]=='Q')
                return false;
        }//end for z

        //end HORIZONTAL check
        for(int z=0;z<SIZEBOARD;z++){
            if(board[z][y]=='Q')
                return false;
        }//end for

        int tempX=x;
        int tempY=y;
        for(int z=0;z<SIZEBOARD;z++){

         System.out.println("X:"+tempX+" Y:"+tempY);
            if(tempX==0 || tempY==0){
                //TEMPORARY VALUE X IS CHANGED
                tempX=SIZEBOARD-tempY-1;
                tempY=SIZEBOARD-tempX-1;
            }//end if
            else{
                tempX=tempX-1-z;
                tempY=tempY-1-z;
            }

            System.out.println("X:"+tempX+" Y:"+tempY);
            if(tempX==x && tempY==y)
                break;
            if(board[tempX][tempY]=='Q')
                return false;
        }//end negative slope diagnal

        return true;
    }//end checkPiece

        public void printIt(char [][] queens){
        for(int x=0;x<SIZEBOARD;x++){
            for(int y=0;y<SIZEBOARD;y++){
                System.out.print("|"+queens[x][y]);
            }//end y
            System.out.println();
        }//end for
    }//end printIt

}
