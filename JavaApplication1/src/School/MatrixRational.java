/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package School;

/**
 *
 * @author Mavis Beacon
 */
public class MatrixRational{
    Rational [][] matrix;

    public static void main(String args []){
        Rational [][] temp=new Rational[3][4];
        for(int x=0;x<temp.length;x++){ 
            for(int y=0;y<temp[x].length;y++){
                temp[x][y]=new Rational((int)(Math.random()*10),1);
            }//end for
        }//end for
        //temp[0][1]=new Rational(0,0);
        //temp[1][1]=new Rational(0,0);
        //temp[2][1]=new Rational(0,0);

        MatrixRational matrix=new MatrixRational(temp);
    }//end main

    public MatrixRational(Rational [][] matrix){
        this.matrix=matrix;
        printMatrix();
        solveMatrix();
    }//end default

    public void solveMatrix(){
        int sX=0;

        ///mainSolving
        for(int x=0;x<matrix[0].length;x++){
            System.out.println("Loop Over X="+x);
            printMatrix();
            sX=getFirstNonZero(sX);
            System.out.println("sX="+sX);
            if(sX==-1)
                break;
            if(matrix[x][sX].equals(Rational.ZERO))
                interchangeForNonZero(sX);
            System.out.println(matrix[x][sX]+" After Interchange");
            printMatrix();

            System.out.println(matrix[x][sX].reverse()+" Before Multiple");
            if(!matrix[x][sX].equals(Rational.ZERO))
                multiplyMatrixRow(x,matrix[x][sX].reverse());
            System.out.println(matrix[x][sX]+" After Multiple");
            printMatrix();

           System.out.println("sX="+sX+" x="+x);
           addMultipleToRows(sX,x);
           System.out.println(matrix[0][sX]+" After Addition");
           printMatrix();
           sX++;
           System.out.println("sX="+sX);
        }//end for

        System.out.println("COmmencing Jordan");
        for(int x=matrix.length-1;x>=0;x--){
            System.out.println("X= "+x);
            int pos=findOne(x);
            if(pos==-1)
                continue;
            System.out.println("pos="+pos+" x="+x);
            addReverseMultipleToRows(pos,x);
            printMatrix();
        }//end for

    }//end solveMatrix

    public int getFirstNonZero(int sX){
        for(int y=sX;y<matrix.length;y++){
            for(int x=0;x<matrix[y].length;x++){
                System.out.println("Matrix["+y+"]["+x+"]="+matrix[y][x]);
                if(!matrix[y][x].equals(Rational.ZERO)){
                    return y;
                }//end if
            }//end for
        }//end for
        return -1;
    }//end getFirstNonZero; 

    public void interchangeForNonZero(int sX){
        int firstXNonZero=0;
        for(int y=0;y<matrix[0].length;y++){
            if(!matrix[sX][y].equals(Rational.ZERO)){
                firstXNonZero=y;
                break;
            }
        }//end for
        System.out.println("FirstXNonZero="+firstXNonZero);

        Rational temp;
        for(int x=0;x<matrix.length;x++){
            temp=matrix[sX][x];
            matrix[sX][x]=matrix[firstXNonZero][x];
            matrix[firstXNonZero][x]=temp;
        }//end for

        return;
    }//end IFNZ

    public void multiplyMatrixRow(int row,Rational factor){
        for(int x=0;x<matrix.length;x++){
            matrix[row][x]=factor.multiply(matrix[row][x]);
        }//end for
    }//end multipleMatrixRow

    public void addMultipleToRows(int column,int row){
        Rational multiple;
        for(int x=row+1;x<matrix.length;x++){
            multiple=matrix[x][column];
            System.out.println("Multiple="+multiple);
            if(!multiple.equals(Rational.ZERO))
                addRowToRow(x,row,multiple.switchSigns());
        }
    }//end AMTR

    public void addReverseMultipleToRows(int column,int row){
        Rational multiple;
        for(int x=row-1;x>=0;x--){
            multiple=matrix[x][column];
            System.out.println("Multiple="+multiple);
            if(!multiple.equals(Rational.ZERO))
                addRowToRow(x,row,multiple.switchSigns());
        }
    }//end AMTR


    public void addRowToRow(int row1,int row2,Rational multiple){
        for(int x=0;x<matrix.length;x++){
            matrix[row1][x]=matrix[row1][x].add(multiple.multiply(matrix[row2][x]));
        }//end for
    }//end addRowToRow

    public int findOne(int row){
        for(int x=0;x<matrix[row].length;x++){
            if(matrix[row][x].equals(Rational.ONE))
                return x;
        }//end for
        return -1;
    }//end findOne

    public void printMatrix(){
        for(int x=0;x<matrix.length;x++){
            for(int y=0;y<matrix[x].length;y++){
                System.out.print("|"+matrix[x][y]);
            }//end for
            System.out.println();
        }//end for
    }//end printmatrix
}//end class
