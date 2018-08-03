/*==============================================================================
|     Assignment: GameOfLife.java
|         Author: Kyle Reese Almryde
| Sect. Leader: Kate Maroney
| 
|        Course: CSc 227
|    Instructor: Rick Mercer
|      Due Date: September 18th, 2012, at 9:00 pm
| 
|  Description: This program models John Conway's Game of Life.
|                
|                
|                
|                
| Deficiencies: None; this program meets specifications.
*=============================================================================*/

import java.util.*;        // Gives easy access to Java API's "util" package


public class GameOfLife {

    private int nRows;
    private int nCols;
    private int [][] society;

    /*---------------------------------------------------------------------
    |          Method: GameOfLife
    |
    |         Purpose: This is the constructor method for the GameOfLife Class
    |                  It creates a society with no cells but space to store
    |                  row*cols cells.
    |
    |      Parameters: int rows: The height of the grid that shows the cells
    |                  int cols: The width of the grid that shows the cells
    |                 
    |         Returns: None
    *-------------------------------------------------------------------*/
    
    public GameOfLife(int rows, int cols) {
        nRows = rows; 
        nCols = cols;
        society = new int[numberOfRows()][numberOfColumns()];
    
    } // End of GameOfLife


    /*---------------------------------------------------------------------
    |          Method: numberOfRows
    |
    |         Purpose: This is the getter method which return the number of
    |                  rows within the society. This can then be indexed
    |                  from 0..numberOfRows()-1
    |
    |      Parameters: None
    |                 
    |         Returns: The height of the society
    *-------------------------------------------------------------------*/
    
    public int numberOfRows() {
        return nRows;
        
    } // End of numberOfRows
    

    /*---------------------------------------------------------------------
    |          Method: numberOfColumns
    |
    |         Purpose: This is the getter method which returns the number
    |                  columns within the society. This can then be indexed
    |                  from 0..numberOfColumns()-1
    |
    |      Parameters: None
    |                 
    |         Returns: The width of the society
    *-------------------------------------------------------------------*/
    
    public int numberOfColumns() {
        return nCols;
        
    } // End of numberOfColumns


    /*---------------------------------------------------------------------
    |          Method: growCellAt
    |
    |         Purpose: This method places a new cell in the society.
    |
    |      Parameters: int row: The row to grow the cell
    |                  int col: The column to grow the cell
    |                 
    |    Precondition: row and col are within range
    |
    |         Returns: None
    *-------------------------------------------------------------------*/
    
    public void growCellAt(int row, int col) {
        society[row][col] = 1;
        
    } // End of growCellAt

 
    /*---------------------------------------------------------------------
    |          Method: cellAt
    |
    |         Purpose: Return true if there is a cell at the given row
    |                  and column. Return false if there is none at the 
    |                  specified location.
    |
    |      Parameters: int row: The row to check
    |                  int col: The column to check
    |                 
    |         Returns: True if there is a cell at the given row and column
    |                  false if none.
    *-------------------------------------------------------------------*/
    
    public boolean cellAt(int row, int col) {
        if (society[row][col] == 0) {
            return false;
        } else {
            return true;
        }
        
    } // End of cellAt


    /*---------------------------------------------------------------------
    |          Method: toString
    |
    |         Purpose: Return one big string of cells to represent the
    |                  current state of the society of cells. It's intended
    |                  use is to facilitate visual inspection of the society
    |                  to ensure it is populating as expected. 
    |
    |      Parameters: None
    |                 
    |         Returns: A textual representation of this society of cells
    *-------------------------------------------------------------------*/
    @Override
    public String toString() {
        String result = "";

        for (int row = 0; row < numberOfRows(); row++) {
            for (int col = 0; col < numberOfColumns(); col++) {
                result += " " + society[row][col];
            }
        }
        return result;
        
    } // End of toString


    /*---------------------------------------------------------------------
    |          Method: neighborCount
    |
    |         Purpose: Count the neighbors around the given location. Uses
    |                  wrap around. A cell in row 0 has neighbors in the 
    |                  last row if a cell is in the same column, or the
    |                  column to the left or right. 
    |
    |      Parameters: int row: The row to check
    |                  int col: The column to check
    |                 
    |         Returns: The number of neighbors around any cell using
    |                  wrap around.
    *-------------------------------------------------------------------*/
    
    public int neighborCount(int row, int col) {
        int neighbors = 0;
        int numRows = numberOfRows();
        int numCols = numberOfColumns();

        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            int r = ((row + i) + numRows) % numRows;
            int c = ((col + j) + numCols) % numCols;
            if (!(r == row && c == col)) {
                if(society[r][c] == 1) {
                    neighbors++;
                }
            }
          }
        }
        return neighbors;

    } // End of neighborCount

    /*---------------------------------------------------------------------
    |          Method: update
    |
    |         Purpose: This method updates the state to represent the next
    |                  society. Typically some cells will die off while 
    |                  others are born.
    |
    |      Parameters: None
    |                 
    |         Returns: None
    *-------------------------------------------------------------------*/
    
    public void update() {
        int [][] nextSociety = new int[numberOfRows()][numberOfColumns()];
        for (int row = 0; row < numberOfRows(); row++) {
            for (int col = 0; col < numberOfColumns(); col++) {
                int neighbors = neighborCount(row,col);

                if (society[row][col] == 1) {
                    if (neighbors > 3 || neighbors < 2) {
                        nextSociety[row][col] = 0;
                    } else {
                        nextSociety[row][col] = 1;
                    }
                } else {
                    if (neighbors == 3)
                        nextSociety[row][col] = 1;
                }   
            }
        }
        society = nextSociety;
        
    } // End of update

} // End of GameOfLife Class
