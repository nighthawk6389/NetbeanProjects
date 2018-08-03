
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mavis Beacon
 */
public class ConvexHull extends JPanel implements ActionListener{
    
    private int n;
    private Point points [];
    static int MAX_RAND = 500;
    private double cheapInsertionTotalLength;
    private double optimalLength;
    private double [][] pointDistances;
    
    private JButton compute, newButton, twoOpt;
    private ConvexHullAnimation cha;
    
    public static void main(String args []){
        
        try{
            String nString = JOptionPane.showInputDialog("Please Enter The Amount Of Points");
            int n = Integer.parseInt(nString);
            ConvexHull ch = new ConvexHull(n); 

            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setSize(800,600);
            frame.getContentPane().add(ch);
        } catch(Exception e){
            System.out.println("Exception: " + e.toString());
        }
        
    }
    
    public ConvexHull(int n){
        this.setBackground(Color.white);
        
        this.n = n;
        this.reInitializePoints();
        
        /////////////
        //  GUI  ///
        ////////////
        this.setLayout(new BorderLayout());
        
        compute = new JButton("Compute Optimal Tour Length");
        compute.addActionListener(this);
        newButton = new JButton("New");
        newButton.addActionListener(this);
        twoOpt = new JButton("Two Opt");
        twoOpt.addActionListener(this);
        
        JPanel south = new JPanel();
        south.add(compute);
        south.add(newButton);
        south.add(twoOpt);
        
        this.add(cha,BorderLayout.CENTER);  
        this.add(south,BorderLayout.SOUTH); 
        
    }
    
    public void reInitializePoints(){
        this.points = new Point[n];
        ArrayList<Point> uniquePoints = new ArrayList<Point>();
        
        Random r = new Random();
        for(int x = 0; x < points.length; x++){
            Point p = new Point(r.nextInt(MAX_RAND),r.nextInt(MAX_RAND));
            if( uniquePoints.contains(p) ){
                x--;
                continue;
            }
            points[x] = p;
            uniquePoints.add(p);
        }
        
        // ConvexHull
        LinkedList<MyAngle> stack = doGrahamScan();
        
        //CheapInsertion
        ArrayList<MyAngle> vertices = tspTour(stack);
        
        cha = new ConvexHullAnimation(points,stack,vertices,cheapInsertionTotalLength);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == compute ){
            //Optimal TSP Tour
            int good = 1;
            if( points.length > 10){
                good = JOptionPane.showConfirmDialog(null,"Are you sure you want to compute the optimal path? \n N = " + points.length);
                if( good == JOptionPane.NO_OPTION || good == JOptionPane.CANCEL_OPTION )
                    return;
            }
            newButton.setEnabled(false);
            System.out.println("Computing Optimal Path...");
            LinkedList<TSPPoint> solution = this.computeOptimalLength();
            newButton.setEnabled(true);
            cha.optimalLength = optimalLength;
            cha.optimal = solution;
            cha.invalidate();
            cha.repaint();
        }
        if( e.getSource() == twoOpt){
            int n = 15;
            TwoOpt ch = new TwoOpt(n); 
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            frame.setSize(800,600);
            frame.getContentPane().add(ch);
            frame.setLocation(this.getX() + 50, this.getY() + 50);
        }
        if( e.getSource() == newButton){
            this.remove(cha);
            this.reInitializePoints();
            this.add(cha,BorderLayout.CENTER);  
            this.revalidate();
            this.repaint();
        }
            
    }
    
    private LinkedList<MyAngle> doGrahamScan() {
        Point lowestRightPoint = findLowestRightPoint();
        
        MyAngle [] sortedAngles = sortByAngleTo(lowestRightPoint);

        LinkedList<MyAngle> stack = new LinkedList<MyAngle>();
        stack.push( sortedAngles[sortedAngles.length - 1] );  
        stack.push( new MyAngle(0,lowestRightPoint) );
        
        int i = 0;
        while( i < sortedAngles.length ){
            MyAngle top = stack.pop();
            
            MyAngle second = stack.pop();
            stack.push(second);
   
            MyAngle three = sortedAngles[i];
            
            int crossProduct = (second.p.x - top.p.x)*(three.p.y - top.p.y) - (second.p.y - top.p.y)*(three.p.x - top.p.x);
            if( crossProduct > 0){ //left turn
                stack.push(top);
                stack.push(three);
                i++;
            }
           
        }
        return stack;
        
    }
    
    private Point findLowestRightPoint(){
        Point toReturn = points[0];
        for(Point p : points){
            if( p.y > toReturn.y || ( p.y == toReturn.y && p.x > toReturn.x))
                toReturn = p;
        }
        return toReturn;
    }
    
    private MyAngle[] sortByAngleTo(Point lrp) {
            
        //Populate angles
        MyAngle [] angles = new MyAngle[points.length - 1];
        int i = 0;
        for( Point p : points){
            if( p.equals(lrp)){
                //angles[i++] = new MyAngle(0, p);
                continue; 
            }
            Point xAxis = new Point( p.x, lrp.y);
            int adjacent = ( xAxis.x - lrp.x );
            double hypotnuse = Math.hypot( Math.abs( lrp.x - p.x ) , lrp.y - p.y);
            double cos = adjacent / hypotnuse;
            //if( adjacent <= 0 ){
            //    cos = Math.abs(cos) + 1;
            //}
            angles[i++] = new MyAngle( cos , p );
        }

        //Sort angles
        MyQuickSort.quickSort(angles, 0, angles.length - 1, MyQuickSort.DESCENDING); 
        
        //Break ties -- keep moving farther to the rights
        for( int j = 0; j < angles.length - 1; j++){
            MyAngle current = angles[j];
            MyAngle next = angles[ j + 1];
            if( current.angle == next.angle){
                Point p1 = next.p;
                Point p2 = current.p;
                double length1 = Math.hypot( ( lrp.x - p1.x ), lrp.y - p1.y);
                double length2 = Math.hypot( ( lrp.x - p2.x ), lrp.y - p2.y);
                if( length1 > length2){
                    MyAngle temp = angles[j]; 
                    angles[j] = angles[j + 1];
                    angles[j + 1] = temp;
                }
            }
        }
        
        //Return points
        return angles;
    }
    
    private ArrayList<MyAngle> tspTour(LinkedList<MyAngle> stack) {
        
        ArrayList<MyAngle> vertices = new ArrayList<MyAngle>();
        vertices.addAll(stack);
        
        ArrayList<Point> nonConvex = new ArrayList<Point>();
        Outer:
        for( Point p : points ){
            for( MyAngle a : vertices){
                if( p.equals( a.p ))
                    continue Outer;
            }
            nonConvex.add(p);
        }        
        
        System.out.println(nonConvex);
        
        double minDistance;
        int left;
        Point between;
        while( nonConvex.size() > 0 ){
            minDistance = Integer.MAX_VALUE;
            left = -1;
            between = null;
            for(int i = 0; i < vertices.size() - 1; i++){
                MyAngle u = vertices.get(i);
                int x1 = u.p.x;
                int y1 = u.p.y;
                MyAngle v = vertices.get(i+1);
                int x2 = v.p.x;
                int y2 = v.p.y;
                for( Iterator<Point> it2 = nonConvex.iterator(); it2.hasNext(); ){
                    Point pointToCheck = it2.next();
                    int newX = pointToCheck.x;
                    int newY = pointToCheck.y;
                    double distUV = Math.hypot( Math.abs( x1 - x2 ), Math.abs( y1 - y2 ) );
                    double distUX = Math.hypot( Math.abs( x1 - newX ), Math.abs( y1 - newY ) );
                    double distXV = Math.hypot( Math.abs( newX - x2 ), Math.abs( newY - y2 ) ); 
                    double totalDist = distUX + distXV - distUV;
  
                    if( totalDist < minDistance){
                        minDistance = totalDist;
                        left = i; 
                        between = pointToCheck;
                    }
                }
            }
            
            if( left != -1 && between != null){ 
                vertices.add(left + 1, new MyAngle(0,between));
                nonConvex.remove(between);
            }
        }     
        for(int i = 0; i < vertices.size() - 1; i++){
            MyAngle u = vertices.get(i);              
            MyAngle v = vertices.get(i+1);
            double distUV = Math.hypot( u.p.x - v.p.x, u.p.y - v.p.y );
            //System.out.println(u + " -> " + v + " : " +distUV);
            this.cheapInsertionTotalLength += distUV;
        }
        return vertices;
    }
    
    private LinkedList<TSPPoint> computeOptimalLength(){
        
        //Create a distance array so that we dont have to calc
        //distances every time
        pointDistances = new double[points.length][points.length];
        for(int x = 0; x < points.length; x++){
            pointDistances[x][x] = 0;
            for(int y = x + 1; y < points.length; y++){
                Point p1 = points[x];
                Point p2 = points[y];
                double dist = Math.hypot( Math.abs(p1.x - p2.x) , Math.abs((p1.y - p2.y)));
                pointDistances[x][y] = dist;
                pointDistances[y][x] = dist;
            }
        }
        
        
        LinkedList<TSPPoint> pointArray = new LinkedList<TSPPoint>();
        int index = 0;
        for( Point p : points){
            pointArray.addFirst(new TSPPoint(p,index++));
        }
        
        //Start iterating through every point using every point as the 
        //starting point. First remove the point being used and then
        //after the distance has been calced, add it back so that
        //we can compute the distance of other starting points
        TSPPoint start = null;
        LinkedList<TSPPoint> mySolution = new LinkedList<TSPPoint>();
        double minDistance = Double.MAX_VALUE; 
        int size = pointArray.size();
        for(int i = 0; i < size; i++){
            TSPPoint p = pointArray.remove(i);
            TSPHolder holder = findMinimalLength( pointArray , p ,  p );
            pointArray.add(i, p);
            if( holder.distance < minDistance ){
                //System.out.println("CHANGING OUTER " + p + " : old was " + minDistance + ". New is " + holder.distance);
                minDistance = holder.distance;
                start = p;
                mySolution = new LinkedList(holder.solution);
            }
        } 
        mySolution.addFirst(start);  
        optimalLength = minDistance; 
        System.out.println("Solution was : " + mySolution);
        System.out.println("MinDistance: " + minDistance);
        
        
        return mySolution;
    }
    
    private TSPHolder findMinimalLength( 
            LinkedList<TSPPoint> available, TSPPoint current, TSPPoint start ){
        
       // System.out.println();
       // System.out.println("In findMinimalLength"); 
       // System.out.println(available);
       // System.out.println(current);  
        
        //Add the last distance to the start point to bring it full circle
        if( available.size() == 0 ){
            double distance = pointDistances[current.indexInPointsArray][start.indexInPointsArray];
            LinkedList<TSPPoint> solution = new LinkedList<TSPPoint>();
            solution.addFirst(start);
            return new TSPHolder(distance, solution);
        } 
        
        double minDistance = Double.MAX_VALUE;
        int size = available.size();
        TSPHolder bestHolder = null;  
        TSPPoint best = null;
        for(int i = 0; i < size; i++){
            TSPPoint p = available.remove(i);
            double directDist = pointDistances[p.indexInPointsArray][current.indexInPointsArray];
            TSPHolder holder = findMinimalLength( available , p , start);
            double totalDist = directDist + holder.distance;
            if( totalDist < minDistance ){  
                //System.out.println("CHANGING " + current+ " : old was " + minDistance + ". New is " + totalDist);
                //System.out.println(available);
                minDistance = totalDist;
                bestHolder = holder;
                best = p;
            }
            available.add(i, p);  
        }
        if ( bestHolder != null ){
            bestHolder.distance = minDistance;
            bestHolder.solution.addFirst(best);
        }
        
        //System.out.println("returning min: " + bestHolder.distance + " for point " + current +" where solution is " + bestHolder.solution); 
        return bestHolder;
        
    }
    

}

class TSPPoint {

    Point p;
    int indexInPointsArray;

    public TSPPoint(Point p, int index){
        this.p = p;
        this.indexInPointsArray = index;
    }

    public String toString(){
        return p.toString();
    }

}

class TSPHolder {
    double distance;
    LinkedList<TSPPoint> solution = new LinkedList<TSPPoint>();
    
    public TSPHolder(double distance, LinkedList<TSPPoint> solution){
        this.distance = distance;
        this.solution = solution;
    }
}

class ConvexHullAnimation extends JPanel{
    private int CIRCLE_WIDTH = 10;
    
    private Point points [];
    private LinkedList<MyAngle> stackToDisplay = new LinkedList<MyAngle>();
    private ArrayList<MyAngle> cheapInsertionToDisplay = new ArrayList<MyAngle>();
    LinkedList<TSPPoint> optimal = new LinkedList<TSPPoint>();
    private double cheapInsertionTotalLength = -1;
    double optimalLength = -1;

    
    ConvexHullAnimation(Point [] points, LinkedList<MyAngle> stack, ArrayList<MyAngle> vertices, double cheapInsertionTotalLength) {
        this.points = points;
        this.stackToDisplay = stack;
        this.cheapInsertionToDisplay = vertices;
        this.cheapInsertionTotalLength = cheapInsertionTotalLength; 
        this.setBackground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
     
        double screenWidth = this.getWidth();
        double screenHeight = this.getHeight();
        
        double xScale = ( screenWidth  / (ConvexHull.MAX_RAND + 20) );
        double yScale = ( screenHeight / (ConvexHull.MAX_RAND + 100) );
        
        int xAdd = 0;
        int yAdd= 10; 
        
        g.setColor(Color.red);
        for(Point p : points){
            int x = (int) ( p.x * xScale + xAdd);
            int y = (int) ( p.y * yScale + yAdd);
            g.fillOval( x , y , CIRCLE_WIDTH, CIRCLE_WIDTH);
            g.drawString(p.x + " " + p.y, x, y);
        }
        
        g.setColor(Color.black);
        for( int x = 0; x < stackToDisplay.size() - 1; x++){
            MyAngle one = stackToDisplay.get(x);
            MyAngle two = stackToDisplay.get(x + 1);
            int x1 = (int) (one.p.x * xScale);
            int y1 = (int) (one.p.y * yScale + yAdd);
            int x2 = (int) (two.p.x * xScale);
            int y2 = (int) (two.p.y * yScale + yAdd);
            g.drawLine( x1 , y1 , x2 , y2 );
        }
        g.drawString("ConvexHull - Black", 70, (int)(screenHeight - 30) );
        
        g.setColor(Color.BLUE);
        for( int x = 0; x < cheapInsertionToDisplay.size() - 1; x++){
            MyAngle one = cheapInsertionToDisplay.get(x);
            MyAngle two = cheapInsertionToDisplay.get(x + 1);
            int x1 = (int) (one.p.x * xScale);
            int y1 = (int) (one.p.y * yScale + yAdd);
            int x2 = (int) (two.p.x * xScale);
            int y2 = (int) (two.p.y * yScale + yAdd);
            g.drawLine( x1 , y1 , x2 , y2 ); 
        }
        g.setColor(Color.blue);
        g.drawString("CheapInsertion - Blue - Length: " + (int)cheapInsertionTotalLength, 270, (int)(screenHeight - 30) );
        
        g.setColor(Color.GREEN);
        for( int x = 0; x < optimal.size() - 1; x++){
            TSPPoint one = optimal.get(x);
            TSPPoint two = optimal.get(x + 1);
            int x1 = (int) (one.p.x * xScale);
            int y1 = (int) (one.p.y * yScale + yAdd);
            int x2 = (int) (two.p.x * xScale);
            int y2 = (int) (two.p.y * yScale + yAdd);
            g.drawLine( x1 , y1 , x2 , y2 ); 
        }
        g.setColor(Color.green);
        if( optimal.size() > 0)
            g.drawString("Optimal - Green - Length: " + (int)optimalLength , 570, (int)(screenHeight - 30) ); 
    }
    
}

