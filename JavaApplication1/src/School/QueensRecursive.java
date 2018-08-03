
package School; 

/**
 *
 * @author Mavis Beacon
 */
public class Queens {
    int SIZEBOARD=8;
    int steps=0;
    int solutions = 0;

    public static void main(String args []){
        Queens q=new Queens();
    }//end public

    public Queens(){
        char [][] queens=new char[SIZEBOARD][SIZEBOARD];

        for(int x=0;x<SIZEBOARD;x++){
            for(int y=0;y<SIZEBOARD;y++){
                queens[x][y]=' ';
            }//end y
        }//end for

        //queens[2][1]='Q';
       // queens[1][2]='Q';
        //queens[0][3]='Q';
        //queens[3][0]='Q';
        

        System.out.println("Before");
        printIt(queens);
        System.out.println("------------------");

        boolean found = findQueens(queens,1,0);
        //checkPiece(queens,3,5);
        System.out.println("Found: " + found);
        System.out.println("After");
        printIt(queens);
        System.out.println("Steps= "+steps);
    }//end constructor

    public boolean findQueens(char [][] queens,int current,int x){
        steps++;
       // System.out.println("In Find Queens");
        if(SIZEBOARD-current<0){
            //System.out.println("Breaking from find queens");
            System.out.println("Solution # " + solutions++);
            printIt(queens);
            System.out.println("----------------");
            return true;
        }

        if(x>=SIZEBOARD)
            return false;
        //System.out.println("Looping in find queens");
        int y=current-1;
       // System.out.println("y="+y);
        //for(int x=0;x<SIZEBOARD;x++){
                if(checkPiece(queens,x,y)){
                    //System.out.println("Good Check piece X:"+x+" Y:"+y+" placing #"+current+" queen");
                    queens[x][y]='Q';
                    //System.out.println("Current: "+current);
                   // printIt(queens);
                    if(findQueens(queens,current+1,0)){
                      //  System.out.println("Returning true from findQUeens");
                        return true;
                    }
                    else
                        queens[x][y]=' ';
                    
                }//end if checkPiece
                
                return findQueens(queens,current,x+1);
    //}//end for
       // System.out.println("Returng false from findQueens. LastX:"+lastGoodX+" LastY:"+lastGoodY);
        //return false;

    }//end findQueens

    public boolean checkBoard(char [][] queens){
        for(int x=0;x<SIZEBOARD;x++){
            for(int y=0;y<SIZEBOARD;y++){
                if(!checkPiece(queens,x,y)){
                    //System.out.println("Bad BOARD in check");
                    return false;
                }
            }//end for
        }//end for
        //System.out.println("GOOD board");
        return true;
    }//end checkBoard

    public boolean checkPiece(char [][] queens,int x,int y){
        for(int h=0;h<SIZEBOARD;h++){
            if(queens[x][h]=='Q'){
                //System.out.println("Bad VERTICAL in check");
                return false;
            }
        }//end for
        for(int v=0;v<SIZEBOARD;v++){
            if(queens[v][y]=='Q'){
               // System.out.println("bad HORIZONTAL in check");
                return false;
            }
        }//end for

        //DIAGONAL NEGATIVE SLOPE
        int temp=0;
        int tempX=x;
        int tempY=y;
       // System.out.println("Before Diagonal check x:"+x+" y:"+y);
        for(int d=0;d<SIZEBOARD;d++){
            tempX=tempX+1;
            tempY=tempY+1;
            if(tempX>SIZEBOARD-1 || tempY>SIZEBOARD-1){
                temp=SIZEBOARD-tempY;
                tempY=SIZEBOARD-tempX;
                tempX=temp;
            }
            if(tempX==x && tempY==y)
                break;
          // System.out.println("IN Diagonal check x:"+tempX+" y:"+tempY);
            if(queens[tempX][tempY]=='Q'){
               // System.out.println("Returning false from diagonal negative check");
                return false;
            }//end if
        }//end for

        
        //DIAGONAL POSITIVE SLOPE
        temp=0;
        tempX=x;
        tempY=y;
        for(int d=0;d<SIZEBOARD;d++){
            tempX=tempX-1<0?tempX:tempX-1;
            tempY=tempY+1>SIZEBOARD-1?tempY:tempY+1;
            if(tempX<=0 || tempY>=SIZEBOARD-1){
                temp=tempY;
                tempY=tempX;
                tempX=temp;
            }
            if(tempX==x && tempY==y)
                break;
            if(queens[tempX][tempY]=='Q'){
               // System.out.println("Returning false from diagonal postiive check");
                return false;
            }//end if
        }//end for
      
        
       // System.out.println("Returning true from single check");
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

}//end queens
