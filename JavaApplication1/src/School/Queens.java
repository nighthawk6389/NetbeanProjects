
package School; 

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Mavis Beacon
 */
public class Queens {
    int SIZEBOARD=10;
    int steps=0;
    int solutions = 0;
    Set<String> set = new HashSet<String>();
    
    
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

        //queens[0][0]='P';
        queens[2][3]='P';
        queens[4][5]='P';
        //queens[3][1]='P';
        //queens[3][3]='P';
        

        System.out.println("Before");
        printIt(queens);
        System.out.println("------------------");

        findQueens(queens,0,0,0);
        //checkPiece(queens,3,5);
        System.out.println("After");
        printIt(queens);
        //System.out.println("Steps= "+steps);
        System.out.println("Solutions: " + solutions);
        System.out.println("Set Num: " + set.size());
    }//end constructor

    public boolean findQueens(char [][] queens, int queen_count, int x_in, int y_in){
        
        steps++;
       
        //System.out.println("In Find Queens");
        if(queen_count >= SIZEBOARD){
            //System.out.println("Breaking from find queens");
            System.out.println("Solution # " + solutions++ + " Set: " + set.size());
            //printIt(queens);
            //System.out.println("----------------");
            set.add(getString(queens));
            return true;
        }

        //System.out.println("Looping in find queens");
        //System.out.println("Y="+y_in + " X="+x_in);
        int x = x_in;
        for(int y = y_in; y < SIZEBOARD; y++){
            //System.out.println("-----------------------------------------");
            //System.out.println("-----------------------------------------");
            //System.out.println("-----------------------------------------");
            while( x < SIZEBOARD ){

                if( queens[x][y] == 'P' || queens[x][y] == 'Q'){
                        x++;
                        continue;
                }   

                //System.out.println("Checking: [" + x + "," + y +"]");
                if( checkPiece(queens,x,y) ){
                    //System.out.println("Good Check piece X:"+x+" Y:"+y+" placing queen");
                    queens[x][y]='Q';
                   // System.out.println("x_in: " + x_in + " y_in:" + y_in);
                    //printIt(queens);
                   if( findQueens(queens, queen_count + 1, x + 1, y) ){
                       //System.out.println("Setting Queen in [" + x + "," + y + "]");
                       //return true;
                   }
                      //  System.out.println("Returning true from findQUeens");
                        //return true;

                    queens[x][y]=' ';

                }//end if checkPiece
                x++;
            }//end for
            x = 0; 
        }
       // System.out.println("Returng false from findQueens. LastX:"+lastGoodX+" LastY:"+lastGoodY);
        
        //System.exit(0);
        return true; 

    }//end findQueens
    
    private String getString(char [][] queens){
        
        String string = ""; 
        
        for(int y=0;y<SIZEBOARD;y++){
            for(int x=0;x<SIZEBOARD;x++){
                if( queens[x][y] == 'Q')
                    string += x; 
            }//end for
            string += "-";
        }//end for
        
        return string;
    }

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
        
        int h_queenfound = y;
        int h = y + 1;
        if( h >= SIZEBOARD ){
            h = 0;
            h_queenfound = -1;
        }
        
        while( y < SIZEBOARD && h != y ){
            if(queens[x][h]=='Q' && h_queenfound != -1){
                //System.out.println("Bad VERTICAL in check");
                return false;
            }
            if(queens[x][h]=='Q'){
                //System.out.println("Bad VERTICAL in check");
                h_queenfound = h;
            }
            if(queens[x][h]=='P'){
                //System.out.println("Bad VERTICAL in check");
                h_queenfound = -1;
            }
            
            h++;
            if( h >= SIZEBOARD ){
               h_queenfound = -1;
               h = 0;
            }
            
        }//end for
        
        //Check the actual cell again
        if(h_queenfound != -1 && h_queenfound != y){
                return false;
        }
        
        int v_queenfound = x;
        int v = x + 1;
        if( v >= SIZEBOARD ){
            v = 0;
            v_queenfound = -1;
        }
        
        while( x < SIZEBOARD && v != x){
            if(queens[v][y]=='Q' && v_queenfound != -1){
               // System.out.println("bad HORIZONTAL in check");
                return false;
            }
            if(queens[v][y]=='Q'){
               // System.out.println("bad HORIZONTAL in check");
                v_queenfound = v;
            }
            if(queens[v][y]=='P'){
               // System.out.println("bad HORIZONTAL in check");
                v_queenfound = -1;
            }
            
            v++;
            if( v >= SIZEBOARD){
                v_queenfound = -1;
                v = 0;
            }
        }//end for
        
        //Check the actual cell again
        if(v_queenfound != -1 && v_queenfound != x){
                return false;
        }
        
        //DIAGONAL NEGATIVE SLOPE
        int dneg_start = x; // Doesnt matter what used here
        int dneg_queenfound = dneg_start; 
        
        int temp=0;
        int tempX=x;
        int tempY=y;
       // System.out.println("Before Diagonal check x:"+x+" y:"+y);
        for(int d=0;d<SIZEBOARD;d--){
            tempX=tempX+1;
            tempY=tempY+1;
            if(tempX>SIZEBOARD-1 || tempY>SIZEBOARD-1){
                temp=SIZEBOARD-tempY;
                tempY=SIZEBOARD-tempX;
                tempX=temp;
                dneg_queenfound = -1;
            }
            if(tempX==x && tempY==y)
                break;
            
           //System.out.println("IN Diagonal check x:"+tempX+" y:"+tempY);
            if(queens[tempX][tempY]=='Q' && dneg_queenfound != -1){
                return false;
            }
            if(queens[tempX][tempY]=='Q'){
                dneg_queenfound = tempX;
            }
            if(queens[tempX][tempY]=='P'){
                dneg_queenfound = -1;
            }
            
            if( d < -SIZEBOARD){
                System.out.println("FAIL IN NEG SLOPE");
                System.exit(1);
            }
            
        }//end for
        
        //Check the actual cell again
        if(dneg_queenfound != -1 && dneg_queenfound != dneg_start){ 
                return false;
        }

        //DIAGONAL POSITIVE SLOPE
        int dpos_start = y; //Doesnt matter value
        int dpos_queenfound = dpos_start;
        
        temp=0;
        tempX=x;
        tempY=y;
        for(int d=0;d<SIZEBOARD;d--){
            tempX = tempX-1;
            tempY = tempY+1;
            if(tempX<0 || tempY>SIZEBOARD-1){
                //System.out.println("Resetting");
                temp=tempY;
                tempY=tempX + 1;
                tempX=temp - 1;
                dpos_queenfound = -1;
            }
            if(tempX==x && tempY==y)
                break;
            
            //System.out.println("IN Diagonal check x:"+tempX+" y:"+tempY + " d:" + d);
            if(queens[tempX][tempY]=='Q' && dpos_queenfound != -1){
                return false;
            }
            if(queens[tempX][tempY]=='Q'){
               dpos_queenfound = tempY;
            }
            if(queens[tempX][tempY]=='P'){
                dpos_queenfound = -1;
            }
            
            if( d < -SIZEBOARD){
                System.out.println("FAIL IN POS SLOPE");
                System.exit(1);
            }
        }//end for
      
        //Check the actual cell again
        if(dpos_queenfound != -1 && dpos_queenfound != dpos_start){ 
                return false;
        }
        
        return true;
    }//end checkPiece

    public void printIt(char [][] queens){
        for(int x=0;x<SIZEBOARD;x++){
            for(int y=0;y<SIZEBOARD;y++){
                System.out.print("|"+queens[y][x]);
            }//end y
            System.out.println();
        }//end for
    }//end printIt

}//end queens