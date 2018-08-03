/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package connect4;

import javax.swing.JFrame;

/**
 *
 * @author Mavis Beacon
 */
public class Board {

    public int[][] board;
    public int horizontal;
    public int vertical;

    public Board(){

        this.board=new int[7][6];
        initializeBoard();
        this.horizontal=7;
        this.vertical=6;
    }
    public Board(int horiz,int vert){

        this.board=new int[horiz][vert];
        initializeBoard();
        this.horizontal=horiz;
        this.vertical=vert;
    }
    public Board(int [] [] board){
        this.board=new int[board.length][board[0].length];
        for(int x=0;x<board.length;x++){
            for(int y=0;y<board[x].length;y++){
                this.board[x][y]=board[x][y];
            }//end for
        }//end for
        this.horizontal=board.length;
        this.vertical=board[0].length;
    }
    public Board(Board b){
        this(b.board);
    }

    public static void main(String arg []){

        Board b=new Board(7,6);
        //b.drawBoard();
        b.move(5, 1);
        b.move(4, 0);
        b.move(4, 1);
        b.move(3, 0);
        b.move(3, 0);
        b.move(3, 1);
        b.move(2, 0);
        b.move(2, 0);
        b.move(2, 0);
        b.move(2, 0);
        b.move(2, 1);
        System.out.println(b.isWon());
        b.drawBoard(true);

    }
    
    private void initializeBoard(){
        
        for(int x=0;x<horizontal;x++){
            for(int y=0;y<vertical;y++){
                board[x][y]=0;
            }//end for
        }//end for
        
    }
    
    public boolean move(int spot, int turn){
        int color=1+turn;

        if(spot>=horizontal || spot<0)
            return false;
        
        for(int x=1;x<vertical;x++){
            if(board[spot][x]!=0 && board[spot][x-1]==0){
                board[spot][x-1]=color;
                return true;
            }//end if
            else if(x==vertical-1 && board[spot][x]==0){
                board[spot][x]=color;
                return true;
            }
        }//end for
        
        return false;
    }//end move

    //Return plyaer number that won(1 or 2) or 0 otherwise
    public int isWon(){

       int current=1;
       int counter=0;
       int place;

        //check vertical
        for(int x=0;x<vertical;x++){
            counter=0;
            for(int y=0;y<horizontal;y++){
                if(board[y][x]==current)
                    counter++;
                else{
                    current=board[y][x];
                    counter=1;
                }
                if(counter==4 && current!=0){
                    return (current);
                }
            }//end for
        }//end for

        current=1;
        counter=0;
        //check horizontal
        for(int x=0;x<horizontal;x++){
            counter=0;
            for(int y=0;y<vertical;y++){
                if(board[x][y]==current)
                    counter++;
                else{
                    current=board[x][y];
                    counter=1;
                }

                if(counter==4 && current!=0){
                    return (current);
                }
            }//end for
        }//end for

        
        //Postivie horizontal ABOVE diagnoal
        current=1;
        counter=0;
        for(int x=0;x<horizontal;x++){
            place=x;
            counter=0;
            for(int y=0;y<vertical;y++){
                if(board[place][y]==current)
                    counter++;
                else{
                    current=board[place][y];
                    counter=1;
                }

                if(counter==4 && current!=0){
                    return (current);
                }

                if(++place>=horizontal){
                    counter=0;
                    break;
                }
            }//end for
        }//end for

       //Postivie horizontal BELOW diagnoal
        current=1;
        counter=0;
        for(int x=0;x<vertical;x++){
            place=x;
            counter=0;
            for(int y=0;y<horizontal;y++){
                if(board[y][place]==current){
                    counter++;
                }
                else{
                    current=board[y][place];
                    counter=1;
                }

                if(counter==4 && current!=0){
                    return (current);
                }

                if(++place>=vertical){
                    counter=0; 
                    break;
                }
            }//end for
        }//end for

       //NEGATIVE horizontal ABOVE diagnoal
        current=1;
        counter=0;
        for(int x=0;x<horizontal;x++){
            place=x;
            counter=0;
            for(int y=vertical-1;y>=0;y--){
                if(board[place][y]==current)
                    counter++;
                else{
                    current=board[place][y];
                    counter=1;
                }

                if(counter==4 && current!=0){
                    return (current);
                }

                if(++place>=horizontal){
                    counter=0;
                    break;
                }
            }//end for
        }//end for

       //NEGATIVE horizontal BELOW diagnoal
        current=1;
        counter=0;
        for(int x=vertical-1;x>=0;x--){
            place=x;
            counter=0;
            for(int y=0;y<horizontal;y++){
                if(board[y][place]==current)
                    counter++;
                else{
                    current=board[y][place];
                    counter=1;
                }

                if(counter==4 && current!=0){
                  return (current);
                }

                if(--place<0){
                    counter=0;
                    break;
                }
            }//end for
        }//end for


        return 0;
    }//end is won

    public void clearBoard(){
        initializeBoard();
    }//end clearBoard

    public void drawBoard(boolean display){

        if(display){
            for(int x=0;x<vertical;x++){
                System.out.println();
                for(int y=0;y<horizontal;y++){
                    System.out.print(""+y+""+x+" ");
                }//end for
            }//end for
            System.out.println();
        }
       

        
        for(int x=0;x<vertical;x++){
            System.out.println();
            for(int y=0;y<horizontal;y++){
                System.out.print(board[y][x]+" ");
                //System.out.print(x+""+y+" ");
            }//end for
        }//end for
        System.out.println();

        
    }//end Board



}
