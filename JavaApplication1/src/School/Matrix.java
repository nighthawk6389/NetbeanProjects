/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package School;

/**
 *
 * @author Mavis Beacon
 */
public class Matrix {
    double [][] matrix;

    public static void main(String args []){
        double [][] temp=new double[3][3];
        for(int x=0;x<temp.length;x++){
            for(int y=0;y<temp[x].length;y++){
                temp[x][y]=(int)(Math.random()*10);  
            }//end for
        }//end for
        temp[0][0]=0;
        //temp[1][0]=0;
        //temp[2][0]=0;

        Matrix matrix=new Matrix(temp);
    }//end main

    public Matrix(double [][] matrix){
        this.matrix=matrix;
        printMatrix();
        solveMatrix();
    }//end default

    public Matrix(Rational [][] matrix){
        //this.matrix=matrix;
        printMatrix();
        solveMatrix();
    }//end default

    public void solveMatrix(){
        int sX=0;

        ///mainSolving
        for(int x=0;x<matrix[0].length;x++){
            sX=getFirstNonZero(sX);
            System.out.println("sX="+sX);
            if(sX==-1)
                break;
            if(matrix[0][sX]==0)
                interchangeForNonZero(sX);
            System.out.println(matrix[0][sX]+" After Interchange");
            printMatrix();

            if(matrix[x][sX]!=1)
                multiplyMatrixRow(x,1/matrix[x][sX]);
            System.out.println(matrix[0][sX]+" After Multiple");
            printMatrix();

           System.out.println("sX="+sX+" x="+x);
           addMultipleToRows(sX,x);
           System.out.println(matrix[0][sX]+" After Addition");
           printMatrix();
           sX++;
           System.out.println("sX="+sX);
        }//end for

    }//end solveMatrix

    public int getFirstNonZero(int sX){
        for(int y=0;y<matrix.length;y++){
            for(int x=sX;x<matrix[y].length;x++){
                System.out.println("Matrix["+x+"]["+y+"]="+matrix[x][y]);
                if(matrix[x][y]!=0){
                    return y;
                }//end if
            }//end for
        }//end for
        return -1;
    }//end getFirstNonZero;

    public void interchangeForNonZero(int sX){
        int firstXNonZero=0;
        for(int y=0;y<matrix[0].length;y++){
            if(matrix[y][sX]!=0){
                firstXNonZero=y;
                break;
            }
        }//end for

        double temp;
        for(int x=0;x<matrix.length;x++){
            temp=matrix[sX][x];
            matrix[sX][x]=matrix[firstXNonZero][x];
            matrix[firstXNonZero][x]=temp;
        }//end for

        return;
    }//end IFNZ

    public void multiplyMatrixRow(int row,double factor){
        for(int x=0;x<matrix.length;x++){
            matrix[row][x]=factor*matrix[row][x];
        }//end for
    }//end multipleMatrixRow

    public void addMultipleToRows(int row,int column){
        double multiple=0;
        for(int x=row+1;x<matrix[0].length;x++){
            multiple=matrix[x][column];
            System.out.println("Multiple="+multiple);
            if(multiple!=0)
                addRowToRow(x,row,-multiple);
        }
    }//end AMTR

    public void addRowToRow(int row1,int row2,double multiple){
        for(int x=0;x<matrix.length;x++){
            matrix[row1][x]=matrix[row1][x]+multiple*matrix[row2][x];
        }//end for
    }//end addRowToRow

    public void printMatrix(){
        for(int x=0;x<matrix.length;x++){
            for(int y=0;y<matrix[x].length;y++){
                System.out.print("|"+matrix[x][y]);
            }//end for
            System.out.println();
        }//end for
    }//end printmatrix
}//end class
