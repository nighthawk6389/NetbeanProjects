/*
 * SudokuSolver.java
 *
 * Created on December 8, 2009, 5:16 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Sudoku;

/**
 *
 * @author elkobi
 */
public class SudokuSolver {
    static int [][] field;   
    static int x;
    /** Creates a new instance of SudokuSolver */
    public SudokuSolver() {
    }
    public static boolean checkValid(int [][] puzzle){
        //checking horizontal
        for(int x=0;x<puzzle.length;x++){
            for(int y=0;y<puzzle[x].length;y++){
                for(int z=y;z<puzzle[x].length;z++){
                    if(puzzle[x][y]==puzzle[x][z] && y!=z && puzzle[x][y]!=0)
                        return false;
                }
            }
        }
        //checking vertical
        for(int x=0;x<puzzle.length;x++){
            for(int y=0;y<puzzle[x].length;y++){
                for(int z=y;z<puzzle[x].length;z++){
                    if(puzzle[y][x]==puzzle[z][x] && y!=z && puzzle[y][x]!=0)
                        return false;
                }
            }
        }
        int x1,y1,x2,y2;
        //checking box--NOT FINISHED
       for(int z=0;z<puzzle.length;z++){
           // System.out.println("Z= "+z);
            for(int x=0;x<puzzle.length;x++){
                //System.out.println("X= "+x);
                for(int y=x+1;y<puzzle.length;y++){
                    //System.out.println("Y= "+y);
                    //System.out.println("Comparing [x]="+((x/3)+(z/3*3))+" [y]="+((x%3)+(z%3*3))+" [x]="+((z/3*3)+((int)(y/3)))+" [y]="+((z%3*3)+(y%3)));
                    x1=((x/3)+(z/3*3));
                    y1=((x%3)+(z%3*3));
                    x2=((z/3*3)+((int)(y/3)));
                    y2=((z%3*3)+(y%3));
                    if(puzzle[x1][y1]==0)
                        break;
                    if(puzzle[x1][y1]==puzzle[x2][y2]){
                        //System.out.println("Returning FALSE");
                        return false;
                    }
                }//end y
            }//end x
       }//end z
        //System.out.println("Returning TRUE");
        return true;
    }//end valid

    public static boolean checkComplete(int[][]puzzle){
       // if(!checkValid(puzzle))
           // return false;
        for(int i=0;i<puzzle.length;i++){
            for(int j=0;j<puzzle.length;j++){
                if(puzzle[i][j]==0)
                   return false;
            }
        }
       // System.out.println("Returning true from checkComplete");
        return true;
    }

    public static int[][] solvePuzzle(int [][] temp){
        field=temp.clone();
        solve(field,0,0,1);
        return field;
    }//end solvePuzzle

    private static boolean solve(int[][]temp,int q,int w, int e){
        //System.out.println("SOLVING..... X="+x);
       // print();
        for(int i=q;i<temp.length;i++){
           // System.out.println("I= "+i);
            for(int k=w;k<temp.length;k++){
                //System.out.println("K= "+k);
                if(temp[i][k]!=0){
                     //   System.out.println("Breaking..");
                        continue;
                    }
                for(int z=e;z<temp.length+1;z++){
                   // System.out.println("Z= "+z);

                    //int[][]hold=temp.clone();
                   // hold[i][k]=z;
                    temp[i][k]=z;
                    //System.out.println("I= "+i+" K= "+k+" Z="+z);
                   //  System.out.println("Hold is");
                    //print(hold);
                    //print(temp);
                    if(checkValid(temp)){
                        if(checkComplete(temp)){
                            print(temp);
                            field=temp.clone();
                            System.out.println("Complete");
                            return true;
                        }//end checkComplete
                        //System.out.println("Going into solve...Added to["+i+"]["+k+"]= "+z);
                        x++;
                        if(solve(temp,0,0,1))
                            return true;
                    }//end check valid
                    else{
                        temp[i][k]=0;
                       // System.out.println("CheckValid is false");
                    }//end else
                }//end z
                if(temp[i][k]==0){
                    x--;
                    return false;
                }
            }//end k
        }//end i
       // print(temp);
        //System.out.println("Solve is returning false");
        return false;
    }//end solve

     public static void print(){
        for(int i=0;i<field.length;i++){
            System.out.print("|");
            for(int k=0;k<field[i].length;k++){
                if(field[i][k]!=0)
                    System.out.print(field[i][k]+"|");
                else
                    System.out.print(" |");
            }
            System.out.println();
        }
    }//end print

      public static void print(int [] [] temp){
        for(int i=0;i<temp.length;i++){
            System.out.print("|");
            for(int k=0;k<temp[i].length;k++){
                if(temp[i][k]!=0)
                    System.out.print(temp[i][k]+"|");
                else
                    System.out.print(" |");
            }
            System.out.println();
        }
    }//end print
}//end class