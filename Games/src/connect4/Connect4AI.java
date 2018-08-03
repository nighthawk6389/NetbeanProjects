/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package connect4;

import java.util.Scanner;

/**
 *
 * @author Mavis Beacon
 */
public class Connect4AI extends Player{

    Scanner in = new Scanner(System.in);
    String name = "";
    public int moveNum=0;
    final private int MAXAMOUNT=10000000;
    final private int MINAMOUNT=-100000000;
    public int maxDepth;
    Board board;
    Control control;
    boolean firstMove=true;

    public Connect4AI(int color,int maxDepth){
        super(color,true);
        this.maxDepth=maxDepth;
    }

    @Override
    public int notifyTurn(Board board){

    int [] myMove;
    if(firstMove){
        firstMove=false;
        myMove=getFirstMove(board);
    }
    else{
        myMove = alphaBetaDecision(board,maxDepth);
    }
    System.out.println("Winner:"+board.isWon()+" Color:"+color);
    System.out.println("AI moving "+myMove[1]+"for utility:"+myMove[0]);
    return(myMove[1]);

    }

    public int [] alphaBetaDecision(Board board, int depth){

        this.maxDepth=depth;
        int [] value=maxValue(board,MINAMOUNT,MAXAMOUNT,0);
        System.out.println("Move: "+value[1]+" Value: "+value[0]);
        return value;
    }

    private int [] maxValue(Board board, int alpha, int beta, int counter){

        //Implementing terminal
        if(terminalTest(board,counter)){
            return utility(board,counter);
        }

        int v;
        Board temp;
        int bestMove=0;
        int bestScore=MINAMOUNT;

        for(int i=0;i<board.horizontal;i++){

            //Make a copy of the board so not to operate on board
            temp=new Board(board);

            //Make a move by going along the houses
            boolean turn;
            if(checkLegitMove(board,i))
                turn=temp.move(i,(color-1)%2);
            else
                continue;

            //Go again if turn==0 or send to minValue otherwise
              v=minValue(temp,alpha,beta,(counter+10))[0];

            //Check if bestScore
            if(v>bestScore){
                bestMove=i;
                bestScore=v;
            }

            //Prune trees that wont be searched
            if(bestScore>=beta && (Math.random()>.5)){
                //System.out.println("Max pruning Best Score:"+bestScore+" Beta:"+beta);
                int [] values={bestScore,bestMove};
                return values;
            }
            alpha=Math.max(alpha, bestScore);

        }//end for

        //System.out.println("Max Counter="+counter+" Alpha:"+alpha+" Beta:"+beta);

        //Return both score and move becuase move is needed for final decision
        int [] values={bestScore,bestMove};
        return values;
    }//end maxValue

    private int [] minValue(Board board,int alpha, int beta, int counter){

        if(terminalTest(board,counter)){
            return utility(board,counter);
        }

        int v;
        Board temp;
        int lowestMove=0;
        int lowestScore=MAXAMOUNT;

        for(int i=0;i<board.horizontal;i++){

            //Make a copy of the board so not to operate on board
            temp=new Board(board);

            //Make a move by going along the houses
            boolean turn;
            if(checkLegitMove(board,i))
                turn=temp.move(i,(color)%2);
            else
                continue;

            //Go again if turn==0 or send to minValue otherwise
              v=maxValue(temp,alpha,beta,(counter+10))[0];

            //Check if bestScore
            if(v<lowestScore){
                lowestMove=i;
                lowestScore=v;
            }

            //Prune trees
            if(lowestScore<=alpha && (Math.random()>.5)){
                //System.out.println("Min pruning LowestScore:"+lowestScore+" Alpha:"+alpha);
                int [] values={lowestScore,lowestMove};
                return values;
            }
            beta=Math.min(beta, lowestScore);

        }//end for

                //System.out.println("Min Counter="+counter+" Alpha:"+alpha+" Beta:"+beta);

        //Return both score and move becuase move is needed for final decision
        int [] values={lowestScore,lowestMove};
        return values;
    }//end maxValue

    //Check to see if gameover or maxdepth reached
    private boolean terminalTest(Board board,int counter){
        if(board.isWon()!=0)
            return true;
        if(counter>=maxDepth*10)
            return true;
        return false;
    }

    //Get score of game and return it in array with -1 as a placeholder for the move;
    private int [] utility(Board board,int counter){

        int score;
        int winner=board.isWon();
        if(winner!=color && winner!=0){
          // System.out.println("My color is "+color);
          //System.out.println("winner is "+board.isWon());
            score=this.MINAMOUNT+counter;
        }
        else if(winner==color){
            //System.out.println("My color is "+color);
           // System.out.println("winner is "+board.isWon());
            score=this.MAXAMOUNT-counter;
        }
        else{
            score=getScore(board);
        }
        int [] values={score,-1};
        return values;
    }

    private int getScore(Board board){
        int score1=getEOScore(board,color);
        int score2=getEOScore(board,(color+1)%2);

        return score1-score2;
    }

    private int getEOScore(Board board, int playerColor){

        int [][] array=board.board;
        int threat=0;
        int threatRow=0;
        boolean noEmpty=true;

        int current=playerColor%2;
        int counter=0;
        int place;

        //check horizontal win
        for(int x=0;x<board.vertical;x+=1){
            counter=0;
            noEmpty=true;
            for(int y=0;y<board.horizontal;y++){
                if(array[y][x]==current){
                    counter++;
                }
                // && (x%2)==current
                else if(array[y][x]==0 && ((x+1<board.vertical && array[y][x+1]==0) || x+1>=board.vertical) && noEmpty){
                    counter++;
                    threatRow=x;
                    noEmpty=false;
                }

                if(counter==4 && current!=0){
                    threat+=1+threatRow*threatRow*threatRow*10;
                    break;
                }
            }//end for
        }//end for

        
        //Postivie board.horizontal ABOVE diagnoal
        current=1;
        counter=0;
        noEmpty=true;
        for(int x=0;x<board.horizontal;x++){
            place=x;
            counter=0;
            for(int y=0;y<board.vertical;y++){
                if(array[place][y]==current)
                    counter++;
                // && (y%2)==current
                else if(array[place][y]==0 && y+1<board.vertical && array[place][y+1]==0 && noEmpty){
                    counter++;
                    threatRow=x;
                    noEmpty=false;
                }

                if(counter==4 && current!=0){
                    threat+=1+threatRow*threatRow*threatRow*10;
                    break;
                }

                if(++place>=board.horizontal){
                    counter=0;
                    break;
                }
            }//end for
        }//end for

       //Postivie board.horizontal BELOW diagnoal
        current=1;
        counter=0;
        noEmpty=true;
        for(int x=0;x<board.vertical;x++){
            place=x;
            counter=0;
            for(int y=0;y<board.horizontal;y++){
                if(array[y][place]==current){
                    counter++;
                }
                // && (place%2)==current
                else if(array[y][place]==0 && place+1<board.vertical && array[y][place+1]==0 && noEmpty){
                    counter++;
                    threatRow=x;
                    noEmpty=false;
                }

                if(counter==4 && current!=0){
                    threat+=1+threatRow*threatRow*threatRow*10;
                    break;
                }

                if(++place>=board.vertical){
                    counter=0;
                    break;
                }
            }//end for
        }//end for

       //NEGATIVE board.horizontal ABOVE diagnoal
        current=1;
        counter=0;
        noEmpty=true;
        for(int x=0;x<board.horizontal;x++){
            place=x;
            counter=0;
            for(int y=board.vertical-1;y>=0;y--){
                if(array[place][y]==current)
                    counter++;
                // && (y%2)==current
                else if(array[place][y]==0 && y+1<board.vertical && array[place][y+1]==0 && noEmpty){
                    counter++;
                    threatRow=x;
                    noEmpty=false;
                }

                if(counter==4 && current!=0){
                    threat+=1+threatRow*threatRow*threatRow*10;
                    break;
                }

                if(++place>=board.horizontal){
                    counter=0;
                    break;
                }
            }//end for
        }//end for

       //NEGATIVE board.horizontal BELOW diagnoal
        current=1;
        counter=0;
        noEmpty=true;
        for(int x=board.vertical-1;x>=0;x--){
            place=x;
            counter=0;
            for(int y=0;y<board.horizontal;y++){
                if(array[y][place]==current)
                    counter++;
                // && (place%2)==current
                else if(array[y][place]==0 && place+1<board.vertical && array[y][place+1]==0 && noEmpty){
                    counter++;
                    threatRow=x;
                    noEmpty=false;
                }

                if(counter==4 && current!=0){
                  threat+=1+threatRow*threatRow*threatRow*10;
                  break;
                }

                if(--place<0){
                    counter=0;
                    break;
                }
            }//end for
        }//end for

        return threat;
        
    }//end getScore

    private int[] getFirstMove(Board board){
        int[][]array=board.board;

        int move=3;
        if(array[2][5]!=0)
            move=2;
        else if(array[3][5]!=0)
            move=3;
        else if(array[4][5]!=0)
            move=4;

        System.out.println("Move: "+move);
        int num[]={1,2,3,4,5};
        int rand=(int)(Math.random()*10);
        if(rand>8)
            move=num[(int)(Math.random()*4)];
        int[] temp={0,move};
        return temp;
    }

    private boolean checkLegitMove(Board board,int i){
        int [][] array=board.board;
        if(array[i][0]==0)
            return true;
        return false;
    }


}

/*
 *  int [][] array=board.board;
        for(int i=0;i<board.horizontal;i++){
            for(int j=0;j<board.vertical;j++){
                if(array[i][j]!=color)
                    continue;
                if(i+1<board.horizontal && array[i+1][j]!=((color-1)%2))
                    score++;
                if(i+1<board.horizontal && j+1<board.vertical && array[i+1][j+1]!=((color-1)%2))
                    score++;
                if(j+1<board.vertical && array[i][j+1]!=((color-1)%2))
                    score++;
                if(i-1>=0 && j+1<board.vertical && array[i-1][j+1]!=((color-1)%2))
                    score++;
                if(i-1>=0 && array[i-1][j]!=((color-1)%2))
                    score++;
                if(i-1>=0 && j-1>=0 && array[i-1][j-1]!=((color-1)%2))
                    score++;
                if(j-1>=0 && array[i][j-1]!=((color-1)%2))
                    score++;
                if(i+1>board.horizontal && j-1>=0 && array[i+1][j-1]!=((color-1)%2))
                    score++;
                //if()
            }//end for
        }//end for
 * */