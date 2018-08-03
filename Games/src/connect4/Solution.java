    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Mavis Beacon
 */
public class Solution {
    
    ArrayList<Position> squares=new ArrayList<Position>();
    ArrayList<Position> involved=new ArrayList<Position>();
    Constants name;
    ArrayList<Solution> claimEvenSolutions=new ArrayList<Solution>();
    ArrayList<Solution> verticalSolutions=new ArrayList<Solution>();
    PossibleGroup solutionToThreat = null;
    
    private boolean isSquare1Playable=false;
    private boolean isSquare2Playable=false;
    
    int solutionListNumber = -1;
    
    public Solution(){
        name=Constants.EMPTY;
    }
    
    public Solution(ArrayList<Position> squares,ArrayList<Position> involved, Constants name){
        this.squares=squares;
        this.involved=involved;
        this.name=name;
    }
    
    public Solution(Solution s){
        this.squares=s.squares;
        this.involved=s.involved;
        this.name=s.name;
    }
    
    public boolean solvesClaimEven(PossibleGroup group){
        int contains = 0;
        for(Position solver: squares){
            for(Position p: group.getGroup()){
                if(p.equals(solver)){
                    contains++;
                }//end if
            }//end for
        }//end for
        
        if(contains < 1)
            return false;
        return true;
    }//end solves
    
     private boolean solvesVertical(PossibleGroup group) {
        int contains=0;
        for(Position solver: squares){
            for(Position p: group.getGroup()){
                if(p.equals(solver)){
                    contains++;
                }//end if
            }//end for
        }//end for
         if(contains < 2)
             return false;
        return true;
        
    }//end solvesVertical
     
     private boolean solvesAfterEven(PossibleGroup group) {
         int contains=0;
        for(Position solver: squares){
            contains=0;
            for(Position p: group.getGroup()){
                if(p.x == solver.x && p.y <= solver.y){
                    contains++;
                }//end if
            }//end for
        }//end for
        
        if(contains < 2)
            return false;
        return true;
    }
     
     private boolean solvesLowInverse(PossibleGroup group) {
         int contains=0;
        for(Position solver: squares){
            contains=0;
            for(Position p: group.getGroup()){
                if(p.equals(solver)){
                    contains++;
                }//end if
            }//end for
        }//end for
        if(contains < 2)
            return false;
        return true;
    }
     
     private boolean solvesHighInverse(PossibleGroup group) {
        int containsUpper=0;
        for(Position solver: squares){
            for(Position p: group.getGroup()){
                if(p.equals(solver)){
                    containsUpper++;
                }//end if
            }//end for
        }//end for
        
        int containsLowerAndUpper=0;
        int containsUpperAndLower=0;
        for(Position p: group.getGroup()){
            if(this.isSquare1Playable){
                if(p.equals(involved.get(2)) || p.equals(squares.get(1)))
                    containsLowerAndUpper++;
            }//end if
            if(this.isSquare2Playable){
                if(p.equals(involved.get(3)) || p.equals(squares.get(0)))
                    containsUpperAndLower++;
            }//end if
        }//end for
        
        if(containsLowerAndUpper < 2 && containsUpperAndLower <2 && containsUpper < 2)
            return false;   
        return true;
    }//end highInverse
     
     private boolean solvesBaseClaim(PossibleGroup group) {
        
        int containsEitherFirstAndSecondUpperORSecondAndThird=0;
        int containsSecondAndThird=0;
        for(Position p: group.getGroup()){
            if(p.equals(squares.get(0)) || p.equals(squares.get(1)))
                containsEitherFirstAndSecondUpperORSecondAndThird++;
        }//end for
        
        if(containsEitherFirstAndSecondUpperORSecondAndThird >= 2){
            return true;   
        }
        if(containsSecondAndThird >= 2){
            return true;
        }
        return false;
    }
     
     private boolean solvesBefore(PossibleGroup group) {
         int contains=0;
         
         if( this.solvesVertical(group) )
             return true;
         if( this.solvesClaimEven(group) )
             return true;
         
        for(Position solver: squares){
            for(Position p: group.getGroup()){
                if(p.x == solver.x && p.y == solver.y-1){
                    contains++;
                }//end if
            }//end for
        }//end for
        
        if(contains < squares.size())
            return false;
        return true;
    }
    
    public boolean solves(PossibleGroup group, boolean solvesForName){
        if(this.name == Constants.CLAIMEVEN)
            return solvesClaimEven(group);
        if(this.name == Constants.VERTICAL)
            return solvesVertical(group);
        if(this.name == Constants.AFTEREVEN)
            return solvesAfterEven(group);
        if(this.name == Constants.LOWINVERSE)
            return solvesLowInverse(group);
        if(this.name == Constants.HIGHINVERSE)
            return solvesHighInverse(group);
        if(this.name == Constants.BASECLAIM)
            return solvesBaseClaim(group);
        if(this.name == Constants.BEFORE)
            return solvesBefore(group);
        
        return false;
    }//end 
    
    public boolean sharesSquares(Solution s2){
        for( Position pos : s2.squares) {
            if( this.squares.contains(pos))
                return true;
        }
        return false;
        
    }
    
    @Override
    public String toString(){
        return "("+name+" "+squares+" "+involved+")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.squares != null ? this.squares.hashCode() : 0);
        hash = 79 * hash + (this.involved != null ? this.involved.hashCode() : 0);
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object o){
        if( o instanceof ArrayList)
           return getIsEqualToArrayList(o);
        if( !(o instanceof Solution) )
            return false;
        Solution s=(Solution)o;
        if(this.involved.equals(s.involved) && this.squares.equals(s.squares) && this.name.equals(s.name))
            return true;
        return false;
    }

    private boolean getIsEqualToArrayList(Object o) {
        System.out.println("GIETA");
        return this.squares.equals(o);
    }
    
    public void setSquare1PlayableForHighInverse(boolean isPlayable){
        this.isSquare1Playable = isPlayable;
    }
    public void setSquare2PlayableForHighInverse(boolean isPlayable){
        this.isSquare2Playable = isPlayable;
    }
    
    
    public ArrayList<Solution> getClaimEvenSolutions() {
        return claimEvenSolutions;
    }

    public void setClaimEvenSolutions(ArrayList<Solution> claimEvenSolutions) {
        this.claimEvenSolutions = claimEvenSolutions;
    }

    public ArrayList<Solution> getVerticalSolutions() {
        return verticalSolutions;
    }

    public void setVerticalSolutions(ArrayList<Solution> verticalSolutions) {
        this.verticalSolutions = verticalSolutions;
    }
    
    public void addClaimEvenSolution(Solution s){
        this.claimEvenSolutions.add(s);
    }
    public void addVerticalSolution(Solution s){
        this.verticalSolutions.add(s);
    }

    public void solutionToThreat(PossibleGroup group) {
        this.solutionToThreat = group;   
    }

}
