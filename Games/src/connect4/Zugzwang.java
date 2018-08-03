/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */
package connect4;

import java.util.*;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 *
 * @author Mavis Beacon
 */
public class Zugzwang {
    
    List<PossibleGroup> threats = new ArrayList<PossibleGroup>();
    Map <PossibleGroup, ArrayList<Solution>> solutions=new HashMap<PossibleGroup, ArrayList<Solution>>();
    List<Solution> listOfSolutions = new ArrayList<Solution>();
    
    int color;
    
    public Zugzwang(int color){
        this.color=color;
    }
    
    public static void main(String args []){
        
        Zugzwang z=new Zugzwang(2);
        
        Board b=new Board();
        b.move(0, 0);
        b.move(1, 1);
        /*
        b.move(2,0);
        b.move(2,0);
        b.move(2,1);
        b.move(2,0);
        b.move(2,1);
        b.move(2,0);
        b.move(3,0);
        b.move(3,1);
        b.move(3,0);
        b.move(3,1);
        b.move(3,1);
        b.move(3,0);
        b.move(4,1);
        b.move(4,1);
        b.move(6,1);
        b.move(6,0);
        b.move(6,1);
        b.move(6,0);
        b.move(6,1);
        b.move(6,0);
         * *
         */
        b.drawBoard(true); 
         
        PossibleGroup pg = new PossibleGroup();
        pg.addPosition(new Position(0,3));
        pg.addPosition(new Position(1,3));
        pg.addPosition(new Position(2,3));
        pg.addPosition(new Position(3,3));
        //z.threats.add(pg);
        
        z.getAllThreats(new Board(b));
        System.out.println("Threats: " + z.threats.size());
        System.out.println(z.threats);

        
        //CHECK BEFORE
        z.before(b);
        z.baseClaim(b);
        z.highInverse(b);
        z.lowInverse(b);
        z.afterEven(b);
        z.vertical(b);
        z.baseInverse(b);
        z.claimEven(b); 
        //System.out.println("\n" + z.solutions);
        System.out.println("Solutions: "+z.solutions.size());
        
        if( z.solutions.size() < z.threats.size()){
            System.out.println("NOT ENOUGH SOLUTIONS!!!!!!!");
            System.out.println();
            System.out.println(z.solutions);
            return;
        }
        
        int count = 0;
        for(ArrayList<Solution> sol : z.solutions.values()){
            count += sol.size();
        }
        System.out.println("Count: " + count + " Avg: " + count*1.0/z.solutions.size());
         
        //System.out.println(z.threats.removeAll(z.solutions.keySet()));
        //System.out.println("Threats left after remove: " + z.threats.size() + " " + z.threats);
//        /z.threatMap();
        //z.visualMap(b);
        
       
        
        int [][] adjMatrix = z.constructAdjacencyMatrix();
        /*
        for(int x=0;x<adjMatrix.length;x++){
            System.out.println();
            for(int y=0;y<adjMatrix[x].length;y++){
                System.out.print(adjMatrix[x][y]+ " ");
            }
        }
         * 
         */

        Map <PossibleGroup, ArrayList<Solution>> sortedMap = z.getSortedMapByKeyLength(z.solutions);
        System.out.println("After Sort: " + sortedMap.size() + " " + z.solutions.size());
        
        
        List<Solution> found = z.findChosenSet( adjMatrix , sortedMap.entrySet() , z.listOfSolutions , new ArrayList() ); 
        if( found != null){
            Set<Solution> set = new HashSet<Solution>();
            set.addAll(found);
            for(Solution s: set){
                System.out.println( s );
            }
        }
        /*
        z.listOfSolutions = z.getListOfSolutionsFromSolutionsMap();
        ArrayList<Integer> list = z.getSolutionIndexes(adjMatrix,new ArrayList<Integer>(),new ArrayList<PossibleGroup>(),0);
        System.out.println(list + "\nSolution Size: " +list.size());
         
        
        ArrayList<Solution> values = new ArrayList<Solution>();
        for(Integer i : list){
            Solution sol = z.listOfSolutions.get(i);
            values.add(sol);
            System.out.println( i + ": " + sol.name + " S: " + sol.squares + " I: " + sol.involved);
        }
        
        int counter = 0;
        for(Solution s1 : values){
            for(Solution s2 : values){ 
                if( !s1.equals(s2) && !z.isSumilAllowed(s1, s2) ){
                    //System.out.println("SIMUL NOT ALLOWED: " + s1.toString() + s2.toString());
                    Integer i1 = z.listOfSolutions.indexOf(s1);
                    Integer i2 = z.listOfSolutions.indexOf(s2);
                    //System.out.println(" " + adjMatrix[i1][i2]);
                    counter++;
                }
            }
        }
        System.out.println("SIMUL NOT ALLOWED Counter : " + counter);
        
        
        z.visualMap(b,values);
         * 
         */
        
        
        
    }
   
    
    public void claimEven(Board b){
        int [][] board=b.board;
        
        List<Solution> possibles=new ArrayList<Solution>();
        ArrayList<Position> squares;
        ArrayList<Position> involved;
        ArrayList<Position> claimEvenPart;
        
        for(int x=0;x<b.horizontal;x++){
            for(int y=0;y<b.vertical;y++){
                squares=new ArrayList<Position>();
                involved=new ArrayList<Position>();
                claimEvenPart=new ArrayList<Position>();
                if(y%2==0 && board[x][y+1]==0 && board[x][y]==0){
                    squares.add(new Position(x,y));
                    involved.add(new Position(x,y+1));
                    claimEvenPart.add(new Position(x,y));
                    Solution solution = new Solution(squares,involved,Constants.CLAIMEVEN); 
                    solution.addClaimEvenSolution(new Solution(new ArrayList(claimEvenPart),new ArrayList(),Constants.CLAIMEVEN));
                    possibles.add(solution);
                    
                }
            }//end for
        }//end for
        
        getSolutionsFromPossibles(possibles);
    }//end claimEven
    
    public void baseInverse(Board b){
        int [][] board=b.board;
       
        Iterator<PossibleGroup> threatIt=threats.iterator();
        
        ArrayList<Position> listGroup1 = new ArrayList<Position>();
        ArrayList<Position> listGroup2 = new ArrayList<Position>();
        PossibleGroup group;
        ArrayList<Position> squares;
        Position p1;
        Position p2;
        int x1;
        int y1;
        int x2;
        int y2;
        while(threatIt.hasNext()){
            squares=new ArrayList<Position>();
            group=threatIt.next();
            listGroup1=group.getGroup();
            for(int i = 0; i< listGroup1.size(); i++){
                p1=listGroup1.get(i);
                x1=p1.x;
                y1=p1.y;
                listGroup2 = group.getGroup();
                for(int j = i+1; j< listGroup2.size(); j++){
                    p2 = listGroup2.get(j);
                    x2=p2.x;
                    y2=p2.y;
                    if(p1.equals(p2))
                        continue;
                    if( board[x1][y1]==0 && ((y1<b.vertical-1 && board[x1][y1+1]!=0) || (y1==5)) && 
                        board[x2][y2]==0 && ((y2<b.vertical-1 && board[x2][y2+1]!=0) || (y2==5))){
                        squares.add(new Position(x1,y1));
                        squares.add(new Position(x2,y2));
                        addToSolutions(group, new Solution(new ArrayList(squares),new ArrayList(),Constants.BASEINVERSE));
                        squares=new ArrayList<Position>();
                    }
                }
            }//end while
        }//end while
    }//end baseInverse
    
    private void vertical(Board b){
        int [][] board=b.board;
        
        List<Solution> possibles=new ArrayList<Solution>();
        ArrayList<Position> squares;
        ArrayList<Position> involved;
        ArrayList<Position> verticalPart;
        
        for(int x=0;x<b.horizontal;x++){
            for(int y=0;y<b.vertical;y++){
                squares=new ArrayList<Position>();
                involved=new ArrayList<Position>();
                verticalPart=new ArrayList<Position>();
                if(y%2==1 && board[x][y]==0 && ((y<b.vertical-1 && board[x][y+1]==0)) ){
                    squares.add(new Position(x,y));
                    squares.add(new Position(x,y+1));
                    verticalPart.add(new Position(x,y));
                    verticalPart.add(new Position(x,y+1));
                    Solution solution = new Solution(squares,involved,Constants.VERTICAL);
                    solution.addVerticalSolution(new Solution(new ArrayList(verticalPart),new ArrayList(),Constants.VERTICAL));
                    possibles.add(solution);
                }
            }//end for
        }//end for
        
        //System.out.println(possibles);
        getSolutionsFromPossibles(possibles);
    }
    
    private void afterEven(Board b){
        int [][] board=b.board;
        
        List<Solution> afterEvenGroups=new ArrayList<Solution>();
        ArrayList<Position> emptySquares;
        ArrayList<Position> involved;
        ArrayList<Position> group;
        ArrayList<Position> claimEvenPart;
        
        int counter=0;
        int emptySquaresNum=0;
        //check horizontal
        for(int x=0;x<b.vertical;x+=2){
            counter=emptySquaresNum=0;
            group=new ArrayList<Position>();
            claimEvenPart=new ArrayList<Position>();
            emptySquares=new ArrayList<Position>();
            for(int y=0;y<b.horizontal;y++){
                if(x%2!=0)
                    continue;
                
                if(board[y][x]==color){
                    counter++;
                    group.add(new Position(y,x));
                }
                else if(board[y][x]==0 && board[y][x+1]==0){
                    counter++;
                    emptySquaresNum++;
                    emptySquares.add(new Position(y,x));
                    claimEvenPart.add(new Position(y,x));
                    group.add(new Position(y,x));
                }
                else{
                    counter=emptySquaresNum=0;
                    emptySquares=new ArrayList<Position>();
                    claimEvenPart=new ArrayList<Position>();
                    group = new ArrayList<Position>();
                }
                
                if(counter>=4){
                    Solution solution = new Solution(new ArrayList(emptySquares),new ArrayList(),Constants.AFTEREVEN);
                    solution.addClaimEvenSolution(new Solution(new ArrayList(claimEvenPart),new ArrayList(),Constants.CLAIMEVEN));
                    afterEvenGroups.add(solution);
                    if(group.get(0).equals(emptySquares.get(0))){
                        emptySquares.remove(0);
                        claimEvenPart.remove(0);
                    }
                }
            }//end for
        }//end for
        
        this.getSolutionsFromPossibles(afterEvenGroups);
        
        //System.out.println(afterEvenGroups);
        //System.out.println(afterEvenGroups.size());
        
    }//end afterEven
    
    private void lowInverse(Board b) {
        int [][] board=b.board;
        
        List<Solution> possibles=new ArrayList<Solution>();
        ArrayList<Position> squares = new ArrayList<Position>();
        ArrayList<Position> involved = new ArrayList<Position>();
        ArrayList<Position> verticalPart = new ArrayList<Position>();
        
        for(int x = 0; x < b.vertical-1; x++){
            for(int y = 0; y < b.horizontal;y++){
                for(int i = 0; i < b.vertical-1; i++){
                    for(int j = y+1; j < b.horizontal; j++){
                        if(( x%2 != 0) && ( i%2 != 0) && 
                           board[y][x]==0 && board[y][x+1]==0 &&
                           board[j][i]==0 && board[j][i+1]==0){
                            squares.add(new Position(y,x));
                            squares.add(new Position(j,i));
                            involved.add(new Position(y,x+1));
                            involved.add(new Position(j,i+1));
                            verticalPart.add(new Position(y,x));
                            verticalPart.add(new Position(y,x+1));
                            Solution vert1 = new Solution(new ArrayList(verticalPart),new ArrayList(),Constants.VERTICAL);
                            verticalPart.clear();
                            verticalPart.add(new Position(j,i));
                            verticalPart.add(new Position(j,i+1));
                            Solution vert2 = new Solution(new ArrayList(verticalPart),new ArrayList(),Constants.VERTICAL);
                            Solution solution = new Solution(new ArrayList(squares),new ArrayList(involved),Constants.LOWINVERSE);
                            solution.addVerticalSolution(vert1);
                            solution.addVerticalSolution(vert1);
                            possibles.add(solution);
                            squares = new ArrayList<Position>();
                            involved = new ArrayList<Position>();
                            verticalPart = new ArrayList<Position>();
                        }//end if
                    }//end j
                }//end i
            }//end y
        }//end x
        
        this.getSolutionsFromPossibles(possibles);
        
        //System.out.println(possibles.size() + " " + possibles);
    }//end lowInverse

    private void highInverse(Board b) {
        int [][] board=b.board;
        
        List<Solution> possibles=new ArrayList<Solution>();
        ArrayList<Position> squares = new ArrayList<Position>();
        ArrayList<Position> involved = new ArrayList<Position>();
         ArrayList<Position> verticalPart = new ArrayList<Position>();
        
        for(int x = 0; x < b.vertical-2; x++){
            for(int y = 0; y < b.horizontal;y++){
                for(int i = 0; i < b.vertical-2; i++){
                    for(int j = y+1; j < b.horizontal; j++){
                        if(( x%2 == 0) && ( i%2 == 0) && 
                                board[y][x]==0 && board[y][x+1]==0 && board[y][x+2]==0 && 
                                board[j][i]==0 && board[j][i+1]==0 && board[j][i+2]==0){
                            squares.add(new Position(y,x));
                            squares.add(new Position(j,i));
                            involved.add(new Position(y,x+1));
                            involved.add(new Position(j,i+1));
                            involved.add(new Position(y,x+2));
                            involved.add(new Position(j,i+2));
                            verticalPart.add(new Position(y,x));
                            verticalPart.add(new Position(y,x+1));
                            Solution vert1 = new Solution(new ArrayList(verticalPart),new ArrayList(),Constants.VERTICAL);
                            verticalPart.clear();
                            verticalPart.add(new Position(j,i));
                            verticalPart.add(new Position(j,i+1));
                            Solution vert2 = new Solution(new ArrayList(verticalPart),new ArrayList(),Constants.VERTICAL);
                            Solution solution = new Solution(new ArrayList(squares),new ArrayList(involved),Constants.HIGHINVERSE);
                            solution.addVerticalSolution(vert1);
                            solution.addVerticalSolution(vert1);
                            if(board[y][x+3]!=0){
                                solution.setSquare1PlayableForHighInverse(true);
                            }
                            if(board[j][i+3]!=0){
                                solution.setSquare2PlayableForHighInverse(true);
                            }
                            possibles.add(solution);
                            squares = new ArrayList<Position>();
                            involved = new ArrayList<Position>();
                            verticalPart = new ArrayList<Position>();
                        }//end if
                    }//end j
                }//end i
            }//end y
        }//end x
        
        this.getSolutionsFromPossibles(possibles);
        
        //System.out.println(possibles.size() + "\n" + possibles);
    }//end highInverse
    
    private void baseClaim(Board b){
        int [][] board = b.board;
        
        List<Solution> possibles=new ArrayList<Solution>();
        ArrayList<Position> squares = new ArrayList<Position>();
        ArrayList<Position> involved = new ArrayList<Position>();
        
        for(int x = 0; x < b.vertical; x++){
            for(int y = 0; y < b.horizontal;y++){
                for(int i = 1; i < b.vertical; i++){
                    for(int j = y+1; j < b.horizontal; j++){
                        for(int k = 0; k < b.vertical; k++){
                            for(int l = j+1; l < b.horizontal; l++){
                                if( board[y][x]==0 && ((x<b.vertical-1 && board[y][x+1]!=0) || (x==5)) && 
                                    board[j][i]==0 && ((i<b.vertical-1 && board[j][i+1]!=0) || (i==5)) && 
                                    board[l][k]==0 && ((k<b.vertical-1 && board[l][k+1]!=0) || (k==5)) &&
                                    (i-1)%2==0){
                                    squares.add(new Position(y,x));
                                    squares.add(new Position(j,i-1));
                                    possibles.add(new Solution(new ArrayList(squares),involved,Constants.BASECLAIM));
                                    squares = new ArrayList<Position>();
                                    squares.add(new Position(j,i));
                                    squares.add(new Position(l,k));
                                    possibles.add(new Solution(new ArrayList(squares),involved,Constants.BASECLAIM));
                                    squares = new ArrayList<Position>(); 
                                }
                            }//end l
                        }//end k
                    }//end j
                }//end i
            }//end y
        }//end x
       
        this.getSolutionsFromPossibles(possibles);
        
    }//end baseClaim

    private void before(Board b){
        int [][] board = b.board;
        
        List<Solution> possibles=new ArrayList<Solution>();
        ArrayList<Position> squares = new ArrayList<Position>();
        ArrayList<Position> involved = new ArrayList<Position>();
       
        ArrayList<PossibleGroup> posGroups = this.getAllPossibleWinningGroups(b);
        for(PossibleGroup posGroup : posGroups){
            squares = new ArrayList<Position>();
            involved = new ArrayList<Position>();
            for(Position p : posGroup.getGroup()){
                //if(board[p.x][p.y]==0)
                    //squares.add(new Position(p.x,p.y-1));
                    squares.add(p);
                //else
                    //involved.add(p);
            }
            possibles.add(new Solution(squares,involved,Constants.BEFORE));
        }
       System.out.println(possibles.size() +"\n"+ possibles);
        this.getSolutionsFromPossibles(possibles); 
        
    }//end baseClaim
    
    
    private void getSolutionsFromPossibles(List<Solution> possibles){
        Set <Solution> uniquePossibles=new HashSet<Solution>(possibles);
     
        Iterator<PossibleGroup> threatIt=threats.iterator();
        Iterator<Solution> possiblesIt;
        PossibleGroup group;

        while(threatIt.hasNext()){
            group=threatIt.next();
            possiblesIt=uniquePossibles.iterator();
            while(possiblesIt.hasNext()){
                Solution s=possiblesIt.next();
                if(s.solves(group,true))
                    addToSolutions(group,new Solution(s)); 
            }//end while
        }
    }
    
    private void addToSolutions(PossibleGroup group, Solution s){
        boolean contains=solutions.containsKey(group);
        ArrayList<Solution> alreadyThere; 
        
        s.solutionToThreat(group);
        
        if(contains){
            alreadyThere=solutions.get(group);
            alreadyThere.add(s);
        }
        else{
            ArrayList<Solution> list=new ArrayList<Solution>();
            list.add(s);
            solutions.put(group,list);
        }
    }
    
    
    public void getAllThreats(Board b){
        int [][] board=b.board;
        
        //int current=color;
        int counter=0;
        int place;
        
        ArrayList<Position> pos=new ArrayList<Position>();

        //check horizontal
        for(int x=0;x<b.vertical;x++){
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=0;y<b.horizontal;y++){
                if(board[y][x]!=color){
                    counter++;
                    pos.add(new Position(y,x));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    threats.add(new PossibleGroup(pos));
                    pos.remove(0);
                }
            }//end for
        }//end for

        
        counter=0;
        pos=new ArrayList<Position>();
        //check vertical
        for(int x=0;x<b.horizontal;x++){
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=0;y<b.vertical;y++){
                if(board[x][y]!=color){
                    counter++;
                    pos.add(new Position(x,y));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    threats.add(new PossibleGroup(pos));
                    pos.remove(0);
                }
            }//end for
        }//end for


        //Postivie horizontal ABOVE diagnoal
        counter=0;
        pos=new ArrayList<Position>();
        for(int x=0;x<b.horizontal;x++){
            place=x;
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=0;y<b.vertical;y++){
                if(board[place][y]!=color){
                    counter++;
                    pos.add(new Position(place,y));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    threats.add(new PossibleGroup(pos));
                    pos.remove(0);
                }

                if(++place>=b.horizontal){
                    counter=0;
                    break;
                }
            }//end for
        }//end for

       //Postivie horizontal BELOW diagnoal
        counter=0;
        pos=new ArrayList<Position>();
        for(int x=0;x<b.vertical;x++){
            place=x;
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=0;y<b.horizontal;y++){
                if(board[y][place]!=color){
                    counter++;
                    pos.add(new Position(y,place));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    threats.add(new PossibleGroup(pos));
                    pos.remove(0);
                }

                if(++place>=b.vertical){
                    counter=0; 
                    break;
                }
            }//end for
        }//end for

       //NEGATIVE horizontal ABOVE diagnoal
        counter=0;
        pos=new ArrayList<Position>();
        for(int x=0;x<b.horizontal;x++){
            place=x;
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=b.vertical-1;y>=0;y--){
                if(board[place][y]!=color){
                    counter++;
                    pos.add(new Position(place,y));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    threats.add(new PossibleGroup(pos));
                    pos.remove(0);
                }

                if(++place>=b.horizontal){
                    counter=0;
                    break;
                }
            }//end for
        }//end for

       //NEGATIVE horizontal BELOW diagnoal
        counter=0;
        pos=new ArrayList<Position>();
        for(int x=b.vertical-1;x>=0;x--){
            place=x;
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=0;y<b.horizontal;y++){
                if(board[y][place]!=color){
                    counter++;
                    pos.add(new Position(y,place));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    threats.add(new PossibleGroup(pos));
                    pos.remove(0);
                }

                if(--place<0){
                    counter=0;
                    break;
                }
            }//end for
        }//end for
        
        Set s=new HashSet(threats);
        
        threats=new ArrayList<PossibleGroup>(s);
  
    }//end getAllThreats
    
    public ArrayList<PossibleGroup> getAllPossibleWinningGroups(Board b){
        int [][] board=b.board;
        
        //int current=color;
        int counter=0;
        int place;
        
        ArrayList<Position> pos=new ArrayList<Position>();
        ArrayList<PossibleGroup> posGroup = new ArrayList<PossibleGroup>();

        //check horizontal
        for(int x=0;x<b.vertical;x++){
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=0;y<b.horizontal;y++){
                if(board[y][x]==color || (board[y][x]==0 && x!=0) ){
                    counter++;
                    pos.add(new Position(y,x));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    posGroup.add(new PossibleGroup(pos));
                    pos.remove(0);
                }
            }//end for
        }//end for

        
        counter=0;
        pos=new ArrayList<Position>();
        //check vertical
        for(int x=0;x<b.horizontal;x++){
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=0;y<b.vertical;y++){
                if(board[x][y]==color || (board[x][y]==0 && y!=0) ){
                    counter++;
                    pos.add(new Position(x,y));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    posGroup.add(new PossibleGroup(pos));
                    pos.remove(0);
                }
            }//end for
        }//end for


        //Postivie horizontal ABOVE diagnoal
        counter=0;
        pos=new ArrayList<Position>();
        for(int x=0;x<b.horizontal;x++){
            place=x;
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=0;y<b.vertical;y++){
                if(board[place][y]==color || (board[place][y]==0 && y!=0) ){
                    counter++;
                    pos.add(new Position(place,y));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    posGroup.add(new PossibleGroup(pos));
                    pos.remove(0);
                }

                if(++place>=b.horizontal){
                    counter=0;
                    break;
                }
            }//end for
        }//end for

       //Postivie horizontal BELOW diagnoal
        counter=0;
        pos=new ArrayList<Position>();
        for(int x=0;x<b.vertical;x++){
            place=x;
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=0;y<b.horizontal;y++){
                if(board[y][place]==color || (board[y][place]==0 && place!=0) ){
                    counter++;
                    pos.add(new Position(y,place));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    posGroup.add(new PossibleGroup(pos));
                    pos.remove(0);
                }

                if(++place>=b.vertical){
                    counter=0; 
                    break;
                }
            }//end for
        }//end for

       //NEGATIVE horizontal ABOVE diagnoal
        counter=0;
        pos=new ArrayList<Position>();
        for(int x=0;x<b.horizontal;x++){
            place=x;
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=b.vertical-1;y>=0;y--){
                if(board[place][y]==color || (board[place][y]==0 && y!=0) ){
                    counter++;
                    pos.add(new Position(place,y));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    posGroup.add(new PossibleGroup(pos));
                    pos.remove(0);
                }

                if(++place>=b.horizontal){
                    counter=0;
                    break;
                }
            }//end for
        }//end for

       //NEGATIVE horizontal BELOW diagnoal
        counter=0;
        pos=new ArrayList<Position>();
        for(int x=b.vertical-1;x>=0;x--){
            place=x;
            counter=0;
            pos=new ArrayList<Position>();
            for(int y=0;y<b.horizontal;y++){
                if(board[y][place]==color || (board[y][place]==0 && place!=0) ){
                    counter++;
                    pos.add(new Position(y,place));
                }
                else{
                    counter=0;
                    pos=new ArrayList<Position>();
                }
                if(counter>=4){
                    posGroup.add(new PossibleGroup(pos));
                    pos.remove(0);
                }

                if(--place<0){
                    counter=0;
                    break;
                }
            }//end for
        }//end for
        
        Set s=new HashSet(posGroup);
        posGroup=new ArrayList<PossibleGroup>(s);
        return posGroup;
    }//end getAllThreats
    
    public int[][] constructAdjacencyMatrix(){
        listOfSolutions = this.getListOfSolutionsFromSolutionsMap();
        int sizeOfSolutions = listOfSolutions.size();
        int [][] adjacencyMatrix = new int[sizeOfSolutions][sizeOfSolutions];
        for(int x = 0; x< adjacencyMatrix.length;x++){
            for(int y = 0; y<adjacencyMatrix[x].length;y++){
                adjacencyMatrix[x][y] = 0;
            }
        }
        int xCount = 0, yCount = 0, solutionCounter = 0;
        
        Iterator<Solution> it1 = listOfSolutions.iterator();
        while(it1.hasNext()){
            Solution sol1 = it1.next();
            Iterator<Solution> it2 = listOfSolutions.iterator();
            yCount = 0;
            while(it2.hasNext()){
                Solution sol2 = it2.next();
                if(yCount <= solutionCounter){
                    yCount++;
                    continue;
                }
                boolean simul1 = isSumilAllowed(sol1,sol2);
                boolean simul2 = isSumilAllowed(sol2,sol1);
                if( simul1 != simul2 ){
                    System.out.println("SIMUL 1 DID NOT EQUAL SIMUL2. " + sol1 + " --- " + sol2);
                }
                if(simul1 && simul2){
                    adjacencyMatrix[solutionCounter][yCount] = 1;
                    adjacencyMatrix[yCount][solutionCounter] = 1;
                }
                yCount++;
            }//end while2
            solutionCounter++;
        }//end while1
        
        return adjacencyMatrix;
    }
    
    private boolean isSumilAllowed(Solution sol1, Solution sol2) {
        
        if(sol1.name.equals(Constants.CLAIMEVEN))
            return claimEvenCombine(sol1,sol2);
        else if(sol1.name.equals(Constants.BASEINVERSE))
            return baseInverseCombine(sol1,sol2);
        else if(sol1.name.equals(Constants.VERTICAL))
            return verticalCombine(sol1,sol2);
        else if(sol1.name.equals(Constants.AFTEREVEN))
            return afterEvenCombine(sol1,sol2);
        else if(sol1.name.equals(Constants.LOWINVERSE))
            return lowInverseCombine(sol1,sol2);
        else if(sol1.name.equals(Constants.HIGHINVERSE))
            return highInverseCombine(sol1,sol2);
        else if(sol1.name.equals(Constants.BASECLAIM))
            return baseClaimCombine(sol1,sol2);
        else if(sol1.name.equals(Constants.BEFORE))
            return beforeCombine(sol1,sol2);
        else if(sol1.name.equals(Constants.SPECIALBEFORE))
            return beforeCombine(sol1,sol2);
        else{
            System.out.println("NO SUMIL FOUND");
            return false;
        }
    }
    
    private boolean claimEvenCombine(Solution sol1, Solution sol2){
        if(sol2.name.equals(Constants.LOWINVERSE) || sol2.name.equals(Constants.HIGHINVERSE)){
            return rule2(sol1,sol2);
        }
        else{
            return rule1(sol1,sol2);
        }
    }
    
    private boolean baseInverseCombine(Solution sol1, Solution sol2){
        return rule1(sol1,sol2);
    }
    
    private boolean verticalCombine(Solution sol1, Solution sol2){
        return rule1(sol1,sol2);
    }
    
    private boolean afterEvenCombine(Solution sol1, Solution sol2){
        if(sol2.name.equals(Constants.AFTEREVEN)){
            return rule3(sol1,sol2);
        }
        else if(sol2.name.equals(Constants.LOWINVERSE) || sol2.name.equals(Constants.HIGHINVERSE)){
            return ( rule1(sol1,sol2) && rule2(sol1,sol2) );
        }
        else if(sol2.name.equals(Constants.BEFORE) || sol2.name.equals(Constants.SPECIALBEFORE)){
            return rule3(sol1,sol2);
        }
        else{
            return rule1(sol1,sol2);
        }
    }
    
    private boolean lowInverseCombine(Solution sol1, Solution sol2){
        if(sol2.name.equals(Constants.CLAIMEVEN)){
            return rule2(sol1,sol2);
        }
        else if(sol2.name.equals(Constants.LOWINVERSE) || sol2.name.equals(Constants.HIGHINVERSE)){
            return rule4(sol1,sol2);
        }
        else if(sol2.name.equals(Constants.BASECLAIM) || sol2.name.equals(Constants.AFTEREVEN)){
            return ( rule1(sol1,sol2) && rule2(sol1,sol2) );
        }
        else if(sol2.name.equals(Constants.BEFORE) || sol2.name.equals(Constants.SPECIALBEFORE)){
            return ( rule2(sol1,sol2) && rule3(sol1,sol2) );
        }
        else{
            return rule1(sol1,sol2);
        }
    }
    
    private boolean highInverseCombine(Solution sol1, Solution sol2){
        if(sol2.name.equals(Constants.CLAIMEVEN)){
            return rule2(sol1,sol2);
        }
        else if(sol2.name.equals(Constants.AFTEREVEN)){
            return ( rule1(sol1,sol2) && rule2(sol1,sol2) );
        }
        else if(sol2.name.equals(Constants.LOWINVERSE) || sol2.name.equals(Constants.HIGHINVERSE)){
            return rule4(sol1,sol2);
        }
        else if(sol2.name.equals(Constants.BASECLAIM) || sol2.name.equals(Constants.BEFORE) || sol2.name.equals(Constants.SPECIALBEFORE)){
            return ( rule1(sol1,sol2) && rule2(sol1,sol2) );
        }
        else{
            return rule1(sol1,sol2);
        }
    }
    
    private boolean baseClaimCombine(Solution sol1, Solution sol2){
        if( sol2.name.equals(Constants.LOWINVERSE) || sol2.name.equals(Constants.HIGHINVERSE)){
            return rule2( sol1, sol2);
        }
        else{
            return rule1(sol1,sol2);
        }
    }
    
    private boolean beforeCombine(Solution sol1,Solution sol2){
        if(sol2.name.equals(Constants.AFTEREVEN) || 
           sol2.name.equals(Constants.BEFORE) || 
           sol2.name.equals(Constants.SPECIALBEFORE)){
            
            return rule3(sol1,sol2);
        }
        else if ( sol2.name.equals(Constants.LOWINVERSE)){
            return ( rule2(sol1,sol2) && rule3(sol1,sol2) );
        }
        else if ( sol2.name.equals(Constants.HIGHINVERSE)){
            return ( rule1(sol1,sol2) && rule2(sol1,sol2) ); 
        }
        else{
            return rule1(sol1,sol2);
        }
    }
    private boolean specialBeforeCombine(Solution sol1, Solution sol2){
        if(sol2.name.equals(Constants.AFTEREVEN)){
            return rule3(sol1,sol2);
        }
        else if(sol2.name.equals(Constants.LOWINVERSE)){
            return ( rule2(sol1,sol2) && rule3(sol1,sol2));
        }
        else if(sol2.name.equals(Constants.HIGHINVERSE)){
            return ( rule1(sol1,sol2) && rule2(sol1,sol2));
        }
        else if(sol2.name.equals(Constants.BEFORE) || sol2.name.equals(Constants.SPECIALBEFORE)){
            return rule3(sol1,sol2);
        }
        else{
            return rule1(sol1,sol2);
        }
    }
    
    private boolean rule1(Solution sol1, Solution sol2){
        for(Position p1 : sol1.squares){
            for(Position p2 : sol2.squares){
                if(p1.equals(p2))
                    return false;
            }
        }
        return true;
    }
    private boolean rule2(Solution sol1, Solution sol2){
        boolean isSol1ClaimEven = sol1.name.equals(Constants.CLAIMEVEN) ||
                                  sol1.name.equals(Constants.AFTEREVEN) || 
                                  sol1.name.equals(Constants.BASECLAIM) || 
                                  sol1.name.equals(Constants.BEFORE) || 
                                  sol1.name.equals(Constants.SPECIALBEFORE);
        boolean isSol2ClaimEven = sol2.name.equals(Constants.CLAIMEVEN) ||
                                  sol2.name.equals(Constants.AFTEREVEN) || 
                                  sol2.name.equals(Constants.BASECLAIM) || 
                                  sol2.name.equals(Constants.BEFORE) || 
                                  sol2.name.equals(Constants.SPECIALBEFORE);
        for(Position p1 : sol1.squares){
            for(Position p2 : sol2.squares){
                if( isSol1ClaimEven && p1.y < p2.y && p1.x==p2.x)
                    return false;
                else if( isSol2ClaimEven && p2.y < p1.y && p2.x==p1.x){
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private boolean rule3(Solution sol1, Solution sol2){
        HashSet<Integer> set1 = new HashSet<Integer>();
        boolean equals = false;
        boolean different = false;
        for(Position p1 : sol1.squares){
            for(Position p2 : sol2.squares){
                if( p1.y == p2.y)
                    equals = true;
                else
                    different = true;
            }
            if ( equals && different )
                return false;
        }

        return true;
    }
    
    private boolean rule4(Solution sol1, Solution sol2){
        for(Position p1 : sol1.squares){
            for(Position p2 : sol2.squares){
                if( p1.equals(p2) || p1.y == p2.y ){
                    return false;
                }
            }
        }
        return true;
    }
    
    public ArrayList<Solution> getListOfSolutionsFromSolutionsMap(){
        
        int count = 0;
        ArrayList<Solution> list = new ArrayList<Solution>();
        for(ArrayList<Solution> singleList : this.solutions.values()){
            for(Solution sol : singleList){
                sol.solutionListNumber = count++;
                list.add(sol);
            }
        }
        return list;
    }//end GLOSFSM
    
    public ArrayList<Integer> getSolutionIndexes(int [][] adjMatrix, ArrayList<Integer> solutionsFoundSoFar, 
            ArrayList<PossibleGroup> threatsSolved, int solutionIndex) {
        
        if(threatsSolved.size() >= threats.size()){
            return new ArrayList<Integer>();
        }
        else if(solutionIndex > adjMatrix.length){
            return new ArrayList<Integer>();
        }
        else{
            Solution solution = listOfSolutions.get(solutionIndex);
            PossibleGroup threatSolvedFromSolution = solution.solutionToThreat;
            if(threatsSolved.contains(threatSolvedFromSolution))
                return new ArrayList<Integer>();
            

            ArrayList<Integer> solutionIndexes = new ArrayList<Integer>();
            solutionIndexes.add(new Integer(solutionIndex));
            solutionsFoundSoFar.add(new Integer(solutionIndex));
            threatsSolved.add(threatSolvedFromSolution);
            
            Outer:
            for(int x = solutionIndex + 1; x < adjMatrix[solutionIndex].length ;x++){
                if(solutionIndex == x)
                    continue Outer;
                for( Integer i : solutionsFoundSoFar){
                    if( adjMatrix[i][x] == 0)
                        continue Outer;
                    }
                ArrayList<Integer> subSolutions = getSolutionIndexes(adjMatrix,new ArrayList<Integer>(solutionsFoundSoFar),new ArrayList<PossibleGroup>(threatsSolved),x);
                if(!subSolutions.isEmpty() && solutionIndexes.size() >= threats.size()){
                    solutionIndexes.addAll(subSolutions);
                }
                if( solutionIndexes.size() >= threats.size())
                    return solutionIndexes;
            }
            //System.out.println("Threats solved : " + solutionIndexes.size());
            return solutionIndexes;
        }//end else
        
    }//end getSolutionIndexes
    
    private List<Solution> findChosenSet(
            int [][] adjMatrix,                                     //Adj matrix
            Set<Entry<PossibleGroup, ArrayList<Solution>>> threats, //threats with solutions(therefore amount of solutions
            List<Solution> solutionsAvailable,                      // list of possible solutions still available to be used
            List<Solution> solutionsFound){                         // list of solution to be returned
        
        System.out.println("In findChosenSet");
        if( threats.isEmpty() ){
            System.out.println(" Threats is EMPTY. Returning!!!!!!");
            return solutionsFound;
                    
        }
        
        Iterator<Entry<PossibleGroup, ArrayList<Solution>>> it = threats.iterator();
        Entry<PossibleGroup, ArrayList<Solution>> mostDifficultNode = it.next();
        it.remove();
        
        System.out.println("Threat is: " + mostDifficultNode.getKey());
                
        for( Solution s: mostDifficultNode.getValue()){
            
            System.out.println("Attempting with solution("+mostDifficultNode.getValue().size()+") # " + s.solutionListNumber + ". S is " + s +" " + s.involved +
                    ". Size of solutions: " + solutionsAvailable.size());
            
            if( !solutionsAvailable.contains(s) ){
                System.out.println("S not in solutions available. S: " + s);
                continue;
            }
            solutionsFound.add(s);
            
            //Remove all solutions that cant be used with s from available
            List<Solution> cantBeUsed = new ArrayList<Solution>();
            for(Solution available : solutionsAvailable){
                if( adjMatrix[available.solutionListNumber][s.solutionListNumber] == 0)
                    cantBeUsed.add(available);
            }
            solutionsAvailable.removeAll(cantBeUsed);
            System.out.print("Removed: ");
            for(Solution removed : cantBeUsed){
                System.out.print(removed.solutionListNumber + " ");
            }
            System.out.println();
            
            List<Solution> solutionsToReturn = findChosenSet(adjMatrix, threats, solutionsAvailable, solutionsFound);
            if( solutionsToReturn != null)
                return solutionsToReturn;
            
            //Add all solution removed for not working with this solution
            solutionsAvailable.addAll(cantBeUsed);
        }
        return null;
    }
    
    private Map <PossibleGroup, ArrayList<Solution>> getSortedMapByKeyLength(Map <PossibleGroup, ArrayList<Solution>> unsortedMap){
       
        LinkedHashMap<PossibleGroup, ArrayList<Solution>> sortedMap = new LinkedHashMap<PossibleGroup, ArrayList<Solution>>();
        int max = 69;
        PossibleGroup maxGroup;
        for(PossibleGroup i : unsortedMap.keySet()){
            max = 69;
            maxGroup = null;
            for(PossibleGroup j : unsortedMap.keySet()){
                int size = unsortedMap.get(j).size();
                if( size < max && sortedMap.get(j) == null){
                    max = size;
                    maxGroup = j;
                }
            }
            if( maxGroup != null){
                sortedMap.put(maxGroup, unsortedMap.get(maxGroup));
            }
        }
        return sortedMap;
        
    }
    
    public void visualMap(Board b, ArrayList<Solution> values){
        String [][] view=new String[b.horizontal][b.vertical];
        for(int x=0;x<view.length;x++){
            for(int y=0;y<view[x].length;y++){
                view[x][y]=""+b.board[x][y];
            }
        }
        
             for(Solution s: values){
                 for(Position p: s.squares){
                     switch(s.name){
                         case BASEINVERSE:  view[p.x][p.y]+="B";break;    
                         case CLAIMEVEN:    view[p.x][p.y]+="C";break;
                         case VERTICAL:     view[p.x][p.y]+="V";break;
                         case AFTEREVEN:    view[p.x][p.y]+="A";break;
                         case LOWINVERSE:   view[p.x][p.y]+="L";break;
                         case HIGHINVERSE:  view[p.x][p.y]+="H";break;
                         case BASECLAIM:    view[p.x][p.y]+="M";break; 
                         case BEFORE:       view[p.x][p.y]+="F";break;
                         default:           view[p.x][p.y]+=""+s.name;
                     }

                 }//end 
             }//for
        
        for(int x=0;x<b.vertical;x++){
            System.out.println();
            for(int y=0;y<b.horizontal;y++){
                System.out.print(view[y][x]+" ");
                //System.out.print(x+""+y+" ");
            }//end for
        }//end for
        System.out.println();
        
        
    }//end visualMap
    
    public void threatMap(){
        Iterator<PossibleGroup> it = threats.iterator();
        while(it.hasNext()){
            PossibleGroup group = it.next();
            ArrayList<Position> positions = group.getGroup();
            Board b = new Board();
            int [][] tempBoard = b.board;
            for(Position p : positions){
                tempBoard[p.x][p.y] = 1 +(color%2);
            }
            System.out.println();
            System.out.print(group);
            b.drawBoard(false);
        }
    }
    
}
